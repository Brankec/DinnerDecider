package branimir.kobescak.com.dinnerdecider;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Globals globalDB = new Globals();

    DatabaseHelper myDB;
    ImageButton addButton, next, previous, call;
    TextView food, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (ImageButton) findViewById(R.id.addBtn);
        next = (ImageButton) findViewById(R.id.nextRightBtn);
        previous = (ImageButton) findViewById(R.id.nextLeftBtn);
        call = (ImageButton) findViewById(R.id.callBtn);

        food = (TextView) findViewById(R.id.foodNameTxt);
        price = (TextView) findViewById(R.id.foodPriceTxt);

        myDB = new DatabaseHelper(this);

        globalDB.ID = myDB.getIDList();

        globalDB.DBsize = globalDB.ID.length;

        //moves to the next layout where we add/delete/edit data
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addContent.class);
                startActivity(intent);
            }
        });

        //Checks if there is any data inside the database before attempting to display anything
        if (myDB.getDatalist().getCount() >= 1) {
            currentData(globalDB.IDTemp);
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDB.getDatalist().getCount() >= 1) {
                    dialContactPhone(globalDB.IDTemp);
                }
                //Toast.makeText(MainActivity.this, myDB.getDataByID(globalDB.ID[globalDB.IDTemp])[4].toString().length() + "", Toast.LENGTH_LONG).show();
            }
        });

        //Moving to next row
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myDB.getDatalist().getCount() >= 1) {

                    globalDB.ID = myDB.getIDList();
                    if (globalDB.IDTemp < myDB.getIDList().length - 1) {
                        globalDB.IDTemp++;
                    }

                    if (myDB.getDatalist().getCount() >= 1) {
                        currentData(globalDB.IDTemp);
                    } else {
                        defaultData();
                    }

                } else {
                    defaultData();
                }
            }
        });

        //Moving to previous row
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myDB.getDatalist().getCount() >= 1) {

                    globalDB.ID = myDB.getIDList();
                    if (globalDB.IDTemp > 0) {
                        globalDB.IDTemp--;
                    }

                    if (myDB.getDatalist().getCount() >= 1) {
                        currentData(globalDB.IDTemp);
                    } else {
                        defaultData();
                    }

                } else {
                    defaultData();
                }
            }
        });
    }

    //Display current data
    public void currentData(int id) {
        food.setText(myDB.getDataByID(globalDB.ID[id])[1]);
        price.setText("Price: " + myDB.getDataByID(globalDB.ID[id])[2] + "kn");
    }

    //Display the default TextView
    public void defaultData() {
        food.setText("FOOD_NAME");
        price.setText("Price:____");
    }

    private void dialContactPhone(int id) {
        //Toast.makeText(MainActivity.this, myDB.getDataByID(globalDB.ID[id])[4].length(), Toast.LENGTH_LONG).show();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", myDB.getDataByID(globalDB.ID[id])[4], null)));

    }
}
