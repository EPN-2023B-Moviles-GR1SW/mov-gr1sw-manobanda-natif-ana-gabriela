package com.example.examenibgm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class AccesoDatosProducto : AppCompatActivity() {
    val arregloProducto = BaseDatosMemoria.arregloProducto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceso_datos_producto)

        val btnAccesoDatosProducto = findViewById<Button>(R.id.btn_AccesoDatosProd)
        val idProductoAEditar = intent.getIntExtra("idLugar", 0)
        val idTiendaSelected = intent.getIntExtra("id", 0)

        findViewById<TextView>(R.id.tv_AccesoDatosProd).text =
            if (idProductoAEditar == 0) "Crear Producto" else "Editar Producto"

        btnAccesoDatosProducto.text = if (idProductoAEditar == 0) "Crear" else "Actualizar"

        if (idProductoAEditar != 0) {
            val prodEncontrado = BaseDatosMemoria.arregloProducto
                .find {
                    it.idProducto == idProductoAEditar
                }
            if (prodEncontrado != null) {
                findViewById<EditText>(R.id.input_nombreProducto).setText(prodEncontrado.nombreProducto)
                findViewById<EditText>(R.id.input_precioUnitario).setText(prodEncontrado.precioUnitario.toString())
                findViewById<EditText>(R.id.input_cantidadDisponible).setText(prodEncontrado.cantidadDisponible.toString())
                findViewById<Switch>(R.id.sw_disponible).isChecked = prodEncontrado.disponible!!
            }
        }
        btnAccesoDatosProducto.setOnClickListener {
            try {
                val nombre = findViewById<EditText>(R.id.input_nombreProducto)
                val precioUnitario = findViewById<EditText>(R.id.input_precioUnitario)
                val cantidadDisponible = findViewById<EditText>(R.id.input_cantidadDisponible)
                val disponible: Boolean = findViewById<Switch>(R.id.sw_disponible).isChecked

                //Eliminar lugar de la lista si es que existe y crear uno nuevo
                val prodEncontrado = arregloProducto
                    .find {
                        it.idProducto == idProductoAEditar
                    }
                if (prodEncontrado != null) {
                    arregloProducto.remove(prodEncontrado)
                }
                arregloProducto.add(
                    Producto(
                        arregloProducto.size + 2,
                        nombre.text.toString(),
                        precioUnitario.text.toString().toDouble(),
                        cantidadDisponible.text.toString().toInt(),
                        disponible,
                        idTiendaSelected
                    )
                )
                finish()
            } catch (e: Exception) {
                mostrarSnackBar("Error, datos incorrectos")
            }

        }
    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_AccesoDatosProducto),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}