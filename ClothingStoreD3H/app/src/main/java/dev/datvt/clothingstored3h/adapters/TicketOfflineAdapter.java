package dev.datvt.clothingstored3h.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dev.datvt.clothingstored3h.R;
import dev.datvt.clothingstored3h.models.Bill;
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.models.Product;
import dev.datvt.clothingstored3h.models.StoreBill;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.ToolsHelper;


/**
 * Created by datvt on 6/30/2016.
 */
public class TicketOfflineAdapter extends BaseAdapter {

    private Activity context;
    private List<StoreBill> storeBillList;
    private DatabaseHandler databaseHandler;

    public TicketOfflineAdapter(Activity context, List<StoreBill> storeBillList) {
        this.context = context;
        this.storeBillList = storeBillList;
        databaseHandler = new DatabaseHandler(context);
    }

    @Override
    public int getCount() {
        if (storeBillList != null) {
            return storeBillList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (storeBillList != null) {
            return storeBillList.get(position);
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
            convertView = inflater.inflate(R.layout.item_ticket_offline, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StoreBill storeBill = (StoreBill) getItem(position);

        holder.maPhieu.setText("Mã phiếu: " + storeBill.getMaPhieu());
        holder.tenKH.setText("Khách hàng: " + "UnKnown");
        holder.index.setText((position + 1) + "");

        Bill bill = databaseHandler.getBillWithID(storeBill.getMaHD() + "");
        if (bill != null) {
            Customer customer = databaseHandler.getCustomerWithID(bill.getMaKH() + "");
            if (customer != null) {
                holder.tenKH.setText("Khách hàng: " + customer.getName());
            }
        }

        holder.type.setText(storeBill.getLoaGiaoDich());
        if (storeBill.getLoaGiaoDich().equals("Offline")) {
            holder.type.setTextColor(context.getResources().getColor(R.color.colorRed));
        }
        holder.thanhToan.setText("Trạng thái: " + storeBill.getTrangThai());

        if (storeBill.getTrangThai().equals("Chưa thanh toán")) {
            holder.isCheck.setImageResource(R.drawable.oval_red);
        } else if (storeBill.getTrangThai().equals("Đã thanh toán")) {
            holder.isCheck.setImageResource(R.drawable.oval_green);
        } else if (storeBill.getTrangThai().equals("Còn nợ")) {
            holder.isCheck.setImageResource(R.drawable.oval_yellow);
        }

        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.maPhieu = (TextView) v.findViewById(R.id.idPhieu);
        holder.tenKH = (TextView) v.findViewById(R.id.tenKH);
        holder.index = (TextView) v.findViewById(R.id.index);
        holder.type = (TextView) v.findViewById(R.id.type);
        holder.thanhToan = (TextView) v.findViewById(R.id.thanhToan);
        holder.isCheck = (ImageView) v.findViewById(R.id.isCheck);
        return holder;
    }

    private static class ViewHolder {
        public TextView maPhieu;
        public TextView tenKH;
        public TextView type;
        public TextView index;
        public TextView thanhToan;
        public ImageView isCheck;
    }
}
