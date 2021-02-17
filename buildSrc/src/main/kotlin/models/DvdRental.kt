package models

//XXX - I tried implementing from element.type.kClass.annotations but I got an NPE and left unimplemented.
annotation class Db(val dbName: String, val dbType: String)

interface DvdRental {

    //COPY public.actor (actor_id, first_name, last_name, last_update) FROM '$$PATH$$/3057.dat';
    //This causes a stacktrace and what is below does not actually make entries into our complexTypeMap.
    class Actor(
        @Db(dbName = "actor_id", dbType = "integer DEFAULT nextval(\"public.actor_actor_id_seq\"::regclass) NOT NULL")
        val actorId: Int,

        @Db(dbName = "first_name", dbType = "character varying(45) NOT NULL")
        val firstName: String,


        @Db(dbName = "last_name", dbType = "character varying(45) NOT NULL")
        val lastName: String,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String
    )

    //COPY public.address (address_id, address, address2, district, city_id, postal_code, phone, last_update) FROM '$$PATH$$/3065.dat';
    class Address(
        @Db(dbName = "address_id", dbType = "integer DEFAULT nextval(\"public.address_address_id_seq\"::regclass) NOT NULL")
        val addressId: Int,

        @Db(dbName = "address", dbType = "character varying(50) NOT NULL")
        val address: String,

        @Db(dbName = "address2", dbType = "character varying(50)")
        val address2: String?,

        @Db(dbName = "district", dbType = "character varying(20) NOT NULL")
        val district: String?,

        @Db(dbName = "city_id", dbType = "smallint NOT NULL")
        val cityId: Int,

        @Db(dbName = "postal_code", dbType = "character varying(10)")
        val postalCode: String?,

        @Db(dbName = "phone", dbType = "character varying(20) NOT NULL")
        val phone: String?,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String
    )

