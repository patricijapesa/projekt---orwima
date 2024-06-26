package ferit.patricijapesa.projekt.details

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ferit.patricijapesa.projekt.R
import ferit.patricijapesa.projekt.details.DetailsState.Success
import ferit.patricijapesa.projekt.models.Drive
import ferit.patricijapesa.projekt.home.IconButton


@Composable
fun DetailsPage(
    navController: NavController,
    driveId: String
) {
    val viewModel: DetailsViewModel = viewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(
        key1 = "",
        block = {
            viewModel.getDrive(driveId)
        }
    )

    when(state.value) {
        is DetailsState.Loading -> DetailsLoading()
        is DetailsState.Success -> DetailsPageContent(
            navController = navController,
            drive =  (state.value as Success).state
        )
    }
}

@Composable
fun DetailsLoading() {
}

@Composable
fun DetailsPageContent(
    navController: NavController,
    drive: Drive
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            TopImageAndBar(
                coverImage = "https://media.architecturaldigest.com/photos/63079fc7b4858efb76814bd2/16:9/w_4000,h_2250,c_limit/9.%20DeLorean-Alpha-5%20%5BDeLorean%5D.jpg",
                navController = navController
            )
            ScreenInfo (
                title = drive.destination,
                category = drive.totalSeats.toString()
            )
            BasicInfo(drive)
            Description(drive)
            Reviews(drive)
        }
    }
}
@Composable
fun StopCard(
    image: String,
    title: String,
    subtitle: String
) {
    Column(
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = title,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .width(70.dp)
                .height(70.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.DarkGray
            )
        )
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResource: Int, color: Color = Color.Gray,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(defaultElevation = 12.dp),
    onClick: () -> Unit = {}
) {
    Button(
        contentPadding = PaddingValues(),
        elevation = elevation,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = color
        ),
        shape = RoundedCornerShape(5.dp), modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = Color.Blue
        )
    }
}

@Composable
fun TopImageAndBar(
    coverImage: String,
    navController: NavController
) {
    Box(
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverImage)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp)
                .padding(horizontal = 16.dp)
        ) {
            CircularButton(
                iconResource = R.drawable.ic_arrow_back,
                onClick = { navController.popBackStack() }
            )
            CircularButton(iconResource = R.drawable.ic_favorite)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White
                        ),
                        startY = 100f
                    )
                )
                .align(alignment = Alignment.BottomCenter)
        )
    }
}

@Composable
fun ScreenInfo(
    title: String,
    category: String
) {
    Column {
        Text(
            text = category,
            style = TextStyle(
                color = Color.Blue,
                fontSize = 15.sp,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun InfoColumn(
    @DrawableRes iconResource: Int,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconResource), contentDescription = null,
            tint = Color.Blue,
            modifier = Modifier.height(24.dp)
        )
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BasicInfo(drive: Drive) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(R.drawable.ic_clock, drive.departureTime.toString())
    }
}

@Composable
fun Description(
    drive: Drive
) {
    Text(
        text = drive.destination,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 20.dp)
    )
}

@Composable
fun <T> EasyGrid(nColumns: Int, items: List<T>, content: @Composable (T) -> Unit) {
    Column(Modifier.padding(16.dp)) {
        for (i in items.indices step nColumns) {
            Row {
                for (j in 0 until nColumns) {
                    if (i + j < items.size) {
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier.weight(1f)
                        ) {
                            content(items[i + j])
                        }
                    } else {
                        Spacer(Modifier.weight(1f, fill = true))
                    }
                }
            }
        }
    }
}

@Composable
fun Reviews(drive: Drive) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 16.dp))
    ) {
        Column {
            Text(text = "Price", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Text(text = drive.price, color = Color.DarkGray)
        }
        IconButton(
            iconResource = R.drawable.ic_arrow_right,
            text = "See all",
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Blue),
            /*side = 1*/
        )
    }
}

