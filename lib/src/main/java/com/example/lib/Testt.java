package com.example.lib;


//import com.mashape.unirest.http.Uniest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpResponse;

import javax.net.ssl.HttpsURLConnection;

public class Testt {

    public static void main(String[] args) throws MalformedURLException {



//                    // Construct data
//                    String apiKey = "apikey=" + "NTE0Njc1NzM3MjM5NDYzMDY2NTg2NTY0NzA0ZDUzN2E=";
//                    String message = "&message=" + "This is your test message";
//                    String sender = "&sender=" + "TXTLCL";
//                    String numbers = "&numbers=" + "+918220293996";
//
////                    HttpResponse response = (HttpResponse) Unirest.post("https://www.fast2sms.com/dev/bulkV2")
////                            .header("authorization", "rDCO9PKYpTNqsxgmlQn5eH0GiRuF6tjfZVkh23bvyM1SJ8ILU730k2Xlx8AspwRVUbuCLEaNcheFDJMZ")
////                            .header("Content-Type", "application/x-www-form-urlencoded")
////                            .body("variables_values=5599&route=otp&numbers=9894817942")
////                            .asString();
//
//                    URL githubEndpoint = new URL("https://api.github.com/");
//
//// Create connection
//                    HttpsURLConnection myConnection =
//                            (HttpsURLConnection) githubEndpoint.openConnection();
//
//                    myConnection.setRequestProperty("authorization",
//                            "rDCO9PKYpTNqsxgmlQn5eH0GiRuF6tjfZVkh23bvyM1SJ8ILU730k2Xlx8AspwRVUbuCLEaNcheFDJMZ");
//                    myConnection.setRequestProperty("Content-Type",
//                            "application/x-www-form-urlencoded");
//
//                    myConnection.setRequestProperty("route",
//                            "otp");
//                    myConnection.setRequestProperty("numbers",
//                            "9894817942");
//
//                    InputStream responseBody = myConnection.getInputStream();
//                    InputStreamReader responseBodyReader =
//                            new InputStreamReader(responseBody, "UTF-8");
//
//                    if (myConnection.getResponseCode() == 200) {
//                        System.out.println(" myConnection");
//                    } else {
//                        System.out.println(" myConnectiosadn");
//                    }
//                    System.out.println(             responseBodyReader.toString());
//                } catch (Exception e) {
//                    System.out.println("Error SMS "+e);
//
//                }


        try {

            URL url = new URL("https://www.fast2sms.com/dev/bulkV2");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("authorization",
                    "rDCO9PKYpTNqsxgmlQn5eH0GiRuF6tjfZVkh23bvyM1SJ8ILU730k2Xlx8AspwRVUbuCLEaNcheFDJMZ");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            conn.setRequestProperty("route",
                    "otp");
            conn.setRequestProperty("numbers",
                    "9894817942");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (IOException ioException) {
            ioException.printStackTrace();

        }
    }

    }