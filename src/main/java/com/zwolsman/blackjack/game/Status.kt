package com.zwolsman.blackjack.game

enum class Status {
    OK,
    BUSTED,
    FINISHED;

    val canPlay:Boolean
        get() = this == OK
}