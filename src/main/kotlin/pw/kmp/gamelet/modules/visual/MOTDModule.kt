package pw.kmp.gamelet.modules.visual

import org.bukkit.event.EventHandler
import org.bukkit.event.server.ServerListPingEvent
import pw.kmp.gamelet.i18n.Translation
import pw.kmp.gamelet.maps.MapInfo
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.matches.Match
import pw.kmp.gamelet.modules.Module
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.modules.ModuleFactory

class MOTDModule(val match: Match, val info: MapInfo) : Module() {

    @EventHandler
    fun onPing(event: ServerListPingEvent) {
        event.motd = (Translation of "match.motd" with "map" being info.name and "objective" being info.description).get()
    }

}

class MOTDModuleFactory : ModuleFactory<MOTDModule> {

    override fun createModule(map: Maplet, ctx: ModuleContext, match: Match) = MOTDModule(match, map.info)
    override fun getModuleClass() = MOTDModule::class

}