package com.cg.mobileassistant2;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.cg.mobileassistant2.checkinendpoint.Checkinendpoint;
import com.cg.mobileassistant2.checkinendpoint.model.CheckIn;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import java.io.IOException;


public class MainActivity extends Activity {

  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    new CheckInTask().execute();

  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  /**
   * AsyncTask for calling Mobile Assistant API for checking into a place (e.g., a store)
   */
  private class CheckInTask extends AsyncTask<Void, Void, Void> {

    /**
     * Calls appropriate CloudEndpoint to indicate that user checked into a place.
     *
     * @param params the place where the user is checking in.
     */
    @Override
    protected Void doInBackground(Void... params) {
      CheckIn checkin = new CheckIn();
      
      // Set the ID of the store where the user is. 
      // This would be replaced by the actual ID in the final version of the code. 
      checkin.setPlaceId("StoreNo123");

      Checkinendpoint.Builder builder = new Checkinendpoint.Builder(
          AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
          null);
          
      builder = CloudEndpointUtils.updateBuilder(builder);

      Checkinendpoint endpoint = builder.build();
      

      try {
        endpoint.insertCheckIn(checkin).execute();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      return null;
    }
  }
}

