import java.net.*;
import java.io.*;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PriceGrabber {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int size;
		String answer, product, brand, clearity, type;

		System.out.println("would you like to find a great deal on a TV?");
		answer = scan.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
			// Prompt user for TV specs
			System.out.println("Choose a size category: >=30inches, 31inches-39inches, 40inches - 49inches, 50inches-59inches, 60inches - 69inches, <=70inches");
			size = scan.nextInt();
			System.out.println("Choose perferred clearity: 1080P,720P, no preference");
			clearity = scan.next();
			System.out.println("Choose perferred brand: Toshiba,Panasonic, Sharp, LG, Toshiba, Element, Vizio, Philips, Sceptre, JVC, RCA, Westinghouse, Dynex, Magnavox, Samsung, Viore, no preference");
			brand = scan.next();
			
			// Build URL based on specifications given by user
			String query = "";
			try {
				query = URLEncoder.encode(brand + size + " inch " + clearity + " tv", "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Get top web result from amazon and print it
			String amazonUrl = "http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=" + query;
			System.out.println(amazonUrl);
			String response = getWebPage(amazonUrl);
			//System.out.println(response);
			
			// Parse html and find first amazon result
			Document doc = Jsoup.parse(response);
			Element e = doc.getElementById("result_0");
			String nodeValue = e.html();
			System.out.println(nodeValue);
			
			AmazonResult result = new AmazonResult(e);
			result.printBasicInfo();
			
			// Get basic info from result
			//e.getElementById();
		}
	}
	
	public static String getWebPage(String url) {
		URL myURL;
		StringBuilder response = new StringBuilder();
		String line;

		try {
			myURL = new URL(url);
			URLConnection myURLConnection = myURL.openConnection();
			myURLConnection.connect();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					myURLConnection.getInputStream()));

			while ((line = rd.readLine()) != null) {
				response.append(line + "\n");
			}
			rd.close();

			return response.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.toString();
	}
}