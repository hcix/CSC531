package program;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import utilities.ui.ImageHandler;

public class Splash extends Frame implements ActionListener {

    BufferedImage splashImg = ImageHandler.getBufferedImage("images/splashScreen.png",500,250);
	
	public Splash() {
        setSize(500, 250);
        setLayout(new BorderLayout());
        Menu m1 = new Menu("File");
        MenuItem mi1 = new MenuItem("Exit");
        m1.add(mi1);
        mi1.addActionListener(this);
        this.addWindowListener(closeWindow);
 
        MenuBar mb = new MenuBar();
        setMenuBar(mb);
        mb.add(m1);
        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        g.drawImage(splashImg,0,0,null);
        splash.close();
        setVisible(true);
        toFront();
    }
    public void actionPerformed(ActionEvent ae) {
        System.exit(0);
    }
     
    private static WindowListener closeWindow = new WindowAdapter(){
        @Override
		public void windowClosing(WindowEvent e){
            e.getWindow().dispose();
        }
    };
    
}