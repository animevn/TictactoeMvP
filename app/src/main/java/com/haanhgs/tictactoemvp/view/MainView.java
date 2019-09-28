package com.haanhgs.tictactoemvp.view;

public interface MainView {

    void setupTextViews(String currentPlayer);
    void updateTextViews(String nextPlayer);
    void updateTextViewsWhenDraw(String state);
    void updateTextViewsWhenWin(String currentPlayer);
    void clearButtons();
    void setButtonText(int row, int col, String playerMark);
}
