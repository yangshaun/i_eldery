package com.example.jamesdouble.i_elederly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link setting_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link setting_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class setting_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Bundle savedState = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String str1;
    EditText k;
    ProgressBar pbAuto;
    private OnFragmentInteractionListener mListener;

    public setting_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment setting_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static setting_fragment newInstance(String param1, String param2) {
        setting_fragment fragment = new setting_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_setting_fragment, null);


      /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
       String[] lunch = {"父親", "母親", "爺爺", "奶奶"};
        ArrayAdapter<String> lunchList;
        lunchList = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, lunch);
        spinner.setAdapter(lunchList);

      ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        pbAuto = (ProgressBar) view.findViewById(R.id.progressBar);
        final Handler handler = new Handler();
        // 次要顯示處理程式
        final Runnable callback1 = new Runnable() {
            @Override
            public void run() {
                pbAuto.incrementSecondaryProgressBy(10);

            }
        };
        final Runnable callback2 = new Runnable() {
            @Override
            public void run() {
                pbAuto.incrementProgressBy(10);
            }
        };

        Button connecting = (Button) view.findViewById(R.id.connectbutton);
        connecting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final Thread thread = new Thread() {

                    @Override
                    public void run() {
                        // 讓進度條不斷自動跑
                        try {
                            while (true) {
                                // 當進度條滿後，將進度歸零
                                if (pbAuto.getProgress() == pbAuto.getMax()) {
                                    pbAuto.incrementSecondaryProgressBy(-100);
                                    pbAuto.incrementProgressBy(-100);

                                   // Toast.makeText(view.getContext(),"Connected",Toast.LENGTH_LONG);
                                    break;
                                }
                                // 先跑SecondaryProgress
                                handler.post(callback1);
                                // 休息200 milliseconds
                                Thread.sleep(200);
                                // 在跑Progress
                                handler.post(callback2);
                                // 休息200 milliseconds
                                Thread.sleep(200);
                            }

                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                };
                thread.start();
            }
        });

        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("save","detach");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("save","pause");
        k = (EditText) getView().findViewById(R.id.telephone);
        str1 = k.getText().toString();

        EditText did = (EditText) getView().findViewById(R.id.deviceid);
        String str2 = k.getText().toString();

        EditText key = (EditText) getView().findViewById(R.id.devicekey);
        String str3 = key.getText().toString();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("tele", str1); // here string is the value you want to save
        editor.putString("id",str2);
        editor.putString("key",str3);
        editor.commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("save","attach now~");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("save","OC");
        View view = getView();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String telephone = settings.getString("tele", "");
        String deviceid = settings.getString("id","");
        String key = settings.getString("key","");
        if (telephone.length()>0) {
            k = (EditText) view.findViewById(R.id.telephone);
            k.setText(telephone);
        }
        if(deviceid.length()>0)
        {
            k = (EditText) view.findViewById(R.id.deviceid);
            k.setText(deviceid);
        }
        if(key.length()>0)
        {
            k = (EditText) view.findViewById(R.id.devicekey);
            k.setText(key);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("save","save now");
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
