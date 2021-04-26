package com.hassan.lalamove.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hassan.lalamove.R;
import com.hassan.lalamove.Tools.Uitls;
import com.hassan.lalamove.api.RetofitInterface;
import com.hassan.lalamove.api.RetrofitClientInstance;
import com.hassan.lalamove.database.AppDatabase;
import com.hassan.lalamove.database.AppExecutors;
import com.hassan.lalamove.events.EventMessage;
import com.hassan.lalamove.models.Delivery;
import com.hassan.lalamove.ui.adapters.DeliveryAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private RecyclerView deliveryListView;
    private ArrayList<Delivery> deliveries = new ArrayList<>();
    private DeliveryAdapter deliveryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private AppDatabase mDb;
    private NestedScrollView nestedScrollView;
    int offset=0;
    int limit=20;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress);
        deliveryListView = findViewById(R.id.delivery_list);
        nestedScrollView=findViewById(R.id.ns_viwe);
        linearLayoutManager = new LinearLayoutManager(this);
        deliveryAdapter = new DeliveryAdapter(MainActivity.this, (ArrayList<Delivery>) deliveries);
        deliveryListView.setAdapter(deliveryAdapter);
        deliveryListView.setLayoutManager(linearLayoutManager);
        mDb = AppDatabase.getInstance(getApplicationContext());

        if(Uitls.isConnected(getApplicationContext()))
            getDeliveries(offset,limit);
        else
            retrieveTasks(offset,limit);

        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (i1==((NestedScrollView)view).getChildAt(0).getMeasuredHeight()-view.getMeasuredHeight()){
                    offset+=20;
                    if(Uitls.isConnected(getApplicationContext()))
                    getDeliveries(offset,limit);
                    else
                        retrieveTasks(offset,limit);
                }
            }

        });

    }

    public void getDeliveries(final int offset, final int limit) {
        RetofitInterface retrofitClientInstance = RetrofitClientInstance.getRetrofitInstance(MainActivity.this).create(RetofitInterface.class);
        Call<ArrayList<Delivery>> call = retrofitClientInstance.getDeliveries(offset,limit);

        call.enqueue(new Callback<ArrayList<Delivery>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Delivery>> call, @NonNull final Response<ArrayList<Delivery>> response) {
                call.cancel();

                try {
                    final ArrayList<Delivery> deliveryArrayList = response.body();
                    if (deliveryArrayList==null){
                        progressBar.setVisibility(View.GONE);
                        return;
                    }
                    for (int i = 0; i < deliveryArrayList.size(); i++) {
                        final int finalI = i;
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {

                            @Override
                            public void run() {

                                if (mDb.deliveryDao().loadPersonById(deliveryArrayList.get(finalI).getId())==null) {
                                    mDb.deliveryDao().insertPerson(deliveryArrayList.get(finalI));
                                }
                            }
                        });

                    }
                    retrieveTasks(offset,limit);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Delivery>> call, @NonNull Throwable t) {
                call.cancel();
                Log.e("res", t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void retrieveTasks(final int offset, final int limit) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
               deliveries.addAll((ArrayList<Delivery>) mDb.deliveryDao().loadAllDeliveries(offset,limit));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        deliveryAdapter.notifyDataSetChanged();

                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 1) {
                if (data != null){
                    deliveries.get(data.getIntExtra("pos",0)).setIs_favorite(data.getIntExtra("status",0));
                    deliveryAdapter=new DeliveryAdapter(MainActivity.this,deliveries);
                    deliveryListView.setAdapter(deliveryAdapter);
                    deliveryListView.setLayoutManager(linearLayoutManager);

                }

            }
        }
    }
}
