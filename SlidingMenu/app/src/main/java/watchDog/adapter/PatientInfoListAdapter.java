package watchDog.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.slidingmenu.R;
import watchDog.model.NavDrawerItem;
import watchDog.model.PatientInfoItem;

/**
 * Created by alexfyang on 30/04/15.
 */
public class PatientInfoListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<PatientInfoItem> patientInfoItems;

    public PatientInfoListAdapter(Context context, ArrayList<PatientInfoItem> patientInfoItems){
        this.context = context;
        this.patientInfoItems = patientInfoItems;
    }

    @Override
    public int getCount() {
        return patientInfoItems.size();
    }

    @Override
    public Object getItem(int position) {
        return patientInfoItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.patient_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView patientName = (TextView) convertView.findViewById(R.id.name);

//        imgIcon.setImageResource(patientInfoItems.get(position).getIcon());
        patientName.setText(patientInfoItems.get(position).getPatientName());

        return convertView;
    }
}
