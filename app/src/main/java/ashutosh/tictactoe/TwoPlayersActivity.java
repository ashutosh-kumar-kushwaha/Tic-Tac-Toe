package ashutosh.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoPlayersActivity extends Activity {

    ArrayList<ArrayList<Integer>> X = new ArrayList<>();
    ArrayList<ArrayList<Integer>> O = new ArrayList<>();
    String nextTurn = "X";
    int[][][] winCombinations = {{{1, 1}, {2, 1}, {3, 1}}, {{1, 2}, {2, 2}, {3, 2}}, {{1, 3}, {2, 3}, {3, 3}}, {{1, 1}, {1, 2}, {1, 3}}, {{2, 1}, {2, 2}, {2, 3}}, {{3, 1}, {3, 2}, {3, 3}}, {{1, 1}, {2, 2}, {3, 3}}, {{1, 3}, {2, 2}, {3, 1}}};
    ArrayList<ArrayList<ArrayList<Integer>>> winningCombinations = new ArrayList<>();
    int[] imageViews = {R.id.imageView11, R.id.imageView21, R.id.imageView31, R.id.imageView12, R.id.imageView22, R.id.imageView32, R.id.imageView13, R.id.imageView23, R.id.imageView33};



    public void click(View view){
        ImageView imageView = (ImageView) view;
        String id = view.getResources().getResourceName(view.getId());
        int lengthOfString = id.length();
        int row = Integer.parseInt(String.valueOf(id.charAt(lengthOfString - 2)));
        int col = Integer.parseInt(String.valueOf(id.charAt(lengthOfString - 1)));
        List<Integer> arr = Arrays.asList(row, col);
        TextView player = (TextView) findViewById(R.id.player);

        if (!X.contains(arr) && !O.contains(arr)){
            if (nextTurn.equals("X")){
                imageView.setScaleX(0);
                imageView.setScaleY(0);
                imageView.setImageResource(R.drawable.cross);
                imageView.animate().scaleX(1).scaleY(1);
                X.add(new ArrayList<>(arr));
                nextTurn = "O";
                hasWon(X, "Player 1");
                player.setText("Player 2 :");
            }
            else{
                imageView.setScaleX(0);
                imageView.setScaleY(0);
                imageView.setImageResource(R.drawable.circle);
                imageView.animate().scaleX(1).scaleY(1);
                O.add(new ArrayList<>(arr));
                nextTurn = "X";
                hasWon(O, "Player 2");
                player.setText("Player 1 :");
            }
        }
    }

    public void reset(View view){
        for (int imgVw : imageViews){
            ((ImageView) findViewById(imgVw)).setImageResource(0);
            ((ImageView) findViewById(imgVw)).setAlpha(1.0f);
        }

        nextTurn = "X";
        X = new ArrayList<>();
        O = new ArrayList<>();
        TextView player = (TextView) findViewById(R.id.player);
        player.setText("Player 1 :");
        TextView win = (TextView) findViewById(R.id.win);
        win.setText("");
        win.setPaintFlags(View.INVISIBLE);
    }

    public void hasWon(ArrayList<ArrayList<Integer>> moves, String str){
        for(ArrayList<ArrayList<Integer>> winningCombination : winningCombinations){
            if (moves.contains(winningCombination.get(0)) && moves.contains(winningCombination.get(1)) && moves.contains(winningCombination.get(2))) {
                TextView player = (TextView) findViewById(R.id.player);
                player.setText("");
                TextView win = (TextView) findViewById(R.id.win);
                win.setText(str + " won!");
                win.setPaintFlags(View.VISIBLE);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players);

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
    }
}