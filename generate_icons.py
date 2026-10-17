import os
from PIL import Image, ImageDraw, ImageFont

def create_fontstyler_icon(size=512):
    img = Image.new('RGBA', (size, size), (15, 23, 42, 255)) # #0F172A
    draw = ImageDraw.Draw(img)
    s = size / 512.0

    # Ambient radial glow in center (Violet & Amber gold)
    glow_center = (int(256 * s), int(205 * s))
    glow_radius = int(185 * s)
    glow_overlay = Image.new('RGBA', (size, size), (0, 0, 0, 0))
    glow_draw = ImageDraw.Draw(glow_overlay)
    for r in range(glow_radius, 0, -6):
        alpha = int(24 * (1.0 - r / glow_radius))
        glow_draw.ellipse(
            [glow_center[0] - r, glow_center[1] - r, glow_center[0] + r, glow_center[1] + r],
            fill=(168, 85, 247, alpha) # Purple / Violet glow
        )
    img = Image.alpha_composite(img, glow_overlay)
    draw = ImageDraw.Draw(img)

    # Base typography badge card (Sleek slate frame with golden/violet border)
    c_l = int(100 * s)
    c_t = int(85 * s)
    c_r = int(412 * s)
    c_b = int(345 * s)

    # Shadow
    draw.rounded_rectangle(
        [c_l + int(6*s), c_t + int(10*s), c_r + int(6*s), c_b + int(10*s)],
        radius=int(26 * s),
        fill=(5, 10, 20, 210)
    )
    # Slate container
    draw.rounded_rectangle(
        [c_l, c_t, c_r, c_b],
        radius=int(26 * s),
        fill=(30, 41, 59, 250), # #1E293B
        outline=(168, 85, 247, 220), # Violet accent
        width=int(4 * s)
    )

    # Typography Grid / Baseline Guides (Ascender, X-Height, Baseline, Descender)
    guides = [
        (int(125 * s), (99, 102, 241, 140)),  # Ascender line (Indigo)
        (int(175 * s), (56, 189, 248, 140)),  # X-Height line (Sky)
        (int(285 * s), (245, 158, 11, 200)),  # Baseline (Amber warning - prominent)
        (int(315 * s), (244, 63, 94, 120))    # Descender line (Rose)
    ]
    for gy, gcol in guides:
        # Draw dashed line
        gx = c_l + int(24 * s)
        while gx < c_r - int(24 * s):
            draw.line([(gx, gy), (gx + int(10 * s), gy)], fill=gcol, width=int(1.5 * s))
            gx += int(18 * s)

    # Slider / Metrics track at the top
    draw.rounded_rectangle(
        [c_l + int(30 * s), c_t + int(16 * s), c_r - int(30 * s), c_t + int(22 * s)],
        radius=int(3 * s),
        fill=(51, 65, 85)
    )
    # Active track
    draw.rounded_rectangle(
        [c_l + int(30 * s), c_t + int(16 * s), c_l + int(150 * s), c_t + int(22 * s)],
        radius=int(3 * s),
        fill=(245, 158, 11)
    )
    # Slider thumb dot
    draw.ellipse(
        [c_l + int(144 * s), c_t + int(13 * s), c_l + int(156 * s), c_t + int(25 * s)],
        fill=(255, 255, 255),
        outline=(245, 158, 11),
        width=int(2 * s)
    )

    # Large Typography Letterforms: "A" and "a"
    font_serif = "C:/Windows/Fonts/georgiab.ttf"
    if not os.path.exists(font_serif):
        font_serif = "C:/Windows/Fonts/timesbd.ttf"
    if not os.path.exists(font_serif):
        font_serif = "C:/Windows/Fonts/arialbd.ttf"

    font_cap_a = ImageFont.truetype(font_serif, int(155 * s))
    font_small_a = ImageFont.truetype(font_serif, int(115 * s))

    # Shadow for 'A'
    draw.text((int(142 * s), int(122 * s)), "A", fill=(10, 15, 29, 230), font=font_cap_a)
    # Main White & Pure Light 'A'
    draw.text((int(138 * s), int(118 * s)), "A", fill=(255, 255, 255), font=font_cap_a)

    # Shadow for 'a'
    draw.text((int(268 * s), int(168 * s)), "a", fill=(10, 15, 29, 230), font=font_small_a)
    # Vibrant Amber / Gold 'a'
    draw.text((int(264 * s), int(164 * s)), "a", fill=(251, 191, 36), font=font_small_a)

    # Bottom Title Text: "FontStyler"
    font_bold = "C:/Windows/Fonts/arialbd.ttf"
    if not os.path.exists(font_bold):
        font_bold = "C:/Windows/Fonts/meiryob.ttc"
        
    font_title = ImageFont.truetype(font_bold, int(46 * s))
    title_text = "FontStyler"
    
    bbox = draw.textbbox((0, 0), title_text, font=font_title)
    text_w = bbox[2] - bbox[0]
    tx = (size - text_w) // 2
    ty = int(395 * s)

    # Text shadow
    draw.text((tx + int(2*s), ty + int(3*s)), title_text, fill=(5, 10, 20, 230), font=font_title)
    # Main white text
    draw.text((tx, ty), title_text, fill=(255, 255, 255, 255), font=font_title)

    # Sub badge "TYPOGRAPHY & CODE"
    font_sub = ImageFont.truetype(font_bold, int(17 * s))
    sub_text = "TYPOGRAPHY & CODE"
    s_bbox = draw.textbbox((0, 0), sub_text, font=font_sub)
    s_w = s_bbox[2] - s_bbox[0]
    stx = (size - s_w) // 2
    sty = int(455 * s)
    
    # Sub badge pill
    draw.rounded_rectangle(
        [stx - int(12*s), sty - int(4*s), stx + s_w + int(12*s), sty + int(24*s)],
        radius=int(10*s),
        fill=(30, 41, 59, 200),
        outline=(245, 158, 11, 160),
        width=int(1.5*s)
    )
    draw.text((stx, sty), sub_text, fill=(251, 191, 36), font=font_sub)

    return img

