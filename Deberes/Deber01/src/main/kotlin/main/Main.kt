package main

import models.Producto
import models.Tienda
import repositories.ProductoRepository
import repositories.TiendaRepository
import java.text.SimpleDateFormat
import java.util.*

/*
import models.Cancion
import models.GeneroMusical
import repositories.CancionRepository
import repositories.GeneroMusicalRepository
import java.text.SimpleDateFormat
import java.util.*
*/

fun main() {
    val tiendaRepository = TiendaRepository(System.getProperty("user.dir") + "\\src\\main\\kotlin\\data\\Tienda.txt")
    val productoRepository = ProductoRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\Productos.txt")

    val scanner = Scanner(System.`in`)
    var opcion: Int

    do {
        println("=== Menú CRUD ===")
        println("1. Agregar Género Musical")
        println("2. Agregar Canción")
        println("3. Mostrar Géneros Musicales")
        println("4. Mostrar Canciones")
        println("5. Actualizar Género Musical")
        println("6. Actualizar Canción")
        println("7. Eliminar Género Musical")
        println("8. Eliminar Canción")
        println("9. Salir")

        print("Seleccione una opción: ")
        opcion = scanner.nextInt()

        when (opcion) {
            1 -> {
                agregarTienda(tiendaRepository)
            }
            2 -> {
                agregarProducto(tiendaRepository, productoRepository)
            }
            3 -> {
                mostrarTiendas(tiendaRepository)
            }
            4 -> {
                mostrarProductos(productoRepository)
            }
            5 -> {
                actualizarTienda(tiendaRepository)
            }
            6 -> {
                actualizarProducto(tiendaRepository, productoRepository)
            }
            7 -> {
                eliminarTienda(tiendaRepository)
            }
            8 -> {
                eliminarProducto(productoRepository)
            }
            9 -> {
                println("Saliendo de la aplicación.")
            }
            else -> println("Opción no válida. Inténtelo nuevamente.")
        }
    } while (opcion != 9)
}

fun agregarTienda(tiendaRepository: TiendaRepository) {
    println("=== Agregar Género Musical ===")
    print("ID: ")
    val id = readLine()?.toInt() ?: 0

    print("Nombre: ")
    val nombre = readLine() ?: ""

    print("Fecha de Apertura (dd/MM/yyyy): ")
    val fechaCreacionStr = readLine() ?: ""
    val fechaCreacion = SimpleDateFormat("dd/MM/yyyy").parse(fechaCreacionStr)

    print("Direccion: ")
    val direccion = readLine() ?: ""

    print("NumeroEmpleados: ")
    val numeroEmpleados = readLine()?.toInt() ?: 0

    val nuevaTienda = Tienda(id, nombre, fechaCreacion, direccion, numeroEmpleados)
    tiendaRepository.guardarTienda(nuevaTienda)

    println("Género Musical agregado correctamente.\n")
}

fun agregarProducto(tiendaRepository: TiendaRepository, productoRepository: ProductoRepository) {
    println("=== Agregar Canción ===")
    print("ID: ")
    val id = readLine()?.toInt() ?: 0

    print("Nombre: ")
    val nombre = readLine() ?: ""

    print("Está disponible (true/false): ")
    val disponible = readLine()?.toBoolean() ?: false

    print("ID de la Tienda: ")
    val idTienda = readLine()?.toInt() ?: 0
    val tienda = tiendaRepository.obtenerTienda(idTienda)

    print("CantidadDisponible: ")
    val cantidadDisponible = readLine()?.toInt() ?: 0

    print("Fecha de Lanzamiento (dd/MM/yyyy): ")
    val fechaLanzamientoStr = readLine() ?: ""
    val fechaLanzamiento = SimpleDateFormat("dd/MM/yyyy").parse(fechaLanzamientoStr)



    val nuevoProducto = Producto(id, nombre, disponible, tienda,cantidadDisponible,fechaLanzamiento)
    productoRepository.guardarProducto(nuevoProducto)

    println("Canción agregada correctamente.\n")
}

fun mostrarTiendas(tiendaRepository: TiendaRepository) {
    println("=== Géneros Musicales ===")
    val tiendas = tiendaRepository.obtenerTiendas()
    tiendas.forEach { println(it) }
    println()
}

fun mostrarProductos(productoRepository: ProductoRepository) {
    println("=== Canciones ===")
    val productos = productoRepository.obtenerProductos()
    productos.forEach { println(it) }
    println()
}


