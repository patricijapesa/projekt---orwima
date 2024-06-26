package ferit.patricijapesa.projekt.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Message(val id: String, val senderName: String, val text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen() {
    val messages = remember { getDummyMessages() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Messages") })
        }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                MessageItem(message)
            }
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = message.senderName, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message.text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun getDummyMessages(): List<Message> {
    return listOf(
        Message(id = "1", senderName = "John Doe", text = "Hello, I am interested in your drive."),
        Message(id = "2", senderName = "Jane Smith", text = "Is the seat still available?"),
        Message(id = "3", senderName = "Mark Brown", text = "Can we change the departure time?")
    )
}