import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class filter {

	public static void  main(String[] args){
		
		
		String stringvalue= new String("DATES");
		
		String textvalue= new String("2001");
	
		
		switch(stringvalue)
		
		{
		case "SYMBOL":
			
			textvalue=textvalue.replaceAll("[!,?.]$", "");
			textvalue=textvalue.replaceAll("('s)|(s')|(')","");
			textvalue=textvalue.replaceAll("'ve"," have");
			textvalue=textvalue.replaceAll("'ll"," will");
			textvalue=textvalue.replaceAll("n't"," not");
			textvalue=textvalue.replaceAll("(\\w+)-(\\w+)", "$1 $2");
			textvalue=textvalue.replaceAll("(\\w+)-(\\d+)", "$1-$2");
			break;
		
		case "ACCENTS":
			textvalue=Normalizer.normalize(textvalue, Normalizer.Form.NFD);
			//not sure about the issue
			break;
		
		case "SPECIALCHARS":
			textvalue = textvalue.replaceAll("[!@#$%^&*/(/)_=+/{/}/[/]/|]","");			
			break;
		
		case"DATES":
			String month=null;
			int day=0;
			int year=0;
			
			
			textvalue=textvalue.concat(" month day year HH:MM:SS");
			System.out.println(textvalue);
			String[] dates= textvalue.split(" ");
			for(int i=0;i<dates.length;i++){
				System.out.println(dates[i]);
			}
			String[] monthArray = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","January","February","March","April","May","June","July","August","September","October","November","December"};
			
			dates[0]=dates[0].replaceAll("month","Jan");
			dates[1]=dates[1].replaceAll("month","1");
			dates[1]=dates[1].replaceAll("day","1");
			dates[2]=dates[2].replaceAll("month","1900");
			dates[2]=dates[2].replaceAll("day","1900");
			dates[2]=dates[2].replaceAll("year","1900");
			dates[3]=dates[3].replaceAll("month", "00:00:00");
			dates[3]=dates[3].replaceAll("day", "00:00:00");
			dates[3]=dates[3].replaceAll("year", "00:00:00");
			dates[3]=dates[3].replaceAll("HH:MM:SS", "00:00:00");
			
			for(int i=0;i<dates.length;i++){
				System.out.println(dates[i]);
			}
			for(int j=0; j<12; j++){
				if(dates[0].equalsIgnoreCase(monthArray[j]) || dates[0].equalsIgnoreCase(monthArray[j+12])){
					month=monthArray[j];
					//int monthvalue=j;
					day=Integer.parseInt(dates[1]);
					year=Integer.parseInt(dates[2]);
					//int rem=year%4;
					if(j==1||j==3||j==5||j==7||j==8||j==10||j==12){
						if(!(day>=1&&day<=31)){
						day=1;
						}	
					}
					else if(j==4||j==6||j==9||j==11){
						if(!(day>=1&&day<=30)){
							day=1;
						}
					}
					else if(j==2){
						if(!(day>=1&&day<=28)){
							day=1;
						}
					}
				}}
				if(dates[0].matches("[0-9]")){
					day=Integer.parseInt(dates[0]);
					year=Integer.parseInt(dates[1]);
					if(day>=1&&day<=31){
						month="Jan";
					}}
				if(dates[0].matches("[0-9]")){
						year=Integer.parseInt(dates[0]);
						if(year!=0){
							month="Jan";
							day=1;
						}
					}
				
			
			

			if(year==0){
				year=1900;
			}
			if(day==0){
				day=1;
			}
			if(month==null){
				month="Jan";
			}
			
			String[] time=dates[3].split(":");
			int hour= Integer.parseInt(time[0]);
			int min= Integer.parseInt(time[1]);
			int sec= Integer.parseInt(time[2]);
			if(!(hour<=00&&hour>=23)){
				hour=00;}
			if(!(min<=00&&min>=59)){
				min=00;}
			if(!(sec<=00&&sec>=59)){
				sec=00;}
						
					
			
			
			textvalue=month+" "+day+" "+year+" "+hour+":"+min+":"+sec;
			break;
			
		case "NUMBERS":
			textvalue= textvalue.replaceAll("[0-9]", "");
			break;
		
		case "CAPITALIZATION":
			char[] text=textvalue.toCharArray();
			String[] camel = textvalue.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");
			System.out.println(camel.length);
				if(camel.length>1){	
				}
				else{
					for(int i = 0;i<text.length;i++){
						if(!Character.isUpperCase(text[i])){
							textvalue=textvalue.toLowerCase();	
						}
						else{
					
						}
					}
				}
			
			
			
			break;
		case "STEMMER":
			
			textvalue=textvalue.replaceAll("ing","");
			textvalue=textvalue.replaceAll("ed","");
			if(textvalue=="are"||textvalue=="am"&&textvalue=="is"){textvalue="be";}
			if(textvalue=="have"||textvalue=="had"&&textvalue=="are"){textvalue="have";}
			if(textvalue=="was"||textvalue=="were"){textvalue="was";}
			
			break;
		
		case "STOPWORD":
			
			String[] stopword =  {"a", "about", "above", "across", "after", "afterwards", "again", "against", "all", "almost","alone", "along", "already", "also","although","always","am","among", "amongst", "amount",  "an", "and", 
	                "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became","because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", 
	                "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt","cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else",
	                "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", 
	                "front", "full", "further", "get", "give", "go", "had", "has", "hasnt","have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself","him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", 
	                "inc", "indeed", "interest", "into","is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many",
	                "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must","my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", 
	                "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps",
	                "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she","should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", 
	                "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", 
	                "these", "they", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", 
	                "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever","where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", 
	                "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet","you", "your", "yours", "yourself", "yourselves","care","sure","contact",
	                "grounds","tried","said,","plan","forces","sent","is,","was","like","discussion","diffrent","layout","area.","thanks","thankyou","hello","bye","rise","fell","fall","psqft","km","miles"};
				
			for(int i=0;i<stopword.length;i++){
				if (textvalue.equalsIgnoreCase(stopword[i])){
					textvalue=textvalue.replaceAll("\\w+","");
				}
			}
			
			break;
			
			
		}
		System.out.println(textvalue);
}
}