package hu.hm.ibm_jetcompose.ui.screens.list

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import hu.hm.ibm_jetcompose.data.model.Item
import hu.hm.ibm_jetcompose.data.model.Playlist
import hu.hm.ibm_jetcompose.data.network.Api
import hu.hm.ibm_jetcompose.interactor.Interactor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import okhttp3.internal.notifyAll
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ListViewModel @Inject constructor(
    private val interactor: Interactor,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    val items : MutableLiveData<List<Item>> by lazy {
        MutableLiveData()
    }

    fun getData(): LiveData<List<Item>> {

        val list: MutableLiveData<List<Item>> by lazy {
            MutableLiveData<List<Item>>()
        }

        CoroutineScope(coroutineContext).launch {
            list.postValue(interactor.getData())
        }
        return list
    }

    @WorkerThread
    fun getDataFlow() = flow<List<Item>> {
        Timber.d("Get Data with Flow")
        val playlist = interactor.getData()
        items.postValue(playlist)
        emit(playlist)

    }.flowOn(Dispatchers.IO)


    fun fetchMore()  {
        CoroutineScope(coroutineContext).launch {
            items.postValue(interactor.getData().subList(0,5))
        }
    }

}