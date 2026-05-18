# ProseccoVan — Arendusplaan: Milestoneid ja taskid

> **Üldpõhimõte:** Iga milestone on iseseisvalt töötav tervik. Backendi endpoint tehakse valmis enne, kui frontendis seda kasutatakse.

---

## Milestone 1 — Projekti alus

*Eesmärk: rakendus käivitub, andmebaas ühendub, navigatsioon toimib.*

**Backend**
- [ ] Kontrolli, et Spring Boot käivitub ja Swagger UI (`/swagger-ui.html`) avaneb
- [ ] Kontrolli, et andmebaas ühendub (`application.properties` seaded õiged)
- [ ] SQL initskriptid (`1_reset`, `2_create`, `3_import`) jooksevad edukalt

**Frontend**
- [ ] Kontrolli, et `npm run dev` käivitub pordil 8081
- [ ] Seadista Vue Router — lisa kõik marsruudid (`/`, `/login`, `/register`, jne)
- [ ] Loo navigatsioonikomponent (nav bar: Prosecco Van, Broneeri, Sündmused, Logi sisse)
- [ ] Loo `HomeView.vue` — tutvustav tekst + pilt

---

## Milestone 2 — Autentimine

*Eesmärk: kasutaja saab sisse logida ja konto luua. Rakendus teab, kes on sisse logitud.*

**Backend**
- [ ] `POST /api/login` — kontrollib emaili ja parooli, tagastab `{ role, userId }`
- [ ] `POST /api/register` — loob uue kasutaja (`customerName`, `email`, `password`)

**Frontend**
- [ ] Loo `LoginView.vue` — sisselogimisvorm (email, parool, "Sisene" nupp)
- [ ] Lisa veateade valede andmete puhul (punane ääris + "Vale e-mail või parool")
- [ ] Loo `RegisterView.vue` — registreerimisvorm (nimi, email, parool, korda parooli)
- [ ] Loo Pinia store (`authStore`) — salvesta `userId`, `role`, sessioon
- [ ] Navibaris: kuva "Logi sisse" kui pole sisse logitud, "Logi välja" kui on

---

## Milestone 3 — Kliendi broneeringud

*Eesmärk: sisselogitud klient saab broneeringu teha, vaadata ja tühistada.*

**Backend**
- [ ] `GET /api/packages` — tagastab pakettide nimekirja (MINI, MIDI, MAXI)
- [ ] `POST /api/booking-form` — salvestab uue broneeringu andmebaasi
- [ ] `GET /api/bookings/user/:userId` — tagastab kasutaja broneeringute nimekirja
- [ ] `GET /api/customer-bookings/:bookingId` — ühe broneeringu täisdetailid
- [ ] `PATCH /api/customer-bookings/:bookingId` — muudab broneeringut (ainult Ootel staatusega)
- [ ] `DELETE /api/customer-bookings/:bookingId` — tühistab broneeringu

**Frontend**
- [ ] Loo `CustomerBookingFormView.vue` — broneerimisankeet (nimi, email, telefon, kuupäev, pakett, asukoht, lisainfo)
- [ ] Lisa kaardi modal — kasutaja saab kaardil asukohta valida, koordinaadid täidetakse automaatselt
- [ ] Loo `CustomerBookingsView.vue` — tabel kliendi broneeringutest (staatus värviga, "Muuda" nupp Ootel real)
- [ ] Loo `CustomerBookingView.vue` — ühe broneeringu detailvaade ("Tühista" + "Sulge" nupud)
- [ ] Loo `CustomerChangeBookingFormView.vue` — muutmisvorm (laeb olemasolevad andmed, "Muuda" nupp)

---

## Milestone 4 — Sündmused

*Eesmärk: avalik sündmuste leht toimib, admin saab sündmusi hallata.*

**Backend**
- [ ] `GET /api/events?season={ALL|KEVAD|SUVI|SÜGIS|TALV}` — avalik sündmuste nimekiri hooaja filtriga
- [ ] `GET /api/admin/events` — admin sündmuste nimekiri
- [ ] `POST /api/admin/event/` — uue sündmuse lisamine
- [ ] `GET /api/admin/events/:eventId` — ühe sündmuse laadimine (muutmiseks)
- [ ] `PUT /api/admin/events/:eventId` — sündmuse muutmine
- [ ] `DELETE /api/admin/events/:eventId` — sündmuse kustutamine

**Frontend**
- [ ] Loo `EventsView.vue` — kaardid sündmustega, hooaja dropdown filter (Kõik/Suvi/Sügis/Kevad/Talv)
- [ ] Loo `AdminEventsView.vue` — sündmuste nimekiri redigeeri/kustuta ikoonidega + "Lisa uus sündmus" nupp
- [ ] Loo `AdminEventFormView.vue` — vorm uue sündmuse lisamiseks ja muutmiseks (nimi, asukoht, kuupäevavahemik, kirjeldus, pilt)

---

## Milestone 5 — Admin broneeringute haldus

*Eesmärk: admin näeb kõiki broneeringuid, saab kinnitada, tühistada ja kliendile emaili saata.*

**Backend**
- [ ] `GET /api/admin-bookings?status={STATUS}` — broneeringute nimekiri staatuse filtriga
- [ ] `GET /api/admin-bookings/:bookingId` — ühe broneeringu täisdetailid (adminile)
- [ ] `PUT /api/admin-bookings/:bookingId/confirm` — kinnita broneering (Ootel → Kinnitatud)
- [ ] `PUT /api/admin-bookings/:bookingId/cancel` — tühista broneering (Ootel/Kinnitatud → Tühistatud)
- [ ] `POST /api/admin-bookings/:bookingId/email` — saada kliendile email (`emailTitle`, `emailMessage`)

**Frontend**
- [ ] Loo `AdminBookingsView.vue` — broneeringute tabel staatuse filtri ja otsinguga, staatuse värvikoodid (kollane/roheline/hall), "Vaata"/"Kinnita"/"Tühista" nupud
- [ ] Loo `AdminBookingView.vue` — detailvaade kaardiga, "Kinnita"/"Tühista"/"Saada email" nupud
- [ ] Email modal — admin saab sisestada emaili pealkirja ja sõnumi

---

## Milestone 6 — Viimistlus

*Eesmärk: rakendus on turvaline, navigatsioon töötab rollide järgi, vead on kaetud.*

- [ ] Lisa marsruudi kaitsed (route guards) — klient ei pääse admin-lehtedele, sisselogimata kasutaja ei pääse broneerimisvormile
- [ ] Loo `ErrorView.vue` — 404 vealeht ("Ups! Midagi läks valesti")
- [ ] Lisa laadimisanimatsioonid API päringute ajaks
- [ ] Testi kõik API endpointid Swagger UI kaudu
- [ ] Testi frontend vood läbi (happy path + veavood)

---

## Kokkuvõte

| Milestone | Sisu | Soovitatav järjekord |
|-----------|------|----------------------|
| 1 | Projekt käivitub, navigatsioon | Esimene |
| 2 | Login + Register | Teine — kõik muu vajab autentimist |
| 3 | Kliendi broneeringud | Kolmas — põhifunktsioon |
| 4 | Sündmused | Neljas — sõltumatum osa |
| 5 | Admin haldus | Viies — eeldab milestone 3 valmimist |
| 6 | Viimistlus | Viimane |
