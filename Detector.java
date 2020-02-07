import java.util.ArrayList;

import java.util.Comparator;
import java.util.Collections;
import java.util.AbstractCollection;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

import javax.swing.JPanel;

/**
 * The implementation of a color blob Detector.
 * The program detects colored "blobs" in an image.
 * @author Quang Vo
 */
public class Detector extends JPanel {
	//
	// Task 2.1: get the difference between two colors (5%)
	// Determines the distance between two colors
	// as a value between 0 and 100.
	//
	/**
	 * Get the distance between two colors as a value between 0 and 100.
	 * @param c1 the first color.
	 * @param c2 the second color.
	 * @return the distance between two colors.
	 * @throws NullPointerException if any of the colors is null
	 */
	public static int getDifference(Color c1, Color c2) {
		//TODO: Your code here...
		//Hint: Read the color class documentation
		//for useful methods and static variables:
		//https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
		
		if(c1 == null || c2 == null){ //check if any of the colors is null
			throw new NullPointerException();
		}
		
		final int difference_255 = (int) Math.pow(255, 2) * 3; // (255^2) * 3 = 195075
		int diff_red = 0;
		int diff_green = 0;
		int diff_blue = 0;
		int color_difference_100 = 0;
								
		diff_red = c1.getRed() - c2.getRed(); //difference in red
		diff_green = c1.getGreen() - c2.getGreen(); //difference in green
		diff_blue = c1.getBlue() - c2.getBlue(); //difference in blue
		
		//difference between two colors in scale between 0 and 100
		color_difference_100 = (int) Math.floor(((Math.pow(diff_red, 2) + Math.pow(diff_green, 2) + Math.pow(diff_blue, 2)) * 100 / difference_255));
		
		return color_difference_100; 
	}
	
