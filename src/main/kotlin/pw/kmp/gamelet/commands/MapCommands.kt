package pw.kmp.gamelet.commands

import co.enviark.speak.Translation
import com.google.common.base.Preconditions
import me.hfox.aphelion.bukkit.command.BukkitCommandHandler
import me.hfox.aphelion.command.CommandContext
import org.bukkit.command.CommandSender
import pw.kmp.gamelet.players.Playerlet

class MapInfoCommand : BukkitCommandHandler {

    override fun getUsage() = ""
    override fun getDescription() = "Show information about the current map"
    override fun getAliases() = arrayOf("map")

    override fun handle(sender: CommandSender, ctx: CommandContext<CommandSender>) {
        val player = Playerlet.get(sender)
        Preconditions.checkNotNull(player.match, "Player is not in match")
        val map = player.match!!.map.info

        player.send(Translation("maps.information").put("map", map))
    }

}