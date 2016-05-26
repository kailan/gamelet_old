package pw.kmp.gamelet.util

import org.bukkit.Bukkit
import pw.kmp.gamelet.Gamelet

abstract class Countdown(var time: Int)  {

    var scheduler: Int? = null

    open fun onStart() {
    }

    open fun onTick() {
    }

    open fun onEnd() {
    }

    open fun onCancel() {
    }

    companion object {
        fun startCountdown(cd: Countdown) {
            cd.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gamelet.plugin, {
                if (cd.time <= 0) {
                    endCountdown(cd)
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