package co.feip.vezdecode.data

import java.io.InputStream

class StreamReader {

    fun readTextFromStream(stream: InputStream): String =
        stream.bufferedReader().readText()

}