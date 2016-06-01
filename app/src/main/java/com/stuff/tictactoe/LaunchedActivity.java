package com.stuff.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchedActivity extends AppCompatActivity
{
    //Number of turns that have passed
    int turn;

    //Determines who's turn it is
    PlayersTurn player;

    //These are the buttons that define the game

    //Places a player can put their mark
    Button[] gameBoard;

    //Icon showing whos turn it is
    Button playerName;

    //Button which resets the game back to square 1
    Button resetGame;

    //Shows the winner information (if there is any)
    Button winnerInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launched);

        //Creates an array of buttons which are the sections of the grid
        createGameBoard();

        //Grabs the Button info from the activity_launched.xml
        playerName = (Button) findViewById(R.id.button_player_name);
        resetGame = (Button) findViewById(R.id.button_restart);
        winnerInfoButton = (Button)findViewById(R.id.button_winner_info);

        //Start off with it being Player 1's turn and the turn number as 0
        player = PlayersTurn.PLAYER_ONE;
        turn = 0;
    }

    //Called whenever a section of the grid is selected
    public void onClickSetMark(View view)
    {
        //This is the index in the Button array for which Button was marked
        int buttonIndex = getClickedPosition(view.getId());

        //Depending on who's turn it is, Mark the grid accordingly
        switch(player)
        {
            //Player 1 gets a red X
            case PLAYER_ONE:
                gameBoard[buttonIndex].setTextColor(Color.RED);
                gameBoard[buttonIndex].setText("X");
                break;

            //Player 2 gets a blue O
            case PLAYER_TWO:
                gameBoard[buttonIndex].setTextColor(Color.BLUE);
                gameBoard[buttonIndex].setText("O");
                break;

            //This should not happen
            default:

                break;
        }

        //Set that section of the grid unclickable so the other player can't cheat
        gameBoard[buttonIndex].setClickable(false);

        //Increment the turn counter
        turn = turn + 1;

        //Check if the game was won
        if(checkWinConditions(buttonIndex))
        {
            //Show the reset game button
            resetGame.setVisibility(View.VISIBLE);

            //Make all other buttons unclickable
            for(int i = 0; i < gameBoard.length; i++)
            {
                gameBoard[i].setClickable(false);
            }

            //Show player winner information on button of screen
            if(player == PlayersTurn.PLAYER_ONE)
            {
                winnerInfoButton.setText("PLAYER 1 WINS!");
                winnerInfoButton.setBackgroundColor(Color.RED);
            }
            else
            {
                winnerInfoButton.setText("PLAYER 2 WINS!");
                winnerInfoButton.setBackgroundColor(Color.BLUE);
            }
        }

        //If the grid is filled with marks
        else if(turn > 8)
        {
            //Show the reset option
            resetGame.setVisibility(View.VISIBLE);

            //State that the game has been tied
            winnerInfoButton.setText("TIED");
        }

        //Otherwise switch the players' turn
        else
        {
            switchPlayers();
        }
    }

    //This switches from either 1->2 or 2->1
    public void switchPlayers()
    {
        //Change the current player and change the playerName button so it's clear who's turn it is
        if(player == PlayersTurn.PLAYER_ONE)
        {
            player = PlayersTurn.PLAYER_TWO;
            playerName.setBackgroundColor(Color.BLUE);
            playerName.setText("Player 2");
        }
        else
        {
            player = PlayersTurn.PLAYER_ONE;
            playerName.setBackgroundColor(Color.RED);
            playerName.setText("Player 1");
        }
    }

    //This determines if the game is over
    //index is the position in the Button array that was most recently marked
    public boolean checkWinConditions(int index)
    {
        boolean gameOver = false;

        //Top left Corner was marked last
        if(index == 0)
        {
            //Check top row, left col, TL to BR diag
            if(checkTopRow() || checkLeftCol() || checkTLBRDiag())
                gameOver = true;
        }
        //Top Center was marked last
        else if(index == 1)
        {
            //Check top row, center col
            if(checkTopRow() || checkCenCol())
                gameOver = true;
        }
        //Top Right Corner was marked last
        else if(index == 2)
        {
            //Check top row, right col, BL to TR diag
            if(checkTopRow() || checkRightCol() || checkBLTRDiag())
                gameOver = true;
        }
        //Center Left was marked last
        else if(index == 3)
        {
            //Check center row, left col
            if(checkCenRow() || checkLeftCol())
                gameOver = true;
        }
        //Center was marked last
        else if(index == 4)
        {
            //Check center row, center col, and both diags
            if(checkCenRow() || checkCenCol() || checkBLTRDiag() || checkTLBRDiag())
                gameOver = true;
        }
        //Center Right was marked last
        else if(index == 5)
        {
            //check center row, right col
            if(checkCenRow() || checkRightCol())
                gameOver = true;
        }
        //Bottom Left was marked last
        else if(index == 6)
        {
            //check bot row, left col, and BL to TR diag
            if(checkBotRow() || checkLeftCol() || checkBLTRDiag())
                gameOver = true;
        }
        //Bottom Center was marked last
        else if(index == 7)
        {
            //Check bot row, center col
            if(checkBotRow() || checkCenCol())
                gameOver = true;
        }
        //Bottom Right was marked last
        else if(index == 8)
        {
            //Check bot row, right col, and TL to BR diag
            if(checkBotRow() || checkRightCol() || checkTLBRDiag())
                gameOver = true;
        }

        return gameOver;
    }

    //Creates an array of Buttons for easy indexing of the grid
    public void createGameBoard()
    {
        gameBoard = new Button[9];
        gameBoard[0] = (Button)findViewById(R.id.button_TL);
        gameBoard[1] = (Button)findViewById(R.id.button_TC);
        gameBoard[2] = (Button)findViewById(R.id.button_TR);
        gameBoard[3] = (Button)findViewById(R.id.button_CL);
        gameBoard[4] = (Button)findViewById(R.id.button_CC);
        gameBoard[5] = (Button)findViewById(R.id.button_CR);
        gameBoard[6] = (Button)findViewById(R.id.button_BL);
        gameBoard[7] = (Button)findViewById(R.id.button_BC);
        gameBoard[8] = (Button)findViewById(R.id.button_BR);
    }

    //Starts the game back at square 1
    public void resetGame(View view)
    {
        //Clears the grid and makes all buttons clickable again
        for(int i = 0; i < gameBoard.length; i++)
        {
            gameBoard[i].setText("");
            gameBoard[i].setClickable(true);
        }

        //Player 1 always starts first
        player = PlayersTurn.PLAYER_ONE;
        playerName.setBackgroundColor(Color.RED);
        playerName.setText("Player 1");

        //It's back to turn 0
        turn = 0;

        //Make the reset button dissappear
        resetGame.setVisibility(View.GONE);

        //State that the game is still being played and no winner has occurred...yet
        winnerInfoButton.setText("STILL PLAYING");
        winnerInfoButton.setBackgroundColor(Color.CYAN);
    }

    //Gets the index of the Button array that was selected recently
    //id is the id of the Button that was recently selected
    public int getClickedPosition(int id)
    {
        int index = -1;

        //Find the id that matches a button in the array of Buttons
        for(int i = 0; i < gameBoard.length; i++)
        {
            if(gameBoard[i].getId() == id)
            {
                //Output that index
                index = i;
            }
        }

        return index;
    }

    //Checks the top row for a winner
    public boolean checkTopRow()
    {
        boolean gameOver = true;

        for(int i = 0; i < 3; i++)
        {
            switch(player)
            {
                case PLAYER_ONE:
                    if(gameBoard[i].getText() != "X")
                        gameOver = false;
                    break;
                case PLAYER_TWO:
                    if(gameBoard[i].getText() != "O")
                        gameOver = false;
                    break;
                default:

                    break;
            }
        }
        return gameOver;
    }

    //Checks the center row for a winner
    public boolean checkCenRow()
    {
        boolean gameOver = true;

        for(int i = 3; i < 6; i++)
        {
            switch(player)
            {
                case PLAYER_ONE:
                    if(gameBoard[i].getText() != "X")
                        gameOver = false;
                    break;
                case PLAYER_TWO:
                    if(gameBoard[i].getText() != "O")
                        gameOver = false;
                    break;
                default:

                    break;
            }
        }

        return gameOver;
    }

    //Check the bottom row for a winner
    public boolean checkBotRow()
    {
        boolean gameOver = true;

        for(int i = 6; i < 9; i++)
        {
            switch(player)
            {
                case PLAYER_ONE:
                    if(gameBoard[i].getText() != "X")
                        gameOver = false;
                    break;
                case PLAYER_TWO:
                    if(gameBoard[i].getText() != "O")
                        gameOver = false;
                    break;
                default:

                    break;
            }
        }

        return gameOver;
    }

    //Check the left col for a winner
    public boolean checkLeftCol()
    {
        boolean gameOver = true;

        for(int i = 0; i < 9; i = i + 3)
        {
            switch(player)
            {
                case PLAYER_ONE:
                    if(gameBoard[i].getText() != "X")
                        gameOver = false;
                    break;
                case PLAYER_TWO:
                    if(gameBoard[i].getText() != "O")
                        gameOver = false;
                    break;
                default:

                    break;
            }
        }
        return gameOver;
    }

    //Check the center col for a winner
    public boolean checkCenCol()
    {
        boolean gameOver = true;

        for(int i = 1; i < 9; i = i + 3)
        {
            switch(player)
            {
                case PLAYER_ONE:
                    if(gameBoard[i].getText() != "X")
                        gameOver = false;
                    break;
                case PLAYER_TWO:
                    if(gameBoard[i].getText() != "O")
                        gameOver = false;
                    break;
                default:

                    break;
            }
        }

        return gameOver;
    }

    //Check the right col for a winner
    public boolean checkRightCol()
    {
        boolean gameOver = true;

        for(int i = 2; i < 9; i = i + 3)
        {
            switch(player)
            {
                case PLAYER_ONE:
                    if(gameBoard[i].getText() != "X")
                        gameOver = false;
                    break;
                case PLAYER_TWO:
                    if(gameBoard[i].getText() != "O")
                        gameOver = false;
                    break;
                default:

                    break;
            }
        }

        return gameOver;
    }

    //Check the TL to BR diag for a winner
    public boolean checkTLBRDiag()
    {
        boolean gameOver = true;

        switch(player)
        {
            case PLAYER_ONE:
                if(gameBoard[0].getText() != "X" || gameBoard[4].getText() != "X" || gameBoard[8].getText() != "X")
                    gameOver = false;
                break;
            case PLAYER_TWO:
                if(gameBoard[0].getText() != "O" || gameBoard[4].getText() != "O" || gameBoard[8].getText() != "O")
                    gameOver = false;
                break;
            default:

                break;
        }
        return gameOver;
    }

    //Check the BL to TR diag for a winner
    public boolean checkBLTRDiag()
    {
        boolean gameOver = true;

        switch(player)
        {
            case PLAYER_ONE:
                if(gameBoard[2].getText() != "X" || gameBoard[4].getText() != "X" || gameBoard[6].getText() != "X")
                    gameOver = false;
                break;
            case PLAYER_TWO:
                if(gameBoard[2].getText() != "O" || gameBoard[4].getText() != "O" || gameBoard[6].getText() != "O")
                    gameOver = false;
                break;
            default:

                break;
        }

        return gameOver;
    }
}
