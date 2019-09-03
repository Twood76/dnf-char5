package com.example.dnd5echaracterassistant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.ValueEventListener;


public class UserMain extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UserMain";
    private String userID, characters, profs;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    //Views Stats
    private EditText et_charName, et_lvl, et_str, et_dex, et_con, et_int, et_wis, et_cha;

    //Views Stats
    private TextView tv_skills, tv_savingThrow;
    private Switch cb_intelSave, cb_chaSave, cb_wisSave, cb_dexSave, cb_conSave, cb_strSave;
    private Switch cb_acrobatics, cb_animalHandling, cb_arcana, cb_athletics, cb_deception, cb_history, cb_insight, cb_intimidation, cb_investigation;
    private Switch cb_medicine, cb_nature, cb_perception, cb_performance, cb_persuasion, cb_religion, cb_sleightOfHand, cb_stealth, cb_survival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_create);


        // Views Stats
        et_charName = findViewById(R.id.et_charName);
        et_lvl = findViewById(R.id.et_lvl);
        et_str = findViewById(R.id.et_str);
        et_dex = findViewById(R.id.et_dex);
        et_con = findViewById(R.id.et_con);
        et_int = findViewById(R.id.et_int);
        et_wis = findViewById(R.id.et_wis);
        et_cha = findViewById(R.id.et_cha);

        // Views Profs
        tv_skills = findViewById(R.id.tv_skills);
        tv_savingThrow = findViewById(R.id.tv_savingThrow);

        cb_intelSave = findViewById(R.id.cb_intelSave);
        cb_chaSave = findViewById(R.id.cb_chaSave);
        cb_wisSave = findViewById(R.id.cb_wisSave);
        cb_dexSave = findViewById(R.id.cb_dexSave);
        cb_conSave = findViewById(R.id.cb_conSave);
        cb_strSave = findViewById(R.id.cb_strSave);
        cb_acrobatics = findViewById(R.id.cb_acrobatics);
        cb_animalHandling = findViewById(R.id.cb_animalHandling);
        cb_arcana = findViewById(R.id.cb_arcana);
        cb_athletics = findViewById(R.id.cb_athletics);
        cb_deception = findViewById(R.id.cb_deception);
        cb_history = findViewById(R.id.cb_history);
        cb_insight = findViewById(R.id.cb_insight);
        cb_intimidation = findViewById(R.id.cb_intimidation);
        cb_investigation = findViewById(R.id.cb_investigation);
        cb_medicine = findViewById(R.id.cb_medicine);
        cb_nature = findViewById(R.id.cb_nature);
        cb_perception = findViewById(R.id.cb_perception);
        cb_performance = findViewById(R.id.cb_performance);
        cb_persuasion = findViewById(R.id.cb_persuasion);
        cb_religion = findViewById(R.id.cb_religion);
        cb_sleightOfHand = findViewById(R.id.cb_sleightOfHand);
        cb_stealth = findViewById(R.id.cb_stealth);
        cb_survival = findViewById(R.id.cb_survival);


        // Buttons
        findViewById(R.id.b_createCharacter).setOnClickListener(this);
        findViewById(R.id.b_returnToMain).setOnClickListener(this);
        findViewById(R.id.b_gotToStats).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        userID = user.getUid();
        characters = "characters";
        profs = "proficiencies";

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        showStats(false);

    }
    private void addCharacter() {
        Log.d(TAG, "addCharacter: Adding character to database");
        if (!validateForm()) {
            return;
        }

        int lvl, str, dex, con, intel, wis, cha;
        //boolean[] throwProf  =  {cb_strSave.isChecked(), cb_dexSave.isChecked(), cb_conSave.isChecked(), cb_intelSave.isChecked(), cb_wisSave.isChecked(), cb_chaSave.isChecked()};
        //boolean throwProf  =  true;

        Proficiency prof = new Proficiency(
                cb_strSave.isChecked(), cb_dexSave.isChecked(), cb_conSave.isChecked(), cb_intelSave.isChecked(), cb_wisSave.isChecked(), cb_chaSave.isChecked(),
                cb_acrobatics.isChecked(), cb_animalHandling.isChecked(), cb_arcana.isChecked(), cb_athletics.isChecked(), cb_deception.isChecked(), cb_history.isChecked(),
                cb_insight.isChecked(), cb_intimidation.isChecked(), cb_investigation.isChecked(), cb_medicine.isChecked(), cb_nature.isChecked(), cb_perception.isChecked(),
                cb_performance.isChecked(), cb_persuasion.isChecked(), cb_religion.isChecked(), cb_sleightOfHand.isChecked(), cb_stealth.isChecked(), cb_survival.isChecked()
        );



        String name = et_charName.getText().toString();

        lvl = etToInt(et_lvl);
        str = etToInt(et_str);
        dex = etToInt(et_dex);
        con = etToInt(et_con);
        intel = etToInt(et_int);
        wis = etToInt(et_wis);
        cha = etToInt(et_cha);

        Log.d(TAG, "onClick: Attempting to submit to database: \n" +
                "name: " + name + "\n"
        );

        //handle the exception if the EditText fields are null
        if(!et_charName.equals("") || !et_lvl.equals("") || !et_str.equals("") || !et_dex.equals("") || !et_con.equals("") || !et_int.equals("") || !et_wis.equals("") || !et_cha.equals("")){
            Player player = new Player(name, lvl, str, dex, con, intel, wis, cha);
            //Character player = new Character(name, name1, name2);
            myRef.child("users").child(userID).child(characters).setValue(player);
            myRef.child("users").child(userID).child(profs).setValue(prof);
            // myRef.child("users").child(userID).child("characters").setValue(player);
            toastMessage("New Information has been saved.");
            et_charName.setText("");
            et_lvl.setText("");
            et_str.setText("");
            et_dex.setText("");
            et_con.setText("");
            et_int.setText("");
            et_wis.setText("");
            et_cha.setText("");
        }else{
            toastMessage("Fill out all the fields");
        }
    }

    private void showStats(boolean showStats) {
        if(showStats){
            //Show stat maker
            findViewById(R.id.et_charName).setVisibility(View.VISIBLE);
            findViewById(R.id.et_lvl).setVisibility(View.VISIBLE);
            findViewById(R.id.et_str).setVisibility(View.VISIBLE);
            findViewById(R.id.et_dex).setVisibility(View.VISIBLE);
            findViewById(R.id.et_con).setVisibility(View.VISIBLE);
            findViewById(R.id.et_int).setVisibility(View.VISIBLE);
            findViewById(R.id.et_wis).setVisibility(View.VISIBLE);
            findViewById(R.id.et_cha).setVisibility(View.VISIBLE);
            findViewById(R.id.b_createCharacter).setVisibility(View.VISIBLE);

            //Hide Profs
            findViewById(R.id.tv_skills).setVisibility(View.GONE);
            findViewById(R.id.cb_intelSave).setVisibility(View.GONE);
            findViewById(R.id.cb_chaSave).setVisibility(View.GONE);
            findViewById(R.id.cb_wisSave).setVisibility(View.GONE);
            findViewById(R.id.cb_dexSave).setVisibility(View.GONE);
            findViewById(R.id.cb_conSave).setVisibility(View.GONE);
            findViewById(R.id.cb_strSave).setVisibility(View.GONE);
            findViewById(R.id.tv_savingThrow).setVisibility(View.GONE);
            findViewById(R.id.cb_acrobatics).setVisibility(View.GONE);
            findViewById(R.id.cb_animalHandling).setVisibility(View.GONE);
            findViewById(R.id.cb_arcana).setVisibility(View.GONE);
            findViewById(R.id.cb_athletics).setVisibility(View.GONE);
            findViewById(R.id.cb_deception).setVisibility(View.GONE);
            findViewById(R.id.cb_history).setVisibility(View.GONE);
            findViewById(R.id.cb_insight).setVisibility(View.GONE);
            findViewById(R.id.cb_intimidation).setVisibility(View.GONE);
            findViewById(R.id.cb_investigation).setVisibility(View.GONE);
            findViewById(R.id.cb_medicine).setVisibility(View.GONE);
            findViewById(R.id.cb_nature).setVisibility(View.GONE);
            findViewById(R.id.cb_perception).setVisibility(View.GONE);
            findViewById(R.id.cb_performance).setVisibility(View.GONE);
            findViewById(R.id.cb_persuasion).setVisibility(View.GONE);
            findViewById(R.id.cb_religion).setVisibility(View.GONE);
            findViewById(R.id.cb_sleightOfHand).setVisibility(View.GONE);
            findViewById(R.id.cb_stealth).setVisibility(View.GONE);
            findViewById(R.id.cb_survival).setVisibility(View.GONE);
            findViewById(R.id.b_gotToStats).setVisibility(View.GONE);
        }
        else{
            //Hide stat maker
            findViewById(R.id.et_charName).setVisibility(View.GONE);
            findViewById(R.id.et_lvl).setVisibility(View.GONE);
            findViewById(R.id.et_str).setVisibility(View.GONE);
            findViewById(R.id.et_dex).setVisibility(View.GONE);
            findViewById(R.id.et_con).setVisibility(View.GONE);
            findViewById(R.id.et_int).setVisibility(View.GONE);
            findViewById(R.id.et_wis).setVisibility(View.GONE);
            findViewById(R.id.et_cha).setVisibility(View.GONE);
            findViewById(R.id.b_createCharacter).setVisibility(View.GONE);

            //Show Profs
            findViewById(R.id.tv_skills).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_intelSave).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_chaSave).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_wisSave).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_dexSave).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_conSave).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_strSave).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_savingThrow).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_acrobatics).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_animalHandling).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_arcana).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_athletics).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_deception).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_history).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_insight).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_intimidation).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_investigation).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_medicine).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_nature).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_perception).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_performance).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_persuasion).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_religion).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_sleightOfHand).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_stealth).setVisibility(View.VISIBLE);
            findViewById(R.id.cb_survival).setVisibility(View.VISIBLE);
            findViewById(R.id.b_gotToStats).setVisibility(View.VISIBLE);
        }

    }
    private void returnToMain(){
        Intent intent = new Intent(UserMain.this, MainActivity.class);
        startActivity(intent);
    }
    private int etToInt(EditText et){
        String value = et.getText().toString();
        int a = Integer.parseInt(value);
        return a;
    }
    private boolean validateForm() {
        boolean valid = true;

        String name = et_charName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            et_charName.setError("Required.");
            valid = false;
        } else {
            et_charName.setError(null);
        }
        String lvl = et_lvl.getText().toString();
        if (TextUtils.isEmpty(lvl)) {
            et_lvl.setError("Required.");
            valid = false;
        } else {
            et_lvl.setError(null);
        }
        String str = et_str.getText().toString();
        if (TextUtils.isEmpty(str)) {
            et_str.setError("Required.");
            valid = false;
        } else {
            et_str.setError(null);
        }
        String dex = et_dex.getText().toString();
        if (TextUtils.isEmpty(dex)) {
            et_dex.setError("Required.");
            valid = false;
        } else {
            et_dex.setError(null);
        }
        String con = et_con.getText().toString();
        if (TextUtils.isEmpty(con)) {
            et_con.setError("Required.");
            valid = false;
        } else {
            et_con.setError(null);
        }
        String intel = et_int.getText().toString();
        if (TextUtils.isEmpty(intel)) {
            et_int.setError("Required.");
            valid = false;
        } else {
            et_int.setError(null);
        }
        String wis = et_wis.getText().toString();
        if (TextUtils.isEmpty(wis)) {
            et_wis.setError("Required.");
            valid = false;
        } else {
            et_wis.setError(null);
        }
        String cha = et_cha.getText().toString();
        if (TextUtils.isEmpty(cha)) {
            et_cha.setError("Required.");
            valid = false;
        } else {
            et_cha.setError(null);
        }

        return valid;
    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: UserMain");
        int i = v.getId();
        if (i == R.id.b_createCharacter) {
            addCharacter();
        } else if (i == R.id.b_returnToMain) {
            returnToMain();
        }
        else if (i == R.id.b_gotToStats) {
            showStats(true);
        }
    }
}
