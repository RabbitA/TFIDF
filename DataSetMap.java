/*Calc each term in DataSet's number.
 *Return a HashMap <term, num>
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;


public class DataSetMap {//每个term在所有文件中出现频数的map
	
	public Integer DFCount(String indexDir, String TestTerm){ //返回每个 term 在文件集中出现次数
		int DFC=0;
		HashMap<String, Integer> DMap=new HashMap<String, Integer>(); //返回的map
		HashMap<String, Integer> FMap=new HashMap<String, Integer>(); //每个文档的map
		File index=new File(indexDir); //read index.txt
		String dataFileDir=null;
		StringBuffer dataSet1=new StringBuffer();
		StringBuffer dataSet2=new StringBuffer();
		int FileCount=0; int LineCount=0; int TermCount=0;
		try {
			FileReader ifr=new FileReader(index);
			BufferedReader ibr=new BufferedReader(ifr);
			while ((dataFileDir=ibr.readLine())!=null){ //read from index
				FileCount++;
				File dataFile=new File(dataFileDir);
				FileReader dFfr=new FileReader(dataFile);
				BufferedReader dFbr=new BufferedReader(dFfr);
				String fileLine="";
				while((fileLine=dFbr.readLine())!=null){ //read from data files
					LineCount++;
					fileLine=Filter.UseFilter((fileLine)); //read one line
					String term =null;
					fileLine=fileLine+" "; //每行结尾 add 空格，空行变为空格
					while((fileLine.indexOf(" "))!=-1){
						if (!fileLine.equals(" ")){ //非空行
							TermCount++;
							term=fileLine.substring(0, fileLine.indexOf(" "));
							fileLine=fileLine.substring(fileLine.indexOf(" ")+1);
							//System.out.println(FileCount+"-"+LineCount+"-"+TermCount+"-"+term);
							if (FMap.get(term)==null){
								FMap.put(term, 1);
							}else{}
							//System.out.println("Map size: "+FMap.size());
						} else{ //空行
							fileLine = "";
						}
					}
					TermCount=0;
				}
				LineCount=0;
				
				if(FMap.get(TestTerm)!=null){
					DFC++;
				}
				//System.out.println(DFC);
				FMap.clear();
				dFbr.close();
				dataSet2.setLength(0);
				dataSet1.setLength(0);
			}
			ibr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DFC;
	}
	
	/*
	 * //返回一个map，key为term，value为“2#3#0#5#0”,代表在3个文件中出现2，3，5次
	 */
	public HashMap<String, String> CreateDMap(String indexDir){ 
		HashMap<String, String> DMap=new HashMap<String, String>(); //返回的map
		HashMap<String, String> FMap=new HashMap<String, String>(); //每个文档的map
		File index=new File(indexDir); //read index.txt
		String dataFileDir=null;
		StringBuffer dataSet1=new StringBuffer();
		StringBuffer dataSet2=new StringBuffer();
		int FileCount=0; int LineCount=0; int TermCount=0;
		try {
			FileReader ifr=new FileReader(index);
			BufferedReader ibr=new BufferedReader(ifr);
			while ((dataFileDir=ibr.readLine())!=null){ //read from index
				FileCount++;
				File dataFile=new File(dataFileDir);
				FileReader dFfr=new FileReader(dataFile);
				BufferedReader dFbr=new BufferedReader(dFfr);
				String fileLine="";
				while((fileLine=dFbr.readLine())!=null){ //read from data files
					LineCount++;
					fileLine=Filter.UseFilter((fileLine)); //read one line
					String term =null;
					fileLine=fileLine+" "; //每行结尾 add 空格，空行变为空格
					while((fileLine.indexOf(" "))!=-1){
						if (!fileLine.equals(" ")){ //非空行
							TermCount++;
							term=fileLine.substring(0, fileLine.indexOf(" "));
							fileLine=fileLine.substring(fileLine.indexOf(" ")+1);
							System.out.println(FileCount+"-"+LineCount+"-"+TermCount+"-"+term);
							if (!DMap.containsKey(term)){ //新的term,放入文件集map中
								DMap.put(term, "1");
							}else{ //若文件集已经记录了该term，则放入当前文件FMap中
								if(!FMap.containsKey(term)){ //若FMap未记录term，put 1
									FMap.put(term,"1");
								}else{ //若FMap记录term，value+1
									FMap.put(term, String.valueOf(Integer.parseInt(FMap.get(term))+1));
								}
							}
						} else{ //空行
							fileLine = "";
						}
					}
					TermCount=0;
				}
				LineCount=0;
				//marge DMap 与 FMap，已经确保了FMap所有key均在DMap中。遍历FMap，与DMap相对value相加
				String temp1=null;String temp2=null;String C=null;String D=null;
				for (Entry<String, String> entry : FMap.entrySet()) {  
					temp1=entry.getKey();
					temp2=((DMap.get(temp1))).substring(((DMap.get(temp1)).length()-1)); //取出DMap相应value最后一位
					C=String.valueOf(Integer.parseInt(temp2)+Integer.parseInt(FMap.get(temp1))); //DMap与FMap求和
					D=(DMap.get(temp1)).substring(0, (DMap.get(temp1)).length()-1); //取出DMap相应value除了最后一位的字符串
					DMap.put(temp1,(D+C));
				} 
				//遍历Dmap, 标记文章结束#
				String temp3=null;String temp4=null;
				for (Entry<String, String> entry : DMap.entrySet()) {  
					temp3=entry.getKey();
					temp4=DMap.get(temp3);
					temp4=temp4+"#0"; //结尾添加0，方便去除Dmap最后一位运算
					DMap.put(temp3, temp4);
				}  
				//清空FMap
				FMap.clear();
				dFbr.close();
				dataSet2.setLength(0);
				dataSet1.setLength(0);
			}
			ibr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Generated DMap: ");
		System.out.println(DMap);
		System.out.println("Total term in Dataset: "+DMap.size());
		return DMap;
	}
}
