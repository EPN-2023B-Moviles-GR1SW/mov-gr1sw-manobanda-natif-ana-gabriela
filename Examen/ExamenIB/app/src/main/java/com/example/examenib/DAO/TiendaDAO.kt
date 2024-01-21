package com.example.examenib.DAO

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.examenib.BaseDeDatos.BaseDatosMemoria
import com.example.examenib.entidades.Producto
import com.example.examenib.entidades.Tienda

class TiendaDAO {

    // Reglas de negocio
    fun getById(id: Int): Tienda? {
        var tiendaEncontrada: Tienda? = null
        getAll().forEach { tienda: Tienda ->
            if (tienda.id == id) tiendaEncontrada = tienda
        }
        return tiendaEncontrada;
    }

    fun getAll(): ArrayList<Tienda> {
        return BaseDatosMemoria.listaDeTiendas;
    }

    fun create(tienda: Tienda) {
        val listaTienda = getAll()
        if (listaTienda.isEmpty()) {
            tienda.id = 0
        } else {
            tienda.id = listaTienda.last().id?.plus(1)!!
        }
        listaTienda.add(tienda)
    }

    fun update(tiendaActualizada: Tienda) {
        val listaTienda = getAll()
        listaTienda.forEachIndexed { index, tienda ->
            if (tienda.id == tiendaActualizada.id) {
                listaTienda[index] = tiendaActualizada
                return
            }
        }
    }

    fun deleteById(id: Int): Boolean {
        val productoDAO = ProductoDAO()
        getAll().forEach { tienda: Tienda ->
            if (tienda.id == id) {
                tienda.productos.forEach { producto: Producto ->
                    productoDAO.deleteById(producto.id!!)
                }
            }
        }
        return getAll().removeIf { tienda -> (tienda.id == id) }
    }
}