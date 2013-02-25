package com.protofarm.baargames;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * TODO Put here a description of what this class does.
 *
 * @author kyle.
 *         Created Apr 4, 2011.
 */
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        Button infoButton = (Button)findViewById(R.id.btn_info);
        infoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub.
				Toast.makeText(v.getContext(),R.string.popup_info, Toast.LENGTH_LONG).show();
				
			}
		});
        
        infoButton.setText("Credit");
        
        Button greenButton = (Button)findViewById(R.id.btn_pokkerhand);
        greenButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						MainActivity.this,
						PokkerHandActivity.class
				);
			startActivity(intent);	
			}
		});
        
        
        Button indianButton = (Button)findViewById(R.id.btn_indian);
        indianButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						MainActivity.this,
						IndianActivity.class
				);
			startActivity(intent);	
			}
		});
        
    }
}