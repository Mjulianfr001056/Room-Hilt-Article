package org.bangkit.roomhiltarticle

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.bangkit.roomhiltarticle.data.DicodingEventRepository
import org.bangkit.roomhiltarticle.data.model.DicodingEventModel
import org.bangkit.roomhiltarticle.util.Result
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DicodingEventRepository
) : ViewModel() {
    private val _eventList = MutableStateFlow<List<DicodingEventModel>>(emptyList())
    val eventList = _eventList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorChannel = Channel<String>()
    val errorMessages = _errorChannel.receiveAsFlow()

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getEvents().collectLatest { result ->
                when (result) {
                    is Result.Success -> handleSuccess(result.data, _eventList)
                    is Result.Error -> handleError(result.message)
                }
            }
            _isLoading.value = false
        }
    }

    fun addEvent(dicodingEvent: DicodingEventModel) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.addEvent(dicodingEvent).collectLatest { result ->
                when (result) {
                    is Result.Success -> fetchEvents()
                    is Result.Error -> handleError(result.message)
                }
            }
            Log.d(TAG, "addEvent: ${eventList.value.size}")
            _isLoading.value = false
        }
    }

    private suspend fun handleError(errorMessage: String?) {
        val error = errorMessage ?: "Terjadi kesalahan"
        Log.e(TAG, "Error: $error")
        _errorChannel.send(error)
    }

    private fun handleSuccess(data: List<DicodingEventModel>?, targetFlow: MutableStateFlow<List<DicodingEventModel>>) {
        targetFlow.value = data ?: emptyList()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}