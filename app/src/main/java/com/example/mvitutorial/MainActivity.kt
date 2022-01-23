package com.example.mvitutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.mvitutorial.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel :AddNumberViewModel by lazy {
        ViewModelProviders.of(this).get(AddNumberViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        render()
        binding.btnSent.setOnClickListener {
            //send
            lifecycleScope.launchWhenStarted {
                viewModel.intentChannel.send(MainIntent.AddMsg(binding.etMsg.text.toString()))
                binding.etMsg.setText("")
            }

        }
        binding.etMsg.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launchWhenStarted {
                viewModel.intentChannel.send(MainIntent.CheckedMsg(text.toString()))
            }

        }

    }

    private fun render(){
       lifecycleScope.launchWhenStarted {
           viewModel.state.collect{
               when(it){
                   is MainViewState.Idle->{binding.txtMsg.text = ""}
                   is MainViewState.Message->{
                       Log.i("TAGAGAGAGA", "render: ${it.msg}")
                       binding.txtMsg.append(it.msg +"\n")
                       binding.tilMsg.error = null
                   }
                   is MainViewState.Error->{
                       binding.tilMsg.error = it.error
                   }
               }
           }
       }
    }
}