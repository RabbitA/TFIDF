import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class TF {

	
	public static HashMap<String, Double> TFcalc (String fileInDir) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap<String, Double> mapFreq = new HashMap<String, Double>();
		String newLine=null;
		String term=null;
		int termCount=0;
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File(fileInDir)));
			while((newLine=br.readLine())!=null){
				newLine=Filter.UseFilter(newLine);
				newLine=newLine+" ";
				if (!newLine.equals(" ")){
					while( (newLine.indexOf(" ")) != -1){
						termCount++;
						term=newLine.substring(0, newLine.indexOf(" "));
						newLine=newLine.substring(newLine.indexOf(" ")+1);
						if(map.get(term) == null){
							map.put(term, 1);
						} else {
							map.put(term, (map.get(term))+1);
						}
					}
				} else{ newLine="";}
			}
			br.close();
			String tempK=null;Double tempV=(double) 0;
			for(Entry<String, Integer> entry: map.entrySet()){
				tempK=entry.getKey();
				tempV=(double) map.get(tempK);
				mapFreq.put(tempK, (tempV/termCount));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapFreq;
	}
		

	
	//filter--
	public static String StringFilter(String str) throws PatternSyntaxException {     
		// 只允许字母和数字       
		// String regEx  =  "[^a-zA-Z0-9]";                     
		// 清除掉所有特殊字符
		// 多个空格变成一个空格
	
		String regEx0="(n’t|n't|’ve|'ve|’m|'m|’s|'s|’re|'re)|[^a-zA-Z]";
		String regEx1="[^a-zA-Z]";
		String regEx2=" {2,}"; //两个及其以上的空格
		Pattern p0 = Pattern.compile(regEx0,Pattern.DOTALL);     
		Matcher m0 = p0.matcher(str);     
		str= m0.replaceAll(" ");
		//Pattern p1 = Pattern.compile(regEx1,Pattern.DOTALL);     
		//Matcher m1 = p1.matcher(str);     
		//str= m1.replaceAll(" ").trim();
		Pattern p2 = Pattern.compile(regEx2);     
		Matcher m2 = p2.matcher(str); 
		str= m2.replaceAll(" ").trim();
		return str;		
	}
	//

}
