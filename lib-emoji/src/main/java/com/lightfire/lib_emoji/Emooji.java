package com.lightfire.lib_emoji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
@SuppressWarnings("WeakerAccess")
public class Emooji {
    private static class ContextWrapper extends android.content.ContextWrapper {
        private EmoojiLayoutInflater inflater;

        public ContextWrapper(Context base) {
            super(base);
        }

        @Override
        public Object getSystemService(String name) {
            if (LAYOUT_INFLATER_SERVICE.equals(name)) {
                if (inflater == null) {
                    inflater = new EmoojiLayoutInflater(LayoutInflater.from(getBaseContext()), this);
                }
                return inflater;
            }
            return super.getSystemService(name);
        }
    }

    public static android.content.ContextWrapper wrap(Context base) {
        return new ContextWrapper(base);
    }

    public static void wrapView(View view) {
        if (view == null) return;

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (textView.getTag(R.id.tag_emojix_watcher) == null) {
                EmoojiTextWatcher watcher = new EmoojiTextWatcher(textView);
                textView.addTextChangedListener(watcher);

                textView.setTag(R.id.tag_emojix_watcher, watcher);
            }

        } else if (view instanceof ViewGroup) {
            if (view.getTag(R.id.tag_layout_listener) == null) {
                View.OnLayoutChangeListener listener = new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                               int oldLeft, int oldTop, int oldRight, int oldBottom) {

                        ViewGroup parentView = (ViewGroup) v;
                        int len = parentView.getChildCount();
                        for (int i = 0; i < len; i ++) {
                            wrapView(parentView.getChildAt(i));
                        }
                    }
                };
                view.addOnLayoutChangeListener(listener);

                view.setTag(R.id.tag_layout_listener, listener);
            }
        }
    }
}
