package com.homework.mhafidhabdulaziz.football_apps.presentation

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.TestContextProvider
import com.homework.mhafidhabdulaziz.football_apps.presentation.matchdetail.MatchScheduleDetailPresenter
import com.homework.mhafidhabdulaziz.football_apps.presentation.matchdetail.MatchScheduleDetailView
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallApiRepository
import com.homework.mhafidhabdulaziz.football_apps.service.FootBallClubRestApi
import com.homework.mhafidhabdulaziz.football_apps.service.entity.Team
import com.homework.mhafidhabdulaziz.football_apps.service.entity.TeamsDto
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by M Hafidh Abdul Aziz on 10/19/2018.
 */
class MatchScheduleDetailPresenterTest {

    @Mock
    private lateinit var view: MatchScheduleDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var footBallApiRepository: FootBallApiRepository

    @Mock lateinit var matchDetailPresenter: MatchScheduleDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        matchDetailPresenter = MatchScheduleDetailPresenter(view, footBallApiRepository, gson, TestContextProvider())
    }

    @Test
    fun test_getHomeTeamDetail(){
        val teams : MutableList<Team> = mutableListOf()
        val response = TeamsDto(teams)
        val teamId = "133604"
        val isHomeTeam = false
        `when`(gson.fromJson(footBallApiRepository.doRequest(FootBallClubRestApi.getTeamDetail(teamId)), TeamsDto::class.java)).thenReturn(response)
        matchDetailPresenter.requestTeamDetailData(teamId, isHomeTeam)
        verify(view).onTeamDetailDataReceived(response, isHomeTeam)
    }

    @Test
    fun test_getAwayTeamDetail(){
        val teams : MutableList<Team> = mutableListOf()
        val response = TeamsDto(teams)
        val teamId = "133604"
        val isHomeTeam = true
        `when`(gson.fromJson(footBallApiRepository.doRequest(FootBallClubRestApi.getTeamDetail(teamId)), TeamsDto::class.java)).thenReturn(response)
        matchDetailPresenter.requestTeamDetailData(teamId, isHomeTeam)
        verify(view).onTeamDetailDataReceived(response, isHomeTeam)
    }
}