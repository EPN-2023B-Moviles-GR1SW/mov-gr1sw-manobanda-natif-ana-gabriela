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
import android.widget.TextView
import com.example.exameniib.models.Producto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot

class ProductoActivity : AppCompatActivity() {

    val query: Query? = null
    val arregloProducto = arrayListOf<Producto>()
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Producto>

    private val db = FirebaseFirestore.getInstance()

    private var productoSeleccionado: Producto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        val botonCrearProducto = findViewById<Button>(R.id.btn_crearLugar)
        botonCrearProducto.setOnClickListener {
            abrirActividadConParametros(
                CRUDProducto::class.java,
                intent.getStringExtra("nombre") ?: "",
                productoSeleccionado?.nombreProd ?: ""
            )
        }
        // Configurando el list view
        listView = findViewById<ListView>(R.id.lv_lugares)
        adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, arregloProducto
        )
        listView.adapter = adaptador
        llenarDatos()

        registerForContextMenu(listView)
    }

    private fun llenarDatos() {
        val nombreTienda = intent.getStringExtra("nombre") ?: ""
        val nombreTienda2 = findViewById<TextView>(R.id.tv_tiendaSelected)
        nombreTienda2.text = nombreTienda

        db.collection("tiendas").whereEqualTo("nombre", nombreTienda).get()
            .addOnSuccessListener { documentos ->
                if (documentos.documents.isNotEmpty()) {
                    val idTienda = documentos.documents[0].id
                    obtenerProductos(idTienda)
                } else {
                    Log.e("Firebase", "No se encontró la tienda con nombre: $nombreTienda")
                }
            }.addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }

    private fun obtenerProductos(idTienda: String) {
        db.collection("tiendas").document(idTienda).collection("productos").get()
            .addOnSuccessListener { documentos ->
                arregloProducto.clear()
                for (documento in documentos) {
                    documento.id
                    añadirAProducto(documento)
                }
                adaptador.notifyDataSetChanged()
            }.addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }

    fun añadirAProducto(producto: QueryDocumentSnapshot) {
        val nuevoProducto = Producto(
            producto.data["nombreProd"] as String?,
            producto.data["costoEntrada"] as Number?,
            producto.data["disponible"] as Boolean?,
        )
        Log.i("NuevoProducto", "Nuevo producto: $nuevoProducto")
        arregloProducto.add(nuevoProducto)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombre = intent.getStringExtra("nombre") ?: ""
        val nombreProducto =
            productoSeleccionado?.nombreProd ?: return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(
                    CRUDProducto::class.java, nombre, nombreProducto
                )
                true
            }

            R.id.mi_eliminar -> {
                eliminarProducto(nombre, nombreProducto)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menuproducto, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        productoSeleccionado = listView.adapter.getItem(info.position) as Producto
    }


    fun eliminarProducto(nombreTienda: String, nombreProd: String) {
        // Primero, obtenemos el ID de la tienda usando el nombreTienda
        db.collection("tiendas").whereEqualTo("nombre", nombreTienda).get()
            .addOnSuccessListener { documentosTienda ->
                if (documentosTienda.documents.isNotEmpty()) {
                    val idTienda = documentosTienda.documents[0].id

                    // Luego, obtenemos el ID del Producto usando el nombreLT
                    db.collection("tiendas").document(idTienda).collection("productos")
                        .whereEqualTo("nombreProd", nombreProd).get()
                        .addOnSuccessListener { documentosProducto ->
                            if (documentosProducto.documents.isNotEmpty()) {
                                val idProducto = documentosProducto.documents[0].id

                                // Ahora que tenemos ambos ID, podemos eliminar el producto
                                db.collection("tiendas").document(idTienda).collection("productos")
                                    .document(idProducto).delete().addOnSuccessListener {
                                        Log.i("Firebase", "Producto eliminado con éxito!")
                                        llenarDatos() // Recargar la lista luego de eliminar un producto
                                    }.addOnFailureListener { exception ->
                                        Log.e(
                                            "Firebase", "Error eliminando el producto: ", exception
                                        )
                                    }
                            } else {
                                Log.e(
                                    "Firebase",
                                    "No se encontró el producto con nombreProd: $nombreProd"
                                )
                            }
                        }.addOnFailureListener { exception ->
                            Log.e("Firebase", "Error obteniendo documentos: ", exception)
                        }
                } else {
                    Log.e("Firebase", "No se encontró la tienda con nombre: $nombreTienda")
                }
            }.addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo documentos: ", exception)
            }
    }


    fun abrirActividadConParametros(clase: Class<*>, nombreTienda: String, nombreProducto: String) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("nombreT", nombreTienda)
        intentExplicito.putExtra("nombreP", nombreProducto)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }

    override fun onResume() {
        super.onResume()
        llenarDatos()
    }
}