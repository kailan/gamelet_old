package pw.kmp.gamelet.modules.region

import org.bukkit.util.Vector

class EmptyRegion : Region {
    override fun contains(point: Vector) = false
}

class EverywhereRegion : Region {
    override fun contains(point: Vector) = true
}

