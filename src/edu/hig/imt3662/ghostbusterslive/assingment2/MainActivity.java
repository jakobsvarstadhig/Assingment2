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
		
		
		// Look for barometer sensor
		mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
		mSensors = mSensorManager.getSensorList( Sensor.TYPE_ALL );
//		Sensor pS = snsMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);
//		snsMgr.registerListener(this, pS, SensorManager.SENSOR_DELAY_UI);

		mSensorNames = new ArrayList<String>();
		for( int i = 0; i < mSensors.size(); ++i ) {
			mSensorNames.add( mSensors.get( i ).getName() );
		}
		
		mMainviewListAdapter = new ArrayAdapter<String>( this,
				android.R.layout.simple_list_item_1,
				mSensorNames );
		
		setListAdapter( mMainviewListAdapter );
		//setContentView(R.layout.activity_main);
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
//		Toast.makeText(
//				getBaseContext(), 
//				getString(R.string.toast_message_p1) + mSensors.get(position).getPower() + getString(R.string.toast_message_p2), 
////				"Uses " + mSensors.get(position).getPower() + "mA while in use...", 
//				Toast.LENGTH_SHORT)
//			.show();
		mSensorManager.registerListener(this, mSensors.get(position), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ToDo: fill here if necessary.
	}

	@Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        String sensorName = event.sensor.getName();
        Toast toast = Toast.makeText(getBaseContext(), sensorName + getString(R.string.sensor_return_text) + values[0], Toast.LENGTH_SHORT);
        toast.show();
        mSensorManager.unregisterListener(this);
    }
}
