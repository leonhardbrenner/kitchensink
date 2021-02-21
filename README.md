# kitchensink

This Kotlin WebStack template is not very exotic but I will add mobile and native shortly. I am using it in this downstream project:

https://github.com/leonhardbrenner/stonesoup/blob/main/README.md

It differentiates from StoneSoup by trying to few uses of each technologie. Kind of like an instruction manual to people trying to build things in StoneSoup. This will gradually document each technology in stonesoup which will be limited so the documentation can remain small.

For right now this is just a backing project to a Medium article:

https://buckysoap.medium.com/the-many-faces-of-interfaces-part-1-52b0d116cedd

Here is a little roadmap around the code that expands on what was mentioned in the article but everything.

This is how I describe my resource objects.

https://github.com/leonhardbrenner/kitchensink/blob/main/buildSrc/src/main/kotlin/models/DvdRental.kt

this code generator writes:

https://github.com/leonhardbrenner/kitchensink/blob/main/src/commonMain/kotlin/generated/model/DvdRental.kt

And this a Dto with a factory method that take any implementation of the interface. Notice that all of the values of my resources are read only. Resource objects should be stateless. You can ship them across the wire with a Dto. 

https://github.com/leonhardbrenner/kitchensink/blob/main/src/commonMain/kotlin/generated/model/DvdRentalDto.kt

Unfortunately, the Dto was sullied by @Serializable and doesn't work with the CsvLoader you want. No worries just make another implementation of the interface.

https://github.com/leonhardbrenner/kitchensink/blob/main/src/jvmMain/kotlin/generated/model/DvdRentalCsvLoader.kt

Also, it would be nice to be integrated with the db using exposed. Like this:

https://github.com/leonhardbrenner/kitchensink/blob/main/src/jvmMain/kotlin/generated/model/db/DvdRentalDb.kt

There are more parts that can be generated:

https://github.com/leonhardbrenner/kitchensink/blob/main/src/jvmMain/kotlin/applications/DvdRentalApplication.kt

I'm just started adding builders but they don't handle fancy and inheritence at the interface level but all projections are homomorphic.Here is the begininings:

https://github.com/leonhardbrenner/kitchensink/blob/main/src/commonMain/kotlin/generated/model/DvdRental.kt

Don't worry it's not complicated. I took advantage of the buildSrc directory and made a task<generate> which I can define however I'd like. This is the task:

https://github.com/leonhardbrenner/kitchensink/blob/main/buildSrc/src/main/kotlin/ModelGenerator.kt

The original description of the interface is written using this DSL:

https://github.com/leonhardbrenner/kitchensink/blob/main/buildSrc/src/main/kotlin/schema/Manifest.kt

And our simple interface has a simple generator:

https://github.com/leonhardbrenner/kitchensink/blob/main/buildSrc/src/main/kotlin/generators/InterfaceGenerator.kt

the rest of the generators are in the same directory. This is actually a full stack demo with a bunch of neat tricks hence kitchen sink. I will explain subsequint Medium articles. This is part of The Stone Soup project to bring goodness to everyone.

Getting started:

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

If everything works as planned you should see four tabs:

The first provides access to the DvdRental database which was copied from. The DB was inserted and queried using Exposed. Second is a seed database which works with JSon. I also have a reference to the original starter app copied from JetBrains hands on demos. Finally, I provide a tab which shows that the MaterialUI integration from here works:

https://www.postgresqltutorial.com/postgresql-sample-database

https://github.com/cfnz/muirwik

If you come across this and are interested in contacting me my email is x@buckysoap.com if you wish to join my slack channel prefix subject with 'Slack request:'.

Feel free to fork.
