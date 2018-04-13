package com.itorba.onmaptextdrawer;
//package com.weather.pangea.google.renderer.overlay;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;


/**
 * Knows how to draw a text
 *
 * @author Igor Torba
 */


class TextDrawer {
    private static final float CENTER = 0.5f;
    private final TextPaint textPaint;
    private final Paint rectPaint;

    TextDrawer() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private static int alphaColor(int color, float opacity) {
        return Color.argb(Math.round(opacity * 255), Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Draws a bitmap containing a styled text.
     *
     * @param text       The text for drawing
     * @param textStyler The style for the text.
     * @return The bitmap with the text.
     */
    Bitmap drawText(String text, TextStyler textStyler) {
        textPaint.setColor(alphaColor(textStyler.getColor(), textStyler.getOpacity()));
        textPaint.setTextAlign(getTextAlign(textStyler.getJustification()));
        textPaint.setTextSize(textStyler.getSize());

        setTextBlur(textStyler);

        int maxLineWidth = textStyler.getMaxWidth();
        float[] measuredWidth = new float[1];
        textPaint.breakText(text, true, maxLineWidth, measuredWidth);

        int widthForUsage = Math.round(measuredWidth[0]);

        StaticLayout textLayout = new StaticLayout(text, textPaint, widthForUsage, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);

        Point textDimension = new Point(textLayout.getWidth(), textLayout.getHeight());
        Log.d("IgorJava", "TextDimens = " + textDimension);
        Point offset = textStyler.getOffset();
        Point bitmapDimensions = calculateBitmapSize(textDimension, textStyler.getHaloWidth(), offset);
        Log.d("IgorJava", "BitmapDimens = " + bitmapDimensions);
        TextStyler.TextAnchor anchor = textStyler.getAnchor();
        PointF anchorAsRatio = getAnchorAsRatios(anchor);

        PointF translation = calculateTranslation(bitmapDimensions, textDimension, offset, anchorAsRatio, anchor);

        Log.d("IgorJava", "Translation = " + translation);

        Bitmap bitmap = Bitmap.createBitmap(bitmapDimensions.x, bitmapDimensions.y, Bitmap.Config.ARGB_8888);
        rectPaint.setColor(Color.RED);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0, 0, bitmapDimensions.x, bitmapDimensions.y, rectPaint);

        canvas.save();
        canvas.translate(translation.x, translation.y);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }

    private Paint.Align getTextAlign(TextStyler.TextJustify justify) {
        switch (justify) {
            case CENTER:
                return Paint.Align.CENTER;
            case LEFT:
                return Paint.Align.LEFT;
            case RIGHT:
                return Paint.Align.RIGHT;
            default:
                throw new IllegalArgumentException("Cannot identify " + justify);
        }
    }

    private void setTextBlur(TextStyler style) {
        float sWidth = style.getHaloWidth();
        int sColor = style.getHaloColor();
        textPaint.setShadowLayer(sWidth, 0, 0, sColor);
    }

    private PointF getAnchorAsRatios(TextStyler.TextAnchor anchor) {
        switch (anchor) {
            case LEFT:
                return new PointF(0.0f, CENTER);
            case RIGHT:
                return new PointF(1.0f, CENTER);
            case TOP:
                return new PointF(CENTER, 0.0f);
            case BOTTOM:
                return new PointF(CENTER, 1.0f);
            case TOP_LEFT:
                return new PointF(0.0f, 0.0f);
            case TOP_RIGHT:
                return new PointF(1.0f, 0.0f);
            case BOTTOM_LEFT:
                return new PointF(0.0f, 1.0f);
            case BOTTOM_RIGHT:
                return new PointF(1.0f, 1.0f);
            default:
                return new PointF(CENTER, CENTER);
        }
    }

    private Point calculateBitmapSize(Point textDimension, float haloBlurWidth, Point offset) {
        // Make bitmap a little large for reason to support anchoring
        float bitmapWidth = textDimension.x + haloBlurWidth + Math.abs(offset.x);
        float bitmapHeight = textDimension.y + haloBlurWidth + Math.abs(offset.y);
        return new Point(Math.round(bitmapWidth), Math.round(bitmapHeight));
    }

    private PointF calculateTranslation(Point bitmapDimension, Point textDimension, Point offset, PointF anchorRatio, TextStyler.TextAnchor anchor) {
        float textAnchorX = textDimension.x * anchorRatio.x;
        float textAnchorY = textDimension.y * anchorRatio.y;

        float bitmapAnchorX = bitmapDimension.x * anchorRatio.x;
        float bitmapAnchorY = bitmapDimension.y * anchorRatio.y;

        float translationX = bitmapAnchorX - textAnchorX;
        float translationY = bitmapAnchorY - textAnchorY;
        switch (anchor) {
            case TOP_LEFT:
            case TOP:
            case LEFT:
            case CENTER:
                translationX += offset.x;
                translationY += offset.y;
                break;

            case TOP_RIGHT:
            case RIGHT:
                translationX -= offset.x;
                translationY += offset.y;
                break;

            case BOTTOM_LEFT:
            case BOTTOM:
                translationX += offset.x;
                translationY -= offset.y;
                break;

            case BOTTOM_RIGHT:
                translationX -= offset.x;
                translationY -= offset.y;
                break;
        }
        return new PointF(translationX, translationY);
    }

}
