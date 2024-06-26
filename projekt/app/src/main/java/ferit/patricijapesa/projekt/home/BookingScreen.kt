package ferit.patricijapesa.projekt.home

import android.annotation.SuppressLint
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

data class Booking(val id: String, val driveInfo: String, val status: String)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen() {
    val bookings = remember { getDummyBookings() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My reservations") })
        }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(bookings) { booking ->
                BookingItem(booking)
            }
        }
    }
}

@Composable
fun BookingItem(booking: Booking) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = booking.driveInfo, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Status: ${booking.status}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun getDummyBookings(): List<Booking> {
    return listOf(
        Booking(id = "1", driveInfo = "Zagreb - Split", status = "Confirmed"),
        Booking(id = "2", driveInfo = "Zagreb - Rijeka", status = "Pending"),
        Booking(id = "3", driveInfo = "Zagreb - Osijek", status = "Cancelled")
    )
}