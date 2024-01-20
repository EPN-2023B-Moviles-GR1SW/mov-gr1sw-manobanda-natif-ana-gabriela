package com.example.examenibgm

import java.text.SimpleDateFormat
import java.util.Date

class Tienda (
    var id: Int?,
    var nombre: String?,
    var fechaApertura: Date?,
    var direccion: String?,
    var contacto: String?,
    ) {

    override fun toString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        return buildString {
            append("ID: ${id ?: "No disponible"}\n")
            append("Nombre: $nombre\n")
            append("Fecha de Apertura: ${fechaApertura?.let { formatoFecha.format(it) } ?: "No disponible"}\n")
            append("Dirección: $direccion\n")
            append("Contacto: $contacto\n")
        }
    }
}