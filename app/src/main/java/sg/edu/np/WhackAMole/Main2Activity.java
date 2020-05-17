package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
    private static final String TAG = "Whack-A-Mole";
    private TextView Score;
    private List<Button> ButtonList = new ArrayList<>();
    int count = 0;
    String debug = "debug";
    CountDownTimer myCountDown;
    private long timeleft = 10000;




    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        myCountDown = new CountDownTimer(timeleft, 1000){
            public void onTick(long l){
                timeleft = l;
                Toast.makeText(getApplicationContext(),"Get Ready In "+String.valueOf(timeleft/1000)+" seconds",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Ready CountDown!" + timeleft/ 1000);

            }

            public void onFinish(){
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(),"GO!",Toast.LENGTH_SHORT).show();
                placeMoleTimer();
            }
        };
        myCountDown.start();

    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        myCountDown=new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long l) {
                Long RemainingTime=l/1000;
                Log.v(TAG, "New Mole Location!");
                setNewMole();

            }

            @Override
            public void onFinish() {
                myCountDown.start();
            }
        };
        myCountDown.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent getscore = getIntent();
        count = getscore.getIntExtra("Score", 0);
        Log.v(TAG, "Current User Score: " + String.valueOf(count));
        Log.v(TAG, "Finished Pre-Initialisation!");
        Button ButtonLeft = (Button) findViewById(R.id.ButtonLeft);
        Button ButtonRight = (Button) findViewById(R.id.ButtonRight);
        Button ButtonMiddle = (Button) findViewById(R.id.ButtonMiddle);
        Button ButtonMiddleLeft = (Button) findViewById(R.id.ButtonLeft);
        Button ButtonMiddleMiddle = (Button) findViewById(R.id.ButtonMiddleMiddle);
        Button ButtonMiddleRight = (Button) findViewById(R.id.ButtonMiddleRight);
        Button ButtonBottomLeft = (Button) findViewById(R.id.ButtonBottomLeft);
        Button ButtonBottomMiddle = (Button) findViewById(R.id.ButtonBottomMiddle);
        Button ButtonBottomRight = (Button) findViewById(R.id.ButtonButtomRight);
        ButtonList.add(ButtonMiddle);
        ButtonList.add(ButtonLeft);
        ButtonList.add(ButtonRight);
        ButtonList.add(ButtonMiddleLeft);
        ButtonList.add(ButtonMiddleMiddle);
        ButtonList.add(ButtonMiddleRight);
        ButtonList.add(ButtonBottomLeft);
        ButtonList.add(ButtonBottomMiddle);
        ButtonList.add(ButtonBottomRight);

        Score = (TextView) findViewById(R.id.Score);
        String numberAsString = String.valueOf(count);
        Score.setText(numberAsString);

        ButtonLeft.setOnClickListener(this);
        ButtonMiddle.setOnClickListener(this);
        ButtonRight.setOnClickListener(this);
        ButtonMiddleLeft.setOnClickListener(this);
        ButtonMiddleMiddle.setOnClickListener(this);
        ButtonMiddleRight.setOnClickListener(this);
        ButtonBottomLeft.setOnClickListener(this);
        ButtonBottomMiddle.setOnClickListener(this);
        ButtonBottomRight.setOnClickListener(this);

        readyTimer();

    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        for (Button i: ButtonList){
            i.setText("O");
        }
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        Button random = ButtonList.get(randomLocation);
        random.setText("*");
    }

    @Override
    public void onClick(View v) {
        Button temp = (Button) v;
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

