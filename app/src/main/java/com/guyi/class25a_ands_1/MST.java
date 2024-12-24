package com.guyi.class25a_ands_1;

import static android.graphics.Typeface.BOLD;

import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.BulletSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class MST {

    public enum TP {
        SIZE_0_5,
        SIZE_0_6,
        SIZE_0_7,
        SIZE_0_8,
        SIZE_0_9,
        SIZE_1_2,
        SIZE_1_4,
        SIZE_1_6,
        SIZE_1_8,
        SIZE_2_0,
        BOLDED,
        BULLET,
        CENTER,
        ALIGN_OPPOSITE,
        GREEN,
        DKGREEN,
        BLACK_COLOR,
        RED_COLOR,
        BLUE_COLOR,
        GRAY_COLOR,
        LTGRAY_COLOR,
        DKGRAY_COLOR,
        WHITE_0_7_COLOR,
        IMAGE,
        IMAGE_DOWN,
        BLUR,
        SHADOW,
        SHADOW_STRONG,
        SUPERSCRIPT,
        SUBSCRIPT,
        STRIKE_THROUGH,
        UNDER_LINE,
    }

    private StringBuilder sb = new StringBuilder();
    private ArrayList<Span> data = new ArrayList<>();

    public MST() { }

    public void clear() {
        sb.setLength(0);
        data.clear();
    }

    public MST add(String s, TP... spans) {
        data.add(new Span(s, sb.length(), sb.length() + s.length(), spans));
        sb.append(s);
        return this;
    }

    public void addLine(String s, TP... spans) {
        s = "\n" + s;
        data.add(new Span(s, sb.length(), sb.length() + s.length(), spans));
        sb.append(s);
    }

    public void addImage(Resources resources, int drawableRes) {
        addImage(resources, drawableRes, 48);
    }

    public void addImage(Resources resources, int drawableRes, int size) {
        String modifiedText = "%icon%";
        Drawable drawable = ResourcesCompat.getDrawable(resources, drawableRes, null);
        if (drawable != null) {
            drawable.setBounds(0, 0, size, size);
            data.add(new Span(drawable, sb.length(), sb.length() + 6, TP.IMAGE_DOWN));
            sb.append(modifiedText);
        }
    }

    public void addImageDown(Resources resources, int drawableRes, int size) {
        String modifiedText = "%icon%";
        Drawable drawable = ResourcesCompat.getDrawable(resources, drawableRes, null);
        if (drawable != null) {
            drawable.setBounds(0, 0, size, size);
            data.add(new Span(drawable, sb.length(), sb.length() + 6, TP.IMAGE));
            sb.append(modifiedText);
        }
    }

    public Spannable getSpannableText() {
        Spannable span = new SpannableString(sb);
        //span.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        for (int i = 0; i < data.size(); i++) {
            final int BEGIN = data.get(i).begin;
            final int END = data.get(i).end;
            final int FLAGS = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

            if (data.get(i).spans != null) {
                for (int j = 0; j < data.get(i).spans.length; j++) {
                    final TP SPAN = data.get(i).spans[j];

                    if (SPAN == TP.BOLDED) {
                        span.setSpan(new StyleSpan(BOLD), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.CENTER) {
                        span.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.ALIGN_OPPOSITE) {
                        span.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_0_5) {
                        span.setSpan(new RelativeSizeSpan(0.5f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_0_6) {
                        span.setSpan(new RelativeSizeSpan(0.6f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_0_7) {
                        span.setSpan(new RelativeSizeSpan(0.7f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_0_8) {
                        span.setSpan(new RelativeSizeSpan(0.8f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_0_9) {
                        span.setSpan(new RelativeSizeSpan(0.9f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_1_2) {
                        span.setSpan(new RelativeSizeSpan(1.2f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_1_4) {
                        span.setSpan(new RelativeSizeSpan(1.4f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_1_6) {
                        span.setSpan(new RelativeSizeSpan(1.6f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_1_8) {
                        span.setSpan(new RelativeSizeSpan(1.8f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SIZE_2_0) {
                        span.setSpan(new RelativeSizeSpan(2.0f), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.BULLET) {
                        span.setSpan(new BulletSpan(12), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.GREEN) {
                        span.setSpan(new ForegroundColorSpan(Color.GREEN), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.DKGREEN) {
                        span.setSpan(new ForegroundColorSpan(Color.parseColor("#FF016004")), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.BLACK_COLOR) {
                        span.setSpan(new ForegroundColorSpan(Color.BLACK), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.RED_COLOR) {
                        span.setSpan(new ForegroundColorSpan(Color.RED), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.GRAY_COLOR) {
                        span.setSpan(new ForegroundColorSpan(Color.GRAY), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.LTGRAY_COLOR) {
                        span.setSpan(new ForegroundColorSpan(Color.LTGRAY), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.DKGRAY_COLOR) {
                        span.setSpan(new ForegroundColorSpan(Color.DKGRAY), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.BLUE_COLOR) {
                        span.setSpan(new ForegroundColorSpan(Color.BLUE), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.WHITE_0_7_COLOR) {
                        span.setSpan(new ForegroundColorSpan(Color.parseColor("#AAFFFFFF")), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SUPERSCRIPT) {
                        span.setSpan(new SuperscriptSpan(), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.SUBSCRIPT) {
                        span.setSpan(new SubscriptSpan(), BEGIN, END, FLAGS);
                    }  else if (SPAN == TP.STRIKE_THROUGH) {
                        span.setSpan(new StrikethroughSpan(), BEGIN, END, FLAGS);
                    }  else if (SPAN == TP.UNDER_LINE) {
                        span.setSpan(new UnderlineSpan(), BEGIN, END, FLAGS);
                    } else if (SPAN == TP.BLUR) {
                        span.setSpan(new MaskFilterSpan(new BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL)), BEGIN, END, FLAGS);
                    }
//                    else if (SPAN == TP.SHADOW) {
//                        span.setSpan(new ShadowSpan(Color.DKGRAY, 4.0f, 4.0f, 4), BEGIN, END, FLAGS);
//                    } else if (SPAN == TP.SHADOW_STRONG) {
//                        span.setSpan(new ShadowSpan(Color.DKGRAY, 4.0f, 4.0f, 7), BEGIN, END, FLAGS);
//                    }
                    else if (SPAN == TP.IMAGE) {
                        Drawable drawable = (Drawable) data.get(i).data;
                        ImageSpan image = new ImageSpan(drawable);
                        span.setSpan(image, BEGIN, END, FLAGS);
                    } else if (SPAN == TP.IMAGE_DOWN) {
                        Drawable drawable = (Drawable) data.get(i).data;
                        ImageSpan image = new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE);
                        span.setSpan(image, BEGIN, END, FLAGS);
                    }
                }
            }
        }

        return span;
    }

    public class Span {
        Object data;
        int begin;
        int end;
        TP[] spans;

        public Span(Object data, int begin, int end, TP... spans) {
            this.data = data;
            this.begin = begin;
            this.end = end;
            this.spans = spans;
        }
    }
}