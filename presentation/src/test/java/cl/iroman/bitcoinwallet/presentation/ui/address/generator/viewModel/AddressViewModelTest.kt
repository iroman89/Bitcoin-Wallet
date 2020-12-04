@file:Suppress("UNREACHABLE_CODE")

package cl.iroman.bitcoinwallet.presentation.ui.address.generator.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import cl.iroman.bitcoinwallet.domain.model.AddressKeychainModel
import cl.iroman.bitcoinwallet.domain.repository.BlockCypherRepository
import cl.iroman.bitcoinwallet.domain.useCase.GenerateAddressUseCase
import cl.iroman.bitcoinwallet.domain.useCase.GetAddressUseCase
import cl.iroman.bitcoinwallet.domain.useCase.SaveAddressUseCase
import cl.iroman.bitcoinwallet.domain.util.Failure
import cl.iroman.bitcoinwallet.presentation.BaseMockitoTest
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
    private lateinit var addressStateObserver: Observer<AddressState<AddressKeychainModel>>

    @Mock
    private lateinit var saveAddressStateObserver: Observer<SaveAddressState<Boolean>>

    @Captor
    private lateinit var addressStateArgumentCaptor: ArgumentCaptor<AddressState<AddressKeychainModel>>

    @Captor
    private lateinit var saveAddressStateArgumentCaptor: ArgumentCaptor<SaveAddressState<Boolean>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        getAddressUseCase = Mockito.spy(GetAddressUseCase(repository))
        generateAddressUseCase = Mockito.spy(GenerateAddressUseCase(repository))
        saveAddressUseCase = Mockito.spy(SaveAddressUseCase(repository))

        addressViewModel =
            AddressViewModel(getAddressUseCase, generateAddressUseCase, saveAddressUseCase)
        addressViewModel.addressLiveData.observeForever(addressStateObserver)
        addressViewModel.saveAddressLiveData.observeForever(saveAddressStateObserver)
    }

    @After
    fun finish() {
        addressViewModel.addressLiveData.removeObserver(addressStateObserver)
        addressViewModel.saveAddressLiveData.removeObserver(saveAddressStateObserver)
    }

    @Test
    fun `getAddress Got State`() = runBlockingTest {

        val addressResponse = AddressKeychainModel()
        val flow = flow {
            emit(addressResponse)
        }
        Mockito.`when`(repository.getAddress()).thenReturn(flow)

        addressViewModel.getAddress()

        Mockito.verify(addressStateObserver, Mockito.times(2)).onChanged(
            addressStateArgumentCaptor.capture()
        )

        with(addressStateArgumentCaptor.value) {
            assert(this is AddressState.Got)
            assertEquals(this.data, addressResponse)
        }
    }

    @Test
    fun `getAddress Empty State`() = runBlockingTest {

        val flow = flow {
            emit(throw Throwable("Empty element"))
        }
        Mockito.`when`(repository.getAddress()).thenReturn(flow)

        addressViewModel.getAddress()

        Mockito.verify(addressStateObserver, Mockito.times(2)).onChanged(
            addressStateArgumentCaptor.capture()
        )

        with(addressStateArgumentCaptor.value) {
            assert(this is AddressState.Error)
            assert(this.failure is Failure.Empty)
        }
    }

    @Test
    fun `getAddress Error State`() = runBlockingTest {

        val exception = Exception("An error has occurred")
        Mockito.`when`(repository.getAddress()).thenThrow(exception)

        addressViewModel.getAddress()

        Mockito.verify(addressStateObserver, Mockito.times(2)).onChanged(
            addressStateArgumentCaptor.capture()
        )

        with(addressStateArgumentCaptor.value) {
            assert(this is AddressState.Error)
            assert(this.failure is Failure.Error)
            assertSame((this.failure as Failure.Error).throwable,
                exception)
        }
    }

    @Test
    fun `generate Address Generated State`() = runBlockingTest {

        val addressResponse = AddressKeychainModel()
        Mockito.`when`(repository.generateAddress()).thenReturn(addressResponse)

        addressViewModel.generateAddress()

        Mockito.verify(addressStateObserver, Mockito.times(2)).onChanged(
            addressStateArgumentCaptor.capture()
        )

        with(addressStateArgumentCaptor.value) {
            assert(this is AddressState.Generated)
            assertEquals(this.data, addressResponse)
        }
    }

    @Test
    fun `generate Address Error State`() = runBlockingTest {

        val exception = Exception("An error has occurred")
        Mockito.`when`(repository.generateAddress()).thenThrow(exception)

        addressViewModel.generateAddress()

        Mockito.verify(addressStateObserver, Mockito.times(2)).onChanged(
            addressStateArgumentCaptor.capture()
        )

        with(addressStateArgumentCaptor.value) {
            assert(this is AddressState.Error)
            assert(this.failure is Failure.Error)
            assertSame((this.failure as Failure.Error).throwable, exception)
        }
    }

    @Test
    fun `save Address Saved State`() = runBlockingTest {

        val request = AddressKeychainModel()

        FieldSetter.setField(addressViewModel,
            addressViewModel::class.java.getDeclaredField("_addressLiveData"),
            MutableLiveData(AddressState.Generated(request)))

        Mockito.`when`(repository.saveAddress(request)).thenReturn(Unit)

        addressViewModel.saveAddress()

        Mockito.verify(saveAddressStateObserver, Mockito.times(2)).onChanged(
            saveAddressStateArgumentCaptor.capture()
        )

        with(saveAddressStateArgumentCaptor.value) {
            assert(this is SaveAddressState.Saved)
        }
    }

    @Test
    fun `save Address NoDataToSave Error State`() = runBlockingTest {

        val request = AddressKeychainModel()

        Mockito.`when`(repository.saveAddress(request)).thenReturn(Unit)

        addressViewModel.saveAddress()

        Mockito.verify(saveAddressStateObserver, Mockito.times(1)).onChanged(
            saveAddressStateArgumentCaptor.capture()
        )

        with(saveAddressStateArgumentCaptor.value) {
            assert(this is SaveAddressState.Error)
            assert(this.failure is Failure.NoDataToSave)
        }
    }

    @Test
    fun `save Address Error State`() = runBlockingTest {

        val request = AddressKeychainModel()
        val exception = Exception("An error has occurred")

        FieldSetter.setField(addressViewModel,
            addressViewModel::class.java.getDeclaredField("_addressLiveData"),
            MutableLiveData(AddressState.Generated(request)))

        Mockito.`when`(repository.saveAddress(request)).thenThrow(exception)

        addressViewModel.saveAddress()

        Mockito.verify(saveAddressStateObserver, Mockito.times(2)).onChanged(
            saveAddressStateArgumentCaptor.capture()
        )

        with(saveAddressStateArgumentCaptor.value) {
            assert(this is SaveAddressState.Error)
            assert(this.failure is Failure.Error)
            assertSame((this.failure as Failure.Error).throwable, exception)
        }
    }
}