

import java.util.Arrays;
public class testfile {

	public static void  main(String[] args){
		
	
	String stringvalue= new String("STEMMER");
	
	String textvalue= new String("hunted");
		
	char[] text= textvalue.toCharArray();
	
	String g= new String(text);
	int b=textvalue.length()-1;
	
	
	
		
		
		switch(stringvalue)
			
			{
			
			case "SYMBOL":
				
						
				if(text[b]=='?'|| text[b]=='!' || text[b]=='.'  ){
					
					text[b]=' ';
					
					g = new String(text);
					
				}	
							
				
				for(int i=0;i<textvalue.length()-2;i++){
				if(text[i]=='\'' && text[i+1]=='v'&& text[i+2]=='e'){ 
					text[i]=' ';
					text[i+1]=' ';
					text[i+2]=' ';
					
					char [] value= Arrays.copyOfRange(text, 0, i);	
					g=new String(value);
					g= g.concat(" have");
					
				}
				
				else if(text[i]=='\'' && text[i+1]=='l'&& text[i+2]=='l'){
					
					text[i]=' ';
					text[i+1]=' ';
					text[i+2]=' ';
					char [] value= Arrays.copyOfRange(text, 0, i);	
					g=new String(value);
					g= g.concat(" will");
				}
				
				else if(text[i]=='n' && text[i+1]=='\''&& text[i+2]=='t'){
					
					text[i]=' ';
					text[i+1]=' ';
					text[i+2]=' ';
					
					char [] value= Arrays.copyOfRange(text, 0, i);	
					g=new String(value);
					g= g.concat(" not");
			
				}
				}
				
				
				for(int i=0;i<textvalue.length()-1;i++){
					
					if(text[i]=='-'){
							if(text[i-1]!=' ' &&text[i-2]!=' '&&text[i+1]!=' '&&text[i+2]!=' '){
									if(Character.isDigit(text[i-1])||Character.isDigit(text[i-2])||Character.isDigit(text[i+1])||Character.isDigit(text[i+2])){
										text[i]='-';
									}
					
							else{text[i]=' ';}
						
									}
							
														}
					
					g=new String(text);
				}
				
				System.out.println(g);
				
				break;
				
				
				
				
				case  "SPECIALCHARS":
					for(int i=0;i<textvalue.length();i++){
						if(text[i]=='@'||text[i]=='#'||text[i]=='$'||text[i]=='%'||text[i]=='^'||text[i]=='&'||text[i]=='*'||text[i]=='('||text[i]==')'){
					
							text[i]=' ';}}	
					g = String.copyValueOf(text);
					
				break;
				
				
				
				case "CAPITALIZATION":
				break;
				
				case "STEMMER":
					for(int i=0;i<textvalue.length()-4;i++){
						if(text[i+2]=='i'&& text[i+3]=='n'&& text[i+4]=='g'){
								if(text[i]==text[i+1]){
									char [] stem= Arrays.copyOfRange(text, 0, i);	
									g=new String(stem);
								}
								
								else if(text[i+1]!=' '){
									char [] stem = Arrays.copyOfRange(text, 0, i+2);
									g=new String(stem);
								}
						}	
					}
					for(int i=0;i<textvalue.length()-1;i++){
						if(text[i+1]=='e'&&text[i+2]=='d'){
							char [] stem= Arrays.copyOfRange(text, 0, i+1);	
							g=new String(stem);
						}
					}
				break;
			
			

		
		
	
	}

		System.out.println(g);
}
}
