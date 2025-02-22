package ua.honchar.domain.model

data class Transaction(
    val id: Int,
    val amount: Double,
    val category: String,
    val date: String,
    val time: String
): ListItem
