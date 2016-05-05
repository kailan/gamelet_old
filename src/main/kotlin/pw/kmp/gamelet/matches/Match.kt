package pw.kmp.gamelet.matches

import org.bukkit.Bukkit
import org.bukkit.World
import pw.kmp.fsm.Stateful
import pw.kmp.fsm.context.StateContext
import pw.kmp.gamelet.event.MatchStartEvent
import pw.kmp.gamelet.i18n.Translation
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.matches.state.GameEndTrigger
import pw.kmp.gamelet.matches.state.GameStartTrigger
import pw.kmp.gamelet.matches.state.MatchState
import pw.kmp.gamelet.matches.state.MatchState.*
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.players.Players
import pw.kmp.gamelet.util.CountdownCancel
import pw.kmp.gamelet.util.CountdownStart

class Match(val id: Int, val map: Maplet, val world: World) : Stateful<MatchState>() {

    val modules = ModuleContext(this)

    init {
        modules.initialize()
    }

    override fun setup(ctx: StateContext<MatchState>) {
        ctx.initialState = LOADED
        ctx.transition() from LOADED to STARTING by CountdownStart::class calling { (it as CountdownStart).cd.fire(it) }
        ctx.transition() from STARTING to LOADED by CountdownCancel::class calling { (it as CountdownCancel).cd.fire(it) }

        ctx.transition() from STARTING to PLAYING by GameStartTrigger::class calling { startGame() }
        ctx.transition() from PLAYING to ENDED by GameEndTrigger::class calling { endGame() }
    }

    fun cleanup() {
        modules.cleanup()
    }

    fun startGame() {
        Bukkit.getPluginManager().callEvent(MatchStartEvent(this))
    }

    fun endGame() {
    }

    fun getPlayers() = Players.getPlayers().filter { it.match == this }

    fun broadcast(msg: Translation.TranslatedMessage) {
        Bukkit.getConsoleSender().sendMessage(msg.to(Translation.defaultLang).get())
        getPlayers().forEach { it.send(msg) }
    }

}