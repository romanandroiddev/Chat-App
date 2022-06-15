package uz.project.graphqlchatapp.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.project.graphql.MessageListQuery
import uz.project.graphqlchatapp.R
import uz.project.graphqlchatapp.databinding.RcItemMeBinding
import uz.project.graphqlchatapp.databinding.RcItemTheyBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val ME = 0
        private const val ANOTHER = 1
    }

    var models = mutableListOf<MessageListQuery.Message>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = models.size

    override fun getItemViewType(position: Int): Int {
        return when (getItemViewType(position)) {
            2 -> ME
            else -> ANOTHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ME -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.rc_item_me, parent, false)
                val binding = RcItemMeBinding.bind(view)
                MeViewHolder(binding)
            }
            else -> {
                val itemView =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.rc_item_they, parent, false)
                val binding = RcItemTheyBinding.bind(itemView)
                AnotherViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ME) {
            (holder as MeViewHolder).bind(models[position])
        } else {
            (holder as AnotherViewHolder).bind(models[position])
        }
    }
}