package pw.kmp.gamelet.modules

import java.util.*
import kotlin.reflect.KClass

object ModuleRegistry {

    val modules = ArrayList<ModuleFactory<Module>>()

    fun registerModule(module: ModuleFactory<Module>) {
        modules.add(module)
    }

    fun unregisterModule(module: ModuleFactory<Module>) {
        modules.remove(module)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Module> getFactory(module: KClass<T>): ModuleFactory<T>? {
        return modules.find { it.getModuleClass().equals(module) } as ModuleFactory<T>
    }

}