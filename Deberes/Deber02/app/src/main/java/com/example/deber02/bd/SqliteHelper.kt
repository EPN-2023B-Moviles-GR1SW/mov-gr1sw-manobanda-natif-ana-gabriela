package com.example.deber02.bd

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02.entidades.Producto
import com.example.deber02.entidades.Tienda
import java.text.SimpleDateFormat

class SqliteHelper (
    contexto: Context?, // this
) : SQLiteOpenHelper(
    contexto,
    "Deber02", // nombre bdd
    null,
    5
){

    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaTienda =
            """
                CREATE TABLE TIENDA(
                    id INTEGER PRIMARY KEY AUTOINCREMENT, 
                    nombre VARCHAR(50),
                    fechaApertura DATE,
                    direccion VARCHAR(50),
                    contacto VARCHAR(10)
                )
             """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaTienda)

        val scriptSQLCrearTablaProducto =
            """
                CREATE TABLE PRODUCTO(
                    id INTEGER PRIMARY KEY AUTOINCREMENT, 
                    nombreProducto VARCHAR(50),
                    precioUnitario DOUBLE,
                    cantidadDisponible INTEGER,
                    disponible BOOLEAN,
                    idTienda INTEGER,
                    FOREIGN KEY (idTienda) REFERENCES TIENDA(id)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaProducto)
    }

    override fun onUpgrade(db: SQLiteDatabase?,  oldVersion:Int, newVersion:Int) {
    }

    fun crearTienda(
        nombre: String,
        fechaApertura: String,
        direccion: String,
        contacto: String
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaApertura", fechaApertura)
        valoresAGuardar.put("direccion", direccion)
        valoresAGuardar.put("contacto", contacto)

        val resultadoGuardar = baseDatosEscritura
            .insert(
                "TIENDA", // Nombre tabla
                null,
                valoresAGuardar // Valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarTiendaFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "TIENDA", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarTiendaFormulario(
        nombre: String,
        fechaApertura: String,
        direccion: String,
        contacto: String,
        id: Int,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaApertura", fechaApertura)
        valoresAActualizar.put("direccion", direccion)
        valoresAActualizar.put("contacto", contacto)

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "TIENDA", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultarTiendaPorID(id: Int): Tienda {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM TIENDA WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parámetros
        )
        // logica busqueda
        val existeTienda = resultadoConsultaLectura.moveToFirst()
        val tiendaEncontrada = Tienda(0, "", null, "", "")
        val arreglo = arrayListOf<Tienda>()
        if (existeTienda) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Índice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaApertura = resultadoConsultaLectura.getString(2)
                val direccion = resultadoConsultaLectura.getString(3)
                val contacto = resultadoConsultaLectura.getString(4)
                if (id != null) {
                    // llenar el arreglo con un nuevo
                    tiendaEncontrada.id = id
                    tiendaEncontrada.nombre = nombre
                    tiendaEncontrada.fechaApertura = formatoFecha.parse(fechaApertura)
                    tiendaEncontrada.direccion = direccion
                    tiendaEncontrada.contacto = contacto
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return tiendaEncontrada
    }

    //listar Tiendas
    fun consultarTiendas(): ArrayList<Tienda> {
        val scriptConsultarTiendas = "SELECT * FROM TIENDA"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarTiendas,
            null
        )
        val existeTienda = resultadoConsultaLectura.moveToFirst()
        val arregloTienda = arrayListOf<Tienda>()
        if (existeTienda) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaApertura = resultadoConsultaLectura.getString(2)
                val direccion = resultadoConsultaLectura.getString(3)
                val contacto = resultadoConsultaLectura.getString(4)
                arregloTienda.add(
                    Tienda(
                        id,
                        nombre,
                        formatoFecha.parse(fechaApertura),
                        direccion,
                        contacto
                    )
                )
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloTienda
    }

    // PRODUCTO
    fun crearProducto(
        nombre: String,
        precioUnitario: Double,
        cantidadDisponible: Int,
        disponible: Boolean,
        idTienda: Int
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombreProducto", nombre)
        valoresAGuardar.put("precioUnitario", precioUnitario)
        valoresAGuardar.put("cantidadDisponible", cantidadDisponible)
        valoresAGuardar.put("disponible", disponible)
        valoresAGuardar.put("idTienda", idTienda)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "PRODUCTO", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarProductoFormulario(idProducto: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idProducto.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "PRODUCTO", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }

    fun actualizarProductoFormulario(
        nombre: String,
        precioUnitario: Double,
        cantidadDisponible: Int,
        disponible: Boolean,
        idTienda: Int,
        id: Int,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombreProducto", nombre)
        valoresAActualizar.put("precioUnitario", precioUnitario)
        valoresAActualizar.put("cantidadDisponible", cantidadDisponible)
        valoresAActualizar.put("disponible", disponible)
        valoresAActualizar.put("idTienda", idTienda)

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "PRODUCTO", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultarProductoPorID(id: Int): Producto {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PRODUCTO WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val existeProducto = resultadoConsultaLectura.moveToFirst()
        val productoEncontrado = Producto(null, "", 0.0, 0, false, 0)

        if (existeProducto) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val precioUnitario = resultadoConsultaLectura.getDouble(2)
                val cantidadDisponible = resultadoConsultaLectura.getInt(3)
                val disponible = resultadoConsultaLectura.getInt(4)
                val idTienda = resultadoConsultaLectura.getInt(5)
                if (id != null) {
                    // Llenar el objeto Producto con los datos obtenidos
                    productoEncontrado.idProducto = id
                    productoEncontrado.nombreProducto = nombre
                    productoEncontrado.precioUnitario = precioUnitario
                    productoEncontrado.cantidadDisponible = cantidadDisponible
                    if (disponible == 1) {
                        productoEncontrado.disponible = true
                    }
                }
                productoEncontrado.idTienda = idTienda
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return productoEncontrado
    }

    // Listar productos
    fun consultarProductos(): ArrayList<Producto> {
        val scriptConsultarProductos = "SELECT * FROM PRODUCTO"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarProductos,
            null
        )
        val existeProducto = resultadoConsultaLectura.moveToFirst()
        val arregloProductos = arrayListOf<Producto>()
        if (existeProducto) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val precioUnitario = resultadoConsultaLectura.getDouble(2)
                val cantidadDisponible = resultadoConsultaLectura.getInt(3)
                val disponible = resultadoConsultaLectura.getInt(4)
                val idTienda = resultadoConsultaLectura.getInt(5)
                if (id != null) {
                    val productoEncontrado = Producto(null, "", 0.0, 0, false, 0)
                    productoEncontrado.idProducto = id
                    productoEncontrado.nombreProducto = nombre
                    productoEncontrado.precioUnitario = precioUnitario
                    productoEncontrado.cantidadDisponible = cantidadDisponible
                    if (disponible == 1) {
                        productoEncontrado.disponible = true
                    }
                    productoEncontrado.idTienda = idTienda
                    arregloProductos.add(productoEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloProductos
    }

    // Consultar productos por tienda
    fun consultarProductosPorTienda(idTienda: Int): ArrayList<Producto> {
        val scriptConsultarProductos = "SELECT * FROM PRODUCTO WHERE idTienda = ?"
        val baseDatosLectura = readableDatabase
        val parametrosConsultaLectura = arrayOf(idTienda.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarProductos,
            parametrosConsultaLectura
        )
        val existeProducto = resultadoConsultaLectura.moveToFirst()
        val arregloProductos = arrayListOf<Producto>()
        if (existeProducto) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val precioUnitario = resultadoConsultaLectura.getDouble(2)
                val cantidadDisponible = resultadoConsultaLectura.getInt(3)
                val disponible = resultadoConsultaLectura.getInt(4)
                val idTienda = resultadoConsultaLectura.getInt(5)
                if (id != null) {
                    val productoEncontrado = Producto(null, "", 0.0, 0, false, 0)
                    productoEncontrado.idProducto = id
                    productoEncontrado.nombreProducto = nombre
                    productoEncontrado.precioUnitario = precioUnitario
                    productoEncontrado.cantidadDisponible = cantidadDisponible
                    if (disponible == 1) {
                        productoEncontrado.disponible = true
                    }
                    productoEncontrado.idTienda = idTienda
                    arregloProductos.add(productoEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloProductos
    }
}