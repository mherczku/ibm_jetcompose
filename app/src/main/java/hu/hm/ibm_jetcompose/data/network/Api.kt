package hu.hm.ibm_jetcompose.data.network

import hu.hm.ibm_jetcompose.data.model.Item
import hu.hm.ibm_jetcompose.data.model.Playlist
import retrofit2.http.GET


interface Api {

    @GET("/api/")
    suspend fun getData(): Playlist

}