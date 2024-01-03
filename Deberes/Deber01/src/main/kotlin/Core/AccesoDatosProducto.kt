package Core

import Entidades.Producto
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AccesoDatosProducto (private val fileName: String) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun guardarProducto(producto: Producto) {
        val registro = "${producto.id},${producto.nombre},${producto.cantidadDisponible}," +
                "${producto.tienda.id},${dateFormat.format(producto.fechaLanzamiento)}," +
                "${producto.disponible}\n"
        File(fileName).appendText(registro)
    }

    fun obtenerProducto(id: Int): Producto {
        val line = File(fileName).readLines().find { it.startsWith("$id,") }
        val campos = line?.split(",") ?: throw NoSuchElementException("No se encontró un producto con el ID $id")

        return Producto(
            id = campos[0].toInt(),
            nombre = campos[1],
            cantidadDisponible = campos[2].toInt(),
            tienda = TiendaRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\Tienda.txt").obtenerTienda(campos[3].toInt()),
            fechaLanzamiento = dateFormat.parse(campos[4]),
            disponible = campos[5].toBoolean()
        )
    }

    fun obtenerProductos(): List<Producto> {
        return File(fileName).readLines().map { line ->
            val campos = line.split(",")
            Producto(
                id = campos[0].toInt(),
                nombre = campos[1],
                disponible = campos[2].toBoolean(),
                tienda = TiendaRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\Tienda.txt").obtenerTienda(campos[3].toInt()),
                cantidadDisponible = campos[4].toInt(),
                fechaLanzamiento = dateFormat.parse(campos[5])
            )
        }
    }

    fun actualizarProducto(producto: Producto) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.map { line ->
            if (line.startsWith("${producto.id},")) {
                "${producto.id},${producto.nombre},${producto.cantidadDisponible}," +
                        "${producto.tienda.id},${dateFormat.format(producto.fechaLanzamiento)}," +
                        "${producto.disponible}"
            } else {
                line
            }
        }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }

    fun eliminarProducto(id: Int) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.filter { !it.startsWith("$id,") }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }

    fun obtenerProductosPorTienda(idTienda: Int): List<Producto> {
        val lineas = File(fileName).readLines()
        val productosDeLaTienda = lineas
            .filter { it.split(",")[3].toInt() == idTienda }
            .map { crearProductoDesdeString(it) }
        return productosDeLaTienda
    }

    private fun crearProductoDesdeString(linea: String): Producto {
        val tiendaRepository = TiendaRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\Tienda.txt")
        val campos = linea.split(",")
        val id = campos[0].toInt()
        val nombre = campos[1]
        val cantidadDisponible = campos[2].toInt()
        val idTienda = campos[3].toInt()
        val fechaLanzamiento = SimpleDateFormat("dd/MM/yyyy").parse(campos[4])
        val tienda = tiendaRepository.obtenerTienda(idTienda)
        val disponible = campos[5].toBoolean()

        return Producto(id, nombre, disponible,tienda, cantidadDisponible,fechaLanzamiento)
    }

}

