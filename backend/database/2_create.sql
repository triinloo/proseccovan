-- Created by Redgate Data Modeler (https://datamodeler.redgate-platform.com)
-- Last modification date: 2026-05-14 09:20:19.966

SET search_path TO proseccovan;

-- tables
-- Table: booking
CREATE TABLE booking (
    id serial  NOT NULL,
    user_id int  NOT NULL,
    package_id int  NOT NULL,
    address varchar(255)  NOT NULL,
    longitude decimal(11,8)  NULL,
    latitude decimal(10,8)  NULL,
    event_date date  NOT NULL,
    status char(1)  NOT NULL,
    booking_type_info varchar(255)  NOT NULL,
    CONSTRAINT bookings_pk PRIMARY KEY (id)
);

-- Table: event
CREATE TABLE event (
    id serial  NOT NULL,
    created_by_user_id int  NOT NULL,
    name varchar(255)  NOT NULL,
    location varchar(255)  NOT NULL,
    start_date date  NOT NULL,
    end_date date  NOT NULL,
    description varchar(500)  NULL,
    image_url text  NULL,
    CONSTRAINT events_pk PRIMARY KEY (id)
);

-- Table: package
CREATE TABLE package (
    id serial  NOT NULL,
    name varchar(255)  NOT NULL,
    description varchar(255)  NOT NULL,
    price decimal(7,2)  NULL,
    CONSTRAINT package_pk PRIMARY KEY (id)
);

-- Table: role
CREATE TABLE role (
    id serial  NOT NULL,
    name varchar(20)  NOT NULL,
    CONSTRAINT role_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE "user" (
    id serial  NOT NULL,
    role_id int  NOT NULL,
    password varchar(255)  NOT NULL,
    email varchar(255)  NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

-- Table: user_contact
CREATE TABLE user_contact (
    id serial  NOT NULL,
    user_id int  NOT NULL,
    phone varchar(255)  NOT NULL,
    user_name varchar(255)  NOT NULL,
    CONSTRAINT user_contact_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: booking_package (table: booking)
ALTER TABLE booking ADD CONSTRAINT booking_package
    FOREIGN KEY (package_id)
    REFERENCES package (id)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference: bookings_user (table: booking)
ALTER TABLE booking ADD CONSTRAINT bookings_user
    FOREIGN KEY (user_id)
    REFERENCES "user" (id)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference: event_user (table: event)
ALTER TABLE event ADD CONSTRAINT event_user
    FOREIGN KEY (created_by_user_id)
    REFERENCES "user" (id)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference: user_contact_user (table: user_contact)
ALTER TABLE user_contact ADD CONSTRAINT user_contact_user
    FOREIGN KEY (user_id)
    REFERENCES "user" (id)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference: user_role (table: user)
ALTER TABLE "user" ADD CONSTRAINT user_role
    FOREIGN KEY (role_id)
    REFERENCES role (id)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- End of file.