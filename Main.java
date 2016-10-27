import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Main {
	public static void main(String[] args){
		DataSetMap DSM=new DataSetMap();
		String TestTerm=null;
		String IndexFileDir=null;
		String TestFile=null;
		int DataSetNum=0;
		int Percent=0;
		IndexFileDir="/Users/jiachenma/TXTDataset/index.txt";
		//TestTerm="hurry";
		TestFile="/Users/jiachenma/TXTDataset/TheLordoftheRings.txt";
		HashMap<String, Double> mapFreq = new HashMap<String, Double>(); //TF 传入
		HashMap<String, Double> TFIDFmap = new HashMap<String, Double>(); //result
		HashMap<String, String> DMap = new HashMap<String, String>(); //传入的dataset map， 结构为 "2#4#0#0#7#0"
		HashMap<String, Double> DMapToDF = new HashMap<String, Double>(); //计算每个term在几个文件中出现次数map，三个文件出现则为3
		Filter.BuildFilter(); //建立新的filterLib
		DMap=DSM.CreateDMap(IndexFileDir);
		mapFreq=TF.TFcalc(TestFile);
		
		// 计算有多少个文件
		BufferedReader bw;
		try {
			bw = new BufferedReader(new FileReader(new File (IndexFileDir)));
			while(bw.readLine()!=null){
				DataSetNum++; //System.out.println("Total Doc Num: "+DataSetNum);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 对传入对DataSetMap处理，生成DMapToDF map
		String temp1=null; String temp2=null; int temp3=0;
		for(Entry<String, String> entry: DMap.entrySet()){
			temp1=entry.getKey();
			temp2=DMap.get(temp1);
			temp2=temp2.substring(0,temp2.length()-1); //去除结尾的0
			temp3=0; //出现次数
			
			while((temp2.indexOf('#')) != -1){ //遍历temp2
				if ((Integer.parseInt(temp2.substring(0,temp2.indexOf('#'))))!=0){
					temp3++;
				}
				temp2=temp2.substring(temp2.indexOf('#')+1);
			}
			DMapToDF.put(temp1,(double) temp3);
			//System.out.println(temp1+": "+DMap.get(temp1)+" "+String.valueOf(temp3)+" "+DataSetNum+" "+mapFreq.get(temp1));
		}
		//System.out.println("DMapToDF is \n"+DMapToDF);
		
		//生成TFIDF map
		String temp4=null; Double temp5= (double) 0;
		for(Entry<String,Double> entry1: mapFreq.entrySet()){
			temp4=entry1.getKey();
			temp5=mapFreq.get(temp4);
			if(DMapToDF.get(temp4) != null){ //如果新term未再dataset中出现，即新的文件
				TFIDFmap.put(temp4, ((temp5)*(Math.log(DataSetNum/(DMapToDF.get(temp4))))));
			}
			else{
				TFIDFmap.put(temp4, ((temp5)*(Math.log((DataSetNum+1)/1))));
			}
		}
		//System.out.println(TFIDFmap);
		
		System.out.println("Total term in Testset: "+mapFreq.size());
		////////排序模块
		 List<Map.Entry<String, Double>> list_Data = new ArrayList<Map.Entry<String, Double>>(TFIDFmap.entrySet());  
		 System.out.println("Orignal TFIDF map:\n"+list_Data);  
		 Collections.sort(list_Data, new Comparator<Map.Entry<String, Double>>() {    
		 public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)  
		 {if ((o2.getValue() - o1.getValue())>0) return 1; else if((o2.getValue() - o1.getValue())==0) return 0; else return -1;} } );  
		////////
		 System.out.println("Sorted TFIDF map:\n"+list_Data);
	}
}
