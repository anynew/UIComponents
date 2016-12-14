package com.ui.anynew.uicomponents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ui.anynew.uicomponents.ui.CircleProgress;
import com.ui.anynew.uicomponents.ui.CreditSesameRingView;
import com.ui.anynew.uicomponents.ui.TestView;

public class MainActivity extends AppCompatActivity {
    private CreditSesameRingView mCreditSesameRingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCreditSesameRingView = (CreditSesameRingView)findViewById(R.id.creditSesameRingView);
        mCreditSesameRingView.setSesameValues(540);
    }
}
