package branimir.kobescak.com.dinnerdecider;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gejmer on 3/13/2018.
 */

public class ListContent extends AppCompatActivity {
    DatabaseHelper myDB = new DatabaseHelper(this);

    ListView listFood;

    private ExpandableListView listView;
    private ExpandableListAdapterClass listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        listView = (ExpandableListView)findViewById(R.id.list);
        populateListView();
        listAdapter = new ExpandableListAdapterClass(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    //Populate list view function
    private void populateListView() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        int index = 0;

        Cursor data = myDB.getDatalist();
        ArrayList<String> listData = new ArrayList<>();

        while(data.moveToNext()) {
            listDataHeader.add(data.getString(1));

            List<String> info = new ArrayList<>();
            info.add(data.getString(0));
            info.add(data.getString(2));
            info.add(data.getString(3));
            info.add(data.getString(4));

            listHash.put(listDataHeader.get(index),info);
            index++;
        }
        index = 0;
    }
}
