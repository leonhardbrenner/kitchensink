package models

//TODO - replace this! Look at DvdRentalNew which has the syntax I am moving towards and this will make more sense.
import schema.Element

val dvdRental = Element.
model("Manifest") {

    x("DvdRental") {

        //COPY public.actor (actor_id, first_name, last_name, last_update) FROM '$$PATH$$/3057.dat';
        x("actor") {
            x("actor_id", Int::class) // integer DEFAULT nextval("public.actor_actor_id_seq"::regclass) NOT NULL,
            x("first_name", String::class) // character varying(45) NOT NULL,
            x("last_name", String::class) // character varying(45) NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.address (address_id, address, address2, district, city_id, postal_code, phone, last_update) FROM '$$PATH$$/3065.dat';
        x("address") {
            x("address_id", Int::class) // integer DEFAULT nextval("public.address_address_id_seq"::regclass) NOT NULL,
            x("address", String::class) // character varying(50) NOT NULL,
            x("address2", String::class, nullable = true) // character varying(50),
            x("district", String::class, nullable = true) // character varying(20) NOT NULL,
            x("city_id", Int::class) // smallint NOT NULL,
            x("postal_code", String::class, nullable = true) // character varying(10),
            x("phone", String::class, nullable = true) // character varying(20) NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.category (category_id, name, last_update) FROM '$$PATH$$/3059.dat';
        x("category") {
            x(
                "category_id",
                Int::class
            ) // integer DEFAULT nextval("public.category_category_id_seq"::regclass) NOT NULL,
            x("name", String::class) // character varying(25) NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.city (city_id, city, country_id, last_update) FROM '$$PATH$$/3067.dat';
        x("city") {
            x("city_id", Int::class) // integer DEFAULT nextval("public.city_city_id_seq"::regclass) NOT NULL,
            x("city", String::class) // character varying(50) NOT NULL,
            x("country_id", Int::class) // smallint NOT NULL,
            x("last_update", Int::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.country (country_id, country, last_update) FROM '$$PATH$$/3069.dat';
        x("country") {
            x("country_id", Int::class) // integer DEFAULT nextval("public.country_country_id_seq"::regclass) NOT NULL,
            x("country", String::class) // character varying(50) NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.customer (customer_id, store_id, first_name, last_name, email, address_id, activebool, create_date, last_update, active) FROM '$$PATH$$/3055.dat';
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
            x("create_date", String::class) // date DEFAULT ("now"::text)::date NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now(),
            x("active", Int::class) // integer
        }
        //COPY public.film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) FROM '$$PATH$$/3061.dat';
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
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL,
            x("special_features", String::class) // text[],
            x("fulltext", String::class) // tsvector NOT NULL
        }
        //COPY public.film_actor (actor_id, film_id, last_update) FROM '$$PATH$$/3062.dat';
        x("film_actor") {
            x("actor_id", Int::class) // smallint NOT NULL,
            x("film_id", Int::class) // smallint NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.film_category (film_id, category_id, last_update) FROM '$$PATH$$/3063.dat';
        x("film_category") {
            x("film_id", Int::class) // smallint NOT NULL,
            x("category_id", Int::class) // smallint NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.inventory (inventory_id, film_id, store_id, last_update) FROM '$$PATH$$/3071.dat';
        x("inventory") {
            x(
                "inventory_id",
                Int::class
            ) // integer DEFAULT nextval("public.inventory_inventory_id_seq"::regclass) NOT NULL,
            x("film_id", Int::class) // smallint NOT NULL,
            x("store_id", Int::class) // smallint NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.language (language_id, name, last_update) FROM '$$PATH$$/3073.dat';
        x("language") {
            x(
                "language_id",
                Int::class
            ) // integer DEFAULT nextval("public.language_language_id_seq"::regclass) NOT NULL,
            x("name", String::class) // character(20) NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.payment (payment_id, customer_id, staff_id, rental_id, amount, payment_date) FROM '$$PATH$$/3075.dat';
        x("payment") {
            x("payment_id", Int::class) // integer DEFAULT nextval("public.payment_payment_id_seq"::regclass) NOT NULL,
            x("customer_id", Int::class) // smallint NOT NULL,
            x("staff_id", Int::class) // smallint NOT NULL,
            x("rental_id", Int::class) // integer NOT NULL,
            x("amount", Double::class) // numeric(5,2) NOT NULL,
            x("payment_date", String::class) // timestamp without time zone NOT NULL
        }
        //COPY public.rental (rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, last_update) FROM '$$PATH$$/3077.dat';
        x("rental") {
            x("rental_id", Int::class) // integer DEFAULT nextval("public.rental_rental_id_seq"::regclass) NOT NULL,
            x("rental_date", Int::class) // timestamp without time zone NOT NULL,
            x("inventory_id", Int::class) // integer NOT NULL,
            x("customer_id", Int::class) // smallint NOT NULL,
            x("return_date", String::class) // timestamp without time zone,
            x("staff_id", Int::class) // smallint NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.staff (staff_id, first_name, last_name, address_id, email, store_id, active, username, password, last_update, picture) FROM '$$PATH$$/3079.dat';
        x("staff") {
            x("staff_id", Int::class) // integer DEFAULT nextval("public.staff_staff_id_seq"::regclass) NOT NULL,
            x("first_name", String::class) // character varying(45) NOT NULL,
            x("last_name", String::class) // character varying(45) NOT NULL,
            x("address_id", Int::class) // smallint NOT NULL,
            x("email", Int::class) // character varying(50),
            x("store_id", Int::class) // smallint NOT NULL,
            x("active", Int::class) // boolean DEFAULT true NOT NULL,
            x("username", String::class) // character varying(16) NOT NULL,
            x("password", String::class) // character varying(40),
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL,
            x("picture", Int::class) // bytea
        }
        //COPY public.store (store_id, manager_staff_id, address_id, last_update) FROM '$$PATH$$/3081.dat';
        x("store") {
            x("store_id", Int::class) // integer DEFAULT nextval("public.store_store_id_seq"::regclass) NOT NULL,
            x("manager_staff_id", Int::class) // smallint NOT NULL,
            x("address_id", Int::class) // smallint NOT NULL,
            x("last_update", String::class) // timestamp without time zone DEFAULT now() NOT NULL
        }
    }
}.namespaces.first() //TODO - Drop the outer layer