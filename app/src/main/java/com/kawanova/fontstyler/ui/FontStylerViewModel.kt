package com.kawanova.fontstyler.ui

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kawanova.fontstyler.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class FontStylerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TypographyRepository(application)

    // 現在のタイポグラフィ設定
    private val _config = MutableStateFlow(TypographyConfig())
    val config: StateFlow<TypographyConfig> = _config.asStateFlow()

    // 選択中のコード出力形式
    private val _activeFormat = MutableStateFlow(CodeExportFormat.CSS)
    val activeFormat: StateFlow<CodeExportFormat> = _activeFormat.asStateFlow()

    // 出力コードテキスト
    private val _generatedCode = MutableStateFlow("")
    val generatedCode: StateFlow<String> = _generatedCode.asStateFlow()

    // WCAG コントラスト比判定結果
    private val _contrastResult = MutableStateFlow(
        TypographyEngine.evaluateContrast("#F8FAFC", "#0F172A")
    )
    val contrastResult: StateFlow<WcagContrastResult> = _contrastResult.asStateFlow()

    // プリセット一覧
    val allPresets = PresetStylesData.presets

    // プリセット用カテゴリフィルター
    private val _presetCategory = MutableStateFlow("All")
    val presetCategory: StateFlow<String> = _presetCategory.asStateFlow()

    // 保存済みスタイル
    val savedStyles: StateFlow<List<SavedTypeStyle>> = repository.savedStylesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        updateCodeAndContrast(_config.value, _activeFormat.value)
    }

    private fun updateCodeAndContrast(cfg: TypographyConfig, fmt: CodeExportFormat) {
        _generatedCode.value = when (fmt) {
            CodeExportFormat.CSS -> TypographyEngine.toCss(cfg)
            CodeExportFormat.TAILWIND -> TypographyEngine.toTailwind(cfg)
            CodeExportFormat.COMPOSE -> TypographyEngine.toCompose(cfg)
            CodeExportFormat.FLUTTER -> TypographyEngine.toFlutter(cfg)
            CodeExportFormat.SWIFT_UI -> TypographyEngine.toSwiftUi(cfg)
        }
        _contrastResult.value = TypographyEngine.evaluateContrast(cfg.textColorHex, cfg.backgroundColorHex)
    }

    fun updateFontSize(sizeSp: Float) {
        val newCfg = _config.value.copy(fontSizeSp = sizeSp)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun updateFontWeight(weight: Int) {
        val newCfg = _config.value.copy(fontWeightValue = weight)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun updateLineHeight(multiplier: Float) {
        val newCfg = _config.value.copy(lineHeightMultiplier = multiplier)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun updateLetterSpacing(em: Float) {
        val newCfg = _config.value.copy(letterSpacingEm = em)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun updateFontFamily(family: FontFamilyType) {
        val newCfg = _config.value.copy(fontFamily = family)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun updateTextTransform(transform: TextTransformType) {
        val newCfg = _config.value.copy(textTransform = transform)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun updateTextAlign(align: TextAlignType) {
        val newCfg = _config.value.copy(textAlign = align)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun updateTextColor(hex: String) {
        val newCfg = _config.value.copy(textColorHex = hex)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun updateBackgroundColor(hex: String) {
        val newCfg = _config.value.copy(backgroundColorHex = hex)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun toggleItalic() {
        val newCfg = _config.value.copy(isItalic = !_config.value.isItalic)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun toggleUnderline() {
        val newCfg = _config.value.copy(isUnderline = !_config.value.isUnderline)
        _config.value = newCfg
        updateCodeAndContrast(newCfg, _activeFormat.value)
    }

    fun updateSampleText(text: String) {
        val newCfg = _config.value.copy(sampleText = text)
        _config.value = newCfg
    }

    fun selectExportFormat(format: CodeExportFormat) {
        _activeFormat.value = format
        updateCodeAndContrast(_config.value, format)
    }

    fun selectPresetCategory(category: String) {
        _presetCategory.value = category
    }

    fun applyPreset(preset: TypeStylePreset) {
        _config.value = preset.config
        updateCodeAndContrast(preset.config, _activeFormat.value)
    }

    fun applySavedStyle(saved: SavedTypeStyle) {
        _config.value = saved.config
        updateCodeAndContrast(saved.config, _activeFormat.value)
    }

    fun saveCurrentStyle(name: String, onSaved: () -> Unit) {
        val id = UUID.randomUUID().toString()
        val style = SavedTypeStyle(
            id = id,
            name = name.ifBlank { "カスタムスタイル" },
            config = _config.value
        )
        viewModelScope.launch {
            repository.saveStyle(style)
            onSaved()
        }
    }

    fun deleteSavedStyle(id: String) {
        viewModelScope.launch {
            repository.deleteStyle(id)
        }
    }

    fun copyCode(context: Context) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("${_activeFormat.value.label} Code", _generatedCode.value)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "${_activeFormat.value.label}コードをコピーしました 📋", Toast.LENGTH_SHORT).show()
    }
}
