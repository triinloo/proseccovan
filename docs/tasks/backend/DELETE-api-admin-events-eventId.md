# DELETE /api/admin/events/{eventId}

**Kontroller:** `AdminEventController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

`AdminEventsView.vue` on adminile mõeldud lehekülg (`/admin-events`), kus iga sündmuse kaardil on kustutamisnupp (prügikasti ikoon). Selle nupu vajutamisel kutsutakse see endpoint välja, et kustutada konkreetne sündmus andmebaasist. Pärast edukat kustutamist uuendab frontend sündmuste nimekirja (kutsub uuesti `GET /api/admin/events`). Endpoint on kaitstud — ainult admin-rolliga kasutajad saavad sündmusi kustutada.

## Mocki vaade

![AdminEventsView mock](../../png/AdminEventsView.vue.png)

## API leping

| Väli | Väärtus |
|------|---------|
| Meetod | `DELETE` |
| Tee | `/api/admin/events/{eventId}` |
| Auth | Jah (admin roll) |

### Request Body

Puudub — `eventId` edastatakse URL path variable'ina

### Response Body

Puudub — HTTP 204 tühi vastus

## Veahaldus

| Olukord | Exception klass | ErrorResponse enum | HTTP staatus |
|---------|----------------|-------------------|--------------|
| Sündmust antud `eventId`-ga ei leitud | `DataNotFoundException` | `DATA_NOT_FOUND` (333) | 404 |
| Autentimata kasutaja üritab kustutada | — | — | 401 (Spring Security filter) |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> `DataNotFoundException` ja `DATA_NOT_FOUND` (333) on juba olemas — kasuta neid. Autentimise viga käsitleb Spring Security filter chain, mitte kontroller ise.

## Andmebaas

Seotud tabelid: `event`

Kustutatakse üks rida `event` tabelist, kus `id = eventId`. Enne kustutamist tuleb kontrollida, kas antud `id`-ga kirje eksisteerib — kui mitte, visata `DataNotFoundException`. Arvestada tuleb ka FK piiranguga: kui `event`-il on seotud broneeringuid (`booking` tabelis), võib andmebaas visata constraint violation — see tuleb teenuse kihis eelnevalt kontrollida või asjakohase vea kaudu kasutajale teatada.

## Vastuvõtu kriteeriumid

- [ ] `DELETE /api/admin/events/{eventId}` tagastab HTTP 204 olemasoleva sündmuse korral
- [ ] Olematu `eventId` korral tagastatakse HTTP 404 koos `DATA_NOT_FOUND` (333) veaga
- [ ] Autentimata päring tagastab HTTP 401 (Spring Security)
- [ ] Sündmus on pärast kustutamist andmebaasist eemaldatud
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav
