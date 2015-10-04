package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateRule extends TokenFilter {

	TokenStream ts;
	TokenStream stream;
	String string = "";
	public DateRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
		this.ts = stream;
		
		
	}

	public String Month(String month) {
		String monthvalue = null;
		if (month.equalsIgnoreCase("January")){
			monthvalue = "01";
			}
		if (month.equalsIgnoreCase("February")){
			monthvalue = "02";
			}
		if (month.equalsIgnoreCase("March")){
			monthvalue = "03";
			}
		if (month.equalsIgnoreCase("April")){
			monthvalue = "04";
			}
		if (month.equalsIgnoreCase("May")){
			monthvalue = "05";
			}
		if (month.equalsIgnoreCase("June")){
			monthvalue = "06";
			}
		if (month.equalsIgnoreCase("July")){
			monthvalue = "07";
			}
		if (month.equalsIgnoreCase("August")){
			monthvalue = "08";
			}
		if (month.equalsIgnoreCase("September")){
			monthvalue = "09";
			}
		if (month.equalsIgnoreCase("October")){
			monthvalue = "10";
			}
		if (month.equalsIgnoreCase("November")){
			monthvalue = "11";
			}
		if (month.equalsIgnoreCase("December")){
			monthvalue = "12";
			}

		return monthvalue;
	}

	@Override
	public boolean increment() throws TokenizerException {
		if (stream != null) {
			String token, access=null;

			while (stream.hasNext()) {
				token = stream.next().toString();
				string = string + " " + token;
			}
			stream.reset();
			String[] tokenvalue, tokenvalue1;
			String date; // stores the final date
			String day = "01", month = "01", year = "1900";
			//Boolean dateexist = false, timeexist = false;
			
			Pattern p1 = Pattern.compile("\\d{1,2}:\\d{1,2} ?(AM|PM|am|pm)\\.{0,1}");
			Pattern p2 = Pattern.compile("\\d+ ?(BC|AD)");
			Pattern p3 = Pattern.compile("\\w+ \\d+, \\d{4}");
			Pattern p4 = Pattern.compile("\\d+ \\w+ \\d{4}");
			Pattern p5 = Pattern.compile("\\d{4}");
			Pattern p6 = Pattern.compile("\\d{4}-\\d{2}");
			Pattern p7 = Pattern.compile("\\w+ \\d{1,2}");
			Pattern p8 = Pattern.compile("\\d{1,2} \\w+");
			
			Matcher timematch = p1.matcher(string);
			Matcher adbcmatch = p2.matcher(string);
			Matcher monthdateyearmatch = p3.matcher(string);
			Matcher datemonthyearmatch = p4.matcher(string);
			Matcher yearonlymatch = p5.matcher(string);
			Matcher yeardatematch = p6.matcher(string);
			Matcher monthdatematch = p7.matcher(string);
			Matcher datemonthmatch = p8.matcher(string);
			
			
			if(timematch.find()){access="timematch";}
			else if(adbcmatch.find()){access="adbcmatch";}
			else if(monthdateyearmatch.find()){access="monthdateyearmatch";}
			else if(datemonthyearmatch.find()){access="datemonthyearmatch";}
			else if(yearonlymatch.find()){access="yearonlymatch";}
			else if(yeardatematch.find()){access="yeardatematch";}
			else if(monthdatematch.find()){access="monthdatematch";}
			else if(datemonthmatch.find()){access="datemonthmatch";}

			switch (access){
				case "timematch":
					
			
					int hour = 0;
					String time = null, min = "", sec = "00", minisec = "";
					
					tokenvalue = timematch.group(0).split(" ");

					if (tokenvalue.length == 2) {
						String[] tokenvalue2 = tokenvalue[0].split(":");
						hour = Integer.parseInt(tokenvalue2[0]);
						min = tokenvalue2[1];
						if (tokenvalue[1].charAt(0) == 'p'
								|| tokenvalue[1].charAt(0) == 'P')
							hour += 12;
						time = Integer.toString(hour) + ":" + min + ":" + sec
								+ minisec;

						while (stream.hasNext()) {

							String temp2 = stream.next().toString();
							if (temp2 != null && temp2 != "") {
								if (temp2.equalsIgnoreCase(tokenvalue[0].trim())) {
									stream.remove();
								}
								if (temp2.equalsIgnoreCase(tokenvalue[1].trim())) {

									if (tokenvalue[1].length() == 3) {
										if (tokenvalue[1].charAt(2) == "."
												.charAt(0))
											stream.replace(time.trim() + ".");
									} else
										stream.replace(time.trim());
									
								}
								if (temp2.equalsIgnoreCase(tokenvalue[1] + ",")) {
									stream.replace(time + ",");
								}

							}
						}
						stream.reset();
							
						while (stream.hasNext()) {
						String temp = stream.next().toString();

						}
					}
					if (tokenvalue.length == 1) {
						
						String[] tokenvalue2 = tokenvalue[0].split(":");
						hour = Integer.parseInt(tokenvalue2[0]);
						if (tokenvalue2[1].charAt(2) == 'p'
								|| tokenvalue2[1].charAt(2) == 'P')
							hour += 12;
						min = tokenvalue2[1].substring(0, 2);
						time = Integer.toString(hour) + ":" + min + ":" + sec;
						while (stream.hasNext()) {

							String temp2 = stream.next().toString();
							if (temp2 != null && temp2 != "") {
								if (tokenvalue[0].equalsIgnoreCase(temp2)) {
									if (tokenvalue[0].contains(".")) {stream.replace(time.trim() + ".");}
									else{stream.replace(time.trim());}
								}
								if (temp2.equalsIgnoreCase(tokenvalue[0] + ",")) {stream.replace(time + ",");}
							}
						}

					}
				

			
		break;
		
		case "yeardatematch": 
					tokenvalue = yeardatematch.group(0).split("-");
					date = tokenvalue[0];
					tokenvalue[1] = tokenvalue[0].substring(0, 2)+ tokenvalue[1];
					date = date + month + day + "-" + tokenvalue[1] + month + day;
					// txt = txt.replace(yeardatematch.group(0), date);
					//dateexist = true;
					while (stream.hasNext()) {
						String temp2 = stream.next().toString();
						if (temp2 != null && temp2 != "") {
							if (temp2.equalsIgnoreCase(yeardatematch.group(0).trim())) {stream.replace(date);}
							if (temp2.equalsIgnoreCase(yeardatematch.group(0).trim() + ".")) {stream.replace(date + ".");}
							if (temp2.equalsIgnoreCase(yeardatematch.group(0).trim() + ",")) {stream.replace(date + ",");}
						}
					}
				
			break;
			
			
			case "datemonthyearmatch":
					tokenvalue = datemonthyearmatch.group(0).split(" ");
					day = tokenvalue[0];
					month = tokenvalue[1];
					year = tokenvalue[2];
					if (day.length() == 1){day = "0" + day;}
					month = Month(month);
					date = year + month + day;
					string = string.replace(datemonthyearmatch.group(0), date);


					while (stream.hasNext()) {
						String temp2 = stream.next().toString();
						if (temp2 != null && temp2 != "") {
							if (temp2.equalsIgnoreCase(tokenvalue[0])) {stream.remove();}
							if (temp2.equalsIgnoreCase(tokenvalue[1])) {stream.remove();}
							if (temp2.equalsIgnoreCase(tokenvalue[2])) {stream.replace(date);}
						}
					}
				break;
				
			case "monthdateyear":
					
						tokenvalue = monthdateyearmatch.group(0).split(",");
						tokenvalue1 = tokenvalue[0].split(" ");
						month = tokenvalue1[0].trim();
						day = tokenvalue1[1].trim();
						year = tokenvalue[1].trim();
						month = Month(month);
						if (day.length() == 1)
							day = "0" + day;
						date = year + month + day;
						string = string.replace(monthdateyearmatch.group(0), date);
					
						stream.reset();
						while (stream.hasNext()) {
							String temp2 = stream.next().toString();
							if (temp2 != null && temp2 != "") {
								if (temp2.equalsIgnoreCase(tokenvalue1[0].trim())) {
									stream.remove();
								}
								if (temp2.equalsIgnoreCase(tokenvalue1[1].trim()
										+ ",")) {
									stream.remove();
								}
								if (temp2.equalsIgnoreCase(tokenvalue[1].trim()
										+ ",")) {
									stream.replace(date + ",");
								}
								if (temp2.equalsIgnoreCase(tokenvalue[1].trim())) {
									stream.replace(date);
								}
							}
						}
					
				
		case "adbcmatch":					
						String temp = adbcmatch.group(0);
						tokenvalue = temp.split(" ");
						int yeartemp2;
						String yeartemp = "";
						StringBuilder sb = new StringBuilder(temp.length());
						for (int i = 0; i < temp.length(); i++) {
							final char c = temp.charAt(i);
							if (c > 47 && c < 58) {
								sb.append(c);
							}
						}
						yeartemp2 = sb.toString().length();
						if (yeartemp2 == 1)
							yeartemp = "000";
						if (yeartemp2 == 2)
							yeartemp = "00";
						if (yeartemp2 == 3)
							yeartemp = "0";
						year = yeartemp + sb.toString();
						if (temp.contains("BC"))
							year = "-" + year;
						date = year + month + day;
						string = string.replace(adbcmatch.group(0), date);
						
						while (stream.hasNext()) {
							String temp2 = stream.next().toString();
							if (tokenvalue.length == 2) {
								if (temp2 != null && temp2 != "") {
									if (tokenvalue[1] != null) {
										if (temp2.equalsIgnoreCase(tokenvalue[0]
												.trim())) {
											stream.remove();
										}
										if (temp2.equalsIgnoreCase(tokenvalue[1]
												.trim())) {
											stream.replace(date);
										}
										if (temp2.equalsIgnoreCase(tokenvalue[1]
												.trim() + ".")) {
											stream.replace(date + ".");
										}
										if (temp2.equalsIgnoreCase(tokenvalue[1]
												.trim() + ",")) {
											stream.replace(date + ",");
										}
									}
								}
							}
							if (tokenvalue.length == 1) {
								if (temp2.equalsIgnoreCase(tokenvalue[0].trim())) {
									stream.replace(date);
								}
								if (temp2.equalsIgnoreCase(tokenvalue[0].trim()
										+ ".")) {
									stream.replace(date + ".");
								}
								if (temp2.equalsIgnoreCase(tokenvalue[0].trim()
										+ ",")) {
									stream.replace(date + ",");
								}
							}
						}

					
				break;
				
		case "yearonlymatch":
						date = yearonlymatch.group(0) + month + day;
						string = string.replace(yearonlymatch.group(0), date);
						
						while (stream.hasNext()) {
							String temp2 = stream.next().toString();
							if (temp2 != null && temp2 != "") {
								if (temp2.equalsIgnoreCase(yearonlymatch.group(0).trim())) {
									stream.replace(date);
								}
							}
						}

					
				break;

				case "monthdatematch":
					
						tokenvalue = monthdatematch.group(0).split(" ");
						month = Month(tokenvalue[0]);
						day = tokenvalue[1];
						date = year + month + day;
						string = string.replace(monthdatematch.group(0), date);
						

						while (stream.hasNext()) {
							String temp2 = stream.next().toString();
							if (temp2 != null && temp2 != "") {
								if (temp2.equalsIgnoreCase(tokenvalue[0])) {
									stream.remove();
								}
								if (temp2.equalsIgnoreCase(tokenvalue[1])) {
									stream.replace(date);
								}
								if (temp2.equalsIgnoreCase(tokenvalue[1] + ".")) {
									stream.replace(date + ".");
								}
								if (temp2.equalsIgnoreCase(tokenvalue[1] + ",")) {
									stream.replace(date + ",");
								}
							}
						}

					
				
			case "datemonthmatch":
					
						if (datemonthmatch.group(0).contains("am")
								|| datemonthmatch.group(0).contains("AM")
								|| datemonthmatch.group(0).contains("PM")
								|| datemonthmatch.group(0).contains("pm")) {
							
						} else {
							tokenvalue = datemonthmatch.group(0).split(" ");
							month = Month(tokenvalue[1]);
							day = tokenvalue[0];
							date = year + month + day;
							string = string.replace(datemonthmatch.group(0), date);
							

							while (stream.hasNext()) {
								String temp2 = stream.next().toString();
								if (temp2 != null && temp2 != "") {
									if (temp2.equalsIgnoreCase(tokenvalue[0])) {
										stream.remove();
									}
									if (temp2.equalsIgnoreCase(tokenvalue[1])) {
										stream.replace(date);
									}
									if (temp2.equalsIgnoreCase(tokenvalue[1] + ".")) {
										stream.replace(date + ".");
									}
									if (temp2.equalsIgnoreCase(tokenvalue[1] + ",")) {
										stream.replace(date + ",");
									}
								}
							}

						}
					
					stream.reset();
					
				break;

		
			}}
		return false;}
		
		
		

	@Override
	public TokenStream getStream() {
		return stream;
	}

}



