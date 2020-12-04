package cl.iroman.bitcoinwallet.presentation.ui.address.historyTransaction.viewModel

import androidx.lifecycle.Observer
import cl.iroman.bitcoinwallet.domain.model.AddressTransactionModel
import cl.iroman.bitcoinwallet.domain.repository.BlockCypherRepository
import cl.iroman.bitcoinwallet.domain.useCase.GetTransactionsUseCase
import cl.iroman.bitcoinwallet.domain.util.Failure
import cl.iroman.bitcoinwallet.presentation.BaseMockitoTest
import cl.iroman.bitcoinwallet.presentation.util.ResourceState
import junit.framework.Assert.assertSame
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*

@ExperimentalCoroutinesApi
class HistoryTransactionViewModelTest : BaseMockitoTest() {

    private lateinit var historyTransactionViewModel: HistoryTransactionViewModel

    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @Mock
    private lateinit var repository: BlockCypherRepository

    @Mock
    private lateinit var observer: Observer<ResourceState<AddressTransactionModel>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<ResourceState<AddressTransactionModel>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        getTransactionsUseCase = Mockito.spy(GetTransactionsUseCase(repository))
        historyTransactionViewModel = HistoryTransactionViewModel(getTransactionsUseCase)
        historyTransactionViewModel.historyTransactionLiveData.observeForever(observer)
    }

    @After
    fun finish() {
        historyTransactionViewModel.historyTransactionLiveData.removeObserver(observer)
    }

    @Test
    fun `get Transactions Success State`() = runBlockingTest {

        val transactionResponse =
            AddressTransactionModel(listOf(AddressTransactionModel.TransactionItem()))

        Mockito.`when`(repository.getAddressTransaction()).thenReturn(transactionResponse)

        historyTransactionViewModel.getTransactions()

        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())

        with(argumentCaptor.value) {
            assert(this is ResourceState.Success)
            assertSame(this.data, transactionResponse)
        }
    }

    @Test
    fun `get Transactions Empty State`() = runBlockingTest {

        val transactionResponse =
            AddressTransactionModel(listOf())

        Mockito.`when`(repository.getAddressTransaction()).thenReturn(transactionResponse)

        historyTransactionViewModel.getTransactions()

        coroutinesTestRule.testDispatcher.advanceUntilIdle()

        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())

        with(argumentCaptor.value) {
            assert(this is ResourceState.Error)
            assertSame(this.failure, Failure.NoTransaction)
        }
    }

    @Test
    fun `get Transactions Error State`() = runBlockingTest {

        val exception = Exception("An error has occurred")
        Mockito.`when`(repository.getAddressTransaction()).thenThrow(exception)

        historyTransactionViewModel.getTransactions()

        coroutinesTestRule.testDispatcher.advanceUntilIdle()

        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())

        with(argumentCaptor.value) {
            assert(this is ResourceState.Error)
            assert(this.failure is Failure.Error)
            assertSame((this.failure as Failure.Error).throwable, exception)
        }
    }
}