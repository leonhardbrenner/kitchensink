package models

import schema.ManifestNew

interface DvdRental {
    //COPY public.actor (actor_id, first_name, last_name, last_update) FROM '$$PATH$$/3057.dat';
    //This causes a stacktrace and what is below does not actually make entries into our complexTypeMap.
    class Actor(
        val actorId: Int,
        //db("actor_id", "integer DEFAULT nextval(\"public.actor_actor_id_seq\"::regclass) NOT NULL")
        val firstName: String,
        //db("first_name", "character varying(45) NOT NULL")
        val lastName: String,
        //db("last_name", "character varying(45) NOT NULL")
        val lastUpdate: String
    )
    //COPY public.address (address_id, address, address2, district, city_id, postal_code, phone, last_update) FROM '$$PATH$$/3065.dat';
    class Address(
        val addressId: Int,
        //db("address_id", "integer DEFAULT nextval(\"public.address_address_id_seq\"::regclass) NOT NULL")

        val address: String,
        //db("address", "character varying(50) NOT NULL")

        val address2: String?,
        //db("address2", "character varying(50)")

        val district: String?,
        //db("district", "character varying(20) NOT NULL")

        val cityId: Int,
        //db("city_id", "smallint NOT NULL")

        val postalCode: String?,
        //db("postal_code", "character varying(10)")

        val phone: String?,
        //db("phone", "character varying(20) NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
    //COPY public.category (category_id, name, last_update) FROM '$$PATH$$/3059.dat';
    class Category(
        val categoryId: Int,
        //db("category_id", "integer DEFAULT nextval(\"public.category_category_id_seq\"::regclass) NOT NULL")

         //TODO - to be consistent this should be category also I think it avoids name intersections in joins.
        val name: String,
        //db("name", "character varying(25) NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
    //COPY public.city (city_id, city, country_id, last_update) FROM '$$PATH$$/3067.dat';
    class City(
        val cityId: Int,
        //db("city_id", "integer DEFAULT nextval(\"public.city_city_id_seq\"::regclass) NOT NULL")

        val city: String,
        //db("city", "character varying(50) NOT NULL")

        val countryId: Int,
        //db("country_id", "smallint NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
    //COPY public.country (country_id, country, last_update) FROM '$$PATH$$/3069.dat';
    class Country(
        val countryId: Int,
        //db("country_id", "integer DEFAULT nextval(\"public.country_country_id_seq\"::regclass) NOT NULL")

        val country: String,
        //db("country", "character varying(50) NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
    //COPY public.customer (customer_id, store_id, first_name, last_name, email, address_id, activebool, create_date, last_update, active) FROM '$$PATH$$/3055.dat';
    class Customer(
        val customerId: Int,
        //db("customer_id", "integer DEFAULT nextval(\"public.customer_customer_id_seq\"::regclass) NOT NULL")

        val storeId: Int,
        //db("store_id", "smallint NOT NULL")

        val firstName: String,
        //db("first_name", "character varying(45) NOT NULL")

        val lastName: String,
        //db("last_name", "character varying(45) NOT NULL")

        val email: String,
        //db("email", "character varying(50)")

        val addressId: Int,
        //db("address_id", "smallint NOT NULL")

        val activebool: Boolean,
        //db("activebool", "boolean DEFAULT true NOT NULL")

        val createDate: String,
        //db("create_date", "date DEFAULT (\"now\"::text)::date NOT NULL")

        val lastUpdate: String,
        //db("last_update", "timestamp without time zone DEFAULT now()")

        val active: Int
        //db("active", "integer")

    )
    //COPY public.film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) FROM '$$PATH$$/3061.dat';
    class Film(
        val filmId: Int,
        //db("film_id", "integer DEFAULT nextval(\"public.film_film_id_seq\"::regclass) NOT NULL")

        val title: String,
        //db("title", "character varying(255) NOT NULL")

        val description: String,
        //db("description", "text")

        val releaseYear: Int,
        //db("release_year", "public.year")

        val languageId: Int,
        //db("language_id", "smallint NOT NULL")

        val rentalDuration: Int,
        //db("rental_duration", "smallint DEFAULT 3 NOT NULL")

        val rentalRate: Double,
        //db("rental_rate", "numeric(4,2) DEFAULT 4.99 NOT NULL")

        val length: Int,
        //db("length", "smallint")

        val replacementCost: Double,
        //db("replacement_cost", "numeric(5,2) DEFAULT 19.99 NOT NULL")

        val rating: String,
        //db("rating", "public.mpaa_rating DEFAULT \"G\"::public.mpaa_rating")

        val lastUpdate: String,
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

        val specialFeatures: String,
        //db("special_features", "text[]")

        val fullText: String
        //db("fulltext", "tsvector NOT NULL")

    )
    //COPY public.film_actor (actor_id, film_id, last_update) FROM '$$PATH$$/3062.dat';
    class FilmActor(
        val actorId: Int,
        //db("actor_id", "smallint NOT NULL")

        val filmId: Int,
        //db("film_id", "smallint NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
    //COPY public.film_category (film_id, category_id, last_update) FROM '$$PATH$$/3063.dat';
    class FilmCategory(
        val filmId: Int,
        //db("film_id", "smallint NOT NULL")

        val categoryId: Int,
        //db("category_id", "smallint NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
    //COPY public.inventory (inventory_id, film_id, store_id, last_update) FROM '$$PATH$$/3071.dat';
    class Inventory(
        val inventoryId: Int,
        //db("inventory_id", "integer DEFAULT nextval(\"public.inventory_inventory_id_seq\"::regclass) NOT NULL")

        val filmId: Int,
        //db("film_id", "smallint NOT NULL")

        val storeId: Int,
        //db("store_id", "smallint NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
    //COPY public.language (language_id, name, last_update) FROM '$$PATH$$/3073.dat';
    class Language(
        val languageId: Int,
        //db("language_id", "integer DEFAULT nextval(\"public.language_language_id_seq\"::regclass) NOT NULL")

        val name: String,
        //db("name", "character(20) NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
    //COPY public.payment (payment_id, customer_id, staff_id, rental_id, amount, payment_date) FROM '$$PATH$$/3075.dat';
    class Payment(
        val paymentId: Int,
        //db("payment_id", "integer DEFAULT nextval(\"public.payment_payment_id_seq\"::regclass) NOT NULL")

        val customerId: Int,
        //db("customer_id", "smallint NOT NULL")

        val staffId: Int,
        //db("staff_id", "smallint NOT NULL")

        val rentalId: Int,
        //db("rental_id", "integer NOT NULL")

        val amount: Double,
        //db("amount", "numeric(5,2) NOT NULL")

        val paymentDate: String
        //db("payment_date", "timestamp without time zone NOT NULL")

    )
    //COPY public.rental (rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, last_update) FROM '$$PATH$$/3077.dat';
    class Rental(
        val rentalId: Int,
        //db("rental_id", "integer DEFAULT nextval(\"public.rental_rental_id_seq\"::regclass) NOT NULL")

        val rentalDate: String,
        //db("rental_date", "timestamp without time zone NOT NULL")

        val inventoryId: Int,
        //db("inventory_id", "integer NOT NULL")

        val customerId: Int,
        //db("customer_id", "smallint NOT NULL")

        val returnDate: String,
        //db("return_date", "timestamp without time zone")

        val staffId: Int,
        //db("staff_id", "smallint NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
    //COPY public.staff (staff_id, first_name, last_name, address_id, email, store_id, active, username, password, last_update, picture) FROM '$$PATH$$/3079.dat';
    class Staff(
        val staffId: Int,
        //db("staff_id", "integer DEFAULT nextval(\"public.staff_staff_id_seq\"::regclass) NOT NULL")

        val firstName: String,
        //db("first_name", "character varying(45) NOT NULL")

        val lastName: String,
        //db("last_name", "character varying(45) NOT NULL")

        val addressId: Int,
        //db("address_id", "smallint NOT NULL")

        val email: String,
        //db("email", "character varying(50)")

        val storeId: Int,
        //db("store_id", "smallint NOT NULL")

        val active: String,
        //db("active", "boolean DEFAULT true NOT NULL")

        val username: String,
        //db("username", "character varying(16) NOT NULL")

        val password: String,
        //db("password", "character varying(40)")

        val lastUpdate: String,
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

        val picture: String
        //db("picture", "bytea")

    )
    //COPY public.store (store_id, manager_staff_id, address_id, last_update) FROM '$$PATH$$/3081.dat';
    class Store(
        val storeId: Int,
        //db("store_id", "integer DEFAULT nextval(\"public.store_store_id_seq\"::regclass) NOT NULL")

        val managerStaffId: Int,
        //db("manager_staff_id", "smallint NOT NULL")

        val addressId: Int,
        //db("address_id", "smallint NOT NULL")

        val lastUpdate: String
        //db("last_update", "timestamp without time zone DEFAULT now() NOT NULL")

    )
}

val dvdRentalsNew = ManifestNew.Namespace(DvdRental::class)
