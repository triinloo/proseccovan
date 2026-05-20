# POST /api/admin/bookings/{bookingId}/email

**Kontroller:** `AdminBookingController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

`AdminBookingView.vue` on adminile mõeldud broneeringu detailvaade (`/admin-bookings/:bookingId`). Lehe allosas on nupp „Saada email", millele vajutades saab administraator saata kliendile vabas vormis emaili. Admin sisestab emaili pealkirja ja sõnumi — backend otsib broneeringu põhjal kliendi emailiaadressi (`user.email`) ja saadab kirja sellele aadressile. Endpoint on kaitstud — ainult admin-rolliga kasutajad saavad emaile saata.

## Mocki vaade

![AdminBookingView mock](../../png/AdminBookingView.vue.png)

## API leping

| Väli | Väärtus |
|------|---------|
| Meetod | `POST` |
| Tee | `/api/admin/bookings/{bookingId}/email` |
| Auth | Jah (admin roll) |

### Request Body — `EmailRequestDto.java`

> Schema: [`EmailRequestDto_schema.json`](../../dtos/schema/EmailRequestDto_schema.json)
> Näidis: [`EmailRequestDto_AdminBookingView_example.json`](../../dtos/examples/EmailRequestDto_AdminBookingView_example.json)

| Väli | Tüüp | Kirjeldus |
|------|------|-----------|
| `emailTitle` | `String` | Emaili pealkiri |
| `emailMessage` | `String` | Emaili sisu |

### Response Body

Puudub — HTTP 200 tühi vastus

## Veahaldus

| Olukord | Exception klass | ErrorResponse enum | HTTP staatus |
|---------|----------------|-------------------|--------------|
| Broneeringut antud `bookingId`-ga ei leitud | `DataNotFoundException` | `DATA_NOT_FOUND` (333) | 404 |
| Autentimata kasutaja üritab emaili saata | — | — | 401 (Spring Security filter) |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> `DataNotFoundException` ja `DATA_NOT_FOUND` (333) on juba olemas — kasuta neid. Autentimise viga (401) käsitleb Spring Security filter chain.

## Andmebaas

Seotud tabelid: `booking`, `user`

Otsitakse `booking` tabelist rida `id = bookingId` järgi — kui ei leita, visatakse `DataNotFoundException`. Kliendi emailiaadressi saamiseks loetakse `user.email` (`booking.user_id = user.id`). Andmebaasi kirjutamine puudub — email saadetakse Spring `JavaMailSender` abil leitud emailiaadressile.

## Vastuvõtu kriteeriumid

- [ ] `POST /api/admin/bookings/{bookingId}/email` tagastab HTTP 200 tühja vastusega
- [ ] Email saadetakse broneeringuga seotud kliendi emailiaadressile (`user.email`)
- [ ] Emaili pealkiri vastab `emailTitle` väljale ja sisu `emailMessage` väljale
- [ ] Olematu `bookingId` korral tagastatakse HTTP 404 koos `DATA_NOT_FOUND` (333) veaga
- [ ] Autentimata päring tagastab HTTP 401 (Spring Security)
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav
