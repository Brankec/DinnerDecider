package branimir.kobescak.com.dinnerdecider;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gejmer on 3/13/2018.
 */

public class ListContent extends AppCompatActivity {
    DatabaseHelper myDB = new DatabaseHelper(this);

    List<String> foods;
    Map<String, List<String>> info;

    ListView listFood;
    //ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        listFood = (ListView) findViewById(R.id.list);

        //Populates list view with data from the database
        populateListView();

        //listData.setAdapter(listAdapter);
    }


    //Populate list view function
    /*private void populateListView() {
        foods = new ArrayList<>();
        info = new HashMap<>();
        Cursor data = myDB.getDatalist();

        while(data.moveToNext()) {
            foods.add(data.getString(1));
        }
    }*/

    //Populate list view function
    private void populateListView() {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

        Cursor data = myDB.getDatalist();
        ArrayList<String> listData = new ArrayList<>();

        HashMap item = new HashMap<String,String>();

        while(data.moveToNext()) {
            listData.add(data.getString(0));

            //item.put( "line1", data.getString(1)); //this is for a 2 line list view
            //item.put( "line2", data.getString(0));
            //list.add( item );
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);

        listFood.setAdapter(adapter);
    }
}
