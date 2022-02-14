package hu.hm.ibm_jetcompose.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import hu.hm.ibm_jetcompose.R
import hu.hm.ibm_jetcompose.data.model.Item

@Composable
fun DetailScreen(
    item: Item,
    navController: NavHostController
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val (image, title, list) = createRefs()
        Box(modifier = Modifier
            .constrainAs(image) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = 20.dp)
        ) {
            DetailImage(item = item)
        }
        Card( Modifier
            .fillMaxWidth()
            .constrainAs(title) {
                top.linkTo(image.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(vertical = 20.dp)) {

            Text(
                text = item.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(vertical = 20.dp)
            )
        }

        TextLines(
            item = item,
            modifier = Modifier
                .constrainAs(list) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
        )
    }
}

@Composable
private fun TextLines(item: Item, modifier: Modifier) {
    Column(modifier = modifier) {
        TextRow(name = stringResource(R.string.description), value = item.description)
        TextRow(name = stringResource(R.string.username), value = item.userName)
        TextRow(name = stringResource(R.string.email), value = item.email)
        TextRow(name = stringResource(R.string.duration), value = item.durationInSec.toString())
        TextRow(name = stringResource(R.string.mediatype), value = item.mediaType)
        TextRow(name = stringResource(R.string.created), value = item.created)
    }
}

@Composable
private fun TextRow(name: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
            Text(text = value, style = MaterialTheme.typography.body1, textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun DetailImage(item: Item) {
    Card(
        modifier = Modifier.size(80.dp),
    ) {
        CoilImage(
            imageModel = item.avatarURL,
            contentScale = ContentScale.Crop,
            shimmerParams = ShimmerParams(
                baseColor = Color.White,
                highlightColor = Color.Blue,
                durationMillis = 2000,
                dropOff = 0.65f,
                tilt = 20f
            ),
            circularReveal = CircularReveal(1000),
            failure = {
                Text(text = stringResource(R.string.img_request_failed), color = Color.Black)
            }
        )
    }
}