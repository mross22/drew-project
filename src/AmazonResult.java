import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AmazonResult {
	private Element result;
	public float newPrice;
	public float usedPrice;
	public String imgUrl;
	public String productName;
	public String productUrl;
	
	public AmazonResult(Element result){
		this.result = result;
		parseResult(); 
	}
	
	private void parseResult(){
		parsePrices();
		parseTitle();
		
	}
	
	private void parseTitle(){
		Elements productTitle = result.getElementsByClass("productTitle");
		if(productTitle.size() > 0){
			Elements title = productTitle.first().getElementsByTag("a");
			if(title.size() > 0){
				productUrl = title.first().attr("href");
				productName = title.first().html();
			}
		}
		
		if(productTitle.size() > 0){
			Elements title = productTitle.first().getElementsByTag("a");
			productUrl = title.first().html();
		}
	}
	
	private void parsePrices()
	{
		Elements newPrices = result.getElementsByClass("newPrice");
		Elements usedPrices = result.getElementsByClass("usedNewPrice");
		
		if(newPrices.size() > 0){
			Elements prices = newPrices.get(0).getElementsByTag("span");
			if(prices.size() > 0){
				newPrice = Float.parseFloat(formatPrice(prices.first().html()));
			}
		}
		
		if(usedPrices.size() > 0){
			Elements subPrice = usedPrices.get(0).getElementsByClass("subPrice");
			if(subPrice.size() > 0){
				Elements prices = subPrice.first().getElementsByTag("span");
				if(prices.size() > 0){
					if(subPrice.first().html().contains("used"))
						usedPrice = Float.parseFloat(formatPrice(prices.first().html()));
					else if(subPrice.first().html().contains("new")){
						newPrice = Float.parseFloat(formatPrice(prices.first().html()));
					}
				}
			}
		}
	}
	public String formatPrice(String price){
		if(price.length() > 0){
			if(price.charAt(0) == '$')
				price = price.substring(1);
			
			if(price.contains(",")){
				StringBuilder sb = new StringBuilder();
				for(char c : price.toCharArray()){
					if(c != ',')
						sb.append(c);
				}
				
				price = sb.toString();
			}
		}
		
		return price;
	}
	
	public void printBasicInfo(){
		System.out.println("Product: " + productName);
		if(productUrl != null && productUrl.length() > 0)
			System.out.println("\tProduct Url: " + productUrl);
		System.out.println("\tNew Price: " + ((newPrice == 0) ? "Not found" : newPrice));
		System.out.println("\tUsed price: " + ((usedPrice == 0) ? "Not found" : usedPrice));
	}
}
