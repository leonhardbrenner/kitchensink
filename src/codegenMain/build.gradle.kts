plugins{
    `kotlin-dsl`
    `application`
}

repositories {
    jcenter()
}

application {
    mainClass.set("generators.MainKt")
}

// optional:  add one string per argument you want as the default JVM args
//applicationDefaultJvmArgs = ["-Xms512m", "-Xmx1g"]