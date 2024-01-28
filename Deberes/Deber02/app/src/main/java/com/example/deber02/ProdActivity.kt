package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.deber02.bd.BaseDeDatos
import com.example.deber02.crud.CrudProducto
import com.example.deber02.entidades.Producto
import com.google.android.material.snackbar.Snackbar

class ProdActivity : AppCompatActivity() {

    var idItemSeleccionado = 0
    val arregloProducto = arrayListOf<Producto>()
    var idProductoSeleccionado = 0
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prod)

        val botonCrearProducto = findViewById<Button>(R.id.btn_crearProducto)
        botonCrearProducto.setOnClickListener {
            abrirActividadConParametros(
                CrudProducto::class.java,
                0
            )
        }
        llenarDatos()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val idReal = productoSeleccionado?.idProducto ?: return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(
                    CrudProducto::class.java,
                    idReal
                )
                return true
            }
            R.id.mi_eliminar -> {
                BaseDeDatos.tablaProducto!!.eliminarProductoFormulario(
                    idReal
                )
                llenarDatos()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    var productoSeleccionado: Producto? = null
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenar las opciones de menú
        val inflater = menuInflater
        inflater.inflate(R.menu.menuproducto, menu)
        //Obtener el id del array list seleccionado.
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        productoSeleccionado = listView.adapter.getItem(info.position) as Producto
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        id: Int
    ) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idProducto", id)
        intentExplicito.putExtra("id", intent.getIntExtra("id", 0))
        startActivity(intentExplicito)
    }

    fun llenarDatos() {
        val idTienda = intent.getIntExtra("id", 0)
        val arregloProducto = BaseDeDatos.tablaProducto!!.consultarProductosPorTienda(idTienda)
        val tiendaEncontrada = BaseDeDatos.tablaTienda!!.consultarTiendaPorID(idTienda)
        val nombreTienda = findViewById<TextView>(R.id.tv_tiendaSelected)
        nombreTienda.text = tiendaEncontrada.nombre

        listView = findViewById<ListView>(R.id.lv_productos)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloProducto
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        llenarDatos()
    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.cl_Prod),
            texto,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null).show()
    }
}
