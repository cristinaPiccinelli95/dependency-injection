package cgm.experiments.dependencyinjection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.cast
import kotlin.reflect.jvm.jvmErasure

object DependencyInjection {
    var listOfClazz = mutableListOf<KClass<Any>>()

    inline fun <reified T> get(): T? {
        //If I have a constructor without parameters return the first, otherwise return null
        val clazz = listOfClazz.first { it == T::class }
        val emptyConstructor: KFunction<Any>? =
            clazz.constructors.firstOrNull { it.parameters.isEmpty() }

        //If I have a constructor with parameters
        if (emptyConstructor == null){
            val params : List<Any> = clazz.constructors.first().parameters
                .map { param ->
                    listOfClazz.first { it == param.type.jvmErasure }
                        .constructors
                        .first()
                        .call()
                }
            return clazz.constructors.first().call(*params.toTypedArray()) as T?
        }

        return emptyConstructor.call() as T?
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