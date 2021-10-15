package Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.she.ui.databinding.RvItemBinding

class DemoListAdapter(
    private val demoList: ArrayList<String>,
    private val addListener: (String) -> Unit
) : RecyclerView.Adapter<DemoListAdapter.DemoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DemoHolder(binding)
    }

    override fun onBindViewHolder(holder: DemoHolder, position: Int) {
        holder.bindItem(demoList[position], addListener)
    }

    override fun getItemCount(): Int = demoList.size

    inner class DemoHolder(private val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(demoName: String, addListener: (String) -> Unit) {
            with(itemView) {
                binding.tvName.text = demoName

                setOnClickListener { addListener(demoName) }
            }
        }
    }
}