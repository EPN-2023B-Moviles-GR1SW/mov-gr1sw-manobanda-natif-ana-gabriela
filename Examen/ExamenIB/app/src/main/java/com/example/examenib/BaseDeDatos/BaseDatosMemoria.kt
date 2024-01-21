package com.example.examenib.BaseDeDatos

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.examenib.entidades.Producto
import com.example.examenib.entidades.Tienda
import java.time.LocalDate

class BaseDatosMemoria{

    companion object {
    var listaDeTiendas: ArrayList<Tienda> = arrayListOf()
    var listaDeProductos: ArrayList<Producto> = arrayListOf()
    init {
        listaDeProductos.add(
            Producto(
                0,
                1,
                "César Tufiño",
                2,
                15.0,
                false
            )
        )
        listaDeProductos.add(
            Producto(
                1,
                2,
                "Cecibel Oñate",
                3,
                20.0,
                true
            )
        )
        listaDeProductos.add(
            Producto(
                2,
                1,
                "Ricardo Franco",
                3,
                30.0,
                true
            )
        )

        listaDeTiendas.add(
            Tienda(
                0,
                "La Primavera GABRIELA",
                "De Los Mortiños Y Av. Eloy Alfaro, Quito",
                LocalDate.of(2001, 12, 13),
                10,
                "998314961"
            )
        )
        listaDeTiendas.add(
            Tienda(
                1,
                "Los Pinos",
                "Av. 17 de Septiembre Y Carapungo, Quito",
                LocalDate.of(2003, 10, 17),
                25,
                "999999999"
            )
        )
        listaDeTiendas.get(0).productos.add(listaDeProductos.get(0))
        listaDeTiendas.get(0).productos.add(listaDeProductos.get(1))
        listaDeTiendas.get(1).productos.add(listaDeProductos.get(2))
        listaDeProductos.get(0).tienda = listaDeTiendas.get(0)
        listaDeProductos.get(1).tienda = listaDeTiendas.get(0)
        listaDeProductos.get(2).tienda = listaDeTiendas.get(1)
    }
}
}