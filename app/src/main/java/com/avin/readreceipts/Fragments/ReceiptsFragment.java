package com.avin.readreceipts.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avin.readreceipts.Bookkeeping.RealmWrapper;
import com.avin.readreceipts.R;

public class ReceiptsFragment extends Fragment {

    private TextView titleText;
    private RealmWrapper realm;
    private static final String OPTION_NUMBER = "option_number";

    public ReceiptsFragment() {

    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new ReceiptsFragment();
        Bundle args = new Bundle();
        args.putInt(OPTION_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.receipts_fragment, container, false);
        realm = new RealmWrapper(getActivity().getApplicationContext());

        int pos = getArguments().getInt(OPTION_NUMBER);
        String option = getResources().getStringArray(R.array.drawer_options)[pos];

        titleText = (TextView) rootView.findViewById(R.id.receipts_title);

        getActivity().setTitle(option);
        displayReceipts();
        return rootView;
    }

    private void displayReceipts() {
        StringBuilder receiptsText = new StringBuilder();
        realm.queryAllExpenses(receiptsText);
        titleText.setText(receiptsText.toString());
    }

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroyView() {
        realm.close();
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }
}
