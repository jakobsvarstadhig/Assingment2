package edu.hig.imt3662.ghostbusterslive.assingment2;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity implements SensorEventListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		// Look for barometer sensor
		SensorManager snsMgr = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
		Sensor pS = snsMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);
		snsMgr.registerListener(this, pS, SensorManager.SENSOR_DELAY_UI);

		List<String> listToView = new ArrayList<String>();
		listToView.add("Item 1");
		listToView.add("Item 2");
		
		setListAdapter( new ArrayAdapter<String>( this, 
				android.R.layout.simple_list_item_1,
				listToView ));
		//setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    }
	    @Override
	    public void onSensorChanged(SensorEvent event) {
	        float[] values = event.values;
	        Toast toast = Toast.makeText(getBaseContext(),"It is " + values[0], Toast.LENGTH_SHORT);
	        toast.show();
	    }
}
