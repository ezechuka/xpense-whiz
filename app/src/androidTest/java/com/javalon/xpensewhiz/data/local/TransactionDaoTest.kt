package com.javalon.xpensewhiz.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.javalon.xpensewhiz.data.local.entity.AccountDto
import com.javalon.xpensewhiz.data.local.entity.TransactionDto
import com.javalon.xpensewhiz.presentation.home_screen.Account
import com.javalon.xpensewhiz.presentation.home_screen.Category
import com.javalon.xpensewhiz.presentation.home_screen.TransactionType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var transactionDatabase: TransactionDatabase
    lateinit var transactionDao: TransactionDao
    lateinit var newTrx: MutableList<TransactionDto>
    lateinit var accounts: List<AccountDto>
    lateinit var date: Date
    lateinit var dateOfEntry: String

    @Before
    fun setup() {
        transactionDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TransactionDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        transactionDao = transactionDatabase.transactionDao
        newTrx = mutableListOf()
        for (i in 1..2) {
            date = Calendar.getInstance().time
            dateOfEntry = SimpleDateFormat("yyyy-MM-dd").format(date)
            newTrx.add(
                TransactionDto(
                    date = date,
                    dateOfEntry = dateOfEntry,
                    amount = 500.0,
                    account = "Cash",
                    category = Category.FOOD_DRINK.title,
                    transactionType = "expense",
                    title = "Lunch snack"
                )
            )
        }
        // refresh time
        date = Calendar.getInstance().time
        dateOfEntry = SimpleDateFormat("yyyy-MM-dd").format(date)
        newTrx.add(
            TransactionDto(
                date = date,
                dateOfEntry = dateOfEntry,
                amount = 500.0,
                account = "Cash",
                category = Category.FOOD_DRINK.title,
                transactionType = "income",
                title = "Lunch snack"
            )
        )
        accounts = listOf (
            AccountDto(1, "Cash", 0.0, 0.0, 0.0),
            AccountDto(2, "Card", 0.0, 0.0, 0.0),
            AccountDto(3, "Bank", 0.0, 0.0, 0.0)
        )
    }

    @After
    fun teardown() {
        transactionDatabase.close()
    }

    @Test
    fun `insert transaction adds a new record to the transaction table`() = runBlocking {
        transactionDao.insertTransaction(newTrx[0])
        transactionDao.getAllTransaction().test {
            val trx = awaitItem()
            assertThat(trx.size).isEqualTo(1)
        }
    }

    @Test
    fun `insert account adds a new record to the account table`() = runBlocking {
        transactionDao.insertAccounts(accounts)
        transactionDao.getAccounts().test {
            val allAccounts = awaitItem()
            assertThat(allAccounts.size).isEqualTo(3)
        }
    }

    @Test
    fun `retrieving daily transaction record returns only transactions for current day`() =
        runBlocking {
            transactionDao.insertTransaction(transaction = newTrx[0])
            transactionDao.insertTransaction(transaction = newTrx[1])

            transactionDao.getDailyTransaction("2022/04/23").test {
                val trx = awaitItem()
                assertThat(trx.size).isEqualTo(1)
            }
        }

    @Test
    fun `retrieving account record by account type returns only the specific account`() =
        runBlocking {
            transactionDao.insertAccounts(accounts)
            transactionDao.getAccount(Account.CASH.title).test {
                val account = awaitItem()
                assertThat(account.accountType).isEqualTo(Account.CASH.title)
            }
        }

    @Test
    fun `retrieiving transaction record by transaction type returns only the specific transaction`() =
        runBlocking {
            transactionDao.insertTransaction(newTrx[0])
            transactionDao.insertTransaction(newTrx[1])
            transactionDao.insertTransaction(newTrx[2])
            transactionDao.getTransactionByType(TransactionType.EXPENSE.title).test {
                val trx = awaitItem()
                assertThat(trx.size).isEqualTo(2)
            }
        }

    @Test
    fun `retrieving transactions by account type returns only transactions performed on the account`() =
        runBlocking {
            transactionDao.insertTransaction(newTrx[0])
            transactionDao.getTransactionByAccount(Account.CASH.title).test {
                val trx = awaitItem()
                assertThat(trx[0].account).isEqualTo(Account.CASH.title)
            }
        }

    @Test
    fun `retrieving all records in account table`() = runBlocking {
        transactionDao.insertAccounts(accounts)
        transactionDao.getAccounts().test {
            val accounts = awaitItem()
            assertThat(accounts.size).isEqualTo(3)
        }
    }

    @Test
    fun `erase all transactions clears the records in transaction table`() = runBlocking {
        transactionDao.insertTransaction(newTrx[0])
        transactionDao.insertTransaction(newTrx[1])

        transactionDao.eraseTransaction()
        transactionDao.getAllTransaction().test {
            val trx = awaitItem()
            assertThat(trx.size).isEqualTo(0)
        }
    }

    @Test
    fun `retrieve all expense transactions performed on current day`() = runBlocking {
        transactionDao.insertTransaction(newTrx[0])
        transactionDao.insertTransaction(newTrx[1])

        transactionDao.getCurrentDayExpTransaction().test {
            val trx = awaitItem()
            assertThat(trx.size).isEqualTo(2)
        }
    }

    @Test
    fun `retrieve all expense transactions performed within 30 days`() = runBlocking {
        newTrx.clear()
        for (i in 1..5) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            date = calendar.time
            dateOfEntry = SimpleDateFormat("yyyy-MM-dd").format(date)
            newTrx.add(
                TransactionDto(
                    date = date,
                    dateOfEntry = dateOfEntry,
                    amount = 500.0,
                    account = "Cash",
                    category = Category.FOOD_DRINK.title,
                    transactionType = "expense",
                    title = "Lunch snack"
                )
            )
        }

        newTrx.forEach {
            transactionDao.insertTransaction(it)
        }
        transactionDao.getWeeklyExpTransaction().test {
            val trx = awaitItem()
            assertThat(trx.size).isEqualTo(5)
        }
    }

    @Test
    fun `retrieve all expense transactions performed within seven days`() = runBlocking {
        newTrx.clear()
        for (i in 1..30) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            date = calendar.time
            dateOfEntry = SimpleDateFormat("yyyy-MM-dd").format(date)
            newTrx.add(
                TransactionDto(
                    date = date,
                    dateOfEntry = dateOfEntry,
                    amount = 500.0,
                    account = "Cash",
                    category = Category.FOOD_DRINK.title,
                    transactionType = "expense",
                    title = "Lunch snack"
                )
            )
        }

        newTrx.forEach {
            transactionDao.insertTransaction(it)
        }
        transactionDao.getWeeklyExpTransaction().test {
            val trx = awaitItem()
            assertThat(trx.size).isEqualTo(30)
        }
    }

    @Test
    fun `retrieve all transactions performed three days ago`() = runBlocking {
        newTrx.clear()
        for (i in 1..4) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            date = calendar.time
            dateOfEntry = SimpleDateFormat("yyyy-MM-dd").format(date)
            newTrx.add(
                TransactionDto(
                    date = date,
                    dateOfEntry = dateOfEntry,
                    amount = 500.0,
                    account = "Cash",
                    category = Category.FOOD_DRINK.title,
                    transactionType = "income",
                    title = "salary"
                )
            )
        }

        newTrx.forEach {
            transactionDao.insertTransaction(it)
        }
        transactionDao.get3DayTransaction(TransactionType.INCOME.title).test {
            val trx = awaitItem()
            assertThat(trx.size).isEqualTo(3)
        }
    }

    @Test
    fun `retrieve all transactions performed seven days ago`() = runBlocking {
        newTrx.clear()
        for (i in 1..8) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            date = calendar.time
            dateOfEntry = SimpleDateFormat("yyyy-MM-dd").format(date)
            newTrx.add(
                TransactionDto(
                    date = date,
                    dateOfEntry = dateOfEntry,
                    amount = 500.0,
                    account = "Cash",
                    category = Category.FOOD_DRINK.title,
                    transactionType = "income",
                    title = "salary"
                )
            )
        }

        newTrx.forEach {
            transactionDao.insertTransaction(it)
        }
        transactionDao.get7DayTransaction(TransactionType.INCOME.title).test {
            val trx = awaitItem()
            assertThat(trx.size).isEqualTo(7)
        }
    }

    @Test
    fun `retrieve all transactions performed fourteen days ago`() = runBlocking {
        newTrx.clear()
        for (i in 1..15) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            date = calendar.time
            dateOfEntry = SimpleDateFormat("yyyy-MM-dd").format(date)
            newTrx.add(
                TransactionDto(
                    date = date,
                    dateOfEntry = dateOfEntry,
                    amount = 500.0,
                    account = "Cash",
                    category = Category.FOOD_DRINK.title,
                    transactionType = "income",
                    title = "salary"
                )
            )
        }

        newTrx.forEach {
            transactionDao.insertTransaction(it)
        }
        transactionDao.get3DayTransaction(TransactionType.INCOME.title).test {
            val trx = awaitItem()
            assertThat(trx.size).isEqualTo(14)
        }
    }
}