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
import androidx.appcompat.app.AlertDialog
import com.example.examenib.DAO.TiendaDAO
import com.example.examenib.entidades.Tienda
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate

class VerTiendas : AppCompatActivity() {
    val arregloTiendas = TiendaDAO().getAll()
    var posicionItemSeleccionado = 0
    var idTiendaSeleccionada = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Tienda>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_tiendas)
        listView = findViewById<ListView>(R.id.lv_condominio_ver)
        adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            arregloTiendas
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonCrearCondominio = findViewById<Button>(R.id.btn_crear_condominio)
        botonCrearCondominio
            .setOnClickListener {
                crearTienda()
            }
        registerForContextMenu(listView)
    }

    fun crearTienda() {
        val tienda =
            Tienda(
                null,
                "Julian Tufiño",
                "Llano Chico",
                LocalDate.now(),
                10,
                "999999999"
            )
        TiendaDAO().create(tienda)
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
        inflater.inflate(R.menu.menu_tienda, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Condominio en la posición seleccionada
        val condominioSeleccionado = arregloTiendas.get(posicion)
        // Obtener el id del Condominio seleccionado
        idTiendaSeleccionada = condominioSeleccionado.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_condominio -> {
                irActividadConId(EditarTienda::class.java, idTiendaSeleccionada)
                return true
            }

            R.id.mi_eliminar_condominio -> {
                abrirDialogo()
                return true
            }

            R.id.mi_ver_departamentos -> {
                irActividadConId(VerProductos::class.java, idTiendaSeleccionada)
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
        intent.putExtra("id", id)
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_ver_tienda),
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
            TiendaDAO().deleteById(idTiendaSeleccionada)
            mostrarSnackbar("Elemento id:${idTiendaSeleccionada} eliminado")
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