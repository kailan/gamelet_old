package pw.kmp.gamelet.modules.world

import org.bukkit.World
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.maps.xml.XMLMap
import pw.kmp.gamelet.matches.Match
import pw.kmp.gamelet.modules.Module
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.modules.ModuleFactory

class GameruleModule(val world: World, val rules: Map<String, String>) : Module() {
    init {
        rules.entries.forEach { world.setGameRuleValue(it.key, it.value) }
    }
}

class GameruleModuleFactory : ModuleFactory<GameruleModule> {

    override fun createModule(map: Maplet, ctx: ModuleContext, match: Match): GameruleModule? {
        if (map.flavour == Maplet.Flavour.PGM) {
            val xml = (map as XMLMap).xml
            val module = xml.getChild("gamerules") ?: return null

            val rules = mutableMapOf<String, String>()
            module.children?.forEach { rules.put(it.name, it.textNormalize) }

            return GameruleModule(match.world, rules)
        }

        return null
    }

    override fun getModuleClass() = GameruleModule::class

}