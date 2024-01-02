package models

import repositories.TiendaRepository
import java.text.SimpleDateFormat
import java.util.Date

class Tienda(
    val id: Int,
    val nombre: String,
    val fechaApertura: Date,
    val direccion: String,
    val numeroEmpleados: Int
){
    private val tiendaRepository = TiendaRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\Tienda.txt")

    constructor(id: Int, nombre: String, fechaApertura: String, direccion: String, numeroEmpleados: Int) :
            this(id, nombre, SimpleDateFormat("dd/MM/yyyy").parse(fechaApertura), direccion, numeroEmpleados)

    fun guardarTienda() {
        tiendaRepository.guardarTienda(this)
        println("Guardando género: $nombre")
    }

    fun obtenerTienda(id: Int): Tienda {
        return tiendaRepository.obtenerTienda(id)
    }

    fun obtenerTiendas(): List<Tienda> {
        return tiendaRepository.obtenerTiendas()
    }

    fun actualizarTienda() {
        tiendaRepository.actualizarTienda(this)
        println("Actualizando tienda: $nombre")
    }

    fun eliminarTienda() {
        tiendaRepository.eliminarTienda(this.id)
        println("Eliminando tiends: $nombre")
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "Tienda(id=$id, nombre='$nombre', fechaApertura=${dateFormat.format(fechaApertura)}, direccion='$direccion', numeroEmpleados=$numeroEmpleados)"
    }
}