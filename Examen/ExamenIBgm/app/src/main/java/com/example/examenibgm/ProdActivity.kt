package com.example.examenibgm

import android.app.Activity
import android.content.DialogInterface
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class ProdActivity : AppCompatActivity() {

    val arregloProducto = BaseDatosMemoria.arregloProducto
    var idItemSeleccionado = 0
    val arregloProd = arrayListOf<Producto>()
    var idLugarSeleccionado = 0
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    //Logica Negocio
                    val data = result.data
                    "${data?.getStringExtra("nombreModificado")}"
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prod)

        val botonCrearLugarTuristico = findViewById<Button>(R.id.btn_crearProducto)
        botonCrearLugarTuristico.setOnClickListener {
            abrirActividadConParametros(
                AccesoDatosProducto::class.java,
                0
            )
        }
        llenarDatos()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(

                    AccesoDatosProducto::class.java,
                    idLugarSeleccionado+1
                )
                return true
            }
            R.id.mi_eliminar ->{
                //eliminar idItemSeleccionado del arreglo
                eliminarProducto()
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
        //Llenar las opciones de menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menuproducto, menu)
        //Obtener el id del array list seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
        idLugarSeleccionado = arregloProd[idItemSeleccionado].idProducto!!-1
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        id: Int
    ) {
        val intentExplicito = Intent(this, clase)
        //Enviar parametros
        //(aceptamos primitivas)
        intentExplicito.putExtra("idLugar", id)
        intentExplicito.putExtra("id", intent.getIntExtra("id", 0))

        //enviamos el intent con RESPUESTA
        //RECIBIMOS RESPUESTA
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }


    fun eliminarProducto(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { _, _ ->
                //Eliminar producto seleccionada
                try {
                    idLugarSeleccionado?.let { arregloProducto.removeAt(it) }
                    arregloProd.removeAt(idItemSeleccionado)
                    llenarDatos()
                    mostrarSnackBar("Eliminado con éxito")
                }catch (e: Exception){
                    mostrarSnackBar("Error al eliminar")
                }
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }


    fun llenarDatos(){
        val idPaisSelected = intent.getIntExtra("id", 0)
        arregloProd.clear()
        for (lugar in arregloProducto){
            if (lugar.idTienda == idPaisSelected){
                arregloProd.add(lugar)
            }
        }
        val nombrePais: String? = BaseDatosMemoria.arregloTienda[idPaisSelected-1].nombre

        findViewById<TextView>(R.id.tv_tiendaSelected).text = nombrePais
        val listView = findViewById<ListView>(R.id.lv_productos)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloProd
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
        )
            .setAction("Action", null).show()
    }
}