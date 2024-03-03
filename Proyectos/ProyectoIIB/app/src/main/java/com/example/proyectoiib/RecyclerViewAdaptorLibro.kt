package com.example.proyectoiib

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
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
        val descripcionTextView: TextView
        val generoTextView: TextView
        //val urlLibroTextView: TextView
        val recordatorioTextView: TextView
        val recordatorioTituloTextView: TextView
        val botonEliminar: ImageView
        val botonEditar: ImageView

        init {
            tituloTextView = view.findViewById(R.id.tv_rvnr_tituloLibro)
            descripcionTextView = view.findViewById(R.id.tv_rvnr_descripcionE)
            generoTextView = view.findViewById(R.id.tv_rvnr_generoLibro)
            //urlLibroTextView = view.findViewById(R.id.tv_urlLibro)

            recordatorioTextView = view.findViewById(R.id.tv_rvnr_recordatorioE)
            recordatorioTituloTextView = view.findViewById(R.id.tv_rvnr_recordatorioT)

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
        holder.tituloTextView.text = libroActual.titulo
        holder.descripcionTextView.text = libroActual.descripcion
        holder.generoTextView.text = libroActual.genero

        if (libroActual.recordatorio == null) {
            holder.recordatorioTituloTextView.visibility = View.INVISIBLE
            holder.recordatorioTextView.visibility = View.INVISIBLE
        } else {
            holder.recordatorioTextView.text = libroActual.getRecordatorioString()
        }
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }
}
