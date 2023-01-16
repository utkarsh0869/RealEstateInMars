package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val status: LiveData<String>
        get() = _status

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties : LiveData<List<MarsProperty>>
            get() = _properties
    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     * Calls our retrofit service and then handles the returned JSON String.
     * getProperties() return a call object, on which we call enqueue to start a network request
     * on a background thread.
     * The enqueue method takes a retrofit callback class as input that contains methods that will
     * be called when the request is complete.
     */
    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            try {
                var listResult = MarsApi.retrofitService.getProperties()
                _status.value = "Success: ${listResult.size} Mars properties retrieved"
                _properties.value = listResult
            } catch (e: java.lang.Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}
