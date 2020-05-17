package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */
    private static final String TAG = "Whack-A-Mole";
    private TextView Score;
    private List<Button> ButtonList = new ArrayList<>();
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Finished Pre-Initialisation!");
        Button ButtonLeft = (Button) findViewById(R.id.ButtonLeft);
        Button ButtonRight = (Button) findViewById(R.id.ButtonRight);
        Button ButtonMiddle = (Button) findViewById(R.id.ButtonMiddle);
        ButtonList.add(ButtonMiddle);
        ButtonList.add(ButtonLeft);
        ButtonList.add(ButtonRight);
        Score = (TextView) findViewById(R.id.Score);

        ButtonLeft.setOnClickListener(this);
        ButtonMiddle.setOnClickListener(this);
        ButtonRight.setOnClickListener(this);

    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (count > 0 && count % 10 == 0) {
            nextlevelQuery();
        }
    }

    private void nextlevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        Log.v(TAG, "Advance option given to user!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Go to next level");
        builder.setMessage("Would you like to go to the next level?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User accepts!");
                nextlevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User declines!");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }

    private void nextlevel(){
        /* Launch advanced page */
        Intent activityName = new Intent(MainActivity.this, Main2Activity.class);
        activityName.putExtra("Score", count);
        startActivity(activityName);


    }

    private void setNewMole() {
        for (Button i: ButtonList){
            i.setText("O");
        }
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        Button random = ButtonList.get(randomLocation);
        random.setText("*");
    }

    @Override
    public void onClick(View v) {
        Button temp = (Button) v;
        switch (v.getId()){
            case R.id.ButtonLeft:
                Log.v(TAG,"Button Left Clicked!");
                break;
            case R.id.ButtonMiddle:
                Log.v(TAG,"Button Middle Clicked!");
                break;
            case R.id.ButtonRight:
                Log.v(TAG,"Button Right Clicked!");
                break;
        }
        if (temp.getText() == "*"){
            count = count + 1;
            temp.setText("O");
            String numberAsString = String.valueOf(count);
            Score.setText(numberAsString);
            Log.v(TAG,"Hit, score added!");
            setNewMole();
            doCheck(temp);
        }
        else {
            count = count - 1;
            String numberAsString = String.valueOf(count);
            Score.setText(numberAsString);
            Log.v(TAG,"Missed, score deducted!");
            setNewMole();
        }
    }
}