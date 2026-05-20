# GET /api/admin/bookings

**Kontroller:** `AdminBookingController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

`AdminBookingsView.vue` on adminile mõeldud lehekülg (`/admin-bookings`), kus administraator näeb kõiki klientide broneeringuid. Iga rea juures on nupud „Vaata" (suunab `AdminBookingView.vue` detailvaatele), „Kinnita" ja „Tühista" (muudavad broneeringu staatust). Lehe staatusefilter (`Kõik / Ootel / Kinnitatud / Tühistatud`) toimib frontendis — backend tagastab alati kõik broneeringud. Endpoint on kaitstud — ainult admin-rolliga kasutajad saavad broneeringuid näha.

## Mocki vaade

![AdminBookingsView mock](../../png/AdminBookingsView.vue.png)

## API leping

| Väli | Väärtus |
|------|---------|
| Meetod | `GET` |
| Tee | `/api/admin/bookings` |
| Auth | Jah (admin roll) |

### Request Body

Puudub — GET päring

### Response Body — `BookingSummaryDto.java`

> Schema: [`BookingSummaryDto_schema.json`](../../dtos/schema/BookingSummaryDto_schema.json)
> Näidis: [`BookingSummaryDto_AdminBookingsView_Array_example.json`](../../dtos/examples/BookingSummaryDto_AdminBookingsView_Array_example.json)

Tagastatakse massiiv (`List<BookingSummaryDto>`).

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
| Broneeringuid ei leitud (tühi tulemus) | — | — | 200 (tühi massiiv `[]`) |
| Autentimata kasutaja üritab ligi pääseda | — | — | 401 (Spring Security filter) |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> Autentimise viga (401) käsitleb Spring Security filter chain, mitte kontroller ise. Tühi tulemus tagastab HTTP 200 koos tühja massiiviga — viga ei visata.

## Andmebaas

Seotud tabelid: `booking`, `user_contact`, `package`

Loetakse kõik read `booking` tabelist. Kliendi nime saamiseks joinitakse `user_contact` tabeliga (`booking.user_id = user_contact.user_id`). Paketi nime saamiseks joinitakse `package` tabeliga (`booking.package_id = package.id`). `bookingId` formaadiks teisendatakse `booking.id` kujule `B0001` (4-kohaline, nullidega täidetud). `bookingStatus` saadakse `booking.status` char-välja teisendamisel: `O`→`OOTEL`, `K`→`KINNITATUD`, `T`→`TÜHISTATUD`. `bookingDate` formaadiks teisendatakse `booking.event_date` kujule `dd/MM/yyyy`.

## Vastuvõtu kriteeriumid

- [ ] `GET /api/admin/bookings` tagastab HTTP 200 ja kõigi broneeringute massiivi
- [ ] Tühja tulemuse korral tagastatakse HTTP 200 koos tühja massiiviga `[]`
- [ ] `bookingId` on formaadis `B0001` (4-kohaline, nullidega täidetud)
- [ ] `bookingStatus` on teisendatud: `O`→`OOTEL`, `K`→`KINNITATUD`, `T`→`TÜHISTATUD`
- [ ] `customerName` pärineb `user_contact.user_name` väljalt
- [ ] `packageType` pärineb `package.name` väljalt
- [ ] Autentimata päring tagastab HTTP 401 (Spring Security)
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav
