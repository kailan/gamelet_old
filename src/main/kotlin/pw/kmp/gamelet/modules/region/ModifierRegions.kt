package pw.kmp.gamelet.modules.region

import org.bukkit.util.Vector

class NegativeRegion(val region: Region) : Region {
    override fun contains(point: Vector) = !region.contains(point)
}

class IntersectingRegion(val regions: List<Region>) : Region {
    override fun contains(point: Vector) = regions.all { it.contains(point) }
}

class UnionRegion(val regions: List<Region>) : Region {
    override fun contains(point: Vector) = regions.any { it.contains(point) }
}

class ComplementRegion(val mainRegion: Region, val regions: List<Region>) : Region {

    override fun contains(point: Vector): Boolean {
        if (!mainRegion.contains(point)) return false
        if (regions.any { it.contains(point) }) return false
        return true
    }

}