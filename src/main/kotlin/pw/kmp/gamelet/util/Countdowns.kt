package pw.kmp.gamelet.util

import org.bukkit.Bukkit
import pw.kmp.fsm.Stateful
import pw.kmp.fsm.context.StateContext
import pw.kmp.fsm.trigger.StateTrigger
import pw.kmp.gamelet.Gamelet

abstract class Countdown(var time: Int) : Stateful<Countdown.State>() {
    var scheduler: Int? = null

    override fun setup(ctx: StateContext<State>) {
        ctx.initialState = State.READY
        ctx.transition() from State.READY to State.RUNNING by CountdownStart::class calling { startCountdown(this) }
        ctx.transition() from State.RUNNING to State.ENDED by CountdownEnd::class calling { endCountdown(this) }
        ctx.transition() from State.RUNNING to State.ENDED by CountdownCancel::class calling { endCountdown(this, true) }
    }

    open fun onStart() {
    }

    open fun onTick() {
    }

    open fun onEnd() {
    }

    open fun onCancel() {
    }

    enum class State { READY, RUNNING, ENDED }

    companion object {
        fun startCountdown(cd: Countdown) {
            cd.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gamelet.plugin, {
                if (cd.time <= 0) {
                    cd.fire(CountdownEnd(cd))
                } else {
                    cd.onTick()
                    cd.time--
                }
            }, 0, 20)
            cd.onStart()
        }

        fun endCountdown(cd: Countdown, cancel: Boolean = false) {
            if (cancel) cd.onCancel()
            else cd.onEnd()
            Bukkit.getScheduler().cancelTask(cd.scheduler!!)
        }
    }

}

class CountdownStart(val cd: Countdown) : StateTrigger()
class CountdownEnd(val cd: Countdown) : StateTrigger()
class CountdownCancel(val cd: Countdown) : StateTrigger()
