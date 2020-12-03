@file:Suppress("UNREACHABLE_CODE")

package com.beetrack.bitcoinwallet.presentation.ui.address.generator.viewmodel

import androidx.lifecycle.Observer
import com.beetrack.bitcoinwallet.domain.model.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.useCase.GenerateAddressUseCase
import com.beetrack.bitcoinwallet.domain.useCase.GetAddressUseCase
import com.beetrack.bitcoinwallet.domain.useCase.SaveAddressUseCase
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.presentation.BaseMockitoTest
import com.beetrack.bitcoinwallet.presentation.ui.address.generator.viewModel.AddressState
import com.beetrack.bitcoinwallet.presentation.ui.address.generator.viewModel.AddressViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertSame
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.internal.util.reflection.FieldSetter

@ExperimentalCoroutinesApi
class AddressViewModelTest : BaseMockitoTest() {

    private lateinit var addressViewModel: AddressViewModel

    private lateinit var getAddressUseCase: GetAddressUseCase
    private lateinit var generateAddressUseCase: GenerateAddressUseCase
    private lateinit var saveAddressUseCase: SaveAddressUseCase

    @Mock
    private lateinit var repository: BlockCypherRepository

    @Mock
    private lateinit var observer: Observer<AddressState<AddressKeychainModel>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<AddressState<AddressKeychainModel>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        getAddressUseCase = Mockito.spy(GetAddressUseCase(repository))
        generateAddressUseCase = Mockito.spy(GenerateAddressUseCase(repository))
        saveAddressUseCase = Mockito.spy(SaveAddressUseCase(repository))

        addressViewModel =
            AddressViewModel(getAddressUseCase, generateAddressUseCase, saveAddressUseCase)
        addressViewModel.addressLiveData.observeForever(observer)
    }

    @After
    fun finish() {
        addressViewModel.addressLiveData.removeObserver(observer)
    }

    private val getAddressResponse: AddressKeychainModel =
        AddressKeychainModel("dd", "dd", "dd", "dd")

    @Test
    fun `getAddress Got State`() = runBlockingTest {

        val flow = flow {
            emit(getAddressResponse)
        }
        Mockito.`when`(repository.getAddress()).thenReturn(flow)

        addressViewModel.getAddress()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is AddressState.Got)
            assertEquals(this.data, getAddressResponse)
        }
    }

    @Test
    fun `getAddress Empty State`() = runBlockingTest {

        val flow = flow {
            emit(throw Throwable("Empty element"))
        }
        Mockito.`when`(repository.getAddress()).thenReturn(flow)

        addressViewModel.getAddress()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is AddressState.Error)
            assert(this.failure is Failure.Empty)
        }
    }

    @Test
    fun `getAddress Error State`() = runBlockingTest {

        val exception = Exception("An error has occurred")
        Mockito.`when`(repository.getAddress()).thenThrow(exception)

        addressViewModel.getAddress()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is AddressState.Error)
            assert(this.failure is Failure.Error)
            assertSame((this.failure as Failure.Error).throwable,
                exception)
        }
    }

    @Test
    fun `generate Address Generated State`() = runBlockingTest {

        Mockito.`when`(repository.generateAddress()).thenReturn(getAddressResponse)

        addressViewModel.generateAddress()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is AddressState.Generated)
            assertEquals(this.data, getAddressResponse)
        }
    }

    @Test
    fun `generate Address Error State`() = runBlockingTest {

        val exception = Exception("An error has occurred")
        Mockito.`when`(repository.generateAddress()).thenThrow(exception)

        addressViewModel.generateAddress()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is AddressState.Error)
            assert(this.failure is Failure.Error)
            assertSame((this.failure as Failure.Error).throwable, exception)
        }
    }

    @Test
    fun `save Address Saved State`() = runBlockingTest {

        val request = AddressKeychainModel("dd", "dd", "dd", "dd")

        FieldSetter.setField(addressViewModel,
            addressViewModel::class.java.getDeclaredField("currentAddress"),
            request)

        Mockito.`when`(repository.saveAddress(request)).thenReturn(Unit)

        addressViewModel.saveAddress()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is AddressState.Saved)
        }
    }

    @Test
    fun `save Address NoDataToSave State`() = runBlockingTest {

        val request = AddressKeychainModel("dd", "dd", "dd", "dd")

        Mockito.`when`(repository.saveAddress(request)).thenReturn(Unit)

        addressViewModel.saveAddress()

        Mockito.verify(observer, Mockito.times(1)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is AddressState.Error)
            assert(this.failure is Failure.NoDataToSave)
        }
    }

    @Test
    fun `save Address Error State`() = runBlockingTest {

        val request = AddressKeychainModel("dd", "dd", "dd", "dd")
        val exception = Exception("An error has occurred")

        FieldSetter.setField(addressViewModel,
            addressViewModel::class.java.getDeclaredField("currentAddress"),
            request)

        Mockito.`when`(repository.saveAddress(request)).thenThrow(exception)

        addressViewModel.saveAddress()

        Mockito.verify(observer, Mockito.times(2)).onChanged(
            argumentCaptor.capture()
        )

        with(argumentCaptor.value) {
            assert(this is AddressState.Error)
            assert(this.failure is Failure.Error)
            assertSame((this.failure as Failure.Error).throwable, exception)
        }
    }
}