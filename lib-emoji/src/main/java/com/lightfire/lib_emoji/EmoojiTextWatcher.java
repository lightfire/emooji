package com.lightfire.lib_emoji;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.DynamicDrawableSpan;
import android.widget.EditText;
import android.widget.TextView;

class EmoojiTextWatcher implements TextWatcher {
    private TextView textView;
    private int start = -1;
    private int count = -1;

    @SuppressWarnings("WeakerAccess")
    public EmoojiTextWatcher(TextView textView) {
        this.textView = textView;

        SpannableString s = new SpannableString(textView.getText());
        if (!TextUtils.isEmpty(s)) {
            float textSize = textView.getTextSize();

            EmojiHandler.addEmojis(textView.getContext(), s, (int) textSize,
                    DynamicDrawableSpan.ALIGN_BASELINE, (int) textSize, 0, -1);

            textView.setText(s, TextView.BufferType.EDITABLE);

            if (textView instanceof EditText) {
                EditText editText = (EditText) textView;
                editText.setSelection(s.length());
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (this.start == -2) return;
        if (this.start == -1 && count > 0) {
            // When text is added
            this.start = start;
            this.count = count;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (start >= 0) {
            float textSize = textView.getTextSize();
            int pos = start;

            start = -2;
            EmojiHandler.addEmojis(textView.getContext(), s, (int) textSize,
                    DynamicDrawableSpan.ALIGN_BASELINE, (int) textSize, pos, count);
            start = -1;
        }
    }
}
