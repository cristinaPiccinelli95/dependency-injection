package cgm.experiments.dependencyinjection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

object DependencyInjection {
    var listOfClazz = mutableListOf<KClass<Any>>()

    inline fun <reified T> get(): T? {
        var clazz = listOfClazz.first { it == T::class }

        clazz = interfaceToClass(clazz)
        var constructor = clazz.constructors.first()

        return recursiveFun(constructor) as T?
    }

    fun interfaceToClass(clazz: KClass<Any>): KClass<Any> {
        var clazz1 = clazz
        if (clazz1.isInterface()) {
            clazz1 = listOfClazz.first { it.isSubclassOf(clazz1) && it != clazz1 }
        }
        return clazz1
    }

    private fun KClass<Any>.isInterface(): Boolean = this.java.isInterface

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

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T: Any, reified U: T> addI() {
        listOfClazz.add(T::class as KClass<Any>)
        listOfClazz.add(U::class as KClass<Any>)
    }

    fun <T: Any, U: T> addI(interfaze: KClass<T>, clazz: KClass<U>) {
        TODO("Not yet implemented")
    }

    @Suppress("UNCHECKED_CAST")
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