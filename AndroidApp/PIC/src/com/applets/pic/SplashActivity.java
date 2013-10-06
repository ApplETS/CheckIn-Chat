package com.applets.pic;
import com.applets.pic.http.InfosServerProvider;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SplashActivity extends Activity {

	private Button connectButton;
	private EditText nameEdit;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		nameEdit = (EditText)findViewById(R.id.editName);

		connectButton = (Button)findViewById(R.id.buttonConnect);
		connectButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(nameEdit.getText().toString()!= null && !nameEdit.getText().toString().trim().isEmpty()){
					String[] availableChannels = InfosServerProvider.getClosestServerInfos();
					if(availableChannels != null && availableChannels.length > 0) {
						Intent intent = new Intent(SplashActivity.this, ChatActivity.class);
						Bundle extras = new Bundle();
						extras.putStringArray("CHANNELS", availableChannels);
						extras.putString("DISPLAY_NAME", nameEdit.getText().toString());
						intent.putExtras(extras);
						startActivity(intent);
					}
					else
					{
						showServerUnknownError();
					}
				}
				else{
					AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
					alertDialog.setTitle(getResources().getString(R.string.error_no_display_name_title));
					alertDialog.setMessage(getResources().getString(R.string.error_no_display_name));
					alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							SplashActivity.this.nameEdit.requestFocus();
						}
					});
					alertDialog.show();
					
				}
			}
		});
	}
	
	private void showServerUnknownError() {
		// Unknown exception
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getResources().getString(R.string.error_connection_infos_server_title));
		alertDialog.setMessage(getResources().getString(R.string.error_connection_infos_server_unknown));
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
