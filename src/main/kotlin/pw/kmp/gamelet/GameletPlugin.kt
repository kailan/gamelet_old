package pw.kmp.gamelet

import org.bukkit.plugin.java.JavaPlugin

class GameletPlugin : JavaPlugin() {

    override fun onEnable() {
        Gamelet.initialize(this)
    }

}