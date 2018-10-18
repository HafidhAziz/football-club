package com.homework.mhafidhabdulaziz.football_apps.common

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by M Hafidh Abdul Aziz on 10/18/2018.
 */
class CommonUtilsTest {

    @Test
    fun test_getFormattedDateWithNewDate(){
        val date = Date()
        CommonUtils.getFormattedDate(date)
        assert(true)
    }

    @Test
    fun test_getFormattedDateWithCustomDate(){
        val dateString = "2018-10-07"
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(dateString)
        val result = CommonUtils.getFormattedDate(date)
        assert(result == "Sunday, October 07 2018")
    }

    @Test
    fun test_getSeparatorWithEmptyString(){
        val result = CommonUtils.getSeparator("")
        assert(result == "")
    }

    @Test
    fun test_getSeparatorWithValidString(){
        val result = CommonUtils.getSeparator("test1; test2; test3")
        assert(result == "test1\r\ntest2\r\ntest3")
    }

    @Test
    fun test_getSeparatorNoSpaceWithEmptyString(){
        val result = CommonUtils.getSeparatorNoSpace("")
        assert(result == "")
    }

    @Test
    fun test_getSeparatorNoSpaceWithValidString(){
        val result = CommonUtils.getSeparatorNoSpace("test1;test2;test3")
        assert(result == "test1\r\ntest2\r\ntest3")
    }
}