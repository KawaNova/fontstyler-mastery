# アプリ仕様書: 031_font-preview (FontStyler)

## 1. アプリ概要
- **アプリ名**: フォントスタイラー - タイポグラフィ検証＆コード生成 (FontStyler - Typography Inspector & Code)
- **パッケージ名**: `com.kawanova.fontstyler`
- **対象プラットフォーム**: Android (minSdk: 24, targetSdk: 36)
- **主要言語/フレームワーク**: Kotlin 2.0.21, Jetpack Compose, Material 3
- **アーキテクチャ**: MVVM (Model-View-ViewModel), 純粋Kotlinドメインロジック (TypographyEngine)
- **データ永続化**: AndroidX Preferences DataStore (JSONシリアライズ)

---

## 2. コア機能要件

### (1) タイポグラフィ精密チューニング
- **フォントサイズ**: 12sp〜72sp（pxとrem換算値をリアルタイム表示）
- **フォントウェイト**: 100〜900（Thin, ExtraLight, Light, Regular, Medium, SemiBold, Bold, ExtraBold, Black）
- **行間 (Line Height)**: 1.0x〜2.5x（px換算値も表示）
- **字間 (Letter Spacing / Tracking)**: -0.05em〜+0.30em
- **フォントファミリー**: Sans-Serif, Serif, Monospace, Cursive
- **配置・変形・装飾**: 左/中央/右揃え、UPPERCASE/lowercase/Capitalize、斜体 (Italic)、下線 (Underline)
- **カラー設定**: 文字色・背景色のリアルタイム切り替え

### (2) マルチプラットフォーム・コード生成
- **CSS**: `.typography { font-size: 24px; font-weight: 700; ... }`
- **Tailwind CSS**: `text-2xl font-bold leading-normal tracking-wide text-slate-100`
- **Jetpack Compose**: `TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, ...)`
- **Flutter**: `TextStyle(fontSize: 24.0, fontWeight: FontWeight.w700, ...)`
- **SwiftUI**: `.font(.system(size: 24, weight: .bold)).lineSpacing(...)`
ワンタップでクリップボードにコピー可能。

### (3) WCAG 2.1 コントラスト比判定
- 文字色と背景色の相対輝度（W3C公式 sRGB ガンマ補正および相対輝度計算）からコントラスト比をリアルタイム算出
- バッジ表示: `AAA` (7.0:1 以上), `AA` (4.5:1 以上), `Large AA` (3.0:1 以上), `Fail` (3.0:1 未満)

### (4) 厳選20選！デザイナーズ・タイポグラフィプリセット
5つのカテゴリに分類された実用スタイル:
1. **Headings**: Hero Display, Modern H1, Section H2, Subtitle H3
2. **Body & Reading**: Clean Body, Lead Paragraph, Compact Caption, Blockquote
3. **UI Components**: Button CTA, Navigation Tab, Badge Pill, Stat Big Number
4. **Editorial & Serif**: Luxury Serif, Literary Essay, Vintage Headline, Handwritten Note
5. **Tech & Monospace**: Code Snippet, Terminal Prompt, Data Table, Cyberpunk Neon

### (5) マイタイポグラフィ保存
- 調整したスタイルを名前をつけて無制限に保存
- 保存したスタイルの一覧・再適用・削除

---

## 3. UI/UX 仕様
- **テーマ**: ダークモード最適化（ミッドナイトスレート `#0F172A` / `#1E293B`、アンバーゴールド `#F59E0B`、バイオレット `#A855F7`）
- **クロスプロモーション**: SisterAppsBanner（MarkNote, Color Palette, API Tester 等のKawaNovaシリーズ相互リンク）

---

## 4. テスト・品質保証
- `TypographyEngineTest.kt`:
  - CSS, Tailwind, Compose, Flutter, SwiftUI 各言語向けコード出力検証
  - 白黒21.0:1コントラスト比の数理検証
  - プリセット20選の全項目妥当性検証
  - JSONシリアライズ/デシリアライズ往復精度
