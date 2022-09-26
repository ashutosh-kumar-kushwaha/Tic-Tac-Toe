package ashutosh.tictactoe;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OnePlayerActivity extends Activity {
    Random random = new Random();
    ArrayList<ArrayList<Integer>> X = new ArrayList<>();
    ArrayList<ArrayList<Integer>> O = new ArrayList<>();
    String nextTurn = "X";
    int[][] remMoves = {{1,1}, {1,2}, {1,3}, {2,1}, {2,2}, {2,3}, {3,1}, {3,2}, {3,3}};
    ArrayList<ArrayList<Integer>> remainingMoves = new ArrayList<>();
    int[][][] winCombinations = {{{1, 1}, {2, 1}, {3, 1}}, {{1, 2}, {2, 2}, {3, 2}}, {{1, 3}, {2, 3}, {3, 3}}, {{1, 1}, {1, 2}, {1, 3}}, {{2, 1}, {2, 2}, {2, 3}}, {{3, 1}, {3, 2}, {3, 3}}, {{1, 1}, {2, 2}, {3, 3}}, {{1, 3}, {2, 2}, {3, 1}}};
    ArrayList<ArrayList<ArrayList<Integer>>> winningCombinations = new ArrayList<>();
    int[] imageViews = {R.id.imageView11, R.id.imageView21, R.id.imageView31, R.id.imageView12, R.id.imageView22, R.id.imageView32, R.id.imageView13, R.id.imageView23, R.id.imageView33};
    boolean gameCompleted = false;


    public void click(View view){
        ImageView imageView = (ImageView) view;
        String id = view.getResources().getResourceName(view.getId());
        int lengthOfString = id.length();
        int row = Integer.parseInt(String.valueOf(id.charAt(lengthOfString - 2)));
        int col = Integer.parseInt(String.valueOf(id.charAt(lengthOfString - 1)));
        List<Integer> arr = Arrays.asList(row, col);
        TextView player = (TextView) findViewById(R.id.player);

        if (!X.contains(arr) && !O.contains(arr)){
            imageView.setScaleX(0);
            imageView.setScaleY(0);
            imageView.setImageResource(R.drawable.cross);
            imageView.animate().scaleX(1).scaleY(1);
            ArrayList<Integer> nextMove = new ArrayList<>(arr);
            X.add(nextMove);
            remainingMoves.remove(nextMove);
            nextTurn = "O";
            player.setText("Computer :");
            hasWon(X, "You");
        }
        if(!gameCompleted && remainingMoves.size()>0){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                public void run(){
                    computerTurn();
                }
            },1000);
        }
        if (remainingMoves.size() == 0 && !gameCompleted){
            player.setText("");
            TextView win = (TextView) findViewById(R.id.win);
            win.setText("Draw!");
            win.setPaintFlags(View.VISIBLE);
            gameCompleted = true;
        }
    }

    public void computerTurn(){
        int count;
        ArrayList<Integer> nextMoveToStopOpponent = null;
        TextView player = (TextView) findViewById(R.id.player);
        ArrayList<Integer> notContains;
        for(ArrayList<ArrayList<Integer>> winningCombination : winningCombinations){
            count = 0;
            for (ArrayList<Integer> move : X){
                if (winningCombination.contains(move)){
                    count++;
                }
            }
            if (count == 2){
                for(ArrayList<Integer> arr: winningCombination){
                    if(!X.contains(arr)){
                        notContains = arr;
                        if(remainingMoves.contains(notContains)){
                            nextMoveToStopOpponent = notContains;
                            break;
                        }
                    }
                }
            }
        }

        ArrayList<Integer> nextMoveToWin = null;

        for (ArrayList<ArrayList<Integer>> winningCombination : winningCombinations) {
            count = 0;
            for (ArrayList<Integer> move : O) {
                if (winningCombination.contains(move)) {
                    count++;
                }
            }
            if (count == 2) {
                for (ArrayList<Integer> arr : winningCombination) {
                    if (!O.contains(arr)) {
                        notContains = arr;
                        if (remainingMoves.contains(notContains)) {
                            nextMoveToWin = notContains;
                            break;
                        }
                    }
                }
            }
        }



        ArrayList<Integer> nextMove;

        if(nextMoveToWin != null){
            nextMove = nextMoveToWin;
        }
        else if(nextMoveToWin == null && nextMoveToStopOpponent != null){
            nextMove = nextMoveToStopOpponent;
        }
        else{
            int i = randomInteger(0, remainingMoves.size() - 1 );
            nextMove = remainingMoves.get(i);
        }
        String contentDescription = nextMove.get(0).toString() + nextMove.get(1).toString();
        String contentDesc;
        ImageView imageView = null;

        for (int imgVw : imageViews){
            imageView = (ImageView) findViewById(imgVw);
            contentDesc = imageView.getContentDescription().toString();
            if (contentDesc.equals(contentDescription)){
                break;
            }
        }



        imageView.setScaleX(0);
        imageView.setScaleY(0);
        imageView.setImageResource(R.drawable.circle);
        imageView.animate().scaleX(1).scaleY(1);
        O.add(nextMove);
        remainingMoves.remove(nextMove);
        nextTurn = "X";
        hasWon(O, "Computer");
        player.setText("You :");

    }

    public void reset(View view){
        for (int imgVw : imageViews){
            ((ImageView) findViewById(imgVw)).setImageResource(0);
            ((ImageView) findViewById(imgVw)).setAlpha(1.0f);
        }
        gameCompleted = false;

        nextTurn = "X";
        X = new ArrayList<>();
        O = new ArrayList<>();
        TextView player = (TextView) findViewById(R.id.player);
        player.setText("You :");
        TextView win = (TextView) findViewById(R.id.win);
        win.setText("");
        win.setPaintFlags(View.INVISIBLE);

        resetRemainingMoves();
    }

    public void hasWon(ArrayList<ArrayList<Integer>> moves, String str){
        for(ArrayList<ArrayList<Integer>> winningCombination : winningCombinations){
            if (moves.contains(winningCombination.get(0)) && moves.contains(winningCombination.get(1)) && moves.contains(winningCombination.get(2))) {
                TextView player = (TextView) findViewById(R.id.player);
                player.setText("");
                TextView win = (TextView) findViewById(R.id.win);
                if (str.equals("You")) {
                    win.setText("You won!");
                }
                else{
                    win.setText("You lost!");
                }
                win.setPaintFlags(View.VISIBLE);
                gameCompleted = true;
                for(int imgVw : imageViews){
                    String contentDescription = (((ImageView) findViewById(imgVw)).getContentDescription()).toString();
                    Integer row = Integer.parseInt(String.valueOf(contentDescription.charAt(0)));
                    Integer col = Integer.parseInt(String.valueOf(contentDescription.charAt(1)));
                    ArrayList<Integer> move = new ArrayList<>(Arrays.asList(row, col));
                    if(!winningCombination.contains(move)){
                        ((ImageView) findViewById(imgVw)).setAlpha(0.25f);
                    }
                }
            }
        }
    }

    public void resetRemainingMoves(){
        ArrayList<Integer> move;
        for(int i = 0; i < remMoves.length; i++){
            move = new ArrayList<>();
            move.add(remMoves[i][0]);
            move.add(remMoves[i][1]);
            if (!remainingMoves.contains(move)){
                remainingMoves.add(move);
            }
        }
    }

    public int randomInteger(int min, int max){
        return (random.nextInt(max-min + 1) + min);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);

        ArrayList<ArrayList<Integer>> combination;
        ArrayList<Integer> move;
        for(int i = 0; i < winCombinations.length; i++){
            combination = new ArrayList<>();
            for (int j = 0; j < 3; j++){
                move = new ArrayList<>();
                move.add(winCombinations[i][j][0]);
                move.add(winCombinations[i][j][1]);
                combination.add(move);
            }
            winningCombinations.add(combination);
        }

        resetRemainingMoves();

    }
}