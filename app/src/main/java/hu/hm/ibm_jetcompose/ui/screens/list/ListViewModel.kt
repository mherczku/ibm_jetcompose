package hu.hm.ibm_jetcompose.ui.screens.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.hm.ibm_jetcompose.data.model.Item
import hu.hm.ibm_jetcompose.data.network.Api
import hu.hm.ibm_jetcompose.interactor.Interactor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ListViewModel @Inject constructor(
    private val interactor: Interactor,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    fun getData(): LiveData<List<Item>> {

        val list: MutableLiveData<List<Item>> by lazy {
            MutableLiveData<List<Item>>()
        }

        CoroutineScope(coroutineContext).launch {
            list.postValue(interactor.getData().playlist)
        }
        return list
    }

}