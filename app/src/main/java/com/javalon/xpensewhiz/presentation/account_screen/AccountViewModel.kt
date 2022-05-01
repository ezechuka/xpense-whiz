package com.javalon.xpensewhiz.presentation.account_screen

import android.text.format.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.xpensewhiz.domain.model.Account
import com.javalon.xpensewhiz.domain.model.Transaction
import com.javalon.xpensewhiz.domain.usecase.read_database.GetAccountsUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetTransactionByAccount
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTransactionByAccount: GetTransactionByAccount
): ViewModel() {

    var transactions = MutableStateFlow(mapOf<String, List<Transaction>>())
        private set

    var allAccounts = MutableStateFlow(emptyList<Account>())
        private set

    var selectedCurrencyCode = MutableStateFlow(String())
        private set

    init {
        currencyFormat()
        viewModelScope.launch(IO) {
            getAccountsUseCase().collect { accountsDto ->
                val accounts = accountsDto.map { it.toAccount() }
                allAccounts.value = accounts
            }
        }
    }

    fun getTransaction(accountType: String) {
        viewModelScope.launch(IO) {
            getTransactionByAccount(accountType).collect { allTrx ->
                allTrx.let { trxDto ->
                    val newTrx = trxDto.map { it.toTransaction() }.reversed()
                    transactions.value = newTrx.groupBy { trx ->
                        getFormattedDate(
                            trx.date
                        )
                    }
                }
            }
        }
    }

    private fun currencyFormat() {
        viewModelScope.launch(IO) {
            getCurrencyUseCase().collect { selectedCurrency ->
                val currencyCode = selectedCurrency
                selectedCurrencyCode.value = currencyCode
            }
        }
    }

    fun getFormattedDate(date: Date): String {
        val dayOfWeek = DateFormat.format("EEE", date)
        val day = DateFormat.format("dd", date)
        val monthAbbr = DateFormat.format("MMM", date)

        return "$dayOfWeek $day, $monthAbbr"
    }
}