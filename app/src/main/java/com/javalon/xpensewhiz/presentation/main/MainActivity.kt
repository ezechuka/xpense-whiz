package com.javalon.xpensewhiz.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.javalon.xpensewhiz.presentation.navigation.MainScreen
import com.javalon.xpensewhiz.presentation.ui.theme.XpenseWhizTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalComposeUiApi
@InternalCoroutinesApi
@AndroidEntryPoint
@ExperimentalPagerApi
@ExperimentalUnitApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XpenseWhizTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val destination by mainViewModel.startDestination.collectAsState()
                    MainScreen(
                        startDestination = destination,
                    )
                }
            }
        }
    }
}