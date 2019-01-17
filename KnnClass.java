package knn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class KnnClass {
		
	int k;
	static int tp = 0;
	static int tn = 0;
	static int fp = 0;
	static int fn = 0;
	public static ArrayList<Data> chunks = new ArrayList<Data>();
	public static int counter = 0;
	
	public void getInput()throws IOException
	{
		Scanner input = new Scanner(System.in);
		
		
		File file = new File("C:\\eclipse\\input.txt");
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		
		String temp;
		while ((temp = buffer.readLine()) != null)
		{
			String[] str = temp.split(",");
			Data dat = new Data(Integer.parseInt(str[0]),Integer.parseInt(str[1]),Integer.parseInt(str[2]),Integer.parseInt(str[3]));
			dat.counted = false;
			chunks.add(dat);
			counter++;			
		}
		input.close();
		buffer.close();
	}
	private double getDistance(Data data, Data data2) {
		double a = data.x - data2.x;
		double b = data.y - data2.y;
		double c = data.z - data2.z;
		return Math.sqrt(a*a + b*b + c*c);
	}
	public void getAccuracy()
	{
		Random rand = new Random(906);
		int temp = (counter)/10;
		int temp1 = temp;
		int i = 0;
		Data[] tempArray =  new Data[temp];
		while(temp1 > 0)
		{
			int temp2 = rand.nextInt(counter);
			if(chunks.get(temp2).counted != false) {continue;}
				
			tempArray[i] = chunks.get(temp2);
			chunks.get(temp2).counted = true;
			temp1--;
			i++;
		}
		
		for(int j = 0; j < temp; j++)
		{
			double[] distArr = new  double[counter+1-temp];
			double[] distArr2 = new  double[counter+1-temp];
			int[] clas = new  int[counter];
			i = 0;
			for(int k = 0; k < counter ; k++)
			{
				
				if(chunks.get(k).counted == true) 
					continue;	
				
				distArr[i] = getDistance(tempArray[j],chunks.get(k));
				clas[i] = chunks.get(k).cls;
				i++;
			}
			for(int k = 0; k < counter+1-temp ; k++)
				distArr2[k] = distArr[k];
				
			
			Arrays.sort(distArr);
			
			int[] topCls = new int[2];
			topCls[0] = 0;
			topCls[1] = 0;
			for(int k = 0; k < 4; k++)
			{
				for(int l = 0; l < counter+1-temp ; l++)
				{
					if((distArr2[l] == distArr[k]))
					{
						if(clas[l] == 1)
							topCls[0]++;
						else if(clas[l] == 2)
							topCls[1]++;
						break;
					}
				}
			}
			
			if(topCls[0] > topCls[1])
			{
				if(tempArray[j].cls == 1)
					tp++;
				else
					tn++;
			}
			else if(topCls[1] > topCls[0])
			{
				if( tempArray[j].cls ==2)
					fp++;
				else
					fn++;
			}		
		}
	}

	public double knn() throws IOException
	{
		getInput();
		getAccuracy();
		
		double finalAccuracy = 0;	
		finalAccuracy = 100*(tp+fn)/(tp+tn+fp+fn); 

		return finalAccuracy;
	}
	
}
