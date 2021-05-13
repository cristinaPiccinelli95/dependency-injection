package cgm.experiments.dependencyinjection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.jvmErasure

object DependencyInjection {
    lateinit var clazz : KClass<Any>

    inline fun <reified T> get(): T? {
        return clazz.constructors.first { it.parameters.isEmpty() }.call() as T?
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T: Any> add() {
        clazz = T::class as KClass<Any>
    }

    fun <T: Any> add(clazz: KClass<T>) {
        TODO("Not yet implemented")
    }


    inline fun <reified T: Any, reified U: T> addI() {
        TODO("Not yet implemented")
    }

    fun <T: Any, U: T> addI(interfaze: KClass<T>, clazz: KClass<U>) {
        TODO("Not yet implemented")
    }

    inline fun <reified T: Any> add(noinline factoryFn: DependencyInjection.() -> T) {
        TODO("Not yet implemented")
    }

    fun reset() {
        println("reset")
    }
}

inline fun <T> di(function: DependencyInjection.() -> T): T = TODO("Not yet implemented")

fun diAutoConfigure(packageName: String) {
    TODO("Not yet implemented")
}