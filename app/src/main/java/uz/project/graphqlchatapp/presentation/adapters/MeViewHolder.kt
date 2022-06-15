package uz.project.graphqlchatapp.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import uz.project.graphql.MessageListQuery
import uz.project.graphqlchatapp.databinding.RcItemMeBinding

class MeViewHolder(private val binding: RcItemMeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(message: MessageListQuery.Message) {
        binding.tvMessage.text = message.message
    }
}