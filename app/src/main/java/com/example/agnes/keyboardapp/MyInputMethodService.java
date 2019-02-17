package com.example.agnes.keyboardapp;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.HashMap;


public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    // Tracks total number of characters
    private int MAX_CHARACTERS = 0;

    private final HashMap<Integer, Key> mapping = new HashMap<Integer, Key>() {{
        put(0, new Key("if :", 1));
        put(1, new Key("else:\n    ", 0));
        put(2, new Key("for :", 1));
        put(3, new Key("while :", 1));
        put(4, new Key("try:\n    \nexcept Exception:", 18));
        put(5, new Key("def ():", 3));
        put(6, new Key("class :", 1));
        put(7, new Key("print()", 1));
        put(8, new Key("# ", 0));
        put(9, new Key("\"\"\"\n\n\"\"\"", 4));
    }};

    @Override
    public View onCreateInputView() {
        KeyboardView keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        Keyboard keyboard = new Keyboard(this, R.xml.number_pad);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();

        if (inputConnection != null) {
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    CharSequence selectedText = inputConnection.getSelectedText(0);

                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                        MAX_CHARACTERS -= 1;
                    }
                    break;
                default:
                    if (primaryCode >= 0 && primaryCode < mapping.size()) {
                        Key key = mapping.get(primaryCode);
                        inputConnection.commitText(key.string, 1);
                        MAX_CHARACTERS += key.string.length();
                        moveCursor(inputConnection, key.offset);
                    } else {
                        char code = (char) primaryCode;
                        inputConnection.commitText(String.valueOf(code), 1);
                        MAX_CHARACTERS += 1;
                    }
            }
        }
    }

    private void moveCursor(InputConnection inputConnection, int amount) {
        int cursorPosition = inputConnection.getTextBeforeCursor(MAX_CHARACTERS, 0).length();
        int position = cursorPosition - amount;
        inputConnection.setSelection(position, position);
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}