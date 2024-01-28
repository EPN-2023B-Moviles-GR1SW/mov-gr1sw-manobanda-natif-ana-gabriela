package com.example.deber02.entidades

class Producto (
    var idProducto: Int?,
    var nombreProducto: String?,
    var precioUnitario: Double?,
    var cantidadDisponible : Int?,
    var disponible: Boolean?,
    var idTienda: Int?
){
    override fun toString(): String {
        return buildString {
            append("Código: $idProducto\n")
            append("Nombre: $nombreProducto\n")
            append("Precio Unitario: $precioUnitario\n")
            append("Cantidad Disponible: $cantidadDisponible\n")
            append("Disponible: $disponible\n")
        }
    }

}