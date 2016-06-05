package pw.kmp.gamelet.modules.spawn

import org.bukkit.World
import pw.kmp.gamelet.modules.region.Region
import pw.kmp.gamelet.modules.team.Team

class Spawn(val team: Team, val regions: Set<Region>) {

    // TODO: random selection
    fun getLocation(world: World) = (regions.first() as Region.Spawnable).getSpawnLocation().toLocation(world)

}