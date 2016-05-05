package pw.kmp.gamelet.maps.xml

import org.jdom2.Element
import pw.kmp.gamelet.maps.MapInfo
import pw.kmp.gamelet.maps.Maplet
import java.io.File

class XMLMap(val xml: Element, directory: File) : Maplet(directory) {

    init {
        if (xml.getAttribute("proto") != null) {
            flavour = Flavour.PGM
        } else throw IllegalArgumentException("Could not detect XML map flavour")

        if (flavour == Flavour.PGM) {
            val name = xml.getChildText("name")!!
            val version = xml.getChildText("version")!!
            val objective = xml.getChildText("objective")!!
            val authors = xml.getChild("authors")!!.children.map { createPGMContributor(it) }
            val contributors = xml.getChild("contributors")?.children?.map { createPGMContributor(it) }
            info = MapInfo(name, version, objective, authors, contributors)
        }
    }

    fun createPGMContributor(el: Element): MapInfo.Contributor {
        return MapInfo.Contributor(el.getAttributeValue("uuid") ?: el.text, el.getAttributeValue("contribution"))
    }

}