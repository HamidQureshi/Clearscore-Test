package com.clearscore.test.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.clearscore.data.repository.ScoreDataRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

internal class ScoreViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val observer = mockk<Observer<UIState>> { every { onChanged(any()) } just Runs }

    @Mock
    private var repository: ScoreDataRepository = mockk()

    private lateinit var viewModel: ScoreViewModel

    private fun setUp() {
        viewModel = ScoreViewModel(repository)
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `GIVEN Repo Layer returns Success WHEN viewModel is created THEN livedata should have success state`() =
        runBlocking {
            //GIVEN
            coEvery { repository.getScore() } returns com.clearscore.data.repository.model.ScoreDataResult.Success(
                accountIDVStatus = "PASS",
                dashboardStatus = "PASS",
                score = 514,
                maxScore = 700
            )

            //WHEN
            setUp()

            //THEN
            verifySequence {
                observer.onChanged(UIState.Loading)
                observer.onChanged(
                    UIState.Success(
                        accountIDVStatus = "PASS",
                        dashboardStatus = "PASS",
                        score = 514,
                        maxScore = 700,
                        creditRingProgress = 73
                    )
                )
            }
        }

    @Test
    fun `GIVEN Repo Layer returns Server Error WHEN viewModel is created THEN livedata should have error state`() =
        runBlocking {
            //GIVEN
            coEvery { repository.getScore() } returns com.clearscore.data.repository.model.ScoreDataResult.ServerError

            //WHEN
            setUp()

            //THEN
            verifySequence {
                observer.onChanged(UIState.Loading)
                observer.onChanged(UIState.Error)
            }
        }

    @Test
    fun `GIVEN Repo Layer returns No Internet WHEN viewModel is created THEN livedata should have no internet state`() =
        runBlocking {
            //GIVEN
            coEvery { repository.getScore() } returns com.clearscore.data.repository.model.ScoreDataResult.NoInternet

            //WHEN
            setUp()

            //THEN
            verifySequence {
                observer.onChanged(UIState.Loading)
                observer.onChanged(UIState.NoInternet)
            }
        }

    @Test
    fun `GIVEN action is Refresh Credit Score WHEN result is success THEN livedata should have success state`() =
        runBlocking {
            //GIVEN
            coEvery { repository.getScore() } returns com.clearscore.data.repository.model.ScoreDataResult.Success(
                accountIDVStatus = "PASS",
                dashboardStatus = "PASS",
                score = 514,
                maxScore = 700
            )
            setUp()

            //WHEN
            viewModel.onAction(CreditScoreIntent.RefreshCreditScore)

            //THEN
            verifySequence {
                observer.onChanged(UIState.Loading)
                observer.onChanged(
                    UIState.Success(
                        accountIDVStatus = "PASS",
                        dashboardStatus = "PASS",
                        score = 514,
                        maxScore = 700,
                        creditRingProgress = 73
                    )
                )
                observer.onChanged(UIState.Loading)
                observer.onChanged(
                    UIState.Success(
                        accountIDVStatus = "PASS",
                        dashboardStatus = "PASS",
                        score = 514,
                        maxScore = 700,
                        creditRingProgress = 73
                    )
                )
            }
        }

    @Test
    fun `GIVEN action is Refresh Credit Score WHEN result is Server Error THEN livedata should have error state`() =
        runBlocking {
            //GIVEN
            coEvery { repository.getScore() } returns com.clearscore.data.repository.model.ScoreDataResult.ServerError
            setUp()

            //WHEN
            viewModel.onAction(CreditScoreIntent.RefreshCreditScore)

            //THEN
            verifySequence {
                observer.onChanged(UIState.Loading)
                observer.onChanged(UIState.Error)
                observer.onChanged(UIState.Loading)
                observer.onChanged(UIState.Error)
            }
        }
}