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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements SensorEventListener {
	
	SensorManager mSensorManager;
	List<Sensor> mSensors;
	List<String> mSensorNames;
	ArrayAdapter<String> mMainviewListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		// Get a sensormanager and make it create a list of the sensors on a device
		mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
		mSensors = mSensorManager.getSensorList( Sensor.TYPE_ALL );

		// Create a list like the sensor-list, but this one will contain the sensornames
		mSensorNames = new ArrayList<String>();
		for( int i = 0; i < mSensors.size(); ++i ) {
			mSensorNames.add( mSensors.get( i ).getName() );
		}
		
		// Set the adapter for this listActivity to show a list of sensornames
		mMainviewListAdapter = new ArrayAdapter<String>( this,
				android.R.layout.simple_list_item_1,
				mSensorNames );
		
		// Activate the adapter - this will show the list in the activity
		setListAdapter( mMainviewListAdapter );
	}
	
	/**
	 * Clicking on a listEntry
	 * 
	 * By clicking on a list-entry, the corresponding sensor will activate and start
	 * communicating its output-info. The action happens when the values changes and
	 * the method called onSensorChanged() are fired up.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		mSensorManager.registerListener(this, mSensors.get(position), SensorManager.SENSOR_DELAY_NORMAL);
	}

	/**
	 * OptionsMenu
	 * 
	 * Inflate the menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * onAccuracyChanged()
	 * 
	 * When the accuracy of a sensor changes, this will be fired. The method is not
	 * used in our application, but it kind of fit together with the next method.
	 */
	@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ToDo: fill here if necessary
	}

	/**
	 * onSensorChanged()
	 * 
	 * This is where the magic happens. A sensor has changed it's value and we are
	 * notified through an event. When this occurs and our event is executed, we
	 * find data from the event and toast it.
	 * <p>
	 * The result will be a toast message telling the name of the sensor and 
	 * the output-value from this sensor.
	 * <p>
	 * At the end the sensorlistener is unregistered. 
	 */
	@Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        String sensorName = event.sensor.getName();
        Toast toast = Toast.makeText(getBaseContext(), sensorName + getString(R.string.sensor_return_text) + values[0], Toast.LENGTH_SHORT);
        toast.show();
        mSensorManager.unregisterListener(this);
    }
}
