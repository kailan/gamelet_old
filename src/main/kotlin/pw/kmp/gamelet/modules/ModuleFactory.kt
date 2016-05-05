package pw.kmp.gamelet.modules

import pw.kmp.gamelet.maps.Maplet
import pw.kmp.gamelet.matches.Match
import kotlin.reflect.KClass

interface ModuleFactory<out T : Module> {

    fun createModule(map: Maplet, ctx: ModuleContext, match: Match): T?
    fun getModuleClass(): KClass<out T>

}