package com.clearscore.test

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.clearscore.test.ui.CreditScoreIntent
import com.clearscore.test.ui.ScoreViewModel
import com.clearscore.test.ui.UIState
import com.clearscore.test.ui.theme.TestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoadUI()
                }
            }
        }
    }

    @Composable
    private fun LoadUI(viewModel: ScoreViewModel = viewModel()) {

        val state = viewModel.state.observeAsState(initial = UIState.Loading)

        state.value.let {
            when (it) {
                UIState.Error -> {
                    ErrorUI { viewModel.onAction(creditScoreIntent = CreditScoreIntent.RefreshCreditScore) }
                }
                UIState.Loading -> {
                    LoadingUI()
                }
                is UIState.Success -> {
                    SuccessUI(
                        it.score.toString(),
                        it.maxScore.toString(),
                        it.creditRingProgress
                    )
                }
            }
        }

    }

}

@Composable
fun ErrorUI(retry: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        OutlinedButton(
            onClick = retry
        ) {
            Text("Something went wrong! Retry")
        }
    }
}

@Composable
fun LoadingUI() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun SuccessUI(score: String, maxScore: String, creditRingProgress: Float) {
    Box(contentAlignment = Alignment.Center) {
        CreditRing(progress = creditRingProgress)
        CreditText(score, maxScore)
    }
}

@Composable
fun CreditRing(progress: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    CircularProgressIndicator(
        progress = animatedProgress,
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 8.dp,
        modifier = Modifier.then(Modifier.size(200.dp))
    )
}

@Composable
fun CreditText(score: String, maxScore: String) {
    Column(
        modifier = Modifier.padding(all = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Your credit score is")
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = score,
            fontSize = 50.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "out of $maxScore")
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview() {
    SuccessUI("355", "700", creditRingProgress = .5f)
}

//TODO: Extract resorces