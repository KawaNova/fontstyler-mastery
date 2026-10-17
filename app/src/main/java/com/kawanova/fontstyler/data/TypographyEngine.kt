package com.kawanova.fontstyler.data

import java.util.Locale
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt

object TypographyEngine {

    /**
     * CSS スタイルシート構文を生成
     */
    fun toCss(config: TypographyConfig): String {
        val rem = String.format(Locale.US, "%.3f", config.fontSizeSp / 16.0).trimEnd('0').trimEnd('.')
        val tracking = String.format(Locale.US, "%.2f", config.letterSpacingEm)
        val leading = String.format(Locale.US, "%.2f", config.lineHeightMultiplier)

        val sb = StringBuilder()
        sb.append(".typography {\n")
        sb.append("  font-family: ${config.fontFamily.cssFamily};\n")
        sb.append("  font-size: ${config.fontSizeSp.roundToInt()}px; /* ${rem}rem */\n")
        sb.append("  font-weight: ${config.fontWeightValue};\n")
        sb.append("  line-height: $leading;\n")
        if (config.letterSpacingEm != 0.0f) {
            sb.append("  letter-spacing: ${tracking}em;\n")
        }
        if (config.textTransform != TextTransformType.NONE) {
            sb.append("  text-transform: ${config.textTransform.cssValue};\n")
        }
        if (config.textAlign != TextAlignType.LEFT) {
            sb.append("  text-align: ${config.textAlign.cssValue};\n")
        }
        if (config.isItalic) {
            sb.append("  font-style: italic;\n")
        }
        if (config.isUnderline) {
            sb.append("  text-decoration: underline;\n")
        }
        sb.append("  color: ${config.textColorHex};\n")
        sb.append("}")
        return sb.toString()
    }

    /**
     * Tailwind CSS クラスを生成
     */
    fun toTailwind(config: TypographyConfig): String {
        val classes = mutableListOf<String>()

        // Font family
        when (config.fontFamily) {
            FontFamilyType.SANS_SERIF -> classes.add("font-sans")
            FontFamilyType.SERIF -> classes.add("font-serif")
            FontFamilyType.MONOSPACE -> classes.add("font-mono")
            FontFamilyType.CURSIVE -> classes.add("font-cursive")
        }

        // Font size
        val sizeClass = when (config.fontSizeSp.roundToInt()) {
            in 0..12 -> "text-xs"
            in 13..14 -> "text-sm"
            in 15..16 -> "text-base"
            in 17..18 -> "text-lg"
            in 19..20 -> "text-xl"
            in 21..24 -> "text-2xl"
            in 25..30 -> "text-3xl"
            in 31..36 -> "text-4xl"
            in 37..48 -> "text-5xl"
            in 49..60 -> "text-6xl"
            else -> "text-[${config.fontSizeSp.roundToInt()}px]"
        }
        classes.add(sizeClass)

        // Weight
        val weightClass = when (config.fontWeightValue) {
            100 -> "font-thin"
            200 -> "font-extralight"
            300 -> "font-light"
            400 -> "font-normal"
            500 -> "font-medium"
            600 -> "font-semibold"
            700 -> "font-bold"
            800 -> "font-extrabold"
            900 -> "font-black"
            else -> "font-[${config.fontWeightValue}]"
        }
        classes.add(weightClass)

        // Line height
        val leadingClass = when {
            config.lineHeightMultiplier <= 1.1f -> "leading-none"
            config.lineHeightMultiplier <= 1.3f -> "leading-tight"
            config.lineHeightMultiplier <= 1.4f -> "leading-snug"
            config.lineHeightMultiplier <= 1.55f -> "leading-normal"
            config.lineHeightMultiplier <= 1.7f -> "leading-relaxed"
            config.lineHeightMultiplier <= 2.1f -> "leading-loose"
            else -> "leading-[${String.format(Locale.US, "%.2f", config.lineHeightMultiplier)}]"
        }
        classes.add(leadingClass)

        // Tracking (letter-spacing)
        when {
            config.letterSpacingEm <= -0.05f -> classes.add("tracking-tighter")
            config.letterSpacingEm < 0.0f -> classes.add("tracking-tight")
            config.letterSpacingEm in 0.01f..0.03f -> classes.add("tracking-wide")
            config.letterSpacingEm in 0.04f..0.07f -> classes.add("tracking-wider")
            config.letterSpacingEm >= 0.08f -> classes.add("tracking-widest")
        }

        // Transform
        when (config.textTransform) {
            TextTransformType.UPPERCASE -> classes.add("uppercase")
            TextTransformType.LOWERCASE -> classes.add("lowercase")
            TextTransformType.CAPITALIZE -> classes.add("capitalize")
            TextTransformType.NONE -> {}
        }

        // Align
        when (config.textAlign) {
            TextAlignType.CENTER -> classes.add("text-center")
            TextAlignType.RIGHT -> classes.add("text-right")
            TextAlignType.LEFT -> {}
        }

        if (config.isItalic) classes.add("italic")
        if (config.isUnderline) classes.add("underline")

        classes.add("text-[${config.textColorHex}]")

        return classes.joinToString(" ")
    }

