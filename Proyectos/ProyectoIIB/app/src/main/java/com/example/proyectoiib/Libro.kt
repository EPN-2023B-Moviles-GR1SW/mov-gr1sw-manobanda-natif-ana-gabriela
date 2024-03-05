package com.example.proyectoiib

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Libro(
    var id: String,
    var tipo: String,
    var titulo: String,
    var autor: String,
    var genero: String,
    var urlLibro: String? = "",
    var urlImagen: String,
) {

    // Getter para obtener la URL del libro
    fun getUrlLibroString(): String? {
        return this.urlLibro
    }

    // Setter para establecer la URL del libro
    fun setUrlLibroString(url: String) {
        this.urlLibro = url
    }

    fun getImagen() :String{
        return this.urlImagen
    }
}