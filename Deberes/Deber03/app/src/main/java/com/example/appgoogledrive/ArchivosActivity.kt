package com.example.appgoogledrive

import ArchivosAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ArchivosActivity : AppCompatActivity() {
    private var archivosItems: MutableList<ArchivosItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archivos)

        var list = findViewById<RecyclerView>(R.id.recyclerView)
        iniData()

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = ArchivosAdapter(this, archivosItems){
            val toast = Toast.makeText(applicationContext, it.name, Toast.LENGTH_LONG)
            toast.show()
        }

        val btn_sugerido = findViewById<TextView>(R.id.tv_sugerido)
        btn_sugerido.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun iniData(){
        val image =  resources.obtainTypedArray(R.array.image2)
        val name = resources.getStringArray(R.array.name1)
        val type = resources.obtainTypedArray(R.array.type)
        val description = resources.getStringArray(R.array.description)
        val imgDestacada = resources.obtainTypedArray(R.array.imgDestacada)
        archivosItems.clear()
        for (i in name.indices){
            archivosItems.add(ArchivosItem(name[i], type.getResourceId(i, 0), image.getResourceId(i, 0), description[i], imgDestacada.getResourceId(i, 1)))
        }
    }
}