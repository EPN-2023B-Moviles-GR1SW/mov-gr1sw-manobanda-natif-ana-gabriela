package repositories

import models.Tienda
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TiendaRepository(private val fileName: String) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun guardarTienda(tienda: Tienda) {
        val registro = "${tienda.id},${tienda.nombre}, ${dateFormat.format(tienda.fechaApertura)}," +
                "${tienda.direccion},${tienda.numeroEmpleados}\n"
        File(fileName).appendText(registro)
    }

    fun obtenerTienda(id: Int): Tienda {
        val line = File(fileName).readLines().find { it.startsWith("$id,") }
        val campos = line?.split(",") ?: throw NoSuchElementException("No se encontró un producto con el ID $id")

        return Tienda(
            id = campos[0].toInt(),
            nombre = campos[1],
            fechaApertura = dateFormat.parse(campos[2]),
            direccion = campos[3],
            numeroEmpleados = campos[4].toInt()
        )
    }

    fun obtenerTiendas(): List<Tienda> {
        return File(fileName).readLines().map { line ->
            val campos = line.split(",")
            Tienda(
                id = campos[0].toInt(),
                nombre = campos[1],
                fechaApertura = dateFormat.parse(campos[2]),
                direccion = campos[3],
                numeroEmpleados = campos[4].toInt()
            )
        }
    }

    fun actualizarTienda(tienda: Tienda) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.map { line ->
            if (line.startsWith("${tienda.id},")) {
                "${tienda.id},${tienda.nombre},${dateFormat.format(tienda.fechaApertura)},${tienda.direccion},${tienda.numeroEmpleados}"
            } else {
                line
            }
        }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }
    fun eliminarTienda(id: Int) {
        val productoRepository = ProductoRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\Productos.txt")
        // Obtener todas las canciones asociadas al género musical
        val productosAsociadas = productoRepository.obtenerProductosPorTienda(id)

        // Eliminar el género musical
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.filter { !it.startsWith("$id,") }
        File(fileName).writeText(nuevaLista.joinToString("\n"))

        // Eliminar en cascada las canciones asociadas
        productosAsociadas.forEach { productoRepository.eliminarProducto(it.id) }
    }

}
