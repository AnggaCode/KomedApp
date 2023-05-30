
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komed.komed.DataModel.ResponseCobaItem
import com.komed.komed.R

class CafeRestoAdapter(private var itemCafe: List<ResponseCobaItem>) :
    RecyclerView.Adapter<CafeRestoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCafe: ImageView = itemView.findViewById(R.id.image_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_caferesto, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = itemCafe.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemCafe[position]

        Glide.with(holder.itemView)
            .load(data.url)
            .into(holder.imageCafe)
    }
}