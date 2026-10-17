package com.kawanova.fontstyler.data

object PresetStylesData {

    val presets: List<TypeStylePreset> = listOf(
        // 1. Headings
        TypeStylePreset(
            id = "hero_display",
            name = "Hero Display (巨大見出し)",
            category = "Headings",
            description = "ランディングページ最上部やメインビジュアル用の圧巻タイポグラフィ",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SANS_SERIF,
                fontSizeSp = 48f,
                fontWeightValue = 800,
                lineHeightMultiplier = 1.15f,
                letterSpacingEm = -0.02f,
                sampleText = "Build The Future."
            )
        ),
        TypeStylePreset(
            id = "modern_h1",
            name = "Modern H1 (第1見出し)",
            category = "Headings",
            description = "記事タイトルやダッシュボードのメインヘッダーに最適なバランス",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SANS_SERIF,
                fontSizeSp = 32f,
                fontWeightValue = 700,
                lineHeightMultiplier = 1.25f,
                letterSpacingEm = -0.01f,
                sampleText = "モダンなWebデザイン原則"
            )
        ),
        TypeStylePreset(
            id = "section_h2",
            name = "Section H2 (セクション見出し)",
            category = "Headings",
            description = "各章・ブロックの境界を明確にする明快なサブタイトル",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SANS_SERIF,
                fontSizeSp = 24f,
                fontWeightValue = 600,
                lineHeightMultiplier = 1.35f,
                letterSpacingEm = 0.0f,
                sampleText = "2. システムアーキテクチャの概要"
            )
        ),
        TypeStylePreset(
            id = "sub_h3",
            name = "Subtitle H3 (小見出し)",
            category = "Headings",
            description = "カードタイトルやウィジェットヘッダーに心地よいサイズ感",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SANS_SERIF,
                fontSizeSp = 18f,
                fontWeightValue = 600,
                lineHeightMultiplier = 1.4f,
                letterSpacingEm = 0.01f,
                sampleText = "リアルタイムデータ同期の仕組み"
            )
        ),

        // 2. Body & Reading
        TypeStylePreset(
            id = "clean_body",
            name = "Clean Body (標準本文)",
            category = "Body & Reading",
            description = "可読性と視線移動の負荷を極限まで減らした黄金比本文スタイル",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SANS_SERIF,
                fontSizeSp = 16f,
                fontWeightValue = 400,
                lineHeightMultiplier = 1.65f,
                letterSpacingEm = 0.02f,
                sampleText = "タイポグラフィは単に文字を表示するだけでなく、情報階層とブランドの品格を直感的に伝達する重要なデザイン要素です。"
            )
        ),
        TypeStylePreset(
            id = "lead_paragraph",
            name = "Lead Paragraph (導入文リード)",
            category = "Body & Reading",
            description = "記事冒頭で読者を惹きつける少し大きめのゆったりした導入スタイル",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SANS_SERIF,
                fontSizeSp = 18f,
                fontWeightValue = 300,
                lineHeightMultiplier = 1.6f,
                letterSpacingEm = 0.01f,
                sampleText = "本記事では、デザインシステムにおけるフォント選定とコード実装の効率化について実践的な知見を共有します。"
            )
        ),
        TypeStylePreset(
            id = "compact_caption",
            name = "Compact Caption (キャプション・注釈)",
            category = "Body & Reading",
            description = "画像キャプション、注釈、タイムスタンプ用の控えめな補助テキスト",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SANS_SERIF,
                fontSizeSp = 12f,
                fontWeightValue = 400,
                lineHeightMultiplier = 1.4f,
                letterSpacingEm = 0.02f,
                textColorHex = "#94A3B8",
                sampleText = "※ 2026年10月時点の最新W3Cアクセシビリティ仕様に準拠しています。"
            )
        ),
        TypeStylePreset(
            id = "blockquote_style",
            name = "Blockquote (引用文)",
            category = "Body & Reading",
            description = "斜体と少し広めの行間で際立たせる引用・格言用スタイル",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SERIF,
                fontSizeSp = 17f,
                fontWeightValue = 400,
                lineHeightMultiplier = 1.7f,
                letterSpacingEm = 0.03f,
                isItalic = true,
                textColorHex = "#E2E8F0",
                sampleText = "「シンプルさとは、不要なものを削ぎ落とし、本質のみを際立たせることである。」"
            )
        ),

        // 3. UI Components
        TypeStylePreset(
            id = "button_cta",
            name = "Button CTA (ボタンプライマリ)",
            category = "UI Components",
            description = "タップ意欲を高める力強い大文字トラッキングボタン",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SANS_SERIF,
                fontSizeSp = 15f,
                fontWeightValue = 700,
                lineHeightMultiplier = 1.0f,
                letterSpacingEm = 0.06f,
                textTransform = TextTransformType.UPPERCASE,
                textAlign = TextAlignType.CENTER,
                textColorHex = "#0F172A",
                backgroundColorHex = "#10B981",
                sampleText = "GET STARTED NOW"
            )
        ),
        TypeStylePreset(
            id = "nav_tab",
            name = "Navigation Tab (ナビゲーションタブ)",
            category = "UI Components",
            description = "ヘッダーや下部バーのアイテムラベルに最適な視認性",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SANS_SERIF,
                fontSizeSp = 13f,
                fontWeightValue = 600,
                lineHeightMultiplier = 1.2f,
                letterSpacingEm = 0.03f,
                sampleText = "ダッシュボード / プロジェクト"
            )
        ),
        TypeStylePreset(
            id = "badge_pill",
            name = "Badge Pill (ステータスタグ)",
            category = "UI Components",
            description = "NEWやSTATUSを示すコンパクトで高密度なピルバッジ",
            config = TypographyConfig(
                fontFamily = FontFamilyType.MONOSPACE,
                fontSizeSp = 11f,
                fontWeightValue = 700,
                lineHeightMultiplier = 1.0f,
                letterSpacingEm = 0.08f,
                textTransform = TextTransformType.UPPERCASE,
                textColorHex = "#38BDF8",
                sampleText = "RELEASED v2.0"
            )
        ),
        TypeStylePreset(
            id = "stat_number",
            name = "Stat Big Number (KPI数値)",
            category = "UI Components",
            description = "ダッシュボードの成果数値・統計を圧倒的な存在感で強調",
            config = TypographyConfig(
                fontFamily = FontFamilyType.MONOSPACE,
                fontSizeSp = 42f,
                fontWeightValue = 900,
                lineHeightMultiplier = 1.0f,
                letterSpacingEm = -0.03f,
                textColorHex = "#F59E0B",
                sampleText = "$1,280,000"
            )
        ),

        // 4. Serif & Editorial
        TypeStylePreset(
            id = "luxury_serif",
            name = "Luxury Serif (高級誌風見出し)",
            category = "Editorial & Serif",
            description = "ブランドマガジンやエディトリアルを彩る優雅なセリフ書体",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SERIF,
                fontSizeSp = 36f,
                fontWeightValue = 400,
                lineHeightMultiplier = 1.3f,
                letterSpacingEm = 0.04f,
                textColorHex = "#FEF08A",
                sampleText = "The Art of Refinement."
            )
        ),
        TypeStylePreset(
            id = "literary_essay",
            name = "Literary Essay (文学・随筆本文)",
            category = "Editorial & Serif",
            description = "長文読書でも疲れにくい、格調高い文学的行間レイアウト",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SERIF,
                fontSizeSp = 16f,
                fontWeightValue = 400,
                lineHeightMultiplier = 1.8f,
                letterSpacingEm = 0.03f,
                sampleText = "木々の間を吹き抜ける風が、静かな夕暮れの空に秋の訪れを告げていた。"
            )
        ),
        TypeStylePreset(
            id = "vintage_headline",
            name = "Vintage Headline (アンティーク見出し)",
            category = "Editorial & Serif",
            description = "歴史的・クラシックなポスターを思わせる重厚なセリフ体",
            config = TypographyConfig(
                fontFamily = FontFamilyType.SERIF,
                fontSizeSp = 30f,
                fontWeightValue = 700,
                lineHeightMultiplier = 1.2f,
                letterSpacingEm = 0.05f,
                textTransform = TextTransformType.UPPERCASE,
                sampleText = "CHRONICLES & TALES"
            )
        ),

        // 5. Tech & Monospace
        TypeStylePreset(
            id = "code_snippet",
            name = "Code Snippet (コードブロック)",
            category = "Tech & Monospace",
            description = "プログラミングコードやJSON構文が最も読みやすい等幅スタイル",
            config = TypographyConfig(
                fontFamily = FontFamilyType.MONOSPACE,
                fontSizeSp = 14f,
                fontWeightValue = 500,
                lineHeightMultiplier = 1.5f,
                letterSpacingEm = 0.0f,
                textColorHex = "#38BDF8",
                sampleText = "const user = await fetchUser({ id: 42 });"
            )
        ),
        TypeStylePreset(
            id = "terminal_green",
            name = "Terminal Prompt (ターミナル画面)",
            category = "Tech & Monospace",
            description = "ハッカー風CLIコンソールを再現する鮮やかなグリーンスタイル",
            config = TypographyConfig(
                fontFamily = FontFamilyType.MONOSPACE,
                fontSizeSp = 13f,
                fontWeightValue = 600,
                lineHeightMultiplier = 1.4f,
                letterSpacingEm = 0.02f,
                textColorHex = "#22C55E",
                backgroundColorHex = "#050B14",
                sampleText = "user@kawanova:~$ kubectl get pods -A"
            )
        ),
        TypeStylePreset(
            id = "data_table_mono",
            name = "Data Table (数値データ表)",
            category = "Tech & Monospace",
            description = "桁揃えが崩れない金融・帳票テーブル向け等幅グリッド",
            config = TypographyConfig(
                fontFamily = FontFamilyType.MONOSPACE,
                fontSizeSp = 13f,
                fontWeightValue = 400,
                lineHeightMultiplier = 1.3f,
                letterSpacingEm = 0.0f,
                textAlign = TextAlignType.RIGHT,
                sampleText = "+ 14,285.50 JPY"
            )
        ),
        TypeStylePreset(
            id = "cyberpunk_neon",
            name = "Cyberpunk Neon (近未来サイバー)",
            category = "Tech & Monospace",
            description = "ゲームUIやサイバーパンク風グラフィックに映える極太トラッキング",
            config = TypographyConfig(
                fontFamily = FontFamilyType.MONOSPACE,
                fontSizeSp = 22f,
                fontWeightValue = 800,
                lineHeightMultiplier = 1.2f,
                letterSpacingEm = 0.15f,
                textTransform = TextTransformType.UPPERCASE,
                textColorHex = "#F43F5E",
                sampleText = "SYSTEM OVERDRIVE"
            )
        ),
        TypeStylePreset(
            id = "handwritten_cursive",
            name = "Handwritten Note (手書きメモ)",
            category = "Editorial & Serif",
            description = "カジュアルで温かみのある手書き・筆記体スタイル",
            config = TypographyConfig(
                fontFamily = FontFamilyType.CURSIVE,
                fontSizeSp = 20f,
                fontWeightValue = 500,
                lineHeightMultiplier = 1.5f,
                letterSpacingEm = 0.02f,
                textColorHex = "#FDE047",
                sampleText = "Don't forget to smile today :)"
            )
        )
    )
}
