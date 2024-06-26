package ferit.patricijapesa.projekt.details

import ferit.patricijapesa.projekt.models.Drive
import ferit.patricijapesa.projekt.repository.DriveRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val state: Drive) : DetailsState()
}
class DetailsViewModel(
    private val repository: DriveRepository = DriveRepository()
) : ViewModel() {

    val state = MutableStateFlow<DetailsState>(DetailsState.Loading)

    fun getDrive(id: String) {
        viewModelScope.launch {
            val result = repository.getDriveById(id)
            state.value = DetailsState.Success(result)
        }
    }
}