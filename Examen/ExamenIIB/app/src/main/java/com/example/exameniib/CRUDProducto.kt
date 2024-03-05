package com.example.exameniib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.google.android.material.snackbar.Snackbar

import com.google.firebase.firestore.FirebaseFirestore

class CRUDProducto : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var documentoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudproducto)

        val nombreTienda = intent.getStringExtra("nombreT") ?: ""
        val nombreProducto = intent.getStringExtra("nombreP") ?: ""


        llenarDatosFormulario(nombreTienda, nombreProducto)
        val botonCrear = findViewById<Button>(R.id.btn_createLugar)
        botonCrear.setOnClickListener {
            try {
                val nombreProductoInput =
                    findViewById<EditText>(R.id.input_nombreProducto).text.toString()
                val costoEntrada =
                    findViewById<EditText>(R.id.input_costoLugar).text.toString().toDouble()
                val disponible: Boolean = findViewById<Switch>(R.id.sw_disponible).isChecked

                val nuevoProducto = hashMapOf(
                    "nombreProd" to nombreProductoInput,
                    "costoEntrada" to costoEntrada,
                    "disponible" to disponible
                )

                // Primero encontramos el ID del documento de la tienda usando nombreTienda
                db.collection("tiendas")
                    .whereEqualTo("nombre", nombreTienda)
                    .get()
                    .addOnSuccessListener { documentos ->
                        if (documentos.documents.isNotEmpty()) {
                            val tiendaId = documentos.documents[0].id

                            // Luego, con el ID de la tienda, creamos un nuevo producto en la subcolección correspondiente
                            db.collection("tiendas").document(tiendaId)
                                .collection("productos")
                                .add(nuevoProducto)
                                .addOnSuccessListener {
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                                }
                        } else {
                            mostrarSnackBar("Tienda no encontrada" + nombreTienda)
                        }
                    }
                    .addOnFailureListener { e ->
                        mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                    }
            } catch (e: Exception) {
                mostrarSnackBar("Error, datos incorrectos: ${e.message}")
            }
        }


        val botonActualizarBDD = findViewById<Button>(R.id.btn_updateLugar)
        botonActualizarBDD.setOnClickListener {
            try {
                val nombreProductoInput =
                    findViewById<EditText>(R.id.input_nombreProducto).text.toString()
                val costoEntrada =
                    findViewById<EditText>(R.id.input_costoLugar).text.toString().toDouble()
                val disponible: Boolean = findViewById<Switch>(R.id.sw_disponible).isChecked

                // Primero, encontramos el ID de la tienda usando el nombreTienda
                db.collection("tiendas")
                    .whereEqualTo("nombre", nombreTienda)
                    .get()
                    .addOnSuccessListener { documentos ->
                        if (documentos.documents.isNotEmpty()) {
                            val tiendaId = documentos.documents[0].id

                            // Luego, usamos el ID de la tienda para encontrar y actualizar el producto
                            db.collection("tiendas").document(tiendaId)
                                .collection("productos")
                                .whereEqualTo("nombreProd", nombreProducto)
                                .get()
                                .addOnSuccessListener { documentosProducto ->
                                    if (documentosProducto.documents.isNotEmpty()) {
                                        val productoId = documentosProducto.documents[0].id

                                        db.collection("tiendas").document(tiendaId)
                                            .collection("productos").document(productoId)
                                            .update(
                                                "nombreProd", nombreProductoInput,
                                                "costoEntrada", costoEntrada,
                                                "disponible", disponible
                                            )
                                            .addOnSuccessListener {
                                                finish()
                                            }
                                    } else {
                                        mostrarSnackBar("Producto no encontrado")
                                    }
                                }
                                .addOnFailureListener { e ->
                                    mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                                }
                        } else {
                            mostrarSnackBar("Tienda no encontrada")
                        }
                    }
                    .addOnFailureListener { e ->
                        mostrarSnackBar("Error, datos incorrectos: ${e.message}")
                    }
            } catch (e: Exception) {
                mostrarSnackBar("Error, datos incorrectos: ${e.message}")
            }
        }


        if (nombreProducto.isNotEmpty()) {
            //ocultar boton crear
            botonCrear.visibility = Button.INVISIBLE
        } else {
            //ocultar boton actualizar
            botonActualizarBDD.visibility = Button.INVISIBLE
        }
    }

    fun llenarDatosFormulario(nombreTienda: String, nombreProducto: String) {
        if (nombreTienda.isNotEmpty() && nombreProducto.isNotEmpty()) {
            db.collection("tiendas").whereEqualTo("nombre", nombreTienda)
                .get()
                .addOnSuccessListener { documentosTienda ->
                    if (documentosTienda.documents.isNotEmpty()) {
                        val tienda = documentosTienda.documents[0]

                        db.collection("tiendas").document(tienda.id)
                            .collection("productos").whereEqualTo("nombreProd", nombreProducto)
                            .get()
                            .addOnSuccessListener { documentosProducto ->
                                if (documentosProducto.documents.isNotEmpty()) {
                                    val producto = documentosProducto.documents[0]

                                    val nombreProductoTuristico =
                                        findViewById<EditText>(R.id.input_nombreProducto)
                                    val costoEntrada = findViewById<EditText>(R.id.input_costoLugar)
                                    val disponible = findViewById<Switch>(R.id.sw_disponible)

                                    nombreProductoTuristico.setText(producto.getString("nombreProd"))
                                    costoEntrada.setText(
                                        producto.getDouble("costoEntrada").toString()
                                    )
                                    disponible.isChecked =
                                        producto.getBoolean("disponible") ?: false
                                }
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    // Log the error
                }
        }
    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_CrudLT),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}
