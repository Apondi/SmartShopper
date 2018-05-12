package com.example.kevpreneur.smartshopper;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by kevpreneur on 4/8/2018.
 */

public class ProductDetails extends DialogFragment {

    private static final String TAG = ProductDetails.class.getSimpleName();

    // the url to search the barcode
    private static final String URL = "https://my-json-server.typicode.com/Apondi/JSONPlaceholder/products/";

    private TextView itemName, itemPrice, totalPrice;
    private Button cencel, btnAddToCart;
    private NumberPicker itemQty;
    Button cancel;

    public ProductDetails() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_productinfo_dialog, container, false);

        //pass the value of the scanned barcode to the attribute as a string
        String barcodeValue = getArguments().getString("barcode");
//        checkBarcode(barcodeValue);

        itemName = rootView.findViewById(R.id.itemName);
        itemPrice = rootView.findViewById(R.id.itemPrice);
        totalPrice = rootView.findViewById(R.id.totalPrice);
        cancel = (Button) rootView.findViewById(R.id.cancel);
        btnAddToCart = rootView.findViewById(R.id.btnAddToCart);
        itemQty = rootView.findViewById(R.id.itemQty);

        numberPickerInitializer();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        searchBarcode(barcodeValue);

        getDialog().setTitle("Item Details");
        return rootView;
    }

    private void searchBarcode(String barcodeValue) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, URL + barcodeValue, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //deal with the response
                        Log.e(TAG, "Barcode response" + response.toString());
                        // check for success status
                        if (!response.has("error")) {
                            //received product response
                            renderProduct(response);
                        } else {
                            showNoProductDetails();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        showNoProductDetails();
                    }
                }
        );

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

    private void showNoProductDetails() {
        Log.d(TAG,"No product data");
    }

    private void renderProduct(JSONObject response) {
        try{
            Product product = new Gson().fromJson(response.toString(), Product.class);
            if (product != null){
                itemName.setText(product.getName());
                itemPrice.setText(product.getPrice());
                totalPrice.setText(product.getPrice());
            }
            else {
                showNoProductDetails();
            }

        } catch (JsonSyntaxException e){
            showNoProductDetails();
            Toast.makeText(getActivity(), "Error occurred. Check your LogCat full report",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // exception
            showNoProductDetails();
            Toast.makeText(getActivity(), "Error occurred. Check your LogCat full report",
                    Toast.LENGTH_SHORT);
        }
    }

    private void numberPickerInitializer() {
        itemQty.setMinValue(1);
        itemQty.setMaxValue(50);
        itemQty.setWrapSelectorWheel(true);

        itemQty.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int quantity = itemQty.getValue();
                double price = Double.parseDouble(itemPrice.getText().toString());


                DecimalFormat numberFormat = new DecimalFormat("#.00");
                String cost = numberFormat.format(quantity*price);

                totalPrice.setText(cost);
            }
        });
    }

//    private void checkBarcode(String barcodeValue) {
//        if (TextUtils.isEmpty(barcodeValue)) {
//            Toast.makeText(getActivity(), "Barcode is empty!", Toast.LENGTH_LONG).show();
//            dismiss();
//        }
//    }

    //the product inner class
    public class Product {
        String productid;
        String name;
        String price;

        public Product() {
        }

        public Product(String productid, String name, String price) {
            this.productid = productid;
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "productid='" + productid + '\'' +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
