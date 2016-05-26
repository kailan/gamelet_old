package pw.kmp.gamelet.maps

import co.enviark.speak.Translation
import com.google.common.base.Preconditions
import pw.kmp.gamelet.Gamelet
import java.io.File
import java.util.*

object MapManager {

    val maps = ArrayList<Maplet>()
    val ignoredDirectories = arrayListOf(".git")

    fun loadMaps(dir: File) {
        Gamelet.info(Translation("maps.loading").put("directory", dir.absolutePath))
        Preconditions.checkArgument(dir.isDirectory, "Invalid map directory")

        val time = System.currentTimeMillis()
        dir.listFiles().filter { it.isDirectory && !ignoredDirectories.contains(it.name) }.forEach { loadMap(it) }
        Gamelet.info(Translation("maps.time").put("count", maps.size).put("time", System.currentTimeMillis()-time))
    }


    fun loadMap(dir: File) {
        try {
            val map = Maplet.load(dir)
            maps.add(map)
            Gamelet.info(Translation("maps.loaded").put("flavour", map.flavour.friendlyName).put("name", map.info.name))
        } catch (err: Exception) {
            Gamelet.info(Translation("maps.error").put("name", dir.name).put("error", err.message!!))
        }
    }

}