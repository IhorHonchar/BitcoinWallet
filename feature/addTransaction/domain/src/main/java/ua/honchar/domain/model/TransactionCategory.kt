package ua.honchar.domain.model

enum class TransactionCategory {
    GROCERIES, TAXI, ELECTRONICS, RESTAURANT, REFILL, OTHER;

    operator fun invoke() = name.lowercase()

    companion object {
        fun getCategory(category: String) = entries.find { it() == category }
        fun costCategories(): List<TransactionCategory> = entries.filter { it != REFILL }
    }
}