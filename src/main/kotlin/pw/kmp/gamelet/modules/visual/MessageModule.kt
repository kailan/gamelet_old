package pw.kmp.gamelet.modules.visual

import co.enviark.speak.Translation
import org.bukkit.event.EventHandler
import pw.kmp.gamelet.event.MatchStartEvent
import pw.kmp.gamelet.event.TeamJoinEvent
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.matches.Match
import pw.kmp.gamelet.modules.Module
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.modules.ModuleFactory

class MessageModule : Module() {

    @EventHandler
    fun onJoinTeam(event: TeamJoinEvent) {
        event.player.send(Translation("team.join").put("team", event.team.getFriendlyName()))
    }

    @EventHandler
    fun onMatchStart(event: MatchStartEvent) {
        event.match.broadcast(Translation("match.start"))
    }

}

class MessageModuleFactory : ModuleFactory<MessageModule> {

    override fun createModule(map: Maplet, ctx: ModuleContext, match: Match) = MessageModule()
    override fun getModuleClass() = MessageModule::class

}