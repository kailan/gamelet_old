package pw.kmp.gamelet.util

import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*

class VoidGenerator : ChunkGenerator() {

    override fun generate(world: World?, random: Random?, x: Int, z: Int): ByteArray? {
        return ByteArray(0)
    }

    override fun generateExtBlockSections(world: World?, random: Random?, x: Int, z: Int, biomes: BiomeGrid?): Array<out ShortArray>? {
        return emptyArray()
    }

    override fun generateBlockSections(world: World?, random: Random?, x: Int, z: Int, biomes: BiomeGrid?): Array<out ByteArray>? {
        return emptyArray()
    }

}