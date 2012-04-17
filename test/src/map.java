import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class map {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URLConnection con = new URL("http://maps.googleapis.com/maps/api/staticmap?center=University+of+miami,miami&zoom=15&size=2160x2160&markers=color:blue%7Clabel:S%7C11211%7C11206%7C11222&sensor=true").openConnection();
		InputStream is = con.getInputStream();
		byte bytes[] = new byte[con.getContentLength()];
		is.read(bytes);
		is.close();
		Toolkit tk = getToolkit();
		map = tk.createImage(bytes);
		tk.prepareImage(map, -1, -1, null);
		
	}

}
