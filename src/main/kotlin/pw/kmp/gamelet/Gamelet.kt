package pw.kmp.gamelet

import co.enviark.speak.Speak
import co.enviark.speak.Translation
import me.hfox.aphelion.bukkit.AphelionBukkit
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import pw.kmp.gamelet.commands.JoinTeamCommand
import pw.kmp.gamelet.commands.MapInfoCommand
import pw.kmp.gamelet.listeners.PlayerListener
import pw.kmp.gamelet.maps.MapManager
import pw.kmp.gamelet.matches.MatchManager
import pw.kmp.gamelet.matches.MatchStartCountdown
import pw.kmp.gamelet.rotation.Rotation
import pw.kmp.gamelet.modules.ModuleRegistry
import pw.kmp.gamelet.modules.region.RegionModuleFactory
import pw.kmp.gamelet.modules.team.TeamModuleFactory
import pw.kmp.gamelet.modules.visual.MOTDModuleFactory
import pw.kmp.gamelet.modules.visual.MessageModuleFactory
import pw.kmp.gamelet.modules.world.GameruleModuleFactory
import pw.kmp.gamelet.rotation.FileRotationProvider
import pw.kmp.gamelet.rotation.RepositoryRotationProvider
import pw.kmp.gamelet.util.Countdown
import java.io.File
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

object Gamelet {

    lateinit var plugin: JavaPlugin
    lateinit var logger: Logger

    fun initialize(plugin: GameletPlugin) {
        this.plugin = plugin
        this.logger = plugin.logger
        plugin.saveDefaultConfig()

        Speak.loadStrings(plugin.getResource("strings.yml"), Locale.ENGLISH)
        registerListeners()
        registerModules()
        registerCommands()
        loadMaps()
        loadRotation()

        MatchManager.match = MatchManager.createMatch(Rotation.next())
        Countdown.startCountdown(MatchStartCountdown(MatchManager.match!!))
    }

    fun registerListeners() {
        Bukkit.getPluginManager().registerEvents(PlayerListener(), plugin)
    }

    fun registerCommands() {
        val ctx = AphelionBukkit(plugin).registration
        ctx.register(MapInfoCommand())
        ctx.register(JoinTeamCommand())
    }

    fun registerModules() {
        // Core modules
        ModuleRegistry.registerModule(RegionModuleFactory())
        ModuleRegistry.registerModule(TeamModuleFactory())

        // Extra map modules
        ModuleRegistry.registerModule(GameruleModuleFactory())

        // Plugin modules
        ModuleRegistry.registerModule(MessageModuleFactory())
        ModuleRegistry.registerModule(MOTDModuleFactory())
    }

    fun loadMaps() {
        MapManager.loadMaps(File(plugin.config.getString("maps.repository")))
        if (MapManager.maps.size < 1) {
            log(Level.SEVERE, Translation("maps.none"))
            Bukkit.shutdown()
        }
    }

    fun loadRotation() {
        val rotationFile = File(plugin.config.getString("maps.rotation"))
        if (!rotationFile.isFile) {
            info(Translation("rotation.nofile"))
            Rotation.initialize(RepositoryRotationProvider())
        } else {
            Rotation.initialize(FileRotationProvider(rotationFile))
            info(Translation("rotation.loaded").put("file", rotationFile.name).put("count", Rotation.getMaps().size))
        }
    }

    fun log(level: Level, message: Translation) {
        logger.log(level, message.to(Speak.defaultLocale).get())
    }

    fun info(message: Translation) {
        log(Level.INFO, message)
    }

}