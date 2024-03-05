package com.example.exameniib.models

class Producto(
    var nombreProd: String?,
    var precioUnitario: Number?,
    var disponible: Boolean?,
) {
    override fun toString(): String {
        return "${nombreProd} - $ ${precioUnitario} - ${disponible}"
    }
}