	//
	// Task 2.2: Threshold the pixels and recolor them (10%)
	// 
	// Color the pixels white (if the pixel is not color we want)
	// or black (if it's the color we want). okDist indicates the
	// acceptable "distance" between the pixel and the color c
	// (inclusive).
	//
	/**
	 * Threshold the pixels and recolor them.
	 * Color the pixels white if the pixel is not the color we want.
	 * Color the pixels black if the pixel is the color we want.
	 * @param image the original image.
	 * @param c the color we are detecting.
	 * @param okDist indicates the distance from c that is still considered the color.
	 * @throws IllegalArgumentException if okDist is not in range from 0 to 100.
	 */
	public static void thresh(BufferedImage image, Color c, int okDist) {
		int width = image.getWidth();
		int height = image.getHeight();
		//TODO: Your code here...
		//Hint: You would use image.setRGB(x,y,new_color.getRGB())
		//to change the color of a pixel (x,y) to a new color new_color				
		
		if(okDist < 0 || okDist > 100){ //okDist must be in range from 0 to 100
			throw new IllegalArgumentException();
		}
						
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				int pixelId = getId(image, x, y); //get the id
				Pixel newPixel = getPixel(image, pixelId); //get the pixel associated with the id
				Color pixelColor = getColor(image, newPixel); //get the color of the pixel
				
				int colorDifference = getDifference(pixelColor, c); //get the color difference
				
				if(colorDifference <= okDist){ //inclusive
					image.setRGB(x, y, Color.black.getRGB()); //color it black if it's the color we want
				}
				else{
					image.setRGB(x, y, Color.white.getRGB()); //color it white if it's not the color we want
				}
												
			}
		}
	}
	
	//
	// Task 3: Get the neighboring sets (5%)
	//
	// Given an image, a disjoint set, and a pixel (defined by its id),
	// return a pair which contains (a) the blob above and
	// (b) the blob to the left (each represented by their _root_ ids)
	//
	// If there is no above/left neighbor, then the appropriate
	// part of the pair should be null
	/**
	 * Get the neighboring sets (above and left sets).
	 * @param image the given image.
	 * @param ds the given disjoint set.
	 * @param pixelId the given pixel defined by its id
	 * @return a pair which contains (a) the root id of the blob above and (b) the root id of the blob to the left.
	 */
	public static Pair<Integer,Integer> getNeighborSets(BufferedImage image, DisjointSets<Pixel> ds, int pixelId) {
		//TODO: Your code here...
				
		Integer rootIdAbove = 0; //root of above blob
		Integer rootIdLeft = 0; //root of left blob
		
		//get above root id
		if((pixelId - image.getWidth()) < 0){
			rootIdAbove = null;
		}
		else{
			int abovePixelId = pixelId - image.getWidth();
			rootIdAbove = ds.find(abovePixelId); //find the root of the above blob
		}
					
		//get left root id
		if(pixelId % image.getWidth() == 0){
			rootIdLeft = null;
		}
		else{
			int leftPixelId = pixelId - 1;		
			rootIdLeft = ds.find(leftPixelId); //find the root of the left blob
		}
				
		Pair<Integer, Integer> toReturn = new Pair<>(rootIdAbove, rootIdLeft);
				
		return toReturn; 
	}

	//
	// Task 4. Implement the detect algorithm (45%)
	//
	// You must use instance variables img, blobColor, okDist, ds, etc.
	//
	/**
	 * The implementation of the detect algorithm.
	 * Threshold the image, initialize DS data structure,
	 * Then walk through the image and perform finds and unions where appropriate.
	 */
	public void detect() {
		//TODO: Your code here...
		
		//threshold the image
		
		//make your DS data structure
		
		//walk through the image and perform
		//finds and unions where appropriate

		//Hint: the algorithm is not fast and you are processing many pixels 
		//	  (e.g., 10,000 pixel for a small 100 by 100 image)
		//	  you may want to output a "." every 100 unions so you
		//	  get some progress updates.
		//If processing seems to be taking a ridiculous amount of time,
		//you probably did either rank-union or path compression incorrectly.
		
		//After this, the instance variable this.ds should contain your color blobs
		//(and non-color areas) for this.img
				
		thresh(this.img, this.blobColor, this.okDist); //threshold the image
		
		ArrayList<Pixel> pixelList = new ArrayList<Pixel>(); //create a new list of pixels
		
		for(int i = 0; i < img.getHeight() * img.getWidth(); i++){
			Pixel p = getPixel(img, i);
			pixelList.add(p); //add all pixels to the list
		}
				
		this.ds = new DisjointSets<Pixel>(pixelList); //create new disjoint sets
				
		for(int j = 0; j < img.getHeight() * img.getWidth(); j++){
				
			Pixel currentPixel = getPixel(img, j); //get current each pixel
			
			Color pixelColor = getColor(img, currentPixel); //get pixel color
			
			Pair<Integer, Integer> neighbors = getNeighborSets(this.img, this.ds, j); //get above and left blobs' roots
						
			//check if has above pixel
			if(neighbors.a != null){
								
				Pixel abovePixel = getPixel(img, neighbors.a); //get above blob's root pixel
				Color aboveColor = getColor(img, abovePixel); //get above blob color

				if(getDifference(pixelColor, aboveColor) == 0){ //check the difference of current and above colors
					int currentPixelRoot2 = ds.find(j); //get the root of current pixel
					ds.union(currentPixelRoot2, neighbors.a); //union them if same color
				}
			}
				
			//check if has left pixel
			if(neighbors.b != null){
				
				Pixel leftPixel = getPixel(img, neighbors.b); //get left blob's root pixel
				Color leftColor = getColor(img, leftPixel); //get left blob color
				
				if(getDifference(pixelColor, leftColor) == 0){		
					int currentPixelRoot = ds.find(j); //get the root of the current pixel
					ds.union(currentPixelRoot, neighbors.b); //union them if same color				
				}
			}
		}
	}

	//
	// Task 5: Output results (10%)
	// Recolor all in the k largest blobs and save output
	//
	// You must use instance variables img, blobColor, okDist, ds, etc.
	//
	/**
	 * Recolor all pixels in the k largest blobs and save output.
	 * @param outputFileName the name for the output image.
	 * @param outputECFileName the name for the output image (extra credit).
	 * @param k the number of largest blobs to be recolored.
	 * @throws IllegalArgumentException when the given k is less than 1.
	 */
	public void outputResults(String outputFileName, String outputECFileName, int k) {
		if(k<1) {
			throw new IllegalArgumentException(new String("! Error: k should be greater than 0, current k="+k));
		}
		
		//Todo: Your code here (remove this line)
		
		//get all the roots from the DS
		//Hint: You can use TreeSet<> here if you want, it's a set supported by a red-black tree
		
		//using the roots, collect all sets of pixels and sort them by size
		//Note: you may use Collections.sort() here if you want
		
		//recolor the k-largest blobs from black to a color from getSeqColor()
		//Hint: Use image.setRGB(x,y,c.getRGB()) to change the color of a pixel (x,y) to the given color "c"
		
		//and output all blobs to console
		
		TreeSet<Integer> rootsSet = new TreeSet<Integer>(); //sorted, ascending order, no duplicates
				
		for(int i = 0; i < img.getHeight() * img.getWidth(); i++){ //get all the roots
			int rootId = ds.find(i);
			Pixel newPixel = getPixel(img, rootId);
			Color pixelColor = getColor(img, newPixel);
			if(getDifference(pixelColor, Color.white) != 0){
				rootsSet.add(rootId); //add all roots to the root set, excludes white pixels.
			}
			
		}
				
		ArrayList<Set<Pixel>> listOfSets = new ArrayList<Set<Pixel>>(); //list of sets of pixels
						
		int numberOfRoots = rootsSet.size(); 
		
		for(int j = 0; j < numberOfRoots; j++){ 
			int root = rootsSet.pollFirst();
			Set<Pixel> newSet = ds.get(root);
			listOfSets.add(newSet); //add each set of pixels into list of Sets
		}
				
		Collections.sort(listOfSets, new Comparator<Set<Pixel>>(){ //sort all the sets by size
			/**
			 * Anonymous Comparator class.
			 */
			public int compare(Set<Pixel> s1, Set<Pixel> s2){
				return s2.size() - s1.size();
			}
		});
				
		if(k >= listOfSets.size()){
			k = listOfSets.size(); //if less than k blobs found, then the total number of blobs
		}
		
		System.out.println(k + "/" + listOfSets.size()); //print k over number of blobs
		
		for(int i = 0; i < k; i++){
			Color c = this.getSeqColor(i, k); //get various colors for the k-biggest blobs.
			
			System.out.println("Blob " + (i+1) + ": " + listOfSets.get(i).size() + " pixels"); //print number of pixels in each blob
			
			for(int x = 0; x < img.getWidth(); x++){
				for(int y = 0; y < img.getHeight(); y++){
					
					int pixelId = getId(img, x, y); //get the pixel id
					int pixelRoot = ds.find(pixelId); //get the root of the pixel
					Set<Pixel> newSet = ds.get(pixelRoot); //get the set associated with the root
					
					if(newSet.size() == listOfSets.get(i).size()){ //if same size, recolor the blob
						img.setRGB(x, y, c.getRGB());
					}
				}
			}
		}
		
		//save output image -- provided
		try {
			File ouptut = new File(outputFileName);
			ImageIO.write(this.img, "png", ouptut);
			System.err.println("- Saved result to "+outputFileName);
		}
		catch (Exception e) {
			System.err.println("! Error: Failed to save image to "+outputFileName);
		}
		
		
		/*EC starts here*/
		
		//reloadImage();
		
		
		
		/*
		//if you're doing the EC and your output image is still this.img,
		//you can uncomment this to save this.img to the specified outputECFileName
		try {
			File ouptut = new File(outputECFileName);
			ImageIO.write(this.img, "png", ouptut);
			System.err.println("- Saved result to "+outputECFileName);
		}
		catch (Exception e) {
			System.err.println("! Error: Failed to save image to "+outputECFileName);
		}
		*/
	}
	
	//main method just for your testing
	//edit as much as you want
	/**
	 *  A main method to test/demo
	 *  @param args not used
	 */
	public static void main(String[] args) {
		
		//System.out.println(Color.red.getRGB());
						
		//getDifference(Color.red, Color.black); //test getDifference method
				
		//Some stuff to get you started...
		
		File imageFile = new File("../input/04_Circles.png");
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(imageFile);
		}
		catch(IOException e) {
			System.err.println("! Error: Failed to read "+imageFile+", error msg: "+e);
			return;
		}
		
		Pixel p = getPixel(img, 110); //100x100 pixel image, pixel id 110
		System.out.println(p.a); //x = 10
		System.out.println(p.b); //y = 1
		System.out.println(getId(img, p)); //gets the id back (110)
		System.out.println(getId(img, p.a, p.b)); //gets the id back (110)
		
		//Pixel p1 = getPixel(img, 2030);
		
