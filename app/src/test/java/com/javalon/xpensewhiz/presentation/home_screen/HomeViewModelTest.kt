package com.javalon.xpensewhiz.presentation.home_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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

        runBlocking {
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
        runBlocking {
            homeViewModel.dailyTransaction.test {
                val dailyTrx = awaitItem()
                assertThat(dailyTrx.size).isGreaterThan(0)
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `when homeviewmodel is created, retrieve all transactions`() = runBlocking {
        homeViewModel.monthlyTransaction.test {
            val allTrx = awaitItem()
            assertThat(allTrx.size).isGreaterThan(0)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when homeviewmodel is created, retrieve selected currency`() = runBlocking {
        homeViewModel.selectedCurrencyCode.test {
            val currency = awaitItem()
            assertThat(currency).isNotEmpty()
            cancelAndConsumeRemainingEvents()

        }
    }

    @Test
    fun `when homeviewmodel is created, retrieve all income info`() = runBlocking {
        homeViewModel.totalIncome.test {
            val income = awaitItem()
            assertThat(income).isEqualTo(30.0)
        }
    }

    @Test
    fun `when homeviewmodel is created, retrieve all expense info`() = runBlocking {
        homeViewModel.totalExpense.test {
            val expense = awaitItem()
            assertThat(expense).isEqualTo(15.0)
        }
    }
}