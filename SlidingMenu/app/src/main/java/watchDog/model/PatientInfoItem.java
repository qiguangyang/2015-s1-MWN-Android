package watchDog.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexfyang on 30/04/15.
 */
public class PatientInfoItem {

    private String patientID;
    private String patientName;
    private String contactNum;
    private List<String> forbiddenArea;

    public PatientInfoItem() {
        List<String> forbiddenArea = new ArrayList<String>();
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public List<String> getForbiddenArea() {
        return forbiddenArea;
    }

    public void setForbiddenArea(List<String> forbiddenArea) {
        this.forbiddenArea = forbiddenArea;
    }

}
