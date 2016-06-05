package pw.kmp.gamelet.modules

import pw.kmp.gamelet.matches.Match
import java.util.*
import kotlin.reflect.KClass

class ModuleContext(val match: Match) {

    val modules = HashSet<Module>()

    fun initialize() {
        ModuleRegistry.modules.forEach { loadModule(it) }
    }

    fun hasModule(module: KClass<out Module>): Boolean {
        return modules.any { it.javaClass == module.java }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Module> getFactory(module: KClass<T>) : ModuleFactory<T>? {
        return ModuleRegistry.modules.filter { it.getModuleClass() == module }.firstOrNull() as ModuleFactory<T>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Module> getModule(module: KClass<T>): T? {
        return modules.find { it.javaClass == module.java } as T
    }

    operator fun <T : Module> get(module: KClass<T>): T = getModule(module) ?: throw IllegalArgumentException("Tried to access unloaded module")

    fun loadModule(factory: ModuleFactory<Module>): Boolean {
        if (hasModule(factory.getModuleClass())) return true

        try {
            val module = factory.createModule(match.map, this, match)
            if (module != null) modules.add(module)
            return module != null
        } catch (ex: Exception) {
            return false
        }
    }

    fun depend(vararg modules: KClass<out Module>) {
        modules.forEach { module ->
            val factory = getFactory(module) ?: throw DependencyException(module)
            if (!loadModule(factory)) throw DependencyException(module)
        }
    }

    fun cleanup() {
        modules.forEach { it.cleanup() }
    }

}