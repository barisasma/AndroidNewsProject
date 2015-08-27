package com.android_gazete;

import com.android_gazete.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	
	String buyuk="Büyük";
	String kucuk="Küçük";
	String letter="Harfe Göre";
	String read="Okunma Sayýsý";
	private Button btn_confirm;
	private Button btn_cancel;
	private RadioGroup rg_size;
	private RadioButton rd_size;
	private RadioGroup rg_sorting;
	private RadioButton rd_sorting;
	private Intent intent;
	private DBHandler dbhandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		dbhandler = new DBHandler(this);
		
		intent= new Intent(this, MainActivity.class);
		
		rg_sorting=(RadioGroup)findViewById(R.id.rg_sorting);
		rg_size= (RadioGroup) findViewById(R.id.rg_size);
		btn_confirm= (Button) findViewById(R.id.btn_confirm);
		btn_cancel=(Button) findViewById(R.id.btn_cancel);
		
		//checking radio-groups
		if(dbhandler.getPreference("list_row_id").equals(buyuk))
			rg_size.check(R.id.rd_big);
		else if (dbhandler.getPreference("list_row_id").equals(kucuk))
			rg_size.check(R.id.rd_small);
		
		if(dbhandler.getPreference("sort_type").equals(letter))
			rg_sorting.check(R.id.rd_letter);
		else if(dbhandler.getPreference("sort_type").equals(read))
			rg_sorting.check(R.id.rd_read);
	
		btn_confirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int selectedid = rg_size.getCheckedRadioButtonId();
				int selectedid2 = rg_sorting.getCheckedRadioButtonId();
				rd_size=(RadioButton) findViewById(selectedid);
				rd_sorting=(RadioButton)findViewById(selectedid2);
				
				Toast.makeText(SettingsActivity.this, rd_size.getText()+"-"+rd_sorting.getText(), Toast.LENGTH_SHORT).show();
				dbhandler.updatePreference(1, rd_size.getText().toString(),rd_sorting.getText().toString());
				startActivity(intent);				
			}
		});
		
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intent);
				
			}
		});
		
	}
	
}
