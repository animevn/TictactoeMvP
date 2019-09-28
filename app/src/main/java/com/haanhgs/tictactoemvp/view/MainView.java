package com.haanhgs.tictactoemvp.view;

public interface MainView {

    void showTextViewWinner(String string);
    void showTextViewGroup(String string);
    void clearButtons();
    void setButtonText(int row, int col, String string);

}
