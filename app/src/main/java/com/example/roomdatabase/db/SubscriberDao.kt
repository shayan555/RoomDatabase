package com.example.roomdatabase.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDao
{
    @Insert
    suspend fun insertSubscriber1(subscriber1: Subscriber):Long

//    @Insert
//    suspend fun insertSubscriber2(subscriber2: Subscriber,subscriber3: Subscriber,subscriber4: Subscriber):List<Long>
//
//    @Insert
//    suspend fun insertSubscriber3(subscriber: List<Subscriber>):List<Long>
//
//    @Insert
//    suspend fun insertSubscriber4(subscriber: Subscriber,subscribers:List<Subscriber>):List<Long>

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) :Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber):Int

    @Query("DELETE FROM Subscriber_data_table")
    suspend fun deleteAll():Int

    @Query("SELECT * FROM Subscriber_data_table")
    fun getAllSubscriber():LiveData<List<Subscriber>>


}