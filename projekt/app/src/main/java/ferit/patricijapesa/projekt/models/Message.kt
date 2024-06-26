package ferit.patricijapesa.projekt.models


import com.google.firebase.Timestamp

class Message (
    val chatId: String = "",
    val senderId: String = "",
    val messageId: String = "",
    val text: String = "",
    val timestamp: Timestamp = Timestamp.now()
)