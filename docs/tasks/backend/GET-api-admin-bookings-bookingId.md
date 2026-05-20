# GET /api/admin/bookings/{bookingId}

**Kontroller:** `AdminBookingController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

`AdminBookingView.vue` on adminile mõeldud detailvaade (`/admin-bookings/:bookingId`), kuhu suunab „Vaata" nupp `AdminBookingsView.vue` lehelt. Administraator näeb konkreetse broneeringu kõiki andmeid: kliendi kontaktinfo, sündmuse kuupäev, tüüp, pakett, lisainfo ja asukoha kaart. Lehel on nupud „Kinnita", „Tühista" (muudavad staatust) ning „Saada email". Endpoint on kaitstud — ainult admin-rolliga kasutajad saavad broneeringu andmeid näha.

## Mocki vaade

![AdminBookingView mock](../../png/AdminBookingView.vue.png)

## API leping

| Väli | Väärtus |
|------|---------|
| Meetod | `GET` |
| Tee | `/api/admin/bookings/{bookingId}` |
| Auth | Jah (admin roll) |

### Request Body

Puudub — GET päring

### Response Body — `AdminBookingResponseDto.java`

> Schema: [`AdminBookingResponseDto_schema.json`](../../dtos/schema/AdminBookingResponseDto_schema.json)
> Näidis: [`AdminBookingResponseDto_AdminBookingView_example.json`](../../dtos/examples/AdminBookingResponseDto_AdminBookingView_example.json)

| Väli | Tüüp | Allikas (DB tabel.veerg) |
|------|------|--------------------------|
| `bookingId` | `String` (nt `B0001`) | `booking.id` — formaadis `"B" + String.format("%04d", id)` |
| `customerName` | `String` | `user_contact.user_name` (joined `booking.user_id → user_contact.user_id`) |
| `email` | `String` | `user.email` (joined `booking.user_id`) |
| `phoneNumber` | `String` | `user_contact.phone` |
| `bookingDate` | `String` (dd/MM/yyyy) | `booking.event_date` |
| `bookingType` | `String` | `booking.booking_type_info` |
| `bookingPackageType` | `String` (`MINI` \| `MIDI` \| `MAXI`) | `package.name` (joined `booking.package_id`) |
| `bookingAddress` | `String` | `booking.address` |
| `latitude` | `String` | `booking.latitude` |
| `longitude` | `String` | `booking.longitude` |
| `info` | `String` | `package.description` |
| `bookingStatus` | `String` (`OOTEL` \| `KINNITATUD` \| `TÜHISTATUD`) | `booking.status` — teisendus: `O`→`OOTEL`, `K`→`KINNITATUD`, `T`→`TÜHISTATUD` |

## Veahaldus

| Olukord | Exception klass | ErrorResponse enum | HTTP staatus |
|---------|----------------|-------------------|--------------|
| Broneeringut antud `bookingId`-ga ei leitud | `DataNotFoundException` | `DATA_NOT_FOUND` (333) | 404 |
| Autentimata kasutaja üritab ligi pääseda | — | — | 401 (Spring Security filter) |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> `DataNotFoundException` ja `DATA_NOT_FOUND` (333) on juba olemas — kasuta neid. Autentimise viga (401) käsitleb Spring Security filter chain.

## Andmebaas

Seotud tabelid: `booking`, `user`, `user_contact`, `package`

Otsitakse `booking` tabelist rida `id = bookingId` järgi — kui ei leita, visatakse `DataNotFoundException`. Kliendi nime ja telefoni saamiseks joinitakse `user_contact` (`booking.user_id = user_contact.user_id`). Emaili saamiseks joinitakse `user` (`booking.user_id = user.id`). Paketi nime ja kirjelduse saamiseks joinitakse `package` (`booking.package_id = package.id`). `bookingId` teisendatakse formaati `B0001`. `bookingDate` teisendatakse formaati `dd/MM/yyyy`. `bookingStatus` saadakse `booking.status` teisendamisel: `O`→`OOTEL`, `K`→`KINNITATUD`, `T`→`TÜHISTATUD`.

## Vastuvõtu kriteeriumid

- [ ] `GET /api/admin/bookings/{bookingId}` tagastab HTTP 200 ja broneeringu detailid
- [ ] `bookingId` on formaadis `B0001` (4-kohaline, nullidega täidetud)
- [ ] `bookingStatus` on teisendatud: `O`→`OOTEL`, `K`→`KINNITATUD`, `T`→`TÜHISTATUD`
- [ ] `customerName` ja `phoneNumber` pärinevad `user_contact` tabelist
- [ ] `email` pärineb `user` tabelist
- [ ] `info` pärineb `package.description` väljalt
- [ ] Olematu `bookingId` korral tagastatakse HTTP 404 koos `DATA_NOT_FOUND` (333) veaga
- [ ] Autentimata päring tagastab HTTP 401 (Spring Security)
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav
