package watchDog;

import info.androidhive.slidingmenu.R;
import watchDog.adapter.PatientInfoListAdapter;
import watchDog.model.PatientInfoItem;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

public class PatientInfoFragment extends ListFragment {

    public class RetrievePatientsListTask extends AsyncTask<String, Void, ArrayList<PatientInfoItem>> {

        protected ArrayList<PatientInfoItem> doInBackground(String... url) {
            HttpClient client = new DefaultHttpClient();
            HttpGet request;
            try {
                request = new HttpGet(new URI(url[0]));
                HttpResponse response = client.execute(request);

                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    ArrayList<PatientInfoItem> patients = new ArrayList<PatientInfoItem>();
                    if (entity != null) {
                        String out = EntityUtils.toString(entity, "UTF-8");
                        JSONArray jsonArray = new JSONArray(out);

                        for(int i = 0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            String id = jsonObject.getString("patientID");
                            String name = jsonObject.getString("patientName");

                            PatientInfoItem patient = new PatientInfoItem();
                            patient.setPatientID(id);
                            patient.setPatientName(name);

                            patients.add(patient);
                        }
                    }
                    return patients;
                }
                return null;
            } catch(Exception e) {
                e.printStackTrace();
                //Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<PatientInfoItem> list) {
            setListAdapter(new PatientInfoListAdapter(getActivity(), list));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RetrievePatientsListTask task = new RetrievePatientsListTask();
        task.execute(getResources().getString(R.string.get_all_patient_url));
    }
}

