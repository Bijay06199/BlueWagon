package com.example.bluewagon.ecalculo.utilities;

import com.android.volley.Response;

import org.json.JSONObject;

public class APIRequest extends com.android.volley.toolbox.JsonObjectRequest {

    private String urlTag = "url:: ";

    public APIRequest(int method, String url,
                      Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, null, listener, errorListener);
        System.out.println(urlTag + url);
    }

    public APIRequest(int method, String url, JSONObject jsonRequest,
                      Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        System.out.println(urlTag + url);
        System.out.println("jsonRequest:: " + jsonRequest);
    }

//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        headers.put("Authorization", "Basic YWRtaW46YWNjb3VudGluZ0AxMjM=");
//        System.out.println("headers:: " + headers);
//        return headers;
//    }
}