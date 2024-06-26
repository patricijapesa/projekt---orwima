package ferit.patricijapesa.projekt.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ferit.patricijapesa.projekt.models.Drive
import ferit.patricijapesa.projekt.repository.DriveRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val state: List<Drive>) : HomeState()
}

class MainViewModel (
    private val repository: DriveRepository = DriveRepository()
) : ViewModel() {

    val state = MutableStateFlow<HomeState>(HomeState.Loading)
    init {
        viewModelScope.launch {
            getDrives()
        }
    }

    private suspend fun getDrives() {
        val result = repository.getDrives()
        state.value = HomeState.Success(result)
    }

    fun searchDrives(departure: String, destination: String) {
        viewModelScope.launch {
            val result = repository.searchDrives(departure, destination)
            state.value = HomeState.Success(result)
        }
    }
}