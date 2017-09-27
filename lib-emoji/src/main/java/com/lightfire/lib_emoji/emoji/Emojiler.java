package com.lightfire.lib_emoji.emoji;

import android.os.Parcel;
import android.os.Parcelable;

public class Emojiler implements Parcelable {

    public static final Creator<Emojiler> CREATOR = new Creator<Emojiler>() {
        @Override
        public Emojiler createFromParcel(Parcel in) {
            return new Emojiler(in);
        }

        @Override
        public Emojiler[] newArray(int size) {
            return new Emojiler[size];
        }
    };

    private int icon;

    private char value;

    private String emoji;

    public Emojiler(int icon, char value, String emoji) {
        this.icon = icon;
        this.value = value;
        this.emoji = emoji;
    }

    public Emojiler(Parcel in) {
        this.icon = in.readInt();
        this.value = (char) in.readInt();
        this.emoji = in.readString();
    }

    private Emojiler() {
    }

    public Emojiler(String emoji) {
        this.emoji = emoji;
    }

    public static Emojiler fromResource(int icon, int value) {
        Emojiler emoji = new Emojiler();
        emoji.icon = icon;
        emoji.value = (char) value;
        return emoji;
    }

    public static Emojiler fromCodePoint(int codePoint) {
        Emojiler emoji = new Emojiler();
        emoji.emoji = newString(codePoint);
        return emoji;
    }

    public static Emojiler fromChar(char ch) {
        Emojiler emoji = new Emojiler();
        emoji.emoji = Character.toString(ch);
        return emoji;
    }

    public static Emojiler fromChars(String chars) {
        Emojiler emoji = new Emojiler();
        emoji.emoji = chars;
        return emoji;
    }

    public static final String newString(int codePoint) {
        if (Character.charCount(codePoint) == 1) {
            return String.valueOf(codePoint);
        } else {
            return new String(Character.toChars(codePoint));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(icon);
        dest.writeInt(value);
        dest.writeString(emoji);
    }

    public char getValue() {
        return value;
    }

    public int getIcon() {
        return icon;
    }

    public String getEmoji() {
        return emoji;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Emojiler && emoji.equals(((Emojiler) o).emoji);
    }

    @Override
    public int hashCode() {
        return emoji.hashCode();
    }

}
