package pw.kmp.gamelet.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import pw.kmp.gamelet.matches.Match
import pw.kmp.gamelet.modules.spawn.Spawn
import pw.kmp.gamelet.players.Playerlet

class PlayerSpawnEvent(val player: Playerlet, val match: Match, var spawn: Spawn? = null) : Event() {

    override fun getHandlers() = PlayerSpawnEvent.handlers

    companion object {

        val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlers

    }

}