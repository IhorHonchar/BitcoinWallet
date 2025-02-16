package ua.honchar.domain.model

data class TransactionContainer(
    val date: String,
    val transactions: List<Transaction>
)
