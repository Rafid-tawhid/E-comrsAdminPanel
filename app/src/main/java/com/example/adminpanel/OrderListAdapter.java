package com.example.adminpanel;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder2> {

    public List<OrderListModelClass> mList;
    private Context context;

    public OrderListAdapter(Context context, List<OrderListModelClass> mList) {

        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.orderlistrv,parent,false);
        return new MyViewHolder2(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {

        OrderListModelClass modelClass=mList.get(position);

        Picasso.get().load(modelClass.getImgUrl()).fit().centerCrop().into(holder.imageView);

        holder.orderCode.setText(modelClass.getOrderAddress());
        holder.name.setText(modelClass.getOrdername());
        holder.mobile.setText(modelClass.getOrderMobile());
        holder.serial.setText(String.valueOf(holder.getAdapterPosition()));






        //show order list in dialog
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show product details on dialog
                final Dialog dialog = new Dialog(v.getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.admin_product_order_list);
                // Set dialog title
                dialog.setTitle(mList.get(position).getOrderNo());

                TextView text4 = (TextView) dialog.findViewById(R.id.productCodeId);
                text4.setText(mList.get(position).getOrderType());
                TextView text = (TextView) dialog.findViewById(R.id.byersNameId);
                text.setText(mList.get(position).getOrdername());
                TextView text2 = (TextView) dialog.findViewById(R.id.byersPhoneId);
                text2.setText(mList.get(position).getOrderMobile());
                TextView text5 = (TextView) dialog.findViewById(R.id.byersAddressId);
                text5.setText(mList.get(position).getOrderAddress());
                TextView text3 = (TextView) dialog.findViewById(R.id.byersBillId);
                text3.setText(mList.get(position).getPrice());


                ImageView image = (ImageView) dialog.findViewById(R.id.rvid);
                Picasso.get().load(mList.get(position).getImgUrl()).fit().centerCrop().into(image);








                dialog.show();

                TextView shopButton=dialog.findViewById(R.id.checkedBtn);
                shopButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, "Product Send", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
            }
        });








    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




    public static class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView orderCode,name,mobile,serial;
        ImageView imageView;


        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            orderCode=itemView.findViewById(R.id.orderCodeId);
            name=itemView.findViewById(R.id.ownerNameId);
            mobile=itemView.findViewById(R.id.ownerPhoneId);
            serial=itemView.findViewById(R.id.orderNo);
            imageView=itemView.findViewById(R.id.rvid);

        }
    }
}
