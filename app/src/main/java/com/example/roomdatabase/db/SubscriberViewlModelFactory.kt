package com.example.roomdatabase.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubscriberViewlModelFactory(private val repository: SubscriberRepository) :ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java))
        {
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }

}