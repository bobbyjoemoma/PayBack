package com.example.payback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.payback.CreateAccountActivity;
import com.example.payback.MainActivity;
import com.example.payback.TitleActivity;
import com.example.payback.User;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends TitleActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle("PayBack");
		try {
			checkCache();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Login(View view) throws InterruptedException, JSONException, IOException 
	{

		//for log in, url stub is AccountLogin.php
		Logger CONLOG = Logger.getLogger(LoginActivity.class .getName());
		CONLOG.setLevel(Level.INFO);
		CONLOG.info("Login page loaded");
		final String EMAIL_PATTERN = 
				"^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
		final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		final Matcher matcher;

		String email = ((EditText)findViewById(R.id.email)).getText().toString();
		String password = ((EditText)findViewById(R.id.password)).getText().toString();
		CONLOG.info("Login info: <"+email+", "+password+">");
		matcher = pattern.matcher(email);

		if(email.isEmpty())
		{
			Toast.makeText(getApplicationContext(), "Email field is empty", Toast.LENGTH_SHORT).show();
		}
		else if(password.isEmpty())
		{
			Toast.makeText(getApplicationContext(), "Password field is empty", Toast.LENGTH_SHORT).show();
		}
		else if(!matcher.matches())
		{
			Toast.makeText(getApplicationContext(), "Email: \""+email+"\" is not a valid email address!", Toast.LENGTH_SHORT).show();
		}
		else
		{
			if(AccessNet.AccountLogin(email,password)){
				if (((CheckBox)findViewById(R.id.rememberLogin)).isChecked())
					rememberLogin();
				else
					removeLoginFile();
				

				CONLOG.info("Server call successful and logged in!");
				Intent intent = new Intent(this, MainActivity.class);
				
				//user is declared in TitleActivity, which every activity extends
				user = new User(email, password); 

				JSONObject friends = AccessNet.lookupFriends(email,password);
				user.setFriends(user.parseFriends(friends));
				
				JSONObject transAsBorrower = AccessNet.lookupTransBorrower(user.getEmail(), user.getPassword());
				JSONObject transAsLender = AccessNet.lookupTransLender(user.getEmail(), user.getPassword());
				
				if(transAsBorrower.get("result").toString().equalsIgnoreCase("1"))
				{
					user.loadTransBorrowList(transAsBorrower,user.getEmail());
				}
				if(transAsLender.get("result").toString().equalsIgnoreCase("1"))
				{
					user.loadTransLendList(transAsLender,user.getEmail());
				}
				
				/*
				ArrayList<Friend> dummy = new ArrayList<Friend>();
				dummy.add(new Friend());
				
				if(friends.getString("NULL").equalsIgnoreCase("false"))
					user.setFriends(dummy);
				else
					user.setFriends(parseFriends(friends));
					*/
				startActivity(intent);
				Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_SHORT).show();
				this.finish();
			}

			else
			{
				CONLOG.info("Server call successful but user failed login.");
				Toast.makeText(getApplicationContext(),"Incorrect username or password", Toast.LENGTH_SHORT).show();
			}
		}
	}
	public void removeLoginFile()
	{
		File inputFile = new File(getApplicationContext().getFilesDir(),"login_info");
		if(inputFile.exists())
		{
			getApplicationContext().deleteFile("login_info");
		}
	}
	public void checkCache() throws IOException
	{
		File inputFile = new File(getApplicationContext().getFilesDir(),"login_info");
		String fileData = "";
		if(inputFile.exists()){
			
			FileInputStream fis = new FileInputStream(inputFile);
			int content;
			
			while(( content = fis.read()) != -1 )
			{
		         fileData+=(char)content;
		    }
			fis.close();
		
			CheckBox rememberLogin = (CheckBox)findViewById(R.id.rememberLogin);
			EditText email = (EditText)findViewById(R.id.email);
			EditText password = (EditText)findViewById(R.id.password);
			rememberLogin.setChecked(true);
			email.setText(fileData.substring(0,fileData.indexOf(" ")));
			password.setText(fileData.substring(fileData.indexOf(" ")+1,fileData.length()));			
		}
	}
	
	public void rememberLogin() throws IOException
	{
		String filename = "login_info";
		String storeData = "";
	 
		String email = ((EditText)findViewById(R.id.email)).getText().toString();
		String password = ((EditText)findViewById(R.id.password)).getText().toString();
		storeData = email + " " + password;
	
		FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
		fos.write(storeData.getBytes());
		fos.close();
	}

	public void CreateAccount(View view)
	{
		Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(intent);
        this.finish();
	}
	
	public void bypass(View view)
	{
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		
        startActivity(intent);
        this.finish();
	}
	
	
}
