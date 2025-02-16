package ua.honchar.domain.usecase

import javax.inject.Inject

class FilterToValidAmountUseCase @Inject constructor() {

    operator fun invoke(value: String): String {
        var hasDot = false
        val validatedText = value.filter {
            val isValidSymbol = it.isDigit() || (it == '.' && !hasDot)
            if (it == '.') {
                hasDot = true
            }
            isValidSymbol
        }
        return validatedText
    }
}