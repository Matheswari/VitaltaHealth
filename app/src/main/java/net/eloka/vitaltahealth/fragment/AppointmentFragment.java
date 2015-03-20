package net.eloka.vitaltahealth.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParseException;
import com.larvalabs.svgandroid.SVGParser;
import com.parse.ParseObject;

import android.view.View.OnClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;

import net.eloka.vitaltahealth.R;
import net.eloka.vitaltahealth.adapter.CalendarAdapter;
import net.eloka.vitaltahealth.manager.SessionManager;

public class AppointmentFragment extends Fragment implements View.OnClickListener,
        View.OnTouchListener {
    private SessionManager sessionManager;
    public Drawable svgReschedule, svgRescheduleSel, svgSet, svgSetSel,
            svgCancel, svgDayBg, svgCancelSel, svgClose, svgCloseWhite;
    private LinearLayout linearMonthCalendar, linearDate, linearDay,
            linearTimeParent, linear1Week, linear2Week, linear3Week,
            linear4Week, linear5Week, linear6Week, linear7Week;
    private RelativeLayout relPrevious, relNext, relWeekCalendar,
            relAppointmentForm, relParent, relAppointmentNames;
    private GridView gridView;
    private TableLayout tblAppointmentNames;
    private ImageView imgSet, imgCancel, imgReschedule, imgCloseWeek,
            imgNextWeek, imgPrevWeek, imgCloseAppointment, imgCloseNames,
            imgCurrentWeek, imgSaveDateSel,
            imgCloseDateSel, imgDateSel, imgTimeDateSel;
    PopupWindow  popupWindow;
    private SeekBar seekbarDate;
    private String year="",selectedOption = "", selTime, selDate;
    private EditText edtTxtName, edtTxtContact;
    private TableRow.LayoutParams nameParam, timeParam, slotParam, lineParam;
    // private RadioGroup radioGroupSlot;
    private int selectedAppointmentId, textSize,datePopupXPos,datePopupYPos,datePopupWidth,datePopupHeight;
    private TextView txtTitleWeek, txtMonthTitle, txtDateName, txtContact, txtTimeDateSel, txtTimeAmPmDateSel, txtDaySel,
            txtMonthSel,
            txtSunCal, txtMonCal, txtTueCal, txtWedCal, txtThursCal, txtFriCal,
            txtSatCal, txtSun, txtMon, txtTue, txtWed, txtThu, txtFri, txtSat,
            txtSunVal, txtMonVal, txtTueVal, txtWedVal, txtThuVal, txtFriVal,
            txtSatVal, txt9AmLinear1, txt10AmLinear1, txt11AmLinear1,
            txt12PmLinear1, txt1PmLinear1, txt2PmLinear1, txt3PmLinear1,
            txt4PmLinear1, txt5PmLinear1, txt6PmLinear1, txt7PmLinear1,
            txt8PmLinear1, txt9PmLinear1, txt9AmLinear2, txt10AmLinear2,
            txt11AmLinear2, txt12PmLinear2, txt1PmLinear2, txt2PmLinear2,
            txt3PmLinear2, txt4PmLinear2, txt5PmLinear2, txt6PmLinear2,
            txt7PmLinear2, txt8PmLinear2, txt9PmLinear2, txt9AmLinear3,
            txt10AmLinear3, txt11AmLinear3, txt12PmLinear3, txt1PmLinear3,
            txt2PmLinear3, txt3PmLinear3, txt4PmLinear3, txt5PmLinear3,
            txt6PmLinear3, txt7PmLinear3, txt8PmLinear3, txt9PmLinear3,
            txt9AmLinear4, txt10AmLinear4, txt11AmLinear4, txt12PmLinear4,
            txt1PmLinear4, txt2PmLinear4, txt3PmLinear4, txt4PmLinear4,
            txt5PmLinear4, txt6PmLinear4, txt7PmLinear4, txt8PmLinear4,
            txt9PmLinear4, txt9AmLinear5, txt10AmLinear5, txt11AmLinear5,
            txt12PmLinear5, txt1PmLinear5, txt2PmLinear5, txt3PmLinear5,
            txt4PmLinear5, txt5PmLinear5, txt6PmLinear5, txt7PmLinear5,
            txt8PmLinear5, txt9PmLinear5, txt9AmLinear6, txt10AmLinear6,
            txt11AmLinear6, txt12PmLinear6, txt1PmLinear6, txt2PmLinear6,
            txt3PmLinear6, txt4PmLinear6, txt5PmLinear6, txt6PmLinear6,
            txt7PmLinear6, txt8PmLinear6, txt9PmLinear6, txt9AmLinear7,
            txt10AmLinear7, txt11AmLinear7, txt12PmLinear7, txt1PmLinear7,
            txt2PmLinear7, txt3PmLinear7, txt4PmLinear7, txt5PmLinear7,
            txt6PmLinear7, txt7PmLinear7, txt8PmLinear7, txt9PmLinear7,
            txtName, txtTime, txtTimeVal, txtDateVal, txtReschedule, txtSet,
            txtCancel, txtNameSet, txtAge, txtWeight, txtHeight, txtContactNo,
            txtMail, txtAddress, txt9AmSlot1Linear1, txt9AmSlot2Linear1,
            txt9AmSlot3Linear1, txt9AmSlot1Linear2, txt9AmSlot2Linear2,
            txt9AmSlot3Linear2, txt9AmSlot1Linear3, txt9AmSlot2Linear3,
            txt9AmSlot3Linear3, txt9AmSlot1Linear4, txt9AmSlot2Linear4,
            txt9AmSlot3Linear4, txt9AmSlot1Linear5, txt9AmSlot2Linear5,
            txt9AmSlot3Linear5, txt9AmSlot1Linear6, txt9AmSlot2Linear6,
            txt9AmSlot3Linear6, txt9AmSlot1Linear7, txt9AmSlot2Linear7,
            txt9AmSlot3Linear7, txt10AmSlot1Linear1, txt10AmSlot2Linear1,
            txt10AmSlot3Linear1, txt10AmSlot1Linear2, txt10AmSlot2Linear2,
            txt10AmSlot3Linear2, txt10AmSlot1Linear3, txt10AmSlot2Linear3,
            txt10AmSlot3Linear3, txt10AmSlot1Linear4, txt10AmSlot2Linear4,
            txt10AmSlot3Linear4, txt10AmSlot1Linear5, txt10AmSlot2Linear5,
            txt10AmSlot3Linear5, txt10AmSlot1Linear6, txt10AmSlot2Linear6,
            txt10AmSlot3Linear6, txt10AmSlot1Linear7, txt10AmSlot2Linear7,
            txt10AmSlot3Linear7, txt11AmSlot1Linear1, txt11AmSlot2Linear1,
            txt11AmSlot3Linear1, txt11AmSlot1Linear2, txt11AmSlot2Linear2,
            txt11AmSlot3Linear2, txt11AmSlot1Linear3, txt11AmSlot2Linear3,
            txt11AmSlot3Linear3, txt11AmSlot1Linear4, txt11AmSlot2Linear4,
            txt11AmSlot3Linear4, txt11AmSlot1Linear5, txt11AmSlot2Linear5,
            txt11AmSlot3Linear5, txt11AmSlot1Linear6, txt11AmSlot2Linear6,
            txt11AmSlot3Linear6, txt11AmSlot1Linear7, txt11AmSlot2Linear7,
            txt11AmSlot3Linear7, txt12PmSlot1Linear1, txt12PmSlot2Linear1,
            txt12PmSlot3Linear1, txt12PmSlot1Linear2, txt12PmSlot2Linear2,
            txt12PmSlot3Linear2, txt12PmSlot1Linear3, txt12PmSlot2Linear3,
            txt12PmSlot3Linear3, txt12PmSlot1Linear4, txt12PmSlot2Linear4,
            txt12PmSlot3Linear4, txt12PmSlot1Linear5, txt12PmSlot2Linear5,
            txt12PmSlot3Linear5, txt12PmSlot1Linear6, txt12PmSlot2Linear6,
            txt12PmSlot3Linear6, txt12PmSlot1Linear7, txt12PmSlot2Linear7,
            txt12PmSlot3Linear7, txt1PmSlot1Linear1, txt1PmSlot2Linear1,
            txt1PmSlot3Linear1, txt1PmSlot1Linear2, txt1PmSlot2Linear2,
            txt1PmSlot3Linear2, txt1PmSlot1Linear3, txt1PmSlot2Linear3,
            txt1PmSlot3Linear3, txt1PmSlot1Linear4, txt1PmSlot2Linear4,
            txt1PmSlot3Linear4, txt1PmSlot1Linear5, txt1PmSlot2Linear5,
            txt1PmSlot3Linear5, txt1PmSlot1Linear6, txt1PmSlot2Linear6,
            txt1PmSlot3Linear6, txt1PmSlot1Linear7, txt1PmSlot2Linear7,
            txt1PmSlot3Linear7, txt2PmSlot1Linear1, txt2PmSlot2Linear1,
            txt2PmSlot3Linear1, txt2PmSlot1Linear2, txt2PmSlot2Linear2,
            txt2PmSlot3Linear2, txt2PmSlot1Linear3, txt2PmSlot2Linear3,
            txt2PmSlot3Linear3, txt2PmSlot1Linear4, txt2PmSlot2Linear4,
            txt2PmSlot3Linear4, txt2PmSlot1Linear5, txt2PmSlot2Linear5,
            txt2PmSlot3Linear5, txt2PmSlot1Linear6, txt2PmSlot2Linear6,
            txt2PmSlot3Linear6, txt2PmSlot1Linear7, txt2PmSlot2Linear7,
            txt2PmSlot3Linear7, txt3PmSlot1Linear1, txt3PmSlot2Linear1,
            txt3PmSlot3Linear1, txt3PmSlot1Linear2, txt3PmSlot2Linear2,
            txt3PmSlot3Linear2, txt3PmSlot1Linear3, txt3PmSlot2Linear3,
            txt3PmSlot3Linear3, txt3PmSlot1Linear4, txt3PmSlot2Linear4,
            txt3PmSlot3Linear4, txt3PmSlot1Linear5, txt3PmSlot2Linear5,
            txt3PmSlot3Linear5, txt3PmSlot1Linear6, txt3PmSlot2Linear6,
            txt3PmSlot3Linear6, txt3PmSlot1Linear7, txt3PmSlot2Linear7,
            txt3PmSlot3Linear7, txt4PmSlot1Linear1, txt4PmSlot2Linear1,
            txt4PmSlot3Linear1, txt4PmSlot1Linear2, txt4PmSlot2Linear2,
            txt4PmSlot3Linear2, txt4PmSlot1Linear3, txt4PmSlot2Linear3,
            txt4PmSlot3Linear3, txt4PmSlot1Linear4, txt4PmSlot2Linear4,
            txt4PmSlot3Linear4, txt4PmSlot1Linear5, txt4PmSlot2Linear5,
            txt4PmSlot3Linear5, txt4PmSlot1Linear6, txt4PmSlot2Linear6,
            txt4PmSlot3Linear6, txt4PmSlot1Linear7, txt4PmSlot2Linear7,
            txt4PmSlot3Linear7, txt5PmSlot1Linear1, txt5PmSlot2Linear1,
            txt5PmSlot3Linear1, txt5PmSlot1Linear2, txt5PmSlot2Linear2,
            txt5PmSlot3Linear2, txt5PmSlot1Linear3, txt5PmSlot2Linear3,
            txt5PmSlot3Linear3, txt5PmSlot1Linear4, txt5PmSlot2Linear4,
            txt5PmSlot3Linear4, txt5PmSlot1Linear5, txt5PmSlot2Linear5,
            txt5PmSlot3Linear5, txt5PmSlot1Linear6, txt5PmSlot2Linear6,
            txt5PmSlot3Linear6, txt5PmSlot1Linear7, txt5PmSlot2Linear7,
            txt5PmSlot3Linear7, txt6PmSlot1Linear1, txt6PmSlot2Linear1,
            txt6PmSlot3Linear1, txt6PmSlot1Linear2, txt6PmSlot2Linear2,
            txt6PmSlot3Linear2, txt6PmSlot1Linear3, txt6PmSlot2Linear3,
            txt6PmSlot3Linear3, txt6PmSlot1Linear4, txt6PmSlot2Linear4,
            txt6PmSlot3Linear4, txt6PmSlot1Linear5, txt6PmSlot2Linear5,
            txt6PmSlot3Linear5, txt6PmSlot1Linear6, txt6PmSlot2Linear6,
            txt6PmSlot3Linear6, txt6PmSlot1Linear7, txt6PmSlot2Linear7,
            txt6PmSlot3Linear7, txt7PmSlot1Linear1, txt7PmSlot2Linear1,
            txt7PmSlot3Linear1, txt7PmSlot1Linear2, txt7PmSlot2Linear2,
            txt7PmSlot3Linear2, txt7PmSlot1Linear3, txt7PmSlot2Linear3,
            txt7PmSlot3Linear3, txt7PmSlot1Linear4, txt7PmSlot2Linear4,
            txt7PmSlot3Linear4, txt7PmSlot1Linear5, txt7PmSlot2Linear5,
            txt7PmSlot3Linear5, txt7PmSlot1Linear6, txt7PmSlot2Linear6,
            txt7PmSlot3Linear6, txt7PmSlot1Linear7, txt7PmSlot2Linear7,
            txt7PmSlot3Linear7, txt8PmSlot1Linear1, txt8PmSlot2Linear1,
            txt8PmSlot3Linear1, txt8PmSlot1Linear2, txt8PmSlot2Linear2,
            txt8PmSlot3Linear2, txt8PmSlot1Linear3, txt8PmSlot2Linear3,
            txt8PmSlot3Linear3, txt8PmSlot1Linear4, txt8PmSlot2Linear4,
            txt8PmSlot3Linear4, txt8PmSlot1Linear5, txt8PmSlot2Linear5,
            txt8PmSlot3Linear5, txt8PmSlot1Linear6, txt8PmSlot2Linear6,
            txt8PmSlot3Linear6, txt8PmSlot1Linear7, txt8PmSlot2Linear7,
            txt8PmSlot3Linear7, txt9PmSlot1Linear1, txt9PmSlot2Linear1,
            txt9PmSlot3Linear1, txt9PmSlot1Linear2, txt9PmSlot2Linear2,
            txt9PmSlot3Linear2, txt9PmSlot1Linear3, txt9PmSlot2Linear3,
            txt9PmSlot3Linear3, txt9PmSlot1Linear4, txt9PmSlot2Linear4,
            txt9PmSlot3Linear4, txt9PmSlot1Linear5, txt9PmSlot2Linear5,
            txt9PmSlot3Linear5, txt9PmSlot1Linear6, txt9PmSlot2Linear6,
            txt9PmSlot3Linear6, txt9PmSlot1Linear7, txt9PmSlot2Linear7,
            txt9PmSlot3Linear7, txtViewSlot1, txtViewSlot2, txtViewSlot3,
            txtNameSlot1, txtNameSlot2, txtNameSlot3,txtTimeSlot1,txtTimeSlot2,txtTimeSlot3;
    private CheckBox chkBoxSlot1, chkBoxSlot2, chkBoxSlot3;
    public GregorianCalendar month, itemmonth;
    public CalendarAdapter adapter;
    public Handler handler;
    boolean[] selectedSlotsArr = {false, false, false};
    int selectedSlot;
    AdapterView<?> selectedAdapterView;
    public ArrayList<String> items, alAppointmentTime, alAppointmentSlots,
            alAppointmentNames, selectedWeekDays, selectedSlotAL,
            selectedNameAL, selectedContactNoAL;
    LayoutInflater layoutInflater;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    View itemView;
    private AdapterView<?> selectedParent;
    private int selectedPosition;

    public AppointmentFragment() {
        // TODO Auto-generated constructor stub
        sessionManager = SessionManager.getInstance(null);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        Log.i("myLog", "OnCreateView:");
        View rootView = inflater.inflate(R.layout.fragment_appointment,
                container, false);
        getSvgImages();
        initViews(rootView);
        initParams();
       dateTimePopup();
        showCalendarDaysInGrid();
        return rootView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

    public void initParams() {
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            nameParam = new TableRow.LayoutParams(dpToPx(300), dpToPx(60));
            nameParam.setMargins(dpToPx(5), 0, 0, 0);
            timeParam = new TableRow.LayoutParams(dpToPx(100), dpToPx(60));
            slotParam = new TableRow.LayoutParams(dpToPx(50), dpToPx(60));
            lineParam = new TableRow.LayoutParams(dpToPx(450), dpToPx(1));
            lineParam.span = 3;
            textSize = 22;
            datePopupXPos=sessionManager.width/4;
            datePopupYPos=dpToPx(200);
            datePopupWidth=dpToPx(500);
            datePopupHeight=dpToPx(300);

        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            nameParam = new TableRow.LayoutParams(dpToPx(250), dpToPx(50));
            nameParam.setMargins(dpToPx(5), 0, 0, 0);
            timeParam = new TableRow.LayoutParams(dpToPx(100), dpToPx(50));
            slotParam = new TableRow.LayoutParams(dpToPx(50), dpToPx(50));
            lineParam = new TableRow.LayoutParams(dpToPx(400), dpToPx(1));
            lineParam.span = 3;
            textSize = 18;
            datePopupXPos=sessionManager.width/4;
            datePopupYPos=dpToPx(200);
            datePopupWidth=dpToPx(400);
            datePopupHeight=dpToPx(220);

        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            nameParam = new TableRow.LayoutParams(dpToPx(200), dpToPx(40));
            nameParam.setMargins(dpToPx(5), 0, 0, 0);
            timeParam = new TableRow.LayoutParams(dpToPx(60), dpToPx(40));
            slotParam = new TableRow.LayoutParams(dpToPx(40), dpToPx(40));
            lineParam = new TableRow.LayoutParams(dpToPx(300), dpToPx(1));
            lineParam.span = 3;
            textSize = 16;
            datePopupXPos=sessionManager.width/4;
            datePopupYPos=dpToPx(100);
            datePopupWidth=dpToPx(300);
            datePopupHeight=dpToPx(150);
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.imgCloseInWeekView:
                relWeekCalendar.setVisibility(View.INVISIBLE);
                break;
            case R.id.imgNextInWeekView:
                showWeeklyData(selectedParent, selectedPosition, "Next");
                break;
            case R.id.imgPrevInWeekView:
                showWeeklyData(selectedParent, selectedPosition, "Prev");
                break;
            case R.id.imgCurrentInWeekView:
                showWeeklyData(selectedParent, selectedPosition, "Current");
                break;
            case R.id.txtNineAMInLinear1InWeekView:
                String date = txtSunVal.getText().toString();
                String time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "9:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "9:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "9:00 AM";
                setDateTimeForAppointment(v, date, time);

                break;
            case R.id.txtNineAMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "9:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "9:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtNineAMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "9:00 AM";
                setDateTimeForAppointment(v, date, time);
               // getAppointmentNames(date, time);
              //  getDataFromTimeSlot(time, date);
                break;
            case R.id.txtTenAMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTenAMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTenAMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTenAMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTenAMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTenAMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTenAMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtElevenAMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtElevenAMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtElevenAMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtElevenAMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtElevenAMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtElevenAMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwelvePMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwelvePMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwelvePMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwelvePMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwelvePMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtOnePMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtOnePMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtOnePMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtOnePMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtOnePMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtOnePMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtOnePMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwoPMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwoPMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwoPMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwoPMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwoPMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwoPMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtTwoPMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtThreePMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtThreePMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtThreePMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtThreePMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtThreePMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtThreePMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtThreePMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtFourPMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtFourPMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtFourPMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFourPMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFourPMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFourPMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFourPMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFivePMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFivePMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFivePMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFivePMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFivePMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFivePMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtFivePMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtSixPMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "06:00 AM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtSixPMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtSixPMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);

            case R.id.txtSixPMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMInLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMInLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMInLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMInLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMInLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMInLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMInLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNineAMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "09:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTenAMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "10:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtElevenAMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "11:00 AM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwelvePMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "12:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtOnePMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "01:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtTwoPMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "02:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtThreePMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "03:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFourPMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "04:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtFivePMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "05:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSixPMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "06:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtSevenPMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "07:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtEightPMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtEightPMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtEightPMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "08:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot1InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot1InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot1InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot1InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot1InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot1InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot1InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtNinePMSlot2InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment (v, date, time);
                break;

            case R.id.txtNinePMSlot2InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot2InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot2InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot2InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;

            case R.id.txtNinePMSlot2InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);
                break;
            case R.id.txtNinePMSlot2InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);

                break;
            case R.id.txtNinePMSlot3InLinear1InWeekView:
                date = txtSunVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);

                break;
            case R.id.txtNinePMSlot3InLinear2InWeekView:
                date = txtMonVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);

                break;
            case R.id.txtNinePMSlot3InLinear3InWeekView:
                date = txtTueVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);

                break;
            case R.id.txtNinePMSlot3InLinear4InWeekView:
                date = txtWedVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);

                break;
            case R.id.txtNinePMSlot3InLinear5InWeekView:
                date = txtThuVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);

                break;
            case R.id.txtNinePMSlot3InLinear6InWeekView:
                date = txtFriVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);

                break;
            case R.id.txtNinePMSlot3InLinear7InWeekView:
                date = txtSatVal.getText().toString();
                time = "09:00 PM";
                setDateTimeForAppointment(v, date, time);

                break;
            case R.id.imgCloseNamesInAppointment:
                relAppointmentNames.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void initViews(View v) {
        linearMonthCalendar = (LinearLayout) v
                .findViewById(R.id.linearMonthViewInAppointment);
        gridView = (GridView) v.findViewById(R.id.gridviewInAppointment);
        txtMonthTitle = (TextView) v.findViewById(R.id.titleInAppointment);
        relPrevious = (RelativeLayout) v
                .findViewById(R.id.previousInAppointment);
        relNext = (RelativeLayout) v.findViewById(R.id.nextInAppointment);
        // tglBtn = (ToggleButton) v.findViewById(R.id.tglTitle);
        relWeekCalendar = (RelativeLayout) v
                .findViewById(R.id.relWeekCalendarInAppointment);
        relAppointmentForm = (RelativeLayout) v
                .findViewById(R.id.relAppointmentFormInAppointment);
        relAppointmentNames = (RelativeLayout) v
                .findViewById(R.id.relAppointmentNamesInAppointment);
        tblAppointmentNames = (TableLayout) v
                .findViewById(R.id.tblAppointNamesInAppointment);
        relParent = (RelativeLayout) v.findViewById(R.id.relParent);
        txtDateName = (TextView) v.findViewById(R.id.txtDateNameInAppointment);
        // viewPager = (ViewPager) v.findViewById(R.id.pagerInAppointment);
        imgCloseNames = (ImageView) v
                .findViewById(R.id.imgCloseNamesInAppointment);
        txtSunCal = (TextView) v.findViewById(R.id.txtSundayInAppointment);
        txtMonCal = (TextView) v.findViewById(R.id.txtMondayInAppointment);
        txtTueCal = (TextView) v.findViewById(R.id.txtTuesdayInAppointment);
        txtWedCal = (TextView) v.findViewById(R.id.txtWednesdayInAppointment);
        txtThursCal = (TextView) v.findViewById(R.id.txtThursdayInAppointment);
        txtFriCal = (TextView) v.findViewById(R.id.txtFridayInAppointment);
        txtSatCal = (TextView) v.findViewById(R.id.txtSaturdayInAppointment);
        txtDateName.setTypeface(sessionManager.tfRobotoCondLight);
        txtMonthTitle.setTypeface(sessionManager.tfRobotoCondLight);
        txtSunCal.setTypeface(sessionManager.tfRobotoCondLight);
        txtMonCal.setTypeface(sessionManager.tfRobotoCondLight);
        txtTueCal.setTypeface(sessionManager.tfRobotoCondLight);
        txtWedCal.setTypeface(sessionManager.tfRobotoCondLight);
        txtThursCal.setTypeface(sessionManager.tfRobotoCondLight);
        txtFriCal.setTypeface(sessionManager.tfRobotoCondLight);
        txtSatCal.setTypeface(sessionManager.tfRobotoCondLight);

        imgCloseNames.setImageDrawable(svgClose);
        relWeekCalendar.setOnTouchListener(this);
        relAppointmentForm.setOnTouchListener(this);
        linearMonthCalendar.setOnTouchListener(this);
        imgCloseNames.setOnClickListener(this);

    }

    private void getSvgImages() {
        SVG svg;
        try {
            Log.i("myLog", "getSvgImages");
            svg = SVGParser.getSVGFromAsset(sessionManager.context.getAssets(),
                    "svg/reschedule.svg");
            svgReschedule = svg.createPictureDrawable();
            svg = SVGParser.getSVGFromAsset(sessionManager.context.getAssets(),
                    "svg/reschedulesel.svg");
            svgRescheduleSel = svg.createPictureDrawable();
            svg = SVGParser.getSVGFromAsset(sessionManager.context.getAssets(),
                    "svg/set.svg");
            svgSet = svg.createPictureDrawable();
            svg = SVGParser.getSVGFromAsset(sessionManager.context.getAssets(),
                    "svg/setsel.svg");
            svgSetSel = svg.createPictureDrawable();
            svg = SVGParser.getSVGFromAsset(sessionManager.context.getAssets(),
                    "svg/cancel.svg");
            svgCancel = svg.createPictureDrawable();
            svg = SVGParser.getSVGFromAsset(sessionManager.context.getAssets(),
                    "svg/cancelsel.svg");
            svgCancelSel = svg.createPictureDrawable();
            svg = SVGParser.getSVGFromAsset(sessionManager.context.getAssets(),
                    "svg/close.svg");
            svgClose = svg.createPictureDrawable();
            Log.i("myLog", "getSvgImages end");
            svg = SVGParser.getSVGFromAsset(sessionManager.context.getAssets(),
                    "svg/close.svg", Color.parseColor("#FFFFFF"),
                    Color.parseColor("#B2AD88"));
            svgCloseWhite = svg.createPictureDrawable();
        } catch (SVGParseException e) {
            // TODO Auto-generated catch block
            Log.i("myLog", "getSvgImages exception:" + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.i("myLog", "getSvgImages exception:" + e.toString());
            e.printStackTrace();
        }
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();
            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("dd MMM yy", Locale.US);
            String itemvalue;
            for (int i = 0; i < 7; i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                items.add("2012-09-12");
                items.add("2012-10-07");
                items.add("2012-10-15");
                items.add("2012-10-20");
                items.add("2012-11-30");
                items.add("2012-11-28");
            }
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    };

    public void getDaysFromAppointmentTbl(String month) {
        Log.i("myLog", "getDaysFromAppointmentTbl:");
        Log.i("myLog", "month :" + month);
        sessionManager.alAppointmentDate = new ArrayList<String>();
        alAppointmentTime = new ArrayList<String>();
        alAppointmentNames = new ArrayList<String>();
        alAppointmentSlots = new ArrayList<String>();
        String str = "SELECT * FROM "
                + sessionManager.databaseIf.appointmentTbl;
        Cursor c = sessionManager.databaseIf.query(str);
        int size = c.getCount();
        Log.i("myLog", "getAppintmentdays size:" + size);
        for (int start = 0; start < size; start++) {
            Log.i("mylog", "for index:" + start);
            String date = c.getString(4);
            String name = c.getString(3);
            String time = c.getString(5);
            String slot = c.getString(2);
            Log.i("myLog", "Date:" + date);
            Log.i("myLog", "month:" + month);
            // if (date.contains(month)) {
            Log.i("myLog", "iffff");
            Log.i("myLog", "date:" + date);
            if (!slot.equalsIgnoreCase("0")) {
                sessionManager.alAppointmentDate.add(date);
                alAppointmentSlots.add(slot);
                alAppointmentNames.add(name);
                alAppointmentTime.add(time);
            }
            // }
            c.moveToNext();
        }
        c.close();
        Log.i("myLog", "al size::" + sessionManager.alAppointmentDate.size());
    }

    private void eventHandlers() {
        relPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        relNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();
            }
        });
    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    protected void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    public void refreshCalendar() {
        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items
        txtMonthTitle.setText(android.text.format.DateFormat.format(
                "MMMM yyyy", month));
    }

    public void initWeeklyViews(View v, int pos) {
        // viewPager.setCurrentItem(pos);
        linearDate = (LinearLayout) v.findViewById(R.id.lineardateInWeekView);
        linearTimeParent = (LinearLayout) v
                .findViewById(R.id.linearForTimeInWeekView);
        txtSun = (TextView) v.findViewById(R.id.txtSundayInWeekView);
        txtMon = (TextView) v.findViewById(R.id.txtMondayInWeekView);
        txtTue = (TextView) v.findViewById(R.id.txtTuesdayInWeekView);
        txtWed = (TextView) v.findViewById(R.id.txtWednesdayInWeekView);
        txtThu = (TextView) v.findViewById(R.id.txtThursdayInWeekView);
        txtFri = (TextView) v.findViewById(R.id.txtFridayInWeekView);
        txtSat = (TextView) v.findViewById(R.id.txtSaturdayInWeekView);
        txtSunVal = (TextView) v.findViewById(R.id.txtSundayDateInWeekView);
        txtMonVal = (TextView) v.findViewById(R.id.txtMondayDateInWeekView);
        txtTueVal = (TextView) v.findViewById(R.id.txtTuesdayDateInWeekView);
        txtWedVal = (TextView) v.findViewById(R.id.txtWednesdayDateInWeekView);
        txtThuVal = (TextView) v.findViewById(R.id.txtThursdayDateInWeekView);
        txtFriVal = (TextView) v.findViewById(R.id.txtFridayDateInWeekView);
        txtSatVal = (TextView) v.findViewById(R.id.txtSaturdayDateInWeekView);
        linear1Week = (LinearLayout) v.findViewById(R.id.linear1InWeekView);
        linear2Week = (LinearLayout) v.findViewById(R.id.linear2InWeekView);
        linear3Week = (LinearLayout) v.findViewById(R.id.linear3InWeekView);
        linear4Week = (LinearLayout) v.findViewById(R.id.linear4InWeekView);
        linear5Week = (LinearLayout) v.findViewById(R.id.linear5InWeekView);
        linear6Week = (LinearLayout) v.findViewById(R.id.linear6InWeekView);
        linear7Week = (LinearLayout) v.findViewById(R.id.linear7InWeekView);
        imgNextWeek = (ImageView) v.findViewById(R.id.imgNextInWeekView);
        imgPrevWeek = (ImageView) v.findViewById(R.id.imgPrevInWeekView);
        imgCurrentWeek = (ImageView) v.findViewById(R.id.imgCurrentInWeekView);
        imgCloseWeek = (ImageView) v.findViewById(R.id.imgCloseInWeekView);
        txtTitleWeek = (TextView) v.findViewById(R.id.txtTitleInWeekView);
        imgCloseWeek.setImageDrawable(svgClose);
        txt9AmLinear1 = (TextView) v
                .findViewById(R.id.txtNineAMInLinear1InWeekView);
        txt9AmLinear2 = (TextView) v
                .findViewById(R.id.txtNineAMInLinear2InWeekView);
        txt9AmLinear3 = (TextView) v
                .findViewById(R.id.txtNineAMInLinear3InWeekView);
        txt9AmLinear4 = (TextView) v
                .findViewById(R.id.txtNineAMInLinear4InWeekView);
        txt9AmLinear5 = (TextView) v
                .findViewById(R.id.txtNineAMInLinear5InWeekView);
        txt9AmLinear6 = (TextView) v
                .findViewById(R.id.txtNineAMInLinear6InWeekView);
        txt9AmLinear7 = (TextView) v
                .findViewById(R.id.txtNineAMInLinear7InWeekView);
        txt10AmLinear1 = (TextView) v
                .findViewById(R.id.txtTenAMInLinear1InWeekView);
        txt10AmLinear2 = (TextView) v
                .findViewById(R.id.txtTenAMInLinear2InWeekView);
        txt10AmLinear3 = (TextView) v
                .findViewById(R.id.txtTenAMInLinear3InWeekView);
        txt10AmLinear4 = (TextView) v
                .findViewById(R.id.txtTenAMInLinear4InWeekView);
        txt10AmLinear5 = (TextView) v
                .findViewById(R.id.txtTenAMInLinear5InWeekView);
        txt10AmLinear6 = (TextView) v
                .findViewById(R.id.txtTenAMInLinear6InWeekView);
        txt10AmLinear7 = (TextView) v
                .findViewById(R.id.txtTenAMInLinear7InWeekView);
        txt11AmLinear1 = (TextView) v
                .findViewById(R.id.txtElevenAMInLinear1InWeekView);
        txt11AmLinear2 = (TextView) v
                .findViewById(R.id.txtElevenAMInLinear2InWeekView);
        txt11AmLinear3 = (TextView) v
                .findViewById(R.id.txtElevenAMInLinear3InWeekView);
        txt11AmLinear4 = (TextView) v
                .findViewById(R.id.txtElevenAMInLinear4InWeekView);
        txt11AmLinear5 = (TextView) v
                .findViewById(R.id.txtElevenAMInLinear5InWeekView);
        txt11AmLinear6 = (TextView) v
                .findViewById(R.id.txtElevenAMInLinear6InWeekView);
        txt11AmLinear7 = (TextView) v
                .findViewById(R.id.txtElevenAMInLinear7InWeekView);
        txt12PmLinear1 = (TextView) v
                .findViewById(R.id.txtTwelvePMInLinear1InWeekView);
        txt12PmLinear2 = (TextView) v
                .findViewById(R.id.txtTwelvePMInLinear2InWeekView);
        txt12PmLinear3 = (TextView) v
                .findViewById(R.id.txtTwelvePMInLinear3InWeekView);
        txt12PmLinear4 = (TextView) v
                .findViewById(R.id.txtTwelvePMInLinear4InWeekView);
        txt12PmLinear5 = (TextView) v
                .findViewById(R.id.txtTwelvePMInLinear5InWeekView);
        txt12PmLinear6 = (TextView) v
                .findViewById(R.id.txtTwelvePMInLinear6InWeekView);
        txt12PmLinear7 = (TextView) v
                .findViewById(R.id.txtTwelvePMInLinear7InWeekView);
        txt1PmLinear1 = (TextView) v
                .findViewById(R.id.txtOnePMInLinear1InWeekView);
        txt1PmLinear2 = (TextView) v
                .findViewById(R.id.txtOnePMInLinear2InWeekView);
        txt1PmLinear3 = (TextView) v
                .findViewById(R.id.txtOnePMInLinear3InWeekView);
        txt1PmLinear4 = (TextView) v
                .findViewById(R.id.txtOnePMInLinear4InWeekView);
        txt1PmLinear5 = (TextView) v
                .findViewById(R.id.txtOnePMInLinear5InWeekView);
        txt1PmLinear6 = (TextView) v
                .findViewById(R.id.txtOnePMInLinear6InWeekView);
        txt1PmLinear7 = (TextView) v
                .findViewById(R.id.txtOnePMInLinear7InWeekView);
        txt2PmLinear1 = (TextView) v
                .findViewById(R.id.txtTwoPMInLinear1InWeekView);
        txt2PmLinear2 = (TextView) v
                .findViewById(R.id.txtTwoPMInLinear2InWeekView);
        txt2PmLinear3 = (TextView) v
                .findViewById(R.id.txtTwoPMInLinear3InWeekView);
        txt2PmLinear4 = (TextView) v
                .findViewById(R.id.txtTwoPMInLinear4InWeekView);
        txt2PmLinear5 = (TextView) v
                .findViewById(R.id.txtTwoPMInLinear5InWeekView);
        txt2PmLinear6 = (TextView) v
                .findViewById(R.id.txtTwoPMInLinear6InWeekView);
        txt2PmLinear7 = (TextView) v
                .findViewById(R.id.txtTwoPMInLinear7InWeekView);
        txt3PmLinear1 = (TextView) v
                .findViewById(R.id.txtThreePMInLinear1InWeekView);
        txt3PmLinear2 = (TextView) v
                .findViewById(R.id.txtThreePMInLinear2InWeekView);
        txt3PmLinear3 = (TextView) v
                .findViewById(R.id.txtThreePMInLinear3InWeekView);
        txt3PmLinear4 = (TextView) v
                .findViewById(R.id.txtThreePMInLinear4InWeekView);
        txt3PmLinear5 = (TextView) v
                .findViewById(R.id.txtThreePMInLinear5InWeekView);
        txt3PmLinear6 = (TextView) v
                .findViewById(R.id.txtThreePMInLinear6InWeekView);
        txt3PmLinear7 = (TextView) v
                .findViewById(R.id.txtThreePMInLinear7InWeekView);
        txt4PmLinear1 = (TextView) v
                .findViewById(R.id.txtFourPMInLinear1InWeekView);
        txt4PmLinear2 = (TextView) v
                .findViewById(R.id.txtFourPMInLinear2InWeekView);
        txt4PmLinear3 = (TextView) v
                .findViewById(R.id.txtFourPMInLinear3InWeekView);
        txt4PmLinear4 = (TextView) v
                .findViewById(R.id.txtFourPMInLinear4InWeekView);
        txt4PmLinear5 = (TextView) v
                .findViewById(R.id.txtFourPMInLinear5InWeekView);
        txt4PmLinear6 = (TextView) v
                .findViewById(R.id.txtFourPMInLinear6InWeekView);
        txt4PmLinear7 = (TextView) v
                .findViewById(R.id.txtFourPMInLinear7InWeekView);
        txt5PmLinear1 = (TextView) v
                .findViewById(R.id.txtFivePMInLinear1InWeekView);
        txt5PmLinear2 = (TextView) v
                .findViewById(R.id.txtFivePMInLinear2InWeekView);
        txt5PmLinear3 = (TextView) v
                .findViewById(R.id.txtFivePMInLinear3InWeekView);
        txt5PmLinear4 = (TextView) v
                .findViewById(R.id.txtFivePMInLinear4InWeekView);
        txt5PmLinear5 = (TextView) v
                .findViewById(R.id.txtFivePMInLinear5InWeekView);
        txt5PmLinear6 = (TextView) v
                .findViewById(R.id.txtFivePMInLinear6InWeekView);
        txt5PmLinear7 = (TextView) v
                .findViewById(R.id.txtFivePMInLinear7InWeekView);
        txt6PmLinear1 = (TextView) v
                .findViewById(R.id.txtSixPMInLinear1InWeekView);
        txt6PmLinear2 = (TextView) v
                .findViewById(R.id.txtSixPMInLinear2InWeekView);
        txt6PmLinear3 = (TextView) v
                .findViewById(R.id.txtSixPMInLinear3InWeekView);
        txt6PmLinear4 = (TextView) v
                .findViewById(R.id.txtSixPMInLinear4InWeekView);
        txt6PmLinear5 = (TextView) v
                .findViewById(R.id.txtSixPMInLinear5InWeekView);
        txt6PmLinear6 = (TextView) v
                .findViewById(R.id.txtSixPMInLinear6InWeekView);
        txt6PmLinear7 = (TextView) v
                .findViewById(R.id.txtSixPMInLinear7InWeekView);
        txt7PmLinear1 = (TextView) v
                .findViewById(R.id.txtSevenPMInLinear1InWeekView);
        txt7PmLinear2 = (TextView) v
                .findViewById(R.id.txtSevenPMInLinear2InWeekView);
        txt7PmLinear3 = (TextView) v
                .findViewById(R.id.txtSevenPMInLinear3InWeekView);
        txt7PmLinear4 = (TextView) v
                .findViewById(R.id.txtSevenPMInLinear4InWeekView);
        txt7PmLinear5 = (TextView) v
                .findViewById(R.id.txtSevenPMInLinear5InWeekView);
        txt7PmLinear6 = (TextView) v
                .findViewById(R.id.txtSevenPMInLinear6InWeekView);
        txt7PmLinear7 = (TextView) v
                .findViewById(R.id.txtSevenPMInLinear7InWeekView);
        txt8PmLinear1 = (TextView) v
                .findViewById(R.id.txtEightPMInLinear1InWeekView);
        txt8PmLinear2 = (TextView) v
                .findViewById(R.id.txtEightPMInLinear2InWeekView);
        txt8PmLinear3 = (TextView) v
                .findViewById(R.id.txtEightPMInLinear3InWeekView);
        txt8PmLinear4 = (TextView) v
                .findViewById(R.id.txtEightPMInLinear4InWeekView);
        txt8PmLinear5 = (TextView) v
                .findViewById(R.id.txtEightPMInLinear5InWeekView);
        txt8PmLinear6 = (TextView) v
                .findViewById(R.id.txtEightPMInLinear6InWeekView);
        txt8PmLinear7 = (TextView) v
                .findViewById(R.id.txtEightPMInLinear7InWeekView);
        txt9PmLinear1 = (TextView) v
                .findViewById(R.id.txtNinePMInLinear1InWeekView);
        txt9PmLinear2 = (TextView) v
                .findViewById(R.id.txtNinePMInLinear2InWeekView);
        txt9PmLinear3 = (TextView) v
                .findViewById(R.id.txtNinePMInLinear3InWeekView);
        txt9PmLinear4 = (TextView) v
                .findViewById(R.id.txtNinePMInLinear4InWeekView);
        txt9PmLinear5 = (TextView) v
                .findViewById(R.id.txtNinePMInLinear5InWeekView);
        txt9PmLinear6 = (TextView) v
                .findViewById(R.id.txtNinePMInLinear6InWeekView);
        txt9PmLinear7 = (TextView) v
                .findViewById(R.id.txtNinePMInLinear7InWeekView);
        txt9AmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtNineAMSlot1InLinear1InWeekView);
        txt9AmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtNineAMSlot2InLinear1InWeekView);
        txt9AmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtNineAMSlot3InLinear1InWeekView);

        txt9AmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtNineAMSlot1InLinear2InWeekView);
        txt9AmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtNineAMSlot2InLinear2InWeekView);
        txt9AmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtNineAMSlot3InLinear2InWeekView);
        txt9AmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtNineAMSlot1InLinear3InWeekView);
        txt9AmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtNineAMSlot2InLinear3InWeekView);
        txt9AmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtNineAMSlot3InLinear3InWeekView);
        txt9AmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtNineAMSlot1InLinear4InWeekView);
        txt9AmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtNineAMSlot2InLinear4InWeekView);
        txt9AmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtNineAMSlot3InLinear4InWeekView);
        txt9AmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtNineAMSlot1InLinear5InWeekView);
        txt9AmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtNineAMSlot2InLinear5InWeekView);
        txt9AmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtNineAMSlot3InLinear5InWeekView);
        txt9AmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtNineAMSlot1InLinear6InWeekView);
        txt9AmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtNineAMSlot2InLinear6InWeekView);
        txt9AmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtNineAMSlot3InLinear6InWeekView);
        txt9AmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtNineAMSlot1InLinear7InWeekView);
        txt9AmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtNineAMSlot2InLinear7InWeekView);
        txt9AmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtNineAMSlot3InLinear7InWeekView);
        txt10AmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtTenAMSlot1InLinear1InWeekView);
        txt10AmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtTenAMSlot2InLinear1InWeekView);
        txt10AmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtTenAMSlot3InLinear1InWeekView);
        txt10AmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtTenAMSlot1InLinear2InWeekView);
        txt10AmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtTenAMSlot2InLinear2InWeekView);
        txt10AmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtTenAMSlot3InLinear2InWeekView);
        txt10AmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtTenAMSlot1InLinear3InWeekView);
        txt10AmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtTenAMSlot2InLinear3InWeekView);
        txt10AmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtTenAMSlot3InLinear3InWeekView);
        txt10AmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtTenAMSlot1InLinear4InWeekView);
        txt10AmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtTenAMSlot2InLinear4InWeekView);
        txt10AmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtTenAMSlot3InLinear4InWeekView);
        txt10AmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtTenAMSlot1InLinear5InWeekView);
        txt10AmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtTenAMSlot2InLinear5InWeekView);
        txt10AmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtTenAMSlot3InLinear5InWeekView);
        txt10AmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtTenAMSlot1InLinear6InWeekView);
        txt10AmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtTenAMSlot2InLinear6InWeekView);
        txt10AmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtTenAMSlot3InLinear6InWeekView);
        txt10AmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtTenAMSlot1InLinear7InWeekView);
        txt10AmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtTenAMSlot2InLinear7InWeekView);
        txt10AmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtTenAMSlot3InLinear7InWeekView);
        txt11AmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot1InLinear1InWeekView);
        txt11AmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot2InLinear1InWeekView);
        txt11AmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot3InLinear1InWeekView);
        txt11AmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot1InLinear2InWeekView);
        txt11AmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot2InLinear2InWeekView);
        txt11AmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot3InLinear2InWeekView);
        txt11AmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot1InLinear3InWeekView);
        txt11AmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot2InLinear3InWeekView);
        txt11AmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot3InLinear3InWeekView);
        txt11AmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot1InLinear4InWeekView);
        txt11AmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot2InLinear4InWeekView);
        txt11AmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot3InLinear4InWeekView);
        txt11AmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot1InLinear5InWeekView);
        txt11AmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot2InLinear5InWeekView);
        txt11AmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot3InLinear5InWeekView);
        txt11AmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot1InLinear6InWeekView);
        txt11AmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot2InLinear6InWeekView);
        txt11AmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot3InLinear6InWeekView);
        txt11AmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot1InLinear7InWeekView);
        txt11AmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot2InLinear7InWeekView);
        txt12PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot1InLinear1InWeekView);
        txt12PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot2InLinear1InWeekView);
        txt12PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot3InLinear1InWeekView);
        txt12PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot1InLinear2InWeekView);
        txt12PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot2InLinear2InWeekView);
        txt12PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot3InLinear2InWeekView);
        txt12PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot1InLinear3InWeekView);
        txt12PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot2InLinear3InWeekView);
        txt12PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot3InLinear3InWeekView);
        txt12PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot1InLinear4InWeekView);
        txt12PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot2InLinear4InWeekView);
        txt12PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot3InLinear4InWeekView);
        txt12PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot1InLinear5InWeekView);
        txt12PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot2InLinear5InWeekView);
        txt12PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot3InLinear5InWeekView);
        txt11AmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtElevenAMSlot3InLinear7InWeekView);
        txt12PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot1InLinear6InWeekView);
        txt12PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot2InLinear6InWeekView);
        txt12PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot3InLinear6InWeekView);
        txt12PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot1InLinear7InWeekView);
        txt12PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot2InLinear7InWeekView);
        txt12PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtTwelvePMSlot3InLinear7InWeekView);
        txt1PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtOnePMSlot1InLinear1InWeekView);
        txt1PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtOnePMSlot2InLinear1InWeekView);
        txt1PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtOnePMSlot3InLinear1InWeekView);
        txt1PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtOnePMSlot1InLinear2InWeekView);
        txt1PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtOnePMSlot2InLinear2InWeekView);
        txt1PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtOnePMSlot3InLinear2InWeekView);
        txt1PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtOnePMSlot1InLinear3InWeekView);
        txt1PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtOnePMSlot2InLinear3InWeekView);
        txt1PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtOnePMSlot3InLinear3InWeekView);
        txt1PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtOnePMSlot1InLinear4InWeekView);
        txt1PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtOnePMSlot2InLinear4InWeekView);
        txt1PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtOnePMSlot3InLinear4InWeekView);
        txt1PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtOnePMSlot1InLinear5InWeekView);
        txt1PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtOnePMSlot2InLinear5InWeekView);
        txt1PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtOnePMSlot3InLinear5InWeekView);
        txt1PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtOnePMSlot1InLinear6InWeekView);
        txt1PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtOnePMSlot2InLinear6InWeekView);
        txt1PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtOnePMSlot3InLinear6InWeekView);
        txt1PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtOnePMSlot1InLinear7InWeekView);
        txt1PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtOnePMSlot2InLinear7InWeekView);
        txt1PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtOnePMSlot3InLinear7InWeekView);

        txt2PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot1InLinear1InWeekView);
        txt2PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot2InLinear1InWeekView);
        txt2PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot3InLinear1InWeekView);
        txt2PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot1InLinear2InWeekView);
        txt2PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot2InLinear2InWeekView);
        txt2PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot3InLinear2InWeekView);
        txt2PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot1InLinear3InWeekView);
        txt2PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot2InLinear3InWeekView);
        txt2PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot3InLinear3InWeekView);
        txt2PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot1InLinear4InWeekView);
        txt2PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot2InLinear4InWeekView);
        txt2PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot3InLinear4InWeekView);
        txt2PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot1InLinear5InWeekView);
        txt2PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot2InLinear5InWeekView);
        txt2PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot3InLinear5InWeekView);
        txt2PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot1InLinear6InWeekView);
        txt2PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot2InLinear6InWeekView);
        txt2PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot3InLinear6InWeekView);
        txt2PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot1InLinear7InWeekView);
        txt2PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot2InLinear7InWeekView);
        txt2PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtTwoPMSlot3InLinear7InWeekView);

        txt3PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtThreePMSlot1InLinear1InWeekView);
        txt3PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtThreePMSlot2InLinear1InWeekView);
        txt3PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtThreePMSlot3InLinear1InWeekView);
        txt3PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtThreePMSlot1InLinear2InWeekView);
        txt3PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtThreePMSlot2InLinear2InWeekView);
        txt3PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtThreePMSlot3InLinear2InWeekView);
        txt3PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtThreePMSlot1InLinear3InWeekView);
        txt3PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtThreePMSlot2InLinear3InWeekView);
        txt3PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtThreePMSlot3InLinear3InWeekView);
        txt3PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtThreePMSlot1InLinear4InWeekView);
        txt3PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtThreePMSlot2InLinear4InWeekView);
        txt3PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtThreePMSlot3InLinear4InWeekView);
        txt3PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtThreePMSlot1InLinear5InWeekView);
        txt3PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtThreePMSlot2InLinear5InWeekView);
        txt3PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtThreePMSlot3InLinear5InWeekView);
        txt3PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtThreePMSlot1InLinear6InWeekView);
        txt3PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtThreePMSlot2InLinear6InWeekView);
        txt3PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtThreePMSlot3InLinear6InWeekView);
        txt3PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtThreePMSlot1InLinear7InWeekView);
        txt3PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtThreePMSlot2InLinear7InWeekView);
        txt3PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtThreePMSlot3InLinear7InWeekView);

        txt4PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtFourPMSlot1InLinear1InWeekView);
        txt4PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtFourPMSlot2InLinear1InWeekView);
        txt4PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtFourPMSlot3InLinear1InWeekView);
        txt4PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtFourPMSlot1InLinear2InWeekView);
        txt4PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtFourPMSlot2InLinear2InWeekView);
        txt4PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtFourPMSlot3InLinear2InWeekView);
        txt4PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtFourPMSlot1InLinear3InWeekView);
        txt4PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtFourPMSlot2InLinear3InWeekView);
        txt4PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtFourPMSlot3InLinear3InWeekView);
        txt4PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtFourPMSlot1InLinear4InWeekView);
        txt4PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtFourPMSlot2InLinear4InWeekView);
        txt4PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtFourPMSlot3InLinear4InWeekView);
        txt4PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtFourPMSlot1InLinear5InWeekView);
        txt4PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtFourPMSlot2InLinear5InWeekView);
        txt4PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtFourPMSlot3InLinear5InWeekView);
        txt4PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtFourPMSlot1InLinear6InWeekView);
        txt4PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtFourPMSlot2InLinear6InWeekView);
        txt4PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtFourPMSlot3InLinear6InWeekView);
        txt4PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtFourPMSlot1InLinear7InWeekView);
        txt4PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtFourPMSlot2InLinear7InWeekView);
        txt4PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtFourPMSlot3InLinear7InWeekView);

        txt5PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtFivePMSlot1InLinear1InWeekView);
        txt5PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtFivePMSlot2InLinear1InWeekView);
        txt5PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtFivePMSlot3InLinear1InWeekView);
        txt5PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtFivePMSlot1InLinear2InWeekView);
        txt5PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtFivePMSlot2InLinear2InWeekView);
        txt5PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtFivePMSlot3InLinear2InWeekView);
        txt5PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtFivePMSlot1InLinear3InWeekView);
        txt5PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtFivePMSlot2InLinear3InWeekView);
        txt5PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtFivePMSlot3InLinear3InWeekView);
        txt5PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtFivePMSlot1InLinear4InWeekView);
        txt5PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtFivePMSlot2InLinear4InWeekView);
        txt5PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtFivePMSlot3InLinear4InWeekView);
        txt5PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtFivePMSlot1InLinear5InWeekView);
        txt5PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtFivePMSlot2InLinear5InWeekView);
        txt5PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtFivePMSlot3InLinear5InWeekView);
        txt5PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtFivePMSlot1InLinear6InWeekView);
        txt5PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtFivePMSlot2InLinear6InWeekView);
        txt5PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtFivePMSlot3InLinear6InWeekView);
        txt5PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtFivePMSlot1InLinear7InWeekView);
        txt5PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtFivePMSlot2InLinear7InWeekView);
        txt5PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtFivePMSlot3InLinear7InWeekView);

        txt6PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtSixPMSlot1InLinear1InWeekView);
        txt6PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtSixPMSlot2InLinear1InWeekView);
        txt6PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtSixPMSlot3InLinear1InWeekView);
        txt6PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtSixPMSlot1InLinear2InWeekView);
        txt6PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtSixPMSlot2InLinear2InWeekView);
        txt6PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtSixPMSlot3InLinear2InWeekView);
        txt6PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtSixPMSlot1InLinear3InWeekView);
        txt6PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtSixPMSlot2InLinear3InWeekView);
        txt6PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtSixPMSlot3InLinear3InWeekView);
        txt6PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtSixPMSlot1InLinear4InWeekView);
        txt6PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtSixPMSlot2InLinear4InWeekView);
        txt6PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtSixPMSlot3InLinear4InWeekView);
        txt6PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtSixPMSlot1InLinear5InWeekView);
        txt6PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtSixPMSlot2InLinear5InWeekView);
        txt6PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtSixPMSlot3InLinear5InWeekView);
        txt6PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtSixPMSlot1InLinear6InWeekView);
        txt6PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtSixPMSlot2InLinear6InWeekView);
        txt6PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtSixPMSlot3InLinear6InWeekView);
        txt6PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtSixPMSlot1InLinear7InWeekView);
        txt6PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtSixPMSlot2InLinear7InWeekView);
        txt6PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtSixPMSlot3InLinear7InWeekView);

        txt7PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot1InLinear1InWeekView);
        txt7PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot2InLinear1InWeekView);
        txt7PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot3InLinear1InWeekView);
        txt7PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot1InLinear2InWeekView);
        txt7PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot2InLinear2InWeekView);
        txt7PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot3InLinear2InWeekView);
        txt7PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot1InLinear3InWeekView);
        txt7PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot2InLinear3InWeekView);
        txt7PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot3InLinear3InWeekView);
        txt7PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot1InLinear4InWeekView);
        txt7PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot2InLinear4InWeekView);
        txt7PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot3InLinear4InWeekView);
        txt7PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot1InLinear5InWeekView);
        txt7PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot2InLinear5InWeekView);
        txt7PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot3InLinear5InWeekView);
        txt7PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot1InLinear6InWeekView);
        txt7PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot2InLinear6InWeekView);
        txt7PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot3InLinear6InWeekView);
        txt7PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot1InLinear7InWeekView);
        txt7PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot2InLinear7InWeekView);
        txt7PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtSevenPMSlot3InLinear7InWeekView);

        txt8PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtEightPMSlot1InLinear1InWeekView);
        txt8PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtEightPMSlot2InLinear1InWeekView);
        txt8PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtEightPMSlot3InLinear1InWeekView);
        txt8PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtEightPMSlot1InLinear2InWeekView);
        txt8PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtEightPMSlot2InLinear2InWeekView);
        txt8PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtEightPMSlot3InLinear2InWeekView);
        txt8PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtEightPMSlot1InLinear3InWeekView);
        txt8PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtEightPMSlot2InLinear3InWeekView);
        txt8PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtEightPMSlot3InLinear3InWeekView);
        txt8PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtEightPMSlot1InLinear4InWeekView);
        txt8PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtEightPMSlot2InLinear4InWeekView);
        txt8PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtEightPMSlot3InLinear4InWeekView);
        txt8PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtEightPMSlot1InLinear5InWeekView);
        txt8PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtEightPMSlot2InLinear5InWeekView);
        txt8PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtEightPMSlot3InLinear5InWeekView);
        txt8PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtEightPMSlot1InLinear6InWeekView);
        txt8PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtEightPMSlot2InLinear6InWeekView);
        txt8PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtEightPMSlot3InLinear6InWeekView);
        txt8PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtEightPMSlot1InLinear7InWeekView);
        txt8PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtEightPMSlot2InLinear7InWeekView);
        txt8PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtEightPMSlot3InLinear7InWeekView);

        txt9PmSlot1Linear1 = (TextView) v
                .findViewById(R.id.txtNinePMSlot1InLinear1InWeekView);
        txt9PmSlot2Linear1 = (TextView) v
                .findViewById(R.id.txtNinePMSlot2InLinear1InWeekView);
        txt9PmSlot3Linear1 = (TextView) v
                .findViewById(R.id.txtNinePMSlot3InLinear1InWeekView);
        txt9PmSlot1Linear2 = (TextView) v
                .findViewById(R.id.txtNinePMSlot1InLinear2InWeekView);
        txt9PmSlot2Linear2 = (TextView) v
                .findViewById(R.id.txtNinePMSlot2InLinear2InWeekView);
        txt9PmSlot3Linear2 = (TextView) v
                .findViewById(R.id.txtNinePMSlot3InLinear2InWeekView);
        txt9PmSlot1Linear3 = (TextView) v
                .findViewById(R.id.txtNinePMSlot1InLinear3InWeekView);
        txt9PmSlot2Linear3 = (TextView) v
                .findViewById(R.id.txtNinePMSlot2InLinear3InWeekView);
        txt9PmSlot3Linear3 = (TextView) v
                .findViewById(R.id.txtNinePMSlot3InLinear3InWeekView);
        txt9PmSlot1Linear4 = (TextView) v
                .findViewById(R.id.txtNinePMSlot1InLinear4InWeekView);
        txt9PmSlot2Linear4 = (TextView) v
                .findViewById(R.id.txtNinePMSlot2InLinear4InWeekView);
        txt9PmSlot3Linear4 = (TextView) v
                .findViewById(R.id.txtNinePMSlot3InLinear4InWeekView);
        txt9PmSlot1Linear5 = (TextView) v
                .findViewById(R.id.txtNinePMSlot1InLinear5InWeekView);
        txt9PmSlot2Linear5 = (TextView) v
                .findViewById(R.id.txtNinePMSlot2InLinear5InWeekView);
        txt9PmSlot3Linear5 = (TextView) v
                .findViewById(R.id.txtNinePMSlot3InLinear5InWeekView);
        txt9PmSlot1Linear6 = (TextView) v
                .findViewById(R.id.txtNinePMSlot1InLinear6InWeekView);
        txt9PmSlot2Linear6 = (TextView) v
                .findViewById(R.id.txtNinePMSlot2InLinear6InWeekView);
        txt9PmSlot3Linear6 = (TextView) v
                .findViewById(R.id.txtNinePMSlot3InLinear6InWeekView);
        txt9PmSlot1Linear7 = (TextView) v
                .findViewById(R.id.txtNinePMSlot1InLinear7InWeekView);
        txt9PmSlot2Linear7 = (TextView) v
                .findViewById(R.id.txtNinePMSlot2InLinear7InWeekView);
        txt9PmSlot3Linear7 = (TextView) v
                .findViewById(R.id.txtNinePMSlot3InLinear7InWeekView);

        txtTitleWeek.setTypeface(sessionManager.tfRobotoCondLight);
        txtSun.setTypeface(sessionManager.tfRobotoCondLight);
        txtMon.setTypeface(sessionManager.tfRobotoCondLight);
        txtTue.setTypeface(sessionManager.tfRobotoCondLight);
        txtWed.setTypeface(sessionManager.tfRobotoCondLight);
        txtThu.setTypeface(sessionManager.tfRobotoCondLight);
        txtFri.setTypeface(sessionManager.tfRobotoCondLight);
        txtSat.setTypeface(sessionManager.tfRobotoCondLight);

        txtSunVal.setTypeface(sessionManager.tfRobotoCondLight);
        txtMonVal.setTypeface(sessionManager.tfRobotoCondLight);
        txtTueVal.setTypeface(sessionManager.tfRobotoCondLight);
        txtWedVal.setTypeface(sessionManager.tfRobotoCondLight);
        txtThuVal.setTypeface(sessionManager.tfRobotoCondLight);
        txtFriVal.setTypeface(sessionManager.tfRobotoCondLight);
        txtSatVal.setTypeface(sessionManager.tfRobotoCondLight);

        txt9AmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt9AmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt9AmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt9AmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt9AmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt9AmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt9AmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt10AmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt10AmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt10AmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt10AmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt10AmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt10AmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt10AmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt11AmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt11AmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt11AmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt11AmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt11AmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt11AmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt11AmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt12PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt12PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt12PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt12PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt12PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt12PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt12PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt1PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt1PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt1PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt1PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt1PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt1PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt1PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt2PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt2PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt2PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt2PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt2PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt2PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt2PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt3PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt3PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt3PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt3PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt3PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt3PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt3PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt4PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt4PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt4PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt4PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt4PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt4PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt4PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt5PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt5PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt5PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt5PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt5PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt5PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt5PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt6PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt6PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt6PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt6PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt6PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt6PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt6PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt7PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt7PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt7PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt7PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt7PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt7PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt7PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt8PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt8PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt8PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt8PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt8PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt8PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt8PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);
        txt9PmLinear1.setTypeface(sessionManager.tfRobotoCondLight);
        txt9PmLinear2.setTypeface(sessionManager.tfRobotoCondLight);
        txt9PmLinear3.setTypeface(sessionManager.tfRobotoCondLight);
        txt9PmLinear4.setTypeface(sessionManager.tfRobotoCondLight);
        txt9PmLinear5.setTypeface(sessionManager.tfRobotoCondLight);
        txt9PmLinear6.setTypeface(sessionManager.tfRobotoCondLight);
        txt9PmLinear7.setTypeface(sessionManager.tfRobotoCondLight);

        imgCloseWeek.setOnClickListener(this);
        imgNextWeek.setOnClickListener(this);
        imgPrevWeek.setOnClickListener(this);
        imgCurrentWeek.setOnClickListener(this);
        txt9AmLinear1.setOnClickListener(this);
        txt9AmLinear2.setOnClickListener(this);
        txt9AmLinear3.setOnClickListener(this);
        txt9AmLinear4.setOnClickListener(this);
        txt9AmLinear5.setOnClickListener(this);
        txt9AmLinear6.setOnClickListener(this);
        txt9AmLinear7.setOnClickListener(this);
        txt10AmLinear1.setOnClickListener(this);
        txt10AmLinear2.setOnClickListener(this);
        txt10AmLinear3.setOnClickListener(this);
        txt10AmLinear4.setOnClickListener(this);
        txt10AmLinear5.setOnClickListener(this);
        txt10AmLinear6.setOnClickListener(this);
        txt10AmLinear7.setOnClickListener(this);
        txt11AmLinear1.setOnClickListener(this);
        txt11AmLinear2.setOnClickListener(this);
        txt11AmLinear3.setOnClickListener(this);
        txt11AmLinear4.setOnClickListener(this);
        txt11AmLinear5.setOnClickListener(this);
        txt11AmLinear6.setOnClickListener(this);
        txt11AmLinear7.setOnClickListener(this);
        txt12PmLinear1.setOnClickListener(this);
        txt12PmLinear2.setOnClickListener(this);
        txt12PmLinear3.setOnClickListener(this);
        txt12PmLinear4.setOnClickListener(this);
        txt12PmLinear5.setOnClickListener(this);
        txt12PmLinear6.setOnClickListener(this);
        txt12PmLinear7.setOnClickListener(this);
        txt1PmLinear1.setOnClickListener(this);
        txt1PmLinear2.setOnClickListener(this);
        txt1PmLinear3.setOnClickListener(this);
        txt1PmLinear4.setOnClickListener(this);
        txt1PmLinear5.setOnClickListener(this);
        txt1PmLinear6.setOnClickListener(this);
        txt1PmLinear7.setOnClickListener(this);
        txt2PmLinear1.setOnClickListener(this);
        txt2PmLinear2.setOnClickListener(this);
        txt2PmLinear3.setOnClickListener(this);
        txt2PmLinear4.setOnClickListener(this);
        txt2PmLinear5.setOnClickListener(this);
        txt2PmLinear6.setOnClickListener(this);
        txt2PmLinear7.setOnClickListener(this);
        txt3PmLinear1.setOnClickListener(this);
        txt3PmLinear2.setOnClickListener(this);
        txt3PmLinear3.setOnClickListener(this);
        txt3PmLinear4.setOnClickListener(this);
        txt3PmLinear5.setOnClickListener(this);
        txt3PmLinear6.setOnClickListener(this);
        txt3PmLinear7.setOnClickListener(this);
        txt4PmLinear1.setOnClickListener(this);
        txt4PmLinear2.setOnClickListener(this);
        txt4PmLinear3.setOnClickListener(this);
        txt4PmLinear4.setOnClickListener(this);
        txt4PmLinear5.setOnClickListener(this);
        txt4PmLinear6.setOnClickListener(this);
        txt4PmLinear7.setOnClickListener(this);
        txt5PmLinear1.setOnClickListener(this);
        txt5PmLinear2.setOnClickListener(this);
        txt5PmLinear3.setOnClickListener(this);
        txt5PmLinear4.setOnClickListener(this);
        txt5PmLinear5.setOnClickListener(this);
        txt5PmLinear6.setOnClickListener(this);
        txt5PmLinear7.setOnClickListener(this);
        txt6PmLinear1.setOnClickListener(this);
        txt6PmLinear2.setOnClickListener(this);
        txt6PmLinear3.setOnClickListener(this);
        txt6PmLinear4.setOnClickListener(this);
        txt6PmLinear5.setOnClickListener(this);
        txt6PmLinear6.setOnClickListener(this);
        txt6PmLinear7.setOnClickListener(this);
        txt7PmLinear1.setOnClickListener(this);
        txt7PmLinear2.setOnClickListener(this);
        txt7PmLinear3.setOnClickListener(this);
        txt7PmLinear4.setOnClickListener(this);
        txt7PmLinear5.setOnClickListener(this);
        txt7PmLinear6.setOnClickListener(this);
        txt7PmLinear7.setOnClickListener(this);
        txt8PmLinear1.setOnClickListener(this);
        txt8PmLinear2.setOnClickListener(this);
        txt8PmLinear3.setOnClickListener(this);
        txt8PmLinear4.setOnClickListener(this);
        txt8PmLinear5.setOnClickListener(this);
        txt8PmLinear6.setOnClickListener(this);
        txt8PmLinear7.setOnClickListener(this);
        txt9PmLinear1.setOnClickListener(this);
        txt9PmLinear2.setOnClickListener(this);
        txt9PmLinear3.setOnClickListener(this);
        txt9PmLinear4.setOnClickListener(this);
        txt9PmLinear5.setOnClickListener(this);
        txt9PmLinear6.setOnClickListener(this);
        txt9PmLinear7.setOnClickListener(this);

        txt9AmSlot1Linear1.setOnClickListener(this);
        txt9AmSlot1Linear2.setOnClickListener(this);
        txt9AmSlot1Linear3.setOnClickListener(this);
        txt9AmSlot1Linear4.setOnClickListener(this);
        txt9AmSlot1Linear5.setOnClickListener(this);
        txt9AmSlot1Linear6.setOnClickListener(this);
        txt9AmSlot1Linear7.setOnClickListener(this);
        txt9AmSlot2Linear1.setOnClickListener(this);
        txt9AmSlot2Linear2.setOnClickListener(this);
        txt9AmSlot2Linear3.setOnClickListener(this);
        txt9AmSlot2Linear4.setOnClickListener(this);
        txt9AmSlot2Linear5.setOnClickListener(this);
        txt9AmSlot2Linear6.setOnClickListener(this);
        txt9AmSlot2Linear7.setOnClickListener(this);
        txt9AmSlot3Linear1.setOnClickListener(this);
        txt9AmSlot3Linear2.setOnClickListener(this);
        txt9AmSlot3Linear3.setOnClickListener(this);
        txt9AmSlot3Linear4.setOnClickListener(this);
        txt9AmSlot3Linear5.setOnClickListener(this);
        txt9AmSlot3Linear6.setOnClickListener(this);
        txt9AmSlot3Linear7.setOnClickListener(this);

        txt10AmSlot1Linear1.setOnClickListener(this);
        txt10AmSlot1Linear2.setOnClickListener(this);
        txt10AmSlot1Linear3.setOnClickListener(this);
        txt10AmSlot1Linear4.setOnClickListener(this);
        txt10AmSlot1Linear5.setOnClickListener(this);
        txt10AmSlot1Linear6.setOnClickListener(this);
        txt10AmSlot1Linear7.setOnClickListener(this);
        txt10AmSlot2Linear1.setOnClickListener(this);
        txt10AmSlot2Linear2.setOnClickListener(this);
        txt10AmSlot2Linear3.setOnClickListener(this);
        txt10AmSlot2Linear4.setOnClickListener(this);
        txt10AmSlot2Linear5.setOnClickListener(this);
        txt10AmSlot2Linear6.setOnClickListener(this);
        txt10AmSlot2Linear7.setOnClickListener(this);
        txt10AmSlot3Linear1.setOnClickListener(this);
        txt10AmSlot3Linear2.setOnClickListener(this);
        txt10AmSlot3Linear3.setOnClickListener(this);
        txt10AmSlot3Linear4.setOnClickListener(this);
        txt10AmSlot3Linear5.setOnClickListener(this);
        txt10AmSlot3Linear6.setOnClickListener(this);
        txt10AmSlot3Linear7.setOnClickListener(this);

        txt11AmSlot1Linear1.setOnClickListener(this);
        txt11AmSlot1Linear2.setOnClickListener(this);
        txt11AmSlot1Linear3.setOnClickListener(this);
        txt11AmSlot1Linear4.setOnClickListener(this);
        txt11AmSlot1Linear5.setOnClickListener(this);
        txt11AmSlot1Linear6.setOnClickListener(this);
        txt11AmSlot1Linear7.setOnClickListener(this);
        txt11AmSlot2Linear1.setOnClickListener(this);
        txt11AmSlot2Linear2.setOnClickListener(this);
        txt11AmSlot2Linear3.setOnClickListener(this);
        txt11AmSlot2Linear4.setOnClickListener(this);
        txt11AmSlot2Linear5.setOnClickListener(this);
        txt11AmSlot2Linear6.setOnClickListener(this);
        txt11AmSlot2Linear7.setOnClickListener(this);
        txt11AmSlot3Linear1.setOnClickListener(this);
        txt11AmSlot3Linear2.setOnClickListener(this);
        txt11AmSlot3Linear3.setOnClickListener(this);
        txt11AmSlot3Linear4.setOnClickListener(this);
        txt11AmSlot3Linear5.setOnClickListener(this);
        txt11AmSlot3Linear6.setOnClickListener(this);
        txt11AmSlot3Linear7.setOnClickListener(this);

        txt12PmSlot1Linear1.setOnClickListener(this);
        txt12PmSlot1Linear2.setOnClickListener(this);
        txt12PmSlot1Linear3.setOnClickListener(this);
        txt12PmSlot1Linear4.setOnClickListener(this);
        txt12PmSlot1Linear5.setOnClickListener(this);
        txt12PmSlot1Linear6.setOnClickListener(this);
        txt12PmSlot1Linear7.setOnClickListener(this);
        txt12PmSlot2Linear1.setOnClickListener(this);
        txt12PmSlot2Linear2.setOnClickListener(this);
        txt12PmSlot2Linear3.setOnClickListener(this);
        txt12PmSlot2Linear4.setOnClickListener(this);
        txt12PmSlot2Linear5.setOnClickListener(this);
        txt12PmSlot2Linear6.setOnClickListener(this);
        txt12PmSlot2Linear7.setOnClickListener(this);
        txt12PmSlot3Linear1.setOnClickListener(this);
        txt12PmSlot3Linear2.setOnClickListener(this);
        txt12PmSlot3Linear3.setOnClickListener(this);
        txt12PmSlot3Linear4.setOnClickListener(this);
        txt12PmSlot3Linear5.setOnClickListener(this);
        txt12PmSlot3Linear6.setOnClickListener(this);
        txt12PmSlot3Linear7.setOnClickListener(this);

        txt1PmSlot1Linear1.setOnClickListener(this);
        txt1PmSlot1Linear2.setOnClickListener(this);
        txt1PmSlot1Linear3.setOnClickListener(this);
        txt1PmSlot1Linear4.setOnClickListener(this);
        txt1PmSlot1Linear5.setOnClickListener(this);
        txt1PmSlot1Linear6.setOnClickListener(this);
        txt1PmSlot1Linear7.setOnClickListener(this);
        txt1PmSlot2Linear1.setOnClickListener(this);
        txt1PmSlot2Linear2.setOnClickListener(this);
        txt1PmSlot2Linear3.setOnClickListener(this);
        txt1PmSlot2Linear4.setOnClickListener(this);
        txt1PmSlot2Linear5.setOnClickListener(this);
        txt1PmSlot2Linear6.setOnClickListener(this);
        txt1PmSlot2Linear7.setOnClickListener(this);
        txt1PmSlot3Linear1.setOnClickListener(this);
        txt1PmSlot3Linear2.setOnClickListener(this);
        txt1PmSlot3Linear3.setOnClickListener(this);
        txt1PmSlot3Linear4.setOnClickListener(this);
        txt1PmSlot3Linear5.setOnClickListener(this);
        txt1PmSlot3Linear6.setOnClickListener(this);
        txt1PmSlot3Linear7.setOnClickListener(this);

        txt2PmSlot1Linear1.setOnClickListener(this);
        txt2PmSlot1Linear2.setOnClickListener(this);
        txt2PmSlot1Linear3.setOnClickListener(this);
        txt2PmSlot1Linear4.setOnClickListener(this);
        txt2PmSlot1Linear5.setOnClickListener(this);
        txt2PmSlot1Linear6.setOnClickListener(this);
        txt2PmSlot1Linear7.setOnClickListener(this);
        txt2PmSlot2Linear1.setOnClickListener(this);
        txt2PmSlot2Linear2.setOnClickListener(this);
        txt2PmSlot2Linear3.setOnClickListener(this);
        txt2PmSlot2Linear4.setOnClickListener(this);
        txt2PmSlot2Linear5.setOnClickListener(this);
        txt2PmSlot2Linear6.setOnClickListener(this);
        txt2PmSlot2Linear7.setOnClickListener(this);
        txt2PmSlot3Linear1.setOnClickListener(this);
        txt2PmSlot3Linear2.setOnClickListener(this);
        txt2PmSlot3Linear3.setOnClickListener(this);
        txt2PmSlot3Linear4.setOnClickListener(this);
        txt2PmSlot3Linear5.setOnClickListener(this);
        txt2PmSlot3Linear6.setOnClickListener(this);
        txt2PmSlot3Linear7.setOnClickListener(this);

        txt3PmSlot1Linear1.setOnClickListener(this);
        txt3PmSlot1Linear2.setOnClickListener(this);
        txt3PmSlot1Linear3.setOnClickListener(this);
        txt3PmSlot1Linear4.setOnClickListener(this);
        txt3PmSlot1Linear5.setOnClickListener(this);
        txt3PmSlot1Linear6.setOnClickListener(this);
        txt3PmSlot1Linear7.setOnClickListener(this);
        txt3PmSlot2Linear1.setOnClickListener(this);
        txt3PmSlot2Linear2.setOnClickListener(this);
        txt3PmSlot2Linear3.setOnClickListener(this);
        txt3PmSlot2Linear4.setOnClickListener(this);
        txt3PmSlot2Linear5.setOnClickListener(this);
        txt3PmSlot2Linear6.setOnClickListener(this);
        txt3PmSlot2Linear7.setOnClickListener(this);
        txt3PmSlot3Linear1.setOnClickListener(this);
        txt3PmSlot3Linear2.setOnClickListener(this);
        txt3PmSlot3Linear3.setOnClickListener(this);
        txt3PmSlot3Linear4.setOnClickListener(this);
        txt3PmSlot3Linear5.setOnClickListener(this);
        txt3PmSlot3Linear6.setOnClickListener(this);
        txt3PmSlot3Linear7.setOnClickListener(this);

        txt4PmSlot1Linear1.setOnClickListener(this);
        txt4PmSlot1Linear2.setOnClickListener(this);
        txt4PmSlot1Linear3.setOnClickListener(this);
        txt4PmSlot1Linear4.setOnClickListener(this);
        txt4PmSlot1Linear5.setOnClickListener(this);
        txt4PmSlot1Linear6.setOnClickListener(this);
        txt4PmSlot1Linear7.setOnClickListener(this);
        txt4PmSlot2Linear1.setOnClickListener(this);
        txt4PmSlot2Linear2.setOnClickListener(this);
        txt4PmSlot2Linear3.setOnClickListener(this);
        txt4PmSlot2Linear4.setOnClickListener(this);
        txt4PmSlot2Linear5.setOnClickListener(this);
        txt4PmSlot2Linear6.setOnClickListener(this);
        txt4PmSlot2Linear7.setOnClickListener(this);
        txt4PmSlot3Linear1.setOnClickListener(this);
        txt4PmSlot3Linear2.setOnClickListener(this);
        txt4PmSlot3Linear3.setOnClickListener(this);
        txt4PmSlot3Linear4.setOnClickListener(this);
        txt4PmSlot3Linear5.setOnClickListener(this);
        txt4PmSlot3Linear6.setOnClickListener(this);
        txt4PmSlot3Linear7.setOnClickListener(this);

        txt5PmSlot1Linear1.setOnClickListener(this);
        txt5PmSlot1Linear2.setOnClickListener(this);
        txt5PmSlot1Linear3.setOnClickListener(this);
        txt5PmSlot1Linear4.setOnClickListener(this);
        txt5PmSlot1Linear5.setOnClickListener(this);
        txt5PmSlot1Linear6.setOnClickListener(this);
        txt5PmSlot1Linear7.setOnClickListener(this);
        txt5PmSlot2Linear1.setOnClickListener(this);
        txt5PmSlot2Linear2.setOnClickListener(this);
        txt5PmSlot2Linear3.setOnClickListener(this);
        txt5PmSlot2Linear4.setOnClickListener(this);
        txt5PmSlot2Linear5.setOnClickListener(this);
        txt5PmSlot2Linear6.setOnClickListener(this);
        txt5PmSlot2Linear7.setOnClickListener(this);
        txt5PmSlot3Linear1.setOnClickListener(this);
        txt5PmSlot3Linear2.setOnClickListener(this);
        txt5PmSlot3Linear3.setOnClickListener(this);
        txt5PmSlot3Linear4.setOnClickListener(this);
        txt5PmSlot3Linear5.setOnClickListener(this);
        txt5PmSlot3Linear6.setOnClickListener(this);
        txt5PmSlot3Linear7.setOnClickListener(this);

        txt6PmSlot1Linear1.setOnClickListener(this);
        txt6PmSlot1Linear2.setOnClickListener(this);
        txt6PmSlot1Linear3.setOnClickListener(this);
        txt6PmSlot1Linear4.setOnClickListener(this);
        txt6PmSlot1Linear5.setOnClickListener(this);
        txt6PmSlot1Linear6.setOnClickListener(this);
        txt6PmSlot1Linear7.setOnClickListener(this);
        txt6PmSlot2Linear1.setOnClickListener(this);
        txt6PmSlot2Linear2.setOnClickListener(this);
        txt6PmSlot2Linear3.setOnClickListener(this);
        txt6PmSlot2Linear4.setOnClickListener(this);
        txt6PmSlot2Linear5.setOnClickListener(this);
        txt6PmSlot2Linear6.setOnClickListener(this);
        txt6PmSlot2Linear7.setOnClickListener(this);
        txt6PmSlot3Linear1.setOnClickListener(this);
        txt6PmSlot3Linear2.setOnClickListener(this);
        txt6PmSlot3Linear3.setOnClickListener(this);
        txt6PmSlot3Linear4.setOnClickListener(this);
        txt6PmSlot3Linear5.setOnClickListener(this);
        txt6PmSlot3Linear6.setOnClickListener(this);
        txt6PmSlot3Linear7.setOnClickListener(this);

        txt7PmSlot1Linear1.setOnClickListener(this);
        txt7PmSlot1Linear2.setOnClickListener(this);
        txt7PmSlot1Linear3.setOnClickListener(this);
        txt7PmSlot1Linear4.setOnClickListener(this);
        txt7PmSlot1Linear5.setOnClickListener(this);
        txt7PmSlot1Linear6.setOnClickListener(this);
        txt7PmSlot1Linear7.setOnClickListener(this);
        txt7PmSlot2Linear1.setOnClickListener(this);
        txt7PmSlot2Linear2.setOnClickListener(this);
        txt7PmSlot2Linear3.setOnClickListener(this);
        txt7PmSlot2Linear4.setOnClickListener(this);
        txt7PmSlot2Linear5.setOnClickListener(this);
        txt7PmSlot2Linear6.setOnClickListener(this);
        txt7PmSlot2Linear7.setOnClickListener(this);
        txt7PmSlot3Linear1.setOnClickListener(this);
        txt7PmSlot3Linear2.setOnClickListener(this);
        txt7PmSlot3Linear3.setOnClickListener(this);
        txt7PmSlot3Linear4.setOnClickListener(this);
        txt7PmSlot3Linear5.setOnClickListener(this);
        txt7PmSlot3Linear6.setOnClickListener(this);
        txt7PmSlot3Linear7.setOnClickListener(this);

        txt8PmSlot1Linear1.setOnClickListener(this);
        txt8PmSlot1Linear2.setOnClickListener(this);
        txt8PmSlot1Linear3.setOnClickListener(this);
        txt8PmSlot1Linear4.setOnClickListener(this);
        txt8PmSlot1Linear5.setOnClickListener(this);
        txt8PmSlot1Linear6.setOnClickListener(this);
        txt8PmSlot1Linear7.setOnClickListener(this);
        txt8PmSlot2Linear1.setOnClickListener(this);
        txt8PmSlot2Linear2.setOnClickListener(this);
        txt8PmSlot2Linear3.setOnClickListener(this);
        txt8PmSlot2Linear4.setOnClickListener(this);
        txt8PmSlot2Linear5.setOnClickListener(this);
        txt8PmSlot2Linear6.setOnClickListener(this);
        txt8PmSlot2Linear7.setOnClickListener(this);
        txt8PmSlot3Linear1.setOnClickListener(this);
        txt8PmSlot3Linear2.setOnClickListener(this);
        txt8PmSlot3Linear3.setOnClickListener(this);
        txt8PmSlot3Linear4.setOnClickListener(this);
        txt8PmSlot3Linear5.setOnClickListener(this);
        txt8PmSlot3Linear6.setOnClickListener(this);
        txt8PmSlot3Linear7.setOnClickListener(this);

        txt9PmSlot1Linear1.setOnClickListener(this);
        txt9PmSlot1Linear2.setOnClickListener(this);
        txt9PmSlot1Linear3.setOnClickListener(this);
        txt9PmSlot1Linear4.setOnClickListener(this);
        txt9PmSlot1Linear5.setOnClickListener(this);
        txt9PmSlot1Linear6.setOnClickListener(this);
        txt9PmSlot1Linear7.setOnClickListener(this);
        txt9PmSlot2Linear1.setOnClickListener(this);
        txt9PmSlot2Linear2.setOnClickListener(this);
        txt9PmSlot2Linear3.setOnClickListener(this);
        txt9PmSlot2Linear4.setOnClickListener(this);
        txt9PmSlot2Linear5.setOnClickListener(this);
        txt9PmSlot2Linear6.setOnClickListener(this);
        txt9PmSlot2Linear7.setOnClickListener(this);
        txt9PmSlot3Linear1.setOnClickListener(this);
        txt9PmSlot3Linear2.setOnClickListener(this);
        txt9PmSlot3Linear3.setOnClickListener(this);
        txt9PmSlot3Linear4.setOnClickListener(this);
        txt9PmSlot3Linear5.setOnClickListener(this);
        txt9PmSlot3Linear6.setOnClickListener(this);
        txt9PmSlot3Linear7.setOnClickListener(this);
    }

    public void highLightTimeInWeekView(String date, int column) {
        Log.i("myLog", "highLightTimeInWeekView");
        Log.i("myLog", "date:" + date + "   column:" + column);
        int size = sessionManager.alAppointmentDate.size();
        String time = null, slot = null;
        for (int index = 0; index < size; index++) {
            String dateStr = sessionManager.alAppointmentDate.get(index);
            if (date.equals(dateStr)) {
                time = alAppointmentTime.get(index);
                slot = alAppointmentSlots.get(index);
                Log.i("mylog", "slot:" + slot);
                if (!slot.equalsIgnoreCase("0")) {
                    ViewGroup vg = (ViewGroup) linearTimeParent
                            .getChildAt(column);
                    LinearLayout linear = (LinearLayout) vg;
                    if (time.equals("09:00 AM")) {
                        highLightSlots(linear, 0, slot);
                    } else if (time.equals("10:00 AM")) {
                        highLightSlots(linear, 1, slot);
                    } else if (time.equals("11:00 AM")) {
                        highLightSlots(linear, 2, slot);
                    } else if (time.equals("12:00 PM")) {
                        highLightSlots(linear, 3, slot);
                    } else if (time.equals("01:00 PM")) {
                        highLightSlots(linear, 4, slot);
                    } else if (time.equals("02:00 PM")) {
                        highLightSlots(linear, 5, slot);
                    } else if (time.equals("03:00 PM")) {
                        highLightSlots(linear, 6, slot);
                    } else if (time.equals("04:00 PM")) {
                        highLightSlots(linear, 7, slot);
                    } else if (time.equals("05:00 PM")) {
                        highLightSlots(linear, 8, slot);
                    } else if (time.equals("06:00 PM")) {
                        highLightSlots(linear, 9, slot);
                    } else if (time.equals("07:00 PM")) {
                        highLightSlots(linear, 10, slot);
                    } else if (time.equals("08:00 PM")) {
                        highLightSlots(linear, 11, slot);
                    } else if (time.equals("09:00 PM")) {
                        highLightSlots(linear, 12, slot);
                    }
                }
            }
        }
    }

    public void highLightSlots(LinearLayout linear, int index, String slot) {
        Log.i("mylog", "highLightSlots");
        ViewGroup vgLinear4 = (ViewGroup) linear.getChildAt(index);
        View v = vgLinear4.getChildAt(0);
        TextView txt = (TextView) v;
        txt.setTextColor(Color.parseColor("#BF1C26"));
        ViewGroup vgSlots = (ViewGroup) vgLinear4.getChildAt(1);
        if (slot.contains("1")) {
            View v1 = vgSlots.getChildAt(0);
            TextView txtView = (TextView) v1;
            txtView.setBackgroundColor(Color.parseColor("#BF1C26"));
        }
        if (slot.contains("2")) {
            View v1 = vgSlots.getChildAt(1);
            TextView txtView = (TextView) v1;
            txtView.setBackgroundColor(Color.parseColor("#BF1C26"));
        }
        if (slot.contains("3")) {
            View v1 = vgSlots.getChildAt(2);
            TextView txtView = (TextView) v1;
            txtView.setBackgroundColor(Color.parseColor("#BF1C26"));
        }
    }

    public void setDateTimeForAppointment(View v, String date, String time) {
        Log.i("mylog", "set date and Time");
        showAppointmentForm(date, time);
        appointmentEventHandler();
    }

    public void showAppointmentForm(String date, String time) {
        relAppointmentForm.removeAllViews();
        relAppointmentForm.setVisibility(View.VISIBLE);
        relAppointmentForm.setBackgroundColor(Color.argb(220, 255, 255, 255));
        View v = sessionManager.inflater.inflate(R.layout.appointment_form,
                relAppointmentForm, false);
        relAppointmentForm.addView(v);
        relAppointmentForm.setOnClickListener(this);
        initializeAppointmentViews(v, date, time);
        getDataFromTimeSlot(time, date);
    }

    public void appointmentEventHandler() {
        txtTimeVal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.showAtLocation(relAppointmentForm, Gravity.NO_GRAVITY, datePopupXPos,
                        datePopupYPos);
                String date=txtDateVal.getText().toString();
                String time=txtTimeVal.getText().toString();
                String[] dateArr=date.split(" ");
                txtDaySel.setText(dateArr[0]);
                txtMonthSel.setText(dateArr[1]);
                String[] timeArr=time.split(" ");
                txtTimeDateSel.setText(timeArr[0]);
                txtTimeAmPmDateSel.setText(timeArr[1]);

            }
        });
        txtDateVal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.showAtLocation(relAppointmentForm, Gravity.NO_GRAVITY, datePopupXPos,
                        datePopupYPos);
                String date=txtDateVal.getText().toString();
                String time=txtTimeVal.getText().toString();
                String[] dateArr=date.split(" ");
                txtDaySel.setText(dateArr[0]);
                txtMonthSel.setText(dateArr[1]);
                String[] timeArr=time.split(" ");
                txtTimeDateSel.setText(timeArr[0]);
                txtTimeAmPmDateSel.setText(timeArr[1]);


            }
        });
        txtNameSlot1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = txtNameSlot1.getText().toString();
                edtTxtName.setText(name);
                selectedSlot = 1;
            }
        });
        txtNameSlot2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = txtNameSlot2.getText().toString();
                edtTxtName.setText(name);
                selectedSlot = 2;
            }
        });
        txtNameSlot3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = txtNameSlot3.getText().toString();
                edtTxtName.setText(name);
                selectedSlot = 3;
            }
        });
        imgCloseAppointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                relAppointmentForm.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager) sessionManager.context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtTxtName.getWindowToken(), 0);
            }
        });
        imgSet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = edtTxtName.getText().toString(), date = txtDateVal
                        .getText().toString(), time = txtTimeVal.getText()
                        .toString();
                Log.i("myLog", "Selected Slot:" + selectedSlot);
                int size = selectedSlotsArr.length;
                // if (size > 0) {
                String[] arr=time.split(" ");
                Log.i("myLog", "Selected arr[0]:" + arr[0]+"   arr[1]:"+arr[1]);
                String[] a=arr[0].split(":");
                Log.i("myLog", "Selected a[0]:" + a[0]+"   a[1]:"+a[1]);
                time=a[0]+":00 "+arr[1];
                for (int start = 0; start < selectedSlotsArr.length; start++) {
                    checkAppointmentDataInDb(date, time, start + 1);
                }
                showCalendarDaysInGrid();
                // } else {
                // Toast.makeText(sessionManager.context, "Select any slot.. ",
                // Toast.LENGTH_SHORT).show();
                // }
                InputMethodManager imm = (InputMethodManager) sessionManager.context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtTxtName.getWindowToken(), 0);
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("myLog", "Cancel onclick");
                String name = edtTxtName.getText().toString(), date = txtDateVal
                        .getText().toString(), time = txtTimeVal.getText()
                        .toString();
                try {
                    boolean result = sessionManager.databaseIf.delQuery(
                            sessionManager.databaseIf.appointmentTbl,
                            sessionManager.databaseIf.appointmentDate
                                    + " =? AND "
                                    + sessionManager.databaseIf.appointmentTime
                                    + " =? AND "
                                    + sessionManager.databaseIf.appointmentSlot
                                    + " =?",
                            new String[]{date, time,
                                    String.valueOf(selectedSlot)});
                    relAppointmentForm.setVisibility(View.INVISIBLE);
                    relWeekCalendar.setVisibility(View.INVISIBLE);
                    showCalendarDaysInGrid();
                    InputMethodManager imm = (InputMethodManager) sessionManager.context
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtTxtName.getWindowToken(), 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        imgReschedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = edtTxtName.getText().toString(), date = txtDateVal
                        .getText().toString(), time = txtTimeVal.getText()
                        .toString();
                String[] arr=time.split(" ");
                String[] a=arr[0].split(":");
                time=a[0]+":00 "+arr[1];
                Log.i("myLog", "Selected Slot:" + selectedSlot);
                for (int start = 0; start < selectedSlotsArr.length; start++) {
                    checkAppointmentDataInDb(date, time, start + 1);
                }
                showCalendarDaysInGrid();
            }
        });
        chkBoxSlot1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                Log.i("myLog", "Slot1 Checked:" + isChecked);
                selectedSlotsArr[0] = isChecked;
                selectedSlot = 1;
            }
        });
        chkBoxSlot2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                Log.i("myLog", "Slot2 Checked:" + isChecked);
                selectedSlotsArr[1] = isChecked;
                selectedSlot = 2;
            }
        });
        chkBoxSlot3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                Log.i("myLog", "Slot3 Checked:" + isChecked);
                selectedSlotsArr[2] = isChecked;
                selectedSlot = 3;
            }
        });
    }

    public void initializeAppointmentViews(View v, String date, String time) {
        Log.i("myLog", "initializeAppointmentViews:");
        txtName = (TextView) v.findViewById(R.id.txtNameInAppointment);
        txtTime = (TextView) v.findViewById(R.id.txtTimeInAppointment);
        txtTimeVal = (TextView) v.findViewById(R.id.txtTimeVal);
        txtDateVal = (TextView) v.findViewById(R.id.txtDateVal);
        edtTxtName = (EditText) v.findViewById(R.id.edtTxtNameInAppointment);
        imgSet = (ImageView) v.findViewById(R.id.btnSetInAppoinment);
        imgCancel = (ImageView) v.findViewById(R.id.btnCancelInAppoinment);
        imgCloseAppointment = (ImageView) v
                .findViewById(R.id.imgCloseInAppointment);
        imgReschedule = (ImageView) v
                .findViewById(R.id.btnRescheduleInAppoinment);
        txtNameSlot1 = (TextView) v
                .findViewById(R.id.txtNameSlot1InAppointment);
        txtNameSlot2 = (TextView) v
                .findViewById(R.id.txtNameSlot2InAppointment);
        txtNameSlot3 = (TextView) v
                .findViewById(R.id.txtNameSlot3InAppointment);
        txtTimeSlot1 = (TextView) v
                .findViewById(R.id.txtTimeSlot1InAppointment);
        txtTimeSlot2 = (TextView) v
                .findViewById(R.id.txtTimeSlot2InAppointment);
        txtTimeSlot3 = (TextView) v
                .findViewById(R.id.txtTimeSlot3InAppointment);
        txtReschedule = (TextView) v
                .findViewById(R.id.txtRescheduleInAppoinment);
        txtSet = (TextView) v.findViewById(R.id.txtSetInAppoinment);
        txtCancel = (TextView) v.findViewById(R.id.txtCancelInAppoinment);
        txtViewSlot1 = (TextView) v
                .findViewById(R.id.txtViewSlot1InAppointment);
        txtViewSlot2 = (TextView) v
                .findViewById(R.id.txtViewSlot2InAppointment);
        txtViewSlot3 = (TextView) v
                .findViewById(R.id.txtViewSlot3InAppointment);
        chkBoxSlot1 = (CheckBox) v.findViewById(R.id.chkBoxSlot1InAppointment);
        chkBoxSlot2 = (CheckBox) v.findViewById(R.id.chkBoxSlot2InAppointment);
        chkBoxSlot3 = (CheckBox) v.findViewById(R.id.chkBoxSlot3InAppointment);
        txtName.setTypeface(sessionManager.tfRobotoCondLight, Typeface.BOLD);
        txtTime.setTypeface(sessionManager.tfRobotoCondLight, Typeface.BOLD);
        txtTimeVal.setTypeface(sessionManager.tfRobotoCondLight);
        txtDateVal.setTypeface(sessionManager.tfRobotoCondLight);
        txtSet.setTypeface(sessionManager.tfRobotoCondLight);
        txtReschedule.setTypeface(sessionManager.tfRobotoCondLight);
        txtCancel.setTypeface(sessionManager.tfRobotoCondLight);
        txtViewSlot2.setTypeface(sessionManager.tfRobotoCondLight);
        txtViewSlot3.setTypeface(sessionManager.tfRobotoCondLight);
        txtViewSlot1.setTypeface(sessionManager.tfRobotoCondLight);
        txtNameSlot1.setTypeface(sessionManager.tfRobotoCondLight);
        txtNameSlot2.setTypeface(sessionManager.tfRobotoCondLight);
        txtNameSlot3.setTypeface(sessionManager.tfRobotoCondLight);
        txtTimeSlot1.setTypeface(sessionManager.tfRobotoCondLight);
        txtTimeSlot2.setTypeface(sessionManager.tfRobotoCondLight);
        txtTimeSlot3.setTypeface(sessionManager.tfRobotoCondLight);
        edtTxtName.setTypeface(sessionManager.tfRobotoCondLight);
        edtTxtName.requestFocus();
        InputMethodManager imm = (InputMethodManager) sessionManager.context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtTxtName, InputMethodManager.SHOW_IMPLICIT);
        txtTimeVal.setText(time);
        String[] arr=time.split(" ");
        String[] arTime=arr[0].split(":");
        txtTimeSlot1.setText(arTime[0]+":00 "+arr[1]);
        txtTimeSlot2.setText(arTime[0]+":20 "+arr[1]);
        txtTimeSlot3.setText(arTime[0]+":40 "+arr[1]);
        txtDateVal.setText(date);
        imgReschedule.setImageDrawable(svgReschedule);
        imgCancel.setImageDrawable(svgCancel);
        imgSet.setImageDrawable(svgSet);
        imgCloseAppointment.setImageDrawable(svgCloseWhite);
    }

    private void addAppointmentToDb(String name, String date, String time,
                                    String slot, String status) {
        ContentValues cv = new ContentValues();
        Log.i("myLog", "name:" + name + " date:" + date + " time:" + time
                + " slot:" + slot + " status:" + status);
        cv.put(sessionManager.databaseIf.appointmentPatientName, name);
        cv.put(sessionManager.databaseIf.appointmentDate, date.trim());
        cv.put(sessionManager.databaseIf.appointmentTime, time);
        cv.put(sessionManager.databaseIf.appointmentSlot, slot);
        cv.put(sessionManager.databaseIf.appointmentStatus, status);
        sessionManager.databaseIf.insertData(
                sessionManager.databaseIf.appointmentTbl, cv);
    }

    private void addAppointmentToCloud(String name, String date, String time,
                                       String slot, String status) {
        ParseObject appointmentObj = new ParseObject("DoctorAppointment");
        Log.i("myLog", "name:" + name + " date:" + date + " time:" + time
                + " slot:" + slot + " status:" + status);
        appointmentObj.put(sessionManager.databaseIf.appointmentPatientName,
                name);
        appointmentObj.put(sessionManager.databaseIf.appointmentDate,
                date.trim());
        appointmentObj.put(sessionManager.databaseIf.appointmentTime, time);
        appointmentObj.put(sessionManager.databaseIf.appointmentSlot, slot);
        appointmentObj.put(sessionManager.databaseIf.appointmentStatus, status);
        // appointmentObj.put(sessionManager.databaseIf.appointmentContactNo,
        // contact);
        appointmentObj.saveEventually();
    }

    public void getAppointmentNames(String date, String time) {
        selectedAppointmentId = 0;
        String query = "SELECT * FROM "
                + sessionManager.databaseIf.appointmentTbl + " WHERE "
                + sessionManager.databaseIf.appointmentDate + "='" + date
                + "' AND " + sessionManager.databaseIf.appointmentTime + "='"
                + time + "'";
        Log.i("myLog", "query:" + query);
        Cursor c = sessionManager.databaseIf.query(query);
        int size = c.getCount();
        Log.i("myLog", "size:" + size);
        String name = "";
        int slot = 0;
        for (int start = 0; start < size; start++) {
            if (start == 0) {
                name = c.getString(2);
                slot = c.getInt(1);
                selectedAppointmentId = c.getInt(0);
            } else {
                name = name + "," + c.getString(2);
                selectedAppointmentId = c.getInt(0);
            }
            c.moveToNext();
        }
        c.close();
        edtTxtName.setText(name);
    }

    public void showCalendarDaysInGrid() {
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        itemmonth = (GregorianCalendar) month.clone();
        items = new ArrayList<String>();
        String monthToCmp = (String) android.text.format.DateFormat.format(
                "MMM yyyy", month);
        Log.i("myLog", "Month to cmp:" + monthToCmp);
        getDaysFromAppointmentTbl(monthToCmp);
        adapter = new CalendarAdapter(sessionManager.context, month);
        gridView.setAdapter(adapter);
        handler = new Handler();
        handler.post(calendarUpdater);
        txtMonthTitle.setText(android.text.format.DateFormat.format(
                "MMMM yyyy", month));
        eventHandlers();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view,
                                    final int position, long id) {
                // TODO Auto-generated method stub
                selectedParent = parent;
                selectedPosition = position;
                String selDate = parent.getItemAtPosition(position).toString();
                Log.i("myLog", "gridView onItemClick SelDate:" + selDate);
                Cursor c = getAppointmentNamesByDate(selDate);
                if (c.getCount() > 0) {
                    showAppointmentNamesByDate(c, parent, position);
                } else {
                    showWeeklyData(parent, position, "Current");
                }
            }
        });
    }

    public class ViewPagerAdapter extends PagerAdapter {
        // Declare Variables
        private Context context;
        private AdapterView<?> parent;
        private View view;
        private int position;
        private LayoutInflater inflater;

        public ViewPagerAdapter(Context context, AdapterView<?> parent,
                                View view, int position) {
            this.context = context;
            this.parent = parent;
            this.view = view;
            this.position = position;

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((FrameLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Declare Variables

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.weekly_view, container, false);
            // Add viewpager_item.xml to ViewPager
            ((ViewPager) container).addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((FrameLayout) object);

        }
    }

    public Cursor getAppointmentNamesByDate(String date) {
        relAppointmentNames.setVisibility(View.VISIBLE);
        txtDateName.setText(date);
        String str = "SELECT * FROM "
                + sessionManager.databaseIf.appointmentTbl + " WHERE "
                + sessionManager.databaseIf.appointmentDate + "='" + date
                + "' AND " + sessionManager.databaseIf.appointmentSlot + "!=0";
        Cursor c = sessionManager.databaseIf.query(str);
        // int size = c.getCount();
        return c;
    }

    public void showAppointmentNamesByDate(Cursor c,
                                           final AdapterView<?> parent, final int position) {
        int size = c.getCount();
        TableRow[] tr = new TableRow[size], trLine = new TableRow[size];
        TextView[] txtName = new TextView[size];
        TextView[] txtTime = new TextView[size], txtLine = new TextView[size];
        TextView[] txtSlot = new TextView[size];
        tblAppointmentNames.removeAllViews();
        for (int start = 0; start < size; start++) {
            Log.i("mylog", "for index:" + start);
            String name = c.getString(3);
            String time = c.getString(5);
            String slot = c.getString(2);
            tr[start] = new TableRow(sessionManager.context);
            txtName[start] = new TextView(sessionManager.context);
            txtTime[start] = new TextView(sessionManager.context);
            txtSlot[start] = new TextView(sessionManager.context);
            txtName[start].setText(name);
            txtName[start].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            txtName[start].setGravity(Gravity.CENTER_VERTICAL);
            txtName[start].setTextColor(Color.BLACK);
            txtName[start].setTypeface(sessionManager.tfRobotoCondLight);
            txtTime[start].setText(time);
            txtTime[start].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            txtTime[start].setGravity(Gravity.CENTER);
            txtTime[start].setTextColor(Color.BLACK);
            txtTime[start].setTypeface(sessionManager.tfRobotoCondLight);
            txtSlot[start].setText(slot);
            txtSlot[start].setGravity(Gravity.CENTER);
            txtSlot[start].setTextColor(Color.BLACK);
            txtSlot[start].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            txtSlot[start].setTypeface(sessionManager.tfRobotoCondLight);
            txtLine[start] = new TextView(sessionManager.context);
            txtLine[start].setText(time);
            txtLine[start].setGravity(Gravity.CENTER_VERTICAL);
            txtLine[start].setBackgroundColor(Color.parseColor("#D7D4C1"));
            txtLine[start].setTypeface(sessionManager.tfRobotoCondLight);
            trLine[start] = new TableRow(sessionManager.context);
            tr[start].addView(txtName[start], nameParam);
            tr[start].addView(txtTime[start], timeParam);
            tr[start].addView(txtSlot[start], slotParam);
            trLine[start].addView(txtLine[start], lineParam);
            tblAppointmentNames.addView(tr[start]);
            tblAppointmentNames.addView(trLine[start]);
            tr[start].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    relAppointmentNames.setVisibility(View.INVISIBLE);
                    showWeeklyData(parent, position, "Current");
                }
            });
            c.moveToNext();
        }
        c.close();
    }

    public void viewPagerWeekView(View v, int page) {
        selectedWeekDays = new ArrayList<String>();
        relAppointmentNames.setVisibility(View.INVISIBLE);
        relWeekCalendar.setVisibility(View.VISIBLE);
        initWeeklyViews(v, selectedPosition);
        Log.i("myLog",
                "Parent7877878:" + selectedParent.getChildAt(selectedPosition)
                        + "   tuyuytuytu  "
                        + selectedParent.getItemAtPosition(selectedPosition));
        int column = selectedPosition % 7;
        int row = selectedPosition / 7;
        String item = selectedParent.getItemAtPosition(selectedPosition)
                .toString();
        String[] str = item.split(" ");
        int day = Integer.parseInt(str[0]);
        int dayCalc = day;
        View v1 = linearDate.getChildAt(column);
        TextView txt = (TextView) v1;
        txt.setText(item);
        Log.i("myLog", "Column:" + column + "   row:" + row);
        int i = 0;
        int j = 1;
        if (page == 1) {
            Log.i("myLog", "page===============1");
            for (int start = column - 1; start >= 0; start--) {
                int index = (row * 7) + start;
                String gridItem = selectedParent.getItemAtPosition(index)
                        .toString();
                selectedWeekDays.add(gridItem);
                View v2 = linearDate.getChildAt(start);
                TextView txtView = (TextView) v2;
                txtView.setText(gridItem);
                txtView.setTypeface(sessionManager.tfRobotoCondLight);
                // dayCalc = dayCalc - j;
                String strDay = String.valueOf(dayCalc - j);
                if (strDay.length() == 1) {
                    strDay = "0" + strDay;
                }
                highLightTimeInWeekView(gridItem, start);
                j++;
            }
            for (int start = column; start < 7; start++) {
                int index = (row * 7) + start;
                String gridItem = selectedParent.getItemAtPosition(index)
                        .toString();
                selectedWeekDays.add(gridItem);
                View v2 = linearDate.getChildAt(start);
                TextView txtView = (TextView) v2;
                txtView.setText(gridItem);
                txtView.setTypeface(sessionManager.tfRobotoCondLight);
                highLightTimeInWeekView(gridItem, start);
                i++;
            }
            Collections.sort(selectedWeekDays);
            if (column == 0) {
                linear1Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 1) {
                linear2Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 2) {
                linear3Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 3) {
                linear4Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 4) {
                linear5Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 5) {
                linear6Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 6) {
                linear7Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            }
        } else if (page == 0) {
            Log.i("myLog", "page===============0");
            for (int start = 0; start < 7; start++) {
                int index = (row - 1) + start;
                String gridItem = selectedParent.getItemAtPosition(index)
                        .toString();
                selectedWeekDays.add(gridItem);
                View v2 = linearDate.getChildAt(start);
                TextView txtView = (TextView) v2;
                txtView.setText(gridItem);
                txtView.setTypeface(sessionManager.tfRobotoCondLight);
                highLightTimeInWeekView(gridItem, start);
            }

        } else if (page == 2) {
            Log.i("myLog", "page===============2");
            for (int start = 0; start < 7; start++) {
                int index = (row + 1) + start;
                String gridItem = selectedParent.getItemAtPosition(index)
                        .toString();
                selectedWeekDays.add(gridItem);
                View v2 = linearDate.getChildAt(start);
                TextView txtView = (TextView) v2;
                txtView.setText(gridItem);
                txtView.setTypeface(sessionManager.tfRobotoCondLight);
                highLightTimeInWeekView(gridItem, start);
            }
        }
    }

    public void showWeeklyData(AdapterView<?> parent, int position,
                               String option) {

        int column = position % 7;
        int row = position / 7;
        String item = parent.getItemAtPosition(position).toString();
        String[] str = item.split(" ");
        int day = Integer.parseInt(str[0]);
        int dayCalc = day;

        Log.i("myLog", "Column:" + column + "   row:" + row);
        int i = 0;
        int j = 1;
        if (option.equalsIgnoreCase("Next")) {
            int total = parent.getCount();
            int totRow = total / 7;
            if (total % 7 != 0) {
                totRow = totRow + 1;
            }
            int ro = row + 1;
            Log.i("myLog", "totRow:" + totRow + "  row+1:" + ro);
            if ((row + 1) < totRow) {
                selectedWeekDays = new ArrayList<String>();
                relAppointmentNames.setVisibility(View.INVISIBLE);
                relWeekCalendar.setVisibility(View.VISIBLE);
                relWeekCalendar.removeAllViews();
                View v = layoutInflater.inflate(R.layout.weekly_view,
                        relWeekCalendar, false);
                relWeekCalendar.addView(v);
                initWeeklyViews(v, position);

                View v1 = linearDate.getChildAt(column);
                TextView txt = (TextView) v1;
                txt.setText(item);

                Log.i("myLog",
                        "Parent7877878:" + parent.getChildAt(position)
                                + "   tuyuytuytu  "
                                + parent.getItemAtPosition(position));
                for (int start = 0; start < 7; start++) {
                    int index = ((row + 1) * 7) + start;
                    String gridItem = parent.getItemAtPosition(index)
                            .toString();
                    selectedWeekDays.add(gridItem);
                    View v2 = linearDate.getChildAt(start);
                    TextView txtView = (TextView) v2;
                    txtView.setText(gridItem);
                    txtView.setTypeface(sessionManager.tfRobotoCondLight);
                    highLightTimeInWeekView(gridItem, start);
                }
            } else {
                Toast.makeText(sessionManager.context,
                        "No more next week in this month.", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (option.equalsIgnoreCase("Prev")) {
            if ((row - 1) > 0) {
                selectedWeekDays = new ArrayList<String>();
                relAppointmentNames.setVisibility(View.INVISIBLE);
                relWeekCalendar.setVisibility(View.VISIBLE);
                relWeekCalendar.removeAllViews();
                View v = layoutInflater.inflate(R.layout.weekly_view,
                        relWeekCalendar, false);
                relWeekCalendar.addView(v);
                initWeeklyViews(v, position);
                View v1 = linearDate.getChildAt(column);
                TextView txt = (TextView) v1;
                txt.setText(item);
                Log.i("myLog",
                        "Parent7877878:" + parent.getChildAt(position)
                                + "   tuyuytuytu  "
                                + parent.getItemAtPosition(position));
                for (int start = 0; start < 7; start++) {
                    int index = ((row - 1) * 7) + start;
                    String gridItem = parent.getItemAtPosition(index)
                            .toString();
                    selectedWeekDays.add(gridItem);
                    View v2 = linearDate.getChildAt(start);
                    TextView txtView = (TextView) v2;
                    txtView.setText(gridItem);
                    txtView.setTypeface(sessionManager.tfRobotoCondLight);
                    highLightTimeInWeekView(gridItem, start);
                }
            } else {
                Toast.makeText(sessionManager.context,
                        "No more previous week in this month.",
                        Toast.LENGTH_SHORT).show();
            }

        } else if (option.equalsIgnoreCase("Current")) {
            selectedWeekDays = new ArrayList<String>();
            relAppointmentNames.setVisibility(View.INVISIBLE);
            relWeekCalendar.setVisibility(View.VISIBLE);
            relWeekCalendar.removeAllViews();
            View v = layoutInflater.inflate(R.layout.weekly_view,
                    relWeekCalendar, false);
            relWeekCalendar.addView(v);
            initWeeklyViews(v, position);
            View v1 = linearDate.getChildAt(column);
            TextView txt = (TextView) v1;
            txt.setText(item);
            Log.i("myLog", "Parent7877878:" + parent.getChildAt(position)
                    + "   tuyuytuytu  " + parent.getItemAtPosition(position));
            int childCount = parent.getChildCount();
            int count = parent.getCount();
            Log.i("myLog", "childCount:" + childCount + "  count:" + count);
            for (int start = column - 1; start >= 0; start--) {
                int index = (row * 7) + start;
                String gridItem = parent.getItemAtPosition(index).toString();
                selectedWeekDays.add(gridItem);
                View v2 = linearDate.getChildAt(start);
                TextView txtView = (TextView) v2;
                txtView.setText(gridItem);
                txtView.setTypeface(sessionManager.tfRobotoCondLight);
                // dayCalc = dayCalc - j;
                String strDay = String.valueOf(dayCalc - j);
                if (strDay.length() == 1) {
                    strDay = "0" + strDay;
                }
                highLightTimeInWeekView(gridItem, start);
                j++;
            }
            for (int start = column; start < 7; start++) {
                int index = (row * 7) + start;
                String gridItem = parent.getItemAtPosition(index).toString();
                selectedWeekDays.add(gridItem);
                View v2 = linearDate.getChildAt(start);
                TextView txtView = (TextView) v2;
                txtView.setText(gridItem);
                txtView.setTypeface(sessionManager.tfRobotoCondLight);
                highLightTimeInWeekView(gridItem, start);
                i++;
            }
            Collections.sort(selectedWeekDays);
            if (column == 0) {
                linear1Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 1) {
                linear2Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 2) {
                linear3Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 3) {
                linear4Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 4) {
                linear5Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 5) {
                linear6Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            } else if (column == 6) {
                linear7Week.setBackgroundColor(Color.parseColor("#D7D4C1"));
            }
        }
        txtTitleWeek.setText(txtMonthTitle.getText().toString());
    }

    public void getDataFromTimeSlot(String time, String date) {
        String query = "SELECT * FROM "
                + sessionManager.databaseIf.appointmentTbl + " WHERE "
                + sessionManager.databaseIf.appointmentTime + "='" + time
                + "' AND " + sessionManager.databaseIf.appointmentDate + "='"
                + date + "'";
        Cursor c = sessionManager.databaseIf.query(query);
        int size = c.getCount();
        Log.i("myLog", "Count:" + size);
        for (int index = 0; index < size; index++) {
            String name = c.getString(3);
            String contact = c.getString(1);
            String slotNo = c.getString(2);
            Log.i("myLog", "name:" + name + "  slotNo:" + slotNo);
            String[] arr=time.split(" ");
            String[] ar=arr[0].split(":");

            txtTimeSlot1.setText(time);
            txtTimeSlot2.setText(ar[0]+":20 "+arr[1]);
            txtTimeSlot3.setText(ar[0]+":40 "+arr[1]);
            if (slotNo.contains("1")) {
                chkBoxSlot1.setChecked(true);
                selectedSlotsArr[0] = true;
                txtNameSlot1.setText(name);

            }
            if (slotNo.contains("2")) {
                chkBoxSlot2.setChecked(true);
                selectedSlotsArr[1] = true;
                txtNameSlot2.setText(name);
            }
            if (slotNo.contains("3")) {
                chkBoxSlot3.setChecked(true);
                selectedSlotsArr[2] = true;
                txtNameSlot3.setText(name);
            }
            c.moveToNext();
        }
        c.close();
    }

    public void checkAppointmentDataInDb(String date, String time, int slot) {
        Log.i("myLog", "checkAppointmentDataInDb");
        String str = "SELECT * FROM "
                + sessionManager.databaseIf.appointmentTbl + " WHERE "
                + sessionManager.databaseIf.appointmentDate + "='" + date
                + "' AND " + sessionManager.databaseIf.appointmentTime + "='"
                + time + "' AND " + sessionManager.databaseIf.appointmentSlot
                + "='" + slot + "'";
        Log.i("myLog", "query:" + str);
        Cursor c = sessionManager.databaseIf.query(str);
        int size = c.getCount();
        Log.i("myLog", "Size:" + size);
        boolean matched = false;
        selectedNameAL = new ArrayList<String>();
        selectedSlotAL = new ArrayList<String>();
        selectedContactNoAL = new ArrayList<String>();
        if (size > 0) {
            for (int start = 0; start < size; start++) {
                String slotNo = c.getString(2);
                String name = c.getString(3);
                String no = c.getString(1);
                selectedNameAL.add(name);
                selectedSlotAL.add(slotNo);
                selectedContactNoAL.add(no);
                if (slotNo.equalsIgnoreCase("1")) {
                    if (chkBoxSlot1.isChecked()) {
                        updateAppointmentData(date, time, 1, 1, txtNameSlot1
                                .getText().toString());
                    } else {
                        updateAppointmentData(date, time, 1, 0, txtNameSlot1
                                .getText().toString());
                    }
                } else if (slotNo.equalsIgnoreCase("2")) {
                    if (chkBoxSlot2.isChecked()) {
                        updateAppointmentData(date, time, 2, 2, txtNameSlot2
                                .getText().toString());
                    } else {
                        updateAppointmentData(date, time, 2, 0, txtNameSlot2
                                .getText().toString());
                    }
                } else if (slotNo.equalsIgnoreCase("3")) {
                    if (chkBoxSlot3.isChecked()) {
                        updateAppointmentData(date, time, 3, 3, txtNameSlot3
                                .getText().toString());
                    } else {
                        updateAppointmentData(date, time, 3, 0, txtNameSlot3
                                .getText().toString());
                    }
                }
                c.moveToNext();
            }
            c.close();
            Log.i("myLog", "matched:" + matched);
        } else {
            if (slot == 1) {
                if (chkBoxSlot1.isChecked()) {
                    addAppointmentData(date, time, slot);
                }
            } else if (slot == 2) {
                if (chkBoxSlot2.isChecked()) {
                    addAppointmentData(date, time, slot);
                }
            } else if (slot == 3) {
                if (chkBoxSlot3.isChecked()) {
                    addAppointmentData(date, time, slot);
                }
            }
        }
    }

    public void addAppointmentData(String dateStr, String timeStr, int slot) {
        Log.i("myLog", "addAppointmentData");
        String name = edtTxtName.getText().toString();
        Log.i("myLog", "Slott:" + slot);
        if (!name.equalsIgnoreCase("")) {
            name = sessionManager.toTitleCase(name);
            addAppointmentToDb(name, dateStr, timeStr, String.valueOf(slot), "Set");
            addAppointmentToCloud(name, dateStr, timeStr,
                    String.valueOf(selectedSlot), "Set");
            relAppointmentForm.setVisibility(View.INVISIBLE);
            relWeekCalendar.setVisibility(View.INVISIBLE);
            showCalendarDaysInGrid();
        } else {
            Toast.makeText(sessionManager.context, "Enter Name ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateAppointmentData(String dateStr, String timeStr,
                                      int updateSlot, int slotNo, String name) {

        if (!name.equalsIgnoreCase("")) {
            name = sessionManager.toTitleCase(name);
            Log.i("myLog", "selectedAppointmentId:" + selectedAppointmentId);
            ContentValues cv = new ContentValues();
            cv.put(sessionManager.databaseIf.appointmentPatientName, name);
            cv.put(sessionManager.databaseIf.appointmentStatus, "Reset");
            cv.put(sessionManager.databaseIf.appointmentDate, dateStr);
            cv.put(sessionManager.databaseIf.appointmentTime, timeStr);
            cv.put(sessionManager.databaseIf.appointmentSlot, slotNo);
            boolean result = sessionManager.databaseIf
                    .updateQuery(sessionManager.databaseIf.appointmentTbl, cv,
                            sessionManager.databaseIf.appointmentDate
                                    + " =? AND "
                                    + sessionManager.databaseIf.appointmentTime
                                    + " =? AND "
                                    + sessionManager.databaseIf.appointmentSlot
                                    + " =?", new String[]{dateStr, timeStr,
                                    String.valueOf(updateSlot)});
            Log.i("mylog", "Result reschedule:" + result);
            relAppointmentForm.setVisibility(View.INVISIBLE);
            relWeekCalendar.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(sessionManager.context, "Enter Name ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void dateTimePopup() {
        LayoutInflater    layoutInflater = (LayoutInflater) sessionManager.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.appointment_date_sel, null);
        popupWindow = new PopupWindow(popupView, datePopupWidth,
                datePopupHeight);
        imgSaveDateSel = (ImageView) popupView
                .findViewById(R.id.imgSaveInAppointmentDateSel);
        imgCloseDateSel = (ImageView) popupView
                .findViewById(R.id.imgCloseInAppointmentDateSel);
        imgDateSel = (ImageView) popupView
                .findViewById(R.id.imgDateInAppointmentDateSel);
        imgTimeDateSel = (ImageView) popupView
                .findViewById(R.id.imgTimeInAppointmentDateSel);
        txtTimeAmPmDateSel = (TextView) popupView
                .findViewById(R.id.txtTimeAmpmInAppointmentDateSel);
        txtTimeDateSel = (TextView) popupView
                .findViewById(R.id.txtTimeInAppointmentDateSel);
        txtDaySel = (TextView) popupView.findViewById(R.id.txtDayInAppointmentDateSel);
        txtMonthSel = (TextView) popupView
                .findViewById(R.id.txtMonthInAppointmentDateSel);
        seekbarDate = (SeekBar) popupView
                .findViewById(R.id.seekbarInAppointmentDateSel);
        txtTimeAmPmDateSel.setTypeface(sessionManager.tfRobotoCondLight);
        txtDaySel.setTypeface(sessionManager.tfRobotoCondLight);
        txtTimeDateSel.setTypeface(sessionManager.tfRobotoCondLight);
        txtMonthSel.setTypeface(sessionManager.tfRobotoCondLight);
        imgSaveDateSel.setImageDrawable(sessionManager.svgSave);
        imgCloseDateSel.setImageDrawable(sessionManager.svgClose);
        imgDateSel.setImageDrawable(sessionManager.svgDaysWhite);
        imgTimeDateSel.setImageDrawable(sessionManager.svgTimeWhite);
        String date = new SimpleDateFormat("dd MMM yy").format(new Date());
        String[] arr = date.split(" ");
        txtDaySel.setText(arr[0]);
        String month = arr[1];
        txtMonthSel.setText(arr[1].toUpperCase());
        year = arr[2];
        String time = sessionManager.getCurrentTime();
        String timeArr[] = time.split(" ");
        txtTimeDateSel.setText(timeArr[0]);
        txtTimeAmPmDateSel.setText(" " + timeArr[1].toUpperCase());
        imgTimeDateSel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                selectedOption = "Time";
                seekbarDate.setMax(720);
                txtTimeAmPmDateSel.setTextColor(Color.parseColor("#05509E"));
                txtTimeDateSel.setTextColor(Color.parseColor("#05509E"));
                txtMonthSel.setTextColor(Color.parseColor("#BF1C26"));
                txtDaySel.setTextColor(Color.parseColor("#BF1C26"));
                imgTimeDateSel.setImageDrawable(sessionManager.svgTimeBlue);
            }
        });
        imgCloseDateSel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });
        imgSaveDateSel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String day = txtDaySel.getText().toString(), month = txtMonthSel
                        .getText().toString();
                selDate = day + " " + month + " " + year;
                selTime = txtTimeDateSel.getText().toString()+" "
                        + txtTimeAmPmDateSel.getText().toString();
                  //  selCategory = getCategoryFromTime(selTime);
                txtTimeVal.setText(selTime);
                txtDateVal.setText(selDate);
                showAppointmentForm(selDate, selTime);
                popupWindow.dismiss();
            }
        });
        txtDaySel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Log.i("myLog", "Day onClick");
                selectedOption = "Day";
                seekbarDate.setMax(31);
                imgTimeDateSel.setImageDrawable(sessionManager.svgTimeRed);
                txtTimeDateSel.setTextColor(Color.parseColor("#BF1C26"));
                txtTimeAmPmDateSel.setTextColor(Color.parseColor("#BF1C26"));
                txtDaySel.setTextColor(Color.parseColor("#05509E"));
                txtMonthSel.setTextColor(Color.parseColor("#BF1C26"));
            }
        });
        txtMonthSel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                selectedOption = "Month";
                seekbarDate.setMax(12);
                Log.i("myLog", "Month onClick");
                imgTimeDateSel.setImageDrawable(sessionManager.svgTimeRed);
                txtTimeDateSel.setTextColor(Color.parseColor("#BF1C26"));
                txtTimeAmPmDateSel.setTextColor(Color.parseColor("#BF1C26"));
                txtMonthSel.setTextColor(Color.parseColor("#05509E"));
                txtDaySel.setTextColor(Color.parseColor("#BF1C26"));
            }
        });
        txtTimeAmPmDateSel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String text = txtTimeAmPmDateSel.getText().toString();
                if (text.contains("AM")) {
                    txtTimeAmPmDateSel.setText("PM");
                } else if (text.contains("PM")) {
                    txtTimeAmPmDateSel.setText("AM");
                }
                imgTimeDateSel.setImageDrawable(sessionManager.svgTimeBlue);
                txtTimeAmPmDateSel.setTextColor(Color.parseColor("#05509E"));
                txtTimeDateSel.setTextColor(Color.parseColor("#05509E"));
            }
        });
        seekbarDate.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar bar, int arg1, boolean arg2) {
                // TODO Auto-generated method stub
                Log.i("myLog", "seekbar");
                int progress = bar.getProgress();
                Log.i("myLog", "progress:" + progress);
                Log.i("myLog", "selectedDietOption:" + selectedOption);
                if (selectedOption.equalsIgnoreCase("Day")) {
                    txtDaySel.setText(String.valueOf(progress));
                    Log.i("myLog", "txtDay");
                } else if (selectedOption.equalsIgnoreCase("Month")) {
                    String month = "";
                    if (progress == 1) {
                        month = "Jan";
                    } else if (progress == 2) {
                        month = "Feb";
                    } else if (progress == 3) {
                        month = "Mar";
                    } else if (progress == 4) {
                        month = "Apr";
                    } else if (progress == 5) {
                        month = "May";
                    } else if (progress == 6) {
                        month = "Jun";
                    } else if (progress == 7) {
                        month = "Jul";
                    } else if (progress == 8) {
                        month = "Aug";
                    } else if (progress == 9) {
                        month = "Sep";
                    } else if (progress == 10) {
                        month = "Oct";
                    } else if (progress == 11) {
                        month = "Nov";
                    } else if (progress == 12) {
                        month = "Dec";
                    }
                    txtMonthSel.setText(month);
                    Log.i("myLog", "txtMonth");
                } else if (selectedOption.equalsIgnoreCase("Time")) {
                    int hr = progress / 60;
                    if (hr != 12) {
                        hr = hr + 1;
                    }
                    String hrStr = String.valueOf(hr);
                    if (hrStr.length() == 1) {
                        hrStr = "0" + hrStr;
                    }
                    int min = progress % 60;
                    String minStr = String.valueOf(min);
                    if (minStr.length() == 1) {
                        minStr = "0" + minStr;
                    }
                    Log.i("myLog", hrStr + ":" + minStr);
                    txtTimeDateSel.setText(hrStr + ":" + minStr);
                }
            }
        });

    }
}
