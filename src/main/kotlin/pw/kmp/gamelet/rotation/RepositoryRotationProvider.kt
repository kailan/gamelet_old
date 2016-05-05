package pw.kmp.gamelet.rotation

import pw.kmp.gamelet.maps.MapManager
import pw.kmp.gamelet.maps.Maplet

class RepositoryRotationProvider : RotationProvider {

    var index = 0

    override fun getMaps() = MapManager.maps

    override fun next(): Maplet {
        if (index >= getMaps().size) index = 0
        return getMaps()[index]
    }


}