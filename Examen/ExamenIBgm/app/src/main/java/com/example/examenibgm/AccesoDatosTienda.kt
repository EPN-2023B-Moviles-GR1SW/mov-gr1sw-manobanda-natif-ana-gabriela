package com.example.examenibgm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

class AccesoDatosTienda : AppCompatActivity() {
    val arregloTienda = BaseDatosMemoria.arregloTienda

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceso_datos_tienda)

        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        val botonCrearTienda = findViewById<Button>(R.id.btn_crearActualizar)

        val idTiendaAEditar = intent.getIntExtra("id", 0)

        findViewById<TextView>(R.id.tv_AccesoDatosTienda).text =
            if (idTiendaAEditar == 0) "Crear Tienda" else "Editar Tienda"

        botonCrearTienda.text = if (idTiendaAEditar == 0) "Crear" else "Actualizar"

        if (idTiendaAEditar != 0) {
            val tiendaEncontrada = arregloTienda
                .find {
                    it.id == idTiendaAEditar
                }
            if (tiendaEncontrada != null) {
                findViewById<EditText>(R.id.input_nombreTienda).setText(tiendaEncontrada.nombre)

                val fechaApertura = tiendaEncontrada.fechaApertura
                if (fechaApertura != null) {
                    findViewById<EditText>(R.id.input_fechaTienda).setText(
                        formatoFecha.format(fechaApertura)
                    )
                }
                /*
                findViewById<EditText>(R.id.input_fechaTienda).setText(
                    formatoFecha.format(
                        tiendaEncontrada.fechaApertura
                    )
                )
                */

                findViewById<EditText>(R.id.input_direccionTienda).setText(tiendaEncontrada.direccion)
                findViewById<EditText>(R.id.input_contactoTienda).setText(tiendaEncontrada.contacto)
            }
        }

        botonCrearTienda.setOnClickListener {
            /*
            try {
                val nombre = findViewById<EditText>(R.id.input_nombreTienda)
                val fecha = findViewById<EditText>(R.id.input_fechaTienda)
                val direccion = findViewById<EditText>(R.id.input_direccionTienda)
                val contacto = findViewById<EditText>(R.id.input_contactoTienda)

                //Eliminar pais de la lista si es que existe y crear uno nuevo

                val paisEncontrado = arregloTienda
                    .find {
                        it.id == idTiendaAEditar
                    }

                if (paisEncontrado != null) {
                    arregloTienda.remove(paisEncontrado)
                }

                arregloTienda.add(
                    Tienda(
                        arregloTienda.size + 1,
                        nombre.text.toString(),
                        formatoFecha.parse(fecha.text.toString()),
                        direccion.text.toString(),
                        contacto.text.toString(),
                        )
                )

                finish()
            } catch (e: Exception) {
                mostrarSnackBar("Ups, parece que los datos son incorrectos")
            }
            */
            try {
                val nombre = findViewById<EditText>(R.id.input_nombreTienda)
                val fecha = findViewById<EditText>(R.id.input_fechaTienda)
                val direccion = findViewById<EditText>(R.id.input_direccionTienda)
                val contacto = findViewById<EditText>(R.id.input_contactoTienda)

                if (idTiendaAEditar == 0) {
                    // Crear nueva tienda si idTiendaAEditar es 0
                    arregloTienda.add(
                        Tienda(
                            arregloTienda.size + 1,
                            nombre.text.toString(),
                            formatoFecha.parse(fecha.text.toString()),
                            direccion.text.toString(),
                            contacto.text.toString()
                        )
                    )
                } else {
                    // Actualizar tienda existente si idTiendaAEditar no es 0
                    val tiendaEncontrada = arregloTienda.find { it.id == idTiendaAEditar }
                    if (tiendaEncontrada != null) {
                        tiendaEncontrada.nombre = nombre.text.toString()
                        tiendaEncontrada.fechaApertura = formatoFecha.parse(fecha.text.toString())
                        tiendaEncontrada.direccion = direccion.text.toString()
                        tiendaEncontrada.contacto = contacto.text.toString()
                    }
                }

                finish()
            } catch (e: Exception) {
                mostrarSnackBar("Datos incorrectos")
            }
        }
    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_AccesoDatosTienda),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}