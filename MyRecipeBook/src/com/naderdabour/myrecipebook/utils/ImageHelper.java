package com.naderdabour.myrecipebook.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageHelper {
	
	private Context context;
	private File file;
	
	public ImageHelper(Context context){
		this.context = context;
	}

	public String createFile(String fileName, byte[] data) throws IOException{
		String savedFilePath = null;
		FileOutputStream fos = null;
		boolean checkExternalStorage = 
				Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		
		if(checkExternalStorage){
			file = new File(context.getExternalFilesDir(null),fileName);
			fos = new FileOutputStream(file);
		} else {
			file = new File(context.getFilesDir(), fileName);
			fos = context.openFileOutput("recipes", Context.MODE_PRIVATE);
		}
		
		if(fos != null){
			fos.write(data);
			fos.close();

			savedFilePath = file.getAbsolutePath();
		}
		
		return savedFilePath;
	}
	
	public Bitmap readFile(String path) throws IOException{
		
		FileInputStream fis = new FileInputStream(new File(path));
		
		BufferedInputStream bis = new BufferedInputStream(fis);
		Bitmap picture = BitmapFactory.decodeStream(bis);
		
		
		bis.close();
		fis.close();

		return picture;

	}
}
