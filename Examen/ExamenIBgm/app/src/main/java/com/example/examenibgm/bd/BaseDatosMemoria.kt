package com.example.examenibgm.bd

import com.example.examenibgm.entidades.Producto
import com.example.examenibgm.entidades.Tienda
import java.text.SimpleDateFormat

class BaseDatosMemoria{
    companion object {
        val arregloTienda = arrayListOf<Tienda>()
        val arregloProducto = arrayListOf<Producto>()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        init {
            arregloTienda.add(
                Tienda(
                    1,
                    "Tienda 1",
                    formatoFecha.parse("18/12/2020"),
                    "Av. 10 de agosto",
                    "0998314961",
                    )
            )
            arregloTienda.add(
                Tienda(
                    2,
                    "Tienda 2",
                    formatoFecha.parse("10/10/2021"),
                    "Av. naciones unidas",
                    "0995752038",
                    )
            )
            arregloTienda.add(
                Tienda(
                    3,
                    "Tienda 3",
                    formatoFecha.parse("09/04/2020"),
                    "Av. mariscal sucre",
                    "0998314961",
                )
            )
        }
        init {
            arregloProducto.add(
                Producto(
                    1,
                    "Producto 1",
                    100.0,
                    25,
                    false,
                    1,
                )
            )
            arregloProducto.add(
                Producto(
                    2,
                    "Producto 2",
                    50.0,
                    35,
                    true,
                    1,
                )
            )
            arregloProducto.add(
                Producto(
                    3,
                    "Producto 3",
                    20.0,
                    45,
                    true,
                    1,
                )
            )
            arregloProducto.add(
                Producto(
                    4,
                    "Producto 1",
                    100.0,
                    55,
                    true,
                    2,
                )
            )
            arregloProducto.add(
                Producto(
                    5,
                    "Producto 2",
                    50.0,
                    65,
                    true,
                    2,
                )
            )
            arregloProducto.add(
                Producto(
                    6,
                    "Producto 3",
                    20.0,
                    75,
                    true,
                    2,
                )
            )
            arregloProducto.add(
                Producto(
                    7,
                    "Producto 1",
                    100.0,
                    85,
                    true,
                    3,
                )
            )
            arregloProducto.add(
                Producto(
                    8,
                    "Producto 2",
                    50.0,
                    95,
                    true,
                    3,
                )
            )
            arregloProducto.add(
                Producto(
                    9,
                    "Producto 3",
                    20.0,
                    105,
                    true,
                    3,
                )
            )
        }
    }
}