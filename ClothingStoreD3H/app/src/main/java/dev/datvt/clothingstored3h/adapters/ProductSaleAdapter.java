package dev.datvt.clothingstored3h.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.utils.ToolsHelper;


/**
 * Created by datvt on 6/30/2016.
 */
public class ProductSaleAdapter extends BaseAdapter {

    private Activity context;
    private List<Product> productList;

    public ProductSaleAdapter(Activity context, List<Product> productList) {
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
            convertView = inflater.inflate(R.layout.item_product_sale, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Product product = (Product) getItem(position);
        holder.name.setText(product.getTenHang());
        if (product.getSoLuongConLai() >= 1000) {
            holder.number.setText("Số lượng: " + ToolsHelper.intToString(product.getSoLuongConLai()));
        } else {
            holder.number.setText("Số lượng: " + product.getSoLuongConLai());
        }

        if (product.getDonGiaNhap() >= 1000) {
            holder.donGia.setText("Đơn giá: " + ToolsHelper.intToString((int) Math.round(product.getDonGiaNhap())) + " $");
        } else {
            holder.donGia.setText("Đơn giá: " + Math.round(product.getDonGiaNhap()) + " $");
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

        holder.etDonGia.setText(Math.round(product.getDonGiaNhap()) + "");

        holder.cbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbx.isChecked()) {
                    holder.frame.setVisibility(View.VISIBLE);
                    product.setSale(true);
                    product.setSoLuongBan(Integer.parseInt(holder.etSoLuong.getText().toString()));
                    product.setDonGiaBan(Double.parseDouble(holder.etDonGia.getText().toString()));
                } else {
                    holder.frame.setVisibility(View.INVISIBLE);
                    product.setSale(false);
                }
            }
        });

        holder.etSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (holder.etSoLuong.getText() != null && !holder.etSoLuong.getText().toString().isEmpty()) {
                        if (Integer.parseInt(holder.etSoLuong.getText().toString()) > product.getSoLuongConLai()) {
                            Toast.makeText(context , "Không đủ sản phẩm để bán", Toast.LENGTH_SHORT).show();
                        } else {
                            product.setSoLuongBan(Integer.parseInt(holder.etSoLuong.getText().toString()));
                            Log.e("PRODUCT_SALE", "Số lượng bán: " + holder.etSoLuong.getText().toString());
                        }
                    }
                } catch (Exception e) {
                }
            }
        });

        holder.etDonGia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (holder.etDonGia.getText() != null && !holder.etDonGia.getText().toString().isEmpty()) {
                        product.setDonGiaBan(Double.parseDouble(holder.etDonGia.getText().toString()));
                        Log.e("PRODUCT_SALE", "Đơn giá bán: " + holder.etDonGia.getText().toString());
                    }
                } catch (Exception e) {
                }

            }
        });

        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) v.findViewById(R.id.nameProduct);
        holder.number = (TextView) v.findViewById(R.id.numberProduct);
        holder.donGia = (TextView) v.findViewById(R.id.donGia);
        holder.img = (ImageView) v.findViewById(R.id.imgProduct);
        holder.cbx = (CheckBox) v.findViewById(R.id.cbxSale);
        holder.etSoLuong = (EditText) v.findViewById(R.id.etSoLuongBan);
        holder.etDonGia = (EditText) v.findViewById(R.id.etDonGiaBan);
        holder.frame = (LinearLayout) v.findViewById(R.id.frameCustom);
        return holder;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView number;
        public TextView donGia;
        public ImageView img;
        public CheckBox cbx;
        public EditText etDonGia;
        public EditText etSoLuong;
        public LinearLayout frame;
    }
}
