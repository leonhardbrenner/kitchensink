package models

import schemanew.Manifest

val dvdRentalsNew = Manifest.namespace("DvdRental") {
    //COPY public.actor (actor_id, first_name, last_name, last_update) FROM '$$PATH$$/3057.dat';
    //This causes a stacktrace and what is below does not actually make entries into our complexTypeMap.
    complexType("Actor") {
        element("actorId", "builtin:int") {
            db("actor_id", "integer DEFAULT nextval(\"public.actor_actor_id_seq\"::regclass) NOT NULL")
        }
        element("firstName", "builtin:string") {
            db("first_name", "character varying(45) NOT NULL")
        }
        element("lastName", "builtin:string") {
            db("last_name", "character varying(45) NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.address (address_id, address, address2, district, city_id, postal_code, phone, last_update) FROM '$$PATH$$/3065.dat';
    complexType("Address") {
        element("addressId", "builtin:int") {
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
        element("cityId", "builtin:int") {
            db("city_id", "smallint NOT NULL")
        }
        element("postalCode", "builtin:nullableString") {
            db("postal_code", "character varying(10)")
        }
        element("phone", "builtin:nullableString") {
            db("phone", "character varying(20) NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.category (category_id, name, last_update) FROM '$$PATH$$/3059.dat';
    complexType("Category") {
        element("categoryId", "builtin:int") {
            db("category_id", "integer DEFAULT nextval(\"public.category_category_id_seq\"::regclass) NOT NULL")
        }
        element("name", "builtin:string") { //TODO - to be consistent this should be category also I think it avoids name intersections in joins.
            db("name", "character varying(25) NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.city (city_id, city, country_id, last_update) FROM '$$PATH$$/3067.dat';
    complexType("City") {
        element("cityId", "builtin:int") {
            db("city_id", "integer DEFAULT nextval(\"public.city_city_id_seq\"::regclass) NOT NULL")
        }
        element("city", "builtin:string") {
            db("city", "character varying(50) NOT NULL")
        }
        element("countryId", "builtin:int") {
            db("country_id", "smallint NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.country (country_id, country, last_update) FROM '$$PATH$$/3069.dat';
    complexType("Country") {
        element("countryId", "builtin:int") {
            db("country_id", "integer DEFAULT nextval(\"public.country_country_id_seq\"::regclass) NOT NULL")
        }
        element("country", "builtin:string") {
            db("country", "character varying(50) NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.customer (customer_id, store_id, first_name, last_name, email, address_id, activebool, create_date, last_update, active) FROM '$$PATH$$/3055.dat';
    complexType("Customer") {
        element("customerId", "builtin:int") {
            db("customer_id", "integer DEFAULT nextval(\"public.customer_customer_id_seq\"::regclass) NOT NULL")
        }
        element("storeId", "builtin:int") {
            db("store_id", "smallint NOT NULL")
        }
        element("firstName", "builtin:string") {
            db("first_name", "character varying(45) NOT NULL")
        }
        element("lastName", "builtin:string") {
            db("last_name", "character varying(45) NOT NULL")
        }
        element("email", "builtin:string") {
            db("email", "character varying(50)")
        }
        element("addressId", "builtin:int") {
            db("address_id", "smallint NOT NULL")
        }
        element("activebool", "builtin:boolean") {
            db("activebool", "boolean DEFAULT true NOT NULL")
        }
        element("createDate", "builtin:string") {
            db("create_date", "date DEFAULT (\"now\"::text)::date NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now()")
        }
        element("active", "builtin:int") {
            db("active", "integer")
        }
    }
    //COPY public.film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) FROM '$$PATH$$/3061.dat';
    complexType("Film") {
        element("filmId", "builtin:int") {
            db("film_id", "integer DEFAULT nextval(\"public.film_film_id_seq\"::regclass) NOT NULL")
        }
        element("title", "builtin:string") {
            db("title", "character varying(255) NOT NULL")
        }
        element("description", "builtin:string") {
            db("description", "text")
        }
        element("releaseYear", "builtin:int") {
            db("release_year", "public.year")
        }
        element("languageId", "builtin:int") {
            db("language_id", "smallint NOT NULL")
        }
        element("rentalDuration", "builtin:int") {
            db("rental_duration", "smallint DEFAULT 3 NOT NULL")
        }
        element("rentalRate", "builtin:double") {
            db("rental_rate", "numeric(4,2) DEFAULT 4.99 NOT NULL")
        }
        element("length", "builtin:int") {
            db("length", "smallint")
        }
        element("replacementCost", "builtin:double") {
            db("replacement_cost", "numeric(5,2) DEFAULT 19.99 NOT NULL")
        }
        element("rating", "builtin:string") {
            db("rating", "public.mpaa_rating DEFAULT \"G\"::public.mpaa_rating")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
        element("specialFeatures", "builtin:string") {
            db("special_features", "text[]")
        }
        element("fullText", "builtin:string") {
            db("fulltext", "tsvector NOT NULL")
        }
    }
    //COPY public.film_actor (actor_id, film_id, last_update) FROM '$$PATH$$/3062.dat';
    complexType("FilmActor") {
        element("actorId", "builtin:int") {
            db("actor_id", "smallint NOT NULL")
        }
        element("filmId", "builtin:int") {
            db("film_id", "smallint NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.film_category (film_id, category_id, last_update) FROM '$$PATH$$/3063.dat';
    complexType("FilmCategory") {
        element("filmId", "builtin:int") {
            db("film_id", "smallint NOT NULL")
        }
        element("categoryId", "builtin:int") {
            db("category_id", "smallint NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.inventory (inventory_id, film_id, store_id, last_update) FROM '$$PATH$$/3071.dat';
    complexType("Inventory") {
        element("inventoryId", "builtin:int") {
            db("inventory_id", "integer DEFAULT nextval(\"public.inventory_inventory_id_seq\"::regclass) NOT NULL")
        }
        element("filmId", "builtin:int") {
            db("film_id", "smallint NOT NULL")
        }
        element("storeId", "builtin:int") {
            db("store_id", "smallint NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.language (language_id, name, last_update) FROM '$$PATH$$/3073.dat';
    complexType("Language") {
        element("languageId", "builtin:int") {
            db("language_id", "integer DEFAULT nextval(\"public.language_language_id_seq\"::regclass) NOT NULL")
        }
        element("name", "builtin:string") {
            db("name", "character(20) NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.payment (payment_id, customer_id, staff_id, rental_id, amount, payment_date) FROM '$$PATH$$/3075.dat';
    complexType("Payment") {
        element("paymentId","builtin:int") {
            db("payment_id", "integer DEFAULT nextval(\"public.payment_payment_id_seq\"::regclass) NOT NULL")
        }
        element("customerId", "builtin:int") {
            db("customer_id", "smallint NOT NULL")
        }
        element("staffId", "builtin:int") {
            db("staff_id", "smallint NOT NULL")
        }
        element("rentalId", "builtin:int") {
            db("rental_id", "integer NOT NULL")
        }
        element("amount", "builtin:double") {
            db("amount", "numeric(5,2) NOT NULL")
        }
        element("paymentDate", "builtin:string") {
            db("payment_date", "timestamp without time zone NOT NULL")
        }
    }
    //COPY public.rental (rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, last_update) FROM '$$PATH$$/3077.dat';
    complexType("Rental") {
        element("rentalId", "builtin:int") {
            db("rental_id", "integer DEFAULT nextval(\"public.rental_rental_id_seq\"::regclass) NOT NULL")
        }
        element("rentalDate", "builtin:string") {
            db("rental_date", "timestamp without time zone NOT NULL")
        }
        element("inventoryId", "builtin:int") {
            db("inventory_id", "integer NOT NULL")
        }
        element("customerId", "builtin:int") {
            db("customer_id", "smallint NOT NULL")
        }
        element("returnDate", "builtin:string") {
            db("return_date", "timestamp without time zone")
        }
        element("staffId", "builtin:int") {
            db("staff_id", "smallint NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
    //COPY public.staff (staff_id, first_name, last_name, address_id, email, store_id, active, username, password, last_update, picture) FROM '$$PATH$$/3079.dat';
    complexType("Staff") {
        element("staffId", "builtin:int") {
            db("staff_id", "integer DEFAULT nextval(\"public.staff_staff_id_seq\"::regclass) NOT NULL")
        }
        element("firstName", "builtin:string") {
            db("first_name", "character varying(45) NOT NULL")
        }
        element("lastName", "builtin:string") {
            db("last_name", "character varying(45) NOT NULL")
        }
        element("addressId", "builtin:int") {
            db("address_id", "smallint NOT NULL")
        }
        element("email", "builtin:string") {
            db("email", "character varying(50)")
        }
        element("storeId", "builtin:int") {
            db("store_id", "smallint NOT NULL")
        }
        element("active", "builtin:string") {
            db("active", "boolean DEFAULT true NOT NULL")
        }
        element("username", "builtin:string") {
            db("username", "character varying(16) NOT NULL")
        }
        element("password", "builtin:string") {
            db("password", "character varying(40)")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
        element("picture", "builtin:string") {
            db("picture", "bytea")
        }
    }
    //COPY public.store (store_id, manager_staff_id, address_id, last_update) FROM '$$PATH$$/3081.dat';
    complexType("Store") {
        element("storeId", "builtin:int") {
            db("store_id", "integer DEFAULT nextval(\"public.store_store_id_seq\"::regclass) NOT NULL")
        }
        element("managerStaffId", "builtin:int") {
            db("manager_staff_id", "smallint NOT NULL")
        }
        element("addressId", "builtin:int") {
            db("address_id", "smallint NOT NULL")
        }
        element("lastUpdate", "builtin:string") {
            db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")
        }
    }
}
