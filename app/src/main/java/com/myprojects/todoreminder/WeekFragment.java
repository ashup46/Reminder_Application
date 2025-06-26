package com.myprojects.todoreminder;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class WeekFragment extends BottomSheetDialogFragment {

   private CheckedTextView monChkd,tueChkd,wedChkd,thuChkd,friChkd,satChkd,sunChkd;
   private AppCompatButton submitBtn;
   private ClickOnListner mListner;

private String[] weeks = {"","","","","","",""};

    public  interface ClickOnListner
    {
        void onSetListner(String[] weekList);
    }

    public void setClickListner(ClickOnListner listner)
    {
        this.mListner = listner;
    }



    public WeekFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        monChkd = view.findViewById(R.id.everyMondayId);
        tueChkd = view.findViewById(R.id.everyTuesdayId);
        wedChkd = view.findViewById(R.id.everyWednesdayId);
        thuChkd = view.findViewById(R.id.everyThursdayId);
        friChkd = view.findViewById(R.id.everyFridayId);
        satChkd = view.findViewById(R.id.everySaturdayId);
        sunChkd = view.findViewById(R.id.everySundayId);
        submitBtn = view.findViewById(R.id.submitWeekId);

//      This Method is for Setting up all the Listners
        listnersForAll();

        return view;
    }

    private void listnersForAll() {

        sunChkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sunChkd.toggle();
                if (sunChkd.isChecked())
                {
                    weeks[0] = "Sun";
                }
                else
                {
                    weeks[0] = "";

                }
            }
        });

        monChkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monChkd.toggle();
                if (monChkd.isChecked())
                {
                    weeks[1] = "Mon";

                }
                else
                {
                    weeks[1] = "";

                }
            }
        });
        tueChkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tueChkd.toggle();
                if (tueChkd.isChecked())
                {
                    weeks[2] = "Tue";
                }
                else
                {

                    weeks[2] = "";
                }
            }
        });
        wedChkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wedChkd.toggle();
                if (wedChkd.isChecked())
                {
                    weeks[3] = "Wed";
                }
                else
                    weeks[3] = "";
            }
        });
        thuChkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                thuChkd.toggle();
                if (thuChkd.isChecked())
                {
                    weeks[4] = "Thu";
                }
                else
                {
                    weeks[4] = "";

                }
            }
        });
        friChkd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                friChkd.toggle();
                if (friChkd.isChecked())
                {
                    weeks[5] = "Fri";
                }
                else
                {
                    weeks[5] = "";

                }
            }
        });
        satChkd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                satChkd.toggle();
                if (satChkd.isChecked())
                {
                    weeks[6] = "Sat";
                }
                else
                {
                    weeks[6] = "";

                }
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("weekList", weeks[0] );

                if (mListner!= null)
                {
                    mListner.onSetListner(weeks);
                    Log.d("weekList", weeks[0] );
                }

//                I need to work from here and put the data in the week editText and by going back
                dismiss();
            }
        });

    }


}