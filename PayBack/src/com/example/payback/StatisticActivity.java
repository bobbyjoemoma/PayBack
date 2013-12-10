package com.example.payback;

import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatisticActivity extends TitleActivity
{
	static Activity activityInstance;	//these are variables
	static PageKillReceiver pkr;		//used for PageKillReceiver.java
	static IntentFilter filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		modifyTitle("Transaction Statistics",R.layout.activity_statistic);
		
		activityInstance = this;
		pkr = new PageKillReceiver(); pkr.setActivityInstance(activityInstance);
		filter = new IntentFilter();
		filter.addAction("com.Payback.Logout_Intent");
		registerReceiver(pkr, filter);
		
		displayValue();

	}

    public void displayTransactionsHistory (View view)
    {
    	LineGraph line = new LineGraph();
    	TimeSeries payableSeries = getPayableSeries("Payable");
    	TimeSeries receivableSeries = getReceivableSeries("Receivable");
    	
    	//Open a new activity to display the graph
    	Intent lineIntent = line.getTwoLineIntent(this, payableSeries, receivableSeries, "Transactions History");
    	startActivity(lineIntent);
    }    
    
    public void displayValue ()
    {   
//    	double currentPayables = getcurrentPayables();
//    	double currentReceivables = getcurrentReceivables();
//    	
//    	double totalPayables = getTotalPayables();
//    	double totalReceivables = getTotalReceivables();
//    	
//    	//Append value to textview
//    	TextView currentPayableTextView = (TextView) findViewById(R.id.currentPayables);
//    	currentPayableTextView.append(String.format("$%.2f", currentPayables));
//    	TextView currentReceivableTextView = (TextView) findViewById(R.id.currentReceivables);
//    	currentReceivableTextView.append(String.format("$%.2f", currentReceivables));
//    	TextView TotalPayableTextView = (TextView) findViewById(R.id.totalPayables);
//    	TotalPayableTextView.append(String.format("$%.2f", totalPayables));
//    	TextView TotalReceivableTextView = (TextView) findViewById(R.id.totalReceivables);
//    	TotalReceivableTextView.append(String.format("$%.2f", totalReceivables));
//    	
//    	//Display Pie Graph
//    	PieGraph pie = new PieGraph();
//    	GraphicalView gView = pie.getTwoSectionView(this, totalPayables, totalReceivables);
//    	LinearLayout chartView = (LinearLayout) findViewById(R.id.chart);
//    	chartView.addView(gView);
    }
    
//    public double getcurrentPayables ()
//    {
//     	
//    	/*get JSON*/
//    	JSONOBJECT jObject = new JSONObject();
//    	JSONArray transactionJSONArray = JObject.getJSONArray("Transaction");
//    	
//    	/*get current payable*/    	
//    	double currentPayable = getCurrentPayableFromJSONArray(transactionJSONArray);
//    	
//    	return currentPayable;
//    }
    
//    public double getTotalPayables ()
//    {
//    	/*get JSON*/
//    	JSONOBJECT jObject = new JSONObject();
//    	JSONArray transactionJSONArray = JObject.getJSONArray("Transaction");
//    	
//    	/*get total payable*/
//    	double totalPayable = getTotalPayableFromJSONArray(transactionJSONArray);
//    	
//    	return totalPayable;
//    }
    
//    public double getcurrentReceivables ()
//    {
//    	/*get JSON*/
//    	JSONOBJECT jObject = new JSONObject();
//    	JSONArray transactionJSONArray = JObject.getJSONArray("Transaction");
//    	
//    	/*get current receivable*/
//    	double currentReceivable = getCurrentRecievableFromJSONArray(transactionJSONArray);
//    
//    	return currentReceivable;
//    }
    
//    public double getTotalReceivables ()
//    {
//    	/*get JSON*/
//    	JSONOBJECT jObject = new JSONObject();
//    	JSONArray transactionJSONArray = JObject.getJSONArray("Transaction");
//    	
//    	/*get total receivable*/
//    	double totalReceivable = getTotalReceivableFromJSONArray(payableJSONArray);
//    	
//    	return totalReceivable;
//    }
        
    public TimeSeries getPayableSeries (String seriesName)
    {
    	int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		int[] y =  { 30, 34, 45, 57, 77, 89, 100, 111 ,123 ,145 }; // y values!
		TimeSeries series = new TimeSeries(seriesName); 
		for( int i = 0; i < x.length; i++)
		{
			series.add(x[i], y[i]);
		}
		
		return series;
    }
    
	public TimeSeries getReceivableSeries (String seriesName)
	{
		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		int[] y =  { 145, 123, 111, 100, 89, 77, 57, 45, 34, 30}; // y values!
		TimeSeries series = new TimeSeries(seriesName); 
		for( int i = 0; i < x.length; i++)
		{
			series.add(x[i], y[i]);
		}
		
		return series;
		
	}
	
    public void showMainMenu(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

}
