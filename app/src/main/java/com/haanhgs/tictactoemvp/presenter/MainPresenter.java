package com.haanhgs.tictactoemvp.presenter;

import android.content.Context;
import android.widget.Toast;
import com.haanhgs.tictactoemvp.model.Repo;
import com.haanhgs.tictactoemvp.model.Move;
import com.haanhgs.tictactoemvp.model.Player;
import com.haanhgs.tictactoemvp.view.MainView;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import static com.haanhgs.tictactoemvp.model.GameState.Draw;
import static com.haanhgs.tictactoemvp.model.GameState.Finished;

public class MainPresenter implements Presenter{

    private final MainView view;
    private final Context context;
    private Repo repo;

    public MainPresenter(Context context){
        this.context = context;
        repo = new Repo();
        if (context instanceof MainView){
            view = (MainView)context;
        }else {
            throw new ClassCastException("must implement MainView interface");
        }
    }

    private void updateTextView(){
        String string = repo.getCurrentPlayer().toString() + " to play";
        view.updateTextView(string);
    }

    public void onButtonClicked(int row, int col){
        Player player = repo.mark(row, col);
        updateTextView();
        if (player != null){
            String playerString = player.toString();
            view.updateButton(playerString, row, col);
            if (repo.getState() == Finished){
                String winner = repo.getWinner() + " wins!!!";
                view.updateTextView(winner);
            } else if (repo.getState() == Draw){
                view.updateTextView("Draw!!!");
            }
        }
    }

    public void onResetSelected(){
        repo.restart();
        updateTextView();
        view.clearButtons();
    }

    public void moveBack(){
        int currentMove = repo.getCurrentMove();
        if (currentMove >= 1){
            Move move = repo.getMoveList().get(currentMove - 1);
            view.updateButton("", move.getRow(), move.getColumn());
            repo.clearCell(move.getRow(), move.getColumn());
            repo.setCurrentMove(currentMove - 1);
            repo.flipSide();
            updateTextView();
        }
    }

    public void moveNext(){
        int currentMove = repo.getCurrentMove();
        if (currentMove < repo.getMoveList().size()){
            Move move = repo.getMoveList().get(currentMove);
            view.updateButton(move.getPlayer().toString(), move.getRow(), move.getColumn());
            repo.fillCell(move.getPlayer(), move.getRow(), move.getColumn());
            repo.setCurrentMove(currentMove + 1);
            repo.flipSide();
            updateTextView();
        }
    }

    public void moveFirst() {
        int currentMove = repo.getCurrentMove();
        if (currentMove >= 1){
            for (int i = currentMove; i > 0; i--){
                Move move = repo.getMoveList().get(i - 1);
                view.updateButton("", move.getRow(), move.getColumn());
                repo.clearCell(move.getRow(), move.getColumn());
                repo.flipSide();
            }
            repo.setCurrentMove(0);
            updateTextView();
        }
    }

    public void moveLast() {
        int currentMove = repo.getCurrentMove();
        if (currentMove < repo.getMoveList().size()){
            for (int i = currentMove; i < repo.getMoveList().size(); i++){
                Move move = repo.getMoveList().get(i);
                view.updateButton(move.getPlayer().toString(), move.getRow(), move.getColumn());
                repo.fillCell(move.getPlayer(), move.getRow(), move.getColumn());
                repo.flipSide();
            }
            repo.setCurrentMove(repo.getMoveList().size());
            updateTextView();
        }
    }

    private void loadGame()throws JSONException, IOException {
        BufferedReader reader = null;
        File file = new File(context.getFilesDir(), "save.json");
        if (file.exists()){
            try{
                InputStream in = context.openFileInput("save.json");
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) builder.append(line);
                JSONObject jsonObject = (JSONObject)new JSONTokener(builder.toString()).nextValue();
                repo.loadGame(jsonObject);
            }finally {
                if (reader != null) reader.close();
            }
        }
    }

    private void moveToCurrent() {
        updateTextView();
        for (int i = 0; i < repo.getCurrentMove(); i++){
            Move move = repo.getMoveList().get(i);
            view.updateButton(move.getPlayer().toString(), move.getRow(), move.getColumn());
            repo.fillCell(move.getPlayer(), move.getRow(), move.getColumn());
        }
    }

    private void loadSaveGame(){
        try{
            loadGame();
            moveToCurrent();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Load error", Toast.LENGTH_LONG).show();
            repo.restart();
        }
    }

    @Override
    public void onCreate() {
        repo = new Repo();
        updateTextView();
        view.clearButtons();
        loadSaveGame();
    }

    @Override
    public void onPause() {}

    @Override
    public void onResume() {}

    private void saveGame()throws JSONException, IOException {
        JSONObject jsonObject = repo.saveGame();
        Writer writer = null;
        try{
            OutputStream out = context.openFileOutput("save.json", Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jsonObject.toString());
        }finally {
            if (writer != null) writer.close();
        }
    }

    @Override
    public void onDestroy() {
        try{
            saveGame();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Save error", Toast.LENGTH_LONG).show();
        }
    }
}
