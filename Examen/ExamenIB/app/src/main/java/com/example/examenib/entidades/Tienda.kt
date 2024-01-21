package com.example.examenib.entidades

import java.time.LocalDate

class Tienda(
    var id: Int?,
    var nombreTienda: String,
    var direccion: String,
    var fechaApertura: LocalDate,
    var numeroEmpleados: Int,
    var contacto: String,
    var productos: ArrayList<Producto> = arrayListOf()
) {
    constructor() : this(null, "", "", LocalDate.now(), 0, "")

    override fun toString(): String {
        return "$nombreTienda"
    }

}