package pw.kmp.gamelet.modules.visual

import org.bukkit.event.EventHandler
import pw.kmp.gamelet.event.MatchStartEvent
import pw.kmp.gamelet.event.TeamJoinEvent
import pw.kmp.gamelet.i18n.Translation
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.matches.Match
import pw.kmp.gamelet.modules.Module
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.modules.ModuleFactory

class MessageModule : Module() {

    @EventHandler
    fun onJoinTeam(event: TeamJoinEvent) {
        event.player.send(Translation of "team.join" with "team" being event.team.getFriendlyName())
    }

    @EventHandler
    fun onMatchStart(event: MatchStartEvent) {
        event.match.broadcast(Translation of "match.start")
    }

}

class MessageModuleFactory : ModuleFactory<MessageModule> {

    override fun createModule(map: Maplet, ctx: ModuleContext, match: Match) = MessageModule()
    override fun getModuleClass() = MessageModule::class

}