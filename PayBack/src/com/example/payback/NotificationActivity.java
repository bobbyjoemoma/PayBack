package com.example.payback;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.payback.Notification;
import com.example.payback.PageKillReceiver;
import com.example.payback.TitleActivity;

public class NotificationActivity extends TitleActivity
{

	static Activity activityInstance;	//these are variables
	static PageKillReceiver pkr;		//used for PageKillReceiver.java
	static IntentFilter filter;
	
	static final boolean DEMO = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		modifyTitle("Notifications",R.layout.activity_notification);
		
		activityInstance = this;
		pkr = new PageKillReceiver(); pkr.setActivityInstance(activityInstance);
		filter = new IntentFilter();
		filter.addAction("com.Payback.Logout_Intent");
		registerReceiver(pkr, filter);
	/*TODO*/
		try {
			user.setNotifications(user.parseNotifs(AccessNet.lookupAllNotifs(user.getEmail(),user.getPassword()),user.getEmail()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ArrayList<Notification> nots = user.getNotifications();
		
		final ListView listview = (ListView) findViewById(R.id.listview);
		/*
		if(DEMO)
		{
			nots.add(new Notification("Santa C.", "Will", "Santa added you as a friend."));
			nots.add(new Notification("Nigerian P.", "Will", "Nigerian has sent you notice of a pending transaction."));
		}
		*/
			//final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, al);
		NotiAdapter na = new NotiAdapter(this, android.R.layout.simple_list_item_1, nots);
		listview.setAdapter(na);
		setContentView(listview);
	}
}

class NotiAdapter extends ArrayAdapter<Notification>
{
	private final Context context;
	private final ArrayList<Notification> al;
	public NotiAdapter (Context context, int textViewResourceId, ArrayList<Notification> al)
	{
		super(context, R.layout.activity_notification, al);
		this.context = context;
		this.al = al;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.activity_noti_row_layout, parent, false);
	    TextView from = (TextView) rowView.findViewById(R.id.from);
	    TextView date = (TextView) rowView.findViewById(R.id.date);
	    TextView message = (TextView) rowView.findViewById(R.id.secondLine);
	    
	    //TODO: set the following text to the person's first name and last name with a server call, instead of just their email
	    from.setText(al.get(position).getFromEmail());
	    
	    date.setText(al.get(position).getDate());
	    message.setText(al.get(position).getMessage());
	    return rowView;
	}
}
