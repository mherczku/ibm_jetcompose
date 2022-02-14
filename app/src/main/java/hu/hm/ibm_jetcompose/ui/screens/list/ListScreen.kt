package hu.hm.ibm_jetcompose.ui.screens.list

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import hu.hm.ibm_jetcompose.NavScreen
import hu.hm.ibm_jetcompose.R
import hu.hm.ibm_jetcompose.data.model.Item
import hu.hm.ibm_jetcompose.ui.theme.graySurface
import timber.log.Timber


@Composable
fun ListScreen(
    viewModel: ListViewModel,
    navController: NavController
){
    //val items = remember {viewModel.getData()} // remember?
    //val resultList = items.observeAsState(initial = listOf())

    //with flow
    //val resultList2 = items.collectAsState(initial = listOf())

    //transfer to viewModel
    //val a = viewModel.getDataFlow()
    //Timber.d("viewmode getdataflow from composable")
    LaunchedEffect(key1 = 1){
        viewModel.getData()
    }
    val loading = viewModel.loading.value
    //val resultList = viewModel.items.observeAsState(initial = listOf())
    val resultList = viewModel.items2

    val listState = rememberLazyListState()
    listState.OnBottomReached() {
        if(!loading) viewModel.fetchMore()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = listState
        ) {
            items(
                items = resultList,
                itemContent = { it ->
                    ListItem(item = it, navigateTo = {
                        navController.navigate(NavScreen.Detail.route)
                        navController.currentBackStackEntry?.arguments?.putParcelable(NavScreen.Detail.argument0, it)
                    })
                }
            )
            if(loading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

    }

}

@Composable
fun ListItem(item: Item, navigateTo: (Item) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                navigateTo(item)
            },
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row {
            ItemImage(item)
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = item.title, style = typography.h6, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = item.userName, style = typography.caption)
            }
        }
    }
}

@Composable
private fun ItemImage(item: Item) {
    Card(
        modifier = Modifier.size(60.dp),
        backgroundColor = graySurface,
        ) {
        CoilImage(
            imageModel = item.avatarURL,
            contentScale = ContentScale.Crop,
            shimmerParams = ShimmerParams(
                baseColor = graySurface,
                highlightColor = Color.White,
                durationMillis = 2000,
                dropOff = 0.65f,
                tilt = 20f
            ),
            circularReveal = CircularReveal(1000),
            failure = {
                Text(text = stringResource(R.string.img_request_failed), color = Color.White)
            }
        )
    }
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore : () -> Unit
){
    Timber.d("BottomReached")
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false
            Timber.d("indexes: ${lastVisibleItem.index} =?= ${layoutInfo.totalItemsCount-1}")
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    if(shouldLoadMore.value){
        Timber.d("BottomReached and loading more")
        loadMore.invoke()
    }
}