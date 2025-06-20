// Generated by view binder compiler. Do not edit!
package com.example.shoppingapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public final class ItemProductBinding implements ViewBinding {
  @NonNull
  private final MaterialCardView rootView;

  @NonNull
  public final TextView categoryText;

  @NonNull
  public final TextView nameText;

  @NonNull
  public final TextView priceText;

  @NonNull
  public final ImageView productImage;

  @NonNull
  public final Button viewProductButton;

  private ItemProductBinding(@NonNull MaterialCardView rootView, @NonNull TextView categoryText,
      @NonNull TextView nameText, @NonNull TextView priceText, @NonNull ImageView productImage,
      @NonNull Button viewProductButton) {
    this.rootView = rootView;
    this.categoryText = categoryText;
    this.nameText = nameText;
    this.priceText = priceText;
    this.productImage = productImage;
    this.viewProductButton = viewProductButton;
  }

  @Override
  @NonNull
  public MaterialCardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemProductBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemProductBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_product, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemProductBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.categoryText;
      TextView categoryText = ViewBindings.findChildViewById(rootView, id);
      if (categoryText == null) {
        break missingId;
      }

      id = R.id.nameText;
      TextView nameText = ViewBindings.findChildViewById(rootView, id);
      if (nameText == null) {
        break missingId;
      }

      id = R.id.priceText;
      TextView priceText = ViewBindings.findChildViewById(rootView, id);
      if (priceText == null) {
        break missingId;
      }

      id = R.id.productImage;
      ImageView productImage = ViewBindings.findChildViewById(rootView, id);
      if (productImage == null) {
        break missingId;
      }

      id = R.id.viewProductButton;
      Button viewProductButton = ViewBindings.findChildViewById(rootView, id);
      if (viewProductButton == null) {
        break missingId;
      }

      return new ItemProductBinding((MaterialCardView) rootView, categoryText, nameText, priceText,
          productImage, viewProductButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
