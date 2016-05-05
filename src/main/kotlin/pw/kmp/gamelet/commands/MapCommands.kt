package pw.kmp.gamelet.commands

import com.google.common.base.Preconditions
import me.hfox.aphelion.bukkit.command.BukkitCommandHandler
import me.hfox.aphelion.command.CommandContext
import org.bukkit.command.CommandSender
import pw.kmp.gamelet.i18n.Translation
import pw.kmp.gamelet.players.Players

class MapInfoCommand : BukkitCommandHandler {

    override fun getUsage() = ""
    override fun getDescription() = "Show information about the current map"
    override fun getAliases() = arrayOf("map")

    override fun handle(sender: CommandSender, ctx: CommandContext<CommandSender>) {
        val player = Players.get(sender)
        Preconditions.checkNotNull(player.match, "Player is not in match")
        val map = player.match!!.map.info

        val message = Translation of "maps.information"
        message with "name" being map.name
        message with "version" being map.version
        message with "authors" being "TODO"
        message with "description" being map.description
        player.send(message)
    }

}