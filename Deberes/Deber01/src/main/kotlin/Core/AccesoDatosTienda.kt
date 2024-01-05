package Core

import Entidades.Tienda
import java.io.File
import java.text.SimpleDateFormat
import java.util.NoSuchElementException

class AccesoDatosTienda(private val fileName: String) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun guardarTienda(tienda: Tienda) {
        val registro = "${tienda.id},${tienda.nombre}, ${dateFormat.format(tienda.fechaApertura)}," +
                "${tienda.direccion},${tienda.numeroEmpleados}, ${tienda.contacto}\n"
        File(fileName).appendText(registro)
    }

    fun obtenerTienda(id: Int): Tienda {
        val line = File(fileName).readLines().find { it.startsWith("$id,") }
        val campos = line?.split(",") ?: throw NoSuchElementException("No se encontró una tienda con el ID $id")

        return try {
            Tienda(
                id = campos[0].toInt(),
                nombre = campos[1],
                fechaApertura = dateFormat.parse(campos[2]),
                direccion = campos[3],
                numeroEmpleados = campos[4].toInt(),
                contacto = campos[5]
            )
        } catch (e: Exception) {
            throw NoSuchElementException("No se encontró una tienda con el ID $id")
        }
    }

    fun obtenerTiendas(): List<Tienda> {
        return File(fileName).readLines().mapNotNull { line ->
            val campos = line.split(",")
            if (campos.size == 6) {
                Tienda(
                    id = campos[0].toInt(),
                    nombre = campos[1],
                    fechaApertura = dateFormat.parse(campos[2]),
                    direccion = campos[3],
                    numeroEmpleados = campos[4].toInt(),
                    contacto = campos[5]
                )
            } else {
                null // Ignora las líneas que no tienen el número correcto de campos
            }
        }
    }

    fun actualizarTienda(tienda: Tienda) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.map { line ->
            if (line.startsWith("${tienda.id},")) {
                "${tienda.id},${tienda.nombre},${dateFormat.format(tienda.fechaApertura)},${tienda.direccion},${tienda.numeroEmpleados},${tienda.contacto}"
            } else {
                line
            }
        }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }


    fun eliminarTienda(id: Int) {
        val productoRepository =
            AccesoDatosProducto(System.getProperty("user.dir") + "\\src\\main\\kotlin\\archivos\\Productos.txt")
        val productosAsociados = productoRepository.obtenerProductosPorTienda(id)

        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.filter { !it.startsWith("$id,") }
        File(fileName).writeText(nuevaLista.joinToString("\n"))

        productosAsociados.forEach { productoRepository.eliminarProducto(it.codigo) }
    }

}
