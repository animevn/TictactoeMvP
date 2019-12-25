package com.haanhgs.tictactoemvp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Move {
    private Player player;
    private int row;
    private int column;
    private GameState state;

    public Move(Player player, int row, int column, GameState state) {
        this.player = player;
        this.row = row;
        this.column = column;
        this.state = state;
    }

    public JSONObject toJson()throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("row", row);
        jsonObject.put("col", column);
        jsonObject.put("player", player.toString());
        jsonObject.put("state", state.toString());
        return jsonObject;
    }

    public Move(JSONObject jsonObject)throws JSONException{
        row = jsonObject.getInt("row");
        column = jsonObject.getInt("col");
        player = Player.valueOf(jsonObject.getString("player"));
        state = GameState.valueOf(jsonObject.getString("state"));
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
