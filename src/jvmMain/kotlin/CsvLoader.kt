import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.google.common.io.CharSource
import com.vhl.blackmo.grass.dsl.grass
import org.postgresql.util.ReaderInputStream
import java.io.File
import java.io.SequenceInputStream

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> loadCsv(pathname: String, header: String): List<T> {
    val header = ReaderInputStream(CharSource.wrap("$header\n").openStream());
    val file = File(pathname).inputStream()
    val csvContents = csvReader {
        delimiter = '\t'
        skipMissMatchedRow = true //Postgres ends it's csv files with \.
    }.readAllWithHeader(SequenceInputStream(header, file))
    return grass<T>().harvest(csvContents)
}

fun main() {
    val actors = DvdRentalCsvLoader.actor.loadCsv("/home/lbrenner/projects/kitchensink/dvdrental/3057.dat")
    val dataClasses = loadCsv<DvdRentalCsvLoader.actor>(
        "/home/lbrenner/projects/kitchensink/dvdrental/3057.dat", DvdRentalCsvLoader.actor.header
    )

    dataClasses.forEach {
        println(it)
    }
}