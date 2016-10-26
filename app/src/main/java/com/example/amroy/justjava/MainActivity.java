package com.example.amroy.justjava;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

import static com.example.amroy.justjava.R.color.colorAccent;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void minus(View view) {
        quantity -= 1;
        displayQuantity(quantity);
    }

    public void plus(View view) {
        quantity = 1 + quantity;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {
        //pilihan pada checkbox
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_CheckBox);
        boolean haswhippedCreamCheckBox = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_CheckBox);
        boolean hasChocolateCheckBox = chocolateCheckBox.isChecked();

        //menampilakn log variabel
        Log.v("MainActivity", "Has whipped cream " + haswhippedCreamCheckBox);

        //memanggil fungsi price
        int price = calculatePrice(quantity, 5, haswhippedCreamCheckBox, hasChocolateCheckBox);
        EditText editTextName = (EditText) findViewById(R.id.editText_name);
        String name = editTextName.getText().toString();
//        Log.v("MainActivity", "Quantity = " + quantity);
//        if(quantity >=1 && quantity <=100) {
//            displayMessage(createOrderSummary(price, quantity, name, haswhippedCreamCheckBox, hasChocolateCheckBox));
//            toastMessage("Transaction Succes " + name + " !");
//        }else {
//            toastMessage("There is no quantity or quantity out of range");
//        }

        //mengirimkan summary pada aplikasi email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + name);
        String Message = createOrderSummary(price, quantity, name, haswhippedCreamCheckBox, hasChocolateCheckBox);
        intent.putExtra(Intent.EXTRA_TEXT, Message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(int quantity, int pricePerCup, boolean cream, boolean chocolate) {
        int price;
            if (cream == true && chocolate == true) {
                pricePerCup = pricePerCup + 2 + 3;
            } else if (cream == true && chocolate == false) {
                pricePerCup = pricePerCup + 2;
            } else if (cream == false && chocolate == true) {
                pricePerCup = pricePerCup + 3;
            }
            price = quantity * pricePerCup;
            return price;
    }

    public void toastMessage (String textToToast){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, textToToast, Toast.LENGTH_SHORT);
        toast.show();
    }

    public String createOrderSummary(int price, int quantity, String name, boolean haswhippedCreamCheckBox, boolean ChocolateCheckBox) {
        String summary = "Name \t\t: " + name;
        summary = summary + "\nAdd Whipeed Cream \t: " + haswhippedCreamCheckBox;
        summary = summary + "\nAdd Chocolate \t\t\t\t\t: " + ChocolateCheckBox;
        summary = summary + "\nQuantity\t: " + quantity;
        summary = summary + "\nTotal\t\t\t: " + "$ " + price;
        summary = summary + "\nThank you !\t";
        return summary;
    }

    public void displayQuantity(int quantity) {
        TextView quantityView = (TextView) findViewById(R.id.quantity_view);
        quantityView.setText("" + quantity);
        quantityView.setTextColor(Color.RED);
        quantityView.setTextSize(30);
    }

    public void displayMessage(String message) {
        TextView jumlahTextView = (TextView) findViewById(R.id.order_summary_text_view);
        jumlahTextView.setText("" + message);
    }

}
