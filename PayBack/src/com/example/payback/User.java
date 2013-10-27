package com.example.payback;

import java.util.*;

import android.os.Parcel;
import android.os.Parcelable;
abstract class Account
{
	String fName;
	String lName;
	String email;
	
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
public class User extends Account{
	private ArrayList<Friend> friends; //updated when the User logs in
	
	/* Only called when creating a brand new account! */
	User(String fName, String lName, String email) 
	{
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		friends = new ArrayList<Friend>();
		boolean worked = sendNewUserToServer();
		if(!worked)
			throw new IllegalArgumentException();
	}
	static private boolean sendNewUserToServer()
	{
		//TODO: send email, fname, lname, pword to database
		//return success/fail
		return true;
	}
	
	/* Used for the rest of the time, when the user logs in */
	User(String email)
	{
		this.email = email;
		fName = firstNameLookup(email);
		lName = lastNameLookup(email);
		friends = friendsLookup(email);
	}
	private String firstNameLookup(String email)
	{
		//TODO: Pull user's first name from server
		return "John";
	}
	private String lastNameLookup(String email)
	{
		//TODO: Pull user's last name from server
		return "Doe";
	}
	private ArrayList<Friend> friendsLookup(String email) // Used for existing users
	{
		ArrayList<Friend> f = new ArrayList<Friend>();
		//TODO: Pull information about each friend from the server: first name, last name, email. 
		return f;
	}
	
	
}

class Friend extends Account implements Parcelable {
	boolean selected;
	
	Friend(String fName, String lName, String email){
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.selected = false;
		
		if(!sendNewFriendToServer())
			throw new IllegalArgumentException();
		
	}
	
	//blank friend. FOR TESTING ONLY
	Friend(String fName, String lName){
		this.fName = fName;
		this.lName = lName;
		this.email = "";
		this.selected = false;
		
		if(!sendNewFriendToServer())
			throw new IllegalArgumentException();
		
	}
	
	//Getters and Setters
	  public boolean isSelected() {
		    return selected;
		  }

	  public void setSelected(boolean selected) {
		  this.selected = selected;
	  }
	
	//Methods
	static boolean sendNewFriendToServer()
	{
		//TODO: send email, fname, lname, pword to database
		//return success/fail
		return true;
	}

	public String toString() {
		return  getfName() + " " + getlName();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(fName);
		dest.writeString(lName);
		dest.writeString(email);
	
	}
	
	public Friend(Parcel source){
		 this.fName = source.readString();
		 this.lName = source.readString();
		 this.email = source.readString();
	}
	
    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {
    	 
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }
 
        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
    
    
    
	
}