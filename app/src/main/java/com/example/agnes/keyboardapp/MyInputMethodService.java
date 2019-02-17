package com.example.agnes.keyboardapp;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputConnection;


public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

//    private HashMap<Integer, String> mapping = new HashMap<Integer, String>() {{
//        put(0, "if :");
//        put(1, "if :\nelse :");
//        put(2, "for :");
//        put(3, "while :");
//        put(4, "try:\n\nexcept Exception:");
//        put(5, "def :");
//        put(6, "class :");
//        put(7, "print()");
//        put(8, "# ");
//        put(9, "\"\"\"\n\n\"\"\"");
//    }};

    int MAX_CHARACTERS = 0;
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
                case Keyboard.KEYCODE_DELETE :
                    CharSequence selectedText = inputConnection.getSelectedText(0);

                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                        MAX_CHARACTERS -= 1;
                    }

                    break;
                case 0:
                    inputConnection.commitText("if :", 1);
                    break;
                case 1:
                    inputConnection.commitText("else :", 1);
                    break;
                case 2:
                    inputConnection.commitText("for :", 1);
                    break;
                case 3:
                    inputConnection.commitText("while :", 1);
                    break;
                case 4:
                    inputConnection.commitText("try:\n\nexcept Exception:", 1);
                    break;
                case 5:
                    inputConnection.commitText("def :", 1);
                    break;
                case 6:
                    inputConnection.commitText("class :", 1);
                    break;
                case 7:
                    inputConnection.commitText("print()", 1);
                    break;
                case 8:
                    inputConnection.commitText("# ", 1);
                    break;
                case 9:
                    inputConnection.commitText("\"\"\"\n\n\"\"\"", 1);
                    break;
                case 49:
                    inputConnection.commitText("if():",1);
                    MAX_CHARACTERS += 5;
                    int cursorPosition = inputConnection.getTextBeforeCursor(MAX_CHARACTERS, 0).length();
                    inputConnection.setSelection(cursorPosition - 2, cursorPosition - 2);
                    break;
                default :
                    char code = (char) primaryCode;
                    inputConnection.commitText(String.valueOf(code), 1);
                    MAX_CHARACTERS += 1;
            }
        }
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