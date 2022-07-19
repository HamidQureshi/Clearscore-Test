package com.clearscore.data.repository

import com.clearscore.data.dataSource.FetchScoreDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
internal class ScoreDataRepositoryImplTest {

    private lateinit var repository: ScoreDataRepositoryImpl

    private var dataSource: FetchScoreDataSource = mockk()

    @Before
    fun setUp() {
        repository = ScoreDataRepositoryImpl(dataSource)
    }

    @Test
    fun `GIVEN get score api is called WHEN server returns success THEN success should be returned with data`() =
        runBlocking {
            //GIVEN
            coEvery { dataSource.getScoreDetails() } returns Response.success(
                com.clearscore.data.dataSource.model.ScoreDetailsResponse(
                    accountIDVStatus = "PASS",
                    dashboardStatus = "PASS",
                    creditReportInfo = com.clearscore.data.dataSource.model.ScoreDetailsResponse.CreditReportInfo(
                        score = 123,
                        maxScoreValue = 500,
                        scoreBand = 3
                    )
                )
            )

            //WHEN
            val actual = repository.getScore()

            //THEN
            assertEquals(
                com.clearscore.data.repository.model.ScoreDataResult.Success(
                    accountIDVStatus = "PASS",
                    dashboardStatus = "PASS",
                    score = 123,
                    maxScore = 500
                ), actual
            )
        }

    @Test
    fun `GIVEN get score api is called WHEN server returns error THEN failure should be returned`() =
        runBlocking {
            //GIVEN
            coEvery { dataSource.getScoreDetails() } returns Response.success(null)

            //WHEN
            val actual = repository.getScore()

            //THEN
            assertEquals(
                com.clearscore.data.repository.model.ScoreDataResult.ServerError, actual
            )
        }

    @Test
    fun `GIVEN get score api is called WHEN there is no internet THEN no internet should be returned`() =
        runBlocking {
            //GIVEN
            coEvery { dataSource.getScoreDetails() } throws IOException()

            //WHEN
            val actual = repository.getScore()

            //THEN
            assertEquals(
                com.clearscore.data.repository.model.ScoreDataResult.NoInternet, actual
            )
        }

}