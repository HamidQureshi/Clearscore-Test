package com.clearscore.test.data.repository

import com.clearscore.test.data.dataSource.FetchScoreDataSource
import com.clearscore.test.data.dataSource.model.ScoreDetailsResponse
import com.clearscore.test.data.repository.model.ScoreDataResult
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
                ScoreDetailsResponse(
                    accountIDVStatus = "PASS",
                    dashboardStatus = "PASS",
                    creditReportInfo = ScoreDetailsResponse.CreditReportInfo(
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
                ScoreDataResult.Success(
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
                ScoreDataResult.ServerError, actual
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
                ScoreDataResult.NoInternet, actual
            )
        }

}