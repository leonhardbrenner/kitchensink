package models

import schemanew.Manifest

val dvdRentalsNew = Manifest.namespace("DvdRental") {
    //COPY public.actor (actor_id, first_name, last_name, last_update) FROM '$$PATH$$/3057.dat';
    //This causes a stacktrace and what is below does not actually make entries into our complexTypeMap.
    complexType("actor") {
        element("actor_id", "builtin:int") {
            db("actor_id", "integer DEFAULT nextval(\"public.actor_actor_id_seq\"::regclass) NOT NULL")
        }
        element("first_name", "builtin:string") {
            db("first_name", "character varying(45) NOT NULL")
        }
        element("last_name", "builtin:string") {
            db("last_name", "character varying(45) NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.address (address_id, address, address2, district, city_id, postal_code, phone, last_update) FROM '$$PATH$$/3065.dat';
    complexType("address") {
        element("address_id", "builtin:int") {
            db("address_id", "integer DEFAULT nextval(\"public.address_address_id_seq\"::regclass) NOT NULL")
        }
        element("address", "builtin:string") {
            db("address", "character varying(50) NOT NULL")
        }
        element("address2", "builtin:nullableString") {
            db("address2", "character varying(50)")
        }
        element("district", "builtin:nullableString") {
            db("district", "character varying(20) NOT NULL")
        }
        element("city_id", "builtin:int") {
            db("city_id", "smallint NOT NULL")
        }
        element("postal_code", "builtin:nullableString") {
            db("postal_code", "character varying(10)")
        }
        element("phone", "builtin:nullableString") {
            db("phone", "character varying(20) NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.category (category_id, name, last_update) FROM '$$PATH$$/3059.dat';
    complexType("category") {
        element("category_id", "builtin:int") {
            db("category_id", "integer DEFAULT nextval(\"public.category_category_id_seq\"::regclass) NOT NULL")
        }
        element("name", "builtin:string") {
            db("name", "character varying(25) NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.city (city_id, city, country_id, last_update) FROM '$$PATH$$/3067.dat';
    complexType("city") {
        element("city_id", "builtin:int") {
            db("city_id", "integer DEFAULT nextval(\"public.city_city_id_seq\"::regclass) NOT NULL")
        }
        element("city", "builtin:string") {
            db("city", "character varying(50) NOT NULL")
        }
        element("country_id", "builtin:int") {
            db("country_id", "smallint NOT NULL")
        }
        element("last_update", "builtin:int") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.country (country_id, country, last_update) FROM '$$PATH$$/3069.dat';
    complexType("country") {
        element("country_id", "builtin:int") {
            db("country_id", "integer DEFAULT nextval(\"public.country_country_id_seq\"::regclass) NOT NULL")
        }
        element("country", "builtin:string") {
            db("country", "character varying(50) NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.customer (customer_id, store_id, first_name, last_name, email, address_id, activebool, create_date, last_update, active) FROM '$$PATH$$/3055.dat';
    complexType("customer") {
        element("customer_id", "builtin:int") {
            db("customer_id", "integer DEFAULT nextval(\"public.customer_customer_id_seq\"::regclass) NOT NULL")
        }
        element("store_id", "builtin:int") {
            db("store_id", "smallint NOT NULL")
        }
        element("first_name", "builtin:string") {
            db("first_name", "character varying(45) NOT NULL")
        }
        element("last_name", "builtin:string") {
            db("last_name", "character varying(45) NOT NULL")
        }
        element("email", "builtin:string") {
            db("email", "character varying(50)")
        }
        element("address_id", "builtin:int") {
            db("address_id", "smallint NOT NULL")
        }
        element("activebool", "builtin:boolean") {
            db("activebool", "boolean DEFAULT true NOT NULL")
        }
        element("create_date", "builtin:string") {
            db("create_date", "date DEFAULT (\"now\"::text)::date NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now()")
        }
        element("active", "builtin:int") {
            db("active", "integer")
        }
    }
    //COPY public.film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) FROM '$$PATH$$/3061.dat';
    complexType("film") {
        element("film_id", "builtin:int") {
            db("film_id", "integer DEFAULT nextval(\"public.film_film_id_seq\"::regclass) NOT NULL")
        }
        element("title", "builtin:string") {
            db("title", "character varying(255) NOT NULL")
        }
        element("description", "builtin:string") {
            db("description", "text")
        }
        element("release_year", "builtin:int") {
            db("release_year", "public.year")
        }
        element("language_id", "builtin:int") {
            db("language_id", "smallint NOT NULL")
        }
        element("rental_duration", "builtin:int") {
            db("rental_duration", "smallint DEFAULT 3 NOT NULL")
        }
        element("rental_rate", "builtin:double") {
            db("rental_rate", "numeric(4,2) DEFAULT 4.99 NOT NULL")
        }
        element("length", "builtin:int") {
            db("length", "smallint")
        }
        element("replacement_cost", "builtin:double") {
            db("replacement_cost", "numeric(5,2) DEFAULT 19.99 NOT NULL")
        }
        element("rating", "builtin:string") {
            db("rating", "public.mpaa_rating DEFAULT \"G\"::public.mpaa_rating")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
        element("special_features", "builtin:string") {
            db("special_features", "text[]")
        }
        element("fulltext", "builtin:string") {
            db("fulltext", "tsvector NOT NULL")
        }
    }
    //COPY public.film_actor (actor_id, film_id, last_update) FROM '$$PATH$$/3062.dat';
    complexType("film_actor") {
        element("actor_id", "builtin:int") {
            db("actor_id", "smallint NOT NULL")
        }
        element("film_id", "builtin:int") {
            db("film_id", "smallint NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.film_category (film_id, category_id, last_update) FROM '$$PATH$$/3063.dat';
    complexType("film_category") {
        element("film_id", "builtin:int") {
            db("film_id", "smallint NOT NULL")
        }
        element("category_id", "builtin:int") {
            db("category_id", "smallint NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.inventory (inventory_id, film_id, store_id, last_update) FROM '$$PATH$$/3071.dat';
    complexType("inventory") {
        element("inventory_id", "builtin:int") {
            db("inventory_id", "integer DEFAULT nextval(\"public.inventory_inventory_id_seq\"::regclass) NOT NULL")
        }
        element("film_id", "builtin:int") {
            db("film_id", "smallint NOT NULL")
        }
        element("store_id", "builtin:int") {
            db("store_id", "smallint NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.language (language_id, name, last_update) FROM '$$PATH$$/3073.dat';
    complexType("language") {
        element("language_id", "builtin:int") {
            db("language_id", "integer DEFAULT nextval(\"public.language_language_id_seq\"::regclass) NOT NULL")
        }
        element("name", "builtin:string") {
            db("name", "character(20) NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.payment (payment_id, customer_id, staff_id, rental_id, amount, payment_date) FROM '$$PATH$$/3075.dat';
    complexType("payment") {
        element("payment_id","builtin:int") {
            db("payment_id", "integer DEFAULT nextval(\"public.payment_payment_id_seq\"::regclass) NOT NULL")
        }
        element("customer_id", "builtin:int") {
            db("customer_id", "smallint NOT NULL")
        }
        element("staff_id", "builtin:int") {
            db("staff_id", "smallint NOT NULL")
        }
        element("rental_id", "builtin:int") {
            db("rental_id", "integer NOT NULL")
        }
        element("amount", "builtin:double") {
            db("amount", "numeric(5,2) NOT NULL")
        }
        element("payment_date", "builtin:string") {
            db("payment_date", "timestamp without time zone NOT NULL")
        }
    }
    //COPY public.rental (rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, last_update) FROM '$$PATH$$/3077.dat';
    complexType("rental") {
        element("rental_id", "builtin:int") {
            db("rental_id", "integer DEFAULT nextval(\"public.rental_rental_id_seq\"::regclass) NOT NULL")
        }
        element("rental_date", "builtin:int") {
            db("rental_date", "timestamp without time zone NOT NULL")
        }
        element("inventory_id", "builtin:int") {
            db("inventory_id", "integer NOT NULL")
        }
        element("customer_id", "builtin:int") {
            db("customer_id", "smallint NOT NULL")
        }
        element("return_date", "builtin:string") {
            db("return_date", "timestamp without time zone")
        }
        element("staff_id", "builtin:int") {
            db("staff_id", "smallint NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.staff (staff_id, first_name, last_name, address_id, email, store_id, active, username, password, last_update, picture) FROM '$$PATH$$/3079.dat';
    complexType("staff") {
        element("staff_id", "builtin:int") {
            db("staff_id", "integer DEFAULT nextval(\"public.staff_staff_id_seq\"::regclass) NOT NULL")
        }
        element("first_name", "builtin:string") {
            db("first_name", "character varying(45) NOT NULL")
        }
        element("last_name", "builtin:string") {
            db("last_name", "character varying(45) NOT NULL")
        }
        element("address_id", "builtin:int") {
            db("address_id", "smallint NOT NULL")
        }
        element("email", "builtin:int") {
            db("email", "character varying(50)")
        }
        element("store_id", "builtin:int") {
            db("store_id", "smallint NOT NULL")
        }
        element("active", "builtin:int") {
            db("active", "boolean DEFAULT true NOT NULL")
        }
        element("username", "builtin:string") {
            db("username", "character varying(16) NOT NULL")
        }
        element("password", "builtin:string") {
            db("password", "character varying(40)")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
        element("picture", "builtin:int") {
            db("picture", "bytea")
        }
    }
    //COPY public.store (store_id, manager_staff_id, address_id, last_update) FROM '$$PATH$$/3081.dat';
    complexType("store") {
        element("store_id", "builtin:int") {
            db("store_id", "integer DEFAULT nextval(\"public.store_store_id_seq\"::regclass) NOT NULL")
        }
        element("manager_staff_id", "builtin:int") {
            db("manager_staff_id", "smallint NOT NULL")
        }
        element("address_id", "builtin:int") {
            db("address_id", "smallint NOT NULL")
        }
        element("last_update", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
}
