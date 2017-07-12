package iiit.speech.vnrvjiet.itra.coughanalysis;
import java.io.File;
import java.io.IOException;

import lib.com.sun.media.sound.WaveFileReader;
import lib.comirva.KMeansClustering;
import lib.comirva.audio.Matrix;
import lib.comirva.audio.PointList;
import lib.sound.sampled.AudioInputStream;
import lib.sound.sampled.AudioSystem;
import lib.sound.sampled.UnsupportedAudioFileException;



public class testing {


private PointList pl1;
private WaveData w;
private float[] audioFloats;
private PreProcess pp;
private FeatureExtract fe;
private int i;
private KMeansClustering kmc1;
private Matrix M1;
private double[][] da;
private WaveFileReader wr;

public double[][] testing1(File testdryfiles) throws UnsupportedAudioFileException, IOException
{
	 pl1 = new PointList(39);
	 System.out.println("PointList instantiated");
	 
	wr = new WaveFileReader();
	
	 AudioInputStream audioInputStream=wr.getAudioInputStream(testdryfiles);//AudioSystem.getAudioInputStream(testdryfiles);
	
	
	System.out.println("audio stream done");
     
     wr.getAudioFileFormat (testdryfiles);
	         
	         
	        
     w = new WaveData();
	 
	       
     audioFloats=w.extractFloatDataFromAudioInputStream(audioInputStream);
	      	
	             
     pp= new   PreProcess(audioFloats, 256, 1600);
	     
	           
	           
     fe = new  FeatureExtract(pp.framedSignal,1600,256);
	      
	           
	           
     fe.makeMfccFeatureVector();
	           
	       

	       
     for(i=0;i< fe.featureVector.length;i++)
	       {
	      	   pl1.add(fe.featureVector[i]);
	    	   
	    	   
	       }

       kmc1 = new KMeansClustering(1,  pl1) ;
       
       kmc1.run();
       
       M1= kmc1.getMean(0);
       
       da = M1.getArray();
     
	
	
	
	return da;
	
}
}