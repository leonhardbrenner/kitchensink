# kitchensink
This Kotlin WebStack template is not very exotic but it has the following:
    hikari and exposed setup
    dependency injection via guice. To understand this concept search for the word @Inject in my code
    multiplatform - web only but I will add mobile and native once I come up with a clean way to represent a few simple problems
    material-ui
    I kept the original task priority demo from the kotlin hands-on pages which is what this page is forked from
    on my part I added some code generators they are primitive but I used these patterns before and I believe it leads to clean code.
    
The purpose of this project to give something back to the community that feeds me. I am working with serveral orginizations that would be served if they had the ability to create there own routines. Thanks to Kotlin, Square and the other contributers I will try to credit. I want to focus on solving a limited set of problems and include just enough technology to save state and create displays. I will initially be using these for a farm we are building as community on land donated by the town of Biddeford ME.

My hope is that these tools will be useful to mirco-farmers, new homesteaders, cummunity supported agriculture, ... and they eventually build their own tools. However, if building these tools is a chore upon itself then it was not worth it to them... plenty to farm no time for games. For this reason I am trying to keep the stack small and only solve 2 problems till there solution feels intuitive. If the FE is looks like like a telecom expert system I am good with that if it is needed to keep the representation of the solution simpler. That said this project will still need some complicated things built to make this posible so for now the primary goal is to make the tools work.

I can be reached at x@buckysoap.com if you wish to join my slack channel prefix subject with 'Slack request:' but don't be offended if I go dark from time to time. I am now going to use my inside voice for a while and move to my private branch till I delete this sentence. I will leave you with this tour.

First off is just a prototype. I have some lofty goals. Some are too lofty to say seriously in public so I will also be working in a private branch from time to time so if you don't see any activity I went inside. It usually means I will realease one of my clever ideas that I was to shy to try in my work life. If you have something you want to contribute to the soup just fork and I can list your downstream project.

I am using it in this downstream project:

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

I am almost certain this will not work the first time for everyone but if everything does works you should see four tabs:

first - has end to end access to the Postgres DvdRental sample database* which I load from csv-files and insert using Exposed.
second - provides access to some mock data about seeds which was spidered from a local seed company using someone elses spider.
third - is the task manager form the kotlin hands-on
fourth - is the example from the muirwik**

* https://www.postgresqltutorial.com/postgresql-sample-database

** https://github.com/cfnz/muirwik

Feel free to fork for any use. It's just bytes.
