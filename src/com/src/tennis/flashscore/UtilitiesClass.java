package com.src.tennis.flashscore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class UtilitiesClass {
	public boolean checkfolderforMatchId(String matchId) {
		// TODO Auto-generated method stub
		String url = "D:\\ATP\\Matches\\"+matchId;
		File file = new File(url);
		if (!file.exists()) 
		{
			return false;
		}
		else{
			return true;
		}
	}
	public void checkAndCreateFolder()
	{
		
	}
	static void createFile(String data, String id, String nameoffile) throws IOException
	{
		String url = "D:\\ATP\\Matches\\"+id+"";
		File file2 = new File(url+"\\"+nameoffile+".html");
		System.out.println(url);
		File file = new File(url);
		//File file = new File("D://ATP//dfasd.txt");
		// if file doesn't exists, then create it
		if (!file.exists()) 
		{
			if(file.mkdirs()){
				System.out.println("FileCreated");

				if(!file2.exists())
				{
					file2.createNewFile();
				}

			}
		}
		FileWriter fw = new FileWriter(file2.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(data);
		bw.close();	

	}
	public void createFile(String fileNameTomorrow) throws IOException {
		// TODO Auto-generated method stub
		File f = new File(fileNameTomorrow);
		if(!f.exists())
		{
			f.createNewFile();
		}
		else
		{
			System.out.println("File Existing so we will delete and create again");
			f.delete();
			f.createNewFile();
		}
	}
}
