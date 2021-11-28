package com.example.bcexplorer.database;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDAO {
    // Insert
    @Insert(onConflict = ABORT)
    void insertLocation(Location location);
    @Insert(onConflict = ABORT)
    void insertLocations(Location... locations);
    @Insert(onConflict = ABORT)
    void insertLocationList(List<Location> locationList);

    // Delete
    @Query("DELETE FROM locations")
    void deleteAllLocations();
    @Query("DELETE FROM locations WHERE locationID = :locationID")
    void deleteLocation(String locationID);

    // Retrieve
    @Query("SELECT * FROM locations")
    List<Location> getAllLocations();
    @Query("SELECT * FROM locations WHERE locationID = :locationID")
    Location getLocationWithID(String locationID);
    @Query("SELECT * FROM locations WHERE locationName = :locationName")
    Location getLocationWithName(String locationName);
    @Query("SELECT DISTINCT category FROM locations")
    List<String> getAllCategories();
    @Query("SELECT * FROM locations WHERE category = :category")
    List<Location> getLocationsMatchingCategory(String category);

}