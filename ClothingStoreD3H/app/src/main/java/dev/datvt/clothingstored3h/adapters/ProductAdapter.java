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
        if (product.getSoLuongNhap() >= 1000) {
            holder.number.setText("Số lượng: " + ToolsHelper.intToString((int) product.getSoLuongNhap()));
        } else {
            holder.number.setText("Số lượng: " + product.getSoLuongNhap());
        }

        if (product.getImg() != null && !product.getImg().isEmpty()) {
            Picasso.with(context).load(product.getImg()).into(holder.img);
        }

        if (product.getTenHang().contains("Áo lót")) {
            holder.img.setImageResource(R.drawable.do_lot_nu_1);
        } else if (product.getTenHang().contains("Áo khoác")) {
            holder.img.setImageResource(R.drawable.product_13);
        } else if (product.getTenHang().contains("sơ mi")) {
            holder.img.setImageResource(R.drawable.ao_so_mi_nu_1);
        } else if (product.getTenHang().contains("Đầm")) {
            holder.img.setImageResource(R.drawable.dam_1);
        } else if (product.getTenHang().contains("Váy")) {
            holder.img.setImageResource(R.drawable.vay_1);
        } else if (product.getTenHang().contains("Quần")) {
            holder.img.setImageResource(R.drawable.quan_nu_1);
        } else if (product.getTenHang().contains("đồ bơi")) {
            holder.img.setImageResource(R.drawable.do_boi_nu_0);
        } else {
            holder.img.setImageResource(R.drawable.product_3);
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
