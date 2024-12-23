package com.avin.readreceipts.Bookkeeping;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmWrapper {

    private Realm realm;

    /**
     * Init realmDB
     */
    public RealmWrapper(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.removeDefaultConfiguration();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
    }

    public void close() {
        realm.close();
    }

    /**
     * Create expense object from total and store into Realm
     */
    public void createExpense(final String result) {
        realm.executeTransactionAsync(bgRealm -> {
            Expense expense = bgRealm.createObject(Expense.class);
            expense.setTotal(result);
            expense.setCategory("Groceries");
            expense.setStore("Target");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                date = sdf.parse("07/06/2017");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            expense.setDate(date);
            expense.setCreatedAt(new Date());
        }, () -> {
            // Success callback
            Log.d("RealmWrapper", "Expense saved successfully");
            queryExpense();
        }, error -> {
            // Error callback
            Log.e("RealmWrapper", "Failed to save expense", error);
        });
    }

    /**
     * Simple query with search parameters
     */
    public String queryExpense() {
        RealmResults<Expense> results = realm.where(Expense.class).equalTo("store", "Target").findAll();
        String target_expense = "No Query Results";
        for (Expense expense : results) {
            target_expense = ("Total: " + expense.getTotal() + "\n");
            target_expense += ("Category: " + expense.getCategory() + "\n");
            target_expense += ("Store: " + expense.getStore() + "\n");
            target_expense += ("Date: " + expense.getDate().toString() + "\n");
            target_expense += ("Created At: " + expense.getCreatedAt() + "\n");
            target_expense += ("\n");

        }
        System.out.println("Query Size: " + results.size());
        return target_expense;
        //TODO Chart Data
    }

    // Query all expenses from Realm
    public void queryAllExpenses(StringBuilder receiptsText) {
        realm.executeTransactionAsync(bgRealm -> {
            RealmResults<Expense> expenses = bgRealm.where(Expense.class).findAll();

            if (expenses.isEmpty()) {
                receiptsText.append("No receipts found.");
            } else {
                for (Expense expense : expenses) {
                    receiptsText.append("Store: ").append(expense.getStore())
                            .append("\nCategory: ").append(expense.getCategory())
                            .append("\nTotal: ").append(expense.getTotal())
                            .append("\nDate: ").append(expense.getDate())
                            .append("\n\n");
                }
            }
        });
    }
}