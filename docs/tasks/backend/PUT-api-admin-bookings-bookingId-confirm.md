# PUT /api/admin-bookings/{bookingId}/confirm

**Kontroller:** `AdminBookingsController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

Broneeringu kinnitamise endpoint, mida kasutab `AdminBookingsView.vue` (URL: `/admin-bookings`). Admin klõpsab "Kinnita" nuppu — broneeringu staatus muutub OOTEL → KINNITATUD. Kinnitamine on võimalik ainult OOTEL staatuses broneeringul. Samal lehel on ka `GET /api/admin-bookings` ja `PUT /api/admin-bookings/{bookingId}/cancel`.

## Mocki vaade

![AdminBookingsView mock](../../png/AdminBookingsView.vue.png)

## API leping

| Väli   | Väärtus                                       |
|--------|-----------------------------------------------|
| Meetod | `PUT`                                         |
| Tee    | `/api/admin-bookings/{bookingId}/confirm`      |
| Auth   | Ei                                            |

### Request Body

Puudub

### Response Body

Puudub — HTTP 200 tühi vastus

## Veahaldus

| Olukord                             | Exception klass         | ErrorResponse enum        | HTTP staatus |
|-------------------------------------|-------------------------|---------------------------|--------------|
| Broneeringut ei leitud              | `DataNotFoundException` | `DATA_NOT_FOUND`          | 404          |
| Broneering ei ole OOTEL staatuses   | `ForbiddenException`    | `CONFIRMATION_NOT_ALLOWED`| 403          |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> Puuduvate enum kirjete puhul lisa need `ErrorResponse`-i. Puuduvate exception klasside puhul loo uus klass `exception/` paketti (järgi olemasolevate klasside mustrit) ja registreeri see `RestExceptionHandler`-is.

## Andmebaas

Seotud tabelid: `booking`

Loetakse `booking` tabelist rida `bookingId` järgi. Kui ei leita, visatakse `DataNotFoundException`. Kui `booking.status != 'O'`, visatakse `ForbiddenException`. Vastasel juhul uuendatakse `booking.status = 'K'` (KINNITATUD).

## Vastuvõtu kriteeriumid

- [ ] `PUT /api/admin-bookings/{bookingId}/confirm` OOTEL broneeringu puhul tagastab HTTP 200
- [ ] Broneeringut ei leitud: tagastab HTTP 404 koos `DATA_NOT_FOUND` veaga
- [ ] Broneering ei ole OOTEL staatuses: tagastab HTTP 403 koos `CONFIRMATION_NOT_ALLOWED` veaga
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav