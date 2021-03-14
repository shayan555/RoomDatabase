package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.db.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: SubscriberViewModel
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDao
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewlModelFactory(repository)
        myViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewmodel = myViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        myViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(applicationContext,it,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView()
    {
        binding.recyclerData.layoutManager = LinearLayoutManager(this)
        adapter =  RecyclerAdapter({selectedItem:Subscriber->listItemClicked(selectedItem)})
        binding.recyclerData.adapter = adapter
        displaySubscriberList()

    }

    private fun displaySubscriberList()
    {
        myViewModel.subscribers.observe(this, Observer {

            Log.i("TAG",it.toString())

            adapter.setList(it)
            adapter.notifyDataSetChanged()

        })
    }

    private fun listItemClicked(subscriber: Subscriber)
    {
        //Toast.makeText(this,"selected name is ${subscriber.name}",Toast.LENGTH_LONG).show()
        myViewModel.initupdateanddelete(subscriber)
    }
}