package ua.honchar.domain.model

data class Transaction(
    val amount: Double,
    val category: String,
    val date: String,
    val time: String
)
