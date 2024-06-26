package ferit.patricijapesa.projekt.models

data class Drive(
    val driveId: String = "",
    val departure: String = "",
    val destination: String = "",
    val price: String = "",
    val driverId: String = "",
    val departureTime: Int = 0,
    val bookedSeats: Int = 0,
    val totalSeats: Int = 0,
    val stops: Int = 0
)