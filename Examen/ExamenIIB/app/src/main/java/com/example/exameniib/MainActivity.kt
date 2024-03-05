package com.example.exameniib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonPais = findViewById<Button>(R.id.btn_Tienda)
        botonPais.setOnClickListener {
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