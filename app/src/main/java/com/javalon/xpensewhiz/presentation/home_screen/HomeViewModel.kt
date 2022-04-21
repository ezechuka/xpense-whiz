package com.javalon.xpensewhiz.presentation.home_screen

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.common.Constants
import com.javalon.xpensewhiz.data.local.entity.TransactionDto
import com.javalon.xpensewhiz.domain.model.Transaction
import com.javalon.xpensewhiz.domain.usecase.read_database.GetAccountUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetAccountsUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetCurrentDayExpTransactionUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetDailyTransactionUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetMonthlyTransactionUseCase
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetCurrencyUseCase
import com.javalon.xpensewhiz.domain.usecase.write_database.InsertAccountsUseCase
import com.javalon.xpensewhiz.domain.usecase.write_database.InsertNewTransactionUseCase
import com.javalon.xpensewhiz.presentation.ui.theme.Amber500
import com.javalon.xpensewhiz.presentation.ui.theme.GreenAlpha700
import com.javalon.xpensewhiz.presentation.ui.theme.Red500
import com.javalon.xpensewhiz.presentation.ui.theme.businessBg
import com.javalon.xpensewhiz.presentation.ui.theme.clothBg
import com.javalon.xpensewhiz.presentation.ui.theme.electricBg
import com.javalon.xpensewhiz.presentation.ui.theme.food_drink
import com.javalon.xpensewhiz.presentation.ui.theme.gadgetBg
import com.javalon.xpensewhiz.presentation.ui.theme.giftBg
import com.javalon.xpensewhiz.presentation.ui.theme.groceryBg
import com.javalon.xpensewhiz.presentation.ui.theme.healthBg
import com.javalon.xpensewhiz.presentation.ui.theme.homeBg
import com.javalon.xpensewhiz.presentation.ui.theme.leisureBg
import com.javalon.xpensewhiz.presentation.ui.theme.miscBg
import com.javalon.xpensewhiz.presentation.ui.theme.petBg
import com.javalon.xpensewhiz.presentation.ui.theme.schBg
import com.javalon.xpensewhiz.presentation.ui.theme.snackBg
import com.javalon.xpensewhiz.presentation.ui.theme.subBg
import com.javalon.xpensewhiz.presentation.ui.theme.taxiBg
import com.javalon.xpensewhiz.presentation.ui.theme.travelBg
import com.javalon.xpensewhiz.presentation.ui.theme.vehicleBg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertDailyTransactionUseCase: InsertNewTransactionUseCase,
    private val insertAccountsUseCase: InsertAccountsUseCase,
    private val getDailyTransactionUseCase: GetDailyTransactionUseCase,
    private val getMonthlyTransactionUseCase: GetMonthlyTransactionUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getCurrentDayExpTransactionUseCase: GetCurrentDayExpTransactionUseCase
) : ViewModel() {
    private var _tabButton = MutableStateFlow(TabButton.TODAY)
    val tabButton: StateFlow<TabButton> = _tabButton

    private var _category = MutableStateFlow(Category.FOOD_DRINK)
    val category: StateFlow<Category> = _category

    private var _account = MutableStateFlow(Account.CASH)
    val account: StateFlow<Account> = _account

    private var _transactionAmount = MutableStateFlow("0.00")
    val transactionAmount: StateFlow<String> = _transactionAmount

    private var _dailyTransaction = MutableStateFlow(emptyList<Transaction>())
    val dailyTransaction: StateFlow<List<Transaction>> = _dailyTransaction

    private var _monthlyTransaction = MutableStateFlow(mapOf<String, List<Transaction>>())
    val monthlyTransaction: StateFlow<Map<String, List<Transaction>>> = _monthlyTransaction

    private var _transactionTitle = MutableStateFlow(String())
    val transactionTitle: StateFlow<String> = _transactionTitle

    private var _showInfoBanner = MutableStateFlow(false)
    val showInfoBanner: StateFlow<Boolean> = _showInfoBanner

    private var _isDecimal = MutableStateFlow(false)
    private var decimal: String = String()

    var totalIncome = MutableStateFlow(0.0)
        private set

    var totalExpense = MutableStateFlow(0.0)
        private set

    var formattedDate = MutableStateFlow(String())
        private set

    var date = MutableStateFlow(String())
        private set

    var currentTime = MutableStateFlow(Calendar.getInstance().time)
        private set

    var selectedCurrencyCode = MutableStateFlow(String())
        private set

    init {
        val currentDate = getDate()
        Log.d("date", currentDate)
        formattedDate.value = currentTime.value.getFormattedDate()
        date.value = currentDate
        currencyFormat()

        viewModelScope.launch(IO) {
            getCurrentDayExpTransactionUseCase().collect {
                val trx = it.map { trans -> trans.toTransaction() }
                Log.d("current", trx.toString())
            }
        }

        viewModelScope.launch(IO) {
            getDailyTransactionUseCase(currentDate).collect {
                it?.let { expenses ->
                    _dailyTransaction.value =
                        expenses.map { dailyExpense -> dailyExpense.toTransaction() }.reversed()
                }
            }
        }

        viewModelScope.launch(IO) {
            getMonthlyTransactionUseCase().collect { allTransaction ->
                allTransaction?.let {
                    val newMonthlyExpenses = allTransaction.map { it.toTransaction() }.reversed()
                    _monthlyTransaction.value = newMonthlyExpenses.groupBy { monthlyExpense ->
                        monthlyExpense.date.getFormattedDate()
                    }
                }
            }
        }

        viewModelScope.launch(IO) {
            getAccountsUseCase().collect { accountsDto ->
                val accounts = accountsDto.map { it.toAccount() }
                val income = calculateTransaction(accounts.map { it.income })
                val expense = calculateTransaction(accounts.map { it.expense })
                Log.d("INCOME", income.toString())

                totalIncome.value = income
                totalExpense.value = expense
            }
        }
    }



    @SuppressLint("SimpleDateFormat")
    private fun getDate(): String {
        return SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
    }

    private fun calculateTransaction(transactions: List<Double>): Double {
        return transactions.sumOf {
            it
        }
    }

    fun selectTabButton(button: TabButton) {
        _tabButton.value = button
    }

    fun selectCategory(category: Category) {
        _category.value = category
    }

    fun selectAccount(account: Account) {
        _account.value = account
    }

    fun setTransactionTitle(title: String) {
        _transactionTitle.value = title
    }

    fun setCurrentTime(time: Date) {
        currentTime.value = time
    }

    fun insertDailyTransaction(
        date: String,
        amount: Double,
        category: String,
        transactionType: String,
        transactionTitle: String
    ) {
        Log.d("INFO", amount.toString())
        viewModelScope.launch(IO) {
            if (amount <= 0.0) {
                _showInfoBanner.value = true
                delay(2000)
                _showInfoBanner.value = false
                return@launch
            }

            val newTransaction = TransactionDto(
                currentTime.value,
                date,
                amount,
                account.value.title,
                category,
                transactionType,
                transactionTitle
            )
            insertDailyTransactionUseCase(newTransaction)

            if (transactionType == Constants.INCOME) {
                val currentAccount = getAccountUseCase(account.value.title).first()
                Log.d("ACCOUNT", account.value.title)
                val newIncomeAmount = currentAccount.income + amount
                val balance = newIncomeAmount - currentAccount.expense

                currentAccount.income = newIncomeAmount
                currentAccount.balance = balance

                insertAccountsUseCase(listOf(currentAccount))
            } else {
                val currentAccount = getAccountUseCase(account.value.title).first()
                val newExpenseAmount = currentAccount.expense + amount
                val balance = currentAccount.income - newExpenseAmount

                currentAccount.expense = newExpenseAmount
                currentAccount.balance = balance

                insertAccountsUseCase(listOf(currentAccount))
            }
        }
    }

    fun setTransaction(amount: String) {
        val value = _transactionAmount.value
        val whole = value.substring(0, value.indexOf("."))

        if (amount == ".") {
            _isDecimal.value = true
            return
        }

        if (_isDecimal.value) {
            if (decimal.length == 2) {
                decimal = decimal.substring(0, decimal.length - 1) + amount
            } else {
                decimal += amount
            }
            val newDecimal = decimal.toDouble() / 100.0
            _transactionAmount.value = String.format("%.2f", whole.toDouble() + newDecimal)
            return
        }

        if (whole == "0") {
            _transactionAmount.value = String.format("%.2f", amount.toDouble())
        } else {
            _transactionAmount.value = String.format("%.2f", (whole + amount).toDouble())
        }
    }

    fun backspace() {
        val value = _transactionAmount.value
        var whole = value.substring(0, value.indexOf("."))

        if (value == "0.00") {
            return
        }

        if (_isDecimal.value) {
            decimal = if (decimal.length == 2) {
                decimal.substring(0, decimal.length - 1)
            } else {
                _isDecimal.value = false
                "0"
            }
            val newDecimal = decimal.toDouble() / 100.0
            _transactionAmount.value = String.format("%.2f", whole.toDouble() + newDecimal)
            decimal = String()

            return
        }

        whole = if (whole.length - 1 == 0)
            "0"
        else
            whole.substring(0, whole.length - 1)
        _transactionAmount.value = String.format("%.2f", whole.toDouble())
    }

    fun displayTransaction(
        transactionDate: String?,
        transactionPos: Int?,
        transactionStatus: Int?
    ) {
        if (transactionPos != -1 && transactionStatus != -1) {
            val transaction = if (transactionStatus == 0)
                dailyTransaction.value[transactionPos!!]
            else {
                transactionDate?.let {
                    monthlyTransaction.value[it]!![transactionPos!!]
                }
            }

            setTransactionTitle(transaction!!.title)
            currentTime.value = transaction.date
            Account.values().forEach {
                if (it.title == transaction.account)
                    selectAccount(it)
                return@forEach
            }
            _transactionAmount.value = transaction.amount.toString()
            Category.values().forEach {
                if (it.title == transaction.category)
                    selectCategory(it)
            }
        }
    }

    fun updateTransaction(transactionDate: String?, transactionPos: Int?, transactionStatus: Int?) {
        if (transactionPos != -1 && transactionStatus != -1) {
            val transaction = if (transactionStatus == 0)
                dailyTransaction.value[transactionPos!!]
            else {
                transactionDate?.let {
                    monthlyTransaction.value[it]!![transactionPos!!]
                }
            }
            viewModelScope.launch(IO) {
                if (transactionAmount.value.toDouble() != transaction!!.amount) {   // new amount

                    val currentAccount = getAccountUseCase(account.value.title).first()
                    if (transaction.transactionType == TransactionType.INCOME.title) {
                        currentAccount.income = currentAccount.income - transaction.amount
                        currentAccount.income =
                            currentAccount.income + transactionAmount.value.toDouble()
                    } else {
                        currentAccount.expense = currentAccount.expense - transaction.amount
                        currentAccount.expense =
                            currentAccount.expense + transactionAmount.value.toDouble()
                    }
                    insertAccountsUseCase(listOf(currentAccount))
                }
                val updateTransaction = TransactionDto(
                    transaction.date,
                    transaction.dateOfEntry,
                    transactionAmount.value.toDouble(),
                    account.value.title,
                    category.value.title,
                    transaction.transactionType,
                    transactionTitle.value
                )
                insertDailyTransactionUseCase(updateTransaction)
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

fun Date.getFormattedDate(): String {
    val dayOfWeek = DateFormat.format("EEE", this)
    val day = DateFormat.format("dd", this)
    val monthAbbr = DateFormat.format("MMM", this)
    val year = DateFormat.format("yyyy", this)

    return "$dayOfWeek $day, $monthAbbr"
}

fun String.amountFormat() : String {
    val amountFormatter = DecimalFormat("#,##0.00")
    return " " + amountFormatter.format(this.toDouble())
}

enum class TabButton(val title: String) {
    TODAY("Today"), MONTH("Month")
}

enum class TransactionType(val title: String) {
    INCOME("income"), EXPENSE("expense")
}

enum class Account(val title: String, val iconRes: Int, val color: Color) {
    CASH("Cash", R.drawable.cash, Amber500),
    BANK("Bank", R.drawable.bank, GreenAlpha700),
    CARD("Card", R.drawable.credit_card, Red500)
}

enum class Category(
    val title: String,
    val iconRes: Int,
    val bgRes: Color,
    val colorRes: Color = Color.White
) {
    FOOD_DRINK("Food & Drink", R.drawable.drink, food_drink, Color.Black),
    CLOTHING("Clothing", R.drawable.clothing, clothBg, Color.Black),
    HOME("Home", R.drawable.home, homeBg, Color.Black),
    HEALTH("Health", R.drawable.health, healthBg),
    SCHOOL("School", R.drawable.school, schBg),
    GROCERY("Grocery", R.drawable.grocery, groceryBg, Color.Black),
    ELECTRICITY("Electricity", R.drawable.electricity, electricBg, Color.Black),
    BUSINESS("Business", R.drawable.business, businessBg, Color.Black),
    VEHICLE("Vehicle", R.drawable.vehicle, vehicleBg),
    TAXI("Taxi", R.drawable.taxi, taxiBg),
    LEISURE("Leisure", R.drawable.leisure, leisureBg, Color.Black),
    GADGET("Gadget", R.drawable.gadget, gadgetBg),
    TRAVEL("Travel", R.drawable.travel, travelBg, Color.Black),
    SUBSCRIPTION("Subscription", R.drawable.sub, subBg),
    PET("Pet", R.drawable.pet, petBg, Color.Black),
    SNACK("Snack", R.drawable.snack, snackBg, Color.Black),
    GIFT("Gift", R.drawable.gift, giftBg, Color.Black),
    MISC("Miscellaneous", R.drawable.misc, miscBg)
}
