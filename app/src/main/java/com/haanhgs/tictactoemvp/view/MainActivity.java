package com.haanhgs.tictactoemvp.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.haanhgs.tictactoemvp.R;
import com.haanhgs.tictactoemvp.model.Board;
import com.haanhgs.tictactoemvp.presenter.MainPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.clBoard)
    ConstraintLayout clBoard;
    @BindView(R.id.tvOutput)
    TextView tvOutput;
    @BindView(R.id.bnFirst)
    ImageButton bnFirst;
    @BindView(R.id.bnLast)
    ImageButton bnLast;
    @BindView(R.id.bnNext)
    ImageButton bnNext;
    @BindView(R.id.bnBack)
    ImageButton bnBack;

    private Board board;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this, this);
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
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mniReset) {
            presenter.onResetSelected();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void updateTextView(String string) {
        tvOutput.setText(string);
    }

    @Override
    public void clearButtons() {
        for (int i = 0; i < clBoard.getChildCount(); i++) {
            Button button = (Button) clBoard.getChildAt(i);
            button.setText("");
        }
    }

    @Override
    public void updateButton(String string, int row, int column) {
        Button button = clBoard.findViewWithTag("" + row + column);
        if (button != null) button.setText(string);
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String tag = button.getTag().toString();
        int row = Integer.valueOf(tag.substring(0, 1));
        int col = Integer.valueOf(tag.substring(1, 2));
        presenter.onButtonClicked(row, col);
    }

    @OnClick({R.id.bnFirst, R.id.bnLast, R.id.bnNext, R.id.bnBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnFirst:
                presenter.moveFirst();
                break;
            case R.id.bnLast:
                presenter.moveLast();
                break;
            case R.id.bnNext:
                presenter.moveNext();
                break;
            case R.id.bnBack:
                presenter.moveBack();
                break;
        }
    }
}
