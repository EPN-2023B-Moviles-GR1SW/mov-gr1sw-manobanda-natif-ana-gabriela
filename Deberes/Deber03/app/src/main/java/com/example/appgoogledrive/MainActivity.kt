package com.example.appgoogledrive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var unidadItems: MutableList<UnidadItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list = findViewById<RecyclerView>(R.id.recyclerView)
        iniData()

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = UnidadAdapter(this, unidadItems){
            val toast = Toast.makeText(applicationContext, it.name, Toast.LENGTH_LONG)
            toast.show()
        }

        val btn_llamadas = findViewById<TextView>(R.id.tv_actividad)
        btn_llamadas.setOnClickListener {
            val intent = Intent(this, ArchivosActivity::class.java)
            startActivity(intent)
        }
    }

    private fun iniData(){
        val image =  resources.obtainTypedArray(R.array.image)
        val name = resources.getStringArray(R.array.name)
        val status = resources.getStringArray(R.array.status)
        unidadItems.clear()
        for (i in name.indices){
            unidadItems.add(UnidadItem(name[i], status[i], image.getResourceId(i, 0)))
        }
    }
}