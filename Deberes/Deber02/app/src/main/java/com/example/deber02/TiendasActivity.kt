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
import com.example.deber02.bd.BaseDeDatos
import com.example.deber02.crud.CrudTienda
import com.example.deber02.entidades.Tienda

class TiendasActivity : AppCompatActivity() {

    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiendas)
        val botonCrearTienda = findViewById<Button>(R.id.btn_crear_tienda)
        botonCrearTienda.setOnClickListener {
            irActividad(CrudTienda::class.java)
        }
        actualizarLista()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val idReal = tiendaSeleccionada?.id ?: return super.onContextItemSelected(item)

        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(
                    CrudTienda::class.java,
                    idReal
                )
                return true
            }
            R.id.mi_eliminar ->{
                //eliminar idItemSeleccionado del arreglo
                BaseDeDatos.tablaTienda!!.eliminarTiendaFormulario(
                    idReal
                )
                actualizarLista()
                return true
            }
            R.id.mi_verProductos ->{
                abrirActividadConParametros(
                    ProdActivity::class.java,
                    idReal
                )
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    var tiendaSeleccionada: Tienda? = null

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        tiendaSeleccionada = listView.adapter.getItem(info.position) as Tienda
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
        // this.startActivity()
    }

    fun abrirActividadConParametros(clase: Class<*>, id: Int) {
        val intentExplicito = Intent(this, clase)
        // Enviar parámetros
        intentExplicito.putExtra("id", id)
        // Iniciar la actividad
        startActivity(intentExplicito)
    }

    fun actualizarLista(){
        val arregloTienda = BaseDeDatos.tablaTienda!!.consultarTiendas()
        //Adaptador
        listView = findViewById<ListView>(R.id.lv_tiendas)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloTienda
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }
    override fun onResume() {
        super.onResume()
        actualizarLista()
    }
}