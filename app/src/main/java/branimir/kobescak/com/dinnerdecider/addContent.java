package branimir.kobescak.com.dinnerdecider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gejmer on 3/2/2018.
 */

public class addContent extends AppCompatActivity {
    Globals globalDB = new Globals();

    private static final String TAG = "List_at_Activity";

    DatabaseHelper myDB;
    EditText food_name, food_price, company_name, phone_number, ID;
    Button addBtn, editBtn, removeBtn, backBtn;
    ImageButton listBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontent_layout);

        food_name = (EditText) findViewById(R.id.foodnameTxt);
        food_price = (EditText) findViewById(R.id.foodpriceTxt);
        company_name = (EditText) findViewById(R.id.companynameTxt);
        phone_number = (EditText) findViewById(R.id.phonenumberTxt);
        ID = (EditText) findViewById(R.id.idTxt);

        addBtn = (Button) findViewById(R.id.adddataBtn);
        editBtn = (Button) findViewById(R.id.editdataBtn);
        removeBtn = (Button) findViewById(R.id.removedataBtn);
        listBtn = (ImageButton) findViewById(R.id.listviewBtn);

        myDB = new DatabaseHelper(this);

        //Adds data to the database
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(food_name.length()         != 0  &&
                        food_price.length()   != 0  &&
                        company_name.length() != 0  &&
                        phone_number.length() != 0 ) {

                    AddData(food_name.getText().toString(), food_price.getText().toString(), company_name.getText().toString(), phone_number.getText().toString());
                    food_name.setText("");
                    food_price.setText("");
                    company_name.setText("");
                    phone_number.setText("");
                }
                else {
                    Toast.makeText(addContent.this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
                }
                globalDB.IDTemp = 0;
                globalDB.ID = myDB.getIDList();
            }
        });

        //Opens layout for listing data
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addContent.this, ListContent.class);
                startActivity(intent);
            }
        });

        //Edits a row inside the database
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID.getText().length() != 0) {
                    String Nname, Ncompany, Nphone;
                    int Nprice;

                    if(food_name.length() == 0){
                        Nname = myDB.getDataByID(Integer.parseInt(ID.getText().toString()))[1];
                    } else {
                        Nname = food_name.getText().toString();
                    }

                    if(food_price.length() == 0){
                        Nprice = Integer.parseInt(myDB.getDataByID(Integer.parseInt(ID.getText().toString()))[2]);
                    } else {
                        Nprice = Integer.parseInt(food_price.getText().toString());
                    }

                    if(company_name.length() == 0){
                        Ncompany = myDB.getDataByID(Integer.parseInt(ID.getText().toString()))[3];
                    } else {
                        Ncompany = company_name.getText().toString();
                    }

                    if(phone_number.length() == 0){
                        Nphone = myDB.getDataByID(Integer.parseInt(ID.getText().toString()))[4];
                    } else {
                        Nphone = phone_number.getText().toString();
                    }

                    myDB.editRowDataByID(Integer.parseInt(ID.getText().toString()), Nname, Nprice, Ncompany, Nphone);

                    ID.setText("");
                    food_name.setText("");
                    food_price.setText("");
                    company_name.setText("");
                    phone_number.setText("");
                }
                else {
                    Toast.makeText(addContent.this, "Please enter the ID!", Toast.LENGTH_LONG).show();
                }

                globalDB.IDTemp = 0;
                globalDB.ID = myDB.getIDList();
            }
        });

        //Removes a row inside the database
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID.getText().length() != 0) {
                    myDB.removeDataByID(Integer.valueOf(ID.getText().toString()));

                    ID.setText("");
                }
                else {
                    Toast.makeText(addContent.this, "Please enter the ID!", Toast.LENGTH_LONG).show();
                }

                globalDB.IDTemp = 0;
                globalDB.ID = myDB.getIDList();
            }
        });
    }

    //Add data to the database function
    public void AddData(String food, String price, String company, String phonenumber) {
        boolean insertData = myDB.addData(food, price, company, phonenumber);

        if(insertData == true) {
            Toast.makeText(addContent.this, "Successfully Entered Data", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(addContent.this, "Something went wrong...", Toast.LENGTH_LONG).show();
        }
    }
}
