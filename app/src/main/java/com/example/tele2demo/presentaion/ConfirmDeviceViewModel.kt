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

class ConfirmDeviceViewModel(
    private val repository: Repository,
    private val localRepository: LocalRepository,
    private val ioContext: CoroutineContext = Dispatchers.IO
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<DeviceInfo>>()
    val viewState: LiveData<ViewState<DeviceInfo>> = _viewState

    fun onData(deviceId: String?, branchId: String) {
        viewModelScope.launch {
            withContext(ioContext) {
                when (val result =
                    repository.getDeviceInfo(deviceId = deviceId ?: "", branchId = branchId)) {
                    is Response.Success -> {
                        localRepository.setDeviceId(deviceId!!)
                        localRepository.setBranchId(branchId)
                        localRepository.saveDeviceInfo(result.data)
                        _viewState.postValue(ViewState.Data(result.data))
                    }
                    is Response.Failure -> {

                    }
                }
            }
        }
    }
}