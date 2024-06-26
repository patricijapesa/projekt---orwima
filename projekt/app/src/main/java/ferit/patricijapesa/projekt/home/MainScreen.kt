package ferit.patricijapesa.projekt.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import ferit.patricijapesa.projekt.R
import ferit.patricijapesa.projekt.models.Drive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (
    navController: NavController,
) {
    val viewModel: MainViewModel = viewModel()
    val state = viewModel.state.collectAsState()

    when(state.value) {
        is HomeState.Loading -> Loading()
        is HomeState.Success -> MainScreenContent(
            navController = navController,
            state = (state.value as HomeState.Success).state
        )
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController as NavHostController,
            startDestination = "search",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("search") { SearchScreen() }
            composable("messages") { MessagesScreen() }
            composable("profile") { UserProfileScreen() }
        }
    }
}

@Composable
fun Loading() {

}

@Composable
fun MainScreenContent(
    navController: NavController,
    state: List<Drive>
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ScreenTitle(
            title = stringResource(id = R.string.home_subtitle),
            subtitle = stringResource(id = R.string.home_title)
        )
        SearchBar(
            iconResource = R.drawable.ic_search,
            labelText = "Search"
        )
        DriveCategories()
        DrivesContainer(
            navController = navController,
            drives = state
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            iconResource = R.drawable.ic_plus,
            text = stringResource(id = R.string.home_add_new_drive)
        )
    }
}

@Composable
fun ScreenTitle(
    title: String,
    subtitle: String
) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = subtitle,
            style = TextStyle(
                color = Color.LightGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
            ),
            modifier = Modifier.padding(horizontal = 15.dp)
        )
        Text(
            text = title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    @DrawableRes iconResource: Int,
    labelText: String,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        placeholderColor = Color.DarkGray,
        textColor = Color.DarkGray,
        unfocusedLabelColor = Color.DarkGray,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
) {
    val searchInput = remember { mutableStateOf("") }
    TextField(
        value = searchInput.value,
        onValueChange = { searchInput.value = it },
        label = {
            Text(labelText)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = labelText,
                tint = Color.DarkGray,
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
            )
        },
        colors = colors,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun TabButton(
    text: String, isActive: Boolean, modifier: Modifier, onClick: () -> Unit
) {
    Button(shape = RoundedCornerShape(24.dp),
        elevation = null,
        colors = if (isActive) ButtonDefaults.buttonColors(
            contentColor = Color.White, containerColor = Color.Blue
        ) else ButtonDefaults.buttonColors(contentColor = Color.DarkGray, containerColor = Color.LightGray),
        modifier = modifier,
        onClick = { onClick() }) {
        Text(text)
    }
}


@Composable
fun DriveCategories() {
    val currentActiveButton = remember { mutableStateOf(0) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TabButton(
            text = stringResource(id = R.string.all),
            isActive = currentActiveButton.value == 0,
            modifier = Modifier.weight(1f)
        ) {
            currentActiveButton.value = 0
        }
        TabButton(
            text = stringResource(id = R.string.nearby),
            isActive = currentActiveButton.value == 1,
            modifier = Modifier.weight(1f)
        ) {
            currentActiveButton.value = 1
        }
    }
}


@Composable
fun IconButton(
    @DrawableRes
    iconResource: Int,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
) {
    Button(
        onClick = { /*TODO*/ },
        colors = colors,
    ) {
        Row {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = text
            )
            Spacer(Modifier.width(2.dp))
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }
    }
}

@Composable
fun Chip(
    text: String,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Blue,
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = textColor,
                fontSize = 12.sp
            )
        )
    }
}

@Composable
fun DrivesContainer(
    navController: NavController,
    drives: List<Drive>
) {
    LazyRow {
        item { Spacer(modifier = Modifier.width(16.dp)) }
        drives.forEach{ drive ->
            item {
                DriveCard(
                    title = drive.destination,
                    driveId = drive.driveId,
                    navController = navController
                )
            }
            item { Spacer(modifier = Modifier.width(8.dp)) }
        }
        item { Spacer(modifier = Modifier.width(16.dp)) }
    }
}


@Composable
fun DriveCard(
    title: String,
    driveId: String,
    navController: NavController
) {
    val timeString = stringResource(id = R.string.two_hours)
    val stopString = stringResource(id = R.string.three_stops)
    Card(
        modifier = Modifier
            .clickable {

            }
            .width(215.dp)
            .height(326.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title, style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White, fontWeight = FontWeight.Medium
            ), modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Chip(text = timeString)
            Spacer(modifier = Modifier.width(8.dp))
            Chip(text = stopString)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem("search", Icons.Filled.Search, "Search"),
        //NavigationItem("messages", Icons.Filled.Message, "Messages"),
        NavigationItem("profile", Icons.Filled.Person, "Profile")
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class NavigationItem(val route: String, val icon: ImageVector, val label: String)

