package org.projects.shoppinglist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "shoppinglist"; // Just an unique identifier, does not refer to a specific path...
    private String name = "";

    ArrayAdapter<String> adapter;
    ListView listView;
    ArrayList<String> bag = new ArrayList<String>();

    public ArrayAdapter getMyAdapter()
    {
        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting our listiew - you can check the ID in the xml to see that it
        //is indeed specified as "list"
        listView = (ListView) findViewById(R.id.list);
        //here we create a new adapter linking the bag and the
        //listview
        adapter =  new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,bag );

        //setting the adapter on the listview
        listView.setAdapter(adapter);
        //here we set the choice mode - meaning in this case we can
        //only select one item at a time.
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        // Add Item to list
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ShoppingInput = (EditText) findViewById(R.id.shoppingText);
                EditText inputQuantity = (EditText) findViewById(R.id.shoppingQuantity);
                if (inputQuantity.getText().toString().length() > 0) {
                    if (inputQuantity.getText().toString().matches("")) {
                        bag.add(ShoppingInput.getText().toString());
                    } else {
                        for (int i = 0; i < Integer.parseInt(inputQuantity.getText().toString()); i++) {
                            bag.add(ShoppingInput.getText().toString());

                        }
                    }
                    getMyAdapter().notifyDataSetChanged();
                    ShoppingInput.setText("");
                    inputQuantity.setText("");
                }
            }
        });

        // Delete Item
        Button deleteItem = (Button) findViewById(R.id.deleteItem);
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedPosition = listView.getCheckedItemPosition();
                if(checkedPosition != ListView.INVALID_POSITION) {
                    listView.setItemChecked(-1, true);
                    bag.remove(checkedPosition);
                    getMyAdapter().notifyDataSetChanged();
                }
            }
        });

        // Clear list
        Button clearList = (Button) findViewById(R.id.deleteEntire);
        clearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    bag.clear();
                    getMyAdapter().notifyDataSetChanged();
                }
        });




        //add some stuff to the list so we have something
        // to show on app startup
        //bag.add("Bananas");
        //bag.add("Apples");
        //bag.add("Goats");
        //bag.add("Cats");
        //bag.add("Squirrels");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }










    //This method is called before our activity is created
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //ALWAYS CALL THE SUPER METHOD
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
		/* Here we put code now to save the state */
        outState.putStringArrayList("bag", bag);
        int checkedPosition = listView.getCheckedItemPosition();
        outState.putInt("checkedPosition", checkedPosition);
        //System.out.println(checkedPosition);
    }
    //this is called when our activity is recreated, but
    //AFTER our onCreate method has been called
    //EXTREMELY IMPORTANT DETAIL
    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        //MOST UI elements will automatically store the information
        //if we call the super.onRestoreInstaceState
        //but other data will be lost.
        super.onRestoreInstanceState(savedState);
        Log.i(TAG, "onRestoreInstanceState");
        bag = savedState.getStringArrayList("bag");

        adapter =  new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,bag );

        //setting the adapter on the listview
        listView.setAdapter(adapter);

        int checkedPosition = savedState.getInt("checkedPosition");
        listView.setItemChecked(checkedPosition, true);


    }





}
