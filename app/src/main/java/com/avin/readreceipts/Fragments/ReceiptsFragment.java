package com.avin.readreceipts.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avin.readreceipts.Bookkeeping.Expense;
import com.avin.readreceipts.Bookkeeping.RealmWrapper;
import com.avin.readreceipts.R;

import java.util.List;

public class ReceiptsFragment extends Fragment {

    private TextView titleText;
    private RealmWrapper realm;
    private static final String OPTION_NUMBER = "option_number";
    private LinearLayout receiptsList;

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
        receiptsList = rootView.findViewById(R.id.receipts_container);
        realm = new RealmWrapper(getActivity().getApplicationContext());

        int pos = getArguments().getInt(OPTION_NUMBER);
        String option = getResources().getStringArray(R.array.drawer_options)[pos];

        titleText = (TextView) rootView.findViewById(R.id.receipts_title);

        getActivity().setTitle(option);
        displayReceipts();
        return rootView;
    }

    private void displayReceipts() {
        receiptsList.removeAllViews(); // Clear previous views if any

        // Assuming `queryAllExpenses()` returns a List<Expense>
        List<Expense> expenses = realm.queryAllExpenses();

        for (Expense expense : expenses) {
            // Create a container for each expense
            LinearLayout expenseView = new LinearLayout(getActivity());
            expenseView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            expenseView.setOrientation(LinearLayout.VERTICAL);
            expenseView.setPadding(8, 8, 8, 8);
            expenseView.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

            // Add details for the expense
            TextView dateText = new TextView(getActivity());
            dateText.setText("Date: " + expense.getDate().toString());
            dateText.setTextSize(16);
            dateText.setGravity(Gravity.START);

            TextView categoryText = new TextView(getActivity());
            categoryText.setText("Category: " + expense.getCategory());
            categoryText.setTextSize(16);
            categoryText.setGravity(Gravity.START);

            TextView storeText = new TextView(getActivity());
            storeText.setText("Store: " + expense.getStore());
            storeText.setTextSize(16);
            storeText.setGravity(Gravity.START);

            TextView totalText = new TextView(getActivity());
            totalText.setText("Total: $" + expense.getTotal());
            totalText.setTextSize(16);
            totalText.setGravity(Gravity.START);

            // Add TextViews to the expense container
            expenseView.addView(dateText);
            expenseView.addView(categoryText);
            expenseView.addView(storeText);
            expenseView.addView(totalText);

            // Add border
            expenseView.setBackground(getResources().getDrawable(R.drawable.border));

            // Add the expense container to the main container
            receiptsList.addView(expenseView);
        }
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