//		System.out.println(p1.a);
//		System.out.println(p1.b);
		
//		Color pixelColor = getColor(img, p1);
//		
//		System.out.println(pixelColor);
		
//		thresh(img, Color.blue, 30);
		
//		Detector newDetector = new Detector("../input/04_Circles.png", Color.blue, 5);
//		newDetector.detect();
//		
//		newDetector.outputResults("hi", "hi", 5);

//		Pair<Integer, Integer> newPair = getNeighborSets(img, test, 10);
//		
//		System.out.println(newPair.a);
//		System.out.println(newPair.b);
		
	}

	//-----------------------------------------------------------------------
	//
	// Todo: Read and provide comments, but do not change the following code
	//
	//-----------------------------------------------------------------------

	//Data
	public BufferedImage img;        //this is the 2D array of RGB pixels
	private Color blobColor;         //the color of the blob we are detecting
	private String imgFileName;      //input image file name
	private DisjointSets<Pixel> ds;  //the disjoint set
	private int okDist;              //the distance between blobColor and the pixel which "still counts" as the color

	// constructor, read image from file
	/**
	 * Read an image from file with given detecting blob color and ok distance.
	 * @param imgfile the name of the image.
	 * @param blobColor the color of the blob we are detecting.
	 * @param okDist the distance between blobColor and the pixel which "still counts" as the color.
	 */
	public Detector(String imgfile, Color blobColor, int okDist) {
		this.imgFileName=imgfile;
		this.blobColor = blobColor;
		this.okDist = okDist;
		
		reloadImage();
	}

	// constructor, read image from file
	/**
	 * Read an image from file.
	 */
	public void reloadImage() {
		File imageFile = new File(this.imgFileName);
		
		try {
			this.img = ImageIO.read(imageFile);
		}
		catch(IOException e) {
			System.err.println("! Error: Failed to read "+this.imgFileName+", error msg: "+e);
			return;
		}
	}

	// JPanel function
	/**
	 * JPanel function to paint an image.
	 */
	public void paint(Graphics g) {
		g.drawImage(this.img, 0, 0,this);
	}

	//private classes below

	//Convenient helper class representing a pair of things
	/**
	 * Convenient helper class representing a pair of things.
	 * @param <A> first item in the pair.
	 * @param <B> second item in the pair.
	 */
	private static class Pair<A,B> {
		A a;
		B b;
		public Pair(A a, B b) {
			this.a=a;
			this.b=b;
		}
	}

	//A pixel is a set of locations a (aka. x, distance from the left) and b (aka. y, distance from the top)
	/**
	 * A pixel class which represents a set of locations
	 * a (aka. x, distance from the left) and b (aka. y, distance from the top).
	 */
	private static class Pixel extends Pair<Integer, Integer> {
		public Pixel(int x, int y) {
			super(x,y);
		}
	}

	//Convert a pixel in an image to its ID
	/**
	 * Convert a pixel in an image to its ID.
	 * @param image the original image.
	 * @param p the given pixel.
	 * @return the id of the pixel.
	 */
	private static int getId(BufferedImage image, Pixel p) {
		return getId(image, p.a, p.b);
	}

	//Convex ID for an image back to a pixel
	/**
	 * Convert an id for an image to a pixel.
	 * @param image the original image.
	 * @param id the id of the pixel.
	 * @return the pixel associated with the given id.
	 */
	private static Pixel getPixel(BufferedImage image, int id) {
		int y = id/image.getWidth(); 
		int x = id-(image.getWidth()*y);

		if(y<0 || y>=image.getHeight() || x<0 || x>=image.getWidth())
			throw new ArrayIndexOutOfBoundsException();

		return new Pixel(x,y);
	}

	//Converts a location in an image into an id
	/**
	 * Convert a location in an image into an id.
	 * @param image the original image.
	 * @param x the distance from the left.
	 * @param y the distance from the top.
	 * @return the id at the location.
	 */
	private static int getId(BufferedImage image, int x, int y) {
		return (image.getWidth()*y)+x;
	}

	//Returns the color of a given pixel in a given image
	/**
	 * Return the color of a given pixel in a given image.
	 * @param image the original image.
	 * @param p the given pixel.
	 * @return the color of a given pixel.
	 */
	private static Color getColor(BufferedImage image, Pixel p) {
		return new Color(image.getRGB(p.a, p.b));
	}
	
	//Pass 0 -> k-1 as i to get the color for the blobs 0 -> k-1
	/**
	 * Pass 0 -> k-1 as i to get the color for the blobs 0 -> k-1.
	 * The k-biggest blobs will be various colors.
	 * @param i the starting blob.
	 * @param max the maximum number of blobs.
	 * @return various colors for the k-biggest blobs.
	 */
	private Color getSeqColor(int i, int max) {
		if(i < 0) i = 0;
		if(i >= max) i = max-1;
		
		int r = (int)(((max-i+1)/(double)(max+1)) * blobColor.getRed());
		int g = (int)(((max-i+1)/(double)(max+1)) * blobColor.getGreen());
		int b = (int)(((max-i+1)/(double)(max+1)) * blobColor.getBlue());
		
		if(r == 0 && g == 0 && b == 0) {
			r = g = b = 10;
		}
		else if(r == 255 && g == 255 && b == 255) {
			r = g = b = 245;
		}
		
		return new Color(r, g, b);
	}
}
