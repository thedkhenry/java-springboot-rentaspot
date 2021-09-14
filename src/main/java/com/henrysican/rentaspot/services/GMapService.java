package com.henrysican.rentaspot.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.henrysican.rentaspot.dao.BookingRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GMapService {
    private final GeoApiContext context;

//TODO: Separate and protect API key
    public GMapService(){
        this.context = new GeoApiContext.Builder().apiKey("AIzaSyAyCd8To3zMx7ydSQc8vsZe2cAr7V3fnFo").build();
    }

    /**
     * Returns a LatLng object that can then be used to for markers on a Google Map.
     * The address argument should be a full address otherwise coordinates will be inaccurate.
     * @param address   a full address to be geocoded
     * @return          the lat/lng coordinates of the address
     * @throws IOException
     * @throws InterruptedException
     * @throws ApiException
     */
    public LatLng getLatLng(String address) throws IOException, InterruptedException, ApiException {
        GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0].geometry));
        return results[0].geometry.location;
    }
}