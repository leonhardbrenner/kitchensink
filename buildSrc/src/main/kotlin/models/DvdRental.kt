package models

import schema.Element

val dvdRental = Element.
model("Manifest") {

    x("DvdRental") {

        x("customer") {

            x(
                "customer_id",
                Int::class
            ) // integer DEFAULT nextval("public.customer_customer_id_seq"::regclass) NOT NULL,
            x("store_id", Int::class) // smallint NOT NULL,
            x("first_name", String::class) // character varying(45) NOT NULL,
            x("last_name", String::class) // character varying(45) NOT NULL,
            x("email", String::class) // character varying(50),
            x("address_id", Int::class) // smallint NOT NULL,
            x("activebool", Boolean::class) // boolean DEFAULT true NOT NULL,
            //x("create_date", OffsetDateTime::class) // date DEFAULT ("now"::text)::date NOT NULL,
            //x("last_update", Timestamp::class) // timestamp without time zone DEFAULT now(),
            x("active", Int::class) // integer
        }
        x("actor") {
            x("actor_id", Int::class) // integer DEFAULT nextval("public.actor_actor_id_seq"::regclass) NOT NULL,
            x("first_name", String::class) // character varying(45) NOT NULL,
            x("last_name", String::class) // character varying(45) NOT NULL,
            //x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("category") {
            x(
                "category_id",
                Int::class
            ) // integer DEFAULT nextval("public.category_category_id_seq"::regclass) NOT NULL,
            x("name", String::class) // character varying(25) NOT NULL,
            //x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("film") {
            x("film_id", Int::class) // integer DEFAULT nextval("public.film_film_id_seq"::regclass) NOT NULL,
            x("title", String::class) // character varying(255) NOT NULL,
            x("description", String::class) // text,
            x("release_year", Int::class) // public.year,
            x("language_id", Int::class) // smallint NOT NULL,
            x("rental_duration", Int::class) // smallint DEFAULT 3 NOT NULL,
            x("rental_rate", Double::class) // numeric(4,2) DEFAULT 4.99 NOT NULL,
            x("length", Int::class) // smallint,
            x("replacement_cost", Double::class) // numeric(5,2) DEFAULT 19.99 NOT NULL,
            x("rating", String::class) // public.mpaa_rating DEFAULT "G"::public.mpaa_rating,
            //x("last_update", Timestamp::class) // timestamp without time zone DEFAULT now() NOT NULL,
            //x("special_features", Int::class) // text[],
            //x("fulltext", Int::class) // tsvector NOT NULL
        }
        x("film_actor") {
            x("actor_id", Int::class) // smallint NOT NULL,
            x("film_id", Int::class) // smallint NOT NULL,
            //x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("film_category") {
            x("film_id", Int::class) // smallint NOT NULL,
            x("category_id", Int::class) // smallint NOT NULL,
            x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("address") {
            x("address_id", Int::class) // integer DEFAULT nextval("public.address_address_id_seq"::regclass) NOT NULL,
            x("address", String::class) // character varying(50) NOT NULL,
            x("address2", String::class) // character varying(50),
            x("district", String::class) // character varying(20) NOT NULL,
            x("city_id", Int::class) // smallint NOT NULL,
            x("postal_code", Int::class) // character varying(10),
            x("phone", Int::class) // character varying(20) NOT NULL,
            x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("city") {
            x("city_id", Int::class) // integer DEFAULT nextval("public.city_city_id_seq"::regclass) NOT NULL,
            x("city", String::class) // character varying(50) NOT NULL,
            x("country_id", Int::class) // smallint NOT NULL,
            x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("country") {
            x("country_id", Int::class) // integer DEFAULT nextval("public.country_country_id_seq"::regclass) NOT NULL,
            x("country", String::class) // character varying(50) NOT NULL,
            //x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("inventory") {
            x(
                "inventory_id",
                Int::class
            ) // integer DEFAULT nextval("public.inventory_inventory_id_seq"::regclass) NOT NULL,
            x("film_id", Int::class) // smallint NOT NULL,
            x("store_id", Int::class) // smallint NOT NULL,
            //x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("language") {
            x(
                "language_id",
                Int::class
            ) // integer DEFAULT nextval("public.language_language_id_seq"::regclass) NOT NULL,
            x("name", String::class) // character(20) NOT NULL,
            //x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("payment") {
            x("payment_id", Int::class) // integer DEFAULT nextval("public.payment_payment_id_seq"::regclass) NOT NULL,
            x("customer_id", Int::class) // smallint NOT NULL,
            x("staff_id", Int::class) // smallint NOT NULL,
            x("rental_id", Int::class) // integer NOT NULL,
            x("amount", Double::class) // numeric(5,2) NOT NULL,
            //x("payment_date", Int::class) // timestamp without time zone NOT NULL
        }
        x("rental") {
            x("rental_id", Int::class) // integer DEFAULT nextval("public.rental_rental_id_seq"::regclass) NOT NULL,
            x("rental_date", Int::class) // timestamp without time zone NOT NULL,
            x("inventory_id", Int::class) // integer NOT NULL,
            x("customer_id", Int::class) // smallint NOT NULL,
            //x("return_date", OffsetDatetime::class) // timestamp without time zone,
            x("staff_id", Int::class) // smallint NOT NULL,
            //x("last_update", OffsetDatetime::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        x("staff") {
            x("staff_id", Int::class) // integer DEFAULT nextval("public.staff_staff_id_seq"::regclass) NOT NULL,
            x("first_name", Int::class) // character varying(45) NOT NULL,
            x("last_name", Int::class) // character varying(45) NOT NULL,
            x("address_id", Int::class) // smallint NOT NULL,
            x("email", Int::class) // character varying(50),
            x("store_id", Int::class) // smallint NOT NULL,
            x("active", Int::class) // boolean DEFAULT true NOT NULL,
            x("username", String::class) // character varying(16) NOT NULL,
            x("password", String::class) // character varying(40),
            x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL,
            x("picture", Int::class) // bytea
        }
        x("store") {
            x("store_id", Int::class) // integer DEFAULT nextval("public.store_store_id_seq"::regclass) NOT NULL,
            x("manager_staff_id", Int::class) // smallint NOT NULL,
            x("address_id", Int::class) // smallint NOT NULL,
            x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
    }
}