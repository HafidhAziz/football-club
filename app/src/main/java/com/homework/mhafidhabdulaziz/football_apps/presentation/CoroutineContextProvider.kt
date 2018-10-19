package com.homework.mhafidhabdulaziz.football_apps.presentation

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by M Hafidh Abdul Aziz on 10/19/2018.
 */
open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy {
        UI
    }
}