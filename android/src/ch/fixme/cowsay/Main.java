package ch.fixme.cowsay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Main extends Activity
{    
	private Cow cow;
	
    // Menu 
	public static final int MENU_SHARE = Menu.FIRST;

    /* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_SHARE, 0, "Share");
        return true;
    }
    
    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_SHARE:
            	Log.d("Main Menu", "Share");
            	Intent intent = new Intent(Intent.ACTION_SEND);
            	intent.setType("text/plain");
            	intent.putExtra(Intent.EXTRA_SUBJECT, "Cowsay");
            	intent.putExtra(Intent.EXTRA_TEXT, cow.asString());
            	startActivity(Intent.createChooser(intent, "Share with"));
        }

        return false;
    }

	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        final Context ctxt = getApplicationContext();
        final EditText txt = (EditText) findViewById(R.id.message);
        
        cow = new Cow(ctxt);

        TextWatcher myTextWatcher = new TextWatcher() {        	
        	public void onTextChanged(CharSequence s, int start, int before, int count) {
        		cowRefresh();
        	}
        	
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        
        txt.addTextChangedListener(myTextWatcher);
        txt.setText("Moo");
        
        populateCowTypes();
    }
    
    private void populateCowTypes() {
        // Populate the cow type Spinner widget        
    	final String[] items = cow.getCowTypes();
    	
    	for (int i = 0; i < items.length; i++) {
    		String item = items[i];
    		Log.d("Main", "item: " + item);
		}
    
        Spinner spinner = (Spinner) findViewById(R.id.type);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        //s.setOnItemClickListener(new OnItemClickListener() {
       	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("Main", "Click");
				cow.style = items[position];
				cowRefresh();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        });
    }
    
    private void cowRefresh() {
    	Log.d("Main", "Let's refresh the cow");
    	
    	final EditText txt = (EditText) findViewById(R.id.message);
    	cow.message = txt.getText().toString();
    			
        ((TextView) findViewById(R.id.thecow)).setText(cow.asString());
    }
}
