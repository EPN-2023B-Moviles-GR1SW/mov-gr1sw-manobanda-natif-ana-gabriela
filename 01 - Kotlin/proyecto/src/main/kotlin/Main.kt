import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    // Tipos de variables
    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Angel";
    // Inmutable = "Vicente";

    // Mutables (re asignar)
    var mutable: String = "Vicente";
    mutable = "Angel";

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
}

