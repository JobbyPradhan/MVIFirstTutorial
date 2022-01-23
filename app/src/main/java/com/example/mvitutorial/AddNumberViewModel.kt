package com.example.mvitutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddNumberViewModel : ViewModel() {

    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)

    private var number = 0
    val state: StateFlow<MainViewState> get() = _viewState

    init {
        processIntent()
    }

    //progress
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.AddMsg -> {
                        addMsg(it.msg)
                    }
                    is MainIntent.CheckedMsg->{
                        checkMsg(it.msg)
                    }


                }
            }
        }
    }

    private fun checkMsg(msg: String?) {
        viewModelScope.launch {
            _viewState.value = try {
                if (msg.isNullOrEmpty())
                     MainViewState.Error("Empty Message")
                else
                    MainViewState.Error(error = null)
            }catch (e:Exception){
                MainViewState.Error(e.message.toString())
            }
        }
    }

    //reduce
    private fun addMsg(msg: String) {
        viewModelScope.launch {
            _viewState.value = try {
                    MainViewState.Message(msg)
            } catch (e: Exception) {
                MainViewState.Error(e.message.toString())
            }
        }
    }
}