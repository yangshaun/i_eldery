package com.example.jamesdouble.i_elederly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link index_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link index_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class index_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static Boolean redornot = false;

    BroadcastReceiver receiver;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public index_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment index_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static index_fragment newInstance(String param1, String param2) {
        index_fragment fragment = new index_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.e("save","index_frag");
        getActivity().startService(new Intent(getActivity(),checkalarm.class));

        receiver = new BroadcastReceiver() {     //接收紅燈通知
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra("alarm");
                // do something here.
                Log.e("emotion",s);
                ImageView light = (ImageView) getView().findViewById(R.id.imageView);
                light.setImageResource(R.drawable.red);
                redornot = true;
            }
        };





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_index_fragment, null);

        // Inflate the layout for this fragment
        Calendar c = Calendar.getInstance();

        if(redornot == true)
        {
            ImageView light = (ImageView) view.findViewById(R.id.imageView);
            light.setImageResource(R.drawable.red);
        }


        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        if(minute<15)
        {minute=0;}
        else if(minute <30 && minute>14)
        {minute= 15;}
        else if(minute <45 && minute>29)
        {minute= 30;}
        else if(minute <60 && minute>44)
        {minute= 45;}

        TextView t = (TextView) view.findViewById(R.id.timetext);
        String r = String.valueOf(hour)+":"+String.valueOf(minute);
        t.setText(r);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((receiver), new IntentFilter("result"));

        Button dial = (Button) view.findViewById(R.id.dialbutton);
        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redornot = false;
                ImageView light = (ImageView) getView().findViewById(R.id.imageView);
                light.setImageResource(R.drawable.green);
                clearalarmphp clear = new clearalarmphp();   //看過後 就清空
                clear.checkDatabase();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+"0937071410"));
                startActivity(callIntent);

            }
        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
