package pw.kmp.gamelet.rotation

import com.google.common.base.Preconditions
import pw.kmp.gamelet.maps.Maplet

object Rotation {

    lateinit var provider: RotationProvider

    fun initialize(provider: RotationProvider) {
        this.provider = provider
        Preconditions.checkState(provider.getMaps().size > 0, "No maps in rotation")
    }

    fun getMaps() = provider.getMaps()
    fun next() = provider.next()

}

interface RotationProvider {

    fun getMaps(): List<Maplet>
    fun next(): Maplet

}