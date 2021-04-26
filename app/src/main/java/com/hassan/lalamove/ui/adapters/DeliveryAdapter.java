package com.hassan.lalamove.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hassan.lalamove.R;
import com.hassan.lalamove.models.Delivery;
import com.hassan.lalamove.ui.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DeliveryAdapter  extends RecyclerView.Adapter<DeliveryHolder> {
    Activity context;
    ArrayList<Delivery> deliveries;

    public DeliveryAdapter(Activity context, ArrayList<Delivery> deliveries) {
        this.context = context;
        this.deliveries = deliveries;
    }

    @NonNull
    @Override
    public DeliveryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeliveryHolder(LayoutInflater.from(context).inflate(R.layout.delivery_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryHolder holder, final int position) {
        final Delivery delivery=deliveries.get(position);
        holder.from_tv.setText(delivery.getRoute().getStart());
        holder.to_tv.setText(delivery.getRoute().getEnd());
        final double deliveryFee=Double.parseDouble(delivery.getDeliveryFee().replace("$",""));
        double surcharge= Double.parseDouble(delivery.getSurcharge().replace("$",""));
        holder.price_tv.setText(String.format("$ %.2f", (deliveryFee+surcharge))+"");
        Picasso.with(context).load(delivery.getGoodsPicture()).into(holder.image);
        if (delivery.getIs_favorite()==0){
            holder.fav_img.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivityForResult(new Intent(context, DetailsActivity.class).putExtra("dev",delivery).putExtra("pos",position),1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveries.size();
    }
}
class DeliveryHolder extends RecyclerView.ViewHolder{

    TextView from_tv, to_tv,price_tv;
    ImageView fav_img;
    CircleImageView image;
    public DeliveryHolder(@NonNull View itemView) {
        super(itemView);
        from_tv =itemView.findViewById(R.id.deliver_from);
        to_tv =itemView.findViewById(R.id.deliver_to);
        price_tv =itemView.findViewById(R.id.delivery_price);
        fav_img =itemView.findViewById(R.id.delivery_fav);
        image =itemView.findViewById(R.id.d_image);
    }
}