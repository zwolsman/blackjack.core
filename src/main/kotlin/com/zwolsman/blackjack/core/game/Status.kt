package com.zwolsman.blackjack.core.game

enum class Status {
    OK,
    BUSTED,
    FINISHED;

    val canPlay:Boolean
        get() = this == OK
}