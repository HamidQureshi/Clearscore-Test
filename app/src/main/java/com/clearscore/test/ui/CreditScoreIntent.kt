package com.clearscore.test.ui

internal sealed class CreditScoreIntent {
    object RefreshCreditScore : CreditScoreIntent()
}
