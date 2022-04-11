package com.javalon.xpensewhiz.presentation.setting_screen

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.xpensewhiz.common.Constants
import com.javalon.xpensewhiz.data.local.entity.AccountDto
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetCurrencyUseCase
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetExpenseLimitUseCase
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetLimitKeyUseCase
import com.javalon.xpensewhiz.domain.usecase.write_database.EraseTransactionUseCase
import com.javalon.xpensewhiz.domain.usecase.write_database.InsertAccountsUseCase
import com.javalon.xpensewhiz.domain.usecase.write_datastore.EditExpenseLimitUseCase
import com.javalon.xpensewhiz.domain.usecase.write_datastore.EditLimitKeyUseCase
import com.javalon.xpensewhiz.presentation.home_screen.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val insertAccountsUseCase: InsertAccountsUseCase,
    private val eraseTransactionUseCase: EraseTransactionUseCase,
    private val getExpenseLimitUseCase: GetExpenseLimitUseCase,
    private val editExpenseLimitUseCase: EditExpenseLimitUseCase,
    private val getLimitKeyUseCase: GetLimitKeyUseCase,
    private val editLimitKeyUseCase: EditLimitKeyUseCase
) : ViewModel() {

    private var _currency = MutableStateFlow(String())
    val currency: StateFlow<String> = _currency

    private var _expenseLimit = MutableStateFlow(0.0)
    val expenseLimit: StateFlow<Double> = _expenseLimit

    private var _reminderLimit = MutableStateFlow(false)
    val reminderLimit: StateFlow<Boolean> = _reminderLimit

    init {
        val selectedCurrency = stringPreferencesKey(Constants.CURRENCY_KEY)
        viewModelScope.launch(IO) {
            getCurrencyUseCase().collect { selectedCurrencyPref ->
                _currency.value = selectedCurrencyPref[selectedCurrency] ?: ""
            }
        }

        val expenseLimit = doublePreferencesKey(Constants.EXPENSE_LIMIT_KEY)
        viewModelScope.launch(IO) {
            getExpenseLimitUseCase().collect { preferences ->
                _expenseLimit.value = preferences[expenseLimit] ?: 0.00
            }
        }

        val limitKey = booleanPreferencesKey(Constants.LIMIT_KEY)
        viewModelScope.launch(IO) {
            getLimitKeyUseCase().collect { preferences ->
                _reminderLimit.value = preferences[limitKey] ?: false
            }
        }
    }

    fun eraseTransaction() {
        viewModelScope.launch(IO) {
            // reset account
            insertAccountsUseCase(
                listOf(
                    AccountDto(1, Account.CASH.title, 0.0, 0.0, 0.0),
                    AccountDto(2, Account.BANK.title, 0.0, 0.0, 0.0),
                    AccountDto(3, Account.CARD.title, 0.0, 0.0, 0.0)
                )
            )
            // erase transactions
            eraseTransactionUseCase()
        }
    }

    fun editExpenseLimit(amount: Double) {
        viewModelScope.launch(IO) {
            editExpenseLimitUseCase(amount)
        }
    }

    fun writeLimitKey(enabled: Boolean) {
        viewModelScope.launch(IO) {
            editLimitKeyUseCase(enabled)
        }
    }
}