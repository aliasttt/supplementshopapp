// Generated by view binder compiler. Do not edit!
package com.example.shoppingapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.shoppingapp.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemCartBinding implements ViewBinding {
  @NonNull
  private final MaterialCardView rootView;

  @NonNull
  public final Button decreaseButton;

  @NonNull
  public final Button increaseButton;

  @NonNull
  public final ImageView productImage;

  @NonNull
  public final TextView productName;

  @NonNull
  public final TextView productPrice;

  @NonNull
  public final TextView quantity;

  @NonNull
  public final LinearLayout quantityLayout;

  @NonNull
  public final ImageButton removeButton;

  private ItemCartBinding(@NonNull MaterialCardView rootView, @NonNull Button decreaseButton,
      @NonNull Button increaseButton, @NonNull ImageView productImage,
      @NonNull TextView productName, @NonNull TextView productPrice, @NonNull TextView quantity,
      @NonNull LinearLayout quantityLayout, @NonNull ImageButton removeButton) {
    this.rootView = rootView;
    this.decreaseButton = decreaseButton;
    this.increaseButton = increaseButton;
    this.productImage = productImage;
    this.productName = productName;
    this.productPrice = productPrice;
    this.quantity = quantity;
    this.quantityLayout = quantityLayout;
    this.removeButton = removeButton;
  }

  @Override
  @NonNull
  public MaterialCardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemCartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemCartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_cart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemCartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.decreaseButton;
      Button decreaseButton = ViewBindings.findChildViewById(rootView, id);
      if (decreaseButton == null) {
        break missingId;
      }

      id = R.id.increaseButton;
      Button increaseButton = ViewBindings.findChildViewById(rootView, id);
      if (increaseButton == null) {
        break missingId;
      }

      id = R.id.productImage;
      ImageView productImage = ViewBindings.findChildViewById(rootView, id);
      if (productImage == null) {
        break missingId;
      }

      id = R.id.productName;
      TextView productName = ViewBindings.findChildViewById(rootView, id);
      if (productName == null) {
        break missingId;
      }

      id = R.id.productPrice;
      TextView productPrice = ViewBindings.findChildViewById(rootView, id);
      if (productPrice == null) {
        break missingId;
      }

      id = R.id.quantity;
      TextView quantity = ViewBindings.findChildViewById(rootView, id);
      if (quantity == null) {
        break missingId;
      }

      id = R.id.quantityLayout;
      LinearLayout quantityLayout = ViewBindings.findChildViewById(rootView, id);
      if (quantityLayout == null) {
        break missingId;
      }

      id = R.id.removeButton;
      ImageButton removeButton = ViewBindings.findChildViewById(rootView, id);
      if (removeButton == null) {
        break missingId;
      }

      return new ItemCartBinding((MaterialCardView) rootView, decreaseButton, increaseButton,
          productImage, productName, productPrice, quantity, quantityLayout, removeButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
