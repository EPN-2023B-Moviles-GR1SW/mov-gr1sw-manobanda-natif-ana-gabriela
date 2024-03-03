package com.example.proyectoiib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonAcceso = findViewById<Button>(R.id.btn_ingresar)
        botonAcceso.setOnClickListener {
            irAActividad(AccesoActivity::class.java)
        }
    }

    private fun irAActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}