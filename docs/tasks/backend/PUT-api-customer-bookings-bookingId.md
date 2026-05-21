# PUT /api/customer-bookings/{bookingId}

**Kontroller:** `CustomerChangeBookingFormController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

Broneeringu muutmise salvestamise endpoint, mida kasutab `CustomerChangeBookingFormView.vue` (URL: `/customer-change-booking-form`). Klient muudab vormi välju ja klõpsab "Muuda" — backend uuendab broneeringu andmed andmebaasis. Sündmuse tüüp ja kliendi nimi on kirjutuskaitstud ning neid ei muudeta. Samal lehel on ka `GET /api/customer-bookings/{bookingId}/change-booking-form` andmete laadimise endpoint.

## Mocki vaade

![CustomerChangeBookingFormView mock](../../png/CustomerChangeBookingFormView.vue.png)

## API leping

| Väli   | Väärtus                             |
|--------|-------------------------------------|
| Meetod | `PUT`                               |
| Tee    | `/api/customer-bookings/{bookingId}`|
| Auth   | Ei                                  |

### Request Body — `BookingRequestDto.java`

> Schema: [`BookingRequestDto_schema.json`](../../dtos/schema/BookingRequestDto_schema.json)
> Näidis: [`BookingRequestDto_CustomerChangeBookingFormView_example.json`](../../dtos/examples/BookingRequestDto_CustomerChangeBookingFormView_example.json)

| Väli           | Tüüp     | Kirjeldus                              |
|----------------|----------|----------------------------------------|
| `customerName` | `String` | Kliendi nimi (kirjutuskaitstud, info)  |
| `email`        | `String` | Kliendi e-post                         |
| `phoneNumber`  | `String` | Kliendi telefon                        |
| `bookingDate`  | `String` | Sündmuse kuupäev                       |
| `bookingType`  | `String` | Sündmuse tüüp (kirjutuskaitstud, info) |
| `address`      | `String` | Asukoha aadress                        |
| `latitude`     | `String` | Koordinaat (kaardi jaoks)              |
| `longitude`    | `String` | Koordinaat (kaardi jaoks)              |
| `packageType`  | `String` | Paketi nimi (MINI / MIDI / MAXI)       |
| `bookingInfo`  | `String` | Lisainfo sündmuse kohta                |

### Response Body

Puudub — HTTP 200 tühi vastus

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

Loetakse `booking` tabelist rida `bookingId` järgi — kui ei leita, visatakse `DataNotFoundException`. Uuendatakse `booking` tabeli väljad: `event_date`, `address`, `latitude`, `longitude`, `package_id`. Paketi ID leitakse `package` tabelist nime järgi (`packageType → package.name`). `user` ja `user_contact` tabelites uuendatakse `email` ja `phone`.

## Vastuvõtu kriteeriumid

- [ ] `PUT /api/customer-bookings/{bookingId}` tagastab HTTP 200 tühja vastusega
- [ ] Broneeringut ei leitud: tagastab HTTP 404 koos `DATA_NOT_FOUND` veaga
- [ ] Kõik muudetavad väljad uuendatakse andmebaasis
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav