package com.codepath.example.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	private ArrayList<String> toDoItems;
	private ArrayAdapter<String> toDoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		readItems();
		toDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
		lvItems.setAdapter(toDoAdapter);
		setupListViewListener();
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int pos, long id) {
				toDoItems.remove(pos);
				toDoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}
	
	public void onAddedItem(View v) {
		 String itemText = etNewItem.getText().toString();
		 toDoAdapter.add(itemText);
		 etNewItem.setText("");
		 writeItems();
	}
	
	private void readItems() {
		File fileDir = getFilesDir();
		File toDoFile = new File(fileDir, "todo.txt");
		try {
				toDoItems = new ArrayList<String>(FileUtils.readLines(toDoFile));
		} catch (IOException e) {
			toDoItems = new ArrayList<String>();
		}
		
	}
	
	private void writeItems() {
		File fileDir = getFilesDir();
		File toDoFile = new File(fileDir, "todo.txt");
		try {
			FileUtils.writeLines(toDoFile, toDoItems);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
