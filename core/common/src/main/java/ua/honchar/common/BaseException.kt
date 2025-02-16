package ua.honchar.common

open class BaseException(message: String) : Exception(message)

class SaveTransactionException(message: String = ""): BaseException(
    message.ifEmpty { "Couldn't save transaction" }
)