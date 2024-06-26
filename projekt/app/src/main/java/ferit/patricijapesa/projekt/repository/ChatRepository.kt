package ferit.patricijapesa.projekt.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import ferit.patricijapesa.projekt.models.Message
import kotlinx.coroutines.tasks.await

class ChatRepository {
    private val db = FirebaseFirestore.getInstance()
    private val messagesCollection = db.collection("messages")

    fun getMessages(chatId: String) = messagesCollection
        .whereEqualTo("chatId", chatId)
        .orderBy("timestamp", Query.Direction.ASCENDING)
        .snapshots()

    suspend fun sendMessage(chatId: String, message: Message) {
        messagesCollection.add(message).await()
    }
}