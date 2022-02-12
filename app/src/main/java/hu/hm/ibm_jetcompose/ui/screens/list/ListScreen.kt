package hu.hm.ibm_jetcompose.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.glide.GlideImage
import hu.hm.ibm_jetcompose.data.model.Item
import hu.hm.ibm_jetcompose.ui.theme.graySurface


@Composable
fun ListScreen(
    viewModel: ListViewModel
){
    val itemss = remember {viewModel.getData()}
    val resultList = itemss.observeAsState(initial = listOf())
    LazyColumn(
        state = rememberLazyListState()
    ) {
        items(
            items = resultList.value,
            itemContent = {
                ListItem(item = it, navigateToProfile = {})
            }
        )
    }
}

@Composable
fun ListItem(item: Item, navigateToProfile: (Item) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(Modifier.clickable { navigateToProfile(item) }) {
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
            failure = {
                Text(text = "image request failed.", color = Color.White)
            }
        )
    }

}