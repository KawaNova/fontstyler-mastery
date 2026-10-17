package com.kawanova.fontstyler.data

import org.junit.Assert.*
import org.junit.Test

class TypographyEngineTest {

    @Test
    fun testToCss_generation() {
        val config = TypographyConfig(
            fontFamily = FontFamilyType.SANS_SERIF,
            fontSizeSp = 24f,
            fontWeightValue = 700,
            lineHeightMultiplier = 1.5f,
            letterSpacingEm = 0.05f,
            textTransform = TextTransformType.UPPERCASE,
            textAlign = TextAlignType.CENTER,
            textColorHex = "#38BDF8"
        )
        val css = TypographyEngine.toCss(config)
        assertTrue(css.contains("font-size: 24px; /* 1.5rem */"))
        assertTrue(css.contains("font-weight: 700;"))
        assertTrue(css.contains("line-height: 1.50;"))
        assertTrue(css.contains("letter-spacing: 0.05em;"))
        assertTrue(css.contains("text-transform: uppercase;"))
        assertTrue(css.contains("text-align: center;"))
        assertTrue(css.contains("color: #38BDF8;"))
    }

    @Test
    fun testToTailwind_classes() {
        val config = TypographyConfig(
            fontFamily = FontFamilyType.MONOSPACE,
            fontSizeSp = 24f,
            fontWeightValue = 600,
            lineHeightMultiplier = 1.4f,
            letterSpacingEm = 0.05f,
            textTransform = TextTransformType.UPPERCASE,
            textAlign = TextAlignType.CENTER,
            textColorHex = "#F59E0B"
        )
        val tw = TypographyEngine.toTailwind(config)
        assertTrue(tw.contains("font-mono"))
        assertTrue(tw.contains("text-2xl"))
        assertTrue(tw.contains("font-semibold"))
        assertTrue(tw.contains("leading-snug"))
        assertTrue(tw.contains("tracking-wider"))
        assertTrue(tw.contains("uppercase"))
        assertTrue(tw.contains("text-center"))
        assertTrue(tw.contains("text-[#F59E0B]"))
    }

    @Test
    fun testToCompose_textStyle() {
        val config = TypographyConfig(
            fontFamily = FontFamilyType.SERIF,
            fontSizeSp = 18f,
            fontWeightValue = 600,
            lineHeightMultiplier = 1.5f,
            letterSpacingEm = 0.02f,
            textColorHex = "#10B981"
        )
        val composeCode = TypographyEngine.toCompose(config)
        assertTrue(composeCode.contains("fontFamily = FontFamily.Serif"))
        assertTrue(composeCode.contains("fontSize = 18.sp"))
        assertTrue(composeCode.contains("fontWeight = FontWeight.SemiBold"))
        assertTrue(composeCode.contains("lineHeight = 27.0.sp"))
        assertTrue(composeCode.contains("Color(0xFF10B981)"))
    }

    @Test
    fun testToFlutter_textStyle() {
        val config = TypographyConfig(
            fontFamily = FontFamilyType.CURSIVE,
            fontSizeSp = 20f,
            fontWeightValue = 500,
            lineHeightMultiplier = 1.4f,
            textColorHex = "#FFFFFF"
        )
        val flutterCode = TypographyEngine.toFlutter(config)
        assertTrue(flutterCode.contains("fontFamily: 'cursive'"))
        assertTrue(flutterCode.contains("fontSize: 20.0"))
        assertTrue(flutterCode.contains("fontWeight: FontWeight.w500"))
        assertTrue(flutterCode.contains("height: 1.40"))
        assertTrue(flutterCode.contains("Color(0xFFFFFFFF)"))
    }

    @Test
    fun testToSwiftUi_viewModifiers() {
        val config = TypographyConfig(
            fontSizeSp = 32f,
            fontWeightValue = 800,
            lineHeightMultiplier = 1.3f,
            letterSpacingEm = 0.02f,
            textColorHex = "#FEF08A"
        )
        val swiftCode = TypographyEngine.toSwiftUi(config)
        assertTrue(swiftCode.contains(".font(.system(size: 32, weight: .heavy"))
        assertTrue(swiftCode.contains(".lineSpacing("))
        assertTrue(swiftCode.contains(".foregroundColor(Color(hex: \"FEF08A\"))"))
    }

    @Test
    fun testEvaluateContrast_blackAndWhite() {
        // 白文字 vs 黒背景 = 21.0:1
        val result = TypographyEngine.evaluateContrast("#FFFFFF", "#000000")
        assertEquals(21.0f, result.ratio, 0.1f)
        assertTrue(result.isAaPass)
        assertTrue(result.isAaaPass)
        assertTrue(result.isLargeAaPass)

        // 同一色 = 1.0:1
        val sameResult = TypographyEngine.evaluateContrast("#FFFFFF", "#FFFFFF")
        assertEquals(1.0f, sameResult.ratio, 0.1f)
        assertFalse(sameResult.isAaPass)
        assertFalse(sameResult.isAaaPass)
    }

    @Test
    fun testPresetStylesData_integrity() {
        val presets = PresetStylesData.presets
        assertEquals(20, presets.size)
        for (p in presets) {
            assertTrue(p.name.isNotBlank())
            assertTrue(p.category.isNotBlank())
            assertTrue(p.config.fontSizeSp in 10f..100f)
            assertTrue(p.config.fontWeightValue in 100..900)
            assertTrue(p.config.lineHeightMultiplier in 0.8f..3.0f)
        }
    }

    @Test
    fun testSavedTypeStyleSerialization() {
        val original = SavedTypeStyle(
            id = "style_123",
            name = "マイ見出し",
            category = "Custom",
            config = TypographyConfig(
                fontSizeSp = 36f,
                fontWeightValue = 800,
                textColorHex = "#38BDF8"
            )
        )
        val json = original.toJson()
        val restored = SavedTypeStyle.fromJson(json)

        assertEquals(original.id, restored.id)
        assertEquals(original.name, restored.name)
        assertEquals(original.config.fontSizeSp, restored.config.fontSizeSp, 0.01f)
        assertEquals(original.config.fontWeightValue, restored.config.fontWeightValue)
        assertEquals(original.config.textColorHex, restored.config.textColorHex)
    }
}
