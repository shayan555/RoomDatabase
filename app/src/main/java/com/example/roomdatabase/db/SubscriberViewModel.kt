package com.example.roomdatabase.db

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SubscriberViewModel(private val repository: SubscriberRepository):ViewModel(),Observable{
    val subscribers = repository.subscribers
    private var isUpdateorDelete = false
    private lateinit var subscriberToUpdateOrDelete:Subscriber

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveButton = MutableLiveData<String>()

    @Bindable
    val clearButton = MutableLiveData<String>()
    init {
        saveButton.value = "Save"
        clearButton.value = "Clear All"
    }

    fun saveClick()
    {
        if (inputName.value==null)
        {
            statusMessage.value = Event("Please enter name")
        }else if (inputEmail.value == null)
        {
            statusMessage.value = Event("Please enter Email")
        }else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches())
        {
            statusMessage.value = Event("Please enter correct email address")
        }else {
            if (isUpdateorDelete){
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!

                update(subscriberToUpdateOrDelete)
            }else
            {
                val name= inputName.value!!
                val email = inputEmail.value!!
                insert(Subscriber(0,name,email))

                inputName.value = null
                inputEmail.value = null
            }

        }
    }

    fun clearClick()
    {
        if (isUpdateorDelete == true)
        {
            delete(subscriberToUpdateOrDelete)
        }
        else
        {
            deleteAll()

        }
    }

    fun insert(subscriber: Subscriber):Job = viewModelScope.launch {
           val newRowId =  repository.insert(subscriber)
        if (newRowId>-1) {
            statusMessage.value = Event("Inserted Successfully with $newRowId")
        }
        else
        {
            statusMessage.value = Event("Error Occured")
        }
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch{

       val noOfRows =  repository.update(subscriber)
        if (noOfRows>0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateorDelete = false
            saveButton.value = "Save"
            clearButton.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        }
        else
        {
            statusMessage.value = Event("Error Occoured")

        }

    }

    fun delete(subscriber: Subscriber)= viewModelScope.launch {

        val noOfRowDeleted = repository.delete(subscriber)
        if (noOfRowDeleted>0)
        {
            inputName.value = null
            inputEmail.value = null
            isUpdateorDelete = false
            saveButton.value = "Save"
            clearButton.value = "Clear All"
            statusMessage.value = Event("$noOfRowDeleted Row deleted Successfully")
        }
        else
        {
            statusMessage.value = Event("Error Occured")
        }


    }

    fun deleteAll()= viewModelScope.launch {
       val noOfRowDeleted =  repository.deleteAll()
        if (noOfRowDeleted>0)
        {
            statusMessage.value = Event("$noOfRowDeleted Deleted Successfully")
        }
        else
        {
            statusMessage.value = Event("Error Occoured")
        }

    }

    fun initupdateanddelete(subscriber: Subscriber)
    {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateorDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveButton.value = "Update"
        clearButton.value = "Delete"
    }


    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
    get() = statusMessage


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}