    /**
     * Jetpack Compose TextStyle 構文を生成
     */
    fun toCompose(config: TypographyConfig): String {
        val calculatedLineHeight = config.fontSizeSp * config.lineHeightMultiplier
        val calculatedTracking = config.fontSizeSp * config.letterSpacingEm
        val weightConst = when (config.fontWeightValue) {
            100 -> "FontWeight.Thin"
            200 -> "FontWeight.ExtraLight"
            300 -> "FontWeight.Light"
            400 -> "FontWeight.Normal"
            500 -> "FontWeight.Medium"
            600 -> "FontWeight.SemiBold"
            700 -> "FontWeight.Bold"
            800 -> "FontWeight.ExtraBold"
            900 -> "FontWeight.Black"
            else -> "FontWeight(weight = ${config.fontWeightValue})"
        }
        val familyConst = when (config.fontFamily) {
            FontFamilyType.SANS_SERIF -> "FontFamily.Default"
            FontFamilyType.SERIF -> "FontFamily.Serif"
            FontFamilyType.MONOSPACE -> "FontFamily.Monospace"
            FontFamilyType.CURSIVE -> "FontFamily.Cursive"
        }
        val alignConst = when (config.textAlign) {
            TextAlignType.LEFT -> "TextAlign.Start"
            TextAlignType.CENTER -> "TextAlign.Center"
            TextAlignType.RIGHT -> "TextAlign.End"
        }

        val colorCode = config.textColorHex.removePrefix("#")
        val colorParam = "Color(0xFF${colorCode.uppercase()})"

        return """
TextStyle(
    fontFamily = $familyConst,
    fontSize = ${config.fontSizeSp.roundToInt()}.sp,
    fontWeight = $weightConst,
    lineHeight = ${String.format(Locale.US, "%.1f", calculatedLineHeight)}.sp,
    letterSpacing = ${String.format(Locale.US, "%.2f", calculatedTracking)}.sp,
    textAlign = $alignConst,
    fontStyle = ${if (config.isItalic) "FontStyle.Italic" else "FontStyle.Normal"},
    textDecoration = ${if (config.isUnderline) "TextDecoration.Underline" else "TextDecoration.None"},
    color = $colorParam
)
        """.trimIndent()
    }

    /**
     * Flutter TextStyle 構文を生成
     */
    fun toFlutter(config: TypographyConfig): String {
        val colorHex = config.textColorHex.removePrefix("#")
        val weightEnum = "FontWeight.w${config.fontWeightValue}"
        val familyStr = when (config.fontFamily) {
            FontFamilyType.SANS_SERIF -> "sans-serif"
            FontFamilyType.SERIF -> "serif"
            FontFamilyType.MONOSPACE -> "monospace"
            FontFamilyType.CURSIVE -> "cursive"
        }
        val calculatedTracking = config.fontSizeSp * config.letterSpacingEm

        return """
TextStyle(
  fontFamily: '$familyStr',
  fontSize: ${config.fontSizeSp.toDouble()},
  fontWeight: $weightEnum,
  height: ${String.format(Locale.US, "%.2f", config.lineHeightMultiplier)},
  letterSpacing: ${String.format(Locale.US, "%.2f", calculatedTracking)},
  fontStyle: ${if (config.isItalic) "FontStyle.italic" else "FontStyle.normal"},
  decoration: ${if (config.isUnderline) "TextDecoration.underline" else "TextDecoration.none"},
  color: const Color(0xFF${colorHex.uppercase()}),
)
        """.trimIndent()
    }

