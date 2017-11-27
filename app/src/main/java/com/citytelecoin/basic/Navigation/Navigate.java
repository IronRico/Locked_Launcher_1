package com.citytelecoin.basic.Navigation;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.citytelecoin.basic.Apps.Apps;
import com.citytelecoin.basic.InmateService.InmateService;
import com.citytelecoin.basic.Login.Login;
import com.citytelecoin.basic.R;

public class Navigate extends AppCompatActivity {

    Button appButton,serviceButton, logoutButton;

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigate);

		//Dismisses the keyguard (lock screen)
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        //Disables status bar drop down
        WindowManager manager = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |

                // this is to enable the notification to receive touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (30 * getResources().getDisplayMetrics().scaledDensity);
        localLayoutParams.format = PixelFormat.TRANSPARENT;

        customViewGroup view = new customViewGroup(this);

		assert manager != null;
		manager.addView(view, localLayoutParams);


        //Here the Toolbar used to exit the app is provided
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Here we are telling the activity to find the button used to access the apps listing
        appButton = findViewById(R.id.appButton);

        //Here we are telling the activity to find the button used to access the inmate kiosk services
        serviceButton = findViewById(R.id.serviceButton);

        //Logout Button
		logoutButton = findViewById(R.id.logoutButton);

        //Here the button is given the ability to take user clicks and thus take them to the new activity with the apps list
        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Navigate.this, Apps.class);
                startActivity(myIntent);
			}
        });

        //Inmate Services button (Webview App)
		serviceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(Navigate.this, InmateService.class);
				startActivity(myIntent);
			}
		});

        //Logout Button
		logoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(Navigate.this, Login.class);
				startActivity(myIntent);
			}
		});

    }

    //This Code works with the Window Manager to help block the user from accessing the Status Bar at the top of the screen
    public class customViewGroup extends ViewGroup {

        public customViewGroup(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            Log.v("customViewGroup", "**********Intercepted");
            return true;
        }
    }

    //This closes the system dialogs that may appear allowing app exit, (long power button press)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }


    //This ensures that the back button does not exit the app
    @Override
    public void onBackPressed() {
        // nothing to do here
        // … really
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder StatusDialogBuilder = new AlertDialog.Builder(context);

            // set title
            StatusDialogBuilder.setTitle("Enter Admin Password");
            // Setting an EditText view to get user input
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input.setTransformationMethod(new PasswordTransformationMethod());
            StatusDialogBuilder.setView(input);

            //set dialog message
            //Note the hard coded password of "1234" in the if input.getText portion below.
            //This should be changed to something more secure on a backend reception.
            //While unlikely that an inmate would get access to final source code, it would just be better security practice.

            StatusDialogBuilder.setMessage("Password Required").setCancelable(false).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    if (input.getText().toString().equals("1234")) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                    }

                }

            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
                }
            });
            // create alert dialog
            AlertDialog alertDialog = StatusDialogBuilder.create();
            // show it
            alertDialog.show();


            return super.onOptionsItemSelected(item);
        }
        return false;
    }


}