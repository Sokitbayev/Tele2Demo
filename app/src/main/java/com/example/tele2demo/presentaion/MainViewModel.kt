package com.example.tele2demo.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tele2demo.domain.LocalRepository
import com.example.tele2demo.domain.Repository
import com.example.tele2demo.domain.Response
import com.example.tele2demo.domain.ViewState
import com.example.tele2demo.domain.model.DeviceInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val localRepository: LocalRepository,
    private val repository: Repository,
    private val ioContext: CoroutineContext = Dispatchers.IO

) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<MainViewState>>()
    val viewState: LiveData<ViewState<MainViewState>> = _viewState

    init {
        val deviceInfo = localRepository.getDeviceInfo()
        _viewState.postValue(ViewState.Data(MainViewState.DataReady(deviceInfo!!)))
        if (localRepository.getBranchId() != null && localRepository.getDeviceId() != null) viewModelScope.launch {
            checkUpdate()
        }
    }

    private suspend fun checkUpdate() = withContext(ioContext) {
        when (val result = repository.getDeviceInfo(
            deviceId = localRepository.getDeviceId()!!,
            branchId = (localRepository.getBranchId()!!)
        )) {
            is Response.Success -> {

                localRepository.saveDeviceInfo(result.data)
                _viewState.postValue(ViewState.Data(MainViewState.DataReady(result.data)))

            }
            is Response.Failure -> {

            }
        }
    }
}