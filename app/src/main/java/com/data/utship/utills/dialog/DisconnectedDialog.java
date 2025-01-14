package com.data.utship.utills.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.data.utship.databinding.FragmentDisconnectedBinding;


public class DisconnectedDialog extends DialogFragment {
    private FragmentDisconnectedBinding vb;

    public DisconnectedDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().getWindow().setWindowAnimations(com.data.utship.R.style.DialogLayOnAnimation);

        vb = FragmentDisconnectedBinding.inflate(inflater, container, false);
        return vb.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vb = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(false);

        vb.disconnectedDialogClose.setOnClickListener(view1 -> dismiss());

    }
}