package com.example.examenib

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.annotation.RequiresApi
import com.example.examenib.DAO.TiendaDAO
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class EditarTienda : AppCompatActivity() {
    lateinit var fechaApertura: EditText
    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tienda)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Tienda
        val tienda = TiendaDAO().getById(id)!!

        // Setear el texto en componentes visuales
        val nombreTienda = findViewById<EditText>(R.id.input_nombre)
        val direccion = findViewById<EditText>(R.id.input_direccion)
        fechaApertura = findViewById<EditText>(R.id.input_fecha)
        val numeroEmpleados = findViewById<EditText>(R.id.input_numero_Empleados)
        val contacto = findViewById<EditText>(R.id.input_contacto)

        nombreTienda.setText(tienda.nombreTienda)
        direccion.setText(tienda.direccion)
        val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val fechaFormateada = tienda.fechaApertura.format(formatoFecha)
        fechaApertura.setText(fechaFormateada)
        numeroEmpleados.setText(tienda.numeroEmpleados.toString())
        contacto.setText(tienda.contacto)

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_condominio)
        botonActualizar
            .setOnClickListener {
                tienda.nombreTienda = nombreTienda.text.toString()
                tienda.direccion = direccion.text.toString()
                val fechaTexto = fechaApertura.text.toString()
                tienda.fechaApertura = LocalDate.parse(fechaTexto, formatoFecha)
                tienda.numeroEmpleados = numeroEmpleados.text.toString().toInt()
                tienda.contacto = contacto.text.toString()
                TiendaDAO().update(tienda)
                mostrarSnackbar("Tienda Actualizada")
            }
        fechaApertura
            .setOnClickListener {
                mostrarDatePickerDialog()
            }
    }

    fun mostrarDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val fechaSeleccionada = Calendar.getInstance()
                fechaSeleccionada.set(year, monthOfYear, dayOfMonth)
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val fechaFormateada = formatoFecha.format(fechaSeleccionada.time)
                fechaApertura.setText(fechaFormateada)

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_editar_tienda),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}