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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = FakeBotAdapter()

        binding  = ActivityMainBinding.inflate(layoutInflater)
        fakeBotViewModel = ViewModelProvider(this).get(FakeBotViewModel::class.java)

        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.submitList(fakeBotViewModel.messageLiveData.value)

            /*
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_login_24)
        binding.toolbar.setNavigationOnClickListener {
            val vm = ViewModelProvider(this).get(LoginViewModel::class.java)
            vm.logout()
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
*/


        binding.arrowButton.setOnClickListener{
            if(!fakeBotViewModel.newMessage(binding.editText.text.toString()))
            {
             Toast.makeText(this,getString(R.string.You_must_write_a_message),Toast.LENGTH_SHORT).show()
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val item_id = item.itemId
        if(item_id === R.id.main_menu_logout)
        {
            val vm = ViewModelProvider(this).get(LoginViewModel::class.java)
            vm.logout()
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
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