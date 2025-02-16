package ua.honchar.data.api

import retrofit2.http.GET
import ua.honchar.data.model.response.BitcoinExchangeRateResponse

interface CoinsApi {

    @GET("/v2/rates/bitcoin")
    suspend fun getBitcoinExchangeRate(): BitcoinExchangeRateResponse
}