package com.example.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.Product;
import java.io.File;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final Context context;
    private List<Product> products;
    private final OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> products, OnProductClickListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    public void updateProducts(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView nameText;
        private final TextView priceText;
        private final TextView categoryText;
        private final Button viewProductButton;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            nameText = itemView.findViewById(R.id.nameText);
            priceText = itemView.findViewById(R.id.priceText);
            categoryText = itemView.findViewById(R.id.categoryText);
            viewProductButton = itemView.findViewById(R.id.viewProductButton);
        }

        void bind(Product product) {
            nameText.setText(product.getName());
            priceText.setText(String.format("%.2f TL", product.getPrice()));
            categoryText.setText(product.getCategory());

            // Load image using Glide
            if (product.getImageResourceName() != null && !product.getImageResourceName().isEmpty()) {
                File imageFile = new File(context.getFilesDir(), product.getImageResourceName());
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

            viewProductButton.setOnClickListener(v -> listener.onProductClick(product));
        }
    }
} 