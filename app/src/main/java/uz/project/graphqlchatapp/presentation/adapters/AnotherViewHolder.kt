package uz.project.graphqlchatapp.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import uz.project.graphql.MessageListQuery
import uz.project.graphqlchatapp.databinding.RcItemTheyBinding

class AnotherViewHolder(private val binding: RcItemTheyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(message: MessageListQuery.Message) {
        binding.tvMessage.text = message.message
        binding.userName.text = message.name
    }
}