package com.df.calculatorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.util.NumberUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.df.logiclib.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 9001;

    private Context mContext;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;
    private Boolean operatorBtnPressed = false;
    private String regex = "((\\d+)((\\.\\d{1,2})?))$";
    Pattern mPattern;
    Matcher mMatcher;

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnAdd, btnSub, btnMul, btnDiv, btnSin, btnCos, btnTan,
            btnDecimal, btnEqual, btnClear;
    TextView mCalculatorDisplay, username, useremail;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mContext = this;
        mSharedPref = mContext.getSharedPreferences("UserCredentials", MODE_PRIVATE);
        mEditor = mSharedPref.edit();
        mPattern = Pattern.compile(regex);

        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void init(){

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btn0 = findViewById(R.id.button0);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btn9 = findViewById(R.id.button9);
        btnSin = findViewById(R.id.buttonSine);
        btnCos = findViewById(R.id.buttonCosine);
        btnTan = findViewById(R.id.buttonTangent);
        btnDecimal = findViewById(R.id.buttonDecimalPoint);
        btnEqual = findViewById(R.id.buttonEquals);
        btnClear = findViewById(R.id.buttonClear);
        btnAdd = findViewById(R.id.buttonAdd);
        btnSub = findViewById(R.id.buttonSubtract);
        btnMul = findViewById(R.id.buttonMultiply);
        btnDiv = findViewById(R.id.buttonDivide);

        mCalculatorDisplay = findViewById(R.id.textView1);
        username = (TextView) navigationView.findViewById(R.id.username);
        useremail = (TextView) navigationView.findViewById(R.id.useremail);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mCalculatorDisplay.getText()))
                    mCalculatorDisplay.setText("0");
                else displayNumber("0");

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumber("1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumber("2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumber("3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumber("4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumber("5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumber("6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumber("7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumber("8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumber("9");
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalculatorDisplay.setText("");
            }
        });
        btnSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String val = mCalculatorDisplay.getText().toString();
                if(!TextUtils.isEmpty(val) &&
                        checkforValidNumner(val)) {

                    Double val1 = Double.parseDouble(val);

                    Double result = new Logic().computeTrigonometricCal(val1, "Sine");
                    mCalculatorDisplay.setText(Double.toString(result));
                    operatorBtnPressed = true;
                } else {
                    Toast.makeText(mContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    mCalculatorDisplay.setText("");
                }
            }
        });
        btnCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String val = mCalculatorDisplay.getText().toString();
                if(!TextUtils.isEmpty(val) &&
                        checkforValidNumner(val)) {

                    Double val1 = Double.parseDouble(val);

                    Double result = new Logic().computeTrigonometricCal(val1, "Cosine");
                    mCalculatorDisplay.setText(Double.toString(result));
                    operatorBtnPressed = true;
                } else {
                    Toast.makeText(mContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    mCalculatorDisplay.setText("");
                }
            }
        });
        btnTan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String val = mCalculatorDisplay.getText().toString();
                if(!TextUtils.isEmpty(val) &&
                        checkforValidNumner(val)) {

                    Double val1 = Double.parseDouble(val);

                    Double result = new Logic().computeTrigonometricCal(val1, "Tangent");
                    mCalculatorDisplay.setText(Double.toString(result));
                    operatorBtnPressed = true;
                } else {
                    Toast.makeText(mContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    mCalculatorDisplay.setText("");
                }
            }
        });
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCalculatorDisplay.getText().toString().contains(".")) {

                } else if(TextUtils.isEmpty(mCalculatorDisplay.getText().toString())) {
                    mCalculatorDisplay.setText("0.");
                } else if(checkforValidNumner(mCalculatorDisplay.getText().toString())) {
                    mCalculatorDisplay.setText(mCalculatorDisplay.getText().toString() + ".");
                }
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = mCalculatorDisplay.getText().toString();
                if(!TextUtils.isEmpty(val) &&
                        checkforValidNumner(val)) {

                    Double val1 = Double.parseDouble(mSharedPref.getString("Val1","0"));
                    Double val2 = Double.parseDouble(val);
                    Logic mLogic = new Logic();
                    Double result = mLogic.computeArithmeticCal(val1, val2, mSharedPref.getString("Operator",""));
                    mCalculatorDisplay.setText(Double.toString(result));
                    operatorBtnPressed = true;

                } else {
                    Toast.makeText(mContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    mCalculatorDisplay.setText("");
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String val = mCalculatorDisplay.getText().toString();
                if(!TextUtils.isEmpty(val) &&
                        checkforValidNumner(val)) {

                    storeVal1(val, "addition");
                    operatorBtnPressed = true;

                } else {
                    Toast.makeText(mContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    mCalculatorDisplay.setText("");
                }
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = mCalculatorDisplay.getText().toString();
                if(!TextUtils.isEmpty(val) &&
                        checkforValidNumner(val)) {

                    storeVal1(val, "subtraction");
                    operatorBtnPressed = true;

                } else {
                    Toast.makeText(mContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    mCalculatorDisplay.setText("");
                }
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = mCalculatorDisplay.getText().toString();
                if(!TextUtils.isEmpty(val) &&
                        checkforValidNumner(val)) {

                    storeVal1(val, "multiplication");
                    operatorBtnPressed = true;

                } else {
                    Toast.makeText(mContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    mCalculatorDisplay.setText("");
                }
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = mCalculatorDisplay.getText().toString();

                if(!TextUtils.isEmpty(val) &&
                        checkforValidNumner(val)) {

                    storeVal1(val, "division");
                    operatorBtnPressed = true;

                } else {
                    Toast.makeText(mContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    mCalculatorDisplay.setText("");
                }
            }
        });

    }

    public void login(){

        List<AuthUI.IdpConfig> providers = Arrays.asList(
        new AuthUI.IdpConfig.GoogleBuilder().build(),
        new AuthUI.IdpConfig.FacebookBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
            RC_SIGN_IN);
    }

    public void displayNumber(String num) {

        if(operatorBtnPressed) {
            mCalculatorDisplay.setText("");
            operatorBtnPressed = false;
        }
        if(mCalculatorDisplay.getText().toString() == "0") mCalculatorDisplay.setText(num);
        else mCalculatorDisplay.setText(mCalculatorDisplay.getText().toString() + num);
    }

    public void storeVal1(String val, String operator){

        mEditor.putString("Operator", operator);
        mEditor.putString("Val1", val);
        mEditor.apply();
    }

    public boolean checkforValidNumner(String val) {

        mMatcher = mPattern.matcher(val);
        if(mMatcher.find() && mMatcher.group().equals(val)){
            return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null) {
                    Toast.makeText(mContext, "Login Successful!!", Toast.LENGTH_SHORT).show();
                    username.setText(user.getDisplayName());
                    useremail.setText(user.getEmail());
                }
            } else
                Toast.makeText(CalculatorViewActivity.this, "Unable to login in ", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculator_view, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.login){

            login();

        }else if (id == R.id.logout) {

            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            Toast.makeText(mContext, "User Logged out!!",Toast.LENGTH_SHORT).show();
                            username.setText("");
                            useremail.setText("");
                        }
                    });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
