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
public class MyProductAdapter extends BaseAdapter {

    private Activity context;
    private List<Product> productList;

    public MyProductAdapter(Activity context, List<Product> productList) {
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
            convertView = inflater.inflate(R.layout.item_my_product, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = (Product) getItem(position);
        holder.name.setText(product.getTenHang());
        if (product.getSoLuongBan() >= 1000) {
            holder.number.setText(ToolsHelper.intToString(product.getSoLuongBan()));
        } else {
            holder.number.setText(product.getSoLuongBan() + "");
        }

        if (product.getDonGiaBan() >= 1000) {
            holder.donGia.setText(ToolsHelper.intToString((int) product.getDonGiaBan()));
        } else {
            holder.donGia.setText((int) product.getDonGiaBan() + "");
        }

        double thanhTien = product.getSoLuongBan() * product.getDonGiaBan();
        if (thanhTien >= 1000) {
            holder.thanhTien.setText(ToolsHelper.intToString((int) thanhTien));
        } else {
            holder.thanhTien.setText(thanhTien + "");
        }


        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) v.findViewById(R.id.tvTenSP);
        holder.number = (TextView) v.findViewById(R.id.tvSoLuong);
        holder.donGia = (TextView) v.findViewById(R.id.tvDonGia);
        holder.thanhTien = (TextView) v.findViewById(R.id.tvThanhTien);
        return holder;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView number;
        public TextView donGia;
        public TextView thanhTien;
    }
}
