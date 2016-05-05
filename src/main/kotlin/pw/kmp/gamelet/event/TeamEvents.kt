package pw.kmp.gamelet.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import pw.kmp.gamelet.players.Playerlet
import pw.kmp.gamelet.modules.team.Team

class TeamJoinEvent(val player: Playerlet, val team: Team) : Event() {

    override fun getHandlers() = TeamJoinEvent.handlers

    companion object {

        val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlers

    }

}