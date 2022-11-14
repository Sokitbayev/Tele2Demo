package com.example.tele2demo.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tele2demo.domain.*
import com.example.tele2demo.domain.model.UserData
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
    private val localRepository: LocalRepository,
    private val userData: UserData
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<Boolean>>()
    val viewState: LiveData<ViewState<Boolean>> = _viewState

    fun onBtnClick(login: String, password: String) {
        val token = Base64.getEncoder().encodeToString("$login:$password".toByteArray())
        localRepository.setToken(token)
        userData.token = token
        _viewState.value = ViewState.Data(true)
    }
}