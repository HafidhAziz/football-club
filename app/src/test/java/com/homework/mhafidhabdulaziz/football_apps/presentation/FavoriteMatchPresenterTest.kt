package com.homework.mhafidhabdulaziz.football_apps.presentation

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.TestContextProvider
import com.homework.mhafidhabdulaziz.football_apps.local.db.MatchFavorite
import com.homework.mhafidhabdulaziz.football_apps.presentation.favoritematch.FavoriteMatchPresenter
import com.homework.mhafidhabdulaziz.football_apps.presentation.favoritematch.FavoriteMatchView
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Event
import com.homework.mhafidhabdulaziz.football_apps.service.entity.MatchScheduleDto
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by M Hafidh Abdul Aziz on 10/19/2018.
 */
class FavoriteMatchPresenterTest {

    @Mock
    private lateinit var view: FavoriteMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var footBallApiRepository: FootBallApiRepository

    @Mock lateinit var favMatchPresenter: FavoriteMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        favMatchPresenter = FavoriteMatchPresenter(view, footBallApiRepository, gson, TestContextProvider())
    }

    @Test
    fun test_getMatchDataFavorite(){
        val favMatch : MutableList<Event> = mutableListOf()
        val response = MatchScheduleDto(favMatch)
        val eventId = "441613"

        val matchFav = MatchFavorite(1,eventId,"","")
        val matchFavList : MutableList<MatchFavorite> = mutableListOf()
        matchFavList.add(matchFav)

        `when`(gson.fromJson(footBallApiRepository.doRequest(FootBallClubRestApi.getMatchEventById(eventId)), MatchScheduleDto::class.java)).thenReturn(response)
        favMatchPresenter.getMatchDataFavorite(matchFavList)
    }
}