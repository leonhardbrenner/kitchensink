import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.google.common.io.CharSource
import com.vhl.blackmo.grass.dsl.grass
import generated.model.DvdRental
import generated.model.DvdRentalDto
import org.postgresql.util.ReaderInputStream
import java.io.File
import java.io.SequenceInputStream

@ExperimentalStdlibApi
fun main() {
    val header = ReaderInputStream(CharSource.wrap("actor_id\tlast_name\tfirst_name\tlast_update\n").openStream());
    val file = File("/home/lbrenner/projects/kitchensink/dvdrental/3057.dat").inputStream()
    val csvContents = csvReader {
        delimiter = '\t'
        skipMissMatchedRow = true //Postgres ends it's csv files with \.
    }.readAllWithHeader(SequenceInputStream(header, file))
    val dataClasses = grass<DvdRentalDto.actor>().harvest(csvContents)
    dataClasses.forEach {
        println(it)
    }
}