package models

import repositories.ProductoRepository
import java.text.SimpleDateFormat
import java.util.*

class Producto(
    var id: Int,
    var nombre: String,
    var disponible: Boolean,
    var tienda:Tienda,
    var cantidadDisponible: Int,
    //var precioUnitario: Double,
    val fechaLanzamiento: Date
) {
    private val productoRepository =
        ProductoRepository(System.getProperty("user.dir") + "\\src\\main\\kotlin\\data\\Productos.txt")

    fun guardarProducto() {
        productoRepository.guardarProducto(this)
        println("Guardando producto: $nombre")
    }

    fun obtenerProducto(id: Int): Producto {
        return productoRepository.obtenerProducto(id)
    }

    fun obtenerProductos(): List<Producto> {
        return productoRepository.obtenerProductos()
    }

    fun actualizarProducto() {
        productoRepository.actualizarProducto(this)
        println("Actualizando producto: $nombre")
    }

    fun eliminarProducto() {
        productoRepository.eliminarProducto(this.id)
        println("Eliminando producto: $nombre")
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "Producto(id=$id, nombre='$nombre', tienda=$tienda, cantidadDisponible=$cantidadDisponible, fechaApertura=${dateFormat.format(fechaLanzamiento)}"
    }

}