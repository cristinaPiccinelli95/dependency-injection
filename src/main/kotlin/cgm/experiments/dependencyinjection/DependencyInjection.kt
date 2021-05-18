package cgm.experiments.dependencyinjection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.jvmErasure

object DependencyInjection {
    var container = mutableMapOf<KClass<Any>, KClass<Any>>()

    inline fun <reified T: Any> get(): T? {
        return get(T::class)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: KClass<T>): T? {
        val foundClazz = container[getContainerKey(clazz)] ?: return null

        val constructor = foundClazz.constructors.minByOrNull { it.parameters.size } ?: return null

        return when {
            constructor.parameters.isEmpty() -> constructor.call() as T?
            else -> callWithParams(constructor) as T?
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getContainerKey(kClass: KClass<T>): KClass<Any> = kClass as KClass<Any>

    private fun callWithParams(constructor: KFunction<Any>): Any {
        return constructor
            .call(*constructor.parameters
                .map { param ->
                    get(param.type.jvmErasure)
                }.toTypedArray())
    }

    inline fun <reified T: Any> add() {
        add(T::class)
    }

    fun <T: Any> add(clazz: KClass<T>) {
        container[getContainerKey(clazz)] = getContainerKey(clazz)
    }

    inline fun <reified T: Any, reified U: T> addI() {
        addI(T::class, U::class)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T: Any, U: T> addI(interfaze: KClass<T>, clazz: KClass<U>) {
        container[getContainerKey(interfaze)] = clazz as KClass<Any>
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T: Any> add(noinline factoryFn: DependencyInjection.() -> T) {
        TODO()
    }

    fun reset() {
        container.clear()
    }
}

inline fun <T> di(function: DependencyInjection.() -> T): T = TODO("Not yet implemented")

fun diAutoConfigure(packageName: String) {
    TODO("Not yet implemented")
}