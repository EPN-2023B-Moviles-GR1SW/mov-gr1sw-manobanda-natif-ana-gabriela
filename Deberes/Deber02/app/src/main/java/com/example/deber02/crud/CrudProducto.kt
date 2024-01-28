package com.example.deber02.crud
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.deber02.R
import com.example.deber02.bd.BaseDeDatos
import com.google.android.material.snackbar.Snackbar
class CrudProducto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_producto)

        val idProductoAEditar = intent.getIntExtra("idProducto", 0)
        val idTiendaSelected = intent.getIntExtra("id", 0)
        llenarDatosFormulario(idProductoAEditar)

        val botonCrearBDD = findViewById<Button>(R.id.btn_CrearProd)
        botonCrearBDD
            .setOnClickListener {
                try {
                    val nombreProducto = findViewById<EditText>(R.id.input_nombreProducto)
                    val precioUnitario = findViewById<EditText>(R.id.input_precioUnitario)
                    val cantidadDisponible = findViewById<EditText>(R.id.input_cantidadDisponible)
                    val disponible: Boolean = findViewById<Switch>(R.id.sw_disponible).isChecked
                    val idTienda = idTiendaSelected

                    BaseDeDatos.tablaProducto!!.crearProducto(
                        nombreProducto.text.toString(),
                        precioUnitario.text.toString().toDouble(),
                        cantidadDisponible.text.toString().toInt(),
                        disponible,
                        idTienda
                    )
                    finish()
                } catch (e: Exception) {
                    mostrarSnackBar("Error, datos incorrectos")
                }
            }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_updateProducto)
        botonActualizarBDD
            .setOnClickListener {
                try {
                    val id = idProductoAEditar
                    val nombreProducto = findViewById<EditText>(R.id.input_nombreProducto)
                    val precioUnitario = findViewById<EditText>(R.id.input_precioUnitario)
                    val cantidadDisponible =
                        findViewById<EditText>(R.id.input_cantidadDisponible)
                    val disponible: Boolean =
                        findViewById<Switch>(R.id.sw_disponible).isChecked
                    val idTienda = idTiendaSelected

                    BaseDeDatos.tablaProducto!!.actualizarProductoFormulario(
                        nombreProducto.text.toString(),
                        precioUnitario.text.toString().toDouble(),
                        cantidadDisponible.text.toString().toInt(),
                        disponible,
                        idTienda,
                        id
                    )
                    finish()
                } catch (e: Exception) {
                    mostrarSnackBar("Error, datos incorrectos")
                }
            }

        if (idProductoAEditar != 0) {
            // Ocultar botón crear
            botonCrearBDD
                .visibility = Button.INVISIBLE
        } else {
            // Ocultar botón actualizar
            botonActualizarBDD
                .visibility = Button.INVISIBLE
        }
    }

    private fun llenarDatosFormulario(idProductoAEditar: Int) {
        if (idProductoAEditar != 0) {
            val productoEncontrado =
                BaseDeDatos.tablaProducto!!.consultarProductoPorID(idProductoAEditar)
            if (productoEncontrado != null) {
                findViewById<EditText>(R.id.input_nombreProducto).setText(productoEncontrado.nombreProducto)
                findViewById<EditText>(R.id.input_precioUnitario).setText(productoEncontrado.precioUnitario.toString())
                findViewById<EditText>(R.id.input_cantidadDisponible).setText(
                    productoEncontrado.cantidadDisponible.toString()
                )
                findViewById<Switch>(R.id.sw_disponible).isChecked = productoEncontrado.disponible!!
            }
        }
    }

    private fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_crudProducto),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}