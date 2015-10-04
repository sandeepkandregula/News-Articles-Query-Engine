package edu.buffalo.cse.irf14.analysis;

public class SymbolRule extends TokenFilter{
	TokenStream stream;
	public SymbolRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
		// TODO Auto-generated constructor stub
		if(stream !=null){
			String token;
			while(stream.hasNext()){
				token = stream.next().toString();
				if(token!=null && token != ""){

					token=token.replaceAll("won't","will not");
					token=token.replaceAll("shan't","shall not");
					token=token.replaceAll("I'm","I am");
					token=token.replaceAll("won't", "will not");
					token=token.replaceAll("'d"," would");
					token=token.replace("n't"," not");
					token=token.replaceAll("(s')", "s");
					token=token.replaceAll("'ve$"," have");
					token=token.replaceAll("('ll$)"," will");
					token=token.replaceAll("'re"," are");
					token=token.replaceAll("('em$)","them");
					token=token.replaceAll("('s)|(')","");
					token=token.replaceAll("[!,?.]*$", "");
					token=token.replaceAll("^-+$","");
					token=token.replaceAll("([A-Z0-9]-[A-Z0-9])", "$1");
					token=token.replaceAll("([a-z])-([a-z])", "$1 $2");
					token=token.replaceAll("--","");
					if(token.length()==0){stream.remove();}else{stream.replace(token);}
				}
			}	
						
			stream.reset();
			while(stream.hasNext()){
				System.out.println(stream.next().getTermText());
			}
						
		}
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