def create_foreground_only(size=512):
    img = Image.new('RGBA', (size, size), (0, 0, 0, 0))
    scale_factor = 0.72
    
    full_icon = create_fontstyler_icon(size)
    scaled_size = int(size * scale_factor)
    scaled = full_icon.resize((scaled_size, scaled_size), Image.Resampling.LANCZOS)
    
    offset = (size - scaled_size) // 2
    img.paste(scaled, (offset, offset), scaled)
    return img

def generate_all():
    base_dir = r"c:\Users\Kawamoto\Documents\AgentAIDev\mobile-apps\031_font-preview"
    res_dir = os.path.join(base_dir, "app", "src", "main", "res")
    store_dir = os.path.join(base_dir, "store_assets", "play")
    os.makedirs(store_dir, exist_ok=True)

    # 1. 512x512 Play Store icon
    icon_512 = create_fontstyler_icon(512)
    icon_512.save(os.path.join(store_dir, "app_icon_512.png"), format="PNG")
    print("Generated app_icon_512.png")

    # Preview artifact
    art_path = r"C:\Users\Kawamoto\.gemini\antigravity\brain\85f914b0-2e74-4dde-88b5-7aefe5547cc8\fontstyler_icon_test.png"
    icon_512.save(art_path, format="PNG")
    print(f"Saved artifact preview to {art_path}")

    # 2. Mipmap standard icons
    mipmap_sizes = {
        "mipmap-mdpi": 48,
        "mipmap-hdpi": 72,
        "mipmap-xhdpi": 96,
        "mipmap-xxhdpi": 144,
        "mipmap-xxxhdpi": 192
    }
    for folder, px in mipmap_sizes.items():
        d = os.path.join(res_dir, folder)
        os.makedirs(d, exist_ok=True)
        ico = icon_512.resize((px, px), Image.Resampling.LANCZOS)
        ico.save(os.path.join(d, "ic_launcher.png"), format="PNG")
        ico.save(os.path.join(d, "ic_launcher_round.png"), format="PNG")
        print(f"Generated {folder}/ic_launcher.png ({px}x{px})")

    # 3. Adaptive foreground image
    fg = create_foreground_only(512)
    drawable_dir = os.path.join(res_dir, "drawable")
    os.makedirs(drawable_dir, exist_ok=True)
    fg.save(os.path.join(drawable_dir, "ic_launcher_foreground_img.png"), format="PNG")
    print("Generated ic_launcher_foreground_img.png")

if __name__ == "__main__":
    generate_all()
