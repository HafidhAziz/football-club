package com.homework.mhafidhabdulaziz.football_apps.presentation

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.TestContextProvider
import com.homework.mhafidhabdulaziz.football_apps.presentation.nextmatch.NextMatchPresenter
import com.homework.mhafidhabdulaziz.football_apps.presentation.nextmatch.NextMatchView
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
class NextMatchPresenterTest {

    @Mock
    private lateinit var view: NextMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var footBallApiRepository: FootBallApiRepository

    @Mock lateinit var nextMatchPresenter: NextMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        nextMatchPresenter = NextMatchPresenter(view, footBallApiRepository, gson, TestContextProvider())
    }

    @Test
    fun test_getNextMatch(){
        val events : MutableList<Event> = mutableListOf()
        val response = MatchScheduleDto(events)
        val leagueId = "4328"
        `when`(gson.fromJson(footBallApiRepository.doRequest(FootBallClubRestApi.getNextMatchSchedule(leagueId)), MatchScheduleDto::class.java)).thenReturn(response)
        nextMatchPresenter.requestNextMatchScheduleData(leagueId)
        verify(view).showLoading()
        verify(view).onNextMatchDataReceived(events)
        verify(view).hideLoading()
    }
}