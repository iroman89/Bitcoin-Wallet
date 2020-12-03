package com.beetrack.bitcoinwallet.presentation.ui.address.balance.viewModel

import androidx.lifecycle.Observer
import com.beetrack.bitcoinwallet.domain.model.AddressBalanceModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.useCase.GetAddressBalanceUseCase
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
class BalanceViewModelTest : BaseMockitoTest() {

    private lateinit var balanceViewModel: BalanceViewModel

    private lateinit var getAddressBalanceUseCase: GetAddressBalanceUseCase

    @Mock
    private lateinit var repository: BlockCypherRepository

    @Mock
    private lateinit var observer: Observer<ResourceState<AddressBalanceModel>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<ResourceState<AddressBalanceModel>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        getAddressBalanceUseCase = Mockito.spy(GetAddressBalanceUseCase(repository))
        balanceViewModel = BalanceViewModel(getAddressBalanceUseCase)
        balanceViewModel.addressBalanceLiveData.observeForever(observer)
    }

    @After
    fun finish() {
        balanceViewModel.addressBalanceLiveData.removeObserver(observer)
    }

    @Test
    fun `get AddressBalance Success State`() = runBlockingTest {

        val response = AddressBalanceModel("")
        Mockito.`when`(repository.getAddressBalance()).thenReturn(response)

        balanceViewModel.getAddressBalance()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is ResourceState.Success)
            assertSame(this.data, response)
        }
    }

    @Test
    fun `get AddressBalance Empty State`() = runBlockingTest {

        val exception = IllegalArgumentException()
        Mockito.`when`(repository.getAddressBalance()).thenThrow(exception)

        balanceViewModel.getAddressBalance()

        coroutinesTestRule.testDispatcher.advanceUntilIdle()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is ResourceState.Error)
            assert(this.failure is Failure.Empty)
        }
    }

    @Test
    fun `get AddressBalance Error State`() = runBlockingTest {

        val exception = Exception("An error has occurred")
        Mockito.`when`(repository.getAddressBalance()).thenThrow(exception)

        balanceViewModel.getAddressBalance()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is ResourceState.Error)
            assert(this.failure is Failure.Error)
            assertSame((this.failure as Failure.Error).throwable, exception)
        }
    }
}