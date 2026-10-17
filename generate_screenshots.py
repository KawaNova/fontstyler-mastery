import os
from PIL import Image, ImageDraw, ImageFont

def make_screenshot(title_text, subtitle_text, content_type, filename):
    w, h = 1080, 2400
    img = Image.new('RGBA', (w, h), (15, 23, 42, 255)) # #0F172A
    draw = ImageDraw.Draw(img)

    # Fonts
    font_bold = "C:/Windows/Fonts/meiryob.ttc"
    if not os.path.exists(font_bold):
        font_bold = "C:/Windows/Fonts/arialbd.ttf"
        
    font_header_title = ImageFont.truetype(font_bold, 52)
    font_header_sub = ImageFont.truetype(font_bold, 32)
    font_card_title = ImageFont.truetype(font_bold, 36)
    font_card_body = ImageFont.truetype(font_bold, 28)
    font_small = ImageFont.truetype(font_bold, 24)
    font_mono = ImageFont.truetype("C:/Windows/Fonts/consola.ttf", 26)
    font_mono_bold = ImageFont.truetype("C:/Windows/Fonts/consolab.ttf", 32)

    # Header Banner
    draw.text((60, 100), title_text, fill=(255, 255, 255), font=font_header_title)
    draw.text((60, 175), subtitle_text, fill=(251, 191, 36), font=font_header_sub)

    # Phone Frame Container (x: 60 ~ 1020, y: 260 ~ 2300)
    fx_l, fy_t, fx_r, fy_b = 60, 260, 1020, 2280
    draw.rounded_rectangle([fx_l, fy_t, fx_r, fy_b], radius=40, fill=(30, 41, 59, 255), outline=(71, 85, 105, 255), width=4)

    # Top Status Bar inside phone
    draw.rounded_rectangle([fx_l + 30, fy_t + 25, fx_r - 30, fy_t + 70], radius=10, fill=(15, 23, 42, 200))
    draw.text((fx_l + 50, fy_t + 32), "🔤 FontStyler (Typography & Code)", fill=(251, 191, 36), font=font_card_body)

    if content_type == "sliders":
        # 1. Live Preview Canvas
        draw.rounded_rectangle([fx_l + 30, fy_t + 90, fx_r - 30, fy_t + 520], radius=20, fill=(15, 23, 42), outline=(245, 158, 11), width=3)
        # Header in canvas
        draw.rounded_rectangle([fx_l + 50, fy_t + 110, fx_l + 220, fy_t + 150], radius=8, fill=(16, 185, 129, 50), outline=(16, 185, 129), width=1)
        draw.text((fx_l + 65, fy_t + 118), "AAA 14.2:1 適合", fill=(16, 185, 129), font=font_small)
        draw.text((fx_l + 240, fy_t + 118), "36px (2.25rem) / w800", fill=(148, 163, 184), font=font_small)

        # Big Preview Headline
        draw.text((fx_l + 50, fy_t + 180), "Build The Future.", fill=(255, 255, 255), font=ImageFont.truetype(font_bold, 54))
        draw.text((fx_l + 50, fy_t + 270), "美しい文字組と視認性の最適バランス。", fill=(203, 213, 225), font=font_card_title)
        draw.text((fx_l + 50, fy_t + 330), "情報階層と品格を直感的に伝達するタイポグラフィ。", fill=(148, 163, 184), font=font_card_body)

        # 2. Sliders Panel
        sy = fy_t + 550
        draw.text((fx_l + 40, sy), "🎛️ タイポグラフィ精密チューニング", fill=(255, 255, 255), font=font_card_title)
        
        sliders = [
            ("フォントサイズ: 36 px (2.25 rem)", 36/72, (245, 158, 11)),
            ("フォントウェイト: 800 (ExtraBold)", 800/900, (168, 85, 247)),
            ("行間 (Line Height): 1.35x (48.6px)", (1.35-1.0)/1.5, (56, 189, 248)),
            ("字間 (Letter Spacing): -0.02 em", 0.35, (16, 185, 129))
        ]
        for idx, (s_label, s_rate, s_col) in enumerate(sliders):
            ly = sy + 60 + idx * 165
            draw.text((fx_l + 40, ly), s_label, fill=(203, 213, 225), font=font_card_body)
            # track
            draw.rounded_rectangle([fx_l + 40, ly + 50, fx_r - 40, ly + 76], radius=12, fill=(51, 65, 85))
            draw.rounded_rectangle([fx_l + 40, ly + 50, fx_l + 40 + int((fx_r - fx_l - 80) * s_rate), ly + 76], radius=12, fill=s_col)
            # thumb
            thumb_x = fx_l + 40 + int((fx_r - fx_l - 80) * s_rate)
            draw.ellipse([thumb_x - 18, ly + 46, thumb_x + 18, ly + 80], fill=(255, 255, 255), outline=s_col, width=4)

        # Font Family Buttons
        fy = sy + 740
        draw.text((fx_l + 40, fy), "フォントファミリー選択", fill=(255, 255, 255), font=font_card_title)
        fams = ["Sans-Serif", "Serif (明朝)", "Monospace (等幅)", "Cursive"]
        for idx, f_name in enumerate(fams):
            bx = fx_l + 40 + (idx % 2) * 450
            by = fy + 55 + (idx // 2) * 90
            is_active = (idx == 0)
            draw.rounded_rectangle([bx, by, bx + 420, by + 75], radius=12, fill=(245, 158, 11) if is_active else (15, 23, 42), outline=(245, 158, 11), width=2)
            draw.text((bx + 30, by + 18), f_name, fill=(15, 23, 42) if is_active else (255, 255, 255), font=font_card_body)

    elif content_type == "code":
        # Multi-Platform Code Export
        draw.text((fx_l + 40, fy_t + 90), "⚡ マルチプラットフォーム・コード生成", fill=(255, 255, 255), font=font_card_title)

        # Tabs: [CSS] [Tailwind] [Compose] [Flutter] [SwiftUI]
        tabs = [("CSS", True), ("Tailwind", False), ("Compose", False), ("Flutter", False), ("SwiftUI", False)]
        for idx, (tname, is_act) in enumerate(tabs):
            tx = fx_l + 40 + idx * 180
            draw.rounded_rectangle([tx, fy_t + 150, tx + 165, fy_t + 220], radius=12, fill=(245, 158, 11) if is_act else (15, 23, 42), outline=(245, 158, 11) if is_act else (71, 85, 105), width=2)
            draw.text((tx + 25, fy_t + 170), tname, fill=(15, 23, 42) if is_act else (203, 213, 225), font=font_card_body)

        # CSS Code Box
        draw.rounded_rectangle([fx_l + 30, fy_t + 250, fx_r - 30, fy_t + 850], radius=18, fill=(11, 15, 25), outline=(245, 158, 11), width=2)
        # Header bar
        draw.rounded_rectangle([fx_l + 30, fy_t + 250, fx_r - 30, fy_t + 320], radius=16, fill=(30, 41, 59))
        draw.text((fx_l + 60, fy_t + 268), "CSS OUTPUT (.typography)", fill=(251, 191, 36), font=font_card_body)
        draw.rounded_rectangle([fx_r - 180, fy_t + 262, fx_r - 50, fy_t + 308], radius=8, fill=(245, 158, 11))
        draw.text((fx_r - 160, fy_t + 270), "📋 コピー", fill=(15, 23, 42), font=font_small)

        css_lines = [
            ".typography {",
            "  font-family: sans-serif, system-ui;",
            "  font-size: 36px; /* 2.250rem */",
            "  font-weight: 800;",
            "  line-height: 1.35;",
            "  letter-spacing: -0.02em;",
            "  text-transform: uppercase;",
            "  color: #F8FAFC;",
            "}"
        ]
        for idx, line in enumerate(css_lines):
            draw.text((fx_l + 60, fy_t + 350 + idx * 52), line, fill=(226, 232, 240), font=font_mono_bold)

        # Tailwind Equivalent Box
        draw.rounded_rectangle([fx_l + 30, fy_t + 890, fx_r - 30, fy_t + 1240], radius=18, fill=(11, 15, 25), outline=(56, 189, 248), width=2)
        draw.rounded_rectangle([fx_l + 30, fy_t + 890, fx_r - 30, fy_t + 960], radius=16, fill=(30, 41, 59))
        draw.text((fx_l + 60, fy_t + 908), "TAILWIND CSS CLASSES", fill=(56, 189, 248), font=font_card_body)
        draw.text((fx_l + 60, fy_t + 990), "font-sans text-4xl font-extrabold", fill=(56, 189, 248), font=font_mono_bold)
        draw.text((fx_l + 60, fy_t + 1050), "leading-snug tracking-tight uppercase", fill=(255, 255, 255), font=font_mono_bold)
        draw.text((fx_l + 60, fy_t + 1110), "text-[#F8FAFC]", fill=(245, 158, 11), font=font_mono_bold)

        # Jetpack Compose Box
        draw.rounded_rectangle([fx_l + 30, fy_t + 1280, fx_r - 30, fy_t + 1750], radius=18, fill=(11, 15, 25), outline=(168, 85, 247), width=2)
        draw.rounded_rectangle([fx_l + 30, fy_t + 1280, fx_r - 30, fy_t + 1350], radius=16, fill=(30, 41, 59))
        draw.text((fx_l + 60, fy_t + 1298), "JETPACK COMPOSE TextStyle", fill=(168, 85, 247), font=font_card_body)
        compose_lines = [
            "TextStyle(",
            "    fontSize = 36.sp,",
            "    fontWeight = FontWeight.ExtraBold,",
            "    lineHeight = 48.6.sp,",
            "    letterSpacing = (-0.72).sp,",
            "    color = Color(0xFFF8FAFC)",
            ")"
        ]
        for idx, line in enumerate(compose_lines):
            draw.text((fx_l + 60, fy_t + 1380 + idx * 50), line, fill=(226, 232, 240), font=font_mono_bold)

    elif content_type == "contrast":
        # WCAG 2.1 Contrast & Readability
        draw.text((fx_l + 40, fy_t + 90), "👁️ WCAG 2.1 コントラスト判定", fill=(255, 255, 255), font=font_card_title)
        draw.text((fx_l + 40, fy_t + 140), "文字色と背景色の視認性をW3C公式計算式で精密解析", fill=(148, 163, 184), font=font_card_body)

        # Contrast Big Score Box
        draw.rounded_rectangle([fx_l + 30, fy_t + 210, fx_r - 30, fy_t + 620], radius=24, fill=(15, 23, 42), outline=(16, 185, 129), width=3)
        draw.text((fx_l + 60, fy_t + 250), "現在のアクティブ比率", fill=(148, 163, 184), font=font_card_body)
        draw.text((fx_l + 60, fy_t + 310), "14.24 : 1", fill=(16, 185, 129), font=ImageFont.truetype(font_bold, 72))
        
        # Badges
        draw.rounded_rectangle([fx_l + 60, fy_t + 430, fx_l + 280, fy_t + 510], radius=14, fill=(16, 185, 129, 50), outline=(16, 185, 129), width=2)
        draw.text((fx_l + 90, fy_t + 450), "✓ AAA 適合", fill=(16, 185, 129), font=font_card_body)
        draw.rounded_rectangle([fx_l + 320, fy_t + 430, fx_l + 540, fy_t + 510], radius=14, fill=(16, 185, 129, 50), outline=(16, 185, 129), width=2)
        draw.text((fx_l + 350, fy_t + 450), "✓ AA 適合", fill=(16, 185, 129), font=font_card_body)
        draw.rounded_rectangle([fx_l + 580, fy_t + 430, fx_l + 840, fy_t + 510], radius=14, fill=(16, 185, 129, 50), outline=(16, 185, 129), width=2)
        draw.text((fx_l + 610, fy_t + 450), "✓ Large AAA", fill=(16, 185, 129), font=font_card_body)

        # Color Pairs Section
        draw.text((fx_l + 40, fy_t + 660), "ワンタップ配色パレット切り替え", fill=(255, 255, 255), font=font_card_title)
        pairs = [
            ("ダーク ＆ ホワイト", "#F8FAFC", "#0F172A", "14.24:1 AAA"),
            ("サイバーネオン", "#38BDF8", "#050B14", "9.85:1 AAA"),
            ("ラグジュアリーゴールド", "#FEF08A", "#1E1B4B", "13.62:1 AAA"),
            ("ターミナルグリーン", "#22C55E", "#0A0A0A", "8.90:1 AAA")
        ]
        for idx, (pname, ptext, pbg, pscore) in enumerate(pairs):
            py = fy_t + 730 + idx * 240
            draw.rounded_rectangle([fx_l + 30, py, fx_r - 30, py + 210], radius=20, fill=(30, 41, 59), outline=(71, 85, 105), width=2)
            draw.text((fx_l + 60, py + 30), pname, fill=(255, 255, 255), font=font_card_title)
            draw.text((fx_l + 60, py + 85), f"文字色: {ptext}  |  背景色: {pbg}", fill=(148, 163, 184), font=font_card_body)
            draw.rounded_rectangle([fx_r - 260, py + 30, fx_r - 60, py + 85], radius=10, fill=(16, 185, 129))
            draw.text((fx_r - 240, py + 40), pscore, fill=(15, 23, 42), font=font_small)

    elif content_type == "presets":
        # Curated 20 Presets
        draw.text((fx_l + 40, fy_t + 90), "📚 厳選20選！タイポグラフィプリセット", fill=(255, 255, 255), font=font_card_title)
        
        # Category Pills
        pills = ["すべて", "Headings", "Body", "UI", "Serif"]
        for idx, pname in enumerate(pills):
            px = fx_l + 30 + idx * 180
            is_active = (idx == 1)
            draw.rounded_rectangle([px, fy_t + 150, px + 165, fy_t + 220], radius=14, fill=(245, 158, 11) if is_active else (15, 23, 42), outline=(245, 158, 11) if is_active else (71, 85, 105), width=2)
            draw.text((px + 20, fy_t + 170), pname, fill=(15, 23, 42) if is_active else (148, 163, 184), font=font_small)

        # Preset Cards
        presets = [
            ("Hero Display (巨大見出し)", "Headings", "Build The Future.", "48px / w800 / LH:1.15x", (245, 158, 11)),
            ("Modern H1 (第1見出し)", "Headings", "モダンなWebデザイン原則", "32px / w700 / LH:1.25x", (56, 189, 248)),
            ("Clean Body (黄金比本文)", "Body & Reading", "タイポグラフィは単に文字を表示するだけでなく...", "16px / w400 / LH:1.65x", (16, 185, 129)),
            ("Button CTA (プライマリボタン)", "UI Components", "GET STARTED NOW", "15px / w700 / UPPERCASE", (168, 85, 247))
        ]
        for idx, (pname, pcat, psample, pmetrics, col) in enumerate(presets):
            py = fy_t + 260 + idx * 360
            draw.rounded_rectangle([fx_l + 30, py, fx_r - 30, py + 320], radius=20, fill=(15, 23, 42, 255), outline=col, width=2)
            # Header
            draw.text((fx_l + 60, py + 30), pname, fill=(255, 255, 255), font=font_card_title)
            draw.rounded_rectangle([fx_r - 220, py + 25, fx_r - 60, py + 75], radius=10, fill=(30, 41, 59))
            draw.text((fx_r - 200, py + 35), pcat, fill=col, font=font_small)
            # Sample render
            draw.text((fx_l + 60, py + 105), psample, fill=(255, 255, 255) if idx != 3 else (251, 191, 36), font=ImageFont.truetype(font_bold, 36 if idx == 0 else 28))
            # Metrics
            draw.text((fx_l + 60, py + 195), pmetrics, fill=(148, 163, 184), font=font_mono)
            draw.text((fx_l + 60, py + 255), "タップしてエディタに適用 →", fill=col, font=font_card_body)

    # Save to store_assets/play/
    out_path = os.path.join("store_assets", "play", filename)
    img.save(out_path, format="PNG")
    print(f"Saved {out_path}")

if __name__ == "__main__":
    os.makedirs(os.path.join("store_assets", "play"), exist_ok=True)
    make_screenshot("直感タイポグラフィ調整", "サイズ・ウェイト・行間・字間をリアルタイム検証", "sliders", "screenshot_1.png")
    make_screenshot("マルチプラットフォーム・コード即出力", "CSS・Tailwind・Compose・Flutter・SwiftUI", "code", "screenshot_2.png")
    make_screenshot("WCAG 2.1 コントラスト判定", "文字色 vs 背景色 の視認性比率＆AA/AAAチェック", "contrast", "screenshot_3.png")
    make_screenshot("厳選20選！デザイナーズプリセット", "Hero Display, Clean Body, Button CTA, Luxury Serif", "presets", "screenshot_4.png")
    print("All screenshots generated!")
