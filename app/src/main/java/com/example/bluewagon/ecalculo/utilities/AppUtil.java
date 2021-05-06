package com.example.bluewagon.ecalculo.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.bluewagon.R;

import java.text.DecimalFormat;


public class AppUtil {
    public static void showInternetDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("No Internet")
                .setMessage("Check your internet connection and try again")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    public static boolean isMobileNumberValid(String mobileNumber) {
        return mobileNumber != null && !mobileNumber.isEmpty() && mobileNumber.matches("9[0-9]{9}");
    }

    public static String formattedAmounts(double amount) {
        if (amount < 0) {
            amount = amount * -1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
        String formattedTXnAmount = decimalFormat.format(amount);
        return formattedTXnAmount;
    }


    public static void showInfoDialog(Context context, String bodyMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(AppTexts.info);
        builder.setMessage(bodyMessage);
        builder.setPositiveButton(AppTexts.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.length() <= 0;
    }

    public static void showSuccessBox(Context context, String bodyMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(AppTexts.successMsg);
        builder.setMessage(bodyMessage);
        builder.setPositiveButton(AppTexts.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void somethingWrongDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error!");
        builder.setMessage(AppTexts.SERVER_ISSUE);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void showErrorTitleBox(Context context, String bodyMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(AppTexts.error);
        builder.setMessage(bodyMessage);
        builder.setPositiveButton(AppTexts.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static AlertDialog.Builder showAlertBox(Context context, String titleText, String bodyMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleText);
        builder.setMessage(bodyMessage);
        builder.setPositiveButton(AppTexts.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder;
    }

    public static void infoDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static AlertDialog progressDialog(Context context, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.layout_progress_dialog, null);
        ((TextView) view.findViewById(R.id.tVProgress)).setText(msg);
        builder.setView(view);
        return builder.create();
    }


        public static boolean isConnectionAvailable(Context context) {
        boolean isWifiConnected = false;
        boolean isCellularConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        isWifiConnected = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        isCellularConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isWifiConnected || isCellularConnected;
    }
//
//
//    public static void setProgressText(AlertDialog alertDialog, String msg) {
//        ((TextView) alertDialog.findViewById(R.id.tVProgress)).setText(msg);
//    }
//
//    public static SharedPreferences getPreferences(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context);
//    }
//
//    public static String getIP(Context context) {
//        SharedPreferences preferences = getPreferences(context);
//        return preferences.getString(AppTexts.newIp, "");
//    }
//
    public static void showErrorDialog(Context context, VolleyError error) {
        new AlertDialog.Builder(context)
                .setTitle(AppTexts.error)
                .setMessage(errorListener(error))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public static String errorListener(VolleyError error) {
        String errorText;
        if (error instanceof TimeoutError) {
            errorText = AppTexts.CONNECTION_TIMEOUT_ERROR_MESSAGE;
        } else if (error instanceof NoConnectionError) {
            errorText = AppTexts.NO_INTERNET_MESSAGE_MESSAGE;
        } else if (error instanceof AuthFailureError) {
            errorText = AppTexts.AUTHENTICATION_ERROR_MESSAGE;
        } else if (error instanceof ServerError) {
            errorText = AppTexts.SOMETHING_WRONG;
        } else if (error instanceof NetworkError) {
            errorText = AppTexts.NETWORK_ERROR_MESSAGE;
        } else if (error instanceof ParseError) {
            errorText = AppTexts.PARSE_ERROR_MESSAGE;
        } else {
            errorText = AppTexts.SOMETHING_WRONG;
        }
        return errorText;
    }
    //
    public static void customizeRequest(Request req) {
        req.setRetryPolicy(new DefaultRetryPolicy(40000, 0
                , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
    }
//
//    public static DefaultRetryPolicy retryPolicy() {
//        return new DefaultRetryPolicy(
//                30 * 1000,
//                -1,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//    }
//
//    public static void JSONExceptionDialog(Context context) {
//        new AlertDialog.Builder(context)
//                .setTitle("System Error!")
//                .setMessage("Unfortunately something went wrong! Please try again later.")
//                .setPositiveButton("close", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//    }
//
//    public static boolean isEmpty(String value) {
//        return value == null || value.length() <= 0;
//    }
//
//    public static String encryption(String signature) {
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        md.update(signature.getBytes());
//
//        byte byteData[] = md.digest();
//        StringBuilder hexString = new StringBuilder();
//        for (byte aByteData : byteData) {
//            String hex = Integer.toHexString(0xff & aByteData);
//            if (hex.length() == 1) hexString.append('0');
//            hexString.append(hex);
//        }
//        System.out.println("Hex format : " + hexString.toString());
//        return hexString.toString();
//    }
//
//    public static String encodeAuth(String string) {
//        return Base64.encodeToString(string.getBytes(), Base64.DEFAULT);
//    }
//
//    public static String decodeAuth(String string) {
//        byte[] data = Base64.decode(string, Base64.DEFAULT);
//        return new String(data);
//    }
//
//    public static void contactDialog(final Context context) {
//        final AlertDialog progressDialog = progressDialog(context, AppTexts.pleaseWait);
//        progressDialog.show();
//        APIRequest contactRequest = new APIRequest(
//                context, Request.Method.GET,
//                AppUtil.getIP(context) + APIs.contacts,
//                response -> {
//                    System.out.println(AppTexts.response + response);
//                    ContactsDTO dto = new Gson().fromJson(response.toString(), ContactsDTO.class);
//                    if (dto.getStatus() == AppTexts.successCode) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                        LayoutInflater inflater = LayoutInflater.from(context);
//                        @SuppressLint("InflateParams")
//                        View dialogView = inflater.inflate(R.layout.contact_dialog_layout, null);
//                        RecyclerView rVContacts = dialogView.findViewById(R.id.rVContacts);
//                        rVContacts.setLayoutManager(new LinearLayoutManager(context));
//                        rVContacts.setAdapter(new ContactsAdapter(context, dto.getDetail()));
//                        builder.setView(dialogView);
//                        builder.setPositiveButton("Close", (dialogInterface, i) -> dialogInterface.dismiss());
//                        builder.show();
//                        progressDialog.dismiss();
//                    } else {
//                        progressDialog.dismiss();
//                        Toast.makeText(context, "Error Fetching Contacts!", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                error -> {
//                    progressDialog.dismiss();
//                    Toast.makeText(context, "Error Fetching Contacts!", Toast.LENGTH_SHORT).show();
//                }
//        );
//        customizeRequest(contactRequest);
//        Volley.newRequestQueue(context).add(contactRequest);
////        rVContacts.setAdapter(new ContactsAdapter(context, ));
//
////        RelativeLayout rLContact1 = dialogView.findViewById(R.id.rLContact1);
////        RelativeLayout rLContact2 = dialogView.findViewById(R.id.rLContact2);
////        RelativeLayout rLContact3 = dialogView.findViewById(R.id.rLContact3);
////        RelativeLayout rLContactEmail = dialogView.findViewById(R.id.rLContactEmail);
////
////        rLContact1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
////                        "tel", context.getString(R.string.contactNumber1), null));
////                context.startActivity(phoneIntent);
////            }
////        });
////
////        rLContact2.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
////                        "tel", context.getString(R.string.contactNumber2), null));
////                context.startActivity(phoneIntent);
////            }
////        });
////
////        rLContact3.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
////                        "tel", context.getString(R.string.contactNumber3), null));
////                context.startActivity(phoneIntent);
////            }
////        });
//    }
//
//    public static void hideKeyboard(Context context, View view) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }
//
//    public static String getDeviceName() {
//        String manufacturer = Build.MANUFACTURER;
//        String model = Build.MODEL;
//        if (model.startsWith(manufacturer)) {
//            return capitalize(model);
//        } else {
//            return capitalize(manufacturer) + " " + model;
//        }
//    }
//
//    private static String capitalize(String s) {
//        if (s == null || s.length() == 0) {
//            return "";
//        }
//        char first = s.charAt(0);
//        if (Character.isUpperCase(first)) {
//            return s;
//        } else {
//            return Character.toUpperCase(first) + s.substring(1);
//        }
//    }
//
//
}
