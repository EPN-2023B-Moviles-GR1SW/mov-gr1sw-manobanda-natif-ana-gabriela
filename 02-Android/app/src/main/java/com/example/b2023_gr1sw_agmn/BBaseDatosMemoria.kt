package com.example.b2023_gr1sw_agmn

class BBaseDatosMemoria {
    // EMPEZAR EL COMPANION OBJECT

    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "Ana", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2, "Gabriela", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "Fernanda", "c@c.com")
                )
        }
    }
}