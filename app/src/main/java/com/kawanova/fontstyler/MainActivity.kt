package com.kawanova.fontstyler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.google.android.gms.ads.MobileAds
import com.kawanova.fontstyler.ui.FontStylerViewModel
import com.kawanova.fontstyler.ui.navigation.NavGraph
import com.kawanova.fontstyler.ui.theme.FontStylerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: FontStylerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Google Mobile Ads SDK in background
        MobileAds.initialize(this) {}

        setContent {
            FontStylerTheme {
                NavGraph(viewModel = viewModel)
            }
        }
    }
}
