package com.homework.mhafidhabdulaziz.football_apps.presentation

import com.google.gson.Gson
import com.homework.mhafidhabdulaziz.football_apps.TestContextProvider
import com.homework.mhafidhabdulaziz.football_apps.presentation.teams.TeamPresenter
import com.homework.mhafidhabdulaziz.football_apps.presentation.teams.TeamView
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
class TeamPresenterTest {

    @Mock
    private lateinit var view: TeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var footBallApiRepository: FootBallApiRepository

    @Mock lateinit var teamPresenter: TeamPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        teamPresenter = TeamPresenter(view, footBallApiRepository, gson, TestContextProvider())
    }

    @Test
    fun test_getTeamList(){
        val teams : MutableList<Team> = mutableListOf()
        val response = TeamsDto(teams)
        val league = "English Premiere League"
        `when`(gson.fromJson(footBallApiRepository.doRequest(FootBallClubRestApi.getTeams(league)), TeamsDto::class.java)).thenReturn(response)
        teamPresenter.getTeamList(league)
        verify(view).showLoading()
        verify(view).onReceivedTeamList(teams)
        verify(view).hideLoading()
    }
}