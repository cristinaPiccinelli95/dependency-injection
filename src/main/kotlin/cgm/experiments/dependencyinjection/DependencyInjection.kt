package cgm.experiments.dependencyinjection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.jvmErasure

object DependencyInjection {
    private val container = mutableMapOf<KClass<Any>, KClass<Any>>()
    private val containerFn = mutableMapOf<KClass<Any>, DependencyInjection.() -> Any>()

    inline fun <reified T: Any> get(): T? {
        return get(T::class)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: KClass<T>): T? {
        val foundClazz = container[getContainerKey(clazz)]
            ?: return containerFn[getContainerKey(clazz)]?.invoke(this) as T?

        val constructor = foundClazz.constructors.minByOrNull { it.parameters.size } ?: return null

        return when {
            constructor.parameters.isEmpty() -> constructor.call() as T?
            else -> callWithParams(constructor) as T?
        }
    }

    private fun callWithParams(constructor: KFunction<Any>): Any {
        return constructor
            .call(*constructor.parameters
                .map { param ->
                    get(param.type.jvmErasure)
                }.toTypedArray())
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getContainerKey(kClass: KClass<T>): KClass<Any> = kClass as KClass<Any>

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getContainerValue(kClass: KClass<T>): KClass<Any> = kClass as KClass<Any>

    inline fun <reified T: Any> add() {
        add(T::class)
    }

    fun <T: Any> add(clazz: KClass<T>) {
        container[getContainerKey(clazz)] = getContainerValue(clazz)
    }

    inline fun <reified T: Any, reified U: T> addI() {
        addI(T::class, U::class)
    }

    fun <T: Any, U: T> addI(interfaze: KClass<T>, clazz: KClass<U>) {
        container[getContainerKey(interfaze)] = getContainerValue(clazz)
    }

    inline fun <reified T: Any> add(noinline factoryFn: DependencyInjection.() -> T) {
        add(T::class, factoryFn)
    }

    fun <T: Any> add(clazz: KClass<T>, factoryFn: DependencyInjection.() -> T) {
        containerFn[getContainerKey(clazz)] = factoryFn
    }

    fun reset() {
        container.clear()
        containerFn.clear()
    }
}

inline fun <T> di(function: DependencyInjection.() -> T): T = TODO("Not yet implemented")

fun diAutoConfigure(packageName: String) {
    TODO("Not yet implemented")
}