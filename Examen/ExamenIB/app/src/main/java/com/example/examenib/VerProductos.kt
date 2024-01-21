package com.example.examenib

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
import androidx.appcompat.app.AlertDialog
import com.example.examenib.DAO.ProductoDAO
import com.example.examenib.DAO.TiendaDAO
import com.example.examenib.entidades.Producto
import com.example.examenib.entidades.Tienda
import com.google.android.material.snackbar.Snackbar

class VerProductos : AppCompatActivity() {
    var arregloProductos = arrayListOf<Producto>()
    var tienda: Tienda = Tienda()
    var posicionItemSeleccionado = 0
    var idProductoSeleccionado = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_productos)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Productos
        tienda = TiendaDAO().getById(id)!!
        arregloProductos = tienda.productos

        val nombreTienda = findViewById<TextView>(R.id.tv_nombre_tienda)
        nombreTienda.setText("${tienda.nombreTienda}")

        listView = findViewById<ListView>(R.id.lv_ver_producto)
        adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            arregloProductos
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonCrearProducto = findViewById<Button>(R.id.btn_crear_producto)
        botonCrearProducto
            .setOnClickListener {
                crearProducto()
            }
        registerForContextMenu(listView)
    }

    fun crearProducto() {
        val producto =
            Producto(
                null,
                0,
                "Julian Tufiño",
                2,
                20.0,
                false,
                tienda
            )
        ProductoDAO().create(producto)
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_producto, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Condominio en la posición seleccionada
        val productoSeleccionado = arregloProductos.get(posicion)
        // Obtener el id del Condominio seleccionado
        idProductoSeleccionado = productoSeleccionado.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_producto -> {
                irActividadConId(EditarProducto::class.java, idProductoSeleccionado)
                return true
            }

            R.id.mi_eliminar_producto -> {
                abrirDialogo()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividadConId(
        clase: Class<*>,
        id: Int
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("id", id);
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_ver_producto),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar"
        ) { dialog, which ->
            ProductoDAO().deleteById(idProductoSeleccionado)
            mostrarSnackbar("Elemento id:${idProductoSeleccionado} eliminado")
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
    }
}