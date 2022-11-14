package com.example.tele2demo.presentaion

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2demo.domain.Repository
import com.example.tele2demo.domain.Response
import com.example.tele2demo.domain.ViewState
import com.example.tele2demo.domain.model.Branch
import com.example.tele2demo.domain.model.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BranchViewModel(
    private val repository: Repository,
    private val ioContext: CoroutineContext = Dispatchers.IO
) : ViewModel() {

    private val cities = mutableListOf<City>()
    private val branches = mutableListOf<Branch>()
    private var selectedCity: City? = null
    private var selectedBranch: Branch? = null

    private val _viewState = MutableLiveData<ViewState<BranchViewState>>()
    val viewState: LiveData<ViewState<BranchViewState>> = _viewState

    init {
        viewModelScope.launch {
            getCities()
        }
    }

    private suspend fun getCities() = withContext(ioContext) {
        when (val result = repository.getCities()) {
            is Response.Success -> {
                cities.clear()
                cities.addAll(result.data)
                _viewState.postValue(ViewState.Data(BranchViewState.Cities(result.data)))
            }
            is Response.Failure -> {}
        }
    }

    fun onCitySelected(position: Int) {
        selectedCity = cities[position]
        getBranches(selectedCity!!)
    }

    private fun getBranches(city: City) {
        viewModelScope.launch {
            when (val result = repository.getBranches(city.id)) {
                is Response.Success -> {
                    branches.clear()
                    branches.addAll(result.data)
                    _viewState.postValue(ViewState.Data(BranchViewState.Branches(result.data)))
                }
                is Response.Failure -> {}
            }
        }
    }

    fun onBranchSelected(position: Int) {
        selectedBranch = branches[position]
    }

    fun onScannerResult(deviceId: String?) {
        _viewState.value = ViewState.Data(
            BranchViewState.OnDataReady(
                deviceId = deviceId,
                branchId = selectedBranch?.id ?: ""
            )
        )
    }
}