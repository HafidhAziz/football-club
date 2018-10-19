package com.homework.mhafidhabdulaziz.football_apps.presentation

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.TestContextProvider
import com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch.LastMatchPresenter
import com.homework.mhafidhabdulaziz.football_apps.presentation.lastmatch.LastMatchView
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
class LastMatchPresenterTest {

    @Mock
    private lateinit var view: LastMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var footBallApiRepository: FootBallApiRepository

    @Mock lateinit var lastMatchPresenter: LastMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        lastMatchPresenter = LastMatchPresenter(view, footBallApiRepository, gson, TestContextProvider())
    }

    @Test
    fun test_getLastMatchList(){
        val events : MutableList<Event> = mutableListOf()
        val response = MatchScheduleDto(events)
        val leagueId = "4328"
        `when`(gson.fromJson(footBallApiRepository.doRequest(FootBallClubRestApi.getLastMatchSchedule(leagueId)), MatchScheduleDto::class.java)).thenReturn(response)
        lastMatchPresenter.requestLastMatchScheduleData(leagueId)
        verify(view).showLoading()
        verify(view).onLastMatchDataReceived(events)
        verify(view).hideLoading()
    }
}