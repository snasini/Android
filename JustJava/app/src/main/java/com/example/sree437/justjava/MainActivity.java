package com.example.sree437.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    String customerName = "";
    boolean whippedChecked = false;
    boolean chocolateChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view){
        EditText nameEditText = (EditText) findViewById(R.id.enter_text_view);
        customerName = nameEditText.getText().toString();
        CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_checkbox);
        whippedChecked = whippedCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        chocolateChecked = chocolateCheckBox.isChecked();

        int price = calculatePrice();

        String subject = "Just java order for " + customerName;
        String body = createOrderSummary(price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    private int calculatePrice(){
        int tempPrice = 5;

        if(whippedChecked){
            tempPrice += 1;
        }
        if(chocolateChecked){
            tempPrice += 2;
        }

        return quantity * tempPrice;
    }

    private String createOrderSummary(int price){
        String message = "Name: " + customerName;
        message += "\nAdd Whipped Cream? " + whippedChecked;
        message += "\nAdd Chocolate? " + chocolateChecked;
        message += "\nQuantity: " + quantity;
        message  += "\nTotal: $" + price + "\nThank you!";
        return message;
    }

    public void incrementQuantity(View view){
        if(quantity == 100){
            Toast.makeText(this,"You can't order more than 100 cups of Coffee",Toast.LENGTH_SHORT).show();
        }else{
            quantity++;
        }
        display(quantity);
    }

    public void decrementQuantity(View view){
        if(quantity == 0){
            Toast.makeText(this,"You can't order less than 0 cups of Coffee",Toast.LENGTH_SHORT).show();
        }else {
            quantity--;
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen
     */
    private void display(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    private void displayMessage(String message){
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

}
