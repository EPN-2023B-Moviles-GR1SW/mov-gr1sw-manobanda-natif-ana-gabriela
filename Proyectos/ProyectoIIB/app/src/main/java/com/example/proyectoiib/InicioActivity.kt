package com.example.proyectoiib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InicioActivity : AppCompatActivity() {

    private var authActual = FirebaseAuth.getInstance().currentUser
    val arregloLibros = arrayListOf<Libro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val anadirButton = findViewById<Button>(R.id.btn_ia_anadir)

        anadirButton.setOnClickListener {
            irAActividad(AnadirActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        inicializarRecyclerView()
    }

    private fun inicializarRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_ia_librosLeidos)

        val adaptador = RecyclerViewAdaptorLibro(
            this, arregloLibros, recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()

        consultarLibros(adaptador)
    }

    public fun consultarLibros(adaptador: RecyclerViewAdaptorLibro) {
        val db = Firebase.firestore

        val bibliotecaPersonal =
            db.collection("usuarios").document(authActual!!.uid).collection("libros")

        limpiarArreglo()
        adaptador.notifyDataSetChanged()

        bibliotecaPersonal.get().addOnSuccessListener {
            for (librosBP in it) {
                anadirAAregloLibro(librosBP, librosBP.id)
            }
            adaptador.notifyDataSetChanged()
        }.addOnFailureListener {

        }
    }

    private fun anadirAAregloLibro(libro: QueryDocumentSnapshot, idLibro: String) {
        val nuevoLibro = Libro(
            idLibro,
            libro.data.get("tipo").toString(),
            libro.data.get("titulo").toString(),
            libro.data.get("autor").toString(),
            libro.data.get("genero").toString(),
            libro.data.get("urlLibro").toString(),
            libro.data.get("urlImagen").toString(),

            )
        if (nuevoLibro.tipo == "Virtual") {
            nuevoLibro.setUrlLibroString(libro.data.get("urlLibro").toString())
        }
        this.arregloLibros.add(nuevoLibro)
    }

    private fun limpiarArreglo() {
        this.arregloLibros.clear()
    }

    private fun irAActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}