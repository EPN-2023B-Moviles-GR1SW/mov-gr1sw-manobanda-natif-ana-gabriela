package com.example.proyectoiib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        auth = FirebaseAuth.getInstance()

        val correo = findViewById<EditText>(R.id.pt_ra_correo).text
        val password = findViewById<EditText>(R.id.p_ra_passwd).text
        val nombreUsuario = findViewById<EditText>(R.id.pt_ra_username).text

        val registrarNuevoUsuario = findViewById<Button>(R.id.btn_ra_registrarse)
        val btnRegresar = findViewById<Button>(R.id.btn_accesoDesdeRegistrar)

        registrarNuevoUsuario.setOnClickListener {
            if (correo.isNotEmpty() && password.isNotEmpty() && nombreUsuario.isNotEmpty()) {
                registrarNuevoUsuario(
                    correo.toString(), password.toString(), nombreUsuario.toString()
                )
            }
        }
        btnRegresar.setOnClickListener {
            val intent = Intent(this, AccesoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registrarNuevoUsuario(correo: String, password: String, nombreUsuario: String) {
        auth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val userID = auth.currentUser!!.uid
                agregarUsuario(userID, nombreUsuario)
            } else {
                showAlert()
            }
        }
    }

    private fun agregarUsuario(userID: String, nombreUsuario: String) {
        val firestore = Firebase.firestore
        val documentoUsuario = firestore.collection("usuarios").document(userID)
        val nuevosDatosUsuario = hashMapOf(
            "username" to nombreUsuario
        )
        documentoUsuario.set(nuevosDatosUsuario).addOnSuccessListener {
            finish()
        }.addOnFailureListener { e ->
            Log.w("Error firestore", "Error al crear la colección", e)
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error de autenticación de usuario")
        builder.setPositiveButton("aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}