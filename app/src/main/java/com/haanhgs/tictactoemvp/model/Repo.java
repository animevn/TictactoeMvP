package com.haanhgs.tictactoemvp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.haanhgs.tictactoemvp.model.GameState.Draw;
import static com.haanhgs.tictactoemvp.model.GameState.Finished;
import static com.haanhgs.tictactoemvp.model.GameState.InProgress;
import static com.haanhgs.tictactoemvp.model.Player.O;
import static com.haanhgs.tictactoemvp.model.Player.X;

public class Repo {

    private Cell[][]cells = new Cell[3][3];
    private Player winner;
    private GameState state;
    private Player currentPlayer;
    private List<Move> moveList;
    private int currentMove;

    private void clearCells(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                cells[i][j] = new Cell();
            }
        }
    }

    public void restart(){
        clearCells();
        winner = null;
        currentPlayer = X;
        state = InProgress;
        moveList = new ArrayList<>();
        currentMove = 0;

    }

    public Repo(){
        restart();
    }

    ///////Save and load Save - parse Json and save, when load do exactly opposite
    //////Can use serialization to save, but json is good if do stuff like client - server
    //////Can even use Room to apply sqlite - for local storage.

    public JSONObject saveGame()throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", state.toString());
        if (winner == null){
            jsonObject.put("winner", "null");
        }else {
            jsonObject.put("winner", winner.toString());
        }
        jsonObject.put("currentPlayer", currentPlayer.toString());
        jsonObject.put("currentMove", currentMove);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < moveList.size(); i++){
            jsonArray.put(moveList.get(i).toJson());
        }
        jsonObject.put("movelist", jsonArray);
        return jsonObject;
    }

    public void loadGame(JSONObject jsonObject)throws JSONException{
        state = GameState.valueOf(jsonObject.getString("state"));
        String winnerString = jsonObject.getString("winner");
        if (winnerString.equals("null")){
            winner = null;
        }else {
            winner = Player.valueOf(winnerString);
        }
        currentPlayer = Player.valueOf(jsonObject.getString("currentPlayer"));
        currentMove = jsonObject.getInt("currentMove");
        JSONArray jsonArray = jsonObject.getJSONArray("movelist");
        moveList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            moveList.add(new Move(jsonArray.getJSONObject(i)));
        }
    }


    //Game algorithm
    private boolean isOutOfBounds(int idx){
        return idx < 0 || idx > 2;
    }

    private boolean isCellValueAlreadySet(int row, int col){
        return cells[row][col].getValue() != null;
    }

    private boolean isValid(int row, int col){
        if (state == Finished){
            return false;
        }else if (isOutOfBounds(row) || isOutOfBounds(col)){
            return false;
        }else return !isCellValueAlreadySet(row, col);
    }

    private boolean isWinningMoveByPlayer(Player player, int currentRow, int currentCol){
        return (        cells[currentRow][0].getValue() == player
                        && cells[currentRow][1].getValue() == player
                        && cells[currentRow][2].getValue() == player
                        ||
                        cells[0][currentCol].getValue() == player
                                && cells[1][currentCol].getValue() == player
                                && cells[2][currentCol].getValue() == player
                        ||
                        cells[0][0].getValue() == player
                                && cells[1][1].getValue() == player
                                && cells[2][2].getValue() == player
                        ||
                        cells[0][2].getValue() == player
                                && cells[1][1].getValue() == player
                                && cells[2][0].getValue() == player
        );
    }

    public void flipSide(){
        currentPlayer = currentPlayer == X? O : X;
    }

    private boolean checkBoardFull(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (cells[i][j].getValue() == null){
                    return false;
                }
            }
        }
        return true;
    }

    private void prepareCell(){
        if (currentMove < moveList.size()){
            moveList = moveList.subList(0, currentMove);
        }
    }

    public Player mark(int row, int col){
        Player playerThatMoved = null;
        if (isValid(row, col)){
            prepareCell();
            cells[row][col].setValue(currentPlayer);
            moveList.add(new Move(currentPlayer, row, col, state));
            currentMove++;
            playerThatMoved = currentPlayer;
            if (isWinningMoveByPlayer(currentPlayer, row, col)){
                state = Finished;
                winner = currentPlayer;
            }else if (checkBoardFull() && state == InProgress){
                state = Draw;
            }
            flipSide();
        }
        return playerThatMoved;
    }

    public void clearCell(int row, int column){
        cells[row][column] = new Cell();
    }

    public void fillCell(Player player, int row, int column){
        cells[row][column].setValue(player);
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getState() {
        return state;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<Move> moveList) {
        this.moveList = moveList;
    }

    public int getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(int currentMove) {
        this.currentMove = currentMove;
    }
}
