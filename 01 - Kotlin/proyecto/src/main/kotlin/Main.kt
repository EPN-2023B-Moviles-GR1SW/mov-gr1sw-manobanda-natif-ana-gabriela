import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    // Tipos de variables
    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Gabriela"
    //inmutable = "Vicente";

    // Mutables (re asignar)
    var mutable: String = "Vicente"
    mutable = "Gabriela"

    // val > var

    // DUCK TYPING
    var ejemploVariable = "Adrian Eguez"
    val edadEjmplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo;

    // Variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    //Clases Java
    val fechaNacimiento: Date = Date()

    // Switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(sueldo = 10.00, tasa = 12.00, bonoEspecial = 20.00) // PARÁMETROS NOMBRADOS
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) //Parámetros nombrados

    // CREAR INSTANCIAS DE LA CLASE SUMA
    // No es necesario poner la palabra 'new' para crear la instancia
    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // ARREGLOS

    // Tipos de arreglos

    // Arreglo estático
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    // Arreglo dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // FOR EACH -> Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
            .forEach{ valorActual: Int ->
                println("Valor actual: ${valorActual}")
            }
    // it (en ingés eso) significa el elemento iterado
    arregloDinamico.forEach{ println(it) }

    arregloEstatico
            .forEachIndexed { indice: Int, valorActual: Int ->
                println("Valor ${valorActual} Indice: ${indice}")
            }

    // MAP -> Muta el arreglo (Cambia el arreglo)
    // 1) Enviemos el nuevo valor de la iteración
    // 2) Nos devuelve es un NUEVO ARREGLO con los valores modificados.

    val respuestaMap: List<Double> = arregloDinamico
            .map { valorActual: Int ->
                return@map valorActual.toDouble() + 100.00
            }

    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }

    // Filter -> FILTRAR EL ARREGLO
    // 1) Devolver una expresión (TRUE OF FALSE)
    // 2) Nuevo arreglo filtrado

    val respuestaFilter: List<Int> = arregloDinamico
            .filter { valorActual: Int ->
                // Expresión condición
                val mayoresACinco: Boolean = valorActual > 5
                return@filter mayoresACinco
            }
    val respuestaFilterDos = arregloDinamico.filter { it <= 5}
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> ANY (Alguno cumple?)
    // AND -> ALL (Todos cumplen?)

    val respuestaAny: Boolean = arregloDinamico
            .any { valorActual: Int ->
                return@any (valorActual > 5)
            }
    println(respuestaAny)  // true

    val respuestaAll: Boolean = arregloDinamico
            .all { valorActual: Int ->
                return@all (valorActual > 5)
            }
    println(respuestaAll) // false

    // REDUCE: Itera un arreglo y guarda el acumulado
    // ACUMULA LOS VALORES
    // EJ: Acumula la suma de todos los valores

    // Valor acumulado = 0 (Siempre 0 en lenguaje Kotlin)
    // [1, 2, 3, 4, 5] -> Sumeme todos los valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteración 1
    // valorIteracion2 = valorEmpieza + 2 = 1 + 2 = 3 -> Iteración 2
    // valorIteracion3 = valorEmpieza + 3 = 3 + 3 = 6 -> Iteración 3
    // valorIteracion4 = valorEmpieza + 4 = 6 + 4 = 10 -> Iteración 4
    // valorIteracion5 = valorEmpieza + 5 = 10 + 5 = 15 -> Iteración 5

    val respuestaReduce: Int = arregloDinamico
            .reduce { // acumulado = 0 -> SIEMPRE EMPIEZA EN 0
                    acumulado: Int, valorActual:Int ->
                    return@reduce (acumulado + valorActual) // -> Lógica de negocio
            }
    println(respuestaReduce) // 78
    // valorCarritoActual.cantidad * valorCarritoActual.valor
    // 2 x 195
    // 1 x 10
    // 1 x 10
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
            uno: Int,
            dos: Int
    ){// Bloque de código del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( // Constructor PRIMARIO
        // Ejemplo:
        // uno: Int, // (Parametro (sin modficador de acceso))
        // private var uno: Int, // Propiedad Publica Clase numeros.uno
        // var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
        // public var uno: Int,
        // Propiedad de la clase protected numeros.numeroUno

        protected  val numeroUno: Int,
        // Propiedad de la clase protcted numeros.numeroDos
        protected val numeroDos: Int,
) {
    // var cedula: string = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)

    init {
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Inicializando")
    }

}



// void --> unit
fun imprimirNombre(nombre: String): Unit{
    //template strings
    //"Bienvenido: " + nombre + ""
    println("Nombre : ${nombre}")
}

fun calcularSueldo(
        sueldo: Double,  //Requerido
        tasa: Double = 12.00, // Opcional (defecto) [Tiene un valor asignado]
        bonoEspecial: Double? = null, //Opcion null -> nullable
):Double{
    //bonoEspecial.dec() //saldría error porque no se diferencia si la variable bonoEspecial es nula o no
    //Int -> Int? (nullable)
    //String -> String? (nullable)
    //Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * (100/tasa) + bonoEspecial
    }
}

class Suma( // Constructor Primario Suma
        uno: Int, // Parámetro
        dos: Int // Parámetro
) : Numeros(uno, dos){ // <- Constructor del Padre
    init { // Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor( // Segundo constructor
            uno: Int?, // parámetros
            dos: Int   // parámetros
    ) : this( // llamada constructor primario
            if(uno == null) 0 else uno,
            dos
    ) { // si necesitamos bloque de código lo usamos
        numeroDos
    }


    constructor( // tercer constructor
            uno: Int, // parámetros
            dos: Int?   // parámetros
    ) : this( // llamada constructor primario
            uno,
            if(dos == null) 0 else uno,
    ) { // si no lo necesitamos al bloque de código "{}" lo omitimos
    }

    constructor( // cuarto constructor
            uno: Int?,  // parámetros
            dos: Int?,  // parámetros
    ) : this(  // llamada constructor primario
            if (uno == null) 0 else uno,
            if (dos == null) 0 else uno
    )

    // public por defecto, o usar private o protected
    public fun sumar(): Int{
        val total = numeroUno + numeroDos
        //Suma.agregarHistorial(total)
        agregarHistorial(total)
        return total
    }

    //Companion object --> Singleton
    companion object{
        // Atributos y Métodos "Compartidos"
        // entre las instancias
        val pi = 3.14

        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valorNuevoSuma: Int){
            historialSumas.add(valorNuevoSuma)
        }
    }

}





