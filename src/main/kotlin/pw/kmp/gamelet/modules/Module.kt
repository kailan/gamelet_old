package pw.kmp.gamelet.modules

import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import pw.kmp.gamelet.Gamelet
import kotlin.reflect.KClass

open class Module : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, Gamelet.plugin)
    }

    fun cleanup() {
        HandlerList.unregisterAll(this)
    }

    @Target(AnnotationTarget.CLASS)
    annotation class Dependencies(vararg val dependencies: KClass<out Module>)

}