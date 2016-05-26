package pw.kmp.gamelet.players

import co.enviark.speak.Translation
import com.google.common.base.Preconditions
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pw.kmp.gamelet.matches.Match
import java.util.*

data class Playerlet(val bukkit: Player) {

    var match: Match? = null
    var state: State = State.OBSERVING

    fun send(message: String) = bukkit.sendMessage(message)
    fun send(message: Translation) = send(message.to(this.bukkit).get())

    enum class State { OBSERVING, PARTICIPATING }

    companion object {

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

}