package com.example.exameniib.models

class Producto(
    var nombreProd: String?,
    var precioUnitario: Number?,
    var cantidadDisponible : Number?,
    var disponible: Boolean?,
) {
    override fun toString(): String {
        return "\nNombre del Producto: $nombreProd\nPrecio Unitario: $precioUnitario\nCantidad Disponible: $cantidadDisponible\nDisponible: $disponible"
    }
}