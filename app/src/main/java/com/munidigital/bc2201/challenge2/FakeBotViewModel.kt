package com.munidigital.bc2201.challenge2


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class FakeBotViewModel : ViewModel() {
    private var _messageLiveData = MutableLiveData<MutableList<Message>>()
    val messageLiveData : LiveData<MutableList<Message>>
        get() = _messageLiveData
    private lateinit var botMessagesList : MutableList<String>



    init {
        _messageLiveData.value = mutableListOf()
        addMessageBotList()
    }

    private fun addMessageBotList() {

        botMessagesList = mutableListOf()
        botMessagesList.add("Si")
        botMessagesList.add("No")
        botMessagesList.add("Pregunta de nuevo")
        botMessagesList.add("Es probable")
        botMessagesList.add("No lo creo")
        botMessagesList.add("No se")
        botMessagesList.add("Quizas")
        botMessagesList.add("Definitivamente no")
    }

    fun newMessage ( message_text: String) : Boolean{

        if(!message_text.isEmpty())
        {
            val listAux = _messageLiveData.value
            listAux?.add(Message(newMessageId(), message_text, false))
            _messageLiveData.value = listAux
            addMessageBot()
            return true
        }
        return false
    }

   private fun newMessageId() : Int{
        if(_messageLiveData.value.isNullOrEmpty())
        {
            return 0
        }

        val listSize : Int? = _messageLiveData.value?.let { if (it.size>0) it.size -1 else 0  }
        val lastItem = listSize?.let { _messageLiveData.value?.get(it) }
        return lastItem?.id?.plus(1) ?: 0
    }

    private fun addMessageBot() {
        val randomNumber = (0..7).random()
        _messageLiveData.value?.add(Message(newMessageId(), botMessagesList[randomNumber],true))

    }

    fun getMessageListSize() : Int
    {
        return _messageLiveData.value?.size ?: 0
    }

}