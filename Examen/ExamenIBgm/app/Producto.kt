class Producto (
    var idProducto: Int?,
    var nombreProducto: String?,
    var precioUnitario: Double?,
    var cantidadDisponible : Int?,
    var disponible: Boolean?,
    var idTienda: Int?
){
    override fun toString(): String {
        //val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        //val fecha = formatoFecha.format(fechaCreacion)
        return "${idProducto}-${nombreProducto} - ${precioUnitario} - ${cantidadDisponible}- ${disponible}"
    }
}