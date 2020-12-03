package com.beetrack.bitcoinwallet.presentation.ui.address.history.viewModel

import androidx.lifecycle.Observer
import com.beetrack.bitcoinwallet.domain.model.AddressTransactionModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.useCase.GetTransactionsUseCase
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.presentation.BaseMockitoTest
import com.beetrack.bitcoinwallet.presentation.util.ResourceState
import junit.framework.Assert.assertSame
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*

@ExperimentalCoroutinesApi
class HistoryViewModelTest : BaseMockitoTest() {

    private lateinit var historyViewModel: HistoryViewModel

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
        historyViewModel = HistoryViewModel(getTransactionsUseCase)
        historyViewModel.historyTransactionLiveData.observeForever(observer)
    }

    @After
    fun finish() {
        historyViewModel.historyTransactionLiveData.removeObserver(observer)
    }

    @Test
    fun `get Transactions Success State`() = runBlockingTest {

        val transactionResponse =
            AddressTransactionModel(listOf(AddressTransactionModel.TransactionItem()))

        Mockito.`when`(repository.getAddressTransaction()).thenReturn(transactionResponse)

        historyViewModel.getTransactions()

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

        historyViewModel.getTransactions()

        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())

        with(argumentCaptor.value) {
            assert(this is ResourceState.Error)
            assertSame(this.failure, Failure.Empty)
        }
    }

    @Test
    fun `get Transactions Error State`() = runBlockingTest {

        val exception = Exception("An error has occurred")
        Mockito.`when`(repository.getAddressTransaction()).thenThrow(exception)

        historyViewModel.getTransactions()

        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())

        with(argumentCaptor.value) {
            assert(this is ResourceState.Error)
            assert(this.failure is Failure.Error)
            assertSame((this.failure as Failure.Error).throwable, exception)
        }
    }
}