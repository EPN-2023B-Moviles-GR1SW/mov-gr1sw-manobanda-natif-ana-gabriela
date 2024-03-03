package com.example.proyectoiib

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Libro(
    var id: String,
    var tipo: String,
    var titulo: String,
    var descripcion: String,
    var genero: String,
    //var urlLibro: String,
    var recordatorio: Calendar?
) {

    fun getRecordatorioString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return formatoFecha.format(this.recordatorio?.time ?: null)
    }

    fun setRecordatorioCalendar(fecha: String) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        try {
            val date = dateFormat.parse(fecha)
            val calendar = Calendar.getInstance()
            calendar.time = date
            this.recordatorio = calendar
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }
}