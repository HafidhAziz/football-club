package com.homework.mhafidhabdulaziz.football_apps

import com.homework.mhafidhabdulaziz.football_apps.presentation.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by M Hafidh Abdul Aziz on 10/19/2018.
 */
class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}