package com.example.exameniib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.exameniib.models.Tienda
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TiendasActivity : AppCompatActivity() {
    val query: Query? = null
    val arreglo = arrayListOf<Tienda>()
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Tienda>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiendas)

        val botonCrearTienda = findViewById<Button>(R.id.btn_crear_pais)
        botonCrearTienda.setOnClickListener {
            irActividad(CRUDTienda::class.java)
        }
        // Configurando el list view
        listView = findViewById<ListView>(R.id.lv_paises)
        adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, arreglo
        )
        listView.adapter = adaptador
        consultarTiendas(adaptador)
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    fun consultarTiendas(adaptador: ArrayAdapter<Tienda>) {
        val db = Firebase.firestore
        val tiendasRefUnico = db.collection("tiendas")
        tiendasRefUnico.get().addOnSuccessListener { // it => eso (lo que llegue)
            limpiarArreglo() // Limpia el arreglo aquí
            for (tienda in it) {
                tienda.id
                anadirAArregloTienda(tienda)
            }
            adaptador.notifyDataSetChanged() // Notifica los cambios aquí
        }.addOnFailureListener {
            // Errores
        }
    }


    fun eliminarTienda(nombre: String) {
        val db = Firebase.firestore
        val tiendasRefUnico = db.collection("tiendas")

        tiendasRefUnico.whereEqualTo("nombre", nombre).get().addOnSuccessListener { result ->
            if (result.documents.isNotEmpty()) {
                // Obtener el ID del primer documento en los resultados y eliminarlo
                val documentId = result.documents[0].id
                tiendasRefUnico.document(documentId).delete().addOnSuccessListener {
                    Log.i("firebase-firestore", "DocumentSnapshot successfully deleted!")
                }.addOnFailureListener {
                    Log.i("firebase-firestore", "Error deleting document")
                }
            } else {
                Log.i("firebase-firestore", "No document found with the name: $nombre")
            }
        }.addOnFailureListener { exception ->
            Log.i("firebase-firestore", "Error getting documents: ", exception)
        }
    }


    fun limpiarArreglo() {
        arreglo.clear()
    }

    fun anadirAArregloTienda(
        tienda: QueryDocumentSnapshot
    ) {
        val nuevaTienda = Tienda(
            tienda.data.get("nombre") as String?,
            tienda.data.get("idioma") as String?,
            tienda.data.get("moneda") as String?,
            tienda.data.get("precioDolar") as Number?
        )
        arreglo.add(nuevaTienda)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombreReal = tiendaSeleccionada?.nombre ?: return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(
                    CRUDTienda::class.java, nombreReal
                )
                return true
            }

            R.id.mi_eliminar -> {
                //eliminar idItemSeleccionado del arreglo
                eliminarTienda(nombreReal)
                consultarTiendas(adaptador)
                return true
            }

            R.id.mi_verProductos -> {
                abrirActividadConParametros(
                    ProductoActivity::class.java, nombreReal
                )
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    var tiendaSeleccionada: Tienda? = null

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menutienda, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        tiendaSeleccionada = listView.adapter.getItem(info.position) as Tienda
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }

    fun abrirActividadConParametros(clase: Class<*>, nombre: String) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("nombre", nombre)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }

    override fun onResume() {
        super.onResume()
        consultarTiendas(adaptador)
    }
}