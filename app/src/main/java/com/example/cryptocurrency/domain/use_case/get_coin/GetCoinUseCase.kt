package com.example.cryptocurrency.domain.use_case.get_coin

import com.example.cryptocurrency.common.Resources
import com.example.cryptocurrency.data.remote.dto.toCoinDetail
import com.example.cryptocurrency.domain.model.CoinDetail
import com.example.cryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(private val repository: CoinRepository) {

    operator fun invoke(coinId: String): Flow<Resources<CoinDetail>> = flow {
        try {
            emit(Resources.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resources.Success(coin))
        } catch (e: HttpException) {
            emit(Resources.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resources.Error("Couldn't reach server. Check your internet connection"))
        }
    }

}