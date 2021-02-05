import org.junit.Test

class TestManifest {

    val dvdRentalsNew = Manifest.namespace("DvdRental") {
        //COPY public.actor (actor_id, first_name, last_name, last_update) FROM '$$PATH$$/3057.dat';
        //This causes a stacktrace and what is below does not actually make entries into our complexTypeMap.
        element("actor") {
            element("actor_id", "builtin:int") // integer DEFAULT nextval("public.actor_actor_id_seq"::regclass) NOT NULL,
            element("first_name", "builtin:string") // character varying(45) NOT NULL,
            element("last_name", "builtin:string") // character varying(45) NOT NULL,
            element("last_update", "builtin:string") // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.address (address_id, address, address2, district, city_id, postal_code, phone, last_update) FROM '$$PATH$$/3065.dat';
        element("address") {
            element(
                "address_id",
                "builtin:int"
            ) // integer DEFAULT nextval("public.address_address_id_seq"::regclass) NOT NULL,
            element("address", "builtin:string") // character varying(50) NOT NULL,
            element("address2", "builtin:nullableString") // character varying(50),
            element("district", "builtin:nullableString") // character varying(20) NOT NULL,
            element("city_id", "builtin:int") // smallint NOT NULL,
            element("postal_code", "builtin:nullableString") // character varying(10),
            element("phone", "builtin:nullableString") // character varying(20) NOT NULL,
            element("last_update", "builtin:string") // timestamp without time zone DEFAULT now() NOT NULL
        }
        //COPY public.film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) FROM '$$PATH$$/3061.dat';
        element("film") {
            element(
                "film_id",
                "builtin:int"
            ) // integer DEFAULT nextval("public.film_film_id_seq"::regclass) NOT NULL,
            element("title", "builtin:string") // character varying(255) NOT NULL,
            element("description", "builtin:string") // text,
            element("release_year", "builtin:int") // public.year,
            element("language_id", "builtin:int") // smallint NOT NULL,
            element("rental_duration", "builtin:int") // smallint DEFAULT 3 NOT NULL,
            element("rental_rate", "builtin:double") // numeric(4,2) DEFAULT 4.99 NOT NULL,
            element("length", "builtin:int") // smallint,
            element("replacement_cost", "builtin:double") // numeric(5,2) DEFAULT 19.99 NOT NULL,
            element("rating", "builtin:string") // public.mpaa_rating DEFAULT "G"::public.mpaa_rating,
            element("last_update", "builtin:string") // timestamp without time zone DEFAULT now() NOT NULL,
            element("special_features", "builtin:string") // text[],
            element("fulltext", "builtin:string") // tsvector NOT NULL
        }


    }
    val buckysoap = Manifest.namespace("BuckySoap") {
        element("ElementAndComplexType") {
            element("boolean", "builtin:boolean")
            element("int", "builtin:int")
            element("long", "builtin:long")
            element("string", "builtin:string")
        }
        complexType("ComplexType") {
            attribute("booleanAttribute", "builtin:boolean")
            element("booleanElement", "builtin:boolean")
            element("int", "builtin:int")
            element("long", "builtin:long")
            element("string", "builtin:string")
        }
        element("element", "BuckySoap:ComplexType")
    }

    @Test
    fun test() {
        listOf(dvdRentalsNew, buckysoap).forEach { namespace ->
            println(namespace.name)
            namespace.elements.forEach { element ->
                println(element.name)
            }
            namespace.simpleTypes.forEach { type ->
                println("${type.name} ${type.typeName}")
            }
            namespace.complexTypes.forEach { type ->
                println("Complex Type:" + type.name)
                type.elements.forEach { element ->
                    println("${element.name} ${element.type.name} ${element.asPropertySpec(false)}")
                }
            }
        }
    }

}