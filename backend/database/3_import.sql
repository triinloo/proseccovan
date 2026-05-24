SET search_path TO proseccovan;

INSERT INTO role (id, name) VALUES (default, 'ADMIN');
INSERT INTO role (id, name) VALUES (default, 'CUSTOMER');

INSERT INTO "user" (id, role_id, email, password) VALUES (default, 1, 'admin@proseccovan.ee', '123');
INSERT INTO "user" (id, role_id, email, password) VALUES (default, 2, 'mari.mets@gmail.com', '123');
INSERT INTO "user" (id, role_id, email, password) VALUES (default, 2, 'jaan.tamm@gmail.com', '123');

INSERT INTO user_contact (id, user_id, phone, user_name) VALUES (default, 1, '+372 5000 0001', 'Admin Admin');
INSERT INTO user_contact (id, user_id, phone, user_name) VALUES (default, 2, '+372 5123 4567', 'Mari Mets');
INSERT INTO user_contact (id, user_id, phone, user_name) VALUES (default, 3, '+372 5765 4321', 'Jaan Tamm');

INSERT INTO package (id, name, description, price) VALUES (default, 'MINI', 'Kuni 20 inimest', 150.00);
INSERT INTO package (id, name, description, price) VALUES (default, 'MIDI', '20-40 inimest', 250.00);
INSERT INTO package (id, name, description, price) VALUES (default, 'MAXI', '40-60 inimest', 350.00);

INSERT INTO booking (id, user_id, package_id, address, longitude, latitude, event_date, status, booking_type_info) VALUES (default, 2, 1, 'Pärna 5, Tartu', 26.72509000, 58.37940000, '2026-06-15', 'O', 'Sünnipäev');
INSERT INTO booking (id, user_id, package_id, address, longitude, latitude, event_date, status, booking_type_info) VALUES (default, 2, 3, 'Vabaduse väljak 1, Tallinn', 24.74580000, 59.43360000, '2026-07-20', 'K', 'Pulmad');
INSERT INTO booking (id, user_id, package_id, address, longitude, latitude, event_date, status, booking_type_info) VALUES (default, 3, 2, 'Rüütli 10, Pärnu', 24.50270000, 58.38560000, '2026-08-05', 'O', 'Firmapidu');
INSERT INTO booking (id, user_id, package_id, address, longitude, latitude, event_date, status, booking_type_info) VALUES (default, 3, 1, 'Raekoja plats 3, Tartu', 26.72200000, 58.37980000, '2026-05-30', 'T', 'Sünnipäev');

INSERT INTO event (id, created_by_user_id, name, location, start_date, end_date, description, image_data) VALUES (default, 1, 'Toidu- ja veinimess', 'Tallinn', '2026-08-15', '2026-08-15', 'Nautige gurmeesöögiga ja peeneid veine, millega kaasas meie parim prosecco.', null);
INSERT INTO event (id, created_by_user_id, name, location, start_date, end_date, description, image_data) VALUES (default, 1, 'Kohalik laadapäev', 'Tartu', '2026-09-10', '2026-09-10', 'Külastage meie laabastandi kohalikul laadal ja nautige prosecco degusteerimist.', null);
INSERT INTO event (id, created_by_user_id, name, location, start_date, end_date, description, image_data) VALUES (default, 1, 'Suvefestival', 'Pärnu', '2026-07-01', '2026-07-03', 'Kolm päeva muusikat ja proseccot Pärnu rannas.', null);