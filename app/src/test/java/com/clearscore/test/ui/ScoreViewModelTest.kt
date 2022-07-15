package com.clearscore.test.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.clearscore.test.data.repository.ScoreDataRepositoryImpl
import com.clearscore.test.data.repository.model.ScoreDataResult
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
    private var repository: ScoreDataRepositoryImpl = mockk()

    private lateinit var viewModel: ScoreViewModel

    private fun setUp() {
        viewModel = ScoreViewModel(repository)
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `GIVEN Repo Layer returns Success WHEN viewModel is created THEN livedata should have success state`() =
        runBlocking {
            //GIVEN
            coEvery { repository.getScore() } returns ScoreDataResult.Success(
                accountIDVStatus = "123",
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
                        score = 514,
                        maxScore = 700,
                        creditRingProgress = 0.7342857f
                    )
                )
            }
        }

    @Test
    fun `GIVEN Repo Layer returns Server Error WHEN viewModel is created THEN livedata should have error state`() =
        runBlocking {
            //GIVEN
            coEvery { repository.getScore() } returns ScoreDataResult.ServerError

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
            coEvery { repository.getScore() } returns ScoreDataResult.NoInternet

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
            coEvery { repository.getScore() } returns ScoreDataResult.Success(
                accountIDVStatus = "123",
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
                        score = 514,
                        maxScore = 700,
                        creditRingProgress = 0.7342857f
                    )
                )
                observer.onChanged(UIState.Loading)
                observer.onChanged(
                    UIState.Success(
                        score = 514,
                        maxScore = 700,
                        creditRingProgress = 0.7342857f
                    )
                )
            }
        }

    @Test
    fun `GIVEN action is Refresh Credit Score WHEN result is Server Error THEN livedata should have error state`() =
        runBlocking {
            //GIVEN
            coEvery { repository.getScore() } returns ScoreDataResult.ServerError
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

//    @Test
//    fun `GIVEN action is Refresh Credit Score WHEN result is No Internet THEN livedata should have no internet state`() =
//        runBlocking {
//            //GIVEN
//            coEvery { repository.getScore() } returns ScoreDataResult.NoInternet
//            setUp()
//
//            //WHEN
//            viewModel.onAction(CreditScoreIntent.RefreshCreditScore)
//
//            //THEN
//            verifySequence {
//                observer.onChanged(UIState.Loading)
//                observer.onChanged(UIState.NoInternet)
//                observer.onChanged(UIState.Loading)
//                observer.onChanged(UIState.NoInternet)
//            }
//        }
}