    /**
     * SwiftUI .font(...) 構文を生成
     */
    fun toSwiftUi(config: TypographyConfig): String {
        val weightSwift = when (config.fontWeightValue) {
            100 -> ".ultraLight"
            200 -> ".thin"
            300 -> ".light"
            400 -> ".regular"
            500 -> ".medium"
            600 -> ".semibold"
            700 -> ".bold"
            800 -> ".heavy"
            900 -> ".black"
            else -> ".regular"
        }
        val designSwift = when (config.fontFamily) {
            FontFamilyType.SANS_SERIF -> ".default"
            FontFamilyType.SERIF -> ".serif"
            FontFamilyType.MONOSPACE -> ".monospaced"
            FontFamilyType.CURSIVE -> ".rounded"
        }
        val lineSpacing = config.fontSizeSp * (config.lineHeightMultiplier - 1.0f)
        val tracking = config.fontSizeSp * config.letterSpacingEm

        val sb = StringBuilder()
        sb.append(".font(.system(size: ${config.fontSizeSp.roundToInt()}, weight: $weightSwift, design: $designSwift))\n")
        if (lineSpacing > 0) {
            sb.append(".lineSpacing(${String.format(Locale.US, "%.1f", lineSpacing)})\n")
        }
        if (tracking != 0.0f) {
            sb.append(".tracking(${String.format(Locale.US, "%.2f", tracking)})\n")
        }
        if (config.isItalic) {
            sb.append(".italic()\n")
        }
        if (config.isUnderline) {
            sb.append(".underline()\n")
        }
        val hex = config.textColorHex.removePrefix("#")
        sb.append(".foregroundColor(Color(hex: \"$hex\"))")
        return sb.toString()
    }

    /**
     * WCAG 2.1 コントラスト比計算
     */
    fun evaluateContrast(textColorHex: String, bgColorHex: String): WcagContrastResult {
        val lum1 = calculateLuminance(textColorHex)
        val lum2 = calculateLuminance(bgColorHex)

        val lighter = max(lum1, lum2)
        val darker = min(lum1, lum2)
        val ratio = ((lighter + 0.05f) / (darker + 0.05f))

        val ratioFormatted = String.format(Locale.US, "%.2f:1", ratio)
        val isLargeAa = ratio >= 3.0f
        val isAa = ratio >= 4.5f
        val isAaa = ratio >= 7.0f

        return WcagContrastResult(
            ratio = ratio,
            ratioFormatted = ratioFormatted,
            isAaPass = isAa,
            isAaaPass = isAaa,
            isLargeAaPass = isLargeAa
        )
    }

    private fun calculateLuminance(hexColor: String): Float {
        val cleaned = hexColor.removePrefix("#").trim()
        val colorInt = try {
            cleaned.toLong(16).toInt()
        } catch (_: Exception) {
            0xFFFFFF
        }

        val r = ((colorInt shr 16) and 0xFF) / 255f
        val g = ((colorInt shr 8) and 0xFF) / 255f
        val b = (colorInt and 0xFF) / 255f

        val rs = if (r <= 0.03928f) r / 12.92f else ((r + 0.055f) / 1.055f).pow(2.4f)
        val gs = if (g <= 0.03928f) g / 12.92f else ((g + 0.055f) / 1.055f).pow(2.4f)
        val bs = if (b <= 0.03928f) b / 12.92f else ((b + 0.055f) / 1.055f).pow(2.4f)

        return 0.2126f * rs + 0.7152f * gs + 0.0722f * bs
    }
}
