package com.example.examenib.entidades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examenib.R

class Producto (
    var id: Int?,
    var codigo: Int,
    var nombre: String?,
    var cantidadDisponible: Int,
    var precioUnitario: Double,
    var disponible: Boolean = false,
    var tienda: Tienda = Tienda()
) {
    constructor() : this(null,0, "", 0, 0.0)

    override fun toString(): String {
        return "Producto código: $codigo"
    }
}