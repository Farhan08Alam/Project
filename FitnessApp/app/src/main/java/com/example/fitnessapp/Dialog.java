package com.example.fitnessapp;

import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment {

    public interface OnInputSelected{
        void  sendInput(String input);
    }

    public OnInputSelected inputSelected;

    private EditText mInput;
    private Button mCancle,mOk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_laoyut
                , container, false);

        mInput = v.findViewById(R.id.dialog_goal);
        mCancle = v.findViewById(R.id.cancle);
        mOk = v.findViewById(R.id.ok);

        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = mInput.getText().toString();
                if(!input.isEmpty()){
                    inputSelected.sendInput(input);
                    getDialog().dismiss();
                }


            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            inputSelected = (OnInputSelected)getTargetFragment();
        }catch (ClassCastException e){
            Log.e("Error","onAttach: ClassCatchExeption - "+e.getMessage());
        }
    }

}
