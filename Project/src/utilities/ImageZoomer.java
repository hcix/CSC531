package utilities;


public class ImageZoomer {

	/*	public Image ZoomIn(Image img) {
    
	Image outImg = null;
		int[] rgbOutput = null;
    
		Graphics g = null;
		int width = 0,height = 0;
    
		try{       
			width = img.getWidth();
			height = img.getHeight();
        outImg  = Image.createImage(height,width);
        int rgbInput[]=new int[width*height];
        rgbOutput = new int[width*height];
        img.getRGB(rgbInput,0,width,0,0,width,height);
        
        int i,j,k,l;
        k=0;
        int tempArr[][]  = new int[height][width];
        
        for(i=0;i<height;i++)
            for(j=0;j<width;j++)
                tempArr[i][j] = rgbInput[k++];
        
        k=0;
        for(i=0;i<height;i++) {
            for(j=0;j<width;j++) {
                rgbOutput[k]= tempArr[j][i];
                k++;
            }
        }
        g = outImg.getGraphics();
        
    } catch(Exception e){}
    
    return outImg.createRGBImage(rgbOutput,height,width,true);
}

public Image zoomImage(Image img) {
    
    image outImg = null;
    
    int[] rgbOutput = null;
    Graphics g = null;
    int width = 0,height = 0;
    
    try {
        
        width  =  img.getWidth();
        height = img.getHeight();
        
        outImg  = Image.createImage(width<<1,height<<1);
        
        int rgbInput[]=new int[width*height];
        rgbOutput = new int[(width<<1)*(height<<1)];
        
        img.getRGB(rgbInput,0,width,0,0,width,height);
        
        int i,j,k;
        k=0;
        for(i=0;i<(height<<1);i+=2) {
            for(j=0;j<(width<<1);j+=2) {
                rgbOutput[i*(width<<1) + j] = rgbInput[k] ;
                rgbOutput[(i+1)*(width<<1) + j]  = rgbInput[k];
                rgbOutput[i*(width<<1) + j+1]  = rgbInput[k];
                rgbOutput[(i+1)*(width<<1) + j+1]  = rgbInput[k];
                k++;
            }
        }
        
        g = outImg.getGraphics();
    } catch(Exception e){}
    
    return outImg.createRGBImage(rgbOutput,width<<1,height<<1,true);*/
}

