import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appgoogledrive.ArchivosItem
import com.example.appgoogledrive.R

class ArchivosAdapter (private val contex: Context, private val archivosItems: List<ArchivosItem>, private val listener: (ArchivosItem) -> Unit)
    : RecyclerView.Adapter<ArchivosAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(contex).inflate(R.layout.activity_archivos_item, parent, false)
    )
    override fun getItemCount(): Int = archivosItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(archivosItems[position], listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.name)
        val type = view.findViewById<ImageView>(R.id.type)
        val image = view.findViewById<ImageView>(R.id.image)
        val descriptionDoc = view.findViewById<TextView>(R.id.descriptionDoc)
        val imgDescription = view.findViewById<ImageView>(R.id.imgDescription)
        fun bindItem(items: ArchivosItem, listener: (ArchivosItem) -> Unit){
            name.text = items.name
            descriptionDoc.text = items.descriptionDoc

            Glide.with(itemView.context)
                .load(items.image)
                .into(image)

            Glide.with(itemView.context)
                .load(items.type)
                .into(type)

            Glide.with(itemView.context)
                .load(items.arrow)
                .into(imgDescription)

            itemView.setOnClickListener{
                listener(items)
            }
        }
    }
}