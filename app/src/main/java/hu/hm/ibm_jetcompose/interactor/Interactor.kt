package hu.hm.ibm_jetcompose.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.hm.ibm_jetcompose.data.model.Item
import hu.hm.ibm_jetcompose.data.model.Playlist
import hu.hm.ibm_jetcompose.data.network.Api
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Interactor @Inject constructor(private val api: Api){

    suspend fun getData(): List<Item> {
        Timber.d("Downloading data")
        val a = api.getData()
        val sub = a.playlist.subList(0,15)
        Timber.d("Received ${sub.size} items")
        return sub

    }
}