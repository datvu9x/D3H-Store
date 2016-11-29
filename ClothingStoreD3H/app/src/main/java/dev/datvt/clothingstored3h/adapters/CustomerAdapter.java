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
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.utils.ToolsHelper;


/**
 * Created by datvt on 6/30/2016.
 */
public class CustomerAdapter extends BaseAdapter {

    private Activity context;
    private List<Customer> customerList;

    public CustomerAdapter(Activity context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @Override
    public int getCount() {
        if (customerList != null) {
            return customerList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (customerList != null) {
            return customerList.get(position);
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
            convertView = inflater.inflate(R.layout.item_customer, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Customer customer = (Customer) getItem(position);
        holder.name.setText(customer.getName());
        holder.address.setText(customer.getAddress());

        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) v.findViewById(R.id.name);
        holder.address = (TextView) v.findViewById(R.id.address);
        holder.avater = (ImageView) v.findViewById(R.id.avatar);
        return holder;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView address;
        public ImageView avater;
    }
}
