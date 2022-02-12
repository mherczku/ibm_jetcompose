package hu.hm.ibm_jetcompose.model

import hu.hm.ibm_jetcompose.data.model.Item

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */
data class LiveDataCollection<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: List<Item>,
    // represents the network request status to show to the user
    val networkState: NetworkState,
    val refreshState: NetworkState,
    val refresh: () -> Unit
)

enum class State {
    RUNNING,
    SUCCESS,
    FAILED
}

data class NetworkState(
    val state: State,
    val msg: String? = null
) {
    companion object {
        val LOADED = NetworkState(State.SUCCESS)
        val LOADING = NetworkState(State.RUNNING)
        fun error(msg: String?) = NetworkState(State.FAILED, msg)
    }
}