fun actualizarTienda(tiendaRepository: TiendaRepository) {
    println("=== Actualizar Género Musical ===")
    // Capturar ID del género a actualizar
    print("Ingrese el ID del Género Musical a actualizar: ")
    val idActualizar = readLine()?.toIntOrNull()

    if (idActualizar != null) {
        // Verificar si el género existe
        val tiendaExistente = tiendaRepository.obtenerTienda(idActualizar)

        // Si el género existe, permitir la actualización
        if (tiendaExistente != null) {
            // Capturar nuevos datos del usuario...
            print("Nuevo nombre (Enter para mantener el actual '${tiendaExistente.nombre}'): ")
            val nuevoNombre = readLine()?.takeIf { it.isNotBlank() } ?: tiendaExistente.nombre

            print("Nueva fecha de Creación (dd/MM/yyyy) (Enter para mantener la actual '${SimpleDateFormat("dd/MM/yyyy").format(tiendaExistente.fechaApertura)}'): ")
            val nuevaFechaCreacionStr = readLine()
            val nuevaFechaCreacion = if (nuevaFechaCreacionStr.isNullOrBlank()) tiendaExistente.fechaApertura else SimpleDateFormat("dd/MM/yyyy").parse(nuevaFechaCreacionStr)

            print("Nueva DIRECCIÓN (Enter para mantener la actual '${tiendaExistente.direccion}'): ")
            val nuevaDireccion = readLine()?.takeIf { it.isNotBlank() } ?: tiendaExistente.direccion

            print("Nueva popularidad (Enter para mantener la actual '${tiendaExistente.numeroEmpleados}'): ")
            val nuevoNumeroEmpleados = readLine()?.toIntOrNull() ?: tiendaExistente.numeroEmpleados

            val tiendaActualizada = Tienda(idActualizar, nuevoNombre, nuevaFechaCreacion, nuevaDireccion, nuevoNumeroEmpleados)
            tiendaRepository.actualizarTienda(tiendaActualizada)
            println("Género Musical actualizado correctamente.\n")
        } else {
            println("No se encontró un Género Musical con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}


fun actualizarProducto(generoRepository: TiendaRepository, productoRepository: ProductoRepository) {
    println("=== Actualizar Canción ===")
    // Capturar ID de la canción a actualizar
    print("Ingrese el ID de la Canción a actualizar: ")
    val idActualizar = readLine()?.toIntOrNull()

    if (idActualizar != null) {
        // Verificar si la canción existe
        val productoExistente = productoRepository.obtenerProducto(idActualizar)

        // Si la canción existe, permitir la actualización
        if (productoExistente != null) {
            // Capturar nuevos datos del usuario...
            print("Nuevo nombre (Enter para mantener el actual '${productoExistente.nombre}'): ")
            val nuevoNombre = readLine()?.takeIf { it.isNotBlank() } ?: productoExistente.nombre

            print("Esta disponible (true/false) (Enter para mantener el actual '${productoExistente.disponible}'): ")
            val nuevaEsDisponible = readLine()?.toBoolean() ?: productoExistente.disponible


            print("Nuevo ID del Género Musical (Enter para mantener el actual '${productoExistente.tienda.id}'): ")
            val nuevaIdTienda = readLine()?.toIntOrNull() ?: productoExistente.tienda.id
            val nuevaTienda = generoRepository.obtenerTienda(nuevaIdTienda)

            print("Nueva duración (Enter para mantener la actual '${productoExistente.cantidadDisponible}'): ")
            val nuevaCantidadDisponible = readLine()?.toIntOrNull() ?: productoExistente.cantidadDisponible


            print("Nueva fecha de Lanzamiento (dd/MM/yyyy) (Enter para mantener la actual '${SimpleDateFormat("dd/MM/yyyy").format(productoExistente.fechaLanzamiento)}'): ")
            val nuevaFechaLanzamientoStr = readLine()
            val nuevaFechaLanzamiento = if (nuevaFechaLanzamientoStr.isNullOrBlank()) productoExistente.fechaLanzamiento else SimpleDateFormat("dd/MM/yyyy").parse(nuevaFechaLanzamientoStr)



            val productoActualizado = Producto(idActualizar, nuevoNombre,nuevaEsDisponible, nuevaTienda, nuevaCantidadDisponible,nuevaFechaLanzamiento)
            productoRepository.actualizarProducto(productoActualizado)
            println("Canción actualizada correctamente.\n")
        } else {
            println("No se encontró una Canción con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}



fun eliminarTienda(tiendaRepository: TiendaRepository) {
    println("=== Eliminar Género Musical ===")
    // Capturar ID del género a eliminar
    print("Ingrese el ID del Género Musical a eliminar: ")
    val idEliminar = readLine()?.toIntOrNull()

    if (idEliminar != null) {
        // Verificar si el género existe
        val tiendaExistente = tiendaRepository.obtenerTienda(idEliminar)

        // Si el género existe, permitir la eliminación
        if (tiendaExistente != null) {
            tiendaRepository.eliminarTienda(idEliminar)
            println("Género Musical eliminado correctamente.\n")
        } else {
            println("No se encontró un Género Musical con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}

fun eliminarProducto(productoRepository: ProductoRepository) {
    println("=== Eliminar Canción ===")
    // Capturar ID de la canción a eliminar
    print("Ingrese el ID de la Canción a eliminar: ")
    val idEliminar = readLine()?.toIntOrNull()

    if (idEliminar != null) {
        // Verificar si la canción existe
        val productoExistente = productoRepository.obtenerProducto(idEliminar)

        // Si la canción existe, permitir la eliminación
        if (productoExistente != null) {
            productoRepository.eliminarProducto(idEliminar)
            println("Canción eliminada correctamente.\n")
        } else {
            println("No se encontró una Canción con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}
