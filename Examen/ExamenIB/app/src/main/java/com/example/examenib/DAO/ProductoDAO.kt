package com.example.examenib.DAO

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.examenib.BaseDeDatos.BaseDatosMemoria
import com.example.examenib.entidades.Producto

class ProductoDAO {
    // Reglas de negocio
    fun getById(id: Int): Producto? {
        var productoEncontrado: Producto? = null
        getAll().forEach { producto: Producto ->
            if (producto.id == id) productoEncontrado = producto
        }
        return productoEncontrado
    }

    fun getAll(): ArrayList<Producto> {
        return BaseDatosMemoria.listaDeProductos;
    }

    fun create(producto: Producto) {
        val listaProducto = getAll()
        if (listaProducto.isEmpty()) {
            producto.id = 0
        } else {
            producto.id = listaProducto.last().id?.plus(1)!!
        }
        listaProducto.add(producto)
        producto.tienda.productos.add(producto)
    }

    fun update(productoActualizado: Producto) {
        val listaProducto = getAll()
        listaProducto.forEachIndexed { index, producto ->
            if (producto.id == productoActualizado.id) {
                listaProducto[index] = productoActualizado
                return
            }
        }
    }

    fun deleteById(id: Int) {
        val producto = getById(id)
        if (producto!=null){
            producto.tienda.productos.remove(producto)
            getAll().remove(producto)
        }
    }
}