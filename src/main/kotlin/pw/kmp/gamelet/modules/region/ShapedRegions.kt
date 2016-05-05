package pw.kmp.gamelet.modules.region

import org.bukkit.util.Vector

class BlockRegion(val point: Vector) : Region, Region.Spawnable {

    override fun contains(point: Vector) = point.equals(this.point)
    override fun getSpawnLocation() = point.clone()

}

// TODO: spawnable
class RectangleRegion(val min: Vector, val max: Vector) : Region {
    override fun contains(point: Vector) = point.x.between(min.x, max.x) && point.z.between(min.z, max.z)
}

// TODO: spawnable
class CuboidRegion(val min: Vector, val max: Vector) : Region {
    override fun contains(point: Vector) = point.isInAABB(min, max)
}

fun Double.between(a: Double, b: Double) = this >= a && this <= b


