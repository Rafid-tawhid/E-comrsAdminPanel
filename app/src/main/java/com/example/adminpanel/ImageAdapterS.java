package com.example.adminpanel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapterS extends RecyclerView.Adapter<ImageAdapterS.ImageViewHolder> implements Serializable, Filterable {
    private Context mcontext;
    private List<Upload> mupload;
    private List<Upload> muploadFull;
    public int controler=0;



    private OnItemClickLisnter mlistner;
   FirebaseStorage mStorage=FirebaseStorage.getInstance();
   DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("uploads");

    public ImageAdapterS(Context context, List<Upload> uploadS) {
        this.mcontext = context;
        this.mupload = uploadS;
        muploadFull=new ArrayList<>(uploadS);

    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.singelproduct,parent,false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadcurrent=mupload.get(position);

        holder.type.setText(uploadcurrent.getType());
        holder.price.setText(uploadcurrent.getPrice());
        Picasso.get().load(uploadcurrent.getImgUrl()).fit().centerCrop().into(holder.imageView);





    }



    @Override
    public int getItemCount() {
        return mupload.size();
    }



    public class ImageViewHolder extends RecyclerView.ViewHolder implements MenuItem.OnMenuItemClickListener
    {

        public TextView price,type;
        public ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            price=itemView.findViewById(R.id.price2);
            type=itemView.findViewById(R.id.type2);
            imageView=itemView.findViewById(R.id.image1);


//            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//                @Override
//                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                    /////
//
//                    menu.setHeaderTitle("Select Action");
//                    MenuItem doWhateEver=menu.add(Menu.NONE,1,1,"Do Whatever");
//                    MenuItem delete=menu.add(Menu.NONE,2,2,"Delete");
//
//                    doWhateEver.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            return false;
//                        }
//                    });
//                    delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//
//                            deleteItem(getAdapterPosition());
//                            return false;
//                        }
//                    });
//
//
//
//
//                }
//            });

//
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position= getAdapterPosition();

                        //show product details on dialog
                    final Dialog dialog = new Dialog(v.getContext());
                    // Include dialog.xml file
                    dialog.setContentView(R.layout.singelproduct2);
                    // Set dialog title
                    dialog.setTitle(mupload.get(position).getName());

                    TextView text = (TextView) dialog.findViewById(R.id.price3);
                    text.setText(mupload.get(position).getPrice());
                    TextView text2 = (TextView) dialog.findViewById(R.id.pdetails);
                    text2.setText(mupload.get(position).getDetails());
                    TextView text3 = (TextView) dialog.findViewById(R.id.type3);
                    text3.setText(mupload.get(position).getType());
                    ImageView image = (ImageView) dialog.findViewById(R.id.showImg);
                    Picasso.get().load(mupload.get(position).getImgUrl()).fit().centerCrop().into(image);




                    dialog.show();

                    TextView shopButton=dialog.findViewById(R.id.addCart);
                   shopButton.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {



//
                                //send object and position to details page

//                            Intent intent=new Intent(v.getContext(),productDetailsActivity.class);
//
//                            dialog.dismiss();
//                            intent.putExtra("img",mupload.get(position).getImgUrl());
//                            intent.putExtra("pp",mupload.get(position).getPrice());
//                            intent.putExtra("nm",mupload.get(position).getName());
//                            intent.putExtra("dt",mupload.get(position).getDetails());
//                            intent.putExtra("tp",mupload.get(position).getType());
//
//                           v.getContext().startActivity(intent);



                       }
                   });





                }
            });


            ///for admin delete update

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mlistner!=null)
//                    {
//                        int position=getAdapterPosition();
//                        if (position!=RecyclerView.NO_POSITION)
//                        {
//                            mlistner.onItemclick(position);
//                            mlistner.onWatheverclick(position);
//                            mlistner.onDeleteclick(position);
//                        }
//                    }
//                }
//            });

        }


        ///Item click delete,
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }
    }

    private void deleteItem(int adapterPosition) {

    int a=adapterPosition;
    Upload selectedItem=mupload.get(a);
    String selectedKey=selectedItem.getKey();
        StorageReference imgRef=mStorage.getReferenceFromUrl(selectedItem.getImgUrl());


        imgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(selectedKey).removeValue();
                Toast.makeText(mcontext, "Item Deleted", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public interface OnItemClickLisnter
    {
        void onItemclick(int position);

        void onWatheverclick(int position);
        void onDeleteclick(int position);




    }
    public void setOnItemClickListener(OnItemClickLisnter listener)
    {

        mlistner=listener;
    }


    //search fulter
    @Override
    public Filter getFilter() {

        return exampleFilter;
    }
    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Upload> filterList=new ArrayList<>();
            if (constraint==null || constraint.length()==0)
            {
                filterList.add((Upload) muploadFull);
            }
            else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (Upload item:muploadFull)
                {
                    if (item.getName().contains(filterPattern)||item.getType().contains(filterPattern)||item.getDetails().contains(filterPattern))
                    {
                        filterList.add(item);
                    }
                }
            }

            FilterResults results=new FilterResults();
            results.values=filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mupload.clear();
            mupload.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };




}
