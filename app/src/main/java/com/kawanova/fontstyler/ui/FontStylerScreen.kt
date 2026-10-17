package com.kawanova.fontstyler.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kawanova.fontstyler.data.*
import com.kawanova.fontstyler.ui.components.AdaptiveBannerAd
import com.kawanova.fontstyler.ui.components.SisterAppsBanner
import com.kawanova.fontstyler.ui.theme.*
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontStylerScreen(
    viewModel: FontStylerViewModel,
    onNavigateToPresets: () -> Unit,
    onNavigateToSaved: () -> Unit
) {
    val context = LocalContext.current
    val config by viewModel.config.collectAsState()
    val activeFormat by viewModel.activeFormat.collectAsState()
    val generatedCode by viewModel.generatedCode.collectAsState()
    val contrast by viewModel.contrastResult.collectAsState()

    var showSaveDialog by remember { mutableStateOf(false) }
    var saveName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Slate900)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Slate800)
                                .border(1.dp, AmberGold, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Aa", color = AmberLight, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Column {
                            Text(
                                text = "FontStyler",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Slate100
                            )
                            Text(
                                text = "タイポグラフィ検証 ＆ コード生成",
                                style = MaterialTheme.typography.labelSmall,
                                color = Slate400
                            )
                        }
                    }

                    // Navigation Actions
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedButton(
                            onClick = onNavigateToPresets,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AmberGold),
                            border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold.copy(alpha = 0.5f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "📚 20選", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(
                            onClick = onNavigateToSaved,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = SkyAccent),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SkyAccent.copy(alpha = 0.5f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "💾 保存済", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        bottomBar = {
            Column(modifier = Modifier.background(Slate900)) {
                AdaptiveBannerAd()
            }
        },
        containerColor = Slate900
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Live Preview Canvas Card
            val textCol = remember(config.textColorHex) {
                try { Color(android.graphics.Color.parseColor(config.textColorHex)) } catch (_: Exception) { Color.White }
            }
            val bgCol = remember(config.backgroundColorHex) {
                try { Color(android.graphics.Color.parseColor(config.backgroundColorHex)) } catch (_: Exception) { Slate900 }
            }
            val composeFontFamily = remember(config.fontFamily) {
                when (config.fontFamily) {
                    FontFamilyType.SANS_SERIF -> FontFamily.Default
                    FontFamilyType.SERIF -> FontFamily.Serif
                    FontFamilyType.MONOSPACE -> FontFamily.Monospace
                    FontFamilyType.CURSIVE -> FontFamily.Cursive
                }
            }
            val composeWeight = remember(config.fontWeightValue) {
                FontWeight(config.fontWeightValue)
            }
            val composeAlign = remember(config.textAlign) {
                when (config.textAlign) {
                    TextAlignType.LEFT -> TextAlign.Start
                    TextAlignType.CENTER -> TextAlign.Center
                    TextAlignType.RIGHT -> TextAlign.End
                }
            }
            val calculatedTracking = remember(config.fontSizeSp, config.letterSpacingEm) {
                (config.fontSizeSp * config.letterSpacingEm).sp
            }
            val calculatedLineHeight = remember(config.fontSizeSp, config.lineHeightMultiplier) {
                (config.fontSizeSp * config.lineHeightMultiplier).sp
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = bgCol),
                border = androidx.compose.foundation.BorderStroke(2.dp, AmberGold.copy(alpha = 0.6f)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Header Bar: Contrast Badge & Save Action
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // WCAG Contrast Badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (contrast.isAaPass) EmeraldSuccess.copy(alpha = 0.2f) else RoseError.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (contrast.isAaaPass) "AAA ${contrast.ratioFormatted}" else if (contrast.isAaPass) "AA ${contrast.ratioFormatted}" else "Fail ${contrast.ratioFormatted}",
                                    color = if (contrast.isAaPass) EmeraldSuccess else RoseError,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "${config.fontSizeSp.roundToInt()}px (${String.format(Locale.US, "%.2f", config.fontSizeSp / 16f)}rem)",
                                color = Slate400,
                                fontSize = 11.sp
                            )
                        }

                        IconButton(
                            onClick = {
                                saveName = "スタイル (${config.fontSizeSp.roundToInt()}px / ${config.fontFamily.name})"
                                showSaveDialog = true
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(imageVector = Icons.Default.BookmarkAdd, contentDescription = "保存", tint = AmberLight)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Text Sample Rendered with Full Typography Rules
                    val transformedText = remember(config.sampleText, config.textTransform) {
                        when (config.textTransform) {
                            TextTransformType.UPPERCASE -> config.sampleText.uppercase()
                            TextTransformType.LOWERCASE -> config.sampleText.lowercase()
                            TextTransformType.CAPITALIZE -> config.sampleText.split(" ").joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
                            TextTransformType.NONE -> config.sampleText
                        }
                    }

                    Text(
                        text = transformedText,
                        color = textCol,
                        fontFamily = composeFontFamily,
                        fontSize = config.fontSizeSp.sp,
                        fontWeight = composeWeight,
                        lineHeight = calculatedLineHeight,
                        letterSpacing = calculatedTracking,
                        textAlign = composeAlign,
                        fontStyle = if (config.isItalic) FontStyle.Italic else FontStyle.Normal,
                        textDecoration = if (config.isUnderline) TextDecoration.Underline else TextDecoration.None,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // 2. Multi-Platform Code Export Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate800),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⚡ 生成コードエクスポート",
                            style = MaterialTheme.typography.titleMedium,
                            color = Slate100,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = { viewModel.copyCode(context) },
                            colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Slate900),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "コピー", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Format Selector Chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (fmt in CodeExportFormat.values()) {
                            val isSelected = (activeFormat == fmt)
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.selectExportFormat(fmt) },
                                label = { Text(fmt.label, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AmberGold,
                                    selectedLabelColor = Slate900,
                                    containerColor = Slate900,
                                    labelColor = Slate300
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = if (isSelected) AmberGold else Slate700
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Code Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF090D16))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = generatedCode,
                            color = Color(0xFFF1F5F9),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // 3. Typography Tuning Sliders & Controls
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate800),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Slate700)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "🎛️ タイポグラフィ詳細設定",
                        style = MaterialTheme.typography.titleMedium,
                        color = Slate100,
                        fontWeight = FontWeight.Bold
                    )

                    // A. Font Size
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "フォントサイズ (Font Size)", style = MaterialTheme.typography.bodyMedium, color = Slate300)
                            Text(text = "${config.fontSizeSp.roundToInt()} px / ${String.format(Locale.US, "%.2f", config.fontSizeSp / 16f)} rem", color = AmberLight, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Slider(
                            value = config.fontSizeSp,
                            onValueChange = { viewModel.updateFontSize(it) },
                            valueRange = 12f..72f,
                            steps = 59,
                            colors = SliderDefaults.colors(thumbColor = AmberGold, activeTrackColor = AmberGold)
                        )
                    }

                    // B. Font Weight
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "フォントウェイト (Weight)", style = MaterialTheme.typography.bodyMedium, color = Slate300)
                            Text(text = "${config.fontWeightValue} (${when(config.fontWeightValue) { 100 -> "Thin"; 300 -> "Light"; 400 -> "Regular"; 600 -> "SemiBold"; 700 -> "Bold"; 800 -> "ExtraBold"; 900 -> "Black"; else -> "Medium" }})", color = AmberLight, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Slider(
                            value = config.fontWeightValue.toFloat(),
                            onValueChange = { viewModel.updateFontWeight(it.roundToInt()) },
                            valueRange = 100f..900f,
                            steps = 7,
                            colors = SliderDefaults.colors(thumbColor = AmberGold, activeTrackColor = AmberGold)
                        )
                    }

                    // C. Line Height
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "行間 (Line Height)", style = MaterialTheme.typography.bodyMedium, color = Slate300)
                            Text(text = "${String.format(Locale.US, "%.2f", config.lineHeightMultiplier)}x (${(config.fontSizeSp * config.lineHeightMultiplier).roundToInt()}px)", color = AmberLight, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Slider(
                            value = config.lineHeightMultiplier,
                            onValueChange = { viewModel.updateLineHeight(it) },
                            valueRange = 1.0f..2.5f,
                            steps = 14,
                            colors = SliderDefaults.colors(thumbColor = AmberGold, activeTrackColor = AmberGold)
                        )
                    }

                    // D. Letter Spacing (Tracking)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "字間 (Letter Spacing)", style = MaterialTheme.typography.bodyMedium, color = Slate300)
                            Text(text = "${String.format(Locale.US, "%.2f", config.letterSpacingEm)} em (${(config.fontSizeSp * config.letterSpacingEm).roundToInt()}px)", color = AmberLight, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Slider(
                            value = config.letterSpacingEm,
                            onValueChange = { viewModel.updateLetterSpacing(it) },
                            valueRange = -0.05f..0.30f,
                            steps = 34,
                            colors = SliderDefaults.colors(thumbColor = AmberGold, activeTrackColor = AmberGold)
                        )
                    }

                    // E. Font Family Selector
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "フォント種別 (Font Family)", style = MaterialTheme.typography.bodyMedium, color = Slate300)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (fam in FontFamilyType.values()) {
                                val isSel = (config.fontFamily == fam)
                                FilterChip(
                                    selected = isSel,
                                    onClick = { viewModel.updateFontFamily(fam) },
                                    label = { Text(fam.displayName, fontSize = 12.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = AmberGold,
                                        selectedLabelColor = Slate900,
                                        containerColor = Slate900,
                                        labelColor = Slate300
                                    )
                                )
                            }
                        }
                    }

                    // F. Text Align & Transform
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "配置・変形 (Align & Transform)", style = MaterialTheme.typography.bodyMedium, color = Slate300)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (align in TextAlignType.values()) {
                                val isSel = (config.textAlign == align)
                                FilterChip(
                                    selected = isSel,
                                    onClick = { viewModel.updateTextAlign(align) },
                                    label = { Text(align.displayName.split(" ")[0], fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AmberGold, selectedLabelColor = Slate900)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (trans in TextTransformType.values()) {
                                val isSel = (config.textTransform == trans)
                                FilterChip(
                                    selected = isSel,
                                    onClick = { viewModel.updateTextTransform(trans) },
                                    label = { Text(trans.displayName.split(" ")[0], fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AmberGold, selectedLabelColor = Slate900)
                                )
                            }
                        }
                    }

                    // G. Style Toggles (Italic & Underline)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FilterChip(
                            selected = config.isItalic,
                            onClick = { viewModel.toggleItalic() },
                            label = { Text("斜体 (Italic)", fontStyle = FontStyle.Italic) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AmberGold, selectedLabelColor = Slate900)
                        )
                        FilterChip(
                            selected = config.isUnderline,
                            onClick = { viewModel.toggleUnderline() },
                            label = { Text("下線 (Underline)", textDecoration = TextDecoration.Underline) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AmberGold, selectedLabelColor = Slate900)
                        )
                    }

                    // H. Color Theme Presets
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "文字色 ＆ 背景色プリセット", style = MaterialTheme.typography.bodyMedium, color = Slate300)
                        val colorPairs = listOf(
                            Triple("ダーク＆ホワイト", "#F8FAFC", "#0F172A"),
                            Triple("ライト＆ダーク", "#0F172A", "#F8FAFC"),
                            Triple("サイバーネオン", "#38BDF8", "#050B14"),
                            Triple("ラグジュアリー", "#FEF08A", "#1E1B4B"),
                            Triple("ターミナル", "#22C55E", "#0A0A0A")
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for ((label, textHex, bgHex) in colorPairs) {
                                Button(
                                    onClick = {
                                        viewModel.updateTextColor(textHex)
                                        viewModel.updateBackgroundColor(bgHex)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Slate700),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Text(text = label, fontSize = 11.sp, color = Slate100)
                                }
                            }
                        }
                    }
                }
            }

            // Sister Apps Banner
            SisterAppsBanner()

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    // 保存ダイアログ
    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            containerColor = Slate800,
            title = { Text("💾 タイポグラフィスタイルを保存", color = Slate100, fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = saveName,
                    onValueChange = { saveName = it },
                    label = { Text("スタイル名", color = Slate400) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Slate100,
                        unfocusedTextColor = Slate100,
                        focusedBorderColor = AmberGold
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveCurrentStyle(saveName) {
                            showSaveDialog = false
                            android.widget.Toast.makeText(context, "スタイルを保存しました 💾", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Slate900)
                ) {
                    Text("保存", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("キャンセル", color = Slate400)
                }
            }
        )
    }
}
