package ferit.patricijapesa.projekt.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import ferit.patricijapesa.projekt.models.Drive
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import ferit.patricijapesa.projekt.models.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DriveRepository (
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val drivesCollection: CollectionReference = database.collection("drives")
){
    suspend fun getDrives(): List<Drive> {
        val data = database.collection("drives").get().await()
        val drives = parseDocumentFromDatabase(data.documents)
        return drives
    }

    suspend fun getDriveById(id: String): Drive {
        val data = database.collection("drives").get().await()
        return data.documents.find {
            it.id == id
        }?.let { document -> parseDrive(document)} ?: Drive()
    }


    suspend fun searchDrives(departure: String, destination: String): List<Drive> {
        return drivesCollection
            .whereEqualTo("departure", departure)
            .whereEqualTo("destination", destination)
            .get()
            .await()
            .toObjects(Drive::class.java)
    }

    suspend fun bookDrive(driveId: String, userId: String) {
        val driveRef = drivesCollection.document(driveId)
        database.runTransaction { transaction ->
            val snapshot = transaction.get(driveRef)
            val bookedSeats = snapshot.getLong("bookedSeats") ?: 0
            val totalSeats = snapshot.getLong("totalSeats") ?: 0
            if (bookedSeats < totalSeats) {
                transaction.update(driveRef, "bookedSeats", bookedSeats + 1)
            } else {
                throw Exception("All seats are booked")
            }
        }.await()
    }

    class ChatViewModel(
        private val repository: ChatRepository = ChatRepository()
    ) : ViewModel() {
        private val messages = MutableStateFlow<List<Message>>(emptyList())
    /*
        fun getMessages(chatId: String) {
            repository.getMessages(chatId).AddSnapshotListener { snapshot, _ ->
                val msgs = snapshot?.toObjects(Message::class.java) ?: emptyList()
                messages.value = msgs
            }
        }
    */
        fun sendMessage(chatId: String, text: String, userId: String) {
            viewModelScope.launch {
                val message = Message(chatId = chatId, senderId = userId, text = text)
                repository.sendMessage(chatId, message)
            }
        }
    }

    private fun parseDocumentFromDatabase(documents: List<DocumentSnapshot>): List<Drive> {
        val drives = mutableListOf<Drive>()
        documents.forEach { document ->
            val drive = parseDrive(document)
            drives.add(drive)
        }
        return drives
    }

    private fun parseDrive(document: DocumentSnapshot): Drive {
        var drive = document.toObject(Drive::class.java)
        if (drive != null) {
            val id = document.id
            val stopsNumber = document.getLong("stops")?.toInt()
            if (stopsNumber != null) {
                drive = drive.copy(stops = stopsNumber)
            }
            drive = drive.copy(driveId = id)
            return drive
        } else {
            return Drive()
        }
    }
}




