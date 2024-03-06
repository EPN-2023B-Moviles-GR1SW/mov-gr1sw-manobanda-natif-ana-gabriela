package com.example.exameniib.models

class Tienda (
    var nombre: String?,
    var direccion: String?,
    var contacto: String?,
    var fechaApertura: String?,
) {
    override fun toString(): String {
        return "\nNombre: $nombre\nDirección: $direccion\nContacto: $contacto\nFecha de Apertura: $fechaApertura"
    }
}