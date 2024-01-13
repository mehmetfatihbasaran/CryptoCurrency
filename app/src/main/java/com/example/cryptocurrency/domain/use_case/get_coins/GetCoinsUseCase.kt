package com.example.cryptocurrency.domain.use_case.get_coins

import com.example.cryptocurrency.common.Resources
import com.example.cryptocurrency.data.remote.dto.toCoin
import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(private val repository: CoinRepository) {

    operator fun invoke(): Flow<Resources<List<Coin>>> = flow {
        try {
            emit(Resources.Loading())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resources.Success(coins))
        } catch (e: HttpException) {
            emit(Resources.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resources.Error("Couldn't reach server. Check your internet connection"))
        }
    }

}