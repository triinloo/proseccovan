# GET /api/packages

**Kontroller:** `PackageController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

Paketite loendi laadimise endpoint, mida kasutab `CustomerBookingFormView.vue` (URL: `/booking-form`). Leht laeb broneerimisankeedi laadimisel paketid andmebaasist, et kuvada need valikutena (Mini, Midi, Maxi). Pakettidest saab valida ainult ühe korraga — valik saadetakse hiljem koos teiste vormiandmetega `POST /api/booking-form` kaudu.

## Mocki vaade

![CustomerBookingFormView mock](../../png/CustomerBookingFormView.vue.png)

## API leping

| Väli   | Väärtus          |
|--------|------------------|
| Meetod | `GET`            |
| Tee    | `/api/packages`  |
| Auth   | Jah              |

### Request Body

Puudub — GET päring

### Response Body — `PackageDto.java`

> Schema: [`PackageDto_schema.json`](../../dtos/schema/PackageDto_schema.json)
> Näidis: [`PackageDto_CustomerBookingFormView_Array_example.json`](../../dtos/examples/PackageDto_CustomerBookingFormView_Array_example.json)

| Väli                 | Tüüp      | Allikas (DB tabel.veerg) |
|----------------------|-----------|--------------------------|
| `packageId`          | `Integer` | `package.id`             |
| `packageName`        | `String`  | `package.name`           |
| `packageDescription` | `String`  | `package.description`    |
| `isSelected`         | `Boolean` | Alati `false` — valik toimub frontendis |

## Veahaldus

Mock-is ei ole veaolukordi määratud. Endpoint tagastab tühja massiivi `[]`, kui pakette ei leita.

> **Märkus veahalduse kohta:**
> Kontrolli, kas vajalikud `ErrorResponse` enum kirjed ja exception klassid juba eksisteerivad:
> - `backend/src/main/java/proseccovan/backend/infrastructure/error/ErrorResponse.java`
> - `backend/src/main/java/proseccovan/backend/infrastructure/exception/`
>
> Puuduvate enum kirjete puhul lisa need `ErrorResponse`-i. Puuduvate exception klasside puhul loo uus klass
> `exception/` paketti (järgi olemasolevate klasside mustrit) ja registreeri see `RestExceptionHandler`-is.

## Andmebaas

Seotud tabelid: `package`

Loetakse kõik read `package` tabelist. Tagastatakse massiivina, kus iga objekti `isSelected = false` (kasutaja valik toimub frontendis, mitte andmebaasis).

## Vastuvõtu kriteeriumid

- [ ] `GET /api/packages` tagastab HTTP 200 ja paketite massiivi
- [ ] Tühi `package` tabel tagastab tühja massiivi `[]`
- [ ] `PackageDto` on loodud Java klassina õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav
