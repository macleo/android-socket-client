package com.boyaa.chat.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


import com.boyaa.chat.R;
import com.boyaa.push.lib.service.Client;
import com.boyaa.push.lib.service.ISocketResponse;
import com.boyaa.push.lib.service.Packet;

public class MainActivity extends AppCompatActivity {

	private Client user=null;
	private EditText ip,port,sendContent,recContent;
	private ACache mACache = null;
	private String server_ip = null;
	private Integer server_port = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		user=new Client(this.getApplicationContext(),socketListener);
	}
	 
	
	private void initView()
	{
		findViewById(R.id.open).setOnClickListener(listener);
		findViewById(R.id.close).setOnClickListener(listener);
		findViewById(R.id.reconn).setOnClickListener(listener);
		findViewById(R.id.send).setOnClickListener(listener);
		findViewById(R.id.clear).setOnClickListener(listener);
		
		ip=(EditText) findViewById(R.id.ip);
		port=(EditText) findViewById(R.id.port);
		sendContent=(EditText) findViewById(R.id.sendContent);
		recContent=(EditText) findViewById(R.id.recContent);
		mACache = ACache.get(this);
		server_ip = (String)mACache.getAsObject( "host");
		server_port = (Integer)mACache.getAsObject( "port" );

		if(server_ip == null ||server_port==null){
			Intent mIntent = new Intent(this,GetServerIPActivity.class);
			startActivityForResult(mIntent, 1000);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1000&&null!=data){
			server_ip = data.getExtras().getString("host");
			server_port = data.getExtras().getInt("port",0);
			//startnet();
			ip.setText(server_ip);
			port.setText(server_port+"");
		}else{
			Toast.makeText(this,getString(R.string.text_ipip_port_not_set), Toast.LENGTH_LONG).show();
			finish();
		}

	}

	private ISocketResponse socketListener=new ISocketResponse() {
		
		@Override
		public void onSocketResponse(final String txt) {
			runOnUiThread(new Runnable() {
				public void run() {
					recContent.getText().append(txt).append("\r\n");
				}
			});
		}
	};
	
	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
				case R.id.open:
//					user.open();
					user.open(ip.getText().toString(), Integer.valueOf(port.getText().toString()));
					break;
					
				case R.id.close:
					user.close();
					break;
					
				case R.id.reconn:
					user.reconn();
					break;
					
				case R.id.send:
					Packet packet=new Packet();
					packet.pack(sendContent.getText().toString());
					user.send(packet);
					sendContent.setText("");
					break;
					
				case R.id.clear:
					recContent.setText("");
					break;
			}
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			android.os.Process.killProcess(android.os.Process.myPid());
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
