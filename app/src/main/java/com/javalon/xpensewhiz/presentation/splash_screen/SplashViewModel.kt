package com.javalon.xpensewhiz.presentation.splash_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.xpensewhiz.domain.usecase.read_datastore.GetOnBoardingKeyUseCase
import com.javalon.xpensewhiz.presentation.navigation.Screen
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
class SplashViewModel @Inject constructor(
    private val getOnBoardingKeyUseCase: GetOnBoardingKeyUseCase
) : ViewModel() {

    private var _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private var _startDestination = mutableStateOf(Screen.WelcomeScreen.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch(IO) {

            getOnBoardingKeyUseCase().collect { completed ->
                if (completed)
                    _startDestination.value = Screen.HomeScreen.route
            }

            _isLoading.value = false
        }
    }
}