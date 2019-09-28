package com.haanhgs.tictactoemvp.presenter;

import com.haanhgs.tictactoemvp.model.Board;
import com.haanhgs.tictactoemvp.model.Player;
import com.haanhgs.tictactoemvp.view.MainView;

public class MainPresenter implements Presenter{

    private MainView view;
    private Board board;

    public MainPresenter(MainView view){
        this.view = view;
        board = new Board();
    }

    @Override
    public void onCreate() {
        board = new Board();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void onButtonClicked(int row, int col){
        Player player = board.mark(row, col);
        if (player != null){
            view.setButtonText(row, col, player.toString());
        }
    }

    public void onResetSelected(){
        view.clearButtons();
        board.restart();
    }
}
