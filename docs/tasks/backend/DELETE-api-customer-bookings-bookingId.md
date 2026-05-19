# DELETE /api/customer-bookings/{bookingId}

**Kontroller:** `CustomerBookingController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

Broneeringu tühistamise endpoint, mida kasutab `CustomerBookingView.vue` (URL: `/customer-booking`). Klient saab broneeringu tühistada ainult siis, kui selle staatus on "OOTEL" — teiste staatuste puhul tühistamine ei ole lubatud. "Tühista" nupp kutsub selle endpointi välja. Samal lehel on ka `GET /api/customer-bookings/{bookingId}` broneeringu kuvamise endpoint.

## Mocki vaade

![CustomerBookingView mock](../../png/CustomerBookingView.vue.png)

## API leping

| Väli   | Väärtus                              |
|--------|--------------------------------------|
| Meetod | `DELETE`                             |
| Tee    | `/api/customer-bookings/{bookingId}` |
| Auth   | Ei                                   |

### Request Body

Puudub

### Response Body

Puudub — HTTP 200 tühi vastus

## Veahaldus

| Olukord                              | Exception klass         | ErrorResponse enum        | HTTP staatus |
|--------------------------------------|-------------------------|---------------------------|--------------|
| Broneeringut ei leitud               | `DataNotFoundException` | `DATA_NOT_FOUND`          | 404          |
| Broneering ei ole OOTEL staatuses    | `ForbiddenException`    | `CANCELLATION_NOT_ALLOWED`| 403          |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> Puuduvate enum kirjete puhul lisa need `ErrorResponse`-i. Puuduvate exception klasside puhul loo uus klass `exception/` paketti (järgi olemasolevate klasside mustrit) ja registreeri see `RestExceptionHandler`-is.

## Andmebaas

Seotud tabelid: `booking`

Loetakse `booking` tabelist rida `bookingId` järgi. Kui broneeringut ei leita, visatakse `DataNotFoundException`. Kui `booking.status != 'O'`, visatakse `ForbiddenException`. Vastasel juhul uuendatakse `booking.status = 'T'` (TÜHISTATUD).

## Vastuvõtu kriteeriumid

- [ ] `DELETE /api/customer-bookings/{bookingId}` OOTEL broneeringu puhul tagastab HTTP 200
- [ ] Broneeringut ei leitud: tagastab HTTP 404 koos `DATA_NOT_FOUND` veaga
- [ ] Broneering ei ole OOTEL staatuses: tagastab HTTP 403 koos `CANCELLATION_NOT_ALLOWED` veaga
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav