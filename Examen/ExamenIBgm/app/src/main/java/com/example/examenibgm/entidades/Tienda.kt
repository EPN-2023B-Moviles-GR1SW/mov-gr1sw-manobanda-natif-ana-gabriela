package com.example.examenibgm.entidades

import java.text.SimpleDateFormat
import java.util.Date

class Tienda (
    var id: Int?,
    var nombre: String,
    var fechaApertura: Date,
    var direccion: String,
    var contacto: String,
    ) {

    override fun toString(): String {
        return buildString {
            append("Nombre: $nombre\n")
        }
    }
}