package pw.kmp.gamelet.matches

import co.enviark.speak.Translation
import pw.kmp.gamelet.util.Countdown

class MatchStartCountdown(val match: Match, length: Int = 30) : Countdown(length) {

    override fun onTick() {
        if (time % 5 == 0 || time < 5) {
            match.broadcast(Translation("match.countdown.tick").put("time", time))
        }
    }

    override fun onEnd() {
        match.startGame()
    }

    override fun onCancel() {
        match.broadcast(Translation("match.countdown.cancelled"))
    }

}

