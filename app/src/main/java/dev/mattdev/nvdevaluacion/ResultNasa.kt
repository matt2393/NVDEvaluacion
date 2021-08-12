package dev.mattdev.nvdevaluacion

sealed class ResultNasa<out T> {
    data class Success<out R>(val data: R): ResultNasa<R>()
    data class Error(val error: Throwable): ResultNasa<Nothing>()
}