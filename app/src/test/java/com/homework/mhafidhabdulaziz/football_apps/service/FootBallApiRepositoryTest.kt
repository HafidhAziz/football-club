package com.homework.mhafidhabdulaziz.football_apps.service

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by M Hafidh Abdul Aziz on 10/19/2018.
 */
class FootBallApiRepositoryTest {

    @Test
    fun test_doRequest(){
        val apiRepository = mock(FootBallApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=German%20Bundesliga"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}