package pw.kmp.gamelet.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import pw.kmp.gamelet.matches.MatchManager
import pw.kmp.gamelet.players.Playerlet
import pw.kmp.gamelet.players.Players

class PlayerListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = Playerlet(event.player)
        Players.players.put(event.player, player)

        if (MatchManager.match != null) {
            player.match = MatchManager.match
        }
    }

}