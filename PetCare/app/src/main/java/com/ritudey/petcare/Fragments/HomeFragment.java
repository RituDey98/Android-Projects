package com.ritudey.petcare.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ritudey.petcare.Adapters.CategoryRecyclerViewAdapter;
import com.ritudey.petcare.Adapters.NewProRecyclerViewAdapter;
import com.ritudey.petcare.Adapters.PetRecyclerViewAdapter;
import com.ritudey.petcare.Adapters.PopularProRecyclerViewAdapter;
import com.ritudey.petcare.Models.CategoryModel;
import com.ritudey.petcare.Models.NewProModel;
import com.ritudey.petcare.Models.PetModel;
import com.ritudey.petcare.Models.PopularProModel;
import com.ritudey.petcare.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    ProgressDialog progressDialog;
    Boolean progress,progress1,progress2,progress3;
    LinearLayout homeLayout;

    RecyclerView catRecyclerview,petRecyclerview,newProRecyclerview,popularProRecyclerview;


    //Category recyclerview
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    List<CategoryModel> categoryModelsList;

    //Pet recyclerview
    PetRecyclerViewAdapter petRecyclerViewAdapter;
    List<PetModel> petModelList;


    //New Product recyclerview
    NewProRecyclerViewAdapter newProRecyclerViewAdapter;
    List<NewProModel> newProModelList;

    //Popular Product recyclerview
    PopularProRecyclerViewAdapter popularProRecyclerViewAdapter;
    List<PopularProModel> popularProModelList;

    //Database declaration
    FirebaseFirestore db;
    

    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        catRecyclerview = root.findViewById(R.id.rec_view_category);
        petRecyclerview=root.findViewById(R.id.rec_view_pet);
        newProRecyclerview=root.findViewById(R.id.rec_view_new_pro);
        popularProRecyclerview=root.findViewById(R.id.rec_view_pop_pro);
        homeLayout=root.findViewById(R.id.home_linear_layout);

        //Database initialization
        db = FirebaseFirestore.getInstance();


        //Progress dialog
        progress=false; progress1=false; progress2=false;progress3=false;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.setMessage("Loading....");
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.getWindow().setLayout(350,350);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        homeLayout.setVisibility(View.GONE);


        //inflating image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.pet_food_banner,"Discount on Pedigree", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.pet_healthcare_1,"Discount on Medicines",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.pets_discount,"40% OFF", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.pet_adopt,"Adopt a pet and Save a life", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        //category recyclerViewAdapter
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelsList = new ArrayList<>();
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(getContext(),categoryModelsList);
        catRecyclerview.setAdapter(categoryRecyclerViewAdapter);

        //pet recyclerViewAdapter
        petRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        petModelList = new ArrayList<>();
        petRecyclerViewAdapter = new PetRecyclerViewAdapter(getContext(),petModelList);
        petRecyclerview.setAdapter(petRecyclerViewAdapter);

        //new product recyclerViewAdapter
        newProRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProModelList = new ArrayList<>();
        newProRecyclerViewAdapter=new NewProRecyclerViewAdapter(getContext(),newProModelList);
        newProRecyclerview.setAdapter(newProRecyclerViewAdapter);

        //popular product recyclerViewAdapter
        popularProRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProModelList = new ArrayList<>();
        popularProRecyclerViewAdapter = new PopularProRecyclerViewAdapter(getContext(),popularProModelList);
        popularProRecyclerview.setAdapter(popularProRecyclerViewAdapter);


        //Read data from db for category
        db.collection("category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelsList.add(categoryModel);
                                categoryRecyclerViewAdapter.notifyDataSetChanged();

                                progress=true;
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        //Read data from db for pets
        db.collection("pets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                PetModel petModel = document.toObject(PetModel.class);
                                petModelList.add(petModel);
                                petRecyclerViewAdapter.notifyDataSetChanged();

                                progress1=true;

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        //Read data from db for New Products
        db.collection("New Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                NewProModel newProModel = document.toObject(NewProModel.class);
                                newProModelList.add(newProModel);
                                newProRecyclerViewAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();

                                progress2=true;
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        //Read data from db for allProducts
        db.collection("allProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                PopularProModel popularProModel = document.toObject(PopularProModel.class);
                                popularProModelList.add(popularProModel);
                                popularProRecyclerViewAdapter.notifyDataSetChanged();

                                progress3=true;
                                homeLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

//        if(progress&&progress1&&progress2&&progress3){
//            homeLayout.setVisibility(View.VISIBLE);
//            progressDialog.dismiss();
//
//        }

        return  root;
    }
}