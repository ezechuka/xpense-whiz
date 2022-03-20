package com.javalon.xpensewhiz.presentation.home_screen

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.data.local.entity.ExpenseDto
import com.javalon.xpensewhiz.domain.model.Expense
import com.javalon.xpensewhiz.domain.usecase.read_database.GetDailyExpenseUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetMonthlyExpenseUseCase
import com.javalon.xpensewhiz.domain.usecase.write_database.InsertNewExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertDailyExpenseUseCase: InsertNewExpenseUseCase,
    private val getDailyExpenseUseCase: GetDailyExpenseUseCase,
    private val getMonthlyExpenseUseCase: GetMonthlyExpenseUseCase
) : ViewModel() {
    private var _tabButton = mutableStateOf(TabButton.TODAY)
    val tabButton: State<TabButton> = _tabButton

    private var _expense = mutableStateOf(Expenses.FOOD_DRINK)
    val expense: State<Expenses> = _expense

    private var _dialogState = mutableStateOf(false)
    val dialogState: State<Boolean> = _dialogState

    private var _expenseAmount = mutableStateOf("0.00")
    val expenseAmount: State<String> = _expenseAmount

    private var _dailyExpense = mutableStateOf(emptyList<Expense>())
    val dailyExpense: State<List<Expense>> = _dailyExpense

    private var _monthlyExpense = mutableStateOf(mapOf<String, List<Expense>>())
    val monthlyExpense: State<Map<String, List<Expense>>> = _monthlyExpense

    private var _showInfoBanner: MutableState<Boolean> = mutableStateOf(false)
    val showInfoBanner: State<Boolean> = _showInfoBanner

    private var _isDecimal = mutableStateOf(false)
    private var decimal: String = String()

    var totalAmountPerDay = mutableStateOf(0.0)
        private set

    var totalAmountMonthly = mutableStateOf(0.0)
        private set

    var formattedDate: MutableState<String> = mutableStateOf(String())
        private set

    var date: MutableState<String> = mutableStateOf(String())
        private set

    init {
        val currentDate = getDate()
        formattedDate.value = Calendar.getInstance().time.getFormattedDate()
        date.value = currentDate

        viewModelScope.launch(IO) {
            getDailyExpenseUseCase(currentDate).collect {
                it?.let { expenses ->
                    _dailyExpense.value = expenses.map { dailyExpense -> dailyExpense.toExpense() }.reversed()
                    totalAmountPerDay.value =
                        String.format("%.2f", calculateExpense(dailyExpense.value)).toDouble()
                }
            }
        }

        viewModelScope.launch(IO) {
            getMonthlyExpenseUseCase().collect { monthlyExpenses ->
                monthlyExpenses?.let {
                    val newMonthlyExpenses = monthlyExpenses.map { it.toExpense() }.reversed()
                    _monthlyExpense.value = newMonthlyExpenses.groupBy { monthlyExpense ->
                        monthlyExpense.date.getFormattedDate()
                    }
                    totalAmountMonthly.value =
                        String.format("%.2f", calculateExpense(newMonthlyExpenses)).toDouble()
                }
            }
        }
    }

    private fun Date.getFormattedDate(): String {
        val dayOfWeek = DateFormat.format("EEE", this)
        val day = DateFormat.format("dd", this)
        val monthAbbr = DateFormat.format("MMM", this)
        val year = DateFormat.format("yyyy", this)

        return "$dayOfWeek $day, $monthAbbr $year"
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(): String {
        return SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)
    }

    fun calculateExpense(expenseInfo: List<Expense>): Double {
        return expenseInfo.sumOf {
            it.amount
        }
    }

    fun selectTabButton(button: TabButton) {
        _tabButton.value = button
    }

    fun selectCategory(expense: Expenses) {
        _expense.value = expense
    }

    fun setDialogState(state: Boolean) {
        _dialogState.value = state
    }

    fun insertDailyExpense(date: String, amount: Double, expenseType: String) {
        Log.d("INFO", amount.toString())
        viewModelScope.launch(IO) {
            if (amount <= 0.0) {
                _showInfoBanner.value = true
                delay(2000)
                _showInfoBanner.value = false
                return@launch
            }
            val calendar = Calendar.getInstance()
            val newExpense = ExpenseDto(calendar.time, date,amount, expenseType)
            insertDailyExpenseUseCase(newExpense)
            val currentExpense = getDailyExpenseUseCase(date).firstOrNull()

            currentExpense?.let {
                totalAmountPerDay.value =
                    String.format("%.2f", calculateExpense(it.map {
                            dailyExpense -> dailyExpense.toExpense()
                    })).toDouble()
            }
        }
    }

    fun addToExpense(amount: Int) {
        val newExpenseAmount =
            String.format("%.2f", _expenseAmount.value.toDouble() + amount)
        _expenseAmount.value = newExpenseAmount
    }

    fun setExpense(amount: String) {
        val value = _expenseAmount.value
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
            _expenseAmount.value = String.format("%.2f", whole.toDouble() + newDecimal)
            return
        }

        if (whole == "0") {
            _expenseAmount.value = String.format("%.2f", amount.toDouble())
        } else {
            _expenseAmount.value = String.format("%.2f", (whole + amount).toDouble())
        }
    }

    fun backspace() {
        val value = _expenseAmount.value
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
            _expenseAmount.value = String.format("%.2f", whole.toDouble() + newDecimal)
            decimal = String()

            return
        }

        whole = if (whole.length - 1 == 0)
            "0"
        else
            whole.substring(0, whole.length - 1)
        _expenseAmount.value = String.format("%.2f", whole.toDouble())
    }
}

enum class TabButton(val title: String) {
    TODAY("Today"), MONTH("Month")
}

enum class Expenses(val title: String, val iconRes: Int) {
    FOOD_DRINK("Food & Drink", R.drawable.food),
    CLOTHING("Clothing", R.drawable.clothing),
    HOME("Home", R.drawable.home),
    HEALTH("Health", R.drawable.health),
    SCHOOL("School", R.drawable.school),
    GROCERY("Grocery", R.drawable.grocery),
    ELECTRICITY("Electricity", R.drawable.electricity),
    BUSINESS("Business", R.drawable.business),
    VEHICLE("Vehicle", R.drawable.vehicle),
    TAXI("Taxi", R.drawable.taxi),
    GADGET("Gadget", R.drawable.gadget),
    TRAVEL("Travel", R.drawable.travel),
    SUBSCRIPTION("Subscription", R.drawable.sub),
    PET("Pet", R.drawable.pet),
    SNACK("Snack", R.drawable.snack),
    GIFT("Gift", R.drawable.gift),
    MISC("Miscellaneous", R.drawable.misc)
}
