package com.itorba.onmaptextdrawer

import android.graphics.Point
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import java.util.*

/**
 * @author Igor Torba
 */
class TextStyler {

    private var fonts: List<String> = emptyList()
    private var size: Int = 0
    @ColorInt
    private var haloColor: Int = 0
    private var haloWidth: Int = 0
    private var haloBlur: Float = 0.toFloat()
    private var maxWidth: Int = 160
    private var lineHeight: Int = 0
    private var justification = TextJustify.LEFT
    private var anchor = TextAnchor.CENTER
    @FloatRange(from = 0.0, to = 360.0)
    private var rotation: Float = 0F
    private var offset: Point = Point(0, 0)
    @ColorInt
    private var color: Int = 0
    @FloatRange(from = 0.0, to = 1.0)
    private var opacity = 1.0f
    private var dirty = true


    /**
     * Enum which represents how to justify the text.
     */
    // API-UPDATE: Rename to justification in 4.0
    enum class TextJustify {
        LEFT,
        CENTER,
        RIGHT
    }

    /**
     * Enum which represents which part of the text should be used as the anchor point when positioning the text.
     */
    // API-UPDATE: Remove and replace with the Anchor enum
    enum class TextAnchor {
        LEFT,
        CENTER,
        RIGHT,
        TOP,
        BOTTOM,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    /**
     * Clears the [.isDirty] flag.
     */
    fun clearDirty() {
        dirty = false
    }

    /**
     * Returns true if the style has changed since the last call to [.clearDirty].  Defaults to being dirty.
     *
     * @return Whether the style is dirty.
     */
    fun isDirty(): Boolean {
        return dirty
    }

    /**
     * Sets font of the text.
     * <br></br>
     * Does not support data driven stylers.
     *
     * @param font font to use for displaying text. If value is empty string, the default font will be applied.
     * @see .getFont
     * @see .getFonts
     * @see .setFonts
     * @see .setFonts
     */
    fun setFont(font: String) {
        val newFonts = if (font.isEmpty()) emptyList() else listOf(font)
        if (fonts != newFonts) {
            fonts = newFonts
            dirty = true
        }
    }

    /**
     * Sets font of the text.
     * <br></br>
     * Does not support data driven stylers.
     *
     * @param textFont font to use for displaying text. If value is empty string, the default font will be applied.
     * @see .getFont
     */
    @Deprecated("Use {@link #setFont(String)} instead")
    fun setTextFont(textFont: String) {
        val newFonts = if (textFont.isEmpty()) emptyList() else listOf(textFont)
        if (fonts != newFonts) {
            fonts = newFonts
            dirty = true
        }
    }

    /**
     * Sets list of font names for the text, in descending preference order.
     * <br></br>
     * Does not support data driven stylers.
     *
     * @param fonts font names, with highest preference font first. If value is empty list, the default font will be applied.
     * @see .getFonts
     * @see .getFont
     * @see .setFont
     * @see .setFonts
     */
    fun setFonts(vararg fonts: String) {
        val newFonts = Arrays.asList(*fonts)
        if (this.fonts != newFonts) {
            this.fonts = newFonts
            dirty = true
        }
    }

    /**
     * Sets list of font names for the text, in descending preference order.
     * <br></br>
     * Does not support data driven stylers.
     *
     * @param fonts font names, with highest preference font first. If value is empty list, the default font will be applied.
     * @see .getFonts
     * @see .getFont
     * @see .setFont
     * @see .setFonts
     */
    fun setFonts(fonts: List<String>) {
        if (this.fonts != fonts) {
            this.fonts = ArrayList(fonts)
            dirty = true
        }
    }

    /**
     * Gets text font of the style. An empty string indicates that the default font should be used.
     * If multiple fonts are specified, this will return the one with highest preference.
     *
     * @return name of preferred font to use for displaying the text.
     * @see .setFont
     * @see .setFonts
     * @see .setFonts
     * @see .getFonts
     */
    fun getFont(): String {
        return if (fonts!!.isEmpty()) "" else fonts!![0]
    }

    /**
     * Gets text font of the style. An empty string indicates that the default font should be used.
     *
     * @return font to use for displaying the text.
     * @see .setFont
     */
    @Deprecated("Use {@link #getFont()} instead")
    fun getTextFont(): String {
        return if (fonts.isEmpty()) "" else fonts[0]
    }

    /**
     * Gets prioritized list of text font names. An empty array indicates that the default font should be used.
     *
     * @return array of font names in descending preference order.
     * @see .setFont
     * @see .setFonts
     * @see .setFonts
     * @see .getFont
     */
    fun getFonts(): List<String> {
        return ArrayList(fonts)
    }

    /**
     * Sets size of the text.
     *
     * @param size text size in pixels. Cannot be less then 0.
     * @see .getSize
     */
    fun setSize(size: Int) {
        if (this.size != size) {
            this.size = size
            dirty = true
        }
    }

