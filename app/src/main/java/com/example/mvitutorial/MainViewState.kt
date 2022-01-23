package com.example.mvitutorial

sealed class MainViewState {
    //idle
    object Idle :MainViewState()
    //number
    data class Message(val msg : String):MainViewState()
    //error
    data class Error(val error:String?) : MainViewState()
    //loading

}
