package ua.honchar.data.model.response
import com.google.gson.annotations.SerializedName

data class BitcoinExchangeRateResponse(
    @SerializedName("data")
    val mData: BitcoinExchangeRateDataResponse?,
    @SerializedName("timestamp")
    val timestamp: Long?
)

data class BitcoinExchangeRateDataResponse(
    @SerializedName("currencySymbol")
    val currencySymbol: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("rateUsd")
    val rateUsd: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("type")
    val type: String?
)




