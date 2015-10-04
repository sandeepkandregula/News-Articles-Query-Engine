package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class CapitalizationRule extends TokenFilter {
	TokenStream stream;
Boolean space=false,propervalue=false;

	public CapitalizationRule(TokenStream stream) {


		super(stream);
		this.stream = stream;
		int j=0;
		//String camel;
		if (stream != null) {

			String token, token2;
			
			while (stream.hasNext()) {
				j++;
				token=stream.next().toString();
				if(j>=stream.streamSize){j=j-2;}
				token2=stream.currentTokenList.get(j);
				token= Spacetest(token,token2);
				if(space){stream.replace(token);
				stream.next();
				stream.remove();
				space=false;
				}
				else{
					token= proper(token);
					System.out.println(propervalue);
					if(propervalue==true){
						propervalue=false;
						}
					else{
					token= Allcaps(token);
					}
				
				stream.replace(token);}
				System.out.println(token);
				
			}
		}
				
				stream.reset();
				while (stream.hasNext()) {
					System.out.println(stream.next().getTermText());
				}

			
			// TODO Auto-generated constructor stub
		}

	
	
	public String proper(String token) {
		String[] propernouns = { "Apple's", "California.", "Texas" };
		
		
		for(int j=0;j<propernouns.length;j++)
		{

			System.out.println(propernouns[j]);
			System.out.println(propervalue);
			if (token.matches(propernouns[j])) {
				propervalue=true;
				
				}
		}
		return token;
}



	public String Allcaps(String token) {
		
		char[] text=token.toCharArray();
		String[] camel = token
				.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");
		if (camel.length > 1) {}

		else {
			for (int i = 0; i < text.length; i++) {
System.out.println(text[i]);
				if (!Character.isUpperCase(text[i])) {
					token=token.toLowerCase();
					return token;
				}
			}
		
			
				
			}
		return token;
		
		}
	public String Spacetest(String token, String token2){
		if(Character.isUpperCase(token.charAt(0))&&Character.isUpperCase(token2.charAt(0))){
			if(!(token==token2)){
			space=true;
			token=token+" "+token2;
			return token; 
		}
			else{
				return token;}
		}
		
		else{
			
			return token;}
	}
		
	

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return stream;
	}
}
