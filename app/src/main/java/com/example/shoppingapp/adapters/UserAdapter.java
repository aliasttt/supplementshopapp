package com.example.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.User;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserViewHolder> {
    private final Context context;
    private final OnUserClickListener listener;
    private final SimpleDateFormat dateFormat;

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public UserAdapter(Context context, OnUserClickListener listener) {
        super(new UserDiffCallback());
        this.context = context;
        this.listener = listener;
        this.dateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("tr"));
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = getItem(position);
        holder.bind(user);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView emailText;
        private final TextView phoneText;
        private final TextView cityText;
        private final TextView registrationDateText;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            emailText = itemView.findViewById(R.id.emailText);
            phoneText = itemView.findViewById(R.id.phoneText);
            cityText = itemView.findViewById(R.id.cityText);
            registrationDateText = itemView.findViewById(R.id.registrationDateText);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onUserClick(getItem(position));
                }
            });
        }

        void bind(User user) {
            nameText.setText(String.format("Ad Soyad: %s", user.getName()));
            emailText.setText(String.format("E-posta: %s", user.getEmail()));
            phoneText.setText(String.format("Telefon: %s", user.getPhone() != null ? user.getPhone() : "Belirtilmemiş"));
            cityText.setText(String.format("Şehir: %s", user.getCity() != null ? user.getCity() : "Belirtilmemiş"));
            registrationDateText.setText(String.format("Üyelik Tarihi: %s", 
                user.getRegistrationDate() != null ? dateFormat.format(user.getRegistrationDate()) : "Belirtilmemiş"));
        }
    }

    private static class UserDiffCallback extends DiffUtil.ItemCallback<User> {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getEmail().equals(newItem.getEmail());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                   oldItem.getEmail().equals(newItem.getEmail()) &&
                   oldItem.getPhone().equals(newItem.getPhone()) &&
                   oldItem.getCity().equals(newItem.getCity());
        }
    }
} 