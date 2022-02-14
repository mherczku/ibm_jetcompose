package hu.hm.ibm_jetcompose.ui.screens.list

import androidx.annotation.WorkerThread
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.ibm_jetcompose.data.model.Item
import hu.hm.ibm_jetcompose.interactor.Interactor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import okhttp3.internal.notifyAll
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ListViewModel @Inject constructor(
    private val interactor: Interactor
) : ViewModel() {

    val loading = mutableStateOf(false)

    /* val items : MutableLiveData<List<Item>> by lazy {
        MutableLiveData()
    } */
    val items2 = mutableListOf<Item>()

    fun getData() {
        viewModelScope.launch {
            Timber.d("Fetching Data")
            loading.value = true
            //items.postValue(interactor.getData())
            items2.addAll(interactor.getData())
            loading.value = false
        }
    }

    fun getDataFlow() = flow<List<Item>> {
        Timber.d("Fetching Data with Flow")
        val playlist = interactor.getData()
        emit(playlist)

    }//.onStart { loading.postValue(true) }.onCompletion { loading.postValue(false) }

    fun fetchMore()  {
        viewModelScope.launch {
            Timber.d("Fetching more data")
            loading.value = true
            //val new = items.value?.plus(interactor.getData())
            items2.addAll(interactor.getData())
            loading.value = false
            //items.postValue(new)
        }
    }

}