    //COPY public.category (category_id, name, last_update) FROM '$$PATH$$/3059.dat';
    class Category(
        @Db(dbName = "category_id", dbType = "integer DEFAULT nextval(\"public.category_category_id_seq\"::regclass) NOT NULL")
        val categoryId: Int,

         //TODO - to be consistent this should be category also I think it avoids name intersections in joins.
        @Db(dbName = "name", dbType = "character varying(25) NOT NULL")
        val name: String,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String

    )
    //COPY public.city (city_id, city, country_id, last_update) FROM '$$PATH$$/3067.dat';
    class City(
        @Db(dbName = "city_id", dbType = "integer DEFAULT nextval(\"public.city_city_id_seq\"::regclass) NOT NULL")
        val cityId: Int,

        @Db(dbName = "city", dbType = "character varying(50) NOT NULL")
        val city: String,

        @Db(dbName = "country_id", dbType = "smallint NOT NULL")
        val countryId: Int,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String

    )
    //COPY public.country (country_id, country, last_update) FROM '$$PATH$$/3069.dat';
    class Country(
        @Db(dbName = "country_id", dbType = "integer DEFAULT nextval(\"public.country_country_id_seq\"::regclass) NOT NULL")
        val countryId: Int,

        @Db(dbName = "country", dbType = "character varying(50) NOT NULL")
        val country: String,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String

    )
    //COPY public.customer (customer_id, store_id, first_name, last_name, email, address_id, activebool, create_date, last_update, active) FROM '$$PATH$$/3055.dat';
    class Customer(
        @Db(dbName = "customer_id", dbType = "integer DEFAULT nextval(\"public.customer_customer_id_seq\"::regclass) NOT NULL")
        val customerId: Int,

        @Db(dbName = "store_id", dbType = "smallint NOT NULL")
        val storeId: Int,

        @Db(dbName = "first_name", dbType = "character varying(45) NOT NULL")
        val firstName: String,

        @Db(dbName = "last_name", dbType = "character varying(45) NOT NULL")
        val lastName: String,

        @Db(dbName = "email", dbType = "character varying(50)")
        val email: String,

        @Db(dbName = "address_id", dbType = "smallint NOT NULL")
        val addressId: Int,

        @Db(dbName = "activebool", dbType = "boolean DEFAULT true NOT NULL")
        val activebool: Boolean,

        @Db(dbName = "create_date", dbType = "date DEFAULT (\"now\"::text)::date NOT NULL")
        val createDate: String,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now()")
        val lastUpdate: String,

        @Db(dbName = "active", dbType = "integer")
        val active: Int

    )
    //COPY public.film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) FROM '$$PATH$$/3061.dat';
    class Film(
        @Db(dbName = "film_id", dbType = "integer DEFAULT nextval(\"public.film_film_id_seq\"::regclass) NOT NULL")
        val filmId: Int,

        @Db(dbName = "title", dbType = "character varying(255) NOT NULL")
        val title: String,

        @Db(dbName = "description", dbType = "text")
        val description: String,

        @Db(dbName = "release_year", dbType = "public.year")
        val releaseYear: Int,

        @Db(dbName = "language_id", dbType = "smallint NOT NULL")
        val languageId: Int,

        @Db(dbName = "rental_duration", dbType = "smallint DEFAULT 3 NOT NULL")
        val rentalDuration: Int,

        @Db(dbName = "rental_rate", dbType = "numeric(4,2) DEFAULT 4.99 NOT NULL")
        val rentalRate: Double,

        @Db(dbName = "length", dbType = "smallint")
        val length: Int,

        @Db(dbName = "replacement_cost", dbType = "numeric(5,2) DEFAULT 19.99 NOT NULL")
        val replacementCost: Double,

        @Db(dbName = "rating", dbType = "public.mpaa_rating DEFAULT \"G\"::public.mpaa_rating")
        val rating: String,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String,

        @Db(dbName = "special_features", dbType = "text[]")
        val specialFeatures: String,

        @Db(dbName = "fulltext", dbType = "tsvector NOT NULL")
        val fullText: String

    )
    //COPY public.film_actor (actor_id, film_id, last_update) FROM '$$PATH$$/3062.dat';
    class FilmActor(
        @Db(dbName = "actor_id", dbType = "smallint NOT NULL")
        val actorId: Int,

        @Db(dbName = "film_id", dbType = "smallint NOT NULL")
        val filmId: Int,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String

    )
    //COPY public.film_category (film_id, category_id, last_update) FROM '$$PATH$$/3063.dat';
    class FilmCategory(
        @Db(dbName = "film_id", dbType = "smallint NOT NULL")
        val filmId: Int,

        @Db(dbName = "category_id", dbType = "smallint NOT NULL")
        val categoryId: Int,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String

    )
    //COPY public.inventory (inventory_id, film_id, store_id, last_update) FROM '$$PATH$$/3071.dat';
    class Inventory(
        @Db(dbName = "inventory_id", dbType = "integer DEFAULT nextval(\"public.inventory_inventory_id_seq\"::regclass) NOT NULL")
        val inventoryId: Int,

        @Db(dbName = "film_id", dbType = "smallint NOT NULL")
        val filmId: Int,

        @Db(dbName = "store_id", dbType = "smallint NOT NULL")
        val storeId: Int,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String

    )
    //COPY public.language (language_id, name, last_update) FROM '$$PATH$$/3073.dat';
    class Language(
        @Db(dbName = "language_id", dbType = "integer DEFAULT nextval(\"public.language_language_id_seq\"::regclass) NOT NULL")
        val languageId: Int,

        @Db(dbName = "name", dbType = "character(20) NOT NULL")
        val name: String,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String

    )
    //COPY public.payment (payment_id, customer_id, staff_id, rental_id, amount, payment_date) FROM '$$PATH$$/3075.dat';
    class Payment(
        @Db(dbName = "payment_id", dbType = "integer DEFAULT nextval(\"public.payment_payment_id_seq\"::regclass) NOT NULL")
        val paymentId: Int,

        @Db(dbName = "customer_id", dbType = "smallint NOT NULL")
        val customerId: Int,

        @Db(dbName = "staff_id", dbType = "smallint NOT NULL")
        val staffId: Int,

        @Db(dbName = "rental_id", dbType = "integer NOT NULL")
        val rentalId: Int,

        @Db(dbName = "amount", dbType = "numeric(5,2) NOT NULL")
        val amount: Double,

        @Db(dbName = "payment_date", dbType = "timestamp without time zone NOT NULL")
        val paymentDate: String

    )
    //COPY public.rental (rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, last_update) FROM '$$PATH$$/3077.dat';
    class Rental(
        @Db(dbName = "rental_id", dbType = "integer DEFAULT nextval(\"public.rental_rental_id_seq\"::regclass) NOT NULL")
        val rentalId: Int,

        @Db(dbName = "rental_date", dbType = "timestamp without time zone NOT NULL")
        val rentalDate: String,

        @Db(dbName = "inventory_id", dbType = "integer NOT NULL")
        val inventoryId: Int,

        @Db(dbName = "customer_id", dbType = "smallint NOT NULL")
        val customerId: Int,

        @Db(dbName = "return_date", dbType = "timestamp without time zone")
        val returnDate: String,

        @Db(dbName = "staff_id", dbType = "smallint NOT NULL")
        val staffId: Int,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String

    )
    //COPY public.staff (staff_id, first_name, last_name, address_id, email, store_id, active, username, password, last_update, picture) FROM '$$PATH$$/3079.dat';
    class Staff(
        @Db(dbName = "staff_id", dbType = "integer DEFAULT nextval(\"public.staff_staff_id_seq\"::regclass) NOT NULL")
        val staffId: Int,

        @Db(dbName = "first_name", dbType = "character varying(45) NOT NULL")
        val firstName: String,

        @Db(dbName = "last_name", dbType = "character varying(45) NOT NULL")
        val lastName: String,

        @Db(dbName = "address_id", dbType = "smallint NOT NULL")
        val addressId: Int,

        @Db(dbName = "email", dbType = "character varying(50)")
        val email: String,

        @Db(dbName = "store_id", dbType = "smallint NOT NULL")
        val storeId: Int,

        @Db(dbName = "active", dbType = "boolean DEFAULT true NOT NULL")
        val active: String,

        @Db(dbName = "username", dbType = "character varying(16) NOT NULL")
        val username: String,

        @Db(dbName = "password", dbType = "character varying(40)")
        val password: String,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String,

        @Db(dbName = "picture", dbType = "bytea")
        val picture: String

    )
    //COPY public.store (store_id, manager_staff_id, address_id, last_update) FROM '$$PATH$$/3081.dat';
    class Store(
        @Db(dbName = "store_id", dbType = "integer DEFAULT nextval(\"public.store_store_id_seq\"::regclass) NOT NULL")
        val storeId: Int,

        @Db(dbName = "manager_staff_id", dbType = "smallint NOT NULL")
        val managerStaffId: Int,

        @Db(dbName = "address_id", dbType = "smallint NOT NULL")
        val addressId: Int,

        @Db(dbName = "last_update", dbType = "timestamp without time zone DEFAULT now() NOT NULL")
        val lastUpdate: String

    )
}
