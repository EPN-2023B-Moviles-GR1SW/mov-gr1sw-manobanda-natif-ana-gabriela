package com.example.proyectoiib

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecyclerViewAdaptorLibro(
    private val contexto: InicioActivity,
    private val lista: ArrayList<Libro>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerViewAdaptorLibro.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var authActual = FirebaseAuth.getInstance().currentUser
        val tituloTextView: TextView
        val autorTextView: TextView
        val generoTextView: TextView
        val urlLibroVTextView: TextView
        val botonEliminar: ImageView
        val botonEditar: ImageView
        val imagenLibro: ImageView
        val tipoLibroTextView: TextView

        init {
            tituloTextView = view.findViewById(R.id.tv_rvnr_tituloLibro)
            autorTextView = view.findViewById(R.id.tv_rvnr_autorLibro)
            generoTextView = view.findViewById(R.id.tv_rvnr_generoLibro)
            urlLibroVTextView = view.findViewById(R.id.tv_rvnr_urlLibroV)
            tipoLibroTextView = view.findViewById(R.id.tv_rvnr_tipoLibro)
            imagenLibro = view.findViewById(R.id.img_libro)
            botonEliminar = view.findViewById<ImageView?>(R.id.iv_rvnr_eliminar)
            botonEliminar.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    abrirDialogoEliminar(position)
                    notifyDataSetChanged()
                }
            }
            botonEditar = view.findViewById(R.id.im_rvnr_editar)
            botonEditar.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val libroSeleccionado = lista[position]
                    val intent = Intent(contexto, EditarActivity::class.java)
                    intent.putExtra("libroId", libroSeleccionado.id)
                    contexto.startActivity(intent)
                }
            }
        }

        fun abrirDialogoEliminar(position: Int) {
            val builder = AlertDialog.Builder(contexto)
            builder.setTitle("Desea Eliminar?")
            builder.setPositiveButton("Aceptar") { dialog, which ->
                val libroSeleccionado = lista[position]
                val db = Firebase.firestore
                val libroRef =
                    db.collection("usuarios").document(authActual!!.uid).collection("libros")
                        .document(libroSeleccionado.id!!)
                lista.removeAt(position)
                notifyDataSetChanged()
                libroRef.delete()
                    .addOnSuccessListener { }
                    .addOnFailureListener { }
            }
            builder.setNegativeButton("Cancelar", null)
            val dialogo = builder.create()
            dialogo.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.activity_recycler_view_adaptor_libro,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val libroActual = this.lista[position]
        Log.d("RecyclerViewAdaptor", "Titulo: ${libroActual.titulo}")
        holder.tituloTextView.text = libroActual.titulo
        holder.autorTextView.text = libroActual.autor
        holder.generoTextView.text = libroActual.genero
        holder.urlLibroVTextView.text = libroActual.getUrlLibroString()
        holder.tipoLibroTextView.text = libroActual.tipo
        Log.d("RecyclerViewAdaptor", "URL LIBRO: ${libroActual.getUrlLibroString()}")
        Log.d("RecyclerViewAdaptor", "Elementos lista: ${this.lista.size}")

        if (!libroActual.urlImagen.isNullOrEmpty()) {
            Log.d("RecyclerViewAdaptor", "Cargando imagen desde URL: ${libroActual.urlImagen}")
            Glide.with(contexto)
                .load(libroActual.urlImagen)
                .centerCrop()
                .placeholder(R.drawable.libro2) // Cambia por tu recurso de imagen de carga
                .error(R.drawable.libroerror) // Cambia por tu recurso de imagen de error
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("RecyclerViewAdaptor", "Error al cargar imagen: $e")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("RecyclerViewAdaptor", "Imagen cargada correctamente")
                        return false
                    }
                })
                .into(holder.imagenLibro)
        } else {
            holder.imagenLibro.setImageResource(R.drawable.libro2) // Cambia por tu recurso de imagen predeterminada
        }
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

}
