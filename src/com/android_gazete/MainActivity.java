package com.android_gazete;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.android_gazete.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

//@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends Activity{
	private ArrayList<Gazete> gazeteler=new ArrayList<Gazete>();
	ListView lv;
	private GazeteArrayAdapter adapter;
	private final Context context=this;
	private Intent intent_settings;
	private Intent intent_getlist_row=null; 
	private Gazete bosgazete;
	String something;
	String buyuk="Büyük";
	String kucuk="Küçük";
	DBHandler dbhandler;
	String sorting_method_letter="Harfe Göre";
	String sorting_method_hit="Okunma Sayısı";
	SearchView searchView;
	//ca-app-pub-4770973048704418/2933556086
	private AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Intent intent=new Intent(this,WebActivity.class);
		intent_settings= new Intent(this,SettingsActivity.class);
		dbhandler=new DBHandler(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Toast.makeText(MainActivity.this, "" +"selam", Toast.LENGTH_SHORT).show();
		
		ActionBar actionBar = getActionBar();
		//actionBar.setIcon(R.drawable.gazete_launcher);//setting actionbar
		
		 // Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
		
		
		if(dbhandler.getGazeteCount()<1){
			Log.d("Insert: ", "Inserting ..");
			
								
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.hurriyet72_72), "Hürriyet", "http://www.hurriyet.com.tr/anasayfa/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.milliyet72_72), "Milliyet", "http://www.milliyet.com.tr/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.cnn72_72), "CNNTurk", "http://m.cnnturk.com/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.vatan72_72), "Vatan", "http://www.gazetevatan.com/root.asp/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.radikal72_72), "Radikal", "http://www.radikal.com.tr/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.bbc72_72), "BBC Türkçe", "http://m.bbc.co.uk/turkce");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.haberturk72_72), "Habertürk", "http://m.haberturk.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.posta72_72), "Posta", "http://m.posta.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.sabah72_72), "Sabah", "http://m.sabah.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.ntvspor72_72), "Ntvspor", "http://m.ntvspor.net");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.ajansspor72_72), "Ajansspor", "http://www.ajansspor.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.aksam72_72), "Akşam", "http://www.aksam.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.amk72_72), "Amk", "http://amkspor.com/m/mobile/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.anadoluhaber72_72), "Anadolu Haber Ajansı", "http://www.aa.com.tr/tr/mod/tag/mobil");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.beyazgazete72_72), "Beyaz Gazete", "http://mobile.beyazgazete.com/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.birgun72_72), "Birgün", "http://www.birgun.net");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.bloomberht72_72), "BloombergHT", "http://www.bloomberght.com");			
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.bugun72_72), "Bugün", "http://www.bugun.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.cihanhaber72_72), "Cihan Haber Ajansı", "http://www.cihan.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.cumhuriyet72_72), "Cumhuriyet", "http:/www.cumhuriyet.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.doganhaber72_72), "Doğan Haber Ajansı", "http://www.dha.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.efsanefotospor72_72), "Fotospor", "http://www.fotospor.com");			
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.ekonomist72_72), "Ekonomist", "http://www.ekonomist.com.tr/AnaSayfa/Default.aspx");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.ensonhaber72_72), "Ensonhaber", "http://m.ensonhaber.com/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.evrensel72_72), "Evrensel", "http://www.evrensel.net");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.fanatik72_72), "Fanatik", "http://m.fanatik.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.fotomac72_72), "Fotomaç", "http://m.fotomac.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.gunes72_72), "Güneş", "http://www.gunes.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.habersol72_72), "HaberSol", "http://haber.sol.org.tr/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.haber372_72), "Haber3", "http://m.haber3.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.haber772_72), "Haber7", "http://m.haber7.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.haberler72_72), "Haberler", "http://m.haberler.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.habermrt72_72), "HabeMRT", "http://www.habermrt.com/m/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.ihlashaber72_72), "Ihlas Haber Ajansı", "http://www.iha.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.internethaber72_72), "Internethaber", "http://m.internethaber.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.kurultay72_72), "Kurultay", "http://www.kurultay.net");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.macadogru72_72), "Maçadoğru", "http://www.macadogru.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.maraton72_72), "Maraton", "http://www.maraton.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.milligazete72_72), "Milli Gazete", "http://m.milligazete.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.mynet72_72), "Mynet Haber", "http://m.mynet.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.odatv72_72), "Oda Tv", "http://www.odatv.com/m.php");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.ortadogu72_72), "Ortadoğu Gazetesi", "http://www.ortadogugazetesi.net");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.piramithaber72_72), "Piramit Haber", "http://www.piramithaber.com/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.samanyoluhaber72_72), "Samanyolu Haber", "http://m2.samanyoluhaber.com/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.skorer72_72), "Skorer", "http://m.milliyet.com.tr/skorer/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.sozcu72_72), "Sözcü", "http://sozcu.com.tr/m/mobil/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.sporx72_72), "Sporx", "http://m.sporx.com/smart");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.star72_72), "Star", "http://www.stargazete.com/mobil/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.t2472_72), "T24", "http://m.t24.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.sok72_72), "Sok", "http://www.gazetesok.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.takvim72_72), "Takvim", "http://m.takvim.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.taraf72_72), "Taraf", "http://www.taraf.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.teknokulis72_72), "Teknokulis", "http://m.teknokulis.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.tgrthaber72_72), "TGRT Haber", "http://www.tgrthaber.com.tr/mobile");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.trtspor72_72), "TRT Spor", "http://www.trtspor.com.tr/m/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.turkiyegazetesi72_72), "Türkiye Gazetesi", "http://m.turkiyegazetesi.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.webrazzi72_72), "Webrazzi", "http://www.webrazzi.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.yeniakit72_72), "YeniAkit", "http://mm.yeniakit.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.yazete72_72), "Yazete", "http://m.yazete.com/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.yeniasya72_72), "YeniAsya", "http://www.yeniasya.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.yenicag72_72), "Yeniçağ", "http://www.yenicaggazetesi.com.tr/mobil/");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.yenisafak72_72), "Yenişafak", "http://m.yenisafak.com");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.yurt72_72), "Yurt Gazetesi", "www.yurtgazetesi.com.tr");
			insertJournalRows(BitmapFactory.decodeResource(getResources(),R.drawable.zaman72_72), "Zaman", "http://www.zaman.com.tr/mobil");
			
			
			
		}
		
		if(dbhandler.getPreferenceCount()<1){
			dbhandler.addNewPreference("Büyük","Harfe Göre");	
		}
		
		
		List<Gazete> gazetes = null;
		if(dbhandler.getPreference("sort_type").equals(sorting_method_letter))
			gazetes = dbhandler.getAllGazete("name",true);
		else if (dbhandler.getPreference("sort_type").equals(sorting_method_hit))
			gazetes=dbhandler.getAllGazete("rank",false);
		
		//gazeteler.clear();
		// Reading all journals from database
		for (int i = 0; i < gazetes.size(); i++) {
			Gazete gt=gazetes.get(i);
			String log = "ID:" + gt.get_id() + " Name: " + gt.getName()
					+ " ,Image: " + gt.getImage()+ " Rank: "+gt.getRank();
					Log.d("Result: ", log);
			gazeteler.add(gt);
		}

	
		something = dbhandler.getPreference("list_row_id");

	
		
		if(buyuk.equals(something))
			adapter = new GazeteArrayAdapter(this, gazeteler,R.layout.list_row);
		else if(kucuk.equals(something))
			adapter = new GazeteArrayAdapter(this, gazeteler,R.layout.list_row_small);
		
		
		lv  = (ListView) findViewById(R.id.list_view);
		
	
		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);
		//enables text filtering
		lv.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
		adapter.notifyDataSetChanged();
		registerForContextMenu(lv);
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		          
				Toast.makeText(MainActivity.this, "" + gazeteler.get(position).name, Toast.LENGTH_SHORT).show();
				bosgazete=gazeteler.get(position);
				int counter = gazeteler.get(position).getRank();
				counter++;
				bosgazete.setRank(counter);
				dbhandler.updateGazete(bosgazete);
				intent.putExtra("webadress", gazeteler.get(position).url);
				startActivity(intent);	
			}
			
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, v.getId(), 0, "Sil");//groupId, itemId, order, title 
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		final int position = info.position;
		if(item.getTitle()=="Sil"){  
			  
			   DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() 
			   { 
				   public void onClick(DialogInterface dialog, int which) { 
				   switch (which) { 
					   case DialogInterface.BUTTON_POSITIVE:   // Yes button clicked 
						 //update code
						   Gazete gt = gazeteler.get(position);
						   Log.d("Result: ", String.valueOf(gt._id));
						   dbhandler.deleteGazete(gt);
						   gazeteler=sortJournals();
						   
							something = dbhandler.getPreference("list_row_id");

							if(buyuk.equals(something))
								adapter = new GazeteArrayAdapter(MainActivity.this, gazeteler,R.layout.list_row);
							else if(kucuk.equals(something))
								adapter = new GazeteArrayAdapter(MainActivity.this, gazeteler,R.layout.list_row_small);
						   
						   //adapter=new GazeteArrayAdapter(context, gazeteler, layoutresourceid)
						   lv.setAdapter(adapter);   
					   break; 
					   case DialogInterface.BUTTON_NEGATIVE: //No button clicked 
						   // do nothing
						   
						    break; 
						    } 
					   } 
			   }; 
			   AlertDialog.Builder builder  = new AlertDialog.Builder(this); 
			   builder.setMessage("Silmek istediğinizden emin misiniz?") 
			   .setPositiveButton("Evet", dialogClickListener) 
			   .setNegativeButton("Hayır", dialogClickListener).show(); 
				   
		}    
         else return false;
		return true; 
	}
	
	//android phone back button
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK) moveTaskToBack(true);
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
    public void onBackPressed() {
        // It's expensive, if running turn it off.
       // DataHelper.cancelSearch();
        hideKeyboard();
        //super.onBackPressed();
    }
	
	//action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is presentsed.
		getMenuInflater().inflate(R.menu.main, menu);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            //searchView.setIconifiedByDefault(false);  
            SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextChange(String newText)
                {
                    // this is your adapter that will be filtered
                    MainActivity.this.adapter.getFilter().filter(newText);
                    System.out.println("on text chnge text: "+newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query)
                {
                    // this is your adapter that will be filtered
                	MainActivity.this.adapter.getFilter().filter(query);    
                    System.out.println("on query submit: "+query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(textChangeListener);
            
            

            return true;//super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//switch (item.getItemId()) {
		if(item.getItemId()==R.id.action_add) {
			// get prompts.xml view
			LayoutInflater layoutInflater = LayoutInflater.from(context);	
			View promptView = layoutInflater.inflate(R.layout.prompts, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			
			// set prompts.xml to be the layout file of the alertdialog builder
			alertDialogBuilder.setView(promptView);
			
			final EditText gisim=(EditText) promptView.findViewById(R.id.et_gisim);
			final EditText gadress=(EditText) promptView.findViewById(R.id.et_adress);
			
	          // setup a dialog window
			
			                alertDialogBuilder
			
			                        .setCancelable(false)
			
			                        .setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
			
			                                    public void onClick(DialogInterface dialog, int id) {			
			                                        // do it what you want to do
			                                    	Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.newspaper_72_72);
			                                		// convert bitmap to byte
			                                		ByteArrayOutputStream stream = new ByteArrayOutputStream();
			                                		image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			                                		byte imageInByte[] = stream.toByteArray();

			                                		dbhandler.addJournal(new Gazete(gisim.getText().toString(),"http://"+gadress.getText().toString(),imageInByte,0));			                                	
			                                		finish();
			                                		Intent intent = new Intent(context,MainActivity.class);
			                                		context.startActivity(intent);
			                                		
			                                    }
			                                })
			
			                        .setNegativeButton("İptal",
			
			                                new DialogInterface.OnClickListener() {
			
			                                    public void onClick(DialogInterface dialog, int id) {
			
			                                        dialog.cancel();
			
			                                    }
			
			                                })
			                             .setOnKeyListener(new DialogInterface.OnKeyListener() {//handles android device back button when alertdialog builder opened
											
											@Override
											public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
												// TODO Auto-generated method stub
												 if(keyCode == KeyEvent.KEYCODE_BACK){
													 	dialog.cancel();
						                                return true;
						                            }
												return false;
											}
										});
			
			 
			
			                // create an alert dialog
			
			                AlertDialog alertD = alertDialogBuilder.create();
						
			                alertD.show();
		}

		else if(item.getItemId()==R.id.action_settings)	
			startActivity(intent_settings);
		
	
	
	return true;
	}
	
	
	
	
	//inserting default journals
	private void insertJournalRows(Bitmap image,String name, String url)
	{
		// convert bitmap to byte
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte imageInByte1[] = stream.toByteArray();
		dbhandler.addJournal(new Gazete(name,url,imageInByte1,0));//name,url,image,rank
	}
	
	 private void hideKeyboard() {
	        InputMethodManager imm = (InputMethodManager) 
	            getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
	 }
	 
	 private ArrayList<Gazete> sortJournals(){
		 //this method sorts journals accordingly
		 List<Gazete> gazetes = null;
		 ArrayList<Gazete> journals=new ArrayList<Gazete>();
			if(dbhandler.getPreference("sort_type").equals(sorting_method_letter))
				gazetes = dbhandler.getAllGazete("name",true);
			else if (dbhandler.getPreference("sort_type").equals(sorting_method_hit))
				gazetes=dbhandler.getAllGazete("rank",false);
			
			//gazeteler.clear();
			// Reading all journals from database
			for (int i = 0; i < gazetes.size(); i++) {
				Gazete gt=gazetes.get(i);
				String log = "ID:" + gt.get_id() + " Name: " + gt.getName()
						+ " ,Image: " + gt.getImage()+ " Rank: "+gt.getRank();
						Log.d("Result: ", log);
				journals.add(gt);
			}
			return journals;
		 
	 }

}
