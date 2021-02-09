package model

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.google.common.io.CharSource
import com.vhl.blackmo.grass.dsl.grass
import org.postgresql.util.ReaderInputStream
import java.io.File
import java.io.SequenceInputStream

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> loadCsv(file: File, header: String): List<T> {
    val header = ReaderInputStream(CharSource.wrap("$header\n").openStream());
    val inputStream = file.inputStream()
    val csvContents = csvReader {
        delimiter = '\t'
        skipMissMatchedRow = true //Postgres ends it's csv files with \.
    }.readAllWithHeader(SequenceInputStream(header, inputStream))
    return grass<T> {
    }.harvest(csvContents)
}
