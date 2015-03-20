package net.eloka.vitaltahealth.fragment;

/**
 * Created by android1 on 14/2/15.
 */

import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParseException;
import com.larvalabs.svgandroid.SVGParser;

import net.eloka.vitaltahealth.R;
import net.eloka.vitaltahealth.manager.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;

import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;


public class ReportFragment extends Fragment implements View.OnClickListener {
    private SessionManager sessionManager;
    private ImageView imgMedicine, imgDiet, imgInsulin, imgGlucoMeter,
            imgPulseOxi, imgWeight, imgThermo, imgBloodPressure,
            imgPatientBorder, imgBarGraph, imgLineGraph, imgPieGraph;
    private TextView txtTitle, txtTitleOption, txtTitleAvg, txtTitleStdDev,
            txtTitlePercent, txtYesterday, txtMonth, txtWeek, txtAvgYesterday,
            txtAvgWeek, txtAvgMonth, txtStdDevYesterday, txtStdDevWeek,
            txtStdDevMonth, txtPercentYesterday, txtPercentWeek,
            txtPercentMonth;
    private LinearLayout linearDevices, linearPatients, linearGraphDevices;
    private HorizontalScrollView horizDevices, horizPatients;
    private int textSize;
    private LinearLayout.LayoutParams txtNameParam, txtLineParam;
    private RelativeLayout relGraphView;
    private ArrayList<Double> glucoseYesAL, glucoseMonthAL, glucoseWeekAL, highGlucoseAL,
            lowGlucoseAL,
            normalGlucoseAL;
    private ArrayList<Integer> al3Values;
    private ArrayList<String> lowGlucoseDateAL, normalGlucoseDateAL, highGlucoseDateAL;
    private TableLayout tblLayout;
    private WebView webView;
    private LinearLayout.LayoutParams trParam, trTxtParam, trLineParam,
            trLineTitleParam;
    float[][] randomNumbersTab;
    private String selectedOption, selectedDietOption;
    PieChart mChart;

    public ReportFragment() {
        sessionManager = SessionManager.getInstance(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reports, container,
                false);
        initParam();
        initViews(rootView);
        Log.i("myLog", "selected Patient id:" + sessionManager.selPatientId);
        getPatients();
        // showPieChart();
        // pieChart("Glucose");
        return rootView;
    }

