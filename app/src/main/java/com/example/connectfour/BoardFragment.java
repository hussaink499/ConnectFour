package com.example.connectfour;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class BoardFragment extends Fragment {
    private final String GAME_STATE = "gameState";
    private ConnectFourGame mGame;
    private GridLayout mGrid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_board, container, false);


        // Add the same click handler to all grid buttons
        mGrid = parentView.findViewById(R.id.board_grid);

        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button gridButton = (Button) mGrid.getChildAt(i);
            gridButton.setOnClickListener(this::onButtonClick);
        }

        // instantiating the connect four game obj
        mGame = new ConnectFourGame();

        // if new game:
        if (savedInstanceState == null)
        {
            startGame();
        }

        // if restoring the game activity
        else
        {
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setDisc();
        }

        return parentView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }

    private void startGame()
    {
        mGame.newGame();
        setDisc();
    }

    private void onButtonClick(View view) {
        // Find the button's row and col
        int buttonIndex = mGrid.indexOfChild(view);
        int row = buttonIndex / ConnectFourGame.COL;
        int col = buttonIndex % ConnectFourGame.COL;

        mGame.selectDisc(row, col);
        setDisc();

        if (mGame.isGameOver())
        {
            if (mGame.isWin())
                Toast.makeText(this.requireActivity(), "Congratulations " + mGame.getPlayer(), Toast.LENGTH_SHORT).show();

            // reset game
            mGame.newGame();
            setDisc();
        }
    }

    private void setDisc()
    {
        int childCount = mGrid.getChildCount();

        for (int buttonIndex = 0; buttonIndex < childCount; buttonIndex++)
        {
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);

            // Find button's row and column
            int row = buttonIndex / ConnectFourGame.COL;
            int col = buttonIndex % ConnectFourGame.COL;

            // create drawable objects to represent the different colored discs
            Drawable whiteDisc = ContextCompat.getDrawable(getActivity(), R.drawable.circle_white);
            Drawable redDisc = ContextCompat.getDrawable(getActivity(), R.drawable.circle_red);
            Drawable blueDisc = ContextCompat.getDrawable(getActivity(), R.drawable.circle_blue);

            // set the drawable objects to wrap
            whiteDisc = DrawableCompat.wrap(whiteDisc);
            redDisc = DrawableCompat.wrap(redDisc);
            blueDisc = DrawableCompat.wrap(blueDisc);


            // check the color of the disc then set its color accordingly
            int discColor = mGame.getDisc(row, col);

            if (discColor == ConnectFourGame.BLUE)
                gridButton.setBackground(blueDisc);
            else if (discColor == ConnectFourGame.RED)
                gridButton.setBackground(redDisc);
            else if (discColor == ConnectFourGame.EMPTY)
                gridButton.setBackground(whiteDisc);
        }
    }
}