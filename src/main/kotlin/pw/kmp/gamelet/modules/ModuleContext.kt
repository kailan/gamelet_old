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
    fun <T : Module> getModule(module: KClass<T>): T? {
        return modules.find { it.javaClass == module.java } as T
    }

    fun <T : Module> get(module: KClass<T>): T? = getModule(module)

    fun loadModule(factory: ModuleFactory<out Module>): Boolean {
        if (hasModule(factory.getModuleClass())) return true

        val ann = factory.getModuleClass().annotations.find { it.annotationClass.equals(Module.Dependencies::class) }
        if (ann != null) {
            (ann as Module.Dependencies).dependencies.forEach {
                loadModule(ModuleRegistry.getFactory(it)!!)
            }
        }

        val module = factory.createModule(match.map, this, match)
        if (module != null) modules.add(module)
        return module != null
    }

    fun cleanup() {
        modules.forEach { it.cleanup() }
    }

}