package com.example.b2023_gr1sw_agmn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material3.Snackbar
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""

    fun mostrarSnackbar(texto:String){
        textoGlobal = textoGlobal + " " + texto
        Snackbar
            .make(
                findViewById(R.id.cl_ciclo_vida),  // view
                textoGlobal, // texto
                Snackbar.LENGTH_INDEFINITE //tiempo
             )
            .show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("OnCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("onStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackbar("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("onRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackbar("onPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackbar("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            // GUARDAR LAS VARIABLES
            // SOLO VARIABLES PRIMITIVAS

            putString("textoGuardado", textoGlobal)
            //putInt("numeroGuardado", numero)
        }
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // RECUPERAR LAS VARIABLES
        // SOLO PRIMITIVOS
        val textoRecuperado:String? = savedInstanceState.getString("textoGuardado")
        // val textoRecuperado:Int? = savedInstanceState.getInt("numeroGuardado")
        if(textoRecuperado != null){
            mostrarSnackbar(textoRecuperado)
            textoGlobal = textoRecuperado
        }
    }

    //Mostrar elementos en pantalla en una lista

}