package com.munidigital.bc2201.challenge2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.munidigital.bc2201.challenge2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var fakeBotViewModel : FakeBotViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = FakeBotAdapter()

        binding  = ActivityMainBinding.inflate(layoutInflater)
        fakeBotViewModel = ViewModelProvider(this).get(FakeBotViewModel::class.java)

        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.submitList(fakeBotViewModel.messageLiveData.value)





        binding.arrowButton.setOnClickListener{
            fakeBotViewModel.newMessage(binding.editText.text.toString())
            binding.editText.setText("")

        }

        fakeBotViewModel.messageLiveData.observe(this, {
                messageList ->
            adapter.submitList(messageList)
            binding.recyclerView.scrollToPosition(fakeBotViewModel.getMessageListSize())
            handleEmptyView()
        })

        handleEmptyView()

    }

    private fun handleEmptyView()
    {
        if (fakeBotViewModel.messageLiveData.value.isNullOrEmpty())
        {
            binding.emptyViewText.visibility = View.VISIBLE
        }
        else
        {
            binding.emptyViewText.visibility = View.GONE
        }
    }
}