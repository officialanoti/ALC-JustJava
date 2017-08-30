package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
       //Takes in customer name
        EditText customerName = (EditText) findViewById(R.id.customer_name);
        String name = customerName.getText().toString();
        //Log.v("MainActivity", "Name: " + name);

        //Figure out if the user wants whipped cream topping
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_id);
        boolean hasWhippedCream = whippedCream.isChecked();

        //Figure out if the user wants chocolate topping
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_id);
        boolean hasChocolate = chocolate.isChecked();
        //String priceMessage = "Free";
        //displayMessage(priceMessage);
        /**displayPrice(quantity * 5);*/
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        //String priceMessage = "Total: $" + price;
        //priceMessage = priceMessage + "\nThank you!";
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        displayMessage(priceMessage);

        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setData(Uri.parse("geo:47.6, -122.3"));
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "official@gmail.com");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }

    /**
     * Create summary of the order.
     *
     * @param name of the customer
     * @param price of the order
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate topping
     * @return text summary
     *
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream: " + hasWhippedCream;
        priceMessage +="\nAdd Chocolate?" + hasChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }


    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;

        if (hasWhippedCream){
            basePrice++;
        }

        if (hasChocolate){
            basePrice += 2;
        }
        int price = quantity * basePrice;
        return price;
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early because ther is nothing left to do
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have more than a 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message );
    }

}
