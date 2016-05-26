package pw.kmp.gamelet.matches

import co.enviark.speak.Speak
import co.enviark.speak.Translation
import org.bukkit.Bukkit
import org.bukkit.World
import pw.kmp.gamelet.event.MatchStartEvent
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.players.Playerlet

class Match(val id: Int, val map: Maplet, val world: World) {

    val modules = ModuleContext(this)

    init {
        modules.initialize()
    }

    fun cleanup() {
        modules.cleanup()
    }

    fun startGame() {
        Bukkit.getPluginManager().callEvent(MatchStartEvent(this))
    }

    fun endGame() {
    }

    fun getPlayers() = Playerlet.getPlayers().filter { it.match == this }

    fun broadcast(msg: Translation) {
        Bukkit.getConsoleSender().sendMessage(msg.to(Speak.defaultLocale).get())
        getPlayers().forEach { it.send(msg) }
    }

}