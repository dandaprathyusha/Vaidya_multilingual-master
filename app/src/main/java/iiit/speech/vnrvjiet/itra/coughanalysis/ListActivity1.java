package iiit.speech.vnrvjiet.itra.coughanalysis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu;


import java.io.File;
import java.io.IOException;

import iiit.speech.itra.R;
import lib.sound.sampled.LineUnavailableException;
import lib.sound.sampled.UnsupportedAudioFileException;


public class ListActivity1 extends Activity {

  ListView listView ;
private String[] fileArray;
private SoundPool c1;
private int c1p;
//	private OnSeekBarChangeListener sb;

MediaPlayer m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        String path = Environment.getExternalStorageDirectory().toString()+"/AudioRecorder";
		Log.d("Files", "Path: " + path);
		File f = new File(path);        
		File files[] = f.listFiles();
		fileArray = new String[files.length];
		for (int i = 0; i < files.length; ++i){
		fileArray[i] = files[i].getName();
		}
        listView = (ListView) findViewById(R.id.list);
        
        // Defined Array values to show in ListView
      

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, fileArray);


        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

        	public SeekBar seek1;
			private String selectedFilePath;

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) 
			{            final int Position = position;
			final AdapterView<?> Parent = parent;
				       PopupMenu popup = new PopupMenu(ListActivity1.this, view);  
	                   //Inflating the Popup using xml file  
	                   popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());  
	           
	                   //registering popup with OnMenuItemClickListener  
	                   popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
	                   {  
	                        
							private String Result;

							public boolean onMenuItemClick(MenuItem item) 
	                         {  if(item.getItemId()==R.id.one)
	                             {    m = new MediaPlayer();
	                             
	                             try {
	                                m.setDataSource(Environment.getExternalStorageDirectory().toString()+"/AudioRecorder/"+fileArray[Position]);
	                             }
	                             
	                             catch (IOException e) {
	                                e.printStackTrace();
	                             }
	                             
	                             try {
	                                m.prepare();
	                             }
	                             
	                             catch (IOException e) {
	                                e.printStackTrace();
	                             }
	                           
	                                     final AlertDialog.Builder popDialog = new AlertDialog.Builder(ListActivity1.this);
	                                     final LayoutInflater inflater = (LayoutInflater)ListActivity1.this.getSystemService(LAYOUT_INFLATER_SERVICE);
	                                     
	                                     final View Viewlayout = inflater.inflate(R.layout.your_dialog,
	                                             (ViewGroup) findViewById(R.id.layout_dialog));       

	                                     final TextView item1 = (TextView)Viewlayout.findViewById(R.id.txtItem1); // txtItem1
	                             
	                             		
	                             		popDialog.setIcon(android.R.drawable.stat_sys_headset);
	                             		popDialog.setTitle("Playing");
	                             		popDialog.setView(Viewlayout);

	                                     popDialog.setCancelable(false);
	                             		//  seek11
	                             		
	                             		 // Important
	                             		
	                             		 
	                      
	                             		// Button OK
	                             		popDialog.setPositiveButton("Stop",
	                             				new DialogInterface.OnClickListener() {
	                             					public void onClick(DialogInterface dialog, int which) {
	                             						dialog.dismiss();
	                             						m.stop();
	                             					}

	                             				});


	                             		popDialog.create();
	                             		popDialog.show();
	                             
	                             		
	                             
	                             m.start();
	                             Toast.makeText(ListActivity1.this,"Playing",Toast.LENGTH_SHORT).show();


	                             }    
	                            else if(item.getItemId()==R.id.two)
	                            { 
	                               String selectedFilePath = Environment.getExternalStorageDirectory().toString()+"/AudioRecorder/"+fileArray[Position];
	             				
	             				File file = new File(selectedFilePath);
	             				if(file.exists())
	             				  file.delete();
	             				Intent intent = new Intent(ListActivity1.this,ListActivity1.class);
	             				Toast.makeText(ListActivity1.this,"" + item.getTitle(),Toast.LENGTH_SHORT).show();
	                     	    startActivity(intent);
	                         	
	                         	
	                            }
	                         else if(item.getItemId()==R.id.three) {
								 String Path = Environment.getExternalStorageDirectory().toString() + "/AudioRecorder/" + fileArray[Position];

								 try {
									 Stress v = new Stress();
									 System.out.println("System instantiated");
									 Result = v.VoiceTest(Path);
									 final AlertDialog.Builder popDialog = new AlertDialog.Builder(ListActivity1.this);
									 final LayoutInflater inflater = (LayoutInflater) ListActivity1.this.getSystemService(LAYOUT_INFLATER_SERVICE);

									 final View Viewlayout = inflater.inflate(R.layout.your_dialog,
											 (ViewGroup) findViewById(R.id.layout_dialog));

									 final TextView item1 = (TextView) Viewlayout.findViewById(R.id.txtItem1); // txtItem1
									 item1.setText(Result);

									 popDialog.setIcon(android.R.drawable.stat_sys_headset);
									 popDialog.setTitle("Stress Processing Result");
									 popDialog.setView(Viewlayout);

									 popDialog.setCancelable(false);
									 //  seek11

									 // Important


									 // Button OK
									 popDialog.setPositiveButton("Back",
											 new DialogInterface.OnClickListener() {
												 public void onClick(DialogInterface dialog, int which) {
													 dialog.dismiss();

												 }

											 });


									 popDialog.create();
									 popDialog.show();
									 //Toast.makeText(ListActivity1.this,""+Result,Toast.LENGTH_SHORT).show();

								 } catch (LineUnavailableException e) {
									 e.printStackTrace();
								 } catch (IOException e) {
									 e.printStackTrace();
								 } catch (UnsupportedAudioFileException e) {
									 e.printStackTrace();
								 }
							 }
	                         
	                         else if(item.getItemId()==R.id.four)
	                         {  
	                        	 String Path = Environment.getExternalStorageDirectory().toString()+"/AudioRecorder/"+fileArray[Position];
	                        	
	                        	 try {
	                        		Cough v  = new Cough();
	                        		System.out.println("System instantiated");
									Result = v.VoiceTest(Path);
									  final AlertDialog.Builder popDialog = new AlertDialog.Builder(ListActivity1.this);
	                                     final LayoutInflater inflater = (LayoutInflater)ListActivity1.this.getSystemService(LAYOUT_INFLATER_SERVICE);
	                                     
	                                     final View Viewlayout = inflater.inflate(R.layout.your_dialog,
	                                             (ViewGroup) findViewById(R.id.layout_dialog));       

	                                     final TextView item1 = (TextView)Viewlayout.findViewById(R.id.txtItem1); // txtItem1
	                                     item1.setText(Result);
	                             		
	                             		popDialog.setIcon(android.R.drawable.stat_sys_headset);
	                             		popDialog.setTitle("Cough Processing Result");
	                             		popDialog.setView(Viewlayout);

	                                     popDialog.setCancelable(false);
	                             		//  seek11
	                             		
	                             		 // Important
	                             		
	                             		 
	                      
	                             		// Button OK
	                             		popDialog.setPositiveButton("Back",
	                             				new DialogInterface.OnClickListener() {
	                             					public void onClick(DialogInterface dialog, int which) {
	                             						dialog.dismiss();
	                             					
	                             					}

	                             				});


	                             		popDialog.create();
	                             		popDialog.show();
									//Toast.makeText(ListActivity1.this,""+Result,Toast.LENGTH_SHORT).show();
									
								} catch (UnsupportedAudioFileException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (LineUnavailableException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

	                         }
	                       return true;     
	                           
	                        	 
	                    
	                          }  
	                    }
	                   );  
	
	            popup.show();
	            return true;//showing popup menu  
	           }  
			
	          }
        );
    }



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	   
	public void onBackPressed()  
	{  
	    //do whatever you want the 'Back' button to do  
	    //as an example the 'Back' button is set to start a new Activity named 'NewActivity'  
		 ListActivity1.this.finish();
	      startActivity(new Intent(ListActivity1.this,MainActivity.class));  
	}
       


}