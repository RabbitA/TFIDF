/*无法过滤空行
 * 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {
	public static String UseFilter(String Str){
		//Str 传入的待处理字符串
		Str=Str.toLowerCase();
		String regEx0="(\\bnot\\b|\\bcan't\\b|\\bshan't\\b|\\bwon't\\b)|(n’t\\b|n't\\b|’ve\\b|'ve\\b|’m\\b|'m\\b|’s\\b|'s\\b|’re\\b|'re\\b|'ll\\b|'d\\b)|[^a-zA-Z]|(\\bi\\b|\\bme\\b|\\bmy\\b|\\bmyself\\b)|(\\bwe\\b|\\bus\\b|\\bour\\b|\\bours\\b|\\bourselves\\b)|(\\byou\\b|\\byour\\b|\\byours\\b|\\byourself\\b|\\byourselves\\b)|(\\bhe\\b|\\bhim\\b|\\bhis\\b|\\bhimself\\b)|(\\bshe\\b|\\bher\\b|\\bhers\\b|\\bherself\\b)|(\\bit\\b|\\bits\\b|\\bitself\\b)|(\\bthey\\b|\\bthem\\b|\\btheir\\b|\\btheirs\\b|\\bthemselves\\b)|(\\bwhat\\b|\\bwhich\\b|\\bwho\\b|\\bwhom\\b|\\bthis\\b|\\bthat\\b|\\bthese\\b|\\bthose\\b)|(\\bbe\\b|\\bam\\b|\\bis\\b|\\bare\\b|\\bwas\\b|\\bwere\\b|\\bbeen\\b|\\bbeing\\b)|(\\bhave\\b|\\bhas\\b|\\bhad\\b|\\bhaving\\b)|(\\bdoes\\b|\\bdid\\b|\\bdoing\\b|\\bdo\\b)|(\\bwould\\b|\\bwill\\b|\\bshall\\b|\\bshould\\b|\\bcan\\b|\\bcould\\b|\\bmay\\b|\\bmight\\b|\\bmust\\b)|(\\ba\\b|\\ban\\b|\\bthe\\b)|(\\band\\b|\\bbut\\b|\\bif\\b|\\bor\\b|\\bbecause\\b|\\bas\\b|\\buntil\\b|\\bwhile\\b)|(\\bof\\b|\\bat\\b|\\bby\\b|\\bfor\\b|\\bwith\\b|\\babout\\b|\\bagainst\\b|\\bbetween\\b|\\binto\\b|\\bthrough\\b|\\bduring\\b|\\bbefore\\b|\\bafter\\b|\\babove\\b|\\bbelow\\b|\\bto\\b|\\bfrom\\b|\\bup\\b|\\bdown\\b|\\bin\\b|\\bout\\b|\\bon\\b|\\boff\\b|\\bover\\b|\\bunder\\b)|(\\bagain\\b|\\bfurther\\b|\\bthen\\b|\\bonce\\b)|(\\bhere\\b|\\bthere\\b|\\bwhen\\b|\\bwhere\\b|\\bwhy\\b|\\bhow\\b)|(\\ball\\b|\\bany\\b|\\bboth\\b|\\beach\\b|\\bfew\\b|\\bmore\\b|\\bmost\\b|\\bother\\b|\\bsome\\b|\\bsuch\\b)|(\\bno\\b|\\bnor\\b|\\bnot\\b|\\bonly\\b|\\bown\\b|\\bsame\\b|\\bso\\b|\\bthan\\b|\\btoo\\b|\\bvery\\b)|(\\bone\\b|\\bevery\\b|\\bleast\\b|\\blss\\b|\\bmay\\b|\\bnow\\b|\\bever\\b|\\bnever\\b|\\bsay\\b|\\bsays\\b|\\bsaid\\b|\\balso\\b|\\bget\\b|\\bgo\\b|\\bgoes\\b|\\bjust\\b|\\bmade\\b|\\bmake\\b|\\bput\\b|\\bsee\\b|\\bseen\\b|\\bwhether\\b|\\blike\\b|\\bwell\\b|\\bback\\b|\\beven\\b|\\bstill\\b|\\bway\\b|\\btake\\b|\\bsince\\b|\\banother\\b|\\bhowever\\b|\\btwo\\b|\\bthree\\b|\\bfour\\b|\\bfive\\b|\\bfirst\\b|\\bsecond\\b|\\bnew\\b|\\bold\\b|\\bhigh\\b|\\blong\\b)";
		Pattern p0 = Pattern.compile(regEx0,Pattern.DOTALL);     
		Matcher m0 = p0.matcher(Str);     
		Str= m0.replaceAll(" ");
		
		/*
		 * filter form: "AAA#BBB_CCC_DDD_" , replace BBB, CCC, DDD with AAA
		 * Snowball stop words: http://snowball.tartarus.org/algorithms/english/stop.txt
		 */
//		String Line=null;String K=null;
//		try {
//			BufferedReader br=new BufferedReader(new FileReader(new File("/Users/jiachenma/TXTDataset/filterLibrary.txt")));
//			while((Line=br.readLine())!=null){
//				K=Line.substring(0, Line.indexOf('('));
//				Line=Line.substring(Line.indexOf('('));
//				//过滤
//				//System.out.println("1"+Str+" "+K);
//				Pattern P = Pattern.compile(Line);
//				Matcher M =P.matcher(Str);
//				Str=M.replaceAll(K);
//				//System.out.println("2"+Str+" "+Line);
//				//@替换成空格
//				Pattern P3 = Pattern.compile("@");
//				Matcher M3 =P3.matcher(Str);
//				Str=M3.replaceAll(" ");
//				//System.out.println(Line);
//			}
//			br.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
		/*
		 * 
		 */
		String regEx2=" {2,}"; //两个及其以上的空格
		Pattern p2 = Pattern.compile(regEx2);     
		Matcher m2 = p2.matcher(Str); 
		Str= m2.replaceAll(" ").trim();
		
		return Str;
	}
	
	/*
	 * filter form: "AAA#BBB_CCC_DDD_" , replace BBB, CCC, DDD with AAA
	 * Snowball stop words: http://snowball.tartarus.org/algorithms/english/stop.txt
	 */
	public static void BuildFilter(){
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(new File("/Users/jiachenma/TXTDataset/filterLibrary.txt")));
			BufferedReader br=new BufferedReader(new FileReader(new File("/Users/jiachenma/TXTDataset/filter.txt")));
			String Line=null;String K=null;	
				while((Line=br.readLine())!=null){
					if(!Line.equals("")){
					K=Line.substring(0, Line.indexOf('#'));
					Line=Line.substring(Line.indexOf('#'));
					Pattern P = Pattern.compile("(#|_)");
					Matcher M =P.matcher(Line);
					Line=M.replaceAll("&&b|&&b");
					Line=Line.substring(4,Line.length()-4);
					//&变为\
					Pattern P1 = Pattern.compile("&");
					Matcher M1 =P1.matcher(Line);
					Line=M1.replaceAll("\\\\");
					Line="("+Line+")";
					bw.write(K+Line+"\n");
					}else{}
				}
				br.close();	
				bw.flush();
				bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
