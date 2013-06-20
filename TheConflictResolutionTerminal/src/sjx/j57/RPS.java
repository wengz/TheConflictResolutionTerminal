package sjx.j57;


import java.util.Random;

import sjx.j57.R;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class RPS extends Activity implements SensorEventListener{
	
	public static final int INIT = 0;
	public static final int READY1 = 1;
	public static final int READY2 = 2;
	public static final int RESULT = 3;
	
	int mode;
	SensorManager mSensorManager;
	Sensor mSensor;
	ImageView show;
	AnimationDrawable anim;
	
	
	public void showRandomResult ( ImageView img ){
		Random rd = new Random();
		int a = rd.nextInt(3);
		
		if ( a == 0 ){
			img.setImageDrawable(getResources().getDrawable(R.drawable.rock));
		}else if ( a == 1 ){
			img.setImageDrawable(getResources().getDrawable(R.drawable.s));
		}else {
			img.setImageDrawable(getResources().getDrawable(R.drawable.paper));
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
	}
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(SensorManager.SENSOR_ORIENTATION);
        
        mode = INIT; 
        
        show  = new ImageView(this);
        show.setImageDrawable(getResources().getDrawable(R.drawable.rps_anim));
        setContentView(show);
        
        anim = (AnimationDrawable)show.getDrawable();
        anim.start();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_rps, menu);
        return true;
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	public void onSensorChanged(SensorEvent event) {
		
		float angle = event.values[1];
		
		if ( mode == INIT ){
			if ( angle > 9 ){
				mode = READY1;
				show.setImageDrawable(getResources().getDrawable(R.drawable.rps_anim));
		        anim = (AnimationDrawable)show.getDrawable();
		        anim.start();
				
			}
			
			if ( angle < -9 ){
				mode = READY2;
				show.setImageDrawable(getResources().getDrawable(R.drawable.rps_anim));
		        anim = (AnimationDrawable)show.getDrawable();
		        anim.start();
				
			}
			
		}else if ( mode == READY1 ){
			if ( angle < 4 ){
				mode = RESULT;
				anim.stop();
				showRandomResult(show);
			}
			
		}else if ( mode == READY2){
			if ( angle > -5 ){
				mode = RESULT;
				anim.stop();
				showRandomResult(show);
			}
			
		}else{
			if ( angle > 9 ){
				mode = READY1;
				show.setImageDrawable(getResources().getDrawable(R.drawable.rps_anim));
		        anim = (AnimationDrawable)show.getDrawable();
		        anim.start();
				
			}
			
			if ( angle < -9 ){
				mode = READY2;
				show.setImageDrawable(getResources().getDrawable(R.drawable.rps_anim));
		        anim = (AnimationDrawable)show.getDrawable();
		        anim.start();
				
			}
		}
	}
}
