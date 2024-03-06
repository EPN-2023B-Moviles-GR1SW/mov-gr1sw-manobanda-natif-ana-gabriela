package com.example.exameniib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CRUDTienda : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var documentoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudtienda)

        db = Firebase.firestore

        val nombreTienda = intent.getStringExtra("nombre")

        val nombre = findViewById<EditText>(R.id.input_nombre_tienda)
        val direccion = findViewById<EditText>(R.id.input_direccion)
        val contacto = findViewById<EditText>(R.id.input_contacto)
        val fechaApertura = findViewById<EditText>(R.id.input_fechaApertura)

        llenarDatosFormulario(nombreTienda)

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener {

            val tienda = hashMapOf(
                "nombre" to nombre.text.toString(),
                "direccion" to direccion.text.toString(),
                "contacto" to contacto.text.toString(),
                "fechaApertura" to fechaApertura.text.toString()
            )

            db.collection("tiendas").add(tienda).addOnSuccessListener {
                    finish()
                }.addOnFailureListener {
                    // Log the error
                }
        }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD.setOnClickListener {
            documentoId?.let { id ->
                val tiendaActualizada = hashMapOf(
                    "nombre" to nombre.text.toString(),
                    "direccion" to direccion.text.toString(),
                    "contacto" to contacto.text.toString(),
                    "fechaApertura" to fechaApertura.text.toString()
                )

                db.collection("tiendas").document(id).update(tiendaActualizada as Map<String, Any>)
                    .addOnSuccessListener {
                        finish()
                    }.addOnFailureListener {
                        // Log the error
                    }
            }
        }

        if (nombreTienda != null) {
            botonCrearBDD.visibility = Button.INVISIBLE
        } else {
            botonActualizarBDD.visibility = Button.INVISIBLE
        }
    }

    fun llenarDatosFormulario(nombreTienda: String?) {
        if (nombreTienda != null) {
            db.collection("tiendas").whereEqualTo("nombre", nombreTienda).get()
                .addOnSuccessListener { documents ->
                    if (documents.documents.isNotEmpty()) {
                        val tienda = documents.documents[0]

                        val nombre = findViewById<EditText>(R.id.input_nombre_tienda)
                        val direccion = findViewById<EditText>(R.id.input_direccion)
                        val contacto = findViewById<EditText>(R.id.input_contacto)
                        val fechaApertura = findViewById<EditText>(R.id.input_fechaApertura)

                        nombre.setText(tienda.getString("nombre"))
                        direccion.setText(tienda.getString("direccion"))
                        contacto.setText(tienda.getString("contacto"))
                        fechaApertura.setText(tienda.getString("fechaApertura"))

                        // Guardar el ID del documento para usarlo durante la actualización
                        documentoId = tienda.id
                    }
                }.addOnFailureListener { exception ->
                    // Log the error
                }
        }
    }
}