    public void initViews(View v) {
        imgMedicine = (ImageView) v.findViewById(R.id.imgMedicationInReports);
        imgDiet = (ImageView) v.findViewById(R.id.imgDietInReports);
        imgThermo = (ImageView) v.findViewById(R.id.imgThermoMeterInReports);
        imgPulseOxi = (ImageView) v.findViewById(R.id.imgPulseOxiInReports);
        imgWeight = (ImageView) v.findViewById(R.id.imgWeightInReports);
        imgGlucoMeter = (ImageView) v.findViewById(R.id.imgGlucoMeterInReports);
        imgInsulin = (ImageView) v.findViewById(R.id.imgInsulinInReports);
        //imgLab = (ImageView) v.findViewById(R.id.imgLabInReports);
        linearGraphDevices = (LinearLayout) v
                .findViewById(R.id.linearGraphDevicesInReports);
        imgPatientBorder = (ImageView) v
                .findViewById(R.id.imgPatientBorderInReports);
        imgBloodPressure = (ImageView) v
                .findViewById(R.id.imgBloodPressureInReports);
        linearDevices = (LinearLayout) v
                .findViewById(R.id.linearDevicesInReports);
        linearPatients = (LinearLayout) v
                .findViewById(R.id.linearPatientsInReports);
        horizPatients = (HorizontalScrollView) v
                .findViewById(R.id.horizScrollPatientsInReports);
        horizDevices = (HorizontalScrollView) v
                .findViewById(R.id.horizScrollDevicesInReports);
        imgBarGraph = (ImageView) v.findViewById(R.id.imgBarGraphInReports);
        imgLineGraph = (ImageView) v.findViewById(R.id.imgLineGraphInReports);
        imgPieGraph = (ImageView) v.findViewById(R.id.imgPieGraphInReports);
        txtTitle = (TextView) v.findViewById(R.id.txtTitleInReports);
        txtTitleOption = (TextView) v
                .findViewById(R.id.txtTitleOptionInReports);
        txtTitleAvg = (TextView) v
                .findViewById(R.id.txtTitleAvgOptionInReports);
        txtTitleStdDev = (TextView) v
                .findViewById(R.id.txtTitleStdDevOptionInReports);
        txtTitlePercent = (TextView) v
                .findViewById(R.id.txtTitlePercentOptionInReports);
        txtYesterday = (TextView) v.findViewById(R.id.txtYesterdayInReports);
        txtWeek = (TextView) v.findViewById(R.id.txtWeekInReports);
        txtMonth = (TextView) v.findViewById(R.id.txtMonthInReports);
        txtAvgYesterday = (TextView) v
                .findViewById(R.id.txtYesterdayAvgInReports);
        txtAvgWeek = (TextView) v.findViewById(R.id.txtWeekAvgInReports);
        txtAvgMonth = (TextView) v.findViewById(R.id.txtMonthAvgInReports);
        txtPercentYesterday = (TextView) v
                .findViewById(R.id.txtYesterdayPercentInReports);
        txtPercentWeek = (TextView) v
                .findViewById(R.id.txtWeekPercentInReports);
        txtPercentMonth = (TextView) v
                .findViewById(R.id.txtMonthPercentInReports);
        txtStdDevYesterday = (TextView) v
                .findViewById(R.id.txtYesterdayStdDevInReports);
        txtStdDevWeek = (TextView) v.findViewById(R.id.txtWeekStdDevInReports);
        txtStdDevMonth = (TextView) v
                .findViewById(R.id.txtMonthStdDevInReports);
        txtTitle = (TextView) v.findViewById(R.id.txtTitleInReports);
        relGraphView = (RelativeLayout) v.findViewById(R.id.relGraphPatientsInReports);
        txtTitle.setTypeface(sessionManager.tfRobotoCondLight);
        txtTitleAvg.setTypeface(sessionManager.tfRobotoCondLight);
        txtTitleOption.setTypeface(sessionManager.tfRobotoCondLight);
        txtTitlePercent.setTypeface(sessionManager.tfRobotoCondLight);
        txtTitleStdDev.setTypeface(sessionManager.tfRobotoCondLight);
        txtYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        txtWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtAvgMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtAvgWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtAvgYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        txtPercentMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtPercentWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtPercentYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        txtStdDevMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtStdDevWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtStdDevYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        imgPatientBorder.setImageDrawable(sessionManager.svgPatientBg);
        horizDevices.setBackgroundColor(Color.WHITE);

        imgMedicine.setImageDrawable(sessionManager.svgMedicineReport);
        imgDiet.setImageDrawable(sessionManager.svgDietReport);
        imgBloodPressure.setImageDrawable(sessionManager.svgPressureReport);
        imgPulseOxi.setImageDrawable(sessionManager.svgPulseReport);
        imgGlucoMeter.setImageDrawable(sessionManager.svgGlucometerReport);
        imgThermo.setImageDrawable(sessionManager.svgTemperatureReport);
        imgWeight.setImageDrawable(sessionManager.svgWeightReport);
        imgInsulin.setImageDrawable(sessionManager.svgInsulinReport);

        imgBarGraph.setImageDrawable(sessionManager.svgBarGraph);
        imgLineGraph.setImageDrawable(sessionManager.svgLineGraph);
        imgPieGraph.setImageDrawable(sessionManager.svgPieGraph);
        imgPieGraph.setOnClickListener(this);
        imgLineGraph.setOnClickListener(this);
        imgBarGraph.setOnClickListener(this);
        imgMedicine.setOnClickListener(this);
        imgDiet.setOnClickListener(this);
        imgThermo.setOnClickListener(this);
        imgPulseOxi.setOnClickListener(this);
        imgWeight.setOnClickListener(this);
        imgGlucoMeter.setOnClickListener(this);
        imgBloodPressure.setOnClickListener(this);
        imgInsulin.setOnClickListener(this);
        txtYesterday.setOnClickListener(this);
        txtWeek.setOnClickListener(this);
        txtMonth.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.imgMedicationInReports) {
            txtTitle.setText("Medication Report");
            txtTitleOption.setText("Medication");
            refreshTextViews();

        } else if (v.getId() == R.id.imgDietInReports) {
            txtTitle.setText("Diet Report");
            txtTitleOption.setText("Diet");
            refreshTextViews();

        } else if (v.getId() == R.id.imgInsulinInReports) {
            txtTitle.setText("Insulin Report");
            txtTitleOption.setText("Insulin");
            refreshTextViews();

        } else if (v.getId() == R.id.imgPulseOxiInReports) {
            txtTitle.setText("Pulse Oximeter Readings");
            txtTitleOption.setText("Pulse");
            refreshTextViews();
            getDeviceReading("Pulse Oximeter");

        } else if (v.getId() == R.id.imgGlucoMeterInReports) {
            txtTitle.setText("Glucometer Readings");
            txtTitleOption.setText("Glucose");
            getDeviceReading("Glucometer");

        } else if (v.getId() == R.id.imgBloodPressureInReports) {
            txtTitle.setText("Blood Pressure Readings");
            txtTitleOption.setText("Blood Pressure");
            refreshTextViews();
            getDeviceReading("Blood Pressure");

        } else if (v.getId() == R.id.imgWeightInReports) {
            txtTitle.setText("Weight Readings");
            txtTitleOption.setText("Weight");
            refreshTextViews();
            getDeviceReading("Weight");

        } else if (v.getId() == R.id.imgThermoMeterInReports) {
            txtTitle.setText("Thermometer Readings");
            txtTitleOption.setText("Temperature");
            refreshTextViews();
            getDeviceReading("Thermometer");

        } else if (v.getId() == R.id.imgPieGraphInReports) {
            // generatePieData(al3Values);
            relGraphView.removeAllViews();
            displayPieGraph();

//            mChart.setUsePercentValues(true);
//
//            // change the color of the center-hole
//            // mChart.setHoleColor(Color.rgb(235, 235, 235));
//            mChart.setHoleColorTransparent(true);
//
//           // tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//
//            mChart.setCenterTextTypeface(sessionManager.tfRobotoCondLight);
//
//            //  mChart.setHoleRadius(60f);
//            mChart.setHoleRadius(52f);
//            mChart.setTransparentCircleRadius(57f);
//            mChart.setDescription("");
//            mChart.setDrawCenterText(true);
//            mChart.setDrawHoleEnabled(true);
//            mChart.setRotationAngle(0);
//            // enable rotation of the chart by touch
//            mChart.setRotationEnabled(true);
//
//            // mChart.setUnit(" €");
//            // mChart.setDrawUnitsInChart(true);
//
//            // add a selection listener
//        //    mChart.setOnChartValueSelectedListener(this);
//            // mChart.setTouchEnabled(false);
//
//            mChart.setCenterText("MPAndroidChart\nLibrary");
//
//            setData(3, 100,mChart);
//
//            mChart.animateXY(1500, 1500);
//            // mChart.spin(2000, 0, 360);
//
//            Legend l = mChart.getLegend();
//            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//            l.setXEntrySpace(7f);
//            l.setYEntrySpace(5f);

        } else if (v.getId() == R.id.imgBarGraphInReports) {
            // generateBarData(al3Values);
            relGraphView.removeAllViews();
            displayBarGraph();

        } else if (v.getId() == R.id.imgLineGraphInReports) {
            //   generateLineData(al3Values);
            relGraphView.removeAllViews();
            displayLineGraph();

        } else if (v.getId() == R.id.txtYesterdayInReports) {
            String text = txtYesterday.getText().toString();
            if (text.equalsIgnoreCase("Yesterday")) {
                selectedOption = "Yesterday";
                txtYesterday.setText("Breakfast");
                txtWeek.setText("Lunch");
                txtMonth.setText("Dinner");
                Cursor c = getYesterdayGlucose("Glucometer");
                getGlucoseFromDay(c);

            } else if (text.equalsIgnoreCase("Breakfast")) {
                Cursor c = null;
                txtYesterday.setText("Low");
                txtWeek.setText("Normal");
                txtMonth.setText("High");
                selectedDietOption = "Breakfast";
                if (selectedOption.equalsIgnoreCase("Yesterday")) {
                    c = getYesterDayGlucoseByCat("Noon");
                } else if (selectedOption.equalsIgnoreCase("Week")) {
                    c = getLastWeekGlucoseByCat("Noon");
                } else if (selectedOption.equalsIgnoreCase("Month")) {
                    c = getLastMonthGlucoseByCat("Noon");
                }
                if (c != null) {
                    int size = c.getCount();
                    Log.i("myLog", "size:" + size);
                    if (size > 0)
                        displayGlucoseData(c);
                }
            }


        } else if (v.getId() == R.id.txtWeekInReports) {
            String text = txtWeek.getText().toString();
            if (text.equalsIgnoreCase("Week")) {
                selectedOption = "Week";
                txtYesterday.setText("Breakfast");
                txtWeek.setText("Lunch");
                txtMonth.setText("Dinner");
                Cursor c = getLastWeekGlucose("Glucometer");
                getGlucoseFromDay(c);
            } else if (text.equalsIgnoreCase("Lunch")) {
                selectedDietOption = "Lunch";
                Cursor c = null;
                txtYesterday.setText("Low");
                txtWeek.setText("Normal");
                txtMonth.setText("High");
                Log.i("myLog", "selectedOption:" + selectedOption);
                if (selectedOption.equalsIgnoreCase("Yesterday")) {
                    c = getYesterDayGlucoseByCat("Night");
                } else if (selectedOption.equalsIgnoreCase("Week")) {
                    c = getLastWeekGlucoseByCat("Night");
                } else if (selectedOption.equalsIgnoreCase("Month")) {
                    c = getLastMonthGlucoseByCat("Night");
                }
                if (c != null) {
                    int size = c.getCount();
                    Log.i("myLog", "size:" + size);
                    if (size > 0)
                        displayGlucoseData(c);
                }
            }
        } else if (v.getId() == R.id.txtMonthInReports) {
            String text = txtMonth.getText().toString();
            if (text.equalsIgnoreCase("Month")) {
                selectedOption = "Month";
                txtYesterday.setText("Breakfast");
                txtWeek.setText("Lunch");
                txtMonth.setText("Dinner");
                Cursor c = getLastMonthGlucose("Glucometer");
                getGlucoseFromDay(c);
            } else if (text.equalsIgnoreCase("Dinner")) {
                selectedDietOption = "Dinner";
                Cursor c = null;
                txtYesterday.setText("Low");
                txtWeek.setText("Normal");
                txtMonth.setText("High");
                Log.i("myLog", "selectedOption:" + selectedOption);
                if (selectedOption.equalsIgnoreCase("Yesterday")) {
                    c = getYesterDayGlucoseByCat("Morning");
                } else if (selectedOption.equalsIgnoreCase("Week")) {
                    c = getLastWeekGlucoseByCat("Morning");
                } else if (selectedOption.equalsIgnoreCase("Month")) {
                    c = getLastMonthGlucoseByCat("Morning");
                }
                if (c != null) {
                    int size = c.getCount();
                    Log.i("myLog", "size:" + size);
                    if (size > 0)
                        displayGlucoseData(c);
                }
            }
        }

    }

    /**
     * Method to Initialize size Parameters
     */
    private void initParam() {
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            textSize = 22;
            txtNameParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(80));
            txtNameParam.setMargins(dpToPx(8), 0, dpToPx(8), 0);
            txtLineParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(80));
            trParam = new LinearLayout.LayoutParams(sessionManager.width / 2
                    - dpToPx(240), dpToPx(40));
            trTxtParam = new LinearLayout.LayoutParams(dpToPx(80), dpToPx(40));
            trLineParam = new LinearLayout.LayoutParams(
                    sessionManager.width / 2, dpToPx(1));
            trLineTitleParam = new LinearLayout.LayoutParams(
                    sessionManager.width / 2, dpToPx(2));

        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            textSize = 18;
            txtNameParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    dpToPx(60));
            txtNameParam.setMargins(dpToPx(4), 0, dpToPx(4), 0);
            txtLineParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(60));
            trParam = new LinearLayout.LayoutParams(sessionManager.width / 2
                    - dpToPx(300), dpToPx(50));
            trTxtParam = new LinearLayout.LayoutParams(dpToPx(100), dpToPx(50));
            trLineParam = new LinearLayout.LayoutParams(
                    sessionManager.width / 2, dpToPx(1));

            trLineTitleParam = new LinearLayout.LayoutParams(
                    sessionManager.width / 2, dpToPx(2));

        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            textSize = 16;
            txtNameParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(40));
            txtNameParam.setMargins(dpToPx(3), 0, dpToPx(3), 0);
            txtLineParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(40));
            trParam = new LinearLayout.LayoutParams(sessionManager.width / 2
                    - dpToPx(240), dpToPx(40));
            trTxtParam = new LinearLayout.LayoutParams(dpToPx(80), dpToPx(40));
            trLineParam = new LinearLayout.LayoutParams(
                    sessionManager.width / 2, dpToPx(1));
            trLineTitleParam = new LinearLayout.LayoutParams(
                    sessionManager.width / 2, dpToPx(2));
        }
    }

    private void getPatients() {
        Log.i("myLOg", "getPatients");
        String query = "Select * from " + sessionManager.databaseIf.patientsTbl;
        Cursor c = sessionManager.databaseIf.query(query);
        final int size = c.getCount();
        final ArrayList<Integer> alId = new ArrayList<Integer>();
        Log.i("myLOg", "getPatients size:" + size);
        final TextView[] txtName = new TextView[size];
        TextView[] txtLine = new TextView[size];
        for (int start = 0; start < size; start++) {
            final int index = start;
            txtName[start] = new TextView(sessionManager.context);
            String name = c.getString(3);
            int id = c.getInt(1);
            alId.add(id);
            Log.i("myLOg", "getPatients id:" + id);
            if (id == sessionManager.selPatientId) {
                txtName[start].setTextColor(Color.WHITE);
            } else {
                txtName[start].setTextColor(Color.BLACK);
            }
            txtName[start].setText(name);
            Log.i("myLOg", "getPatients name:" + name);
            txtName[start].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

            txtName[start].setGravity(Gravity.CENTER);
            txtName[start].setTypeface(sessionManager.tfRobotoCondLight);
            txtLine[start] = new TextView(sessionManager.context);
            txtLine[start].setText("  |  ");
            txtLine[start].setGravity(Gravity.CENTER);
            txtLine[start]
                    .setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize + 3);
            txtLine[start].setTextColor(Color.WHITE);
            linearPatients.addView(txtName[start], txtNameParam);
            linearPatients.addView(txtLine[start], txtLineParam);
            c.moveToNext();
            txtName[start].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    for (int i = 0; i < size; i++) {
                        if (index == i) {
                            txtName[i].setTextColor(Color.WHITE);
                        } else {
                            txtName[i].setTextColor(Color.BLACK);
                        }
                    }
                    sessionManager.selPatientId = alId.get(index);
                }
            });
        }
    }

    /**
     * Method to convert dp to px values
     *
     * @return rounded dp value
     */
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void pieChart(String title) {
        txtTitle.setText(title + " Readings");
        linearGraphDevices.removeAllViews();
        LinearLayout linear1 = new LinearLayout(sessionManager.context);
        linear1.setOrientation(LinearLayout.HORIZONTAL);
        TextView txtTitle = new TextView(sessionManager.context);
        txtTitle.setText(title);
        txtTitle.setTypeface(sessionManager.tfRobotoCondLight, Typeface.BOLD);
        txtTitle.setGravity(Gravity.CENTER_VERTICAL);
        txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        TextView txtYesterday = new TextView(sessionManager.context);
        txtYesterday.setText("Yesterday");
        txtYesterday.setBackgroundColor(Color.parseColor("#F29EB7"));
        txtYesterday.setTypeface(sessionManager.tfRobotoCondLight,
                Typeface.BOLD);
        txtYesterday.setGravity(Gravity.CENTER);
        txtYesterday.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        TextView txtWeek = new TextView(sessionManager.context);
        txtWeek.setText("Week");
        txtWeek.setBackgroundColor(Color.parseColor("#EF7A91"));
        txtWeek.setTypeface(sessionManager.tfRobotoCondLight, Typeface.BOLD);
        txtWeek.setGravity(Gravity.CENTER);
        txtWeek.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        TextView txtMonth = new TextView(sessionManager.context);
        txtMonth.setText("Month");
        txtMonth.setBackgroundColor(Color.parseColor("#D15780"));
        txtMonth.setTypeface(sessionManager.tfRobotoCondLight, Typeface.BOLD);
        txtMonth.setGravity(Gravity.CENTER);
        txtMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        linear1.addView(txtTitle, trParam);
        linear1.addView(txtYesterday, trTxtParam);
        linear1.addView(txtWeek, trTxtParam);
        linear1.addView(txtMonth, trTxtParam);
        linearGraphDevices.addView(linear1);
        LinearLayout linear2 = new LinearLayout(sessionManager.context);
        linear2.setOrientation(LinearLayout.HORIZONTAL);
        TextView txtLine1 = new TextView(sessionManager.context);
        txtLine1.setBackgroundColor(Color.parseColor("#A94062"));
        linear2.addView(txtLine1, trLineParam);
        linearGraphDevices.addView(linear2);
        LinearLayout linear3 = new LinearLayout(sessionManager.context);
        TextView txtAvgHeading = new TextView(sessionManager.context);
        txtAvgHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        txtAvgHeading.setText("Average :");
        txtAvgHeading.setPadding(dpToPx(5), 0, 0, 0);
        txtAvgHeading.setGravity(Gravity.CENTER_VERTICAL);
        txtAvgHeading.setTypeface(sessionManager.tfRobotoCondLight);
        linear3.addView(txtAvgHeading, trParam);
        TextView txtAvgYesterday = new TextView(sessionManager.context);
        // txtAvgYesterday.setText(String.valueOf(avg));
        // txtAvgYesterday.setBackgroundColor(Color.parseColor("#F29EB7"));
        txtAvgYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        txtAvgYesterday.setGravity(Gravity.CENTER);
        txtAvgYesterday.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        linear3.addView(txtAvgYesterday, trTxtParam);
        TextView txtAvgWeek = new TextView(sessionManager.context);
        // txtAvgWeek.setText(String.valueOf(weekAvg));
        // txtAvgWeek.setBackgroundColor(Color.parseColor("#EF7A91"));
        txtAvgWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtAvgWeek.setGravity(Gravity.CENTER);
        txtAvgWeek.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        linear3.addView(txtAvgWeek, trTxtParam);
        TextView txtAvgMonth = new TextView(sessionManager.context);
        // txtAvgMonth.setText(String.valueOf(monthAvg));
        // txtAvgMonth.setBackgroundColor(Color.parseColor("#D15780"));
        txtAvgMonth.setGravity(Gravity.CENTER);
        txtAvgMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtAvgMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        linear3.addView(txtAvgMonth, trTxtParam);
        linearGraphDevices.addView(linear3);
        LinearLayout linear4 = new LinearLayout(sessionManager.context);
        linear4.setOrientation(LinearLayout.HORIZONTAL);
        TextView txtLine2 = new TextView(sessionManager.context);
        txtLine2.setBackgroundColor(Color.parseColor("#A94062"));
        linear4.addView(txtLine2, trLineParam);
        linearGraphDevices.addView(linear4);

        LinearLayout linear5 = new LinearLayout(sessionManager.context);
        TextView txtAvgPercent = new TextView(sessionManager.context);
        txtAvgPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        txtAvgPercent.setText("Percent :");
        txtAvgPercent.setPadding(dpToPx(5), 0, 0, 0);
        txtAvgPercent.setGravity(Gravity.CENTER_VERTICAL);
        txtAvgPercent.setTypeface(sessionManager.tfRobotoCondLight);
        linear5.addView(txtAvgPercent, trParam);
        TextView txtPercentYesterday = new TextView(sessionManager.context);
        // txtPercentYesterday.setText(percent + "%");
        txtPercentYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        txtPercentYesterday.setGravity(Gravity.CENTER);
        txtPercentYesterday.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        linear5.addView(txtPercentYesterday, trTxtParam);
        TextView txtPercentWeek = new TextView(sessionManager.context);
        // txtPercentWeek.setText(weekPercent + "%");
        txtPercentWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtPercentWeek.setGravity(Gravity.CENTER);
        txtPercentWeek.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        linear5.addView(txtPercentWeek, trTxtParam);
        TextView txtPercentMonth = new TextView(sessionManager.context);
        // txtPercentMonth.setText(monthPercent + "%");
        txtPercentMonth.setGravity(Gravity.CENTER);
        txtPercentMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtPercentMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        linear5.addView(txtPercentMonth, trTxtParam);
        linearGraphDevices.addView(linear5);
        LinearLayout linear6 = new LinearLayout(sessionManager.context);
        TextView txtLine3 = new TextView(sessionManager.context);
        txtLine3.setBackgroundColor(Color.parseColor("#A94062"));
        linear6.addView(txtLine3, trLineParam);
        linearGraphDevices.addView(linear6);

        LinearLayout linear7 = new LinearLayout(sessionManager.context);
        TextView txtStdDevHeading = new TextView(sessionManager.context);
        txtStdDevHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        txtStdDevHeading.setText("Std Dev :");
        txtStdDevHeading.setPadding(dpToPx(5), 0, 0, 0);
        txtStdDevHeading.setGravity(Gravity.CENTER_VERTICAL);
        txtStdDevHeading.setTypeface(sessionManager.tfRobotoCondLight);
        linear7.addView(txtStdDevHeading, trParam);
        TextView txtStdDevYesterday = new TextView(sessionManager.context);
        // double stdDeviate = getStdDev(glucoseYesAL);
        // String valStdDev = String.format("%.2f", stdDeviate);
        // txtStdDevYesterday.setText(valStdDev);
        txtStdDevYesterday.setGravity(Gravity.CENTER);
        txtStdDevYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        txtStdDevYesterday.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        linear7.addView(txtStdDevYesterday, trTxtParam);
        TextView txtStdDevWeek = new TextView(sessionManager.context);
        // double weekStdDeviate = getStdDev(glucoseWeekAL);
        // String weekStdDev = String.format("%.2f", weekStdDeviate);
        // txtStdDevWeek.setText(weekStdDev);
        txtStdDevWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtStdDevWeek.setGravity(Gravity.CENTER);
        txtStdDevWeek.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        linear7.addView(txtStdDevWeek, trTxtParam);
        TextView txtStdDevMonth = new TextView(sessionManager.context);
        // double monthStdDeviate = getStdDev(glucoseMonthAL);
        // String monthStdDev = String.format("%.2f", monthStdDeviate);
        // txtStdDevMonth.setText(monthStdDev);
        txtStdDevMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtStdDevMonth.setGravity(Gravity.CENTER);
        txtStdDevMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        linear7.addView(txtStdDevMonth, trTxtParam);
        linearGraphDevices.addView(linear7);
    }

    public void showPieChart(String device) {
        glucoseYesAL = new ArrayList<Double>();
        glucoseMonthAL = new ArrayList<Double>();
        glucoseWeekAL = new ArrayList<Double>();
        linearGraphDevices.removeAllViews();
        SVG svg = null;
        try {
            svg = SVGParser.getSVGFromAsset(sessionManager.context.getAssets(),
                    "svg/glucometerpink.svg");
        } catch (SVGParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LinearLayout li = new LinearLayout(sessionManager.context);
        li.setBackgroundColor(Color.parseColor("#F29EB7"));
        // li.setBackgroundColor(Color.parseColor("#A94062"));
        Drawable svgGlucoMeter = svg.createPictureDrawable();
        ImageView img = new ImageView(sessionManager.context);
        img.setImageDrawable(svgGlucoMeter);
        li.addView(img, new LinearLayout.LayoutParams(dpToPx(45), dpToPx(45)));
        TextView txtTitle = new TextView(sessionManager.context);
        txtTitle.setText("  BLOOD GLUCOSE READINGS");
        txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        txtTitle.setGravity(Gravity.CENTER_VERTICAL);
        txtTitle.setTypeface(sessionManager.tfRobotoCondLight, Typeface.BOLD);
        li.addView(txtTitle, new LinearLayout.LayoutParams(
                sessionManager.width / 2, dpToPx(45)));
        linearGraphDevices.addView(li, new LinearLayout.LayoutParams(
                sessionManager.width / 2, dpToPx(45)));
        TextView txtLineTitle = new TextView(sessionManager.context);
        txtLineTitle.setBackgroundColor(Color.parseColor("#A94062"));
        linearGraphDevices.addView(txtLineTitle, new LinearLayout.LayoutParams(
                sessionManager.width / 2, dpToPx(4)));
        Cursor c = getYesterdayGlucose(device);
        int size = c.getCount();
        Double sum = 0.0;
        Log.i("myLog", "Size:" + size);
        for (int start = 0; start < size; start++) {
            Double val = c.getDouble(6);
            sum = sum + val;
            glucoseYesAL.add(val);
            c.moveToNext();
        }
        c.close();
        final int avg = (int) (sum / size);
        int percent = (avg * 100) / 400;
        Cursor cWeek = getLastWeekGlucose(device);
        int weekSize = cWeek.getCount();
        Double weekSum = 0.0;
        Log.i("myLog", "weekSize:" + weekSize);
        for (int start = 0; start < weekSize; start++) {
            Double val = cWeek.getDouble(6);
            weekSum = weekSum + val;
            glucoseWeekAL.add(val);
            cWeek.moveToNext();
        }
        cWeek.close();
        final int weekAvg = (int) (weekSum / weekSize);
        int weekPercent = (weekAvg * 100) / 400;

        Cursor cMonth = getLastMonthGlucose(device);
        int monthSize = cMonth.getCount();
        Double monthSum = 0.0;
        Log.i("myLog", "monthSize:" + monthSize);
        for (int start = 0; start < monthSize; start++) {
            Double val = cMonth.getDouble(6);
            monthSum = monthSum + val;
            glucoseMonthAL.add(val);
            cMonth.moveToNext();
        }
        cMonth.close();
        final int monthAvg = (int) (monthSum / monthSize);
        int monthPercent = (monthAvg * 100) / 400;
        tblLayout = new TableLayout(sessionManager.context);
        TableRow tr = new TableRow(sessionManager.context);
        TextView txtHeading = new TextView(sessionManager.context);
        txtHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        txtHeading.setText("Glucose");
        txtHeading.setPadding(dpToPx(5), 0, 0, 0);
        txtHeading.setTypeface(sessionManager.tfRobotoCondLight, Typeface.BOLD);

        tr.addView(txtHeading, trParam);
        TextView txtYesterday = new TextView(sessionManager.context);
        txtYesterday.setText("Yesterday");
        txtYesterday.setPadding(dpToPx(5), 0, 0, 0);
        // txtYesterday.setBackgroundColor(Color.parseColor("#F29EB7"));
        txtYesterday.setTypeface(sessionManager.tfRobotoCondLight,
                Typeface.BOLD);
        txtYesterday.setGravity(Gravity.FILL);
        txtYesterday.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tr.addView(txtYesterday, trTxtParam);
        TextView txtWeek = new TextView(sessionManager.context);
        txtWeek.setText("Week");
        txtWeek.setBackgroundColor(Color.parseColor("#EF7A91"));
        txtWeek.setTypeface(sessionManager.tfRobotoCondLight, Typeface.BOLD);
        txtWeek.setGravity(Gravity.FILL);
        txtWeek.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tr.addView(txtWeek, trTxtParam);
        TextView txtMonth = new TextView(sessionManager.context);
        txtMonth.setText("Month");
        txtMonth.setBackgroundColor(Color.parseColor("#D15780"));
        txtMonth.setGravity(Gravity.FILL);
        txtMonth.setTypeface(sessionManager.tfRobotoCondLight, Typeface.BOLD);
        txtMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tr.addView(txtMonth, trTxtParam);
        tblLayout.addView(tr);
        tr.setBaselineAligned(false);

        // TableRow trColor = new TableRow(sessionManager.context);
        // TextView txtEmpty = new TextView(sessionManager.context);
        // trColor.addView(txtEmpty);
        // Button btnYes = new Button(sessionManager.context);
        // btnYes.setBackgroundColor(Color.parseColor("#F29EB7"));
        // Button btnWeek = new Button(sessionManager.context);
        // btnWeek.setBackgroundColor(Color.parseColor("#EF7A91"));
        // Button btnMonth = new Button(sessionManager.context);
        // btnMonth.setBackgroundColor(Color.parseColor("#D15780"));
        // trColor.addView(btnYes);
        // trColor.addView(btnWeek);
        // trColor.addView(btnMonth);
        // tblLayout.addView(trColor);

        TableRow trTitleLine = new TableRow(sessionManager.context);
        TextView txtTitleLine = new TextView(sessionManager.context);
        txtTitleLine.setBackgroundColor(Color.parseColor("#A94062"));
        trTitleLine.addView(txtTitleLine, trLineTitleParam);
        tblLayout.addView(trTitleLine);

        TableRow trVal = new TableRow(sessionManager.context);
        TextView txtAvgHeading = new TextView(sessionManager.context);
        txtAvgHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        txtAvgHeading.setText("Average :");
        txtAvgHeading.setPadding(dpToPx(5), 0, 0, 0);
        txtAvgHeading.setGravity(Gravity.CENTER_VERTICAL);
        txtAvgHeading.setTypeface(sessionManager.tfRobotoCondLight);
        trVal.addView(txtAvgHeading, trParam);
        TextView txtAvgYesterday = new TextView(sessionManager.context);
        txtAvgYesterday.setText(String.valueOf(avg));
        // txtAvgYesterday.setBackgroundColor(Color.parseColor("#F29EB7"));
        txtAvgYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        txtAvgYesterday.setGravity(Gravity.CENTER);
        txtAvgYesterday.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        trVal.addView(txtAvgYesterday, trTxtParam);
        TextView txtAvgWeek = new TextView(sessionManager.context);
        txtAvgWeek.setText(String.valueOf(weekAvg));
        // txtAvgWeek.setBackgroundColor(Color.parseColor("#EF7A91"));
        txtAvgWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtAvgWeek.setGravity(Gravity.CENTER);
        txtAvgWeek.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        trVal.addView(txtAvgWeek, trTxtParam);
        TextView txtAvgMonth = new TextView(sessionManager.context);
        txtAvgMonth.setText(String.valueOf(monthAvg));
        // txtAvgMonth.setBackgroundColor(Color.parseColor("#D15780"));
        txtAvgMonth.setGravity(Gravity.CENTER);
        txtAvgMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtAvgMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        trVal.addView(txtAvgMonth, trTxtParam);
        tblLayout.addView(trVal);
        TableRow trTitleLine1 = new TableRow(sessionManager.context);
        TextView txtTitleLine1 = new TextView(sessionManager.context);
        txtTitleLine1.setBackgroundColor(Color.parseColor("#A94062"));
        trTitleLine1.addView(txtTitleLine1, trLineParam);
        tblLayout.addView(trTitleLine1);
        TableRow trPercent = new TableRow(sessionManager.context);
        TextView txtAvgPercent = new TextView(sessionManager.context);
        txtAvgPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        txtAvgPercent.setText("Percent :");
        txtAvgPercent.setPadding(dpToPx(5), 0, 0, 0);
        txtAvgPercent.setGravity(Gravity.CENTER_VERTICAL);
        txtAvgPercent.setTypeface(sessionManager.tfRobotoCondLight);
        trPercent.addView(txtAvgPercent, trParam);
        TextView txtPercentYesterday = new TextView(sessionManager.context);
        txtPercentYesterday.setText(percent + "%");
        txtPercentYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        txtPercentYesterday.setGravity(Gravity.CENTER);
        txtPercentYesterday.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        trPercent.addView(txtPercentYesterday, trTxtParam);
        TextView txtPercentWeek = new TextView(sessionManager.context);
        txtPercentWeek.setText(weekPercent + "%");
        txtPercentWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtPercentWeek.setGravity(Gravity.CENTER);
        txtPercentWeek.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        trPercent.addView(txtPercentWeek, trTxtParam);
        TextView txtPercentMonth = new TextView(sessionManager.context);
        txtPercentMonth.setText(monthPercent + "%");
        txtPercentMonth.setGravity(Gravity.CENTER);
        txtPercentMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtPercentMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        trPercent.addView(txtPercentMonth, trTxtParam);
        tblLayout.addView(trPercent);
        TableRow trTitleLinePercent = new TableRow(sessionManager.context);
        TextView txtTitleLinePercent = new TextView(sessionManager.context);
        txtTitleLinePercent.setBackgroundColor(Color.parseColor("#A94062"));
        trTitleLinePercent.addView(txtTitleLinePercent, trLineParam);
        tblLayout.addView(trTitleLinePercent);

        TableRow trValStdDev = new TableRow(sessionManager.context);
        TextView txtStdDevHeading = new TextView(sessionManager.context);
        txtStdDevHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        txtStdDevHeading.setText("Std Dev :");
        txtStdDevHeading.setPadding(dpToPx(5), 0, 0, 0);
        txtStdDevHeading.setGravity(Gravity.CENTER_VERTICAL);
        txtStdDevHeading.setTypeface(sessionManager.tfRobotoCondLight);
        trValStdDev.addView(txtStdDevHeading, trParam);
        TextView txtStdDevYesterday = new TextView(sessionManager.context);
        double stdDeviate = getStdDev(glucoseYesAL);
        String valStdDev = String.format("%.2f", stdDeviate);
        txtStdDevYesterday.setText(valStdDev);
        txtStdDevYesterday.setGravity(Gravity.CENTER);
        txtStdDevYesterday.setTypeface(sessionManager.tfRobotoCondLight);
        txtStdDevYesterday.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        trValStdDev.addView(txtStdDevYesterday, trTxtParam);
        TextView txtStdDevWeek = new TextView(sessionManager.context);
        double weekStdDeviate = getStdDev(glucoseWeekAL);
        String weekStdDev = String.format("%.2f", weekStdDeviate);
        txtStdDevWeek.setText(weekStdDev);
        txtStdDevWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtStdDevWeek.setGravity(Gravity.CENTER);
        txtStdDevWeek.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        trValStdDev.addView(txtStdDevWeek, trTxtParam);
        TextView txtStdDevMonth = new TextView(sessionManager.context);
        double monthStdDeviate = getStdDev(glucoseMonthAL);
        String monthStdDev = String.format("%.2f", monthStdDeviate);
        txtStdDevMonth.setText(monthStdDev);
        txtStdDevMonth.setTypeface(sessionManager.tfRobotoCondLight);
        txtStdDevMonth.setGravity(Gravity.CENTER);
        txtStdDevMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        trValStdDev.addView(txtStdDevMonth, trTxtParam);
        tblLayout.addView(trValStdDev);
        linearGraphDevices.addView(tblLayout, new LinearLayout.LayoutParams(
                sessionManager.width, LinearLayout.LayoutParams.WRAP_CONTENT));
        webView = new WebView(sessionManager.context);
        webView.getSettings().setJavaScriptEnabled(true);
        // String summary =
        // "<html> <head> <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script> <script type=\"text/javascript\">google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});google.setOnLoadCallback(drawChart);function drawChart() {var data = google.visualization.arrayToDataTable([['Task', 'Hours per Day'],['Work',     11],['Eat',      2],['Commute',  2],['Watch TV', 2],['Sleep',    7]);var options = {title: 'My Daily Activities'};var chart = new google.visualization.PieChart(document.getElementById('piechart'));"
        // +
        // "chart.draw(data, options);}</script></head><body> <div id=\"piechart\" style=\"width: 900px; height: 500px;\"></div></body></html>";
        // webView.loadData(summary, "text/html", "UTF-8");

        // final String URL = "file:///android_asset/piechart_donut.html";
        final String URL = "file:///android_asset/2.html";
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                webView.removeAllViews();
                webView.loadUrl("javascript:load()");
                webView.loadUrl("javascript:test(" + avg + "," + weekAvg + ","
                        + monthAvg + ")");
                // webView.loadUrl("javascript:draw(" + avg + "," + weekAvg +
                // ","
                // + monthAvg + ")");

            }
        });

        webView.loadUrl(URL);
        linearGraphDevices.addView(webView,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
        txtWeek.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // selectedOption = "Week";
                // Cursor cWeek = getLastWeekGlucose();
                // getGlucoseFromDay(cWeek);
            }
        });
        txtMonth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // selectedOption = "Month";
                // Cursor c = getLastMonthGlucose();
                // getGlucoseFromDay(c);
            }
        });
        txtYesterday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // selectedOption = "Yesterday";
                // Cursor c = getYesterdayGlucose();
                // getGlucoseFromDay(c);
            }
        });

    }

    public Cursor getYesterdayGlucose(String device) {
        Log.i("myLog", "getYesterdayGlucose");
        glucoseYesAL = new ArrayList<Double>();
        String selQuery = "SELECT * from "
                + sessionManager.databaseIf.deviceTbl + " where "
                + sessionManager.databaseIf.deviceDate
                + " = (select max(date) from "
                + sessionManager.databaseIf.deviceTbl
                + " WHERE date < DATE('now')) AND "
                + sessionManager.databaseIf.deviceName + "='" + device
                + "' AND " + sessionManager.databaseIf.devicePatientId + "="
                + sessionManager.selPatientId;
        Log.i("myLog", " getYesterdayGlucose selQuery:" + selQuery);
        Cursor c = sessionManager.databaseIf.query(selQuery);
        int size = c.getCount();
        return c;
        // String query = "SELECT * FROM "
        // + controller.databaseIf.deviceTbl
        // +
        // " WHERE date BETWEEN datetime('now', '-1 day') AND datetime('now', 'localtime')";
        // Log.i("myLog", "selQuery1:" + selQuery);
        // Cursor c1 = controller.databaseIf.query(query);
        // int size1 = c1.getCount();
        // Log.i("myLog", "Size1:" + size1);
    }

    public Cursor getLastWeekGlucose(String device) {
        glucoseWeekAL = new ArrayList<Double>();
        String str = "SELECT * FROM "
                + sessionManager.databaseIf.deviceTbl
                + " WHERE "
                + sessionManager.databaseIf.deviceName
                + "='"
                + device
                + "' AND date BETWEEN datetime('now', '-7 days') AND datetime('now', 'localtime') AND "
                + sessionManager.databaseIf.devicePatientId + "="
                + sessionManager.selPatientId;
        Log.i("myLog", "getLastWeekGlucose Query:" + str);
        Cursor c = sessionManager.databaseIf.query(str);
        return c;
    }

    public Cursor getLastMonthGlucose(String device) {
        glucoseMonthAL = new ArrayList<Double>();
        String str2 = "SELECT * FROM "
                + sessionManager.databaseIf.deviceTbl
                + " WHERE "
                + sessionManager.databaseIf.deviceName
                + "='"
                + device
                + "' AND date BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime') AND "
                + sessionManager.databaseIf.devicePatientId + "="
                + sessionManager.selPatientId;
        Log.i("myLog", "getLastMonthGlucose query:" + str2);
        Cursor c = sessionManager.databaseIf.query(str2);
        return c;
    }

    double getStdDev(ArrayList<Double> data) {
        // Log.i("myLog", "getStdDev");
        return Math.sqrt(getVariance(data));
    }

    double getMean(ArrayList<Double> data) {
        // Log.i("myLog", "getMean");
        double sum = 0.0;
        int size = data.size();
        for (double a : data)
            sum += a;
        // Log.i("myLog", "sum:" + sum + " size:" + size);
        return sum / size;
    }

    double getVariance(ArrayList<Double> data) {
        // Log.i("myLog", "getVariance");
        double mean = getMean(data);
        // Log.i("myLog", "mean:" + mean);
        int size = data.size();
        double temp = 0;
        for (double a : data) {
            temp += (mean - a) * (mean - a);
            // Log.i("myLog", "a:" + a + "   temp:" + temp);
        }
        // Log.i("myLog", "temp:" + temp + "  size:" + size);
        return temp / size;
    }

    public void getDeviceReading(String device) {
        Cursor c = getYesterdayGlucose(device);
        int size = c.getCount();
        Double sum = 0.0;
        Log.i("myLog", "Size:" + size);
        for (int start = 0; start < size; start++) {
            Double val = c.getDouble(6);
            sum = sum + val;
            glucoseYesAL.add(val);
            c.moveToNext();
        }
        c.close();
        final int avg = (int) (sum / size);
        int percent = (avg * 100) / 400;
        Cursor cWeek = getLastWeekGlucose(device);
        int weekSize = cWeek.getCount();
        Double weekSum = 0.0;
        Log.i("myLog", "weekSize:" + weekSize);
        for (int start = 0; start < weekSize; start++) {
            Double val = cWeek.getDouble(6);
            weekSum = weekSum + val;
            glucoseWeekAL.add(val);
            cWeek.moveToNext();
        }
        cWeek.close();
        final int weekAvg = (int) (weekSum / weekSize);
        int weekPercent = (weekAvg * 100) / 400;

        Cursor cMonth = getLastMonthGlucose(device);
        int monthSize = cMonth.getCount();
        Double monthSum = 0.0;
        Log.i("myLog", "monthSize:" + monthSize);
        for (int start = 0; start < monthSize; start++) {
            Double val = cMonth.getDouble(6);
            monthSum = monthSum + val;
            glucoseMonthAL.add(val);
            cMonth.moveToNext();
        }
        cMonth.close();
        final int monthAvg = (int) (monthSum / monthSize);
        int monthPercent = (monthAvg * 100) / 400;

        double stdDeviate = getStdDev(glucoseYesAL);
        String valStdDev = String.format("%.2f", stdDeviate);
        double stdDeviateWeek = getStdDev(glucoseWeekAL);
        String valStdDevWeek = String.format("%.2f", stdDeviateWeek);
        double stdDeviateMonth = getStdDev(glucoseMonthAL);
        String valStdDevMonth = String.format("%.2f", stdDeviateMonth);

        txtYesterday.setText("Yesterday");
        txtWeek.setText("Week");
        txtMonth.setText("Month");

        txtAvgYesterday.setText(String.valueOf(avg));
        txtAvgWeek.setText(String.valueOf(weekAvg));
        txtAvgMonth.setText(String.valueOf(monthAvg));

        txtPercentYesterday.setText(String.valueOf(percent));
        txtPercentWeek.setText(String.valueOf(weekPercent));
        txtPercentMonth.setText(String.valueOf(monthPercent));

        txtStdDevYesterday.setText(valStdDev);
        txtStdDevWeek.setText(valStdDevWeek);
        txtStdDevMonth.setText(valStdDevMonth);
        al3Values = new ArrayList<Integer>();
        al3Values.add(avg);
        al3Values.add(weekAvg);
        al3Values.add(monthAvg);


    }

    private void refreshTextViews() {
        txtAvgYesterday.setText("");
        txtAvgWeek.setText("");
        txtAvgMonth.setText("");

        txtPercentYesterday.setText("");
        txtPercentWeek.setText("");
        txtPercentMonth.setText("");

        txtStdDevYesterday.setText("");
        txtStdDevWeek.setText("");
        txtStdDevMonth.setText("");
    }

    protected PieData generatePieData() {

        int count = 4;

        ArrayList<Entry> entries1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Quarter 1");
        xVals.add("Quarter 2");
        xVals.add("Quarter 3");
        xVals.add("Quarter 4");

        for (int i = 0; i < count; i++) {
            xVals.add("entry" + (i + 1));
            entries1.add(new Entry((float) (Math.random() * 60) + 40, i));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2014");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);

        PieData d = new PieData(xVals, ds1);
        d.setValueTypeface(sessionManager.tfRobotoCondLight);
        return d;
    }

    private void generatePieData(ArrayList<Integer> chartValue) {
        relGraphView.removeAllViews();
        PieChartView chart = new PieChartView(sessionManager.context);
        relGraphView.addView(chart, android.widget.LinearLayout.LayoutParams.FILL_PARENT,
                android.widget.LinearLayout.LayoutParams.FILL_PARENT);
        int numValues = chartValue.size();
        Log.i("myLog", "  Sizeeeeee:" + numValues);
        chart.setCircleFillRatio(1.0f);
        boolean hasLabels = true;
        boolean hasLabelsOutside = false;
        boolean hasCenterCircle = true;
        boolean hasCenterText1 = false;
        boolean hasCenterText2 = false;
        boolean isExploaded = false;
        boolean hasArcSeparated = false;
        boolean hasLabelForSelected = false;

        List<SliceValue> values = new ArrayList<SliceValue>();
        int[] str = new int[]{Color.parseColor("#F29EB7"), Color.parseColor("#EF7A91"), Color.parseColor("#D15780")};
        for (int i = 0; i < numValues; ++i) {
            Log.i("myLog", "  chartValue.get(i):" + chartValue.get(i));
            SliceValue sliceValue = new SliceValue((float) chartValue.get(i), str[i]);
            values.add(sliceValue);
            //   if (isExploaded) {
            //     sliceValue.setSliceSpacing(24);
            //  }

            //   if (hasArcSeparated && i == 0) {
            //       sliceValue.setSliceSpacing(32);
            //   }

        }

        PieChartData data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (hasCenterText1) {
            data.setCenterText1("Hello!");

            // Get roboto-italic font.
            Typeface tf = Typeface.createFromAsset(sessionManager.context.getAssets(), "Roboto-Italic.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) 42));
        }

        if (hasCenterText2) {
            data.setCenterText2("Charts (Roboto Italic)");
            Typeface tf = Typeface.createFromAsset(sessionManager.context.getAssets(), "Roboto-Italic.ttf");
            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) 16));
        }
        chart.setPieChartData(data);
    }

    private void generateBarData(ArrayList<Integer> al3Values) {
        relGraphView.removeAllViews();
        ColumnChartView chart = new ColumnChartView(sessionManager.context);
        ColumnChartData data;
        boolean hasAxes = true;
        boolean hasAxesNames = true;
        int numSubcolumns = 1;
        int numColumns = al3Values.size();
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        int[] str = new int[]{Color.parseColor("#F29EB7"), Color.parseColor("#EF7A91"), Color.parseColor("#D15780")};
        List<AxisValue> axisValuesForX = new ArrayList<AxisValue>();
        for (int i = 0; i < numColumns; ++i) {
            axisValuesForX.add(new AxisValue(i));
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(al3Values.get(i), str[i % 3]));
            }
            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
        }
        ArrayList<String> al = new ArrayList<String>();
        data = new ColumnChartData(columns);
        chart.setScrollEnabled(true);
        if (hasAxes) {

            Axis axisX = new Axis(axisValuesForX);
            Axis axisY = new Axis();
            //  axisX.setLineColor(Color.TRANSPARENT);

            if (hasAxesNames) {
                axisY.setName("Level");
                axisX.setName("Days");
                //  axisX.setHasTiltedLabels(false);
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        //  data.setBaseValue(8);
        chart.setColumnChartData(data);
        relGraphView.addView(chart, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void generateLineData(ArrayList<Integer> al3Values) {
        relGraphView.removeAllViews();
        generateLineValues(al3Values);
        int numberOfLines = 1;
        int maxNumberOfLines = 4;
        int numberOfPoints = 3;
        // float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
        boolean hasAxes = true;
        boolean hasAxesNames = true;
        boolean hasLines = true;
        boolean hasPoints = true;
        ValueShape shape = ValueShape.CIRCLE;
        boolean isFilled = false;
        boolean hasLabels = false;
        boolean isCubic = false;
        boolean hasLabelForSelected = false;
        int[] str = new int[]{Color.parseColor("#F29EB7"), Color.parseColor("#EF7A91"), Color.parseColor("#D15780")};
        LineChartView lineChart = new LineChartView(sessionManager.context);
        List<Line> lines = new ArrayList<Line>();

        String[] days = new String[]{"Yesterday", "Week", "Month"};
        List<AxisValue> axisValuesForX = new ArrayList<AxisValue>();


        List<AxisValue> axisValuesForY = new ArrayList<AxisValue>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                axisValuesForX.add(new AxisValue(j));
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Line line = new Line(values);
            line.setColor(str[i % 3]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            lines.add(line);
        }
        LineChartData data = new LineChartData(lines);
        if (hasAxes) {
            Axis axisX = new Axis(axisValuesForX);
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Days");
                axisY.setName("Level");

            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        data.setBaseValue(Float.NEGATIVE_INFINITY);

        lineChart.setLineChartData(data);
        relGraphView.addView(lineChart, android.widget.LinearLayout.LayoutParams.FILL_PARENT,
                android.widget.LinearLayout.LayoutParams.FILL_PARENT);
        lineChart.setViewportCalculationEnabled(false);
        lineChart.setZoomEnabled(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
//        final Viewport v = new Viewport(lineChart.getMaximumViewport());
//        v.bottom = 0;
//        v.top = 400;
//        v.left = 0;
//        v.right = numberOfPoints - 1;
//        lineChart.setMaximumViewport(v);
//        lineChart.setCurrentViewport(v);
    }

    private void generateLineValues(ArrayList<Integer> al3Values) {
        int numberOfLines = 1;
        int maxNumberOfLines = 1;
        int numberOfPoints = al3Values.size();
        randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) al3Values.get(j);
            }
        }
    }

    public void getGlucoseFromDay(Cursor c) {
        relGraphView.removeAllViews();
        ArrayList<Double> alBreak = new ArrayList<Double>();
        ArrayList<Double> alLunch = new ArrayList<Double>();
        ArrayList<Double> alDinner = new ArrayList<Double>();
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        int size = c.getCount();
        Log.i("myLog", "size:" + size);
        for (int index = 0; index < size; index++) {
            String cat = c.getString(10);
            // Log.i("myLog", "cat:" + cat);
            double value = c.getDouble(6);
            // Log.i("myLog", "value:" + value);
            if (cat.equalsIgnoreCase("Morning")) {
                alBreak.add(value);
                sum1 = sum1 + value;
            } else if (cat.equalsIgnoreCase("Noon")) {
                alLunch.add(value);
                sum2 = sum2 + value;
            } else if (cat.equalsIgnoreCase("Night")) {
                alDinner.add(value);
                sum3 = sum3 + value;
            }
            c.moveToNext();
        }
        c.close();
        Log.i("myLog", "outside for");
        Log.i("myLog", "alBreak.size():" + alBreak.size() + "  alLunch.size():"
                + alLunch.size() + " alDinner.size():" + alDinner.size());
        double stdDeviation1, stdDeviation2, stdDeviation3;
        int avg1 = 0;
        if (sum1 > 0) {
            avg1 = (int) (sum1 / alBreak.size());
        }
        int percent1 = 0;
        if (avg1 > 0) {
            percent1 = (avg1 * 100) / 400;
        }
        stdDeviation1 = getStdDev(alBreak);
        String stdDev1 = String.format("%.2f", stdDeviation1);
        Log.i("myLog", "sum1:" + sum1 + " avg1:" + avg1 + "  percent1:"
                + percent1);
        int avg2 = 0;
        if (sum2 > 0) {
            avg2 = (int) (sum2 / alLunch.size());
        }
        int percent2 = 0;
        if (avg2 > 0) {
            percent2 = (avg2 * 100) / 400;
        }
        stdDeviation2 = getStdDev(alLunch);
        String stdDev2 = String.format("%.2f", stdDeviation2);
        Log.i("myLog", "sum2:" + sum2 + " avg2:" + avg2 + "  percent2:"
                + percent2);
        int avg3 = 0;
        if (sum3 > 0) {
            avg3 = (int) (sum3 / alDinner.size());
        }
        int percent3 = 0;
        if (percent3 > 0) {
            percent3 = (avg3 * 100) / 400;
        }
        stdDeviation3 = getStdDev(alDinner);
        String stdDev3 = String.format("%.2f", stdDeviation3);
        Log.i("myLog", "sum3:" + sum3 + " avg3:" + avg3 + "  percent3:"
                + percent3);
//        tblLayout.removeAllViews();
        // tblLayout = new TableLayout(ReportMenu.this);

        al3Values = new ArrayList<Integer>();
        al3Values.add(avg1);
        al3Values.add(avg2);
        al3Values.add(avg3);

        txtAvgYesterday.setText(String.valueOf(avg1));
        txtAvgWeek.setText(String.valueOf(avg2));
        txtAvgMonth.setText(String.valueOf(avg3));

        txtPercentYesterday.setText(String.valueOf(percent1));
        txtPercentWeek.setText(String.valueOf(percent2));
        txtPercentMonth.setText(String.valueOf(percent3));

        txtStdDevYesterday.setText(String.valueOf(stdDev1));
        txtStdDevWeek.setText(String.valueOf(stdDev2));
        txtStdDevMonth.setText(String.valueOf(stdDev3));

    }

    public Cursor getYesterDayGlucoseByCat(String category) {
        Log.i("myLog", "getYesterDayGlucoseByCat");
        String selQuery = "SELECT * from " + sessionManager.databaseIf.deviceTbl
                + " where " + sessionManager.databaseIf.deviceDate
                + " = (select max(date) from "
                + sessionManager.databaseIf.deviceTbl
                + " WHERE date < DATE('now')) AND "
                + sessionManager.databaseIf.deviceTimeCategory + "='" + category
                + "'";
        Log.i("myLog", "selQuery:" + selQuery);
        Cursor c = sessionManager.databaseIf.query(selQuery);
        return c;
    }

    public Cursor getLastMonthGlucoseByCat(String category) {
        Log.i("myLog", "getLastMonthGlucoseAvgByCat");
        String str2 = "SELECT * from "
                + sessionManager.databaseIf.deviceTbl
                + " where "
                + sessionManager.databaseIf.deviceTimeCategory
                + "='"
                + category
                + "' AND date BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime')";
        Log.i("myLog", "str2:" + str2);
        Cursor c = sessionManager.databaseIf.query(str2);
        return c;
    }

    public Cursor getLastWeekGlucoseByCat(String category) {
        Log.i("myLog", "getLastWeekGlucoseAvgByCat");
        String str = "SELECT * from "
                + sessionManager.databaseIf.deviceTbl
                + " where "
                + sessionManager.databaseIf.deviceTimeCategory
                + "='"
                + category
                + "' AND date BETWEEN datetime('now', '-7 days') AND datetime('now', 'localtime')";
        Log.i("myLog", "selQuery:" + str);
        Cursor c = sessionManager.databaseIf.query(str);
        return c;
    }

    public Cursor getLastWeekDietAvgByCat(String category) {
        Log.i("myLog", "getLastWeekDietAvgByCat");
        String str = "SELECT * from "
                + sessionManager.databaseIf.dietRecomTbl
                + " where "
                + sessionManager.databaseIf.dietRecomCategory
                + "='"
                + category
                + "' AND date BETWEEN datetime('now', '-7 days') AND datetime('now', 'localtime')";
        Log.i("myLog", "selQuery:" + str);
        Cursor c = sessionManager.databaseIf.query(str);
        return c;
    }

    public Cursor getYesterdayDietAvgByCat(String category) {
        Log.i("myLog", "getYesterdayDietAvgByCat");
        String str = "SELECT * from " + sessionManager.databaseIf.dietRecomTbl
                + " where " + sessionManager.databaseIf.dietRecomDate
                + " = (select max(date) from "
                + sessionManager.databaseIf.dietRecomTbl
                + " WHERE date < DATE('now')) AND "
                + sessionManager.databaseIf.dietRecomCategory + "='" + category + "'";
        Log.i("myLog", "selQuery:" + str);
        Cursor c = sessionManager.databaseIf.query(str);
        return c;
    }

    public void displayGlucoseData(Cursor c) {
        int size = c.getCount();
        highGlucoseAL = new ArrayList<Double>();
        lowGlucoseAL = new ArrayList<Double>();
        normalGlucoseAL = new ArrayList<Double>();
        lowGlucoseDateAL = new ArrayList<String>();
        normalGlucoseDateAL = new ArrayList<String>();
        highGlucoseDateAL = new ArrayList<String>();
        txtYesterday.setText("Low");
        txtWeek.setText("Normal");
        txtMonth.setText("High");
        double sumLow = 0, sumNormal = 0, sumHigh = 0;
        int countLow = 0, countNormal = 0, countHigh = 0;
        for (int index = 0; index < size; index++) {
            double val = c.getDouble(6);
            String date = c.getString(4);
            // Log.i("myLog", "Value:" + val);
            c.moveToNext();
            if (val < 70) {
                sumLow = sumLow + val;
                countLow++;
                lowGlucoseAL.add(val);
                lowGlucoseDateAL.add(date);
            } else if (val >= 70 && val < 140) {
                sumNormal = sumNormal + val;
                countNormal++;
                normalGlucoseAL.add(val);
                normalGlucoseDateAL.add(date);
            } else if (val >= 140) {
                sumHigh = sumHigh + val;
                countHigh++;
                highGlucoseAL.add(val);
                highGlucoseDateAL.add(date);
            }
        }
        try {
            int avgLow = (int) (sumLow / countLow);
            int percentLow = (avgLow * 100) / 400;
            int avgNormal = (int) (sumNormal / countNormal);
            int percentNormal = (avgNormal * 100) / 400;
            int avgHigh = (int) (sumHigh / countHigh);
            int percentHigh = (avgHigh * 100) / 400;

            double stdDevLow = getStdDev(lowGlucoseAL);
            String stdDevLowVal = String.format("%.2f", stdDevLow);
            double stdDevNormal = getStdDev(normalGlucoseAL);
            String stdDevNormalVal = String.format("%.2f", stdDevNormal);
            double stdDevHigh = getStdDev(highGlucoseAL);
            String stdDevHighVal = String.format("%.2f", stdDevHigh);
            c.close();

            txtAvgYesterday.setText(String.valueOf(avgLow));
            txtPercentYesterday.setText(String.valueOf(percentLow));
            txtStdDevYesterday.setText(String.valueOf(stdDevLowVal));
            txtAvgWeek.setText(String.valueOf(avgNormal));
            txtPercentWeek.setText(String.valueOf(percentNormal));
            txtStdDevWeek.setText(String.valueOf(stdDevNormalVal));
            txtAvgMonth.setText(String.valueOf(avgHigh));
            txtPercentMonth.setText(String.valueOf(percentHigh));
            txtStdDevMonth.setText(String.valueOf(stdDevHighVal));

        } catch (Exception ex) {

        }
    }

    public void displayPieGraph() {
        relGraphView.removeAllViews();
        PieChart chart = new PieChart(getActivity());
        chart.animateXY(900, 900);
        chart.setDescription("");
        chart.setHoleRadius(52f);
        chart.setTransparentCircleRadius(57f);
        chart.setCenterText("MPChart\nAndroid");
        chart.setCenterTextTypeface(sessionManager.tfRobotoCondLight);
        chart.setCenterTextSize(18f);
        chart.setUsePercentValues(true);
        ChartData<?> mChartData = generateDataPie(5);
        mChartData.setValueFormatter(new PercentFormatter());
        mChartData.setValueTypeface(sessionManager.tfRobotoCondLight);
        mChartData.setValueTextSize(11f);
        mChartData.setValueTextColor(Color.WHITE);
        // set data
        chart.setData((PieData) mChartData);
        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        relGraphView.addView(chart, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void displayBarGraph() {
        relGraphView.removeAllViews();
        BarChart chart = new BarChart(getActivity());

        chart.setDescription("");
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(sessionManager.tfRobotoCondLight);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(sessionManager.tfRobotoCondLight);
        leftAxis.setLabelCount(5);
        leftAxis.setSpaceTop(20f);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTypeface(sessionManager.tfRobotoCondLight);
        rightAxis.setLabelCount(5);
        rightAxis.setSpaceTop(20f);

        chart.setData((BarData) generateDataBar(5));
        chart.animateY(700);

        relGraphView.addView(chart, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    public void displayLineGraph() {
        relGraphView.removeAllViews();

        LineChart chart = new LineChart(getActivity());
        chart.setDescription("");
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(sessionManager.tfRobotoCondLight);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(sessionManager.tfRobotoCondLight);
        leftAxis.setLabelCount(5);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTypeface(sessionManager.tfRobotoCondLight);
        rightAxis.setLabelCount(5);
        rightAxis.setDrawGridLines(false);
        // set data
        chart.setData((LineData) generateDataLine(5));
        chart.animateX(750);
        relGraphView.addView(chart, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private BarData generateDataBar(int cnt) {
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        for (int i = 0; i < 5; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }
        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);
        ArrayList<String> al = new ArrayList<String>();
        al.add("Test1");
        al.add("Test2");
        al.add("Test3");
        al.add("Test4");
        al.add("Test5");
        BarData cd = new BarData(al, d);
        return cd;
    }

    private LineData generateDataLine(int cnt) {
        ArrayList<Entry> e1 = new ArrayList<Entry>();
        for (int i = 0; i < 5; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }
        LineDataSet d1 = new LineDataSet(e1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);
        ArrayList<Entry> e2 = new ArrayList<Entry>();
        for (int i = 0; i < 5; i++) {
            e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }
        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);
        //  sets.add(d2);
        ArrayList<String> al = new ArrayList<String>();
        al.add("Test1");
        al.add("Test2");
        al.add("Test3");
        al.add("Test4");
        al.add("Test5");
        LineData cd = new LineData(al, sets);
        return cd;
    }

    private PieData generateDataPie(int cnt) {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < 5; i++) {
            entries.add(new Entry((int) (Math.random() * 70) + 30, i));
        }
        PieDataSet d = new PieDataSet(entries, "");
        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ArrayList<String> al = new ArrayList<String>();
        al.add("Test1");
        al.add("Test2");
        al.add("Test3");
        al.add("Test4");
        al.add("Test5");
        PieData cd = new PieData(al, d);
        return cd;
    }
}

