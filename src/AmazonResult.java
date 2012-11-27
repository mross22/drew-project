import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AmazonResult {
	private Element result;
	public float newPrice;
	public float usedPrice;
	
	public AmazonResult(Element result){
		this.result = result;
		setPrice(); 
	}
	
	private void setPrice(){
		Elements prices = result.getElementsByClass("newPrice");
		newPrice = Float.parseFloat(prices.get(0).getElementsByTag("span").first().html());
	}
	
	public float getPrice(){
		return newPrice;
	}
	
	public void printBasicInfo(){
		System.out.println("New Price: " + newPrice);
	}
}
