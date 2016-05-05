package pw.kmp.gamelet.modules.region.xml

import org.jdom2.Element
import pw.kmp.gamelet.modules.region.*
import pw.kmp.gamelet.util.toVector2D
import pw.kmp.gamelet.util.toVector3D
import java.util.*

class XMLRegionLoader(val mod: RegionModule) : RegionLoader<Element> {

    override fun getRegion(region: Element): Region? {
        val reg = parseTag(region)

        if (!region.name.equals("region") && reg != null) {
            if (region.getAttributeValue("id") != null) {
                mod.regions.put(region.getAttributeValue("id"), reg)
            } else {
                mod.regions.put(UUID.randomUUID().toString(), reg)
            }
        }
        return reg
    }

    fun parseChildren(el: Element): List<Region> {
        return el.children.filter { !it.name.equals("apply") }.map { getRegion(it)!! }
    }

    fun parseTag(el: Element): Region? {
        return when (el.name) {
            "block" -> BlockRegion(el.text.toVector2D())
            "cuboid" -> CuboidRegion(el.getAttributeValue("min").toVector3D(), el.getAttributeValue("max").toVector3D())

            "region" -> mod.regions.get(el.getAttributeValue("id"))
            "empty" -> EmptyRegion()
            "nowhere" -> EmptyRegion()
            "everywhere" -> EverywhereRegion()

            "negative" -> NegativeRegion(UnionRegion(parseChildren(el)))
            "union" -> UnionRegion(parseChildren(el))
            "intersect" -> IntersectingRegion(parseChildren(el))
            "complement" -> {
                val rs = parseChildren(el)
                ComplementRegion(rs[0], rs.subList(1, rs.size - 1))
            }

            else -> throw IllegalArgumentException(el.name + " is not a valid region type")
        }
    }

}