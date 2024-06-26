package ferit.patricijapesa.projekt.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UserProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray)
        ) {
            //profile pic
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Name and surname", style = MaterialTheme.typography.headlineMedium)
        Text("Rating: 4.5/5", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Reviews:", style = MaterialTheme.typography.headlineMedium)
    }
}