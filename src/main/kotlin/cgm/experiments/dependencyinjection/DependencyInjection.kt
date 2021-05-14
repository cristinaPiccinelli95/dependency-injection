package cgm.experiments.dependencyinjection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.jvmErasure

object DependencyInjection {
    var listOfClazz = mutableListOf<KClass<Any>>()

    inline fun <reified T> get(): T? {
        val clazz = listOfClazz.first { it == T::class }
        var constructor = clazz.constructors.first()

        return recursiveFun(constructor) as T?
    }

    fun recursiveFun(constructor: KFunction<Any>): Any {
        return constructor
            .call(*constructor.parameters
                .map { param ->
                    val cons = listOfClazz.first { it == param.type.jvmErasure }.constructors.first()
                    if (cons.parameters.isNotEmpty()){
                        recursiveFun(cons)
                    }else{
                        cons.call()
                    }
                }.toTypedArray())
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