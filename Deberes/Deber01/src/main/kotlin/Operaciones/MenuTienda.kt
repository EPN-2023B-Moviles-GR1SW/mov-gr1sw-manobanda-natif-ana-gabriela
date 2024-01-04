package Operaciones

import Core.AccesoDatosTienda
import Entidades.Tienda
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MenuTienda(
    private val accesoDatosTienda: AccesoDatosTienda
) {
    private val scanner = Scanner(System.`in`)


    fun mostrarMenu(): Int {
        while (true) {
            try {
                println("\n------------------ MENÚ TIENDA ------------------  ")
                println("1. Crear Tienda")
                println("2. Mostrar Tiendas")
                println("3. Actualizar Tienda")
                println("4. Eliminar Tienda")
                println("5. Ir al menú principal")
                print("\nSeleccione una opción: ")

                val input = readLine()?.toInt()
                if (input != null && input in 1..5) {
                    return input
                } else {
                    println("Por favor, ingrese una opción válida (1-5).")
                }
            } catch (e: NumberFormatException) {
                println("Por favor, ingrese un número válido.")
            }
        }
    }


    fun ejecutarOpcion(opcion: Int) {
        when (opcion) {
            1 -> {
                crearTienda(accesoDatosTienda)
            }
            2 -> {
                mostrarTiendas(accesoDatosTienda)
            }
            3 -> {
                actualizarTienda(accesoDatosTienda)
            }
            4 -> {
                eliminarTienda(accesoDatosTienda)
            }
            5 -> println("Saliendo del menú de Productos.")

            else -> println("Opción no válida. Inténtelo nuevamente.")
        }
    }

    fun crearTienda(accesoDatosTienda: AccesoDatosTienda) {
        println("\n\t -- Ingresa los datos de la tienda a crear --")

        val tiendasExistentes = accesoDatosTienda.obtenerTiendas()
        val ultimoId = tiendasExistentes.maxByOrNull { it.id }?.id ?: 0
        val nuevoId = ultimoId + 1

        var nombre: String? = null
        while (nombre.isNullOrBlank()) {
            print("\t1. Nombre: ")
            nombre = readLine()
            if (nombre.isNullOrBlank()) {
                println("\tDebe ingresar un nombre para la tienda.")
            }
        }

        var fechaApertura: Date? = null
        while (fechaApertura == null) {
            print("\t2. Fecha de Apertura (dd/MM/yyyy): ")
            var fechaAperturaStr = readLine() ?: ""
            if (!validarFormatoFecha(fechaAperturaStr)) {
                println("\tPor favor, ingrese una fecha válida en formato (dd/MM/yyyy).")
            } else {
                fechaApertura = SimpleDateFormat("dd/MM/yyyy").parse(fechaAperturaStr)
            }
        }

        var direccion: String? = null
        while (direccion.isNullOrBlank()) {
            print("\t3. Dirección: ")
            direccion = readLine()
            if (direccion.isNullOrBlank()) {
                println("\tDebe ingresar una dirección para la tienda.")
            }
        }

        var numeroEmpleados: Int? = null
        while (numeroEmpleados == null) {
            print("\t4. Número de empleados: ")
            numeroEmpleados = readLine()?.toIntOrNull()
            if (numeroEmpleados == null) {
                println("\tDebe ingresar un número válido para los empleados.")
            }
        }

        var contacto: String? = null
        while (contacto.isNullOrBlank() || !contacto.matches(Regex("[0-9]+")) || contacto.length != 10) {
            print("\t5. Contacto (10 dígitos): ")
            contacto = readLine()?.trim()
            if (contacto.isNullOrBlank()) {
                println("\tDebe ingresar un contacto para la tienda.")
            } else if (!contacto.matches(Regex("[0-9]+"))) {
                println("\tEl contacto debe contener solo números.")
            } else if (contacto.length != 10) {
                println("\tEl contacto debe tener exactamente 10 dígitos.")
            }
        }


        val nuevaTienda = Tienda(nuevoId, nombre, fechaApertura!!, direccion!!, numeroEmpleados!!, contacto!!)
        accesoDatosTienda.guardarTienda(nuevaTienda)

        println("\n\t** Tienda '$nombre' creada correctamente. **\n")
    }

    fun validarFormatoFecha(fecha: String): Boolean {
        val formato = SimpleDateFormat("dd/MM/yyyy")
        formato.isLenient = false // Esto hará que la validación sea estricta

        try {
            formato.parse(fecha)
            return true
        } catch (e: ParseException) {
            return false
        }
    }


    fun mostrarTiendas(accesoDatosTienda: AccesoDatosTienda) {
        println("\n\t -- Tiendas creadas --")
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val tiendas = accesoDatosTienda.obtenerTiendas()

        if (tiendas.isEmpty()) {
            println("\n\t** No hay tiendas creadas.**\n")
        } else {
            tiendas.forEach { tienda ->
                with(tienda) {
                    val fechaAperturaStr = dateFormat.format(fechaApertura)
                    println("\tID: $id")
                    println("\tNombre: $nombre")
                    println("\tFecha de Apertura: $fechaAperturaStr")
                    println("\tDirección: $direccion")
                    println("\tNúmero de empleados: $numeroEmpleados")
                    println("\tContacto: $contacto\n")
                }
            }
        }
    }

    fun actualizarTienda(accesoDatosTienda: AccesoDatosTienda) {
        println("\n\t -- Actualizar los datos de una tienda --")
        print("\tIngrese el ID de la tienda a actualizar: ")
        val idActualizar = readLine()?.toIntOrNull()

        if (idActualizar != null) {
            // ¿La tienda existe?
            try {
                val tiendaExistente = accesoDatosTienda.obtenerTienda(idActualizar)

                // Si existe, se actualiza
                if (tiendaExistente != null) {
                    val nuevoNombre = solicitarNuevaInformacion("nombre", tiendaExistente.nombre)
                    val nuevaFechaApertura = solicitarNuevaFecha("fecha de apertura", tiendaExistente.fechaApertura)
                    val nuevaDireccion = solicitarNuevaInformacion("dirección", tiendaExistente.direccion)
                    val nuevoNumeroEmpleados =
                        solicitarNuevoNumero("número de empleados", tiendaExistente.numeroEmpleados)
                    val nuevoContacto = solicitarNuevaInformacion("contacto", tiendaExistente.contacto)

                    if (huboCambios(
                            nuevoNombre,
                            nuevaFechaApertura,
                            nuevaDireccion,
                            nuevoNumeroEmpleados,
                            nuevoContacto,
                            tiendaExistente
                        )
                    ) {
                        val tiendaActualizada = Tienda(
                            idActualizar,
                            nuevoNombre,
                            nuevaFechaApertura,
                            nuevaDireccion,
                            nuevoNumeroEmpleados,
                            nuevoContacto
                        )
                        accesoDatosTienda.actualizarTienda(tiendaActualizada)
                        println("\n\t ** Tienda actualizada correctamente.**\n")

                    } else {
                        println("\n\t ** No se han realizado cambios en los datos de la tienda. **\n")
                    }
                }
            } catch (e: NoSuchElementException) {
                println("\n\t ** No se encontró una tienda con el ID ingresado. **\n")
            }
        } else {
            println("\n\t ** Entrada no válida. El ID debe ser un número entero. **\n")
        }
    }

    private fun solicitarNuevaInformacion(campo: String, valorExistente: String): String {
        print("\tActualizar $campo [Mantener '$valorExistente' [Enter]]: ")
        return readLine()?.takeIf { it.isNotBlank() } ?: valorExistente
    }

    private fun solicitarNuevaFecha(campo: String, valorExistente: Date): Date {
        print(
            "\tActualizar $campo (dd/MM/yyyy) [Mantener '${SimpleDateFormat("dd/MM/yyyy").format(valorExistente)}' [Enter]]: "
        )
        val nuevaFechaAperturaStr = readLine()
        return if (nuevaFechaAperturaStr.isNullOrBlank()) valorExistente else SimpleDateFormat("dd/MM/yyyy").parse(
            nuevaFechaAperturaStr
        )
    }

    private fun solicitarNuevoNumero(campo: String, valorExistente: Int): Int {
        print("\tActualizar $campo [Mantener '$valorExistente' [Enter]]: ")
        return readLine()?.toIntOrNull() ?: valorExistente
    }

    private fun huboCambios(
        nuevoNombre: String,
        nuevaFechaApertura: Date,
        nuevaDireccion: String,
        nuevoNumeroEmpleados: Int,
        nuevoContacto: String,
        tiendaExistente: Tienda
    ): Boolean {
        return nuevoNombre != tiendaExistente.nombre ||
                nuevaFechaApertura != tiendaExistente.fechaApertura ||
                nuevaDireccion != tiendaExistente.direccion ||
                nuevoNumeroEmpleados != tiendaExistente.numeroEmpleados ||
                nuevoContacto != tiendaExistente.contacto
    }

    fun eliminarTienda(accesoDatosTienda: AccesoDatosTienda) {
        println("\n\t -- Eliminar los datos de una tienda --")

        print("\tIngrese el ID de la tienda a eliminar: ")
        val input = readLine()

        val idEliminar = input?.toIntOrNull()

        if (idEliminar != null) {
            try {
                val tiendaExistente = accesoDatosTienda.obtenerTienda(idEliminar)

                if (tiendaExistente != null) {
                    accesoDatosTienda.eliminarTienda(idEliminar)
                    println("\n\t ** Tienda eliminada correctamente. **\n")
                } else {
                    println("\n\t ** No se encontró una tienda con el ID ingresado. **\n")
                }
            } catch (e: NoSuchElementException) {
                println("\n\t ** No se encontró una tienda con el ID ingresado. **\n")
            }
        } else {
            println("\n\t ** Entrada no válida. Por favor, ingrese un número válido. **\n")
        }
    }


}