    /**
     * Sets size of the text.
     *
     * @param textSize text size in pixels. Cannot be less then 0.
     * @see .getSize
     */
    @Deprecated("Use {@link #setSize(int)} instead")
    fun setTextSize(textSize: Int) {
        if (textSize != size) {
            size = textSize
            dirty = true
        }
    }

    /**
     * Gets the size of the text.
     *
     * @return text size in pixels.
     * @see .setSize
     */
    fun getSize(): Int {
        return size
    }

    /**
     * Gets the size of the text.
     *
     * @return text size in pixels.
     * @see .setSize
     */
    @Deprecated("Use {@link #getSize()} instead")
    fun getTextSize(): Int {
        return size
    }

    /**
     * Sets the color of the text's halo, which helps it stand out from backgrounds.
     *
     * @param haloColor The color of the text's halo in ARGB format.
     * @see .getHaloColor
     */
    fun setHaloColor(@ColorInt haloColor: Int) {
        if (this.haloColor != haloColor) {
            this.haloColor = haloColor
            dirty = true
        }
    }

    /**
     * Sets the color of the text's halo, which helps it stand out from backgrounds.
     *
     * @param textHaloColor The color of the text's halo in ARGB format.
     * @see .getHaloColor
     */
    @Deprecated("Use {@link #setHaloColor(int)} instead")
    fun setTextHaloColor(@ColorInt textHaloColor: Int) {
        if (haloColor != textHaloColor) {
            haloColor = textHaloColor
            dirty = true
        }
    }

    /**
     * Gets the color of the text's halo for the style, which helps it stand out from backgrounds.
     *
     * @return The color of the text's halo in ARGB format.
     * @see .setHaloColor
     */
    fun getHaloColor(): Int {
        return haloColor
    }

    /**
     * Gets the color of the text's halo for the style, which helps it stand out from backgrounds.
     *
     * @return The color of the text's halo in ARGB format.
     * @see .setHaloColor
     */
    @Deprecated("Use {@link #getHaloColor()} instead")
    fun getTextHaloColor(): Int {
        return haloColor
    }

    /**
     * Sets the halo's width.
     *
     * @param haloWidth distance of halo to the font outline in pixels.
     * @see .getHaloWidth
     */
    fun setHaloWidth(haloWidth: Int) {
        if (this.haloWidth != haloWidth) {
            this.haloWidth = haloWidth
            dirty = true
        }
    }

    /**
     * Sets the halo's width.
     *
     * @param textHaloWidth distance of halo to the font outline in pixels.
     * @see .getHaloWidth
     */
    @Deprecated("Use {@link #setHaloWidth(int)} instead")
    fun setTextHaloWidth(textHaloWidth: Int) {
        if (haloWidth != textHaloWidth) {
            haloWidth = textHaloWidth
            dirty = true
        }
    }

    /**
     * Gets the halo's width.
     *
     * @return distance of halo to the font outline in pixels.
     * @see .setHaloWidth
     */
    fun getHaloWidth(): Int {
        return haloWidth
    }

    /**
     * Gets the halo's width.
     *
     * @return distance of halo to the font outline in pixels.
     * @see .setHaloWidth
     */
    @Deprecated("Use {@link #getHaloWidth()} instead")
    fun getTextHaloWidth(): Int {
        return haloWidth
    }

    /**
     * Sets the halo's blur.
     *
     * @param haloBlur halo's fadeout distance towards the outside in pixels. Cannot be lest then 0.
     * @see .getHaloBlur
     */
    fun setHaloBlur(haloBlur: Float) {

        if (this.haloBlur != haloBlur) {
            this.haloBlur = haloBlur
            dirty = true
        }
    }

    /**
     * Sets the halo's blur.
     *
     * @param textHaloBlur halo's fadeout distance towards the outside in pixels. Cannot be lest then 0.
     * @see .getHaloBlur
     */
    @Deprecated("Use {@link #setHaloBlur(float)} instead")
    fun setTextHaloBlur(textHaloBlur: Float) {

        if (haloBlur != textHaloBlur) {
            haloBlur = textHaloBlur
            dirty = true
        }
    }

    /**
     * Gets the halo's blur.
     *
     * @return halo's fadeout distance towards the outside in pixels.
     * @see .setHaloBlur
     */
    fun getHaloBlur(): Float {
        return haloBlur
    }

    /**
     * Gets the halo's blur.
     *
     * @return halo's fadeout distance towards the outside in pixels.
     * @see .setHaloBlur
     */
    @Deprecated("Use {@link #getHaloBlur()} instead")
    fun getTextHaloBlur(): Float {
        return haloBlur
    }

