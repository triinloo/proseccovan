# GET /api/admin/events/{eventId}

**Kontroller:** `AdminEventController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

`AdminEventFormView.vue` on adminile mõeldud lehekülg (`/admin-event-form`), kus administraator saab nii luua uue sündmuse kui ka muuta olemasolevat. Kui admin klõpsab `AdminEventsView.vue` lehel sündmuse redigeerimise nupul, suunatakse ta `AdminEventFormView.vue` lehele koos `eventId` URL-i parameetriga. Lehe laadimise hetkel kutsutakse see endpoint välja, et laadida sündmuse praegused andmed vormi eeltäitmiseks. Ilma selle endpointita ei teaks vorm, mida kuvatakse. Endpoint on kaitstud — ainult admin-rolliga kasutajad saavad sündmuse andmeid laadida.

## Mocki vaade

![AdminEventFormView mock](../../png/AdminEventFormView.vue.png)

## API leping

| Väli | Väärtus |
|------|---------|
| Meetod | `GET` |
| Tee | `/api/admin/events/{eventId}` |
| Auth | Jah (admin roll) |

### Request Body

Puudub — `eventId` edastatakse URL path variable'ina

### Response Body — `EventDetailResponseDto.java`

> Schema: [`EventDetailResponseDto_schema.json`](../../dtos/schema/EventDetailResponseDto_schema.json)
> Näidis: [`EventDetailResponseDto_AdminEventFormView_example.json`](../../dtos/examples/EventDetailResponseDto_AdminEventFormView_example.json)

| Väli | Tüüp | Allikas (DB tabel.veerg) |
|------|------|--------------------------|
| `eventId` | `Integer` | `event.id` |
| `eventName` | `String` | `event.name` |
| `eventStartDate` | `String` (yyyy-MM-dd) | `event.start_date` |
| `eventEndDate` | `String` (yyyy-MM-dd) | `event.end_date` |
| `eventLocation` | `String` | `event.location` |
| `eventDescription` | `String` | `event.description` |
| `imageData` | `String` | `event.image_url` |

## Veahaldus

| Olukord | Exception klass | ErrorResponse enum | HTTP staatus |
|---------|----------------|-------------------|--------------|
| Sündmust antud `eventId`-ga ei leitud | `DataNotFoundException` | `DATA_NOT_FOUND` (333) | 404 |
| Autentimata kasutaja üritab ligi pääseda | — | — | 401 (Spring Security filter) |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> `DataNotFoundException` ja `DATA_NOT_FOUND` (333) on juba olemas — kasuta neid. Autentimise viga (401) käsitleb Spring Security filter chain.

## Andmebaas

Seotud tabelid: `event`

Loetakse üks rida `event` tabelist, kus `id = eventId`. `image_url` veerg vastendatakse DTO väljale `imageData`. Kui kirjet ei eksisteeri, visatakse `DataNotFoundException`.

## Vastuvõtu kriteeriumid

- [ ] `GET /api/admin/events/{eventId}` tagastab HTTP 200 ja sündmuse andmed
- [ ] Olematu `eventId` korral tagastatakse HTTP 404 koos `DATA_NOT_FOUND` (333) veaga
- [ ] Autentimata päring tagastab HTTP 401 (Spring Security)
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav
