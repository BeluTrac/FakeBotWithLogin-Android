package com.munidigital.bc2201.challenge2


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class FakeBotViewModel() : ViewModel() {
    private var _messageLiveData = MutableLiveData<MutableList<Message>>()
    val messageLiveData : LiveData<MutableList<Message>>
        get() = _messageLiveData
    lateinit var botMessagesList : MutableList<String>



    init {
        _messageLiveData.value = mutableListOf<Message>()
        addMessageBotList()
    }

    private fun addMessageBotList() {

        botMessagesList = mutableListOf<String>()
        botMessagesList.add("Si")
        botMessagesList.add("No")
        botMessagesList.add("Pregunta de nuevo")
        botMessagesList.add("Es probable")
        botMessagesList.add("No lo creo")
        botMessagesList.add("No se")
        botMessagesList.add("Quizas")
        botMessagesList.add("Definitivamente no")
    }

    fun newMessage ( message_text: String) {
        var list_aux = _messageLiveData.value
        list_aux?.add(Message(new_message_id(),message_text,false))
        _messageLiveData.value = list_aux
        addMessageBot()
    }

    fun new_message_id() : Int{
        if(_messageLiveData.value.isNullOrEmpty())
        {
            return 0
        }

        val list_size : Int? = _messageLiveData.value?.let { if (it.size>0) it.size -1 else 0  }
        val last_item = list_size?.let { _messageLiveData.value?.get(it) }
        return last_item?.id?.plus(1) ?: 0
    }

    private fun addMessageBot() {
        val randomNumber = (0..7).random()
        _messageLiveData.value?.add(Message(new_message_id(),botMessagesList.get(randomNumber),true))

    }

    fun getMessageListSize() : Int
    {
        return _messageLiveData?.value?.size ?: 0
    }

}