    /**
     * Sets max width of a line of text.  Used for wrapping the text.
     *
     * @param maxWidth The maximum line width in pixels.
     * @see .getMaxWidth
     */
    fun setMaxWidth(maxWidth: Int) {
        if (this.maxWidth != maxWidth) {
            this.maxWidth = maxWidth
            dirty = true
        }
    }

    /**
     * Gets max width of a line of text.  Used for wrapping the text.
     *
     * @return The maximum line width in pixels `int` value.
     * @see .setMaxWidth
     */
    fun getMaxWidth(): Int {
        return maxWidth
    }

    /**
     * Sets height of a line of text.  Is the text leading value for multi-line text.
     * <br></br>
     * Does not support data driven stylers. Mapbox bases this off of ratio between line height and text size, so using the same ratio is needed.
     *
     * @param lineHeight text leading value for multi-line text in pixels. Cannot be less then 0.
     * @see .getLineHeight
     */
    fun setLineHeight(lineHeight: Int) {
        if (this.lineHeight != lineHeight) {
            this.lineHeight = lineHeight
            dirty = true
        }
    }

    /**
     * Sets height of a line of text.  Is the text leading value for multi-line text.
     * <br></br>
     * Does not support data driven stylers. Mapbox bases this off of ratio between line height and text size, so using the same ratio is needed.
     *
     * @param textLineHeight text leading value for multi-line text in pixels. Cannot be less then 0.
     * @see .getLineHeight
     */
    @Deprecated("Use {@link #setLineHeight(int)} instead")
    fun setTextLineHeight(textLineHeight: Int) {
        if (lineHeight != textLineHeight) {
            lineHeight = textLineHeight
            dirty = true
        }
    }

    /**
     * Gets height of a line of text.  Is the text leading value for multi-line text.
     *
     * @return text leading value for multi-line text in pixels.
     * @see .setLineHeight
     */
    fun getLineHeight(): Int {
        return lineHeight
    }

    /**
     * Gets height of a line of text.  Is the text leading value for multi-line text.
     *
     * @return text leading value for multi-line text in pixels.
     * @see .setLineHeight
     */
    @Deprecated("Use {@link #getLineHeight()} instead")
    fun getTextLineHeight(): Int {
        return lineHeight
    }

    /**
     * Sets the text's justification option.
     *
     * @param justification text justification option.
     * @see .getJustification
     */
    fun setJustification(justification: TextJustify) {
        if (this.justification != justification) {
            this.justification = justification
            dirty = true
        }
    }

    /**
     * Sets the text's justification option.
     *
     * @param textJustify text justification option.
     * @see .getJustification
     */
    @Deprecated("Use {@link #setJustification(TextJustify)} instead")
    // weird warning, we are using enum values
    fun setTextJustify(textJustify: TextJustify) {
        if (justification != textJustify) {
            justification = textJustify
            dirty = true
        }
    }

    /**
     * Gets the text's justification option for the style.
     *
     * @return text justification option [TextJustify] value.
     * @see .setJustification
     */
    fun getJustification(): TextJustify {
        return justification
    }

    /**
     * Gets the text's justification option for the style.
     *
     * @return text justification option [TextJustify] value.
     * @see .setJustification
     */
    @Deprecated("Use {@link #getJustification()} instead")
    fun getTextJustify(): TextJustify {
        return justification
    }

    /**
     * Sets the text's anchor.
     *
     * @param anchor part of the text placed closest to the anchor.
     * @see .getAnchor
     */
    fun setAnchor(anchor: TextAnchor) {
        if (this.anchor != anchor) {
            this.anchor = anchor
            dirty = true
        }
    }

    /**
     * Sets the text's anchor.
     *
     * @param textAnchor part of the text placed closest to the anchor.
     * @see .getAnchor
     */
    @Deprecated("Use {@link #setAnchor(TextAnchor)} instead")
    // weird warning, we are using enum values
    fun setTextAnchor(textAnchor: TextAnchor) {
        if (anchor != textAnchor) {
            anchor = textAnchor
            dirty = true
        }
    }

    /**
     * Gets the text's anchor.
     *
     * @return part of the text placed closest to the anchor [TextAnchor] value.
     * @see .setAnchor
     */
    fun getAnchor(): TextAnchor {
        return anchor
    }

    /**
     * Gets the text's anchor.
     *
     * @return part of the text placed closest to the anchor [TextAnchor] value.
     * @see .setAnchor
     */
    @Deprecated("Use {@link #getAnchor()} instead")
    fun getTextAnchor(): TextAnchor {
        return anchor
    }

    /**
     * Sets the text's rotation angle.
     *
     * @param rotation the text's rotation angle from 0.0 to 360.0 in degrees.
     * @see .getRotation
     */
    fun setRotation(@FloatRange(from = 0.0, to = 360.0) rotation: Float) {

        if (this.rotation != rotation) {
            this.rotation = rotation
            dirty = true
        }
    }

