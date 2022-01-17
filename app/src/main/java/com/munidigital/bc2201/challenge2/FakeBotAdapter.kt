package com.munidigital.bc2201.challenge2



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.munidigital.bc2201.challenge2.databinding.ItemBinding


class FakeBotAdapter: ListAdapter<Message, FakeBotAdapter.MessageViewHolder> (DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<Message>()
    {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FakeBotAdapter.MessageViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context))
        return MessageViewHolder(binding) //Devuelvo un objeto viewHolder con los elementos del item
    }

    override fun onBindViewHolder(holder: FakeBotAdapter.MessageViewHolder, position: Int) { //Seteo los valores del item, para cada uno se ejecuta
        val message :Message = getItem(position)
        holder.bind(message)
    }

    inner class MessageViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(message: Message){

            if(message.bot)
            {
                binding.botText.text = message.message
                binding.botText.visibility = View.VISIBLE
                binding.userText.visibility = View.GONE

            }else
            {
                binding.userText.text = message.message
                binding.userText.visibility = View.VISIBLE
                binding.botText.visibility = View.GONE
            }
            binding.executePendingBindings()

        }
    }
}