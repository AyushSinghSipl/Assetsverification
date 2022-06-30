package com.mahyco.assetsverification.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.mahyco.assetsverification.HomeActivity;
import com.mahyco.assetsverification.MainActivity;
import com.mahyco.assetsverification.R;
import com.mahyco.assetsverification.login.LoginActivity;


public class Messageclass {


    private Context context;
    private Boolean fg = false;

    public Messageclass(Context context) {
        this.context = context;
    }


    public void showMessage(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle("Asset Verification");
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.applauncher);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                //        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    public void showAutomaticTimeMessage(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle("Crop Monitor Report");
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.tick);
        // Setting OK Button
        alertDialog.setButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                //        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));

            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    public void showMessageandRedirectToHome(String message, final boolean redirectToHome, final Context context) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle("Asset Verification");
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.tick);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (redirectToHome) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);

                    alertDialog.dismiss();
                }

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void showMessageandRedirectToLogin(String message, final boolean redirectToHome, final Context context) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle("Asset Verification");
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.tick);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (redirectToHome) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);


                    alertDialog.dismiss();
                }

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void showMessageandRedirectToPrevious(String message, final boolean redirectToHome, final Context context) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle("Trial Data Management");
        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.tick);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (redirectToHome) {
                    ((Activity) context).finish();


                }
                alertDialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean showMessageConfirm(String message) {

        // fg=false;
        // AlertDialog alertDialog = new AlertDialog.Builder(VisitorInformation.this).create();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle("Trial Data Management");
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.tick);
        // Setting OK Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do do my action here
                fg = true;
                dialog.dismiss();

            }

        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I do not need any action here you might
                fg = false;
                dialog.dismiss();

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
        return fg;
    }

    /*public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(this.context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            }
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }*/
}
