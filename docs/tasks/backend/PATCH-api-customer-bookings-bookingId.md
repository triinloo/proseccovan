# PATCH /api/customer-bookings/{bookingId}

**Kontroller:** `CustomerChangeBookingFormController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

Broneeringu muutmise endpoint, mida kasutab `CustomerChangeBookingFormView.vue` (URL: `/customer-change-booking-form`). Klient muudab broneeringu andmeid ja kinnitab "Muuda" nupuga. Ees- ja perekonnanimi ning sündmuse tüüp on vormil kirjutuskaitstud ja neid ei muudeta. Samal lehel on ka `GET /api/customer-bookings/{bookingId}` vormi eeltäitmise endpoint.

## Mocki vaade

![CustomerChangeBookingFormView mock](../../png/CustomerChangeBookingFormView.vue.png)

## API leping

| Väli   | Väärtus                              |
|--------|--------------------------------------|
| Meetod | `PATCH`                              |
| Tee    | `/api/customer-bookings/{bookingId}` |
| Auth   | Ei                                   |

### Request Body — `BookingRequestDto.java`

> Schema: [`BookingRequestDto_schema.json`](../../dtos/schema/BookingRequestDto_schema.json)
> Näidis: [`BookingRequestDto_CustomerChangeBookingFormView_example.json`](../../dtos/examples/BookingRequestDto_CustomerChangeBookingFormView_example.json)

| Väli           | Tüüp     | Kirjeldus                              |
|----------------|----------|----------------------------------------|
| `customerName` | `String` | Kliendi nimi (kirjutuskaitstud, info)  |
| `email`        | `String` | Kliendi e-post                         |
| `phoneNumber`  | `String` | Kliendi telefon                        |
| `bookingType`  | `String` | Sündmuse tüüp (kirjutuskaitstud, info) |
| `bookingDate`  | `String` | Sündmuse kuupäev (dd/MM/yyyy)          |
| `address`      | `String` | Asukoha aadress                        |
| `latitude`     | `String` | Koordinaat (kaardi jaoks)              |
| `longitude`    | `String` | Koordinaat (kaardi jaoks)              |
| `packageType`  | `String` | Paketi nimi (MINI / MIDI / MAXI)       |
| `bookingInfo`  | `String` | Lisainfo sündmuse kohta                |

### Response Body — `BookingResponseDto.java`

> Schema: [`BookingResponseDto_schema.json`](../../dtos/schema/BookingResponseDto_schema.json)
> Näidis: [`BookingRequestDto_CustomerChangeBookingFormView_example.json`](../../dtos/examples/BookingRequestDto_CustomerChangeBookingFormView_example.json)

| Väli           | Tüüp     | Allikas (DB tabel.veerg)            |
|----------------|----------|-------------------------------------|
| `customerName` | `String` | `user_contact.user_name`            |
| `email`        | `String` | `user.email`                        |
| `phoneNumber`  | `String` | `user_contact.phone`                |
| `bookingType`  | `String` | `booking.booking_type_info`         |
| `bookingDate`  | `String` | `booking.event_date` (dd/MM/yyyy)   |
| `address`      | `String` | `booking.address`                   |
| `latitude`     | `String` | `booking.latitude`                  |
| `longitude`    | `String` | `booking.longitude`                 |
| `packageType`  | `String` | `package.name` (MINI / MIDI / MAXI) |
| `bookingInfo`  | `String` | `package.description`               |

## Veahaldus

| Olukord                | Exception klass         | ErrorResponse enum | HTTP staatus |
|------------------------|-------------------------|--------------------|--------------|
| Broneeringut ei leitud | `DataNotFoundException` | `DATA_NOT_FOUND`   | 404          |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> Puuduvate enum kirjete puhul lisa need `ErrorResponse`-i. Puuduvate exception klasside puhul loo uus klass `exception/` paketti (järgi olemasolevate klasside mustrit) ja registreeri see `RestExceptionHandler`-is.

## Andmebaas

Seotud tabelid: `booking`, `package`, `user`, `user_contact`

Loetakse `booking` tabelist rida `bookingId` järgi — kui ei leita, visatakse `DataNotFoundException`. Uuendatakse `booking` tabelis: `event_date`, `address`, `latitude`, `longitude`, `package_id` (leitakse `package` tabelist nime järgi). `user` tabelis uuendatakse `email`. `user_contact` tabelis uuendatakse `phone`.

## Vastuvõtu kriteeriumid

- [ ] `PATCH /api/customer-bookings/{bookingId}` tagastab HTTP 200 ja uuendatud `BookingResponseDto`
- [ ] Broneeringut ei leitud: tagastab HTTP 404 koos `DATA_NOT_FOUND` veaga
- [ ] Kõik muudetavad väljad uuendatakse andmebaasis
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav