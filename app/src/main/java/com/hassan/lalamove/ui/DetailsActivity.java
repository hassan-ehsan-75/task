package com.hassan.lalamove.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hassan.lalamove.R;
import com.hassan.lalamove.database.AppDatabase;
import com.hassan.lalamove.database.AppExecutors;
import com.hassan.lalamove.events.EventMessage;
import com.hassan.lalamove.models.Delivery;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

public class DetailsActivity extends AppCompatActivity {

    private ImageView back_btn,goods_img;
    private TextView details_from_tv,details_to_tv,details_fee_tv;
    private Button addToFav_btn;
    private AppDatabase mDb;
    private int pos;
    private Delivery delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        back_btn = findViewById(R.id.app_back);
        details_fee_tv=findViewById(R.id.details_delivery_fee);
        details_to_tv=findViewById(R.id.details_to);
        details_from_tv=findViewById(R.id.details_from);
        goods_img=findViewById(R.id.details_img);
        addToFav_btn=findViewById(R.id.details_add_fav);
        mDb = AppDatabase.getInstance(getApplicationContext());
        delivery = (Delivery) getIntent().getSerializableExtra("dev");
        pos = getIntent().getIntExtra("pos",0);
        if (delivery !=null){
            details_from_tv.setText("From : "+ delivery.getRoute().getStart());
            details_to_tv.setText("To : "+ delivery.getRoute().getEnd());
            Picasso.with(this).load(delivery.getGoodsPicture()).into(goods_img);
            final double deliveryFee=Double.parseDouble(delivery.getDeliveryFee().replace("$",""));
            double surcharge= Double.parseDouble(delivery.getSurcharge().replace("$",""));
            details_fee_tv.setText(String.format("Delivery Fee: $ %.2f", (deliveryFee+surcharge))+"");
            if (delivery.getIs_favorite()==1){
                addToFav_btn.setText("Remove From favorites");
                Drawable img = getResources().getDrawable(R.drawable.ic_fav_on);
                img.setBounds(0, 0, 60, 60);
                addToFav_btn.setCompoundDrawables(null,null,img,null);

            }
        }
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("pos", pos);
                intent.putExtra("status", delivery.getIs_favorite());
                getIntent().putExtra("pos", pos);
                getIntent().putExtra("status", delivery.getIs_favorite());
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
        addToFav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delivery.getIs_favorite()==0) {
                    addToFav_btn.setText("Remove from favorites");
                    Drawable img = getResources().getDrawable(R.drawable.ic_fav_on);
                    img.setBounds(0, 0, 60, 60);
                    addToFav_btn.setCompoundDrawables(null,null,img,null);
                    delivery.setIs_favorite(0);
                }else {
                    addToFav_btn.setText("Add to favorites");
                    Drawable img = getResources().getDrawable(R.drawable.ic_fav_off);
                    img.setBounds(0, 0, 60, 60);
                    addToFav_btn.setCompoundDrawables(null,null,img,null);
                    delivery.setIs_favorite(1);
                }
                AppExecutors.getInstance().diskIO().execute(new Runnable() {

                    @Override
                    public void run() {
                        if (delivery.getIs_favorite()==0) {
                            delivery.setIs_favorite(1);
                            mDb.deliveryDao().updatePerson(delivery);


                        }else{
                            delivery.setIs_favorite(0);
                            mDb.deliveryDao().updatePerson(delivery);
                        }

                    }
                });

            }
        });



    }


    @Override
    public void onBackPressed() {
        return;
    }
}
