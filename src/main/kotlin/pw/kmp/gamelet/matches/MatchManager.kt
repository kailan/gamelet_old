package pw.kmp.gamelet.matches

import co.enviark.speak.Translation
import org.apache.commons.io.FileUtils
import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import pw.kmp.gamelet.Gamelet
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.util.VoidGenerator
import java.io.File

object MatchManager {

    // current match
    var match: Match? = null
    var matchCount = 0

    fun createMatch(map: Maplet) : Match {
        val worldName = "match-" + ++matchCount
        val worldFolder = File(Bukkit.getWorldContainer(), worldName)
        if (worldFolder.exists()) FileUtils.deleteDirectory(worldFolder)
        FileUtils.copyDirectory(map.directory, worldFolder)

        val wc = WorldCreator(worldName).generator(VoidGenerator()).generateStructures(false)
        val world = Bukkit.createWorld(wc)
        world.isAutoSave = false

        val match = Match(matchCount, map, world)
        Gamelet.info(Translation("match.loaded").put("map", map.info).put("world", worldName))
        return match
    }

    fun unloadMatch(match: Match) {
        match.cleanup()

        val world = match.world
        Bukkit.unloadWorld(world, false)
        FileUtils.deleteDirectory(world.worldFolder)

        Gamelet.info(Translation("match.unloaded").put("world", world.name))
    }

}