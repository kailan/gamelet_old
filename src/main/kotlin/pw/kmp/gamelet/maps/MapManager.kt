package pw.kmp.gamelet.maps

import com.google.common.base.Preconditions
import pw.kmp.gamelet.Gamelet
import pw.kmp.gamelet.i18n.Translation
import java.io.File
import java.util.*

object MapManager {

    val maps = ArrayList<Maplet>()
    val ignoredDirectories = arrayListOf(".git")

    fun loadMaps(dir: File) {
        Gamelet.info(Translation of "maps.loading" with "directory" being dir.absolutePath)
        Preconditions.checkArgument(dir.isDirectory, "Invalid map directory")

        dir.listFiles().filter { it.isDirectory && !ignoredDirectories.contains(it.name) }.forEach { loadMap(it) }
    }


    fun loadMap(dir: File) {
        try {
            val map = Maplet.load(dir)
            maps.add(map)
            Gamelet.info(Translation of "maps.loaded" with "flavour" being map.flavour.friendlyName and "name" being map.info.name)
        } catch (err: Exception) {
            Gamelet.info(Translation of "maps.error" with "name" being dir.name and "error" being err.message!!)
        }
    }

}