package Entidades


import Core.AccesoDatosTienda
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class Tienda(
    val id: Int = idCounter.getAndIncrement(),
    val nombre: String,
    val fechaApertura: Date,
    val direccion: String,
    val numeroEmpleados: Int,
    val contacto: String
) {
    companion object {
        private val idCounter = AtomicInteger(0)
    }

    private val accesoDatosTienda =
        AccesoDatosTienda(System.getProperty("user.dir") + "\\src\\main\\kotlin\\archivos\\Tienda.txt")

    constructor(id: Int, nombre: String, fechaApertura: String, direccion: String, numeroEmpleados: Int, contacto: String) :
            this(id, nombre, SimpleDateFormat("dd/MM/yyyy").parse(fechaApertura), direccion, numeroEmpleados, contacto)


    override fun toString(): String {
        return "Tienda(id=$id, nombre='$nombre', fechaApertura=$fechaApertura, direccion='$direccion', numeroEmpleados=$numeroEmpleados, contacto='$contacto', accesoDatosTienda=$accesoDatosTienda)"
    }


}