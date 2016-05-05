package pw.kmp.gamelet.players

import com.google.common.base.Preconditions
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

object Players {

    val players = WeakHashMap<Player, Playerlet>()

    fun get(sender: CommandSender) : Playerlet {
        Preconditions.checkArgument(sender is Player, "Can't get playerlet from command sender")
        return get(sender as Player)
    }

    fun get(bukkit: Player): Playerlet {
        return players[bukkit]!!
    }

    fun get(uuid: UUID): Playerlet? {
        return players.values.first { it.bukkit.uniqueId.equals(uuid) }
    }

    fun getPlayers(): Collection<Playerlet> {
        return players.values
    }

}