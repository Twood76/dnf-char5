package com.example.dnd5echaracterassistant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class UseCharacter extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "UseCharacter";
    //Player player = new Player();
    //Player player = new Player("Ryan", 1, 18, 16, 14, 12, 10, 8);

    private TextView tv_result, tv_advRoll, tv_damage, tv_charName, tv_lvl, tv_passivePer, tv_str, tv_dex, tv_con, tv_intel, tv_wis, tv_cha;
    private Switch switch_advRoll, switch_dadvRoll;
    private Spinner spinner_atkTypes, spinner_damage;
    private EditText et_damageModifier;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String userID, characters, profs;

    Player player = new Player();
    Proficiency prof = new Proficiency();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_character);

        //Views
        tv_result = findViewById(R.id.tv_result);
        tv_advRoll = findViewById(R.id.tv_advRoll);
        tv_damage = findViewById(R.id.tv_damage);
        tv_charName = findViewById(R.id.tv_charName);
        tv_lvl = findViewById(R.id.tv_lvl);
        tv_passivePer = findViewById(R.id.tv_passivePer);
        tv_str = findViewById(R.id.tv_str);
        tv_dex = findViewById(R.id.tv_dex);
        tv_con = findViewById(R.id.tv_con);
        tv_intel = findViewById(R.id.tv_intel);
        tv_wis = findViewById(R.id.tv_wis);
        tv_cha = findViewById(R.id.tv_cha);

        //Switchs
        switch_advRoll = findViewById(R.id.switch_advRoll);
        switch_dadvRoll = findViewById(R.id.switch_dadvRoll);

        //EditTexts
        et_damageModifier = findViewById(R.id.et_damageModifier);

        //Spinners
        spinner_atkTypes = findViewById(R.id.spinner_atkTypes);
        String[] atkTypes = new String[]{"Str", "Dex", "Con", "Int", "Wis", "Cha"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, atkTypes);
        spinner_atkTypes.setAdapter(adapter);

        spinner_damage = findViewById(R.id.spinner_damage);
        String[] damageDice = new String[]{"D20", "D12", "D10", "D8", "D6", "D4", "D2"};
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, damageDice);
        spinner_damage.setAdapter(adapter);

        //Buttons
        findViewById(R.id.b_attack).setOnClickListener(this);
        findViewById(R.id.b_damage).setOnClickListener(this);

        //Button Checks
        findViewById(R.id.b_strCheck).setOnClickListener(this);
        findViewById(R.id.b_dexCheck).setOnClickListener(this);
        findViewById(R.id.b_conCheck).setOnClickListener(this);
        findViewById(R.id.b_intCheck).setOnClickListener(this);
        findViewById(R.id.b_wisCheck).setOnClickListener(this);
        findViewById(R.id.b_chaCheck).setOnClickListener(this);
        findViewById(R.id.b_initiative).setOnClickListener(this);

        //Button Saves
        findViewById(R.id.b_strSave).setOnClickListener(this);
        findViewById(R.id.b_dexSave).setOnClickListener(this);
        findViewById(R.id.b_conSave).setOnClickListener(this);
        findViewById(R.id.b_intelSave).setOnClickListener(this);
        findViewById(R.id.b_wisSave).setOnClickListener(this);
        findViewById(R.id.b_chaSave).setOnClickListener(this);

        findViewById(R.id.b_returnToMain).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        characters = "characters";
        profs = "proficiencies";

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){

            player.setName(ds.child(userID).child(characters).getValue(Player.class).getName()); //set the name
            player.setLvl(ds.child(userID).child(characters).getValue(Player.class).getLvl());
            player.setStrength(ds.child(userID).child(characters).getValue(Player.class).getStrength());
            player.setDexterity(ds.child(userID).child(characters).getValue(Player.class).getDexterity());
            player.setConstitution(ds.child(userID).child(characters).getValue(Player.class).getConstitution());
            player.setIntelligence(ds.child(userID).child(characters).getValue(Player.class).getIntelligence());
            player.setWisdom(ds.child(userID).child(characters).getValue(Player.class).getWisdom());
            player.setCharisma(ds.child(userID).child(characters).getValue(Player.class).getCharisma());

            //Save Prof
            prof.setStrSave(ds.child(userID).child(profs).getValue(Proficiency.class).isStrSave()); //set the name
            prof.setDexSave(ds.child(userID).child(profs).getValue(Proficiency.class).isDexSave());
            prof.setConSave(ds.child(userID).child(profs).getValue(Proficiency.class).isConSave());
            prof.setIntelSave(ds.child(userID).child(profs).getValue(Proficiency.class).isIntelSave());
            prof.setWisSave(ds.child(userID).child(profs).getValue(Proficiency.class).isWisSave());
            prof.setChaSave(ds.child(userID).child(profs).getValue(Proficiency.class).isChaSave());

            //Skill Prof
            prof.setAcrobatics(ds.child(userID).child(profs).getValue(Proficiency.class).isAcrobatics());
            prof.setAnimalHandling(ds.child(userID).child(profs).getValue(Proficiency.class).isAnimalHandling());
            prof.setArcana(ds.child(userID).child(profs).getValue(Proficiency.class).isArcana());
            prof.setAthletics(ds.child(userID).child(profs).getValue(Proficiency.class).isAthletics());
            prof.setDeception(ds.child(userID).child(profs).getValue(Proficiency.class).isDeception());
            prof.setHistory(ds.child(userID).child(profs).getValue(Proficiency.class).isHistory());
            prof.setInsight(ds.child(userID).child(profs).getValue(Proficiency.class).isInsight());
            prof.setIntimidation(ds.child(userID).child(profs).getValue(Proficiency.class).isIntimidation());
            prof.setInvestigation(ds.child(userID).child(profs).getValue(Proficiency.class).isInvestigation());
            prof.setMedicine(ds.child(userID).child(profs).getValue(Proficiency.class).isMedicine());
            prof.setNature(ds.child(userID).child(profs).getValue(Proficiency.class).isNature());
            prof.setPerception(ds.child(userID).child(profs).getValue(Proficiency.class).isPerception());
            prof.setPerformance(ds.child(userID).child(profs).getValue(Proficiency.class).isPerformance());
            prof.setPersuasion(ds.child(userID).child(profs).getValue(Proficiency.class).isPersuasion());
            prof.setReligion(ds.child(userID).child(profs).getValue(Proficiency.class).isReligion());
            prof.setSleightOfHand(ds.child(userID).child(profs).getValue(Proficiency.class).isSleightOfHand());
            prof.setStealth(ds.child(userID).child(profs).getValue(Proficiency.class).isStealth());
            prof.setSurvival(ds.child(userID).child(profs).getValue(Proficiency.class).isSurvival());

            //display all the information
            Log.d(TAG, "showData: name: " + player.getName());

            showStats();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();

    }

    //Test methods
    public void atheleticsCheck() {
        strCheck(prof.isAcrobatics());
    }

    //Ability Check
    public void AbilityCheck(int mod, String s, boolean isProf){
        int d20Roll = rollDie(20);
        int check = d20Roll + mod;
        String display;

        tv_result.setTextColor(Color.BLACK);
        tv_advRoll.setTextColor(Color.BLACK);

        if(isProf) {
            check = d20Roll + mod + player.proficiency;
            display = (s + check + "\nRoll = " + d20Roll + " Mod = " + mod + " Prof = " + player.proficiency);
        }
        else display = (s + check + "\nRoll = " + d20Roll + " Mod = " + mod);

        tv_result.setText(display);
        tv_advRoll.setText("");
        tv_damage.setText("");

        if (switch_advRoll.isChecked() ^ switch_dadvRoll.isChecked()) {
            d20Roll = rollDie(20);
            int check2 = d20Roll + mod;
            if(isProf){
                check2 = d20Roll + mod + player.proficiency;
                display = (s + check2 + "\nRoll = " + d20Roll + " Mod = " + mod + " Prof = " + player.proficiency);
            }
            else display = (s + check2 + "\nRoll = " + d20Roll + " Mod = " + mod);

            tv_advRoll.setText(display);

            if(check >= check2) {
                tv_result.setTextColor(Color.GREEN);
            }
            else{
                tv_advRoll.setTextColor(Color.GREEN);
            }
        }
    }
    public void strCheck(boolean prof) {
        int mod = player.getStrBonus();
        String s = "Strength        : ";
        AbilityCheck(mod, s, prof);
    }
    public void dexCheck(boolean prof) {
        int mod = player.dexBonus;
        String s = "Dexterity       : ";
        AbilityCheck(mod, s, prof);
    }
    public void conCheck(boolean prof) {
        int mod = player.conBonus;
        String s = "Constitution : ";
        AbilityCheck(mod, s, prof);
    }
    public void intCheck(boolean prof) {
        int mod = player.intBonus;
        String s = "Intelligence  : ";
        AbilityCheck(mod, s, prof);
    }
    public void wisCheck(boolean prof) {
        int mod = player.wisBonus;
        String s = "Wisdom        : ";
        AbilityCheck(mod, s, prof);
    }
    public void chaCheck(boolean prof) {
        int mod = player.chaBonus;
        String s = "Charisma     : ";
        AbilityCheck(mod, s, prof);
    }
    public void initiativeCheck(boolean prof) {
        int mod = player.dexBonus;
        String s = "Initiative        : ";
        AbilityCheck(mod, s, prof);
    }
    public void attack(int a) {

        if (a == 0) {
            strCheck(true);
        } else if (a == 1) {
            dexCheck(true);
        } else if (a == 2) {
            conCheck(true);
        } else if (a == 3) {
            intCheck(true);
        } else if (a == 4) {
            wisCheck(true);
        } else if (a == 5) {
            chaCheck(true);
        }
        damage(spinner_damage.getSelectedItemPosition(), etToInt(et_damageModifier));
    }
    public void damage(int a, int mod) {
        int roll, damage;
        roll = 0;
        if (a == 0) {
            roll = rollDie(20);
        } else if (a == 1) {
            roll = rollDie(12);
        } else if (a == 2) {
            roll = rollDie(10);
        } else if (a == 3) {
            roll = rollDie(8);
        } else if (a == 4) {
            roll = rollDie(6);
        } else if (a == 5) {
            roll = rollDie(4);
        } else if (a == 6) {
            roll = rollDie(2);
        }
        damage = roll + mod;
        tv_damage.setText("Damage: " + damage + "\nRoll = " + roll + " Mod = " + mod);
    }
    public void SavingThrow(int mod, String s, boolean isProf) {
        int d20Roll = rollDie(20);
        int save = d20Roll + mod;
        String display;

        tv_result.setTextColor(Color.BLACK);
        tv_advRoll.setTextColor(Color.BLACK);

        tv_advRoll.setText("");
        tv_damage.setText("");

        if(isProf) {
            save = mod + d20Roll + player.proficiency;
            display = (s + save + "\nRoll = " + d20Roll + " Mod = " + mod + " Prof = " + player.proficiency);
        }
        else display = (s + save + "\nRoll = " + d20Roll + " Mod = " + mod);

        tv_result.setText(display);

        if (switch_advRoll.isChecked() ^ switch_dadvRoll.isChecked()) {
            d20Roll = rollDie(20);
            int save2 = d20Roll + mod;
            if(isProf){
                save2 = mod + d20Roll + player.proficiency;
                display = (s + save2 + "\nRoll = " + d20Roll + " Mod = " + mod + " Prof = " + player.proficiency);
            }
            else display = (s + save2 + "\nRoll = " + d20Roll + " Mod = " + mod);

            tv_advRoll.setText(display);

            if(save >= save2) {
                tv_result.setTextColor(Color.GREEN);
            }
            else{
                tv_advRoll.setTextColor(Color.GREEN);
            }

        }

    }
    public void strSave() {
        int mod = player.strBonus;
        String s = "Strength      : ";
        SavingThrow(mod, s, prof.isStrSave());
    }
    public void dexSave() {
        int mod = player.dexBonus;
        String s = "Dexterity     : ";
        SavingThrow(mod, s, prof.isDexSave());
    }
    public void conSave() {
        int mod = player.conBonus;
        String s = "Constitution : ";
        SavingThrow(mod, s, prof.isConSave());
    }
    public void intelSave() {
        int mod = player.intBonus;
        String s = "Intelligence   : ";
        SavingThrow(mod, s, prof.isIntelSave());
    }
    public void wisSave() {
        int mod = player.wisBonus;
        String s = "Wisdom       : ";
        SavingThrow(mod, s, prof.isWisSave());
    }
    public void chaSave() {
        int mod = player.chaBonus;
        String s = "Charisma     : ";
        SavingThrow(mod, s, prof.isChaSave());
    }
    public int rollDie(int dieSize){
        Random r = new Random();
        int d20Roll =  r.nextInt(dieSize) + 1;
        return d20Roll;
    }

    public void showStats(){
        tv_charName.setText("Name: " + player.getName());
        tv_lvl.setText("Level: " + player.getLvl() + "");
        tv_passivePer.setText("Passive Perception: " + (10 + player.getWisBonus()) + "");
        tv_str.setText("Str:/n" + player.getStrength() + "");
        tv_dex.setText("Dex:/n" + player.getDexterity() + "");
        tv_con.setText("Con:/n" + player.getConstitution() + "");
        tv_intel.setText("Int:/n" + player.getIntelligence() + "");
        tv_wis.setText("Wis:/n" + player.getWisdom() + "");
        tv_cha.setText("Cha:/n" + player.getCharisma() + "");
    }

    private int etToInt(EditText et){
        String value = et.getText().toString();
        if(TextUtils.isEmpty(value) || (value.equals("-"))) value = "0";
        int a = Integer.parseInt(value);
        return a;
    }
    private void returnToMain(){
        Intent intent = new Intent(UseCharacter.this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.b_strCheck) {
            strCheck(false);
        } else if (i == R.id.b_dexCheck) {
            dexCheck(false);
        } else if (i == R.id.b_conCheck) {
            conCheck(false);
        } else if (i == R.id.b_intCheck) {
            intCheck(false);
        } else if (i == R.id.b_wisCheck) {
            wisCheck(false);
        } else if (i == R.id.b_chaCheck) {
            chaCheck(false);
        } else if (i == R.id.b_strSave) {
            strSave();
        } else if (i == R.id.b_dexSave) {
            dexSave();
        } else if (i == R.id.b_conSave) {
            conSave();
        } else if (i == R.id.b_intelSave) {
            intelSave();
        } else if (i == R.id.b_wisSave) {
            wisSave();
        } else if (i == R.id.b_chaSave) {
            chaSave();
        } else if (i == R.id.b_attack) {
            attack(spinner_atkTypes.getSelectedItemPosition());
        } else if (i == R.id.b_damage) {
            damage(spinner_damage.getSelectedItemPosition(), etToInt(et_damageModifier));
        } else if (i == R.id.b_initiative) {
            initiativeCheck(false);
        } else if (i == R.id.b_returnToMain) {
            returnToMain();
        }

    }
}
