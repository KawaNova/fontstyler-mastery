package com.kawanova.fontstyler.data

import org.json.JSONObject

enum class FontFamilyType(val displayName: String, val cssFamily: String) {
    SANS_SERIF("Sans-Serif (ゴシック)", "sans-serif, system-ui, -apple-system"),
    SERIF("Serif (明朝)", "serif, Georgia, Cambria"),
    MONOSPACE("Monospace (等幅)", "monospace, Consolas, Monaco"),
    CURSIVE("Cursive (筆記体)", "cursive, Comic Sans MS")
}

enum class TextTransformType(val displayName: String, val cssValue: String) {
    NONE("標準 (None)", "none"),
    UPPERCASE("大文字 (UPPERCASE)", "uppercase"),
    LOWERCASE("小文字 (lowercase)", "lowercase"),
    CAPITALIZE("先頭大文字 (Capitalize)", "capitalize")
}

enum class TextAlignType(val displayName: String, val cssValue: String) {
    LEFT("左揃え (Left)", "left"),
    CENTER("中央揃え (Center)", "center"),
    RIGHT("右揃え (Right)", "right")
}

enum class CodeExportFormat(val label: String) {
    CSS("CSS"),
    TAILWIND("Tailwind"),
    COMPOSE("Compose"),
    FLUTTER("Flutter"),
    SWIFT_UI("SwiftUI")
}

data class TypographyConfig(
    val fontFamily: FontFamilyType = FontFamilyType.SANS_SERIF,
    val fontSizeSp: Float = 24f,
    val fontWeightValue: Int = 600, // 100 .. 900
    val lineHeightMultiplier: Float = 1.4f, // 1.0 .. 2.5
    val letterSpacingEm: Float = 0.0f, // -0.1 .. 0.5
    val textTransform: TextTransformType = TextTransformType.NONE,
    val textAlign: TextAlignType = TextAlignType.LEFT,
    val textColorHex: String = "#F8FAFC",
    val backgroundColorHex: String = "#0F172A",
    val isItalic: Boolean = false,
    val isUnderline: Boolean = false,
    val sampleText: String = "The quick brown fox jumps over the lazy dog. 美しい文字組と洗練されたタイポグラフィ。"
) {
    fun toJson(): JSONObject {
        return JSONObject().apply {
            put("fontFamily", fontFamily.name)
            put("fontSizeSp", fontSizeSp.toDouble())
            put("fontWeightValue", fontWeightValue)
            put("lineHeightMultiplier", lineHeightMultiplier.toDouble())
            put("letterSpacingEm", letterSpacingEm.toDouble())
            put("textTransform", textTransform.name)
            put("textAlign", textAlign.name)
            put("textColorHex", textColorHex)
            put("backgroundColorHex", backgroundColorHex)
            put("isItalic", isItalic)
            put("isUnderline", isUnderline)
            put("sampleText", sampleText)
        }
    }

    companion object {
        fun fromJson(json: JSONObject): TypographyConfig {
            return TypographyConfig(
                fontFamily = try { FontFamilyType.valueOf(json.optString("fontFamily", FontFamilyType.SANS_SERIF.name)) } catch (_: Exception) { FontFamilyType.SANS_SERIF },
                fontSizeSp = json.optDouble("fontSizeSp", 24.0).toFloat(),
                fontWeightValue = json.optInt("fontWeightValue", 600),
                lineHeightMultiplier = json.optDouble("lineHeightMultiplier", 1.4).toFloat(),
                letterSpacingEm = json.optDouble("letterSpacingEm", 0.0).toFloat(),
                textTransform = try { TextTransformType.valueOf(json.optString("textTransform", TextTransformType.NONE.name)) } catch (_: Exception) { TextTransformType.NONE },
                textAlign = try { TextAlignType.valueOf(json.optString("textAlign", TextAlignType.LEFT.name)) } catch (_: Exception) { TextAlignType.LEFT },
                textColorHex = json.optString("textColorHex", "#F8FAFC"),
                backgroundColorHex = json.optString("backgroundColorHex", "#0F172A"),
                isItalic = json.optBoolean("isItalic", false),
                isUnderline = json.optBoolean("isUnderline", false),
                sampleText = json.optString("sampleText", "The quick brown fox jumps over the lazy dog.")
            )
        }
    }
}

data class TypeStylePreset(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val config: TypographyConfig
)

data class SavedTypeStyle(
    val id: String,
    val name: String,
    val category: String = "Custom",
    val updatedAt: Long = System.currentTimeMillis(),
    val config: TypographyConfig
) {
    fun toJson(): JSONObject {
        return JSONObject().apply {
            put("id", id)
            put("name", name)
            put("category", category)
            put("updatedAt", updatedAt)
            put("config", config.toJson())
        }
    }

    companion object {
        fun fromJson(json: JSONObject): SavedTypeStyle {
            return SavedTypeStyle(
                id = json.optString("id", java.util.UUID.randomUUID().toString()),
                name = json.optString("name", "カスタムスタイル"),
                category = json.optString("category", "Custom"),
                updatedAt = json.optLong("updatedAt", System.currentTimeMillis()),
                config = if (json.has("config")) TypographyConfig.fromJson(json.getJSONObject("config")) else TypographyConfig()
            )
        }
    }
}

data class WcagContrastResult(
    val ratio: Float,
    val ratioFormatted: String,
    val isAaPass: Boolean,
    val isAaaPass: Boolean,
    val isLargeAaPass: Boolean
)
