package Entidades


import java.util.*

class Producto(
    var id: Int,
    var nombre: String,
    var cantidadDisponible: Int,
    var tienda:Tienda,
    var disponible: Boolean,
    var precioUnitario: Double
) {


}

