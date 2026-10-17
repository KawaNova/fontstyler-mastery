# 🔤 FontStyler - Typography Inspector & Code

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-M3-purple.svg)](https://developer.android.com/jetpack/compose)
[![Tests](https://img.shields.io/badge/Unit%20Tests-Passing-brightgreen.svg)]()
[![License](https://img.shields.io/badge/License-MIT-orange.svg)]()

> **直感的かつ高精度。Web・モバイルデザイナーとエンジニアのための完全オンデバイス・タイポグラフィ検証＆コード生成ツール**

---

## 🌟 主な特徴

- 🎛️ **直感スライダーによる文字組調整**: フォントサイズ（px/rem）、ウェイト（100〜900）、行間（line-height）、字間（letter-spacing）、揃え、大文字小文字変換、斜体、下線
- ⚡ **マルチプラットフォーム・コード即時生成**: CSS, Tailwind CSS, Jetpack Compose (`TextStyle`), Flutter (`TextStyle`), SwiftUI (`.font(...)`) へワンタップでコピー
- 👁️ **WCAG 2.1 コントラスト判定**: 文字色と背景色の視認性比率とAA/AAA適合度をW3C計算式でリアルタイム判定
- 📚 **デザイナー厳選20選プリセット**: Hero Display, Clean Body, CTA Button, Luxury Serif, Code Snippet 等を収録
- 💾 **マイタイポグラフィ保存**: お気に入りのテキストスタイル設定を名前をつけて無制限に保存
- 🔒 **100% オフライン＆プライバシー保護**: 全データは端末内にのみ保存。外部通信なし・追跡なし

---

## 🏗️ 技術スタック & アーキテクチャ

- **言語**: Kotlin 2.0.21
- **UI**: Jetpack Compose + Material Design 3
- **アーキテクチャ**: MVVM (Model-View-ViewModel) + Pure Kotlin Domain Engine
- **計算エンジン**: `TypographyEngine.kt` (CSS/Tailwind/Compose/Flutter/SwiftUIコードジェネレータ、WCAG 2.1 相対輝度・コントラスト比計算)
- **データ永続化**: AndroidX Preferences DataStore (JSONシリアライズ)
- **ビルドツール**: Gradle 8.8.x + AGP 8.8.x
- **SDK**: minSdk 24, compileSdk / targetSdk 36

---

## 🧪 テスト実行

```powershell
.\gradlew testDebugUnitTest
```

---

## 📦 ビルド & リリース

```powershell
# AAB (Android App Bundle) 生成
.\gradlew bundleRelease

# APK 生成
.\gradlew assembleRelease
```

---

## 📄 ライセンス

Copyright (c) 2026 KawaNova. All rights reserved.
Licensed under the MIT License.
