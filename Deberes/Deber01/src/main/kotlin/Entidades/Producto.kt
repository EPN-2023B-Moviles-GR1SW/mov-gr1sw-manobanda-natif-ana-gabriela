package Entidades

import Core.AccesoDatosProducto

class Producto(
    var codigo: Int,
    var nombre: String,
    var cantidadDisponible: Int,
    var tienda: Tienda,
    var disponible: Boolean,
    var precioUnitario: Double
) {

    private val accesoDatosProducto =
        AccesoDatosProducto(System.getProperty("user.dir") + "\\src\\main\\kotlin\\archivos\\Productos.txt")

    override fun toString(): String {
        return "Producto(id=$codigo, nombre='$nombre', cantidadDisponible=$cantidadDisponible, tienda=$tienda, " + "disponible=$disponible, precioUnitario=$precioUnitario, accesoDatosProducto=$accesoDatosProducto)"
    }


}

