package com.example.roomdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.ListDataBinding
import com.example.roomdatabase.db.Subscriber
import com.example.roomdatabase.generated.callback.OnClickListener

class RecyclerAdapter (private val clickListener:(Subscriber)->Unit ):RecyclerView.Adapter<MyViewHolder>()
{
    private val subscriberlist:ArrayList<Subscriber> = ArrayList<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ListDataBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_data,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscriberlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriberlist[position],clickListener)
    }
    fun setList(subscribers: List<Subscriber>)
    {
        subscriberlist.clear()
        subscriberlist.addAll(subscribers)
    }
}

class MyViewHolder(val binding: ListDataBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun bind(subscriber: Subscriber,clickListener:(Subscriber)->Unit )
    {
        binding.nameText.text = subscriber.name
        binding.emailText.text = subscriber.email
        binding.listItemlayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}