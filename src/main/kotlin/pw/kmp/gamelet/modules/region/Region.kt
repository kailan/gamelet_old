package pw.kmp.gamelet.modules.region

import org.bukkit.util.Vector

interface Region {

    fun contains(point: Vector): Boolean

    interface Spawnable {
        fun getSpawnLocation(): Vector
    }

}