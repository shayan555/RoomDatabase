package com.example.roomdatabase.db

class SubscriberRepository(private val dao:SubscriberDao)
{
    val subscribers = dao.getAllSubscriber()

    suspend fun insert(subscriber: Subscriber): Long{
        return  dao.insertSubscriber1(subscriber)
    }

    suspend fun update(subscriber: Subscriber):Int
    {
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber):Int
    {
       return  dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll():Int
    {
        return dao.deleteAll()
    }

}