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
import dev.datvt.clothingstored3h.models.BillOnline;
import dev.datvt.clothingstored3h.models.Customer;
import dev.datvt.clothingstored3h.models.StoreBill;
import dev.datvt.clothingstored3h.utils.DatabaseHandler;
import dev.datvt.clothingstored3h.utils.NumberTextWatcherForThousand;


/**
 * Created by datvt on 6/30/2016.
 */
public class TicketOnlineAdapter extends BaseAdapter {

    private Activity context;
    private List<BillOnline> billOnlines;

    public TicketOnlineAdapter(Activity context, List<BillOnline> billOnlines) {
        this.context = context;
        this.billOnlines = billOnlines;
    }

    @Override
    public int getCount() {
        if (billOnlines != null) {
            return billOnlines.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (billOnlines != null) {
            return billOnlines.get(position);
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
            convertView = inflater.inflate(R.layout.item_ticket_online, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BillOnline billOnline = (BillOnline) getItem(position);
        holder.index.setText((position + 1) + "");
        holder.tenKH.setText("Khách hàng: " + billOnline.TenKH);
        holder.loaiKH.setText("Loại: " + billOnline.LoaiKH);
        holder.diaChi.setText("Địa chỉ: " + billOnline.DiaChi);
        holder.ngayMua.setText("Ngày mua: " + billOnline.NgayMuaHang);
        holder.soLuong.setText("Số lượng mua: " + NumberTextWatcherForThousand.getDecimalFormattedString(billOnline.SanPham.size() + "") + " SP");

        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.loaiKH = (TextView) v.findViewById(R.id.loaiKH);
        holder.tenKH = (TextView) v.findViewById(R.id.tenKH);
        holder.diaChi = (TextView) v.findViewById(R.id.diaChi);
        holder.soLuong = (TextView) v.findViewById(R.id.soLuong);
        holder.ngayMua = (TextView) v.findViewById(R.id.ngayMua);
        holder.index = (TextView) v.findViewById(R.id.index);
        holder.isCheck = (ImageView) v.findViewById(R.id.isCheck);
        return holder;
    }

    private static class ViewHolder {
        public TextView loaiKH;
        public TextView tenKH;
        public TextView diaChi;
        public TextView soLuong;
        public TextView ngayMua;
        public TextView index;
        public ImageView isCheck;
    }
}
