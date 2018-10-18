package com.homework.mhafidhabdulaziz.football_apps.dependency

import com.homework.mhafidhabdulaziz.football_apps.presenter.main.FavoriteMatchPresenter
import com.homework.mhafidhabdulaziz.football_apps.presenter.main.LastMatchPresenter
import com.homework.mhafidhabdulaziz.football_apps.presenter.main.MatchScheduleDetailsPresenter
import com.homework.mhafidhabdulaziz.football_apps.presenter.main.NextMatchPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by mhafidhabdulaziz on 14/08/18.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class))
interface NetComponent {
    fun inject(lastMatchPresenter: LastMatchPresenter)
    fun inject(nextMatchPresenter: NextMatchPresenter)
    fun inject(nextMatchPresenter: MatchScheduleDetailsPresenter)
    fun inject(favMatchPresenter: FavoriteMatchPresenter)
}
