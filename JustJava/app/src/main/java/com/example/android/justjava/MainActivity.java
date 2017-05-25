package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.order;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view){
        if(numberOfCoffees ==100){
            Toast.makeText(this, "You cannot have more than 100 Coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees = numberOfCoffees+1;
        display(numberOfCoffees);

    }

    public void decrement(View view){
        if(numberOfCoffees==1) {
            Toast.makeText(this, "You cannot have less than 1 Coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees = numberOfCoffees - 1;
        display(numberOfCoffees);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String summary = getString(R.string.order_summary_name, name);
        summary = summary + "\n"+ getString(R.string.order_summary_whipped_cream, addWhippedCream);
        summary = summary + "\n"+ getString(R.string.order_summary_chocolate, addChocolate);
        summary = summary+ "\n"+ getString(R.string.order_summary_quantity, numberOfCoffees);
        summary = summary+ "\n"+ getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        summary = summary+ "\n"+ getString(R.string.thank_you);
        return summary;
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        int basePrice = 5;
        if(hasWhippedCream){
            basePrice = basePrice + 1;
        }

        if(hasChocolate){
            basePrice = basePrice +2;
        }
        return numberOfCoffees*basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}