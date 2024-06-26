package ferit.patricijapesa.projekt.home
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        RouteFilterSection()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteFilterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Filter routes", style = MaterialTheme.typography.headlineMedium)

        TextField(value = "", onValueChange = {}, label = { Text("Departure") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = "", onValueChange = {}, label = { Text("Destination") })

        Button(onClick = {  }) {
            Text("Apply filter")
        }
    }
}