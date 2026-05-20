 # POST /api/admin/events

**Kontroller:** `AdminEventController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

`AdminEventFormView.vue` on adminile mõeldud lehekülg (`/admin-event-form`), kus administraator saab luua uue sündmuse. Vormi täitmisel ja „Lisa sündmus" nupule vajutamisel saadetakse uue sündmuse andmed sellele endpointile. Pärast edukat loomist suunab frontend tagasi `AdminEventsView.vue` lehele (`/admin-events`). Endpoint on kaitstud — ainult admin-rolliga kasutajad saavad sündmusi luua.

## Mocki vaade

![AdminEventFormView mock](../../png/AdminEventFormView.vue.png)

## API leping

| Väli | Väärtus |
|------|---------|
| Meetod | `POST` |
| Tee | `/api/admin/events` |
| Auth | Jah (admin roll) |

### Request Body — `EventRequestDto.java`

> Schema: [`EventRequestDto_schema.json`](../../dtos/schema/EventRequestDto_schema.json)
> Näidis: [`EventRequestDto_AdminEventFormView_example.json`](../../dtos/examples/EventRequestDto_AdminEventFormView_example.json)

| Väli | Tüüp | Kirjeldus |
|------|------|-----------|
| `eventName` | `String` | Sündmuse nimi |
| `eventStartDate` | `String` (yyyy-MM-dd) | Sündmuse alguskuupäev |
| `eventEndDate` | `String` (yyyy-MM-dd) | Sündmuse lõpukuupäev |
| `eventLocation` | `String` | Sündmuse asukoht |
| `eventDescription` | `String` | Sündmuse kirjeldus |
| `imageData` | `String` | Pildi URL |

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
| Autentimata kasutaja üritab luua | — | — | 401 (Spring Security filter) |

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> Autentimise viga (401) käsitleb Spring Security filter chain, mitte kontroller ise. `created_by_user_id` salvestatakse tokenist loetud kasutaja ID põhjal teenuse kihis.

## Andmebaas

Seotud tabelid: `event`, `user`

Luuakse uus rida `event` tabelisse. `created_by_user_id` väli täidetakse autentitud kasutaja ID-ga (loetakse tokenist teenuse kihis). `image_url` veerg vastendatakse DTO väljalt `imageData`. Tagastatakse loodud sündmus HTTP 201 vastusega.

## Vastuvõtu kriteeriumid

- [ ] `POST /api/admin/events` tagastab HTTP 201 ja loodud sündmuse andmed
- [ ] Uus sündmus on andmebaasi `event` tabelis salvestatud korrektsetel väljadel
- [ ] `created_by_user_id` on täidetud autentitud kasutaja ID-ga
- [ ] Autentimata päring tagastab HTTP 401 (Spring Security)
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav
