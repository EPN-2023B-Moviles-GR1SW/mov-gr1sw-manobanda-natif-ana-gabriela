package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examenib.DAO.ProductoDAO
import com.google.android.material.snackbar.Snackbar

class EditarProducto : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Condominio
        val producto = ProductoDAO().getById(id)!!

        // Setear el texto en componentes visuales
        val codigo = findViewById<EditText>(R.id.input_codigo)
        val nombre = findViewById<EditText>(R.id.input_nombre_prod)
        val cantidadDisponible = findViewById<EditText>(R.id.input_cantidad_disponible)
        val precioUnitario = findViewById<EditText>(R.id.input_precio_Unitario)
        val disponible = findViewById<Switch>(R.id.input_disponible)
        codigo.setText(producto.codigo.toString())
        nombre.setText(producto.nombre)
        cantidadDisponible.setText(producto.cantidadDisponible.toString())
        precioUnitario.setText(producto.precioUnitario.toString())
        disponible.isChecked = producto.disponible

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_departamento)
        botonActualizar
            .setOnClickListener {
                producto.codigo = codigo.text.toString().toInt()
                producto.nombre = nombre.text.toString()
                producto.cantidadDisponible = cantidadDisponible.text.toString().toInt()
                producto.precioUnitario = precioUnitario.text.toString().toDouble()
                producto.disponible = disponible.isChecked
                ProductoDAO().update(producto)
                mostrarSnackbar("Departamento Actualizado")
            }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_editar_producto),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}