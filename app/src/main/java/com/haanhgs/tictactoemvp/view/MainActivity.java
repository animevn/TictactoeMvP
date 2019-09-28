package com.haanhgs.tictactoemvp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.haanhgs.tictactoemvp.R;
import com.haanhgs.tictactoemvp.model.Board;
import com.haanhgs.tictactoemvp.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView{

    private Board board;
    private TextView tvWinner;
    private TextView tvGroup;
    private ConstraintLayout clBoard;
    MainPresenter presenter = new MainPresenter(this);

    private void initViews(){
        board = new Board();
        tvWinner = findViewById(R.id.tvWinner);
        tvGroup = findViewById(R.id.tvViewGroup);
        clBoard = findViewById(R.id.clBoard);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        presenter.onCreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mniReset){
            presenter.onResetSelected();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showTextViewWinner(String string) {

    }

    @Override
    public void showTextViewGroup(String string) {

    }

    @Override
    public void clearButtons() {
        for (int i = 0; i < clBoard.getChildCount(); i++){
            Button button = (Button)clBoard.getChildAt(i);
            button.setText("");
        }
    }

    @Override
    public void setButtonText(int row, int col, String string) {
        Button button = clBoard.findViewWithTag("" + row + col);
        if (button != null) button.setText(string);
    }
}
