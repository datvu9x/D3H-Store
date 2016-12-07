package dev.datvt.clothingstored3h.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.utils.ToolsHelper;


/**
 * Created by datvt on 6/30/2016.
 */
public class ProductAdapter extends BaseAdapter {

    private Activity context;
    private List<Product> productList;

    public ProductAdapter(Activity context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (productList != null) {
            return productList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = (Product) getItem(position);
        holder.name.setText(product.getTenHang());
        if (product.getSoLuongConLai() >= 1000) {
            holder.number.setText("Số lượng: " + ToolsHelper.intToString(product.getSoLuongConLai()));
        } else {
            holder.number.setText("Số lượng: " + product.getSoLuongConLai());
        }

        if (position % 2 == 1) {
            holder.img.setImageResource(R.drawable.ic_myproducts);
        } else if (position % 2 == 0) {
            holder.img.setImageResource(R.drawable.ic_myproducts_1);
        } else if (position % 10 == 0) {
            holder.img.setImageResource(R.drawable.ic_myproducts_2);
        } else {
            holder.img.setImageResource(R.drawable.ic_myproducts);
        }

        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) v.findViewById(R.id.nameProduct);
        holder.number = (TextView) v.findViewById(R.id.numberProduct);
        holder.img = (ImageView) v.findViewById(R.id.imgProduct);
        return holder;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView number;
        public ImageView img;
    }
}