    /**
     * Sets the text's rotation angle.
     *
     * @param textRotation the text's rotation angle from 0.0 to 360.0 in degrees.
     * @see .getRotation
     */
    @Deprecated("Use {@link #setRotation(float)} instead")
    fun setTextRotation(@FloatRange(from = 0.0, to = 360.0) textRotation: Float) {

        if (rotation != textRotation) {
            rotation = textRotation
            dirty = true
        }
    }

    /**
     * Gets the text's rotation angle.
     *
     * @return the text's rotation angle in degrees.
     * @see .setRotation
     */
    @FloatRange(from = 0.0, to = 360.0)
    fun getRotation(): Float {
        return rotation
    }

    /**
     * Gets the text's rotation angle.
     *
     * @return the text's rotation angle in degrees.
     * @see .setRotation
     */
    @FloatRange(from = 0.0, to = 360.0)
    @Deprecated("Use {@link #getRotation()} instead")
    fun getTextRotation(): Float {
        return rotation
    }

    /**
     * Sets the text's offset.
     *
     * @param offset Offset distance of text from its anchor in pixels. Positive values indicate right and down, while negative values indicate left and
     * up.
     * @see .getOffset
     */
    fun setOffset(offset: Point) {
        if (offset != this.offset) {
            this.offset = offset
            dirty = true
        }
    }

    /**
     * Sets the text's offset.
     *
     * @param textOffset Offset distance of text from its anchor in pixels. Positive values indicate right and down, while negative values indicate left and
     * up.
     * @see .getOffset
     */
    @Deprecated("Use {@link #setOffset(Point)} instead")
    fun setTextOffset(textOffset: Point) {
        if (textOffset != offset) {
            offset = textOffset
            dirty = true
        }
    }

    /**
     * Gets  the text's offset for the style.
     *
     * @return Offset distance of text from its anchor in pixels. Positive values indicate right and down, while negative values indicate left and up.
     * @see .setOffset
     */
    fun getOffset(): Point {
        return offset
    }

    /**
     * Gets  the text's offset for the style.
     *
     * @return Offset distance of text from its anchor in pixels. Positive values indicate right and down, while negative values indicate left and up.
     * @see .setOffset
     */
    @Deprecated("Use {@link #getOffset()} instead")
    fun getTextOffset(): Point? {
        return offset
    }

    /**
     * Gets the color of the text.
     *
     * @return The color in ARGB format.
     * @see .setColor
     */
    @ColorInt
    fun getColor(): Int {
        return color
    }

    /**
     * Sets the color of the text.
     *
     * @param color The color in ARGB format.
     * @see .getColor
     */
    fun setColor(@ColorInt color: Int) {
        if (this.color != color) {
            this.color = color
            dirty = true
        }
    }

    /**
     * Gets the text's opacity.
     *
     * @return The opacity from 0.0 to 1.0.
     * @see .setOpacity
     */
    @FloatRange(from = 0.0, to = 1.0)
    fun getOpacity(): Float {
        return opacity
    }

    /**
     * Gets the text's opacity.
     *
     * @return The opacity from 0.0 to 1.0.
     * @see .setOpacity
     */
    @FloatRange(from = 0.0, to = 1.0)
    @Deprecated("Use {@link #getOpacity()} instead")
    fun getTextOpacity(): Float {
        return opacity
    }

    /**
     * Sets the text's opacity.
     *
     * @param opacity The opacity from 0.0 to 1.0.  0.0 being completely transparent and 1.0 being completely opaque.
     * @see .getOpacity
     */
    fun setOpacity(@FloatRange(from = 0.0, to = 1.0) opacity: Float) {

        if (this.opacity != opacity) {
            this.opacity = opacity
            dirty = true
        }
    }

    /**
     * Sets the text's opacity.
     *
     * @param textOpacity The opacity from 0.0 to 1.0.  0.0 being completely transparent and 1.0 being completely opaque.
     * @see .getOpacity
     */
    @Deprecated("Use {@link #setOpacity(float)} instead")
    fun setTextOpacity(@FloatRange(from = 0.0, to = 1.0) textOpacity: Float) {

        if (opacity != textOpacity) {
            opacity = textOpacity
            dirty = true
        }
    }

    override fun toString(): String {
        return "TextStyler{" +
                "fonts=" + fonts +
                ", size=" + size +
                ", haloColor=" + haloColor +
                ", haloWidth=" + haloWidth +
                ", haloBlur=" + haloBlur +
                ", maxWidth=" + maxWidth +
                ", lineHeight=" + lineHeight +
                ", justification=" + justification +
                ", anchor=" + anchor +
                ", rotation=" + rotation +
                ", offset=" + offset +
                ", isDirty=" + dirty +
                '}'.toString()
    }
}