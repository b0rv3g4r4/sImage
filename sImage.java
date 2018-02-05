import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.json.*;

public class sImage {
	private static String[] API = {"https://www.googleapis.com/customsearch/v1?key=yourkeyhere&cx=010854235945703695588:26juxmhvxum&num=1&q=cover+"};
	private String url;
	private BufferedImage image;
	
	
	public sImage(String titaut) {
		//Check of internet
		try{
		     URL myUrl = new URL("http://google.es");
		     URLConnection connection = myUrl.openConnection();
		     connection.connect();
		     JSONObject obj = readJsonFromUrl(API[0]+titaut); // search "cover + title + artist"
			 JSONArray arr = obj.getJSONArray("items");
				for (int i = 0; i < arr.length(); i++){
					JSONArray arr2 =arr.getJSONObject(i).getJSONObject("pagemap").getJSONArray("imageobject");
				    String link = arr2.getJSONObject(0).getString("url");
				    this.url = link;
				}
				try {
				    this.image = ImageIO.read(new URL(this.url));
				} catch (IOException e) {
					 image = null;
				     this.url = "";
				}
		 }catch (Exception e){
		     image = null;
		     this.url = "";
		 }  
	}
	
	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    try {
		    	URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent","Mozilla/5.0");
				InputStreamReader in = new InputStreamReader(con.getInputStream());
				BufferedReader rd = new BufferedReader(in);
				String jsonText = readAll(rd);
				JSONObject json = new JSONObject(jsonText);
		      		return json;
		    }catch(Exception e){
				e.printStackTrace();
			}
		  }
	
	
	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	
	public BufferedImage getImage() {
		return image;
	}
	
	

}
