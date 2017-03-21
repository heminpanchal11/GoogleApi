package com.googleapi.qpx;

import java.io.IOException;
import java.util.ArrayList;

import java.io.IOException;
import java.util.*;

import javax.swing.text.View;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.PricingInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

public class QPXQuickStart {

	public static void main(String[] args) {

		final String APPLICATION_NAME = "MyFlightApplication";

		final String API_KEY = "yourapikey";

		/** Global instance of the HTTP transport. */
		HttpTransport httpTransport;

		/** Global instance of the JSON factory. */
		final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			PassengerCounts passengers = new PassengerCounts();
			passengers.setAdultCount(1);

			List slices = new ArrayList<SliceInput>();

			SliceInput slice = new SliceInput();
			slice.setOrigin("LAX");
			slice.setDestination("JFK");
			slice.setDate("2017-04-29");
			slices.add(slice);

			TripOptionsRequest request = new TripOptionsRequest();
			request.setSolutions(10);
			request.setPassengers(passengers);
			request.setSlice(slices);

			TripsSearchRequest parameters = new TripsSearchRequest();
			parameters.setRequest(request);
			QPXExpress qpXExpress = new QPXExpress.Builder(httpTransport, JSON_FACTORY, null)
					.setApplicationName(APPLICATION_NAME)
					.setGoogleClientRequestInitializer(new QPXExpressRequestInitializer(API_KEY)).build();

			TripsSearchResponse list = qpXExpress.trips().search(parameters).execute();
			List<TripOption> tripResults = list.getTrips().getTripOption();

			System.out.println(tripResults); // this line will print a json
												// object which contains
												// information of available
												// flights.

			return;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.exit(1);

	}

}
