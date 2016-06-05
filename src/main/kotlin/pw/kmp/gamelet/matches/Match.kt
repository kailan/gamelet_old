package pw.kmp.gamelet.matches

import co.enviark.speak.Speak
import co.enviark.speak.Translation
import com.google.common.base.Preconditions
import org.bukkit.Bukkit
import org.bukkit.World
import pw.kmp.gamelet.event.MatchEndEvent
import pw.kmp.gamelet.event.MatchStartEvent
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.players.Playerlet

class Match(val id: Int, val map: Maplet, val world: World) {

    val modules = ModuleContext(this)
    var state = MatchState.LOADED

    init {
        modules.initialize()
    }

    fun cleanup() {
        modules.cleanup()
    }

    fun startGame() {
        Preconditions.checkState(state == MatchState.LOADED)
        Bukkit.getPluginManager().callEvent(MatchStartEvent(this))
        state = MatchState.RUNNING
        getPlayers().forEach { it.bukkit.respawn() }
    }

    fun endGame() {
        Preconditions.checkState(state == MatchState.RUNNING)
        Bukkit.getPluginManager().callEvent(MatchEndEvent(this))
        state = MatchState.ENDED
    }

    fun getPlayers() = Playerlet.getPlayers().filter { it.match == this }

    fun broadcast(msg: Translation) {
        Bukkit.getConsoleSender().sendMessage(msg.to(Speak.defaultLocale).get())
        getPlayers().forEach { it.send(msg) }
    }

}

enum class MatchState {

    LOADED, RUNNING, ENDED;

}