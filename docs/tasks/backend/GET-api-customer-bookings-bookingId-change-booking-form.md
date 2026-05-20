# GET /api/customer-bookings/{bookingId}/change-booking-form

**Kontroller:** `CustomerChangeBookingFormController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

Broneeringu muutmisvormi eeltäitmise endpoint, mida kasutab `CustomerChangeBookingFormView.vue` (URL: `/customer-change-booking-form`). Lehe avamisel laaditakse olemasoleva broneeringu andmed vormi väljadele, et klient saaks neid muuta. Ees- ja perekonnanimi ning sündmuse tüüp on vormil kirjutuskaitstud. Samal lehel on ka `PUT /api/customer-bookings/{bookingId}` salvestamise endpoint.

## Mocki vaade

![CustomerChangeBookingFormView mock](../../png/CustomerChangeBookingFormView.vue.png)

## API leping

| Väli   | Väärtus                                                  |
|--------|----------------------------------------------------------|
| Meetod | `GET`                                                    |
| Tee    | `/api/customer-bookings/{bookingId}/change-booking-form` |
| Auth   | Ei                                                       |

### Request Body

Puudub — GET päring

### Response Body — `BookingRequestDto.java`

> Schema: [`BookingRequestDto_schema.json`](../../dtos/schema/BookingRequestDto_schema.json)
> Näidis: [`BookingRequestDto_CustomerChangeBookingFormView_example.json`](../../dtos/examples/BookingRequestDto_CustomerChangeBookingFormView_example.json)

| Väli           | Tüüp     | Allikas (DB tabel.veerg)                    |
|----------------|----------|---------------------------------------------|
| `customerName` | `String` | `user_contact.user_name`                    |
| `email`        | `String` | `user.email`                                |
| `phoneNumber`  | `String` | `user_contact.phone`                        |
| `bookingDate`  | `String` | `booking.event_date`                        |
| `bookingType`  | `String` | `booking.booking_type_info`                 |
| `address`      | `String` | `booking.address`                           |
| `latitude`     | `String` | `booking.latitude`                          |
| `longitude`    | `String` | `booking.longitude`                         |
| `packageType`  | `String` | `package.name` (MINI / MIDI / MAXI)         |
| `bookingInfo`  | `String` | `package.description`                       |

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

Loetakse `booking` tabelist rida `bookingId` järgi. Kasutaja andmed loetakse `user` ja `user_contact` tabelitest. Paketi nimi ja kirjeldus loetakse `package` tabelist (`booking.package_id → package.id`).

## Vastuvõtu kriteeriumid

- [ ] `GET /api/customer-bookings/{bookingId}/change-booking-form` tagastab HTTP 200 ja `BookingRequestDto`
- [ ] Broneeringut ei leitud: tagastab HTTP 404 koos `DATA_NOT_FOUND` veaga
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav