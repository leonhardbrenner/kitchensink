import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.IOException

val kMapper = ObjectMapper().registerModule(KotlinModule())

data class Actor(
    val name: String,
    val bornYear: Int,
    @JsonDeserialize(keyUsing = MoviesMapKeyDeserializer::class)
    @JsonSerialize(keyUsing = MoviesMapKeySerializer::class)
    val movies: Map<Movie, Rating>
) {
    companion object {
        fun deserialize(str: String) = kMapper.readValue<Actor>(str)
        fun serialize(actor: Actor) = kMapper.writeValueAsString(actor)
    }

    class MoviesMapKeySerializer: JsonSerializer<Movie>() {
        @Throws(IOException::class, JsonProcessingException::class)
        override fun serialize(value: Movie?, gen: JsonGenerator?, serializers: SerializerProvider?) {
            gen?.let { jGen ->
                value?.let { movie ->
                    jGen.writeFieldName(kMapper.writeValueAsString(movie))
                } ?: jGen.writeNull()
            }
        }
    }

    class MoviesMapKeyDeserializer: KeyDeserializer() {
        @Throws(IOException::class, JsonProcessingException::class)
        override fun deserializeKey(key: String?, ctxt: DeserializationContext?): Movie? {
            return key?.let { kMapper.readValue<Movie>(key) }
        }
    }

}

data class Movie(
    val title: String,
    val releasingYear: Int,
    val genre: String
)

data class Rating(
    val stars: Double,
    val votes: Long
)

fun main(args: Array<String>) {
    val robertDowneyJrActor = Actor(
        "Rober Downey Jr",
        1965,
        mapOf(
            Movie("Iron Man", 2008, "Action") to Rating(4.9, 2000L),
            Movie("The Avengers", 2012, "Action") to Rating(4.5, 1800L),
            Movie("Avengers: Endgame", 2019, "Action") to Rating(4.3, 1300L)
        )
    )
    println(Actor.deserialize(Actor.serialize(robertDowneyJrActor)))
}
