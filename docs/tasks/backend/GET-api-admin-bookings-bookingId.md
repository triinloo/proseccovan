# GET /api/admin-bookings/{bookingId}

**Kontroller:** `AdminBookingController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

Ühe broneeringu detailvaate endpoint, mida kasutab `AdminBookingView.vue` (URL: `/admin-bookings/:bookingId`). Admin näeb broneeringu kõiki andmeid koos kaardiga. Lehel on ka nupud "Kinnita" ja "Tühista" (kasutavad `PUT /api/admin-bookings/{bookingId}/confirm` ja `PUT /api/admin-bookings/{bookingId}/cancel` — need on juba implementeeritud `AdminBookingsController`-is) ning "Saada email" (`POST /api/admin-bookings/{bookingId}/email`).

## Mocki vaade

![AdminBookingView mock](../../png/AdminBookingView.vue.png)

## API leping

| Väli   | Väärtus                             |
|--------|-------------------------------------|
| Meetod | `GET`                               |
| Tee    | `/api/admin-bookings/{bookingId}`   |
| Auth   | Ei                                  |

### Request Body

Puudub — GET päring

### Response Body — `BookingsResponseDto.java`

> Schema: [`BookingsResponseDto_schema.json`](../../dtos/schema/BookingsResponseDto_schema.json)
> Näidis: [`BookingsResponseDto_AdminBookingView_example.json`](../../dtos/examples/BookingsResponseDto_AdminBookingView_example.json)

| Väli            | Tüüp     | Allikas (DB tabel.veerg)                               |
|-----------------|----------|--------------------------------------------------------|
| `customerName`  | `String` | `user_contact.user_name`                               |
| `email`         | `String` | `user.email`                                           |
| `phoneNumber`   | `String` | `user_contact.phone`                                   |
| `bookingDate`   | `String` | `booking.event_date` (formaadis yyyy-MM-dd)            |
| `bookingType`   | `String` | `booking.booking_type_info`                            |
| `packageType`   | `String` | `package.name` (MINI / MIDI / MAXI)                    |
| `address`       | `String` | `booking.address`                                      |
| `latitude`      | `String` | `booking.latitude`                                     |
| `longitude`     | `String` | `booking.longitude`                                    |
| `bookingInfo`   | `String` | `package.description`                                  |
| `bookingStatus` | `String` | `booking.status` (O=OOTEL, K=KINNITATUD, T=TÜHISTATUD) |

## Veahaldus

| Olukord                | Exception klass         | ErrorResponse enum | HTTP staatus |
|------------------------|-------------------------|--------------------|--------------|
| Broneeringut ei leitud | `DataNotFoundException` | `DATA_NOT_FOUND`   | 404          |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`

## Andmebaas

Seotud tabelid: `booking`, `package`, `user`, `user_contact`

Loetakse `booking` tabelist rida `bookingId` järgi. Kasutaja andmed loetakse `user` ja `user_contact` tabelitest. Paketi nimi ja kirjeldus loetakse `package` tabelist. `booking.status` char teisendatakse tekstiks. `booking.event_date` formaadis yyyy-MM-dd.

## Vastuvõtu kriteeriumid

- [ ] `GET /api/admin-bookings/{bookingId}` tagastab HTTP 200 ja `BookingsResponseDto`
- [ ] Broneeringut ei leitud: tagastab HTTP 404 koos `DATA_NOT_FOUND` veaga
- [ ] `bookingDate` on formaadis yyyy-MM-dd
- [ ] `bookingStatus` on loetav tekst (OOTEL / KINNITATUD / TÜHISTATUD)
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav