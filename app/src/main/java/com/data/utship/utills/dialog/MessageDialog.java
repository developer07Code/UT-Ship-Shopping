package com.data.utship.utills.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.data.utship.databinding.FragmentMessageDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MessageDialog extends BottomSheetDialogFragment {
    private FragmentMessageDialogBinding vb;
    private String titleText, subTitleText;

    public MessageDialog() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vb = FragmentMessageDialogBinding.inflate(inflater, container, false);
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

        vb.messageDialogTitle.setText(titleText);
        vb.messageDialogSubtitle.setText(subTitleText);

        vb.messageDialogClose.setOnClickListener(button -> dismiss());

    }

    public void setMessage(String titleText, String subTitleText) {
        this.titleText = titleText;
        this.subTitleText = subTitleText;
    }


}
