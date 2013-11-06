package com.example.payback;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.view.LayoutInflater;
import android.content.Intent;
import android.content.DialogInterface;
import android.widget.Toast;

public class SettingsActivity extends TitleActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		modifyTitle("Settings",R.layout.activity_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	public void showEditPassword(View view) {
		LayoutInflater inflater = this.getLayoutInflater();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("Edit Password")
			   .setView(inflater.inflate(R.layout.dialog_settings_password, null))
			   .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   Toast.makeText(getApplicationContext(),"Back", Toast.LENGTH_SHORT).show();				        	   
		        	   dialog.dismiss();
				   }
			   })
			   .setNegativeButton(R.string.save_changes, new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_SHORT).show();
					   editPassword();
				   }
			   });
		Dialog dialog = builder.create();
		dialog.show();
	}
	
	public void editPassword() {
		//server call to edit password
	}
	
	public void showMainMenu(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
