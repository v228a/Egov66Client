package com.vovka.egov66client.ui.profile

import android.content.Context
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovka.egov66client.domain.student.GetStudentInfoUseCase
import com.vovka.egov66client.domain.student.LogoutUseCase
import com.vovka.egov66client.utils.MutablePublishFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    //TODO add lazy<>?
    @ApplicationContext private val context: Context,
    private val getStudentInfoUseCase: GetStudentInfoUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    private val _action = MutablePublishFlow<Action>()
    val action = _action.asSharedFlow()

    private val _state = MutableStateFlow<State>(initialState)
    val state = _state.asStateFlow()

    init {
        updateStudentInfo()
    }

    fun clickExit(){
        viewModelScope.launch {
            _action.emit(Action.OpenExit)
        }
    }

    fun logout(){
        viewModelScope.launch {
            CookieManager.getInstance().removeAllCookies(null)

            WebView(context).clearCache(true)

            WebStorage.getInstance().deleteAllData()

            context.deleteDatabase("webview.db")
            context.deleteDatabase("webviewCache.db")

            WebViewDatabase.getInstance(context).clearFormData()

            logoutUseCase.invoke()
        }
    }

    fun updateStudentInfo(){
        viewModelScope.launch {
            getStudentInfoUseCase.invoke().fold(
                onSuccess = { value ->
                    _state.update {
                        State.Show(
                            firstName = value.firstName,
                            lastName = value.lastName,
                            surName = value.surName,
                            className = value.className,
                            orgName = value.orgName,
                            id = value.id,
                            avatarId = value.avatarId,
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("Profile",error.message.toString())
                }
            )
        }
    }


    sealed interface State {
        data object Loading : State
        data class Show(
            val firstName: String,
            val lastName: String,
            val surName: String,
            val className: String,
            val orgName: String,
            val id: String,
            val avatarId: String?
        ) : State
    }

    sealed interface Action {
        data object OpenExit : Action
    }
    companion object {
        val initialState = State.Loading
    }
}