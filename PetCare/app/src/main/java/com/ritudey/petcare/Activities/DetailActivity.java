package com.ritudey.petcare.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ritudey.petcare.Models.NewProModel;
import com.ritudey.petcare.Models.PopularProModel;
import com.ritudey.petcare.R;

public class DetailActivity extends AppCompatActivity {

    ImageView proImg;
    TextView proName,proAmount,rating,description,minus,quantity,plus;
    Button addToCart,buyNow;

    //db
    FirebaseFirestore firebaseFirestore;

    //New products
    NewProModel newProModel=null;
    //Popular Products
    PopularProModel popularProModel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        proImg = findViewById(R.id.pro_img);
        proName=findViewById(R.id.pro_name_TV);
        proAmount=findViewById(R.id.pro_amount_TV);
        rating=findViewById(R.id.rating_value_TV);
        description=findViewById(R.id.pro_desc_TV);
        minus=findViewById(R.id.minus_TV);
        plus=findViewById(R.id.plus_TV);
        quantity=findViewById(R.id.quantity_TV);
        addToCart=findViewById(R.id.btn_add_cart);
        buyNow=findViewById(R.id.btn_buy_now);

        firebaseFirestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detail");


        if(obj instanceof NewProModel){
            newProModel = (NewProModel) obj;
        }else if(obj instanceof PopularProModel){
            popularProModel = (PopularProModel) obj;
        }


        //New Products
        if(newProModel!=null)
        {
            Glide.with(getApplicationContext()).load(newProModel.getImg_url()).into(proImg);
            proName.setText(newProModel.getName());
            proAmount.setText("$"+String.valueOf(newProModel.getAmount()));
            description.setText(newProModel.getDescription());
            rating.setText(newProModel.getRating());
        }

        //Popular Products
        if(popularProModel!=null)
        {
            Glide.with(getApplicationContext()).load(popularProModel.getImg_url()).into(proImg);
            proName.setText(popularProModel.getName());
            proAmount.setText("$"+String.valueOf(popularProModel.getAmount()));
            description.setText(popularProModel.getDescription());
            rating.setText(popularProModel.getRating());
        }

    }
}