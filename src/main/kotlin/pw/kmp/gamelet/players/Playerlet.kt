package pw.kmp.gamelet.players

import org.bukkit.entity.Player
import pw.kmp.gamelet.i18n.Translation
import pw.kmp.gamelet.matches.Match

data class Playerlet(val bukkit: Player) {

    var match: Match? = null
    var state: State = State.OBSERVING

    fun send(message: String) = bukkit.sendMessage(message)
    fun send(message: Translation.TranslatedMessage) = send(message.to(this).get())

    enum class State { OBSERVING, PARTICIPATING }

}