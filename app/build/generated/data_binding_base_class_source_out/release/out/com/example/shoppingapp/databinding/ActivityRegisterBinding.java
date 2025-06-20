// Generated by view binder compiler. Do not edit!
package com.example.shoppingapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.shoppingapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final EditText emailEditText;

  @NonNull
  public final TextView loginTextView;

  @NonNull
  public final EditText nameEditText;

  @NonNull
  public final EditText passwordEditText;

  @NonNull
  public final Button registerButton;

  private ActivityRegisterBinding(@NonNull LinearLayout rootView, @NonNull EditText emailEditText,
      @NonNull TextView loginTextView, @NonNull EditText nameEditText,
      @NonNull EditText passwordEditText, @NonNull Button registerButton) {
    this.rootView = rootView;
    this.emailEditText = emailEditText;
    this.loginTextView = loginTextView;
    this.nameEditText = nameEditText;
    this.passwordEditText = passwordEditText;
    this.registerButton = registerButton;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.emailEditText;
      EditText emailEditText = ViewBindings.findChildViewById(rootView, id);
      if (emailEditText == null) {
        break missingId;
      }

      id = R.id.loginTextView;
      TextView loginTextView = ViewBindings.findChildViewById(rootView, id);
      if (loginTextView == null) {
        break missingId;
      }

      id = R.id.nameEditText;
      EditText nameEditText = ViewBindings.findChildViewById(rootView, id);
      if (nameEditText == null) {
        break missingId;
      }

      id = R.id.passwordEditText;
      EditText passwordEditText = ViewBindings.findChildViewById(rootView, id);
      if (passwordEditText == null) {
        break missingId;
      }

      id = R.id.registerButton;
      Button registerButton = ViewBindings.findChildViewById(rootView, id);
      if (registerButton == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((LinearLayout) rootView, emailEditText, loginTextView,
          nameEditText, passwordEditText, registerButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
