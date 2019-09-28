package com.haanhgs.tictactoemvp.presenter;

import com.haanhgs.tictactoemvp.model.Board;
import com.haanhgs.tictactoemvp.model.Player;
import com.haanhgs.tictactoemvp.view.MainView;
import static com.haanhgs.tictactoemvp.model.GameState.Draw;

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
        view.setupTextViews(board.getCurrentTurn().toString());
        view.clearButtons();

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
        view.updateTextViews(board.getCurrentTurn().toString());
        if (player != null){
            view.setButtonText(row, col, player.toString());
            if (board.getWinner() != null){
                view.updateTextViewsWhenWin(player.toString());
            }
            if (board.getState() == Draw){
                view.updateTextViewsWhenDraw(board.getState().toString());
            }
        }
    }

    public void onResetSelected(){
        board.restart();
        view.setupTextViews(board.getCurrentTurn().toString());
        view.clearButtons();

    }
}
