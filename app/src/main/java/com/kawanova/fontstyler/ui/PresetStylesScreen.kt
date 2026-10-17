package com.kawanova.fontstyler.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kawanova.fontstyler.data.FontFamilyType
import com.kawanova.fontstyler.data.TextAlignType
import com.kawanova.fontstyler.data.TextTransformType
import com.kawanova.fontstyler.data.TypeStylePreset
import com.kawanova.fontstyler.ui.theme.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresetStylesScreen(
    viewModel: FontStylerViewModel,
    onNavigateBack: () -> Unit
) {
    val selectedCategory by viewModel.presetCategory.collectAsState()
    val allPresets = viewModel.allPresets

    val categories = listOf("All", "Headings", "Body & Reading", "UI Components", "Editorial & Serif", "Tech & Code")

    val filteredPresets = remember(selectedCategory) {
        if (selectedCategory == "All") allPresets
        else allPresets.filter { it.category == selectedCategory }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Slate900)
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "戻る",
                            tint = Slate100
                        )
                    }
                    Text(
                        text = "📚 厳選20選！タイポグラフィプリセット",
                        style = MaterialTheme.typography.titleLarge,
                        color = Slate100,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Category Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (cat in categories) {
                        val isSel = (selectedCategory == cat)
                        FilterChip(
                            selected = isSel,
                            onClick = { viewModel.selectPresetCategory(cat) },
                            label = { Text(cat, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AmberGold,
                                selectedLabelColor = Slate900,
                                containerColor = Slate800,
                                labelColor = Slate300
                            )
                        )
                    }
                }
                HorizontalDivider(color = Slate700, thickness = 1.dp)
            }
        },
        containerColor = Slate900
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredPresets, key = { it.id }) { preset ->
                PresetCardItem(
                    preset = preset,
                    onApply = {
                        viewModel.applyPreset(preset)
                        onNavigateBack()
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun PresetCardItem(
    preset: TypeStylePreset,
    onApply: () -> Unit
) {
    val cfg = preset.config
    val textCol = remember(cfg.textColorHex) {
        try { Color(android.graphics.Color.parseColor(cfg.textColorHex)) } catch (_: Exception) { Color.White }
    }
    val bgCol = remember(cfg.backgroundColorHex) {
        try { Color(android.graphics.Color.parseColor(cfg.backgroundColorHex)) } catch (_: Exception) { Slate900 }
    }
    val fontFamily = remember(cfg.fontFamily) {
        when (cfg.fontFamily) {
            FontFamilyType.SANS_SERIF -> FontFamily.Default
            FontFamilyType.SERIF -> FontFamily.Serif
            FontFamilyType.MONOSPACE -> FontFamily.Monospace
            FontFamilyType.CURSIVE -> FontFamily.Cursive
        }
    }
    val textAlign = remember(cfg.textAlign) {
        when (cfg.textAlign) {
            TextAlignType.LEFT -> TextAlign.Start
            TextAlignType.CENTER -> TextAlign.Center
            TextAlignType.RIGHT -> TextAlign.End
        }
    }
    val transformed = remember(cfg.sampleText, cfg.textTransform) {
        when (cfg.textTransform) {
            TextTransformType.UPPERCASE -> cfg.sampleText.uppercase()
            TextTransformType.LOWERCASE -> cfg.sampleText.lowercase()
            TextTransformType.CAPITALIZE -> cfg.sampleText.split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
            TextTransformType.NONE -> cfg.sampleText
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onApply() },
        colors = CardDefaults.cardColors(containerColor = Slate800),
        border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Row: Title & Category Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = preset.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = AmberLight,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Slate700)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(text = preset.category, color = SkyAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = preset.description, color = Slate400, fontSize = 11.sp)

            Spacer(modifier = Modifier.height(12.dp))

            // Mini Preview Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(bgCol)
                    .border(1.dp, Slate700, RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = transformed,
                    color = textCol,
                    fontFamily = fontFamily,
                    fontSize = minOf(cfg.fontSizeSp, 28f).sp, // カード内表示用に適度に収める
                    fontWeight = FontWeight(cfg.fontWeightValue),
                    lineHeight = (minOf(cfg.fontSizeSp, 28f) * cfg.lineHeightMultiplier).sp,
                    letterSpacing = (minOf(cfg.fontSizeSp, 28f) * cfg.letterSpacingEm).sp,
                    textAlign = textAlign,
                    fontStyle = if (cfg.isItalic) FontStyle.Italic else FontStyle.Normal,
                    textDecoration = if (cfg.isUnderline) TextDecoration.Underline else TextDecoration.None,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Metrics Line + Apply CTA
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${cfg.fontSizeSp.roundToInt()}px / w${cfg.fontWeightValue} / LH:${cfg.lineHeightMultiplier}x",
                    color = Slate400,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "タップしてエディタに適用 →",
                    color = AmberGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
