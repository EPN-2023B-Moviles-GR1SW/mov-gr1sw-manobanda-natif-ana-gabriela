package Operaciones

import Core.AccesoDatosProducto
import Core.AccesoDatosTienda
import Entidades.Producto
import Entidades.Tienda
import java.util.*

class MenuProducto(
    private val accesoDatosProducto: AccesoDatosProducto,
    private val accesoDatosTienda: AccesoDatosTienda
) {

    fun mostrarMenu(): Int {
        while (true) {
            try {
                println("\n------------------  MENÚ PRODUCTOS ------------------ ")
                println("1. Crear Producto")
                println("2. Mostrar Productos")
                println("3. Mostrar Productos de una tienda")
                println("4. Actualizar Producto")
                println("5. Eliminar Producto")
                println("6. Ir al menú principal")
                print("\nSeleccione una opción: ")

                val input = readLine()?.toInt()
                if (input != null && input in 1..6) {
                    return input
                } else {
                    println("Por favor, ingrese una opción válida (1-6).")
                }
            } catch (e: NumberFormatException) {
                println("Por favor, ingrese un número válido.")
            }
        }
    }

    fun ejecutarOpcion(opcion: Int) {
        when (opcion) {
            1 -> {
                crearProducto(accesoDatosTienda, accesoDatosProducto)
            }

            2 -> {
                mostrarProductos(accesoDatosProducto)
            }

            3 -> {
                print("\n\tIngrese el ID de la tienda para mostrar sus productos: ")
                val idTienda = readLine()?.toIntOrNull()
                mostrarProductosPorTienda(accesoDatosProducto, accesoDatosTienda, idTienda)
            }

            4 -> {
                actualizarProducto(accesoDatosTienda, accesoDatosProducto)
            }

            5 -> {
                eliminarProducto(accesoDatosProducto)
            }

            6 -> println("Saliendo del menú de Productos.")
            else -> println("Opción no válida. Inténtelo nuevamente.")
        }
    }

    fun crearProducto(accesoDatosTienda: AccesoDatosTienda, accesoDatosProducto: AccesoDatosProducto) {
        val tiendasExistentes = accesoDatosTienda.obtenerTiendas()

        if (tiendasExistentes.isEmpty()) {
            println("\n\t ** Aún no hay tiendas creadas. Cree una tienda antes de agregar un producto. **\n")
            return
        }

        println("\n\t -- Ingresa los datos del producto a crear --")

        var codigo: Int? = null
        while (codigo == null) {
            print("\tCódigo: ")
            codigo = readLine()?.toIntOrNull()
            if (codigo == null) {
                println("\tDebe ingresar un valor válido para el código.")
            }
        }

        var nombre: String? = null
        while (nombre.isNullOrBlank()) {
            print("\tNombre: ")
            nombre = readLine()
            if (nombre.isNullOrBlank()) {
                println("\tDebe ingresar un nombre para el producto.")
            }
        }

        var cantidadDisponible: Int? = null
        while (cantidadDisponible == null) {
            print("\tCantidad Disponible: ")
            cantidadDisponible = readLine()?.toIntOrNull()
            if (cantidadDisponible == null) {
                println("\tDebe ingresar una cantidad válida.")
            }
        }

        val tiendasIds = tiendasExistentes.map { it.id }
        var idTienda: Int? = null

        while (idTienda == null) {
            print("\tID de la Tienda: ")
            val inputId = readLine()?.toIntOrNull()
            if (inputId != null && inputId in tiendasIds) {
                idTienda = inputId
            } else {
                println("\n\t ** La tienda con el ID ingresado no se encuentra creada. \n\tLista de tiendas disponibles: **")
                tiendasExistentes.forEach { tienda ->
                    println("\tID: ${tienda.id}, Nombre: ${tienda.nombre}")
                }
                println()
            }
        }

        val tienda = accesoDatosTienda.obtenerTienda(idTienda)

        var disponible: Boolean? = null
        while (disponible == null) {
            print("\tEstá disponible (true/false): ")
            disponible = readLine()?.toBoolean()
            if (disponible == null) {
                println("\tDebe ingresar un valor booleano (true/false) para la disponibilidad.")
            }
        }

        var precioUnitario: Double? = null
        while (precioUnitario == null) {
            print("\tPrecio Unitario: ")
            precioUnitario = readLine()?.toDoubleOrNull()
            if (precioUnitario == null) {
                println("\tDebe ingresar un precio unitario válido.")
            }
        }

        val nuevoProducto = Producto(codigo, nombre, cantidadDisponible, tienda, disponible, precioUnitario)
        accesoDatosProducto.guardarProducto(nuevoProducto)

        println("\n\t** Producto '$nombre' creado correctamente. **\n")
    }



    fun mostrarProductos(accesoDatosProducto: AccesoDatosProducto) {
        println("\n\t -- Productos creados --")
        val productos = accesoDatosProducto.obtenerProductos()

        if (productos.isEmpty()) {
            println("\n\t** No hay productos creados.**\n")
        } else {
            productos.forEach { producto ->
                with(producto) {
                    println("\tCódigo: $codigo")
                    println("\tNombre: $nombre")
                    println("\tCantidad Disponible: $cantidadDisponible")
                    println("\tID de la Tienda: ${tienda.id}")
                    println("\tEstá disponible: $disponible")
                    println("\tPrecio Unitario: $precioUnitario\n")
                }
            }
        }
        println()
    }

    fun mostrarProductosPorTienda(
        accesoDatosProducto: AccesoDatosProducto,
        accesoDatosTienda: AccesoDatosTienda,
        idTienda: Int?
    ) {
        if (idTienda == null) {
            println("\n\t** No se ha ingresado el ID de la tienda. **\n")
            return
        }

        try {
            val tiendaExistente = accesoDatosTienda.obtenerTienda(idTienda)

            if (tiendaExistente != null) {
                println("\n\t -- Productos de la tienda $idTienda --")

                val productosTienda = accesoDatosProducto.obtenerProductosPorTienda(idTienda)

                if (productosTienda.isEmpty()) {
                    println("\n\t** No hay productos para la tienda $idTienda. **\n")
                } else {
                    productosTienda.forEach { producto ->
                        with(producto) {
                            println("\tCódigo: $codigo")
                            println("\tNombre: $nombre")
                            println("\tCantidad Disponible: $cantidadDisponible")
                            println("\tID de la Tienda: ${tienda.id}")
                            println("\tEstá disponible: $disponible")
                            println("\tPrecio Unitario: $precioUnitario\n")
                        }
                    }
                }
            } else {
                println("\n\t** No existe la tienda $idTienda. **\n")
            }
        } catch (e: NumberFormatException) {
            println("\n\t** Entrada inválida para el ID de la tienda. **\n")
        } catch (e: NoSuchElementException) {
            println("\n\t** No existe la tienda $idTienda. **\n")
        }
        println()
    }

    fun actualizarProducto(accesoDatosTienda: AccesoDatosTienda, accesoDatosProducto: AccesoDatosProducto) {
        println("\n\t -- Actualizar los datos de un producto --")

        print("\tIngrese el código del producto a actualizar: ")
        val codigoActualizar = readLine()?.toIntOrNull()

        if (codigoActualizar != null) {
            try {
                val productoExistente = accesoDatosProducto.obtenerProducto(codigoActualizar)

                if (productoExistente != null) {
                    val nuevoNombre = solicitarNuevaInformacion("nombre", productoExistente.nombre)
                    val nuevaCantidadDisponible =
                        solicitarNuevoNumero("cantidad disponible", productoExistente.cantidadDisponible)
                    val nuevaIdTienda = solicitarNuevoIdTienda(accesoDatosTienda, productoExistente.tienda.id)
                    val nuevaTienda = accesoDatosTienda.obtenerTienda(nuevaIdTienda)
                    val nuevaEsDisponible = solicitarNuevaDisponibilidad("disponible", productoExistente.disponible)
                    val nuevoPrecioUnitario =
                        solicitarNuevoPrecioUnitario("precio unitario", productoExistente.precioUnitario)

                    if (huboCambios(
                            nuevoNombre,
                            nuevaCantidadDisponible,
                            nuevaTienda,
                            nuevaEsDisponible,
                            nuevoPrecioUnitario,
                            productoExistente
                        )
                    ) {
                        val productoActualizado = Producto(
                            codigoActualizar,
                            nuevoNombre,
                            nuevaCantidadDisponible,
                            nuevaTienda,
                            nuevaEsDisponible,
                            nuevoPrecioUnitario
                        )
                        accesoDatosProducto.actualizarProducto(productoActualizado)
                        println("\n\t ** Producto actualizado correctamente. **\n")
                    } else {
                        println("\n\t ** No se han realizado cambios en los datos del producto. **\n")
                    }
                }
            } catch (e: NoSuchElementException) {
                println("\n\t ** No se encontró un producto con el código proporcionado. **\n")
            }
        } else {
            println("\n\t ** Entrada no válida. El código debe ser un número entero. **\n")
        }
    }

    private fun solicitarNuevaInformacion(campo: String, valorExistente: String): String {
        print("\tActualizar $campo [Mantener '$valorExistente'[Enter]]: ")
        return readLine()?.takeIf { it.isNotBlank() } ?: valorExistente
    }

    private fun solicitarNuevoNumero(campo: String, valorExistente: Int): Int {
        print("\tActualizar $campo [Mantener '$valorExistente'[Enter]]: ")
        return readLine()?.toIntOrNull() ?: valorExistente
    }

    private fun solicitarNuevoIdTienda(accesoDatosTienda: AccesoDatosTienda, valorExistente: Int): Int {
        print("\tActualizar ID de la Tienda [Mantener '$valorExistente'[Enter]]: ")
        return readLine()?.toIntOrNull() ?: valorExistente
    }

    private fun solicitarNuevaDisponibilidad(campo: String, valorExistente: Boolean): Boolean {
        print("\t¿Actualizar $campo? (true/false) [Mantener '$valorExistente'[Enter]]: ")
        return readLine()?.toBoolean() ?: valorExistente
    }

    private fun solicitarNuevoPrecioUnitario(campo: String, valorExistente: Double): Double {
        print("\tActualizar $campo [Mantener '$valorExistente'[Enter]]: ")
        return readLine()?.toDoubleOrNull() ?: valorExistente
    }

    private fun huboCambios(
        nuevoNombre: String,
        nuevaCantidadDisponible: Int,
        nuevaTienda: Tienda,
        nuevaEsDisponible: Boolean,
        nuevoPrecioUnitario: Double,
        productoExistente: Producto
    ): Boolean {
        return nuevoNombre != productoExistente.nombre ||
                nuevaCantidadDisponible != productoExistente.cantidadDisponible ||
                nuevaTienda != productoExistente.tienda ||
                nuevaEsDisponible != productoExistente.disponible ||
                nuevoPrecioUnitario != productoExistente.precioUnitario
    }


    fun eliminarProducto(accesoDatosProducto: AccesoDatosProducto) {
        println("\n\t -- Eliminar los datos de un producto --")

        print("\tIngrese el código del producto a eliminar: ")
        val codigoEliminar = readLine()?.toIntOrNull()

        if (codigoEliminar != null) {
            try {
                val productoExistente = accesoDatosProducto.obtenerProducto(codigoEliminar)

                if (productoExistente != null) {
                    accesoDatosProducto.eliminarProducto(codigoEliminar)
                    println(
                        "\n" +
                                "\t ** Producto eliminado correctamente. **\n"
                    )
                }
            } catch (e: NoSuchElementException) {
                println("\n\t ** No se encontró un producto con el código proporcionado. **\n")
            }
        } else {
            println(
                "\n" +
                        "\t ** Entrada no válida. El código debe ser un número entero. **\n"
            )
        }
    }
}
