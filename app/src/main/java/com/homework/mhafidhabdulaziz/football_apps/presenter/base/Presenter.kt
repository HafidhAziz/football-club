package com.homework.mhafidhabdulaziz.football_apps.presenter.base

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

interface Presenter<T : View<*>> {
    fun onAttach(view: T)

    fun onDetach()
}
