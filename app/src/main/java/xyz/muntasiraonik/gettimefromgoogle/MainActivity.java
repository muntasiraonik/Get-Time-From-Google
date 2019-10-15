package xyz.muntasiraonik.gettimefromgoogle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    String dateStr;
    String[] Date;
    String CurrentDate;
    long CurrentTime;
    int Month;
    String CurrentTime2;
    String DateAndTime;
    String Datw;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this ;

        if(isNetworkAvailable()){new GoogleTime().execute();}else
        {
            Toast.makeText(context, "Sorry no Internet", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class GoogleTime extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet("https://google.com/"));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){

                    dateStr = response.getFirstHeader("Date").getValue();

                    Date  = dateStr.split(" ");
                    int counter = 0 ;
                    for(String a : Date){
                        counter = counter + 1;

                        Log.e("Time - " + counter  , a.trim() );
                        Month = getMonthInIntFormat(Date[2]);

                    }


                    CurrentDate = Date[1]+"/"+Month+"/" + Date[3];
                    //  String dateFormat = Date[0]
                    String[] Timee = Date[4].split(":");
                    int hour = Integer.parseInt(Timee[0]);

                    Log.e("Timeee", hour + " -- " + Timee[1] + " --" + Timee[2]);
                    int seconds = Integer.parseInt(Timee[2]);
                    int minutes = Integer.parseInt(Timee[1]);
                    CurrentTime = seconds + minutes + hour;


                    CurrentTime2 = Date[3]+"-" + Month +"-" + Date[1] +" " + hour+":" +Timee[1]+":"+Timee[2];


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    java.util.Date myDate = simpleDateFormat.parse(CurrentTime2);





                    Date date1 = null;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        date1 =myDate;
                    }catch (Exception e){

                    }
                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
                    //        System.out.println(sdf.format(date));
                    Log.e("@@@Date: ",String.valueOf(sdf.format(date1)));
                    DateAndTime = String.valueOf(sdf.format(date1));

                    Date date = sdf.parse(DateAndTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int Da = calendar.get(Calendar.DATE);
                    int Mm = calendar.get(Calendar.MONTH);
                    int yy = calendar.get(Calendar.YEAR);


                    Datw = Da +""+Mm+""+yy;









                    // Here I do something with the Date String

                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            }catch (ClientProtocolException e) {
                Log.d("Response", e.getMessage());
            }catch (IOException e) {
                Log.d("Response", e.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this, "Time " + CurrentDate+ "Time :" + DateAndTime +"?"+ Datw    , Toast.LENGTH_SHORT).show();
            Log.e("Date", CurrentDate);
            Log.e("DateTime",CurrentTime+"");


        }


    }
    private int getMonthInIntFormat(String s) {
        int num = 0;
        switch (s){

            case "Jan":
                num = 1 ;
                break;
            case "Feb":
                num = 2;
                break;
            case "Mar":
                num = 3;
                break;
            case "Apr":
                num = 4;
                break;
            case "May":
                num = 5;
                break;
            case "Jun":
                num = 6;
                break;
            case "Jul":
                num = 7;
                break;
            case "Aug":
                num = 8;
                break;
            case "Sep":
                num = 9;
                break;
            case "Oct":
                num = 10;
                break;
            case "Nov":
                num = 11;
                break;
            case "Dec":
                num = 12;
                break;

        }
        return num;
    }



    public  void getDate()
    {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd/M/yyyy"); // /*HH:mm:ss*/
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT+6"));
        System.out.println(dateFormatGmt.format(new Date())+"");
        Log.e("Date",dateFormatGmt.format(new Date())+"" );
        Toast.makeText(MainActivity.this, "Time " + dateFormatGmt.format(new Date()), Toast.LENGTH_SHORT).show();



    }
    public  void getTimeinMilliSeconds(){
        long time= System.currentTimeMillis();
        Log.e("Time", " Time value in millisecinds "+time);
    }

}
