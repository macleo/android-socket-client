package com.boyaa.chat.demo;

import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.boyaa.chat.R;


public class GetServerIPActivity extends Activity implements OnClickListener {

	private static final String TAG = "GetServerIPActivity";
	AlertDialog ad;
	private String DeviceId;
	private EditText et_getip,et_port;
	private Button btn_submit,btn_cancel;
	private final long SHOWTIME = 2000; // LOGO页锟斤拷示时锟斤拷,2s
	private AlertDialog dialog;
	private ACache mACache = null;
	private Context mContext = null;
	private Map<String,String> flag = null;



	@Override
	protected void onCreate(Bundle bd){
		super.onCreate(bd);
		setContentView(R.layout.activity_getip);

		et_port  = (EditText)findViewById(R.id.et_port);
		et_getip = (EditText)findViewById(R.id.et_getip);
		btn_submit = (Button)findViewById(R.id.btn_submit);
		btn_cancel = (Button)findViewById(R.id.btn_cancel);



		init();
	}

	private void init(){
		mACache = ACache.get(this);
		mContext = this;
		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dialog!=null)
			dialog.dismiss();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.btn_submit:
				String host = et_getip.getText().toString().trim();
				Integer port = Integer.parseInt(et_port.getText().toString());
				if(!host.equals("")&&!et_port.getText().toString().equals("")){
					//Utils.setServerIp(this,ip);
					mACache.put("host",host);
					mACache.put("port",port);
					Intent mIntent  = new Intent();
					mIntent.putExtra("host", host);
					mIntent.putExtra("port", port);
					this.setResult(Activity.RESULT_OK,mIntent);
					finish();
					//startActivity(new Intent(this,SplashActivity.class));

					//new IsRegAsyncTask().execute(ip);
				}
				else if(et_getip.getText().toString().equals("")){
					et_getip.requestFocus();
					et_getip.setError(getString(R.string.input_serverip));
				}
				else{
					et_port.requestFocus();
					et_port.setError("请输入端口号");
				}
				break;
			case R.id.btn_cancel:
				finish();
				//startActivity(new Intent(this,SplashActivity.class));
				break;
		}
	}





}
