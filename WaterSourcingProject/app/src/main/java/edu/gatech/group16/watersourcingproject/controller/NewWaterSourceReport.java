package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.HomeActivity;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class NewWaterSourceReport extends AppCompatActivity implements OnClickListener {

    private User user;
    private String currentDateTimeString;
    private List<WaterSourceReport> wsReports;
    private Spinner waterType, waterCondition;

    /**
     * OnCreate method required to load activity and loads everything that
     * is needed for the page while setting the view.
     *
     *
     * @param savedInstanceState Takes in a bundle that may contain an object
     *                           for use within this class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ws_report);

        waterType = (Spinner) findViewById(R.id.spinner_watertype);
        waterCondition = (Spinner) findViewById(R.id.spinner_watercondition);

        findViewById(R.id.edit_button_cancel).setOnClickListener(this);
        findViewById(R.id.edit_button_save).setOnClickListener(this);

        // Fills the spinners with ENUM
        ArrayAdapter<WaterCondition> adaptWaterCondition
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalConditions);
        ArrayAdapter<WaterType> adaptWaterType
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalTypes);

        findViewById(R.id.button_submit).setOnClickListener(this);

        waterType.setAdapter(adaptWaterType);
        waterCondition.setAdapter(adaptWaterCondition);

        user = (User) getIntent().getSerializableExtra("USER");

    }

    /**
     * OnClick method that will listen for clicks on the
     * view that is taken in and proceed with actions.
     *
     *
     * @param v Takes in a view that will contain buttons
     *          for the onClick method to listen to.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_cancel) {
            Intent cancelIntent = new Intent(this, HomeActivity.class);
            cancelIntent.putExtra("USER", user);
            startActivity(cancelIntent);
        } else if (i == R.id.button_submit) {
            Intent saveIntent = new Intent(this, HomeActivity.class);
            wsReports = user.getWaterSourceReport();
            wsReports.add(compileReport());
            user.setWaterSourceReports(wsReports);
            saveIntent.putExtra("USER", user);
            startActivity(saveIntent);
        }
    }

    /**
     * compileReport method which will create a new report and put
     * all of the
     *
     * @return wsReport will
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public WaterSourceReport compileReport() {
        int reportNumber = getReportNumber();
        Date currentDate = new Date();
        Location location = getUserLocation();
        WaterType type = (WaterType) waterType.getSelectedItem();
        WaterCondition condition = (WaterCondition) waterCondition.getSelectedItem();
        String submittedBy = user.getName();
        WaterSourceReport wsReport = new WaterSourceReport(reportNumber, currentDate, location, type, condition, submittedBy);
        return wsReport;
    }
    public static int getReportNumber() {
        //TO DO: Retrieve number of reports from Firebase variable.
        return 0;
    }

    public static Location getUserLocation() {
        //TO DO: Retrieve actual location from phone.
        Location location = new Location("Temp Location");
        location.setLatitude(1.2345d);
        location.setLongitude(1.2345d);
        return location;
    }
}
