package com.javalon.xpensewhiz.presentation.insight_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.xpensewhiz.domain.model.Transaction
import com.javalon.xpensewhiz.domain.usecase.read_database.Get14DayTransaction
import com.javalon.xpensewhiz.domain.usecase.read_database.Get3DayTransaction
import com.javalon.xpensewhiz.domain.usecase.read_database.Get7DayTransaction
import com.javalon.xpensewhiz.domain.usecase.read_database.GetLastMonthTransaction
import com.javalon.xpensewhiz.domain.usecase.read_database.GetStartOfMonthTransaction
import com.javalon.xpensewhiz.domain.usecase.read_database.GetTransactionByTypeUseCase
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetCurrencyUseCase
import com.javalon.xpensewhiz.presentation.home_screen.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val get3DayTransaction: Get3DayTransaction,
    private val get7DayTransaction: Get7DayTransaction,
    private val get14DayTransaction: Get14DayTransaction,
    private val getStartOfMonthTransaction: GetStartOfMonthTransaction,
    private val getLastMonthTransaction: GetLastMonthTransaction,
    private val getAllTransaction: GetTransactionByTypeUseCase
) : ViewModel() {

    private var _tabButton = MutableStateFlow(TransactionType.INCOME)
    val tabButton: StateFlow<TransactionType> = _tabButton

    private var _filteredTransaction = MutableStateFlow(emptyList<Transaction>())
    val filteredTransaction: StateFlow<List<Transaction>> = _filteredTransaction

    var selectedCurrencyCode = MutableStateFlow(String())
        private set

    fun selectTabButton(tab: TransactionType) {
        _tabButton.value = tab
        getFilteredTransaction()
    }

    init {
        getFilteredTransaction()
        currencyFormat()
    }

    private fun currencyFormat() {
        viewModelScope.launch(IO) {
            getCurrencyUseCase().collect { selectedCurrency ->
                selectedCurrencyCode.value = selectedCurrency
            }
        }
    }

     fun getFilteredTransaction(duration: Int = 5) {
        viewModelScope.launch(IO) {
            if (_tabButton.value == TransactionType.INCOME) {
                filterTransaction(duration, TransactionType.INCOME.title)
            } else {
                filterTransaction(duration, TransactionType.EXPENSE.title)
            }
        }
    }

    private suspend fun filterTransaction(duration: Int, transactionType: String = TransactionType.INCOME.title) {
        when (duration) {
            0 -> {
                get3DayTransaction(transactionType).collectLatest { filteredResults ->
                    _filteredTransaction.value = filteredResults.map { it.toTransaction() }
                }
            }
            1 -> {
                get7DayTransaction(transactionType).collectLatest { filteredResults ->
                    _filteredTransaction.value = filteredResults.map { it.toTransaction() }
                }
            }
            2 -> {
                get14DayTransaction(transactionType).collectLatest { filteredResults ->
                    _filteredTransaction.value = filteredResults.map { it.toTransaction() }
                }
            }
            3 -> {
                getStartOfMonthTransaction(transactionType).collectLatest { filteredResults ->
                    _filteredTransaction.value = filteredResults.map { it.toTransaction() }
                }
            }
            4 -> {
                getLastMonthTransaction(transactionType).collectLatest { filteredResults ->
                    _filteredTransaction.value = filteredResults.map { it.toTransaction() }
                }
            }
            5 -> {
                getAllTransaction(transactionType).collectLatest { filteredResults ->
                    _filteredTransaction.value = filteredResults.map { it.toTransaction() }
                }
            }
        }
    }
}