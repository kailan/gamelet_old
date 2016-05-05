package pw.kmp.gamelet.matches

import pw.kmp.gamelet.i18n.Translation
import pw.kmp.gamelet.matches.state.GameStartTrigger
import pw.kmp.gamelet.util.Countdown

class MatchStartCountdown(val match: Match, length: Int = 30) : Countdown(length) {

    override fun onTick() {
        if (time % 5 == 0 || time < 5) {
            if (time == 1) match.broadcast(Translation of "match.countdown.tick.singular")
            else match.broadcast(Translation of "match.countdown.tick.plural" with "time" being time.toString())
        }
    }

    override fun onEnd() {
        match.fire(GameStartTrigger())
    }

    override fun onCancel() {
        match.broadcast(Translation of "match.countdown.cancelled")
    }

}