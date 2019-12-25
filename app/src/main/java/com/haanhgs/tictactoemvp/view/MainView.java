package com.haanhgs.tictactoemvp.view;

public interface MainView {
    void updateTextView(String string);
    void clearButtons();
    void updateButton(String string, int row, int column);
}
