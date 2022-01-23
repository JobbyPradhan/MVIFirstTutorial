package com.example.mvitutorial

sealed class MainIntent{
    data class AddMsg(val msg :String):MainIntent()
}
