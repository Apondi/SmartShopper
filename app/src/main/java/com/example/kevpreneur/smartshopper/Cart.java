package com.example.kevpreneur.smartshopper;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.toolbox.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    String[] productArray = {"Apples (pack of 5)	- MUR 80.94",
            "Bananas (1kg)	- MUR 50.68",
            "Oranges (pack of 5)	- MUR 11.50",
            "Pineapple	- MUR 50.69",
            "Mango	- MUR 70.75",
            "Potatoes (2.5kg)	- MUR 11.98",
            "Tomatoes (1kg)	- MUR 21.99",
            "Onions (pack of 4)	- MUR 51.83",
            "Lettuce	- MUR 47.49",
            "Rice (1kg)	- MUR 40.48",
            "Pasta (1kg)	- MUR 80.00",
            "Bread (800g loaf)	- MUR 15.00",
            "Pizza (350g)	- MUR 40.60",
            "Beef Mince (500g)	- MUR 75.99",
            "Chicken Breast (pack of 2)	- MUR 78.00",
            "Salmon Fillets (pack of 2)	- MUR 500.23",
            "Pasta Sauce (500g jar)	- MUR 41.84",
            "Curry Sauce (500g jar)	- MUR 20.84",
            "Cheese (250g)	- MUR 23.74",
            "Butter (250g)	- MUR 20.92",
            "Plain Yoghurt (500g)	- MUR 60.94",
            "Milk (568ml / 1pint)	- MUR 20.58",
            "Milk (2.27L / 4pints)	- MUR 150.89",
            "Fresh Orange Juice (1L)	- MUR 20.69",
            "Cola (2L)	- MUR 25.00",
            "Beer (pack of 4 bottles)	- MUR 410.00"};
//    ArrayList<String> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

//        cartItems.add("Colgate");
//        cartItems.add("Shoe");
//        cartItems.add("Laptop");
//        cartItems.add("Mouse");

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,productArray);

        ListView listView  = (ListView) findViewById(R.id.items_List);
        listView.setAdapter(adapter);

        findViewById(R.id.btn_scan_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cart.this, ScanActivity.class));
            }
        });

        findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcbAPICall();
            }
        });
    }


    public void mcbAPICall(){
        oAuth();
    }
    public void oAuth() {
        WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadUrl("https://mcboauth3.azurewebsites.net/oauth/authorize?client_id=59b2e360-a0ad-449a-b364-3be2c46b7b3e&response_type=token&redirect_uri=http://smartshopper?&response_mode=query&scope=read,write");
    }
//
//    public void addToCart (ProductDetails.Product product){
////        repository.add(product);
//    }
//
//    public void removeFromCart (ProductDetails.Product product){
////        repository.remove(product);
//    }
//
//    private void getRepoItems(){
//        for (ProductDetails.Product product : repository ){
//            cartItems.add(product.getName());
//        }
//    }

}
