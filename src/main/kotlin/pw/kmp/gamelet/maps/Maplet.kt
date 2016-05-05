package pw.kmp.gamelet.maps

import org.jdom2.input.SAXBuilder
import org.jdom2.located.LocatedJDOMFactory
import pw.kmp.gamelet.maps.xml.XMLMap
import java.io.File

open class Maplet(val directory: File) {

    lateinit var info: MapInfo
    lateinit var flavour: Flavour

    companion object {
        fun load(dir: File): Maplet {
            val xml = File(dir, "map.xml")
            if (xml.isFile) {
                val builder = SAXBuilder()
                builder.jdomFactory = LocatedJDOMFactory()
                val map = builder.build(xml).rootElement
                map.getChildren("include")?.forEach {
                    try {
                        val include = builder.build(File(dir, it.getAttributeValue("src")))
                        map.addContent(include.rootElement.content)
                    } catch (err: Exception) {
                        // doesn't really matter tbh
                    }
                }
                return XMLMap(map, dir)
            }

            throw IllegalArgumentException("Unknown map format")
        }
    }

    enum class Flavour(val friendlyName: String) {
        PGM("PGM")
    }
}