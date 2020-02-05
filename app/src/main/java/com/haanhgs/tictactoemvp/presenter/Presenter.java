package com.haanhgs.tictactoemvp.presenter;

@SuppressWarnings("EmptyMethod")
public interface Presenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
}
