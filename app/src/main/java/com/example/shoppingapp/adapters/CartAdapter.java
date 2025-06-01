package com.example.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.CartItem;
import com.example.shoppingapp.viewmodels.CartViewModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private final CartViewModel cartViewModel;
    private final Context context;
    private final OnCartItemClickListener listener;

    public interface OnCartItemClickListener {
        void onQuantityChanged(CartItem item, int newQuantity);
        void onRemoveItem(CartItem item);
    }

    public CartAdapter(Context context, CartViewModel cartViewModel, OnCartItemClickListener listener) {
        this.context = context;
        this.cartViewModel = cartViewModel;
        this.cartItems = new ArrayList<>();
        this.listener = listener;
    }

    public void setCartItems(List<CartItem> items) {
        this.cartItems = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return cartItems != null ? cartItems.size() : 0;
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;
        private final TextView quantity;
        private final Button decreaseButton;
        private final Button increaseButton;
        private final Button removeButton;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            quantity = itemView.findViewById(R.id.quantity);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            removeButton = itemView.findViewById(R.id.removeButton);
        }

        void bind(CartItem item) {
            productName.setText(item.getProductName());
            productPrice.setText(String.format("%.2f TL", item.getProductPrice()));
            quantity.setText(String.valueOf(item.getQuantity()));

            // Load image from internal storage
            if (item.getProductImage() != null && !item.getProductImage().isEmpty()) {
                File imageFile = new File(context.getFilesDir(), item.getProductImage());
                if (imageFile.exists()) {
                    Glide.with(context)
                        .load(imageFile)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                        .into(productImage);
                } else {
                    productImage.setImageResource(R.drawable.placeholder_image);
                }
            } else {
                productImage.setImageResource(R.drawable.placeholder_image);
            }

            decreaseButton.setOnClickListener(v -> {
                int newQuantity = item.getQuantity() - 1;
                if (newQuantity > 0) {
                    listener.onQuantityChanged(item, newQuantity);
                }
            });

            increaseButton.setOnClickListener(v -> {
                listener.onQuantityChanged(item, item.getQuantity() + 1);
            });

            removeButton.setOnClickListener(v -> {
                listener.onRemoveItem(item);
            });
        }
    }
} 