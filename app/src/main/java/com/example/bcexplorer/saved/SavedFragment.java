package com.example.bcexplorer.saved;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bcexplorer.Constants;
import com.example.bcexplorer.global.MainActivity;
import com.example.bcexplorer.R;
import com.example.bcexplorer.database.Location;
import com.example.bcexplorer.databinding.FragmentSavedBinding;
import com.example.bcexplorer.utils.ListItemModel;
import com.example.bcexplorer.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SavedFragment extends Fragment {

    public SavedFragment() {
        // Required empty public constructor
    }

    private FragmentSavedBinding b;
    private static boolean noSavedCityGuides = false, noSavedDestinations = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentSavedBinding.inflate(inflater, container, false);
//        Log.d("SAVED_FRAGMENT", "SavedFragment created");
        initAdapter();
        setupRecyclerView();
        checkSavedItems();

        return b.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d("SAVED_FRAGMENT", "SavedFragment resumed");
        initAdapter();
        setupRecyclerView();
        checkSavedItems();
    }

    private void initAdapter() {
        ArrayList<ListItemModel> arrayList = new ArrayList<ListItemModel>();

        Context context = requireContext();

        // WHISTLER
        if (Utils.getBoolean(context, Constants.WHISTLER))
            arrayList.add(new ListItemModel("Whistler",
                    "Tourist Spot Outside Vancouver",
                    R.drawable.whistler));

        // VANCOUVER
        if (Utils.getBoolean(context, Constants.VANCOUVER))
            arrayList.add(new ListItemModel("Downtown Vancouver",
                    "Center of The City",
                    R.drawable.vancouver));

        // WHITE ROCK
        if (Utils.getBoolean(context, Constants.WHITE_ROCK))
            arrayList.add(new ListItemModel("White Rock",
                    "City In British Columbia",
                    R.drawable.white_rock));

        // GETTING_AROUND_WHISTLER
        if (Utils.getBoolean(context, Constants.GETTING_AROUND_WHISTLER))
            arrayList.add(new ListItemModel("Getting Around Whistler",
                    "Maps and Tips",
                    R.drawable.map_whistler));

        // GETTING_AROUND_VANCOUVER
        if (Utils.getBoolean(context, Constants.GETTING_AROUND_VANCOUVER))
            arrayList.add(new ListItemModel("Getting Around DT Vancouver",
                    "Maps and Tips",
                    R.drawable.map_dtvancouver));

        // GETTING_AROUND_WHITE ROCK
        if (Utils.getBoolean(context, Constants.GETTING_AROUND_WHITE_ROCK))
            arrayList.add(new ListItemModel("Getting Around White Rock",
                    "Maps and Tips",
                    R.drawable.map_whiterock));

        // WHATS_NEW_WHISTLER
        if (Utils.getBoolean(context, Constants.WHATS_NEW_WHISTLER))
            arrayList.add(new ListItemModel("Like Darts But More Canadian",
                    "What's New in Whistler",
                    R.drawable.whats_new_whistler));

        // WHATS_NEW_VANCOUVER
        if (Utils.getBoolean(context, Constants.WHATS_NEW_VANCOUVER))
            arrayList.add(new ListItemModel("Vancouver is upping the steaks",
                    "What's New in Downtown Vancouver",
                    R.drawable.whats_new_vancouver));

        // WHATS_NEW_WHITE ROCK
        if (Utils.getBoolean(context, Constants.WHATS_NEW_WHITE_ROCK))
            arrayList.add(new ListItemModel("The Pond at BFS",
                    "What's New in White Rock",
                    R.drawable.whats_new_white_rock));

        Collections.sort(arrayList, new Comparator<ListItemModel>() {
            @Override
            public int compare(ListItemModel listItemModel, ListItemModel t1) {
                return listItemModel.getItemTitle().compareTo(t1.getItemTitle());
            }
        });

//        CustomAdapter customAdapter = new CustomAdapter(requireContext(), arrayList);
//        b.list.setAdapter(customAdapter);

        SavedItemRecyclerViewAdapter2 adapter = new SavedItemRecyclerViewAdapter2(arrayList, requireContext());
        b.recyclerViewSavedCityGuides.setLayoutManager(new LinearLayoutManager(b.recyclerViewSaved.getContext()));
        b.recyclerViewSavedCityGuides.setAdapter(adapter);

        noSavedCityGuides = arrayList.isEmpty();
        checkSavedItems();
    }

    private void setupRecyclerView() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<Location> locationList = MainActivity.database.locationDAO().getSavedLocations();
            SavedItemRecyclerViewAdapter adapter = new SavedItemRecyclerViewAdapter(locationList, requireContext(), (ViewGroup) getView().getParent());

            ((AppCompatActivity) requireContext()).runOnUiThread(() -> {
                b.recyclerViewSaved.setLayoutManager(new LinearLayoutManager(b.recyclerViewSaved.getContext()));
                b.recyclerViewSaved.setAdapter(adapter);
                noSavedDestinations = locationList.isEmpty();
                checkSavedItems();
            });
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    // Hiding save button
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        Log.d("SAVED_FRAGMENT", "Creating options menu");
        if (menu != null && menu.findItem(R.id.location_save) != null)
            menu.findItem(R.id.location_save).setVisible(false);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
//        Log.d("SAVED_FRAGMENT", "Preparing options menu");
        if (menu != null && menu.findItem(R.id.location_save) != null)
            menu.findItem(R.id.location_save).setVisible(false);
    }

    private void checkSavedItems() {
        if (noSavedCityGuides)
            b.textViewCities.setVisibility(View.GONE);
        else b.textViewCities.setVisibility(View.VISIBLE);

        if (noSavedDestinations)
            b.textViewDestination.setVisibility(View.GONE);
        else b.textViewDestination.setVisibility(View.VISIBLE);

        if (noSavedCityGuides && noSavedDestinations)
            b.textViewNoSavedItem.setVisibility(View.VISIBLE);
        else
            b.textViewNoSavedItem.setVisibility(View.GONE);
    }

}
