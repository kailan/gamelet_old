package pw.kmp.gamelet.modules.team

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import pw.kmp.gamelet.event.TeamJoinEvent
import pw.kmp.gamelet.players.Playerlet
import java.util.*

class Team(val info: Info) {

    val players = ArrayList<Playerlet>()

    fun addPlayer(player: Playerlet) {
        players.add(player)
        Bukkit.getPluginManager().callEvent(TeamJoinEvent(player, this))
    }

    fun removePlayer(player: Playerlet) {
        players.remove(player)
    }

    fun getFriendlyName() = info.color.toString() + info.name

    class Info(val id: String, var name: String, val color: ChatColor, val plural: Boolean, val min: Int, val max: Int)

}
