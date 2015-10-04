
public class snippet {

	public static void main(String[] args) {
		String docID="0001";
		String value="\"home\" AND heart OR Category:family";
		int length=5;
		
		String output= snippet(docID,value,length);
		System.out.println(output);
	}

	public static String snippet(String docID, String value, int length) {
		// TODO Auto-generated method stub
		// finding the doc and placing the content in the string below...
		
		
		String query=value;
		
		query=query.replaceAll("AND ","");
		query=query.replaceAll("OR ", "");
		query=query.replaceAll("NOT ", "");
		query=query.replaceAll("([A-Za-z]+):([A-Za-z])", "$2");
		query=query.replaceAll("\"","");
		System.out.println(query);
		String[] com=query.split(" ");
		
		int lenperword=length;//(com.length+2);
		int l=0;
		
		
		String content="The home is where the heart is placed. We see that the home is a house where a family stays.";
		
		String[] tokens= content.split(" ");
		String out="";
		
		for(int i=0;i<tokens.length;i++){
			for(int j=0;j<com.length;j++)
			if(tokens[i].equalsIgnoreCase(com[j])){
				for(l=-lenperword/2;l<=lenperword/2;l++){
					if(i+l<0||i+l>tokens.length-(lenperword/2-1)){}
					else{
						out=out+" "+tokens[i+l];
					}
				}
				out=out+"..";}
		}
		
		String[] a=out.split(" ");
		out="";
		for(int i=0;i<a.length-1;i++){
			if(a[i].contains(a[i+1])){a[i+1]="";}
		out=out+" "+a[i];
		}
		out=out.toLowerCase();
		return out;
	
	}
}
