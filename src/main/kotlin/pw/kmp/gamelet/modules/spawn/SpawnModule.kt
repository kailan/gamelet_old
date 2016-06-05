package pw.kmp.gamelet.modules.spawn

import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerRespawnEvent
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.maps.xml.XMLMap
import pw.kmp.gamelet.matches.Match
import pw.kmp.gamelet.modules.Module
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.modules.ModuleFactory
import pw.kmp.gamelet.modules.region.Region
import pw.kmp.gamelet.modules.region.RegionModule
import pw.kmp.gamelet.modules.team.TeamModule
import java.util.*

class SpawnModule : Module() {

    val spawns = HashSet<Spawn>()

    @EventHandler
    fun spawn(event: PlayerRespawnEvent) {
    }

}

class SpawnModuleFactory : ModuleFactory<SpawnModule> {

    override fun createModule(map: Maplet, ctx: ModuleContext, match: Match): SpawnModule? {
        ctx.depend(RegionModule::class, TeamModule::class)

        if (map.flavour == Maplet.Flavour.PGM) {
            val spawns = (map as XMLMap).xml.getChild("spawns") ?: return null
            val module = SpawnModule()
            spawns.children.forEach {
                val team = ctx[TeamModule::class].getTeamByID(it.getAttributeValue("team"))!!
                val regions = HashSet<Region>()
                it.getChild("regions").children?.forEach { r ->
                    regions.add(ctx[RegionModule::class].getRegion(r)!!)
                }
                module.spawns.add(Spawn(team, regions))
            }
            return module
        }
        return null
    }

    override fun getModuleClass() = SpawnModule::class

}