import Core.AccesoDatosProducto
import Core.AccesoDatosTienda
import Operaciones.MenuProducto
import Operaciones.MenuTienda
import java.util.*

fun main() {
    val accesoDatosTienda = AccesoDatosTienda(System.getProperty("user.dir") + "\\src\\main\\kotlin\\archivos\\Tienda.txt")
    val accesoDatosProducto =
        AccesoDatosProducto(System.getProperty("user.dir") + "\\src\\main\\kotlin\\archivos\\Productos.txt")

    val menuTienda = MenuTienda(accesoDatosTienda)
    val menuProducto = MenuProducto(accesoDatosProducto, accesoDatosTienda)

    val scanner = Scanner(System.`in`)
    var opcion: String? = ""

    while (opcion != null) {
        println("\n------------------ MENÚ PRINCIPAL ------------------ ")
        println("1. Operaciones de Tienda")
        println("2. Operaciones de Producto")
        println("3. Salir")
        print("\nSeleccione una opción: ")


        val input = readLine()?.trim()

        if (input.isNullOrEmpty()) {
            println("\nPor favor, seleccione una opción.")
            continue
        }
        opcion = input

        when (opcion) {
            "1" -> {
                while (true) {
                    val opcionTienda = menuTienda.mostrarMenu()
                    if (opcionTienda == 5) break
                    menuTienda.ejecutarOpcion(opcionTienda)
                }
            }
            "2" -> {
                while (true) {
                    val opcionProducto = menuProducto.mostrarMenu()
                    if (opcionProducto == 6) break
                    menuProducto.ejecutarOpcion(opcionProducto)
                }
            }
            "3" -> {
                println("Saliendo de la aplicación.")
                return
            }
            else -> println("Opción no válida. Inténtelo nuevamente.")
        }
    }
}