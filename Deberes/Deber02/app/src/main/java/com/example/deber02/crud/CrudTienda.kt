package com.example.deber02.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber02.R
import com.example.deber02.bd.BaseDeDatos
import java.text.SimpleDateFormat

class CrudTienda : AppCompatActivity() {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_tienda)
        val idTienda = intent.getIntExtra("id", 0)
        llenarDatosFormulario(idTienda)

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD
            .setOnClickListener{
                val nombre = findViewById<EditText>(R.id.input_nombreTienda)
                val fechaApertura = findViewById<EditText>(R.id.input_fechaTienda)
                val direccion = findViewById<EditText>(R.id.input_direccionTienda)
                val contacto = findViewById<EditText>(R.id.input_contactoTienda)
                BaseDeDatos
                    .tablaTienda!!.crearTienda(
                        nombre.text.toString(),
                        fechaApertura.text.toString(),
                        direccion.text.toString(),
                        contacto.text.toString()
                    )
                finish()
            }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD
            .setOnClickListener{
                // Obtener componentes visuales
                val id = idTienda
                val nombre = findViewById<EditText>(R.id.input_nombreTienda)
                val fechaApertura = findViewById<EditText>(R.id.input_fechaTienda)
                val direccion = findViewById<EditText>(R.id.input_direccionTienda)
                val contacto = findViewById<EditText>(R.id.input_contactoTienda)
                BaseDeDatos.tablaTienda!!.actualizarTiendaFormulario(
                    nombre.text.toString(),
                    fechaApertura.text.toString(),
                    direccion.text.toString(),
                    contacto.text.toString(),
                    id
                )
                finish()
            }
        if (idTienda !=0){
            //ocultar boton crear
            botonCrearBDD
                .visibility = Button.INVISIBLE
        }else{
            //ocultar boton actualizar
            botonActualizarBDD
                .visibility = Button.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
    }

    //si idTienda es distinto de 0, entonces se va a actualizar
    fun llenarDatosFormulario(idTienda: Int) {
        if (idTienda != 0) {
            val tiendaEncontrada = BaseDeDatos.tablaTienda!!.consultarTiendaPorID(idTienda)
            val nombre = findViewById<EditText>(R.id.input_nombreTienda)
            val fechaApertura = findViewById<EditText>(R.id.input_fechaTienda)
            val direccion = findViewById<EditText>(R.id.input_direccionTienda)
            val contacto = findViewById<EditText>(R.id.input_contactoTienda)

            nombre.setText(tiendaEncontrada.nombre)
            fechaApertura.setText(formatoFecha.format(tiendaEncontrada.fechaApertura))
            direccion.setText(tiendaEncontrada.direccion)
            contacto.setText(tiendaEncontrada.contacto)
        }
    }
}
