package com.example.examenibgm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity()  {
    val arregloTienda = BaseDatosMemoria.arregloTienda
    var idItemSeleccionado = 0
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    "${data?.getStringExtra("nombreModificado")}"
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonCrearTienda = findViewById<Button>(R.id.btn_crear_tienda)
        botonCrearTienda.setOnClickListener {
            irActividad(AccesoDatosTienda::class.java)
        }

        //Adaptador
        val listView = findViewById<ListView>(R.id.lv_tiendas)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloTienda
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(
                    AccesoDatosTienda::class.java,
                    idItemSeleccionado+1
                )
                return true
            }
            R.id.mi_eliminar ->{
                eliminarTienda(idItemSeleccionado)
                return true
            }
            R.id.mi_verProductos ->{
                abrirActividadConParametros(
                    ProdActivity::class.java,
                    idItemSeleccionado+1
                )
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //Obtener el id del array list seleccionado.
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        id: Int
    ) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("id", id)
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    fun eliminarTienda( id: Int){
        arregloTienda.removeAt(id)
        //Adaptador
        val listView = findViewById<ListView>(R.id.lv_tiendas)
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
        val listView = findViewById<ListView>(R.id.lv_tiendas)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloTienda
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }
}