package com.example.deber02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.deber02.bd.BaseDeDatos
import com.example.deber02.bd.SqliteHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BaseDeDatos.tablaTienda = SqliteHelper(this)
        BaseDeDatos.tablaProducto = SqliteHelper(this)

        val botonTienda = findViewById<Button>(R.id.btn_Tienda)
        botonTienda
            .setOnClickListener {
                irActividad(
                    TiendasActivity::class.java
                )
            }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        // NO RECIBIMOS RESPUESTA
        startActivity(intent)
        // this.startActivity()
    }
}