
# kitchensink
I hate boiler plate! This is how I describe my resources.

https://github.com/leonhardbrenner/kitchensink/blob/main/buildSrc/src/main/kotlin/models/DvdRental.kt

which results in:

https://github.com/leonhardbrenner/kitchensink/blob/main/src/commonMain/kotlin/generated/model/DvdRentalDto.kt

a Dto with a factory method that take any implementation of the interface. Notice that all of the values of my resources are read only. Resource objects should be stateless. To ship them across the wire you a Dto. 

https://github.com/leonhardbrenner/kitchensink/blob/main/src/commonMain/kotlin/generated/model/DvdRentalDto.kt

Unfortunately, the Dto was sullied by @Serializable and doesn't work with the CsvLoader you want. No worries just make another implementation of the interface.

https://github.com/leonhardbrenner/kitchensink/blob/main/src/jvmMain/kotlin/generated/model/DvdRentalCsvLoader.kt

Also it would be nice to be integrated with the db using exposed. Like this:

https://github.com/leonhardbrenner/kitchensink/blob/main/src/jvmMain/kotlin/generated/model/db/DvdRentalDb.kt

I'm thinking this can be expanded to allow a block instead of fetchAll.

https://github.com/leonhardbrenner/kitchensink/blob/main/src/jvmMain/kotlin/applications/DvdRentalApplication.kt

I'm adding builders and inheritence at the interface level but all projections are homomorphic. I will then start work on the atomizer which will open worm holes.

Don't worry it's not complicated. I took advantage of the buildSrc directory and made a task<generate> which I can define however I'd like. This is the task:

https://github.com/leonhardbrenner/kitchensink/blob/main/buildSrc/src/main/kotlin/ModelGenerator.kt

If you come across this and are interested in what is coming my email is buckysoap@gmail.com actually right now it is lenbrenner@gmail.com just use kitchensink: as your subject prefix.

The original description of the interface is comes from this DSL:

https://github.com/leonhardbrenner/kitchensink/blob/main/buildSrc/src/main/kotlin/schema/Manifest.kt

And our simple interface has a simple generator:

https://github.com/leonhardbrenner/kitchensink/blob/main/buildSrc/src/main/kotlin/generators/InterfaceGenerator.kt

the rest of the generators are in the same directory. This is actually a full stack demo with a bunch of neat tricks hence kitchen sink which I will explain in a series of Medium articles. This is part of a Stone soup project to bring goodness to everyone.

Basic setup on Ubuntu:

    Install Postgres 10+:

    From terminal:
        user> sudo su - postgres
        postgres> psql
        psql> postgres=# CREATE DATABASE test;
        psql> CREATE DATABASE
        psql> postgres=# CREATE USER test PASSWORD 'test';
        psql> CREATE ROLE
        psql> postgres=# GRANT ALL ON DATABASE test TO test;
        psql> GRANT
        psql> postgres=# GRANT ALL ON ALL TABLES IN SCHEMA test TO test;
        psql> GRANT
        
    In IntelliJ set:
        File -> Open [kitchensink]
        NOTE: This next step will fail the following step will fix it and you can rerun
        Gradle -> (kitchensink|garden-planner) -> Tasks -> Application -> run
        kitchensink[run] -> Edit Configuration -> DB_URL=jdbc:postgresql://localhost/test;DB_USER=test;DB_PASSWORD=test
        Hit the play button
