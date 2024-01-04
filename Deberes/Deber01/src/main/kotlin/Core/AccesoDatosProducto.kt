package Core

import Entidades.Producto
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AccesoDatosProducto(private val fileName: String) {
    //private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun guardarProducto(producto: Producto) {
        val registro =
            "${producto.codigo},${producto.nombre},${producto.cantidadDisponible}," + "${producto.tienda.id},${producto.disponible},${producto.precioUnitario}\n"
        File(fileName).appendText(registro)
    }

    fun obtenerProducto(codigo: Int): Producto {
        val line = File(fileName).readLines().find { it.startsWith("$codigo,") }
        val datosProducto = line?.split(",") ?: throw NoSuchElementException("No se encontró un producto con el código $codigo")

        return Producto(
            codigo = datosProducto[0].toInt(),
            nombre = datosProducto[1],
            cantidadDisponible = datosProducto[2].toInt(),
            tienda = AccesoDatosTienda(System.getProperty("user.dir") + "\\src\\main\\kotlin\\archivos\\Tienda.txt").obtenerTienda(
                datosProducto[3].toInt()
            ),
            disponible = datosProducto[4].toBoolean(),
            precioUnitario = datosProducto[5].toDouble()
        )
    }

    fun obtenerProductos(): List<Producto> {
        return File(fileName).readLines().map { line ->
            val datosProducto = line.split(",")
            Producto(
                codigo = datosProducto[0].toInt(),
                nombre = datosProducto[1],
                cantidadDisponible = datosProducto[2].toInt(),
                tienda = AccesoDatosTienda(System.getProperty("user.dir") + "\\src\\main\\kotlin\\archivos\\Tienda.txt").obtenerTienda(
                    datosProducto[3].toInt()
                ),
                disponible = datosProducto[4].toBoolean(),
                precioUnitario = datosProducto[5].toDouble()
            )
        }
    }

    fun actualizarProducto(producto: Producto) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.map { line ->
            if (line.startsWith("${producto.codigo},")) {
                "${producto.codigo},${producto.nombre},${producto.cantidadDisponible}," + "${producto.tienda.id}," + "${producto.disponible},${producto.precioUnitario}"
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
        val productosDeLaTienda =
            lineas.filter { it.split(",")[3].toInt() == idTienda }.map { crearProductoDesdeString(it) }
        return productosDeLaTienda
    }

    private fun crearProductoDesdeString(linea: String): Producto {
        val accesoDatosTienda =
            AccesoDatosTienda(System.getProperty("user.dir") + "\\src\\main\\kotlin\\archivos\\Tienda.txt")
        val datosProducto = linea.split(",")
        val id = datosProducto[0].toInt()
        val nombre = datosProducto[1]
        val cantidadDisponible = datosProducto[2].toInt()
        val idTienda = datosProducto[3].toInt()
        val tienda = accesoDatosTienda.obtenerTienda(idTienda)
        val disponible = datosProducto[4].toBoolean()
        val precioUnitario = datosProducto[5].toDouble()

        return Producto(id, nombre, cantidadDisponible, tienda, disponible, precioUnitario)
    }

}

