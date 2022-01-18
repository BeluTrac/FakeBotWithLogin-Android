package com.munidigital.bc2201.challenge2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.munidigital.bc2201.challenge2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var fakeBotViewModel : FakeBotViewModel
    private lateinit var binding : ActivityMainBinding
    private lateinit var loginViewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = FakeBotAdapter()

        binding  = ActivityMainBinding.inflate(layoutInflater)
        fakeBotViewModel = ViewModelProvider(this).get(FakeBotViewModel::class.java)

        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.arrowButton.setOnClickListener{
            if (!fakeBotViewModel.newMessage(binding.editText.text.toString())) {
                Toast.makeText(this,getString(R.string.You_must_write_a_message),Toast.LENGTH_SHORT).show()
            }
            binding.editText.setText("")
            binding.recyclerView.scrollToPosition(fakeBotViewModel.getMessageListSize()-1)
        }

        fakeBotViewModel.messageLiveData.observe(this, { messageList ->
            adapter.submitList(messageList)
            handleEmptyView()
        })

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        handleEmptyView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.main_menu_logout) {
            loginViewModel.logout()
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleEmptyView() {
        if (fakeBotViewModel.messageLiveData.value.isNullOrEmpty()) {
            binding.emptyViewText.visibility = View.VISIBLE
        } else {
            binding.emptyViewText.visibility = View.GONE
        }
    }
}