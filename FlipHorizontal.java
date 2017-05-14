import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;
import acm.graphics.*;


public class FlipHorizontal extends GraphicsProgram {
	
	private GImage flipHorizontal(GImage image) {
		   int[][] array = image.getPixelArray();
		   int width = array[0].length;
		   for (int i = 0; i < array.length; i++) {
			   for (int p1 = 0; p1 < width / 2; p1++) {
				      int p2 = width - p1 - 1;
				      int temp = array[i][p1];
				      array[i][p1] = array[i][p2];
				      array[i][p2] = temp;
			   }
		   }
		   return new GImage(array);
		}

	
	public void run() {
		GImage myImage = new GImage("download.png");
		GImage flipped = flipHorizontal(myImage);
		add(myImage, 50, 50);
		add(flipped, 100 + myImage.getWidth(), 50);
	}
}