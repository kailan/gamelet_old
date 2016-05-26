package pw.kmp.gamelet.commands

import co.enviark.speak.Translation
import com.google.common.base.Preconditions
import me.hfox.aphelion.bukkit.command.BukkitCommandHandler
import me.hfox.aphelion.command.CommandContext
import org.bukkit.command.CommandSender
import pw.kmp.gamelet.modules.team.TeamModule
import pw.kmp.gamelet.players.Playerlet

class JoinTeamCommand : BukkitCommandHandler {

    override fun getUsage() = "[team]"
    override fun getDescription() = "Join a team in the current match"
    override fun getAliases() = arrayOf("join")

    override fun handle(sender: CommandSender, ctx: CommandContext<CommandSender>) {
        val player = Playerlet.get(sender)
        Preconditions.checkNotNull(player.match, "Player is not in match")
        val teams = player.match!!.modules.getModule(TeamModule::class)!!

        val currentTeam = teams.getTeam(player)
        if (ctx.length() > 0) {
            val team = teams.getTeam(ctx.joinedString)
            if (team != null) {
                if (team != currentTeam) {
                    team.addPlayer(player)
                } else {
                    player.send(Translation("team.member").put("team", currentTeam.getFriendlyName()))
                }
            } else {
                player.send(Translation("team.invalid"))
            }
        } else {
            if (currentTeam != null) {
                player.send(Translation("team.member").put("team", currentTeam.getFriendlyName()))
                return
            }

            val team = teams.teams.sortedByDescending {it.players.size}.firstOrNull()
            if (team == null) player.send(Translation("team.none"))
            team?.addPlayer(player)
        }
    }

}