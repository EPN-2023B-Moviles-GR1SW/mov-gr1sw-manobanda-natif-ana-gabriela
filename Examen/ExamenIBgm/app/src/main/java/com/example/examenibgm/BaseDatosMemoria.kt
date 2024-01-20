package com.example.examenibgm

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
                    formatoFecha.parse("28/12/2020"),
                    "Av. 10 de agosto",
                    "0998314961",
                    )
            )
            arregloTienda.add(
                Tienda(
                    2,
                    "Tienda 2",
                    formatoFecha.parse("28/12/2020"),
                    "Av. naciones unidas",
                    "0995752038",
                    )
            )
            arregloTienda.add(
                Tienda(
                    3,
                    "Tienda 3",
                    formatoFecha.parse("28/12/2020"),
                    "Av. mariscal sucre",
                    "0998314961",
                )
            )
        }
        init {
            arregloProducto.add(
                Producto(
                    1,
                    "P1",
                    100.0,
                    25,
                    false,
                    1,
                )
            )
            arregloProducto.add(
                Producto(
                    2,
                    "P2",
                    50.0,
                    35,
                    true,
                    1,
                )
            )
            arregloProducto.add(
                Producto(
                    3,
                    "P3",
                    20.0,
                    45,
                    true,
                    1,
                )
            )
            arregloProducto.add(
                Producto(
                    4,
                    "P1",
                    100.0,
                    55,
                    true,
                    2,
                )
            )
            arregloProducto.add(
                Producto(
                    5,
                    "P2",
                    50.0,
                    65,
                    true,
                    2,
                )
            )
            arregloProducto.add(
                Producto(
                    6,
                    "P3",
                    20.0,
                    75,
                    true,
                    2,
                )
            )
            arregloProducto.add(
                Producto(
                    7,
                    "P4",
                    100.0,
                    85,
                    true,
                    3,
                )
            )
            arregloProducto.add(
                Producto(
                    8,
                    "P5",
                    50.0,
                    95,
                    true,
                    3,
                )
            )
            arregloProducto.add(
                Producto(
                    9,
                    "P6",
                    20.0,
                    105,
                    true,
                    3,
                )
            )
        }
    }
}