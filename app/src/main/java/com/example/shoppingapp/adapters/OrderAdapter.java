package com.example.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.Order;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<Order> orders;
    private SimpleDateFormat dateFormat;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderIdText.setText(String.format("Sipariş #%d", order.getId()));
        holder.dateText.setText(dateFormat.format(order.getOrderDate()));
        holder.totalPriceText.setText(String.format(Locale.getDefault(), "%.2f TL", order.getTotalAmount()));
        holder.statusText.setText(order.getStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdText, dateText, totalPriceText, statusText;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdText = itemView.findViewById(R.id.orderIdText);
            dateText = itemView.findViewById(R.id.dateText);
            totalPriceText = itemView.findViewById(R.id.totalPriceText);
            statusText = itemView.findViewById(R.id.statusText);
        }
    }
} 