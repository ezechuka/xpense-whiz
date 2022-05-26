package com.javalon.xpensewhiz.presentation.home_screen

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.javalon.xpensewhiz.data.repository.FakeTransactionRepository
import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import com.javalon.xpensewhiz.domain.usecase.GetDateUseCase
import com.javalon.xpensewhiz.domain.usecase.GetFormattedDateUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetAccountUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetAccountsUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetAllTransactionUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetCurrentDayExpTransactionUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetDailyTransactionUseCase
import com.javalon.xpensewhiz.domain.usecase.read_database.GetMonthlyExpTransactionUse
import com.javalon.xpensewhiz.domain.usecase.read_database.GetWeeklyExpTransactionUseCase
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetCurrencyUseCase
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetExpenseLimitUseCase
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetLimitDurationUseCase
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetLimitKeyUseCase
import com.javalon.xpensewhiz.domain.usecase.write_database.InsertAccountsUseCase
import com.javalon.xpensewhiz.domain.usecase.write_database.InsertNewTransactionUseCase
import com.javalon.xpensewhiz.domain.usecase.write_database.MockitoHelper.anyObject
import com.javalon.xpensewhiz.util.CoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var datastoreRepository: DatastoreRepository

    private lateinit var getDateUseCase: GetDateUseCase
    private lateinit var getFormattedDateUseCase: GetFormattedDateUseCase
    private lateinit var insertNewTransactionUseCase: InsertNewTransactionUseCase
    private lateinit var insertAccountsUseCase: InsertAccountsUseCase
    private lateinit var getDailyTransactionUseCase: GetDailyTransactionUseCase
    private lateinit var allTransactionUseCase: GetAllTransactionUseCase
    private lateinit var getAccountUseCase: GetAccountUseCase
    private lateinit var getAccountsUseCase: GetAccountsUseCase
    private lateinit var getCurrencyUseCase: GetCurrencyUseCase
    private lateinit var getExpenseLimitUseCase: GetExpenseLimitUseCase
    private lateinit var getLimitDurationUseCase: GetLimitDurationUseCase
    private lateinit var getLimitKeyUseCase: GetLimitKeyUseCase
    private lateinit var getCurrentDayExpTransactionUseCase: GetCurrentDayExpTransactionUseCase
    private lateinit var getWeeklyExpTransactionUseCase: GetWeeklyExpTransactionUseCase
    private lateinit var getMonthlyExpTransactionUse: GetMonthlyExpTransactionUse

    @Before
    fun setUp() {
        transactionRepository = FakeTransactionRepository()
        datastoreRepository = mock()

        getDateUseCase = mock()
        getFormattedDateUseCase = mock()
        insertNewTransactionUseCase = InsertNewTransactionUseCase(transactionRepository)
        insertAccountsUseCase = InsertAccountsUseCase(transactionRepository)
        getDailyTransactionUseCase = GetDailyTransactionUseCase(transactionRepository)
        allTransactionUseCase = GetAllTransactionUseCase(transactionRepository)
        getAccountUseCase = GetAccountUseCase(transactionRepository)
        getAccountsUseCase = GetAccountsUseCase(transactionRepository)
        getCurrencyUseCase = GetCurrencyUseCase(datastoreRepository)
        getExpenseLimitUseCase = GetExpenseLimitUseCase(datastoreRepository)
        getLimitKeyUseCase = GetLimitKeyUseCase(datastoreRepository)
        getLimitDurationUseCase = GetLimitDurationUseCase(datastoreRepository)
        getCurrentDayExpTransactionUseCase =
            GetCurrentDayExpTransactionUseCase(transactionRepository)
        getWeeklyExpTransactionUseCase = GetWeeklyExpTransactionUseCase(transactionRepository)
        getMonthlyExpTransactionUse = GetMonthlyExpTransactionUse(transactionRepository)

        `when`(getDateUseCase())
            .thenReturn("2022-04-28")

        `when`(getFormattedDateUseCase.invoke(anyObject()))
            .thenReturn("Thu 28, Apr")

        runTest {
            `when`(getCurrencyUseCase.invoke()).thenReturn(
                flow {
                    emit("NGN")
                }
            )
        }

        homeViewModel = HomeViewModel(
            getDateUseCase,
            getFormattedDateUseCase,
            insertNewTransactionUseCase,
            insertAccountsUseCase,
            getDailyTransactionUseCase,
            allTransactionUseCase,
            getAccountUseCase,
            getAccountsUseCase,
            getCurrencyUseCase,
            getExpenseLimitUseCase,
            getLimitDurationUseCase,
            getLimitKeyUseCase,
            getCurrentDayExpTransactionUseCase,
            getWeeklyExpTransactionUseCase,
            getMonthlyExpTransactionUse
        )
    }

    @Test
    fun `when homeviewmodel is created, retrieve daily transactions`() =
        runTest {
            launch {
                homeViewModel.dailyTransaction.test {
                    val dailyTrx = awaitItem()
                    assertThat(dailyTrx.size).isGreaterThan(0)
                    cancelAndConsumeRemainingEvents()
                }
            }
            runCurrent()
        }

    @Test
    fun `when homeviewmodel is created, retrieve all transactions`() = runTest {
        launch {
            homeViewModel.monthlyTransaction.test {
                val allTrx = awaitItem()
                assertThat(allTrx.size).isGreaterThan(0)
                cancelAndConsumeRemainingEvents()
            }
        }
        runCurrent()
    }

    @Test
    fun `when homeviewmodel is created, retrieve selected currency`() = runTest {
        launch {
            homeViewModel.selectedCurrencyCode.test {
                val currency = awaitItem()
                assertThat(currency).isNotEmpty()
                cancelAndConsumeRemainingEvents()
            }
        }
        runCurrent()
    }

    @Test
    fun `when homeviewmodel is created, retrieve all income info`() = runTest {
        launch {
            homeViewModel.totalIncome.test {
                val income = awaitItem()
                assertThat(income).isEqualTo(30.0)
                cancelAndConsumeRemainingEvents()
            }
        }
        runCurrent()
    }

    @Test
    fun `when homeviewmodel is created, retrieve all expense info`() = runTest {
        launch {
            homeViewModel.totalExpense.test {
                val expense = awaitItem()
                assertThat(expense).isEqualTo(15.0)
                cancelAndConsumeRemainingEvents()
            }
        }
        runCurrent()
    }

    @Test
    fun `when homeviewmodel is created, retrieve expense limit amount info`() = runTest {
        launch {
            homeViewModel.currentExpenseAmount.test {
                val expenseLimitAmount = awaitItem()
                assertThat(expenseLimitAmount).isGreaterThan(0.0)
                cancelAndConsumeRemainingEvents()
            }
        }
        runCurrent()
    }

    @Test
    fun `when homeviewmodel is created, check if expense limit is enabled`() = runTest {
        launch {
            homeViewModel.limitKey.test {
                val limitEnabled = awaitItem()
                assertThat(limitEnabled).isNotNull()
                cancelAndConsumeRemainingEvents()
            }
        }
        runCurrent()
    }

    @Test
    fun `inserting a transaction, triggers the correct use cases`() = runTest {

    }

    @Test
    fun `inputting amount without point, correctly sets the transaction amount`() {
        homeViewModel.setTransaction("1")
        homeViewModel.setTransaction("0")
        homeViewModel.setTransaction("0")

        assertThat(homeViewModel.transactionAmount.value).isEqualTo("100.00")
    }

    @Test
    fun `inputting amount with point, correctly sets the transaction amount`() {
        homeViewModel.setTransaction("1")
        homeViewModel.setTransaction("0")
        homeViewModel.setTransaction("0")
        homeViewModel.setTransaction(".")
        homeViewModel.setTransaction("3")
        homeViewModel.setTransaction("2")

        assertThat(homeViewModel.transactionAmount.value).isEqualTo("100.32")
    }

    @Test
    fun `when inputting amount with fractional part above 2 digits, truncates it down to 2 digits only`() {
        homeViewModel.setTransaction("1")
        homeViewModel.setTransaction("0")
        homeViewModel.setTransaction("0")
        homeViewModel.setTransaction(".")
        homeViewModel.setTransaction("3")
        homeViewModel.setTransaction("4")
        homeViewModel.setTransaction("1")
        homeViewModel.setTransaction("5")

        assertThat(homeViewModel.transactionAmount.value).isEqualTo("100.35")
    }

    @Test
    fun `backspace correctly deletes rightmost digit from transaction amount without point`() {
        homeViewModel.setTransaction("1")
        homeViewModel.setTransaction("0")
        homeViewModel.setTransaction("0")

        homeViewModel.backspace()

        assertThat(homeViewModel.transactionAmount.value).isEqualTo("10.00")
    }

    @Test
    fun `backspace correctly deletes rightmost digit from transaction amount with point`() {
        homeViewModel.setTransaction("1")
        homeViewModel.setTransaction("0")
        homeViewModel.setTransaction("0")
        homeViewModel.setTransaction(".")
        homeViewModel.setTransaction("3")
        homeViewModel.setTransaction("2")

        homeViewModel.backspace()
        homeViewModel.backspace()

        assertThat(homeViewModel.transactionAmount.value).isEqualTo("100.00")
    }
}