package com.example.project;

import static com.example.project.ShoppingCart.cart;
import static com.example.project.products.id_pro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private Context context; //context
    public static ArrayList<product> items;
    DataBase db ;
    LayoutInflater inflt;

    public CustomListAdapter(Context context, ArrayList<product>  items) {
        db= new DataBase(context);
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         View vi = convertView;
        if (vi == null)

        {
        inflt=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vi=inflt.inflate(R.layout.view_cart,null);
            }


        ImageView min = (ImageView) vi.findViewById(R.id.min);
        min.setImageResource(R.drawable.ic_minus);

        ImageView max = (ImageView) vi.findViewById(R.id.max);
        max.setImageResource(R.drawable.ic_plus);


        ImageView del = (ImageView) vi.findViewById(R.id.del);
        del.setImageResource(R.drawable.ic_trash);


        TextView id = (TextView) vi.findViewById(R.id.txt_id);
        id.setText(String.valueOf (items.get(position).id));

        TextView name = (TextView) vi.findViewById(R.id.name);
        name.setText(items.get(position).Name);

        TextView price = (TextView) vi.findViewById(R.id.price);
        price.setText(String.valueOf (items.get(position).Price));

        TextView Tquan = (TextView) vi.findViewById(R.id.Tquan);

        Tquan.setText(String.valueOf (items.get(position).Quantity-1));

        TextView quan = (TextView) vi.findViewById(R.id.quan);
        quan.setText(String.valueOf(1));

        TextView Tprice = (TextView) vi.findViewById(R.id.tprice);

        Tprice.setText (String.valueOf(Integer.parseInt(quan.getText().toString()) *
                Integer.parseInt(price.getText().toString())));


        Button ADD = (Button) vi.findViewById(R.id.ADD);

        max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if( items.get(position).Quantity >1 &&
                       (Integer.parseInt(Tquan.getText().toString())<= Integer.parseInt(Tquan.getText().toString()))){

                   quan.setText(String.valueOf(Integer.parseInt(quan.getText().toString()) + 1));

                   Tquan.setText(String.valueOf(Integer.parseInt(Tquan.getText().toString()) - 1));

                   Tprice.setText(String.valueOf(Integer.parseInt(quan.getText().toString()) *
                           Integer.parseInt(price.getText().toString())));
                   items.get(position).Quantity -= 1;

               }

              else {
                   Toast.makeText(context, "This product is sold out", Toast.LENGTH_LONG).show();


                }



            }
        });

        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(quan.getText().toString()) > 1 ) {
                    quan.setText(String.valueOf(String.valueOf(Integer.parseInt(quan.getText().toString()) - 1)));

                    Tquan.setText(String.valueOf(Integer.parseInt(Tquan.getText().toString()) + 1));

                    Tprice.setText(String.valueOf(Integer.parseInt(quan.getText().toString()) *
                            Integer.parseInt(price.getText().toString())));
                    items.get(position).Quantity += 1;
                }
            }
        });



       del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             db.Delete_product(items.get(position).id);
                Toast.makeText(context, "This product is Deleted", Toast.LENGTH_LONG).show();
                    id_pro.remove(position);

            }
        });


        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(db.CheckISOreded()==true)
                {

                    db.Insert_Order( Map.address_Order );

                }

             if(   db.CheckISOreded_Details(items.get(position).id) == true)
                {
                    db.update_order_details(items.get(position).id, Integer.parseInt(quan.getText().toString()));
                    Toast.makeText(context, "Quantity is updated", Toast.LENGTH_LONG).show();

                }
             else
             {
                 db.Insert_Order_Details(items.get(position).id
                         ,Integer.parseInt(quan.getText().toString()));
                 Toast.makeText(context, "Insert_Product", Toast.LENGTH_LONG).show();

             }

            }
        });
        return vi;
    }
}
