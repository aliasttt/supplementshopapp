package com.example.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.Product;
import java.io.File;

public class AdminProductAdapter extends ListAdapter<Product, AdminProductAdapter.ProductViewHolder> {
    private final Context context;
    private final ProductActionListener listener;

    public interface ProductActionListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public AdminProductAdapter(Context context, ProductActionListener listener) {
        super(new DiffUtil.ItemCallback<Product>() {
            @Override
            public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return oldItem.getId() == newItem.getId() &&
                       oldItem.getName().equals(newItem.getName()) &&
                       oldItem.getDescription().equals(newItem.getDescription()) &&
                       oldItem.getPrice() == newItem.getPrice() &&
                       oldItem.getStock() == newItem.getStock() &&
                       oldItem.getCategory().equals(newItem.getCategory()) &&
                       oldItem.getImageResourceName().equals(newItem.getImageResourceName());
            }
        });
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);
        holder.bind(product);
    }

    public Product getProductAt(int position) {
        return getItem(position);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;
        private final TextView productStock;
        private final TextView productCategory;
        private final Button editButton;
        private final Button deleteButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImageView);
            productName = itemView.findViewById(R.id.nameTextView);
            productPrice = itemView.findViewById(R.id.priceTextView);
            productStock = itemView.findViewById(R.id.stockTextView);
            productCategory = itemView.findViewById(R.id.categoryTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(position);
                }
            });

            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position);
                }
            });
        }

        public void bind(Product product) {
            productName.setText(product.getName());
            productPrice.setText(String.format("Fiyat: %.2f TL", product.getPrice()));
            productStock.setText(String.format("Stok: %d", product.getStock()));
            productCategory.setText(String.format("Kategori: %s", product.getCategory()));

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
        }
    }
} 