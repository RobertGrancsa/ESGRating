package com.redrevoraise.esgrating;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceCompanyFront;
    private DatabaseReference mReferenceCompany;
    private DatabaseReference mReferenceTops;
//    private DatabaseReference mReferenceHomework;
    private List<CompanyFront> companies = new ArrayList<>();
//    private List<Homework> homeworks = new ArrayList<>();
//    private String user;
//    private String currentYear;
//    private String currentSemester;
//    private String profile;

    public interface DataStatus{
        void DataIsLoadedComp(List<CompanyFront> companies, List<String> keys);
        void DataIsLoadedCompany(Company company, String key);
        void DataIsLoadedTop(List<CompanyFront> companies, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
//        profile = "CTI"; // TODO link users profile and semester/year when he logs in
//        currentYear = "Anul_1";
//        currentSemester = "Semestrul_1";
//        user = "https://awaree-ea116-default-rtdb.firebaseio.com/Specializations/"+profile+"/"+currentYear+"/"+currentSemester;
//        mReferenceSubjects = mDatabase.getReferenceFromUrl(user);
        mReferenceCompanyFront = mDatabase.getReference("CompanyList");
        mReferenceCompany = mDatabase.getReference("Companies");
        mReferenceTops = mDatabase.getReference("Tops");
    }

    public void readCompanies(final DataStatus dataStatus){
        mReferenceCompanyFront.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                companies.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Log.d(TAG, "onDataChange: " + keyNode.getKey());
                    CompanyFront company = keyNode.getValue(CompanyFront.class);
                    companies.add(company);
                }
                dataStatus.DataIsLoadedComp(companies, keys);
                Log.d(TAG, "onDataChange: data is loaded sj");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    public void readCompany(String key, final DataStatus dataStatus){
        mReferenceCompany.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot keyNode = dataSnapshot.child(key);
                Company company = keyNode.getValue(Company.class);
                Log.d(TAG, "onDataChange: " + company.getESG_score_peers().get(0));
                dataStatus.DataIsLoadedCompany(company, key);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    public void readTop(final DataStatus dataStatus){
        mReferenceTops.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                companies.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Log.d(TAG, "onDataChange: " + keyNode.getKey());
                    CompanyFront company = keyNode.getValue(CompanyFront.class);
                    companies.add(company);
                }
                dataStatus.DataIsLoadedTop(companies, keys);
                Log.d(TAG, "onDataChange: data is loaded top comps");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    public void addCompany(CompanyFront company, final DataStatus dataStatus){
        String key = mReferenceCompanyFront.push().getKey();
        mReferenceCompanyFront.child(key).setValue(company).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateCompany(String key, CompanyFront company, final DataStatus dataStatus){
        mReferenceCompanyFront.child(key).setValue(company).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteCompany(String key,final DataStatus dataStatus){
        mReferenceCompanyFront.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsDeleted();
            }
        });
    }

}

