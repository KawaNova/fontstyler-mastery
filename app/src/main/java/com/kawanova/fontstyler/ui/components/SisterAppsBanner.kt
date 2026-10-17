package com.kawanova.fontstyler.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kawanova.fontstyler.ui.theme.*

data class SisterApp(
    val nameJa: String,
    val nameEn: String,
    val descJa: String,
    val descEn: String,
    val icon: String,
    val packageName: String
)

val sisterApps = listOf(
    SisterApp(
        nameJa = "MarkNote",
        nameEn = "MarkNote",
        descJa = "超高速モバイルMarkdownエディタ ＆ プレビュー",
        descEn = "Mobile Markdown Editor & GFM Preview",
        icon = "📝",
        packageName = "com.kawanova.marknote"
    ),
    SisterApp(
        nameJa = "カラーパレット",
        nameEn = "Color Palette",
        descJa = "配色調和生成・WCAGコントラスト・コード変換",
        descEn = "Color Harmonies, WCAG Contrast & Code Export",
        icon = "🎨",
        packageName = "com.kawanova.colorpalette"
    ),
    SisterApp(
        nameJa = "モバイルAPIテスター",
        nameEn = "API Tester",
        descJa = "スマホで即時REST APIテスト・JSON整形",
        descEn = "Mobile REST Client & JSON Viewer",
        icon = "⚡",
        packageName = "com.kawanova.apitester"
    ),
    SisterApp(
        nameJa = "正規表現超入門",
        nameEn = "Regex Drill",
        descJa = "実務パターン逆引き ＆ リアルタイムテスター",
        descEn = "Regex Mastery & Realtime Tester",
        icon = "🔍",
        packageName = "com.kawanova.regexdrill"
    ),
    SisterApp(
        nameJa = "SEOアナライザー",
        nameEn = "SEO Analyzer",
        descJa = "Web健全性診断・SNS OGPプレビュー",
        descEn = "Mobile Web Health & OpenGraph Inspector",
        icon = "🌐",
        packageName = "com.kawanova.seoanalyzer"
    )
)

@Composable
fun SisterAppsBanner(
    modifier: Modifier = Modifier,
    isJapanese: Boolean = true
) {
    val context = LocalContext.current
    val randomApp = sisterApps.random()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Slate800)
            .border(1.dp, Slate700, RoundedCornerShape(12.dp))
            .clickable {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=${randomApp.packageName}")
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                try {
                    context.startActivity(intent)
                } catch (_: Exception) {
                    val webIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=${randomApp.packageName}")
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(webIntent)
                }
            }
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = randomApp.icon,
                fontSize = 24.sp
            )
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isJapanese) randomApp.nameJa else randomApp.nameEn,
                        color = Slate100,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(AmberGold.copy(alpha = 0.2f))
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = if (isJapanese) "姉妹アプリ" else "Sister App",
                            color = AmberLight,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (isJapanese) randomApp.descJa else randomApp.descEn,
                    color = Slate400,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }
            Text(
                text = if (isJapanese) "入手 →" else "Get →",
                color = AmberGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
