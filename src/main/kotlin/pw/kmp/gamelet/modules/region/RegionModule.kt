package pw.kmp.gamelet.modules.region

import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.maps.xml.XMLMap
import pw.kmp.gamelet.matches.Match
import pw.kmp.gamelet.modules.Module
import pw.kmp.gamelet.modules.ModuleContext
import pw.kmp.gamelet.modules.ModuleFactory
import pw.kmp.gamelet.modules.region.xml.XMLRegionLoader
import java.util.*

class RegionModule : Module() {

    lateinit var loader: RegionLoader<out Any>

    val regions = HashMap<String, Region>()
    fun getRegion(region: Any) = (loader as RegionLoader<Any>).getRegion(region)

}

interface RegionLoader<T> {
    fun getRegion(region: T): Region?
}

class RegionModuleFactory : ModuleFactory<RegionModule> {

    override fun createModule(map: Maplet, ctx: ModuleContext, match: Match): RegionModule? {
        if (map.flavour == Maplet.Flavour.PGM && map is XMLMap) {
            val mod = RegionModule()
            mod.loader = XMLRegionLoader(mod)

            // PGM allows referencing by these IDs
            mod.regions["nowhere"] = EmptyRegion()
            mod.regions["everywhere"] = EverywhereRegion()

            if (map.xml.getChild("regions") != null) (mod.loader as XMLRegionLoader).parseChildren(map.xml.getChild("regions"))
            return mod
        }

        return null
    }

    override fun getModuleClass() = RegionModule::class

}