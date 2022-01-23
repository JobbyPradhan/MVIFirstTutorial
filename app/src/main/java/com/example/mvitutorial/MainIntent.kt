package com.example.mvitutorial

sealed class MainIntent{
    data class AddMsg(val msg :String):MainIntent()
    data class CheckedMsg(val msg:String?) :MainIntent()
}
