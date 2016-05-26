package pw.kmp.gamelet.rotation

import co.enviark.speak.Translation
import pw.kmp.gamelet.Gamelet
import pw.kmp.gamelet.maps.MapManager
import pw.kmp.gamelet.maps.Maplet
import java.io.File
import java.io.FileInputStream
import java.util.*
import java.util.logging.Level

class FileRotationProvider(val file: File) : RotationProvider {

    val maps = ArrayList<Maplet>()
    var index = 0

    init {
        val scanner = Scanner(FileInputStream(file))
        while (scanner.hasNextLine()) {
            val name = scanner.nextLine()
            var possibleMaps = MapManager.maps.filter { it.info.name.equals(name, true) }
            if (possibleMaps.size < 1) {
                Gamelet.log(Level.WARNING, Translation("rotation.unknownmap").put("name", name))
            } else {
                this.maps.add(possibleMaps.first())
            }
        }
    }

    override fun getMaps(): List<Maplet> = maps

    override fun next(): Maplet {
        if (index >= getMaps().size) index = 0
        return getMaps()[index]
    }

}