package dev.datvt.clothingstored3h.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.models.Summary;
import dev.datvt.clothingstored3h.utils.ToolsHelper;


/**
 * Created by datvt on 6/30/2016.
 */
public class SummaryAdapter extends BaseAdapter {

    private Activity context;
    private List<Summary> summaries;

    public SummaryAdapter(Activity context, List<Summary> summaries) {
        this.context = context;
        this.summaries = summaries;
    }

    @Override
    public int getCount() {
        if (summaries != null) {
            return summaries.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (summaries != null) {
            return summaries.get(position);
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
            convertView = inflater.inflate(R.layout.item_summary, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Summary summary = (Summary) getItem(position);
        holder.name.setText(summary.getTenHang());
        if (summary.getDonGiaNhap() >= 1000) {
            holder.donGiaNhap.setText(ToolsHelper.intToString((int) Math.round(summary.getDonGiaNhap())) + " $");
        } else {
            holder.donGiaNhap.setText(summary.getDonGiaNhap() + " $");
        }

        if (summary.getDonGiaBan() >= 1000) {
            holder.donGiaBan.setText(ToolsHelper.intToString((int) Math.round(summary.getDonGiaBan())) + " $");
        } else {
            holder.donGiaBan.setText(summary.getDonGiaBan() + " $");
        }

        if (summary.getSoLuongBan() >= 1000) {
            holder.soLuongBan.setText(ToolsHelper.intToString(summary.getSoLuongBan()) + " SP");
        } else {
            holder.soLuongBan.setText(summary.getSoLuongBan() + " SP");
        }


        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) v.findViewById(R.id.tenHang);
        holder.donGiaNhap = (TextView) v.findViewById(R.id.donGiaNhap);
        holder.donGiaBan = (TextView) v.findViewById(R.id.donGiaBan);
        holder.soLuongBan = (TextView) v.findViewById(R.id.soLuongBan);
        return holder;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView donGiaNhap;
        public TextView donGiaBan;
        public TextView soLuongBan;
    }
}
