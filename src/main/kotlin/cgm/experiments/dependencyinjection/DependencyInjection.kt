package cgm.experiments.dependencyinjection

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.jvm.jvmErasure

object DependencyInjection {
    var listOfClazz = mutableListOf<KClass<Any>>()

    inline fun <reified T> get(): T? {
        val clazz = listOfClazz.first { it == T::class }

        val params = clazz.constructors.first().parameters
            .map { param ->
                val constructor2 = listOfClazz.first { it == param.type.jvmErasure }.constructors.first()
                constructor2.takeIf { it.parameters.isNotEmpty() }
                    ?.let { cons ->
                        val param2 = cons.parameters
                            .map { param2 ->
                                listOfClazz.first { it == param2.type.jvmErasure }
                                    .constructors.first().call() }
                    constructor2.call(*param2.toTypedArray())}
                    ?: constructor2.call()
            }

        return clazz.constructors.first().call(*params.toTypedArray()) as T?

    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T: Any> add() {
        listOfClazz.add(T::class as KClass<Any>)
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
        listOfClazz = mutableListOf()
    }
}

inline fun <T> di(function: DependencyInjection.() -> T): T = TODO("Not yet implemented")

fun diAutoConfigure(packageName: String) {
    TODO("Not yet implemented")
}