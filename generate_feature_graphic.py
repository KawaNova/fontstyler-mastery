import os
from PIL import Image, ImageDraw, ImageFont

def make_feature_graphic():
    w, h = 1024, 500
    img = Image.new('RGBA', (w, h), (15, 23, 42, 255)) # #0F172A
    draw = ImageDraw.Draw(img)

    # Ambient Radial Glow
    glow = Image.new('RGBA', (w, h), (0, 0, 0, 0))
    gdraw = ImageDraw.Draw(glow)
    for r in range(260, 0, -8):
        alpha = int(22 * (1.0 - r / 260.0))
        gdraw.ellipse([700 - r, 250 - r, 700 + r, 250 + r], fill=(245, 158, 11, alpha)) # Amber
        gdraw.ellipse([260 - r, 250 - r, 260 + r, 250 + r], fill=(168, 85, 247, alpha)) # Violet
    img = Image.alpha_composite(img, glow)
    draw = ImageDraw.Draw(img)

    # Fonts
    font_bold = "C:/Windows/Fonts/meiryob.ttc"
    if not os.path.exists(font_bold):
        font_bold = "C:/Windows/Fonts/arialbd.ttf"
    
    font_main_title = ImageFont.truetype(font_bold, 48)
    font_sub_title = ImageFont.truetype(font_bold, 24)
    font_badge = ImageFont.truetype(font_bold, 18)
    font_card_head = ImageFont.truetype(font_bold, 20)
    font_card_body = ImageFont.truetype(font_bold, 15)
    font_mono = ImageFont.truetype("C:/Windows/Fonts/consola.ttf", 14)

    # Left: App Branding
    draw.rounded_rectangle([60, 60, 124, 124], radius=16, fill=(30, 41, 59), outline=(245, 158, 11), width=2)
    draw.text((72, 74), "Aa", fill=(251, 191, 36), font=ImageFont.truetype("C:/Windows/Fonts/arialbd.ttf", 34))

    draw.text((144, 72), "FontStyler", fill=(255, 255, 255), font=font_main_title)
    draw.text((60, 145), "タイポグラフィ検証 ＆ コード生成", fill=(251, 191, 36), font=font_sub_title)
    draw.text((60, 185), "文字組を直感調整。CSS・Tailwind・Composeコードを即出力", fill=(203, 213, 225), font=ImageFont.truetype(font_bold, 17))

    # Feature Badges
    badges = [
        ("🎛️ サイズ/ウェイト/行間", (245, 158, 11)),
        ("⚡ CSS & Tailwind", (56, 189, 248)),
        ("📱 Compose & Flutter", (168, 85, 247)),
        ("👁️ WCAG 2.1 判定", (16, 185, 129)),
        ("📚 20選プリセット", (236, 72, 153))
    ]
    bx = 60
    by = 240
    for btext, bcol in badges:
        draw.rounded_rectangle([bx, by, bx + 180, by + 42], radius=10, fill=(30, 41, 59, 220), outline=bcol, width=2)
        draw.text((bx + 12, by + 10), btext, fill=(255, 255, 255), font=font_badge)
        bx += 195
        if bx > 400:
            bx = 60
            by += 55

    # Right: Mockup Preview Card
    rx_l, ry_t, rx_r, ry_b = 580, 50, 970, 450
    draw.rounded_rectangle([rx_l + 8, ry_t + 8, rx_r + 8, ry_b + 8], radius=24, fill=(10, 15, 29, 200))
    draw.rounded_rectangle([rx_l, ry_t, rx_r, ry_b], radius=24, fill=(30, 41, 59, 255), outline=(71, 85, 105, 255), width=2)

    # Card Top Header
    draw.rounded_rectangle([rx_l + 16, ry_t + 16, rx_r - 16, ry_t + 52], radius=8, fill=(15, 23, 42))
    draw.text((rx_l + 28, ry_t + 22), "🔤 Hero Display: 36px / w800", fill=(255, 255, 255), font=font_card_head)
    draw.rounded_rectangle([rx_r - 110, ry_t + 20, rx_r - 26, ry_t + 48], radius=6, fill=(16, 185, 129))
    draw.text((rx_r - 98, ry_t + 24), "AAA 14.2:1", fill=(15, 23, 42), font=ImageFont.truetype(font_bold, 13))

    # Big Headline Sample
    draw.text((rx_l + 24, ry_t + 70), "Build The Future.", fill=(255, 255, 255), font=ImageFont.truetype(font_bold, 34))
    draw.text((rx_l + 24, ry_t + 115), "洗練された文字組と視認性の最適バランス。", fill=(203, 213, 225), font=font_card_body)

    # Slider Tracks Mockup
    sliders = [
        ("サイズ: 36px (2.25rem)", 0.50, (245, 158, 11)),
        ("ウェイト: 800 (ExtraBold)", 0.85, (168, 85, 247)),
        ("行間: 1.35x (48px)", 0.35, (56, 189, 248))
    ]
    for s_i, (s_label, s_rate, s_col) in enumerate(sliders):
        sy = ry_t + 155 + s_i * 42
        draw.text((rx_l + 24, sy), s_label, fill=(148, 163, 184), font=ImageFont.truetype(font_bold, 12))
        draw.rounded_rectangle([rx_l + 24, sy + 20, rx_r - 24, sy + 28], radius=4, fill=(51, 65, 85))
        draw.rounded_rectangle([rx_l + 24, sy + 20, rx_l + 24 + int((rx_r - rx_l - 48) * s_rate), sy + 28], radius=4, fill=s_col)

    # Generated Code Snippet Box
    draw.rounded_rectangle([rx_l + 20, ry_t + 295, rx_r - 20, ry_t + 385], radius=10, fill=(11, 15, 25), outline=(71, 85, 105), width=1)
    draw.text((rx_l + 32, ry_t + 305), "/* CSS OUTPUT */", fill=(148, 163, 184), font=font_mono)
    draw.text((rx_l + 32, ry_t + 325), "font-size: 36px; font-weight: 800;", fill=(251, 191, 36), font=font_mono)
    draw.text((rx_l + 32, ry_t + 345), "line-height: 1.35; letter-spacing: -0.02em;", fill=(56, 189, 248), font=font_mono)

    # Save
    out_dir = os.path.join("store_assets", "play")
    os.makedirs(out_dir, exist_ok=True)
    p1 = os.path.join(out_dir, "feature_graphic_1024x500.png")
    p2 = os.path.join(out_dir, "feature_graphic.png")
    img.save(p1, format="PNG")
    img.save(p2, format="PNG")
    print("Feature graphics generated successfully!")

if __name__ == "__main__":
    make_feature_graphic()
