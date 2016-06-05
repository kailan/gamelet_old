package pw.kmp.gamelet.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import pw.kmp.gamelet.matches.Match

class MatchStartEvent(val match: Match) : Event() {

    override fun getHandlers() = MatchStartEvent.handlers

    companion object {

        val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlers

    }

}

class MatchEndEvent(val match: Match) : Event() {

    override fun getHandlers() = MatchEndEvent.handlers

    companion object {

        val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlers

    }

}