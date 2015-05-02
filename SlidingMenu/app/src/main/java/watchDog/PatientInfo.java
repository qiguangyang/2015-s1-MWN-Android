package watchDog;

import info.androidhive.slidingmenu.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

public class PatientInfo extends Activity {
	
	TextView nameTv, roomTv;
	Spinner s1,s2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_patient_list);

	}

}
