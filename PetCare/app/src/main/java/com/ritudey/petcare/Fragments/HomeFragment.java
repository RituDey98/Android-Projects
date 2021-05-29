package com.ritudey.petcare.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.ritudey.petcare.Adapters.CategoryRecyclerViewAdapter;
import com.ritudey.petcare.Models.CategoryModel;
import com.ritudey.petcare.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    RecyclerView catRecyclerview;
    //Category recyclerview
    CategoryRecyclerViewAdapter categoryAdapter;
    List<CategoryModel> categoryModelsList;
    

    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        catRecyclerview = root.findViewById(R.id.rec_view_category);

        //inflating image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner1,"Discount on Pedigree", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"Discount on Medicines",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3,"40% OFF", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);


        //category recyclerViewAdapter
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelsList = new ArrayList<>();
        categoryAdapter = new CategoryRecyclerViewAdapter(getContext(),categoryModelsList);
        catRecyclerview.setAdapter(categoryAdapter);

        
        return  root;
    }
}