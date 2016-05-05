package pw.kmp.gamelet.modules.team

import com.google.common.base.Preconditions
import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.maps.xml.XMLMap
import pw.kmp.gamelet.matches.Match
import pw.kmp.gamelet.modules.Module
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.modules.ModuleFactory
import pw.kmp.gamelet.players.Playerlet
import pw.kmp.gamelet.util.LiquidMetal
import pw.kmp.gamelet.util.toColor
import java.util.*

class TeamModule : Module() {

    val teams = ArrayList<Team>()

    fun getTeam(name: String) = LiquidMetal.fuzzyMatch(teams, name, {it.info.name}, 0.85)
    fun getTeamByID(id: String) = teams.filter { it.info.id.equals(id, true) }.firstOrNull()
    fun getTeam(player: Playerlet) = teams.filter { it.players.contains(player) }.firstOrNull()

}

class TeamModuleFactory : ModuleFactory<TeamModule> {

    override fun createModule(map: Maplet, ctx: ModuleContext, match: Match): TeamModule? {
        if (map.flavour == Maplet.Flavour.PGM) {
            val teams = (map as XMLMap).xml.getChild("teams") ?: return null
            val module = TeamModule()
            teams.children.forEach {
                val name = it.text;
                val id = it.getAttributeValue("id") ?: name.toLowerCase()
                val color = it.getAttributeValue("color").toColor()
                Preconditions.checkNotNull(name, "Team must have a name")

                val plural = it.getAttributeValue("plural")?.toBoolean() ?: false
                val min = it.getAttributeValue("min")?.toInt() ?: 0
                val max = it.getAttributeValue("max")?.toInt() ?: -1

                val info = Team.Info(id, name, color, plural, min, max)
                module.teams.add(Team(info))
            }

            return module
        }

        return null
    }

    override fun getModuleClass() = TeamModule::class

}