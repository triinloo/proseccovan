# PUT /api/admin/bookings/{bookingId}/confirm

**Kontroller:** `AdminBookingController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

`AdminBookingsView.vue` on adminile mõeldud lehekülg (`/admin-bookings`), kus administraator haldab klientide broneeringuid. Iga broneeringu real on nupp „Kinnita", millele vajutades muudetakse broneeringu staatus `OOTEL`-ist `KINNITATUD`-ks. Endpoint uuendab ainult `booking.status` väärtuse — kõik muud väljad jäävad muutmata. Endpoint on kaitstud — ainult admin-rolliga kasutajad saavad broneeringuid kinnitada.

## Mocki vaade

![AdminBookingsView mock](../../png/AdminBookingsView.vue.png)

## API leping

| Väli | Väärtus |
|------|---------|
| Meetod | `PUT` |
| Tee | `/api/admin/bookings/{bookingId}/confirm` |
| Auth | Jah (admin roll) |

### Request Body

Puudub — `bookingId` on URL-is

### Response Body — `BookingSummaryDto.java`

> Schema: [`BookingSummaryDto_schema.json`](../../dtos/schema/BookingSummaryDto_schema.json)
> Näidis: [`BookingSummaryDto_AdminBookingsView_example.json`](../../dtos/examples/BookingSummaryDto_AdminBookingsView_example.json)

| Väli | Tüüp | Allikas (DB tabel.veerg) |
|------|------|--------------------------|
| `bookingId` | `String` (nt `B0001`) | `booking.id` — formaadis `"B" + String.format("%04d", id)` |
| `customerName` | `String` | `user_contact.user_name` (joined `booking.user_id → user_contact.user_id`) |
| `bookingDate` | `String` (dd/MM/yyyy) | `booking.event_date` |
| `bookingType` | `String` | `booking.booking_type_info` |
| `location` | `String` | `booking.address` |
| `packageType` | `String` (`MINI` \| `MIDI` \| `MAXI`) | `package.name` (joined `booking.package_id`) |
| `bookingStatus` | `String` (`OOTEL` \| `KINNITATUD` \| `TÜHISTATUD`) | `booking.status` — teisendus: `O`→`OOTEL`, `K`→`KINNITATUD`, `T`→`TÜHISTATUD` |

## Veahaldus

| Olukord | Exception klass | ErrorResponse enum | HTTP staatus |
|---------|----------------|-------------------|--------------|
| Broneeringut antud `bookingId`-ga ei leitud | `DataNotFoundException` | `DATA_NOT_FOUND` (333) | 404 |
| Autentimata kasutaja üritab kinnitada | — | — | 401 (Spring Security filter) |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> `DataNotFoundException` ja `DATA_NOT_FOUND` (333) on juba olemas — kasuta neid. Autentimise viga (401) käsitleb Spring Security filter chain.

## Andmebaas

Seotud tabelid: `booking`, `user_contact`, `package`

Otsitakse `booking` tabelist rida `id = bookingId` järgi — kui ei leita, visatakse `DataNotFoundException`. Uuendatakse `booking.status = 'K'`. Tagastamiseks joinitakse `user_contact` (`booking.user_id = user_contact.user_id`) ja `package` (`booking.package_id = package.id`) tabelid, et koostada `BookingSummaryDto`.

## Vastuvõtu kriteeriumid

- [ ] `PUT /api/admin/bookings/{bookingId}/confirm` tagastab HTTP 200 ja uuendatud `BookingSummaryDto`
- [ ] `booking.status` on andmebaasis uuendatud väärtusele `K`
- [ ] Olematu `bookingId` korral tagastatakse HTTP 404 koos `DATA_NOT_FOUND` (333) veaga
- [ ] Autentimata päring tagastab HTTP 401 (Spring Security)
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav
