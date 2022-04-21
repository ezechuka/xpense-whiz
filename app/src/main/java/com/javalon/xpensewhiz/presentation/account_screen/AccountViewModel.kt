package com.javalon.xpensewhiz.presentation.account_screen

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.xpensewhiz.common.Constants
import com.javalon.xpensewhiz.domain.model.Account
import com.javalon.xpensewhiz.domain.model.Transaction
import com.javalon.xpensewhiz.domain.usecase.read_database.GetAccountsUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetTransactionByAccount
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetCurrencyUseCase
import com.javalon.xpensewhiz.presentation.home_screen.getFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
                        trx.date.getFormattedDate()
                    }
                }
            }
        }
    }

    private fun currencyFormat() {
        viewModelScope.launch(IO) {
            val selectedCurrency = stringPreferencesKey(Constants.CURRENCY_KEY)
            getCurrencyUseCase().collect { selectedCurrencyPref ->
                val currencyCode = selectedCurrencyPref[selectedCurrency] ?: ""
                selectedCurrencyCode.value = currencyCode
            }
        }
    }
}