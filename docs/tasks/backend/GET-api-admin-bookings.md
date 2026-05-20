# GET /api/admin-bookings

**Kontroller:** `AdminBookingsController.java`
**Tüüp:** Backend
**Staatus:** To Do

## Kontekst

Kõigi broneeringute nimekirja endpoint, mida kasutab `AdminBookingsView.vue` (URL: `/admin-bookings`). Admin näeb kõiki broneeringuid ja saab neid filtreerida staatuse järgi. "Vaata" nupp viib `AdminBookingView.vue` lehele. Samal lehel on ka `PUT /api/admin-bookings/{bookingId}/confirm` ja `PUT /api/admin-bookings/{bookingId}/cancel` endpointid.

## Mocki vaade

![AdminBookingsView mock](../../png/AdminBookingsView.vue.png)

## API leping

| Väli   | Väärtus                               |
|--------|---------------------------------------|
| Meetod | `GET`                                 |
| Tee    | `/api/admin-bookings?status={STATUS}` |
| Auth   | Ei                                    |

**Query parameeter:**

| Parameeter | Tüüp     | Kirjeldus                                                        |
|------------|----------|------------------------------------------------------------------|
| `status`   | `String` | Valikuline filter: `OOTEL`, `KINNITATUD` või `TÜHISTATUD`. Kui puudub, tagastatakse kõik broneeringud. |

### Request Body

Puudub — GET päring

### Response Body — `BookingSummaryDto.java`

> Schema: [`BookingSummaryDto_schema.json`](../../dtos/schema/BookingSummaryDto_schema.json)
> Näidis: [`BookingSummaryDto_AdminBookingsView_Array_example.json`](../../dtos/examples/BookingSummaryDto_AdminBookingsView_Array_example.json)

Tagastab **massiivi** `BookingSummaryDto` objektidest.

| Väli            | Tüüp     | Allikas (DB tabel.veerg)                               |
|-----------------|----------|--------------------------------------------------------|
| `bookingId`     | `String` | `booking.id` (formaadis "B0001")                       |
| `customerName`  | `String` | `user_contact.user_name`                               |
| `bookingDate`   | `String` | `booking.event_date` (formaadis yyyy-MM-dd)            |
| `bookingType`   | `String` | `booking.booking_type_info`                            |
| `location`      | `String` | `booking.address`                                      |
| `packageType`   | `String` | `package.name` (MINI / MIDI / MAXI)                    |
| `bookingStatus` | `String` | `booking.status` (O=OOTEL, K=KINNITATUD, T=TÜHISTATUD) |

> **Märkus:** `BookingSummaryDto` väljad klapivad täpselt olemasoleva `BookingSummaryDto`-ga. Kaalumisel, kas luua uus klass või taaskasutada `BookingSummaryDto`.

## Veahaldus

Veaolukordi ei ole — tühi nimekiri `[]` kui broneeringuid ei leidu.

## Andmebaas

Seotud tabelid: `booking`, `package`, `user_contact`

Kui `status` parameeter on antud, filtreeritakse `booking.status` järgi (OOTEL=O, KINNITATUD=K, TÜHISTATUD=T). Kliendi nimi loetakse `user_contact` tabelist. Paketi nimi loetakse `package` tabelist. `booking.id` kuvatakse formaadis "B0001".

## Vastuvõtu kriteeriumid

- [ ] `GET /api/admin-bookings` tagastab HTTP 200 ja kõik broneeringud
- [ ] `GET /api/admin-bookings?status=OOTEL` tagastab ainult OOTEL staatusega broneeringud
- [ ] Kui broneeringuid ei leidu, tagastatakse tühi massiiv `[]`
- [ ] `bookingId` on formaadis "B0001"
- [ ] `bookingStatus` on loetav tekst (OOTEL / KINNITATUD / TÜHISTATUD)
- [ ] Kõik DTO klassid on loodud Java klassidena õigesse paketti
- [ ] Controller, Service, Repository kihid on eraldatud
- [ ] Kontrolleri meetodil on `@Operation` ja `@ApiResponses` annotatsioonid (sh veavastused `ApiError` skeemiga)
- [ ] Swagger UI kaudu on endpoint nähtav ja testitav