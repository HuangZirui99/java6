
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.time.ZonedDateTime;
import static java.lang.Math.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.time.temporal.ChronoUnit;
public class Track extends Point{
	ArrayList<Point> track = new ArrayList<Point>(); 
	Track(){
		
	}
	void readFile(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner input = new Scanner(file);
		if(!file.exists()) {
			throw new FileNotFoundException("the file not exist");}
		else {
			String first = input.nextLine();
			while (input.hasNextLine()) 
			{
				String string = input.nextLine();
				String[] result=string.split(",");
				if(result.length<4) {
					throw new GPSException("less data");}
				else {
					Point point= new Point(ZonedDateTime.parse(result[0]),Double.parseDouble(result[1]),Double.parseDouble(result[2]),Double.parseDouble(result[3]));
				add(point);}}
			}
		
			input.close();
		}
	public void add (Point point){
		track.add(point);
	}
	public int size() {
			int num = track.size();
			 for (int i = 0; i < track.size(); i++) {
		            for (int j = i + 1; j < track.size(); j++) {
		                if (track.get(i).elevation == track.get(j).elevation && track.get(i).longitude == track.get(j).longitude && track.get(i).latitude == track.get(j).latitude){
		                    num = num - 1;
					break;
		                }
		            }
		        }
		return num;
	}
	public Point get(int index) throws GPSException{
		if(index<0) throw new GPSException("can not get index ");
		if(index>=track.size()) throw new GPSException(" index out of range  ");
		return track.get(index);
	}
	public Point lowestPoint() {
		if(track.size()<1) throw new GPSException("size not enough");
		Point low=new Point();
		low=get(0);
		for ( int i = 0; i < track.size() - 1; i++) {
			if(get(i+1).elevation<low.elevation) {
				low=get(i+1);
			}
		}
		return low;
	}
	public Point highestPoint() {
		if(track.size()<1) throw new GPSException("size not enough");
		double highestPoint=track.get(0).elevation;
		int j = 0;
		for (int i = 0; i < track.size() - 1; i++) {
			if(track.get(i).elevation>highestPoint) {
				highestPoint=track.get(i).elevation;
				j = i;
			}
		}
		return track.get(j);
	}
	
	public double totalDistance() {
		if(track.size()<2) throw new GPSException("size not enough");
		double totaldis=0;
		double Dis=0;
		for(int i=0;i<track.size() - 1;i++) {
			Point p=track.get(i);
			Point q=track.get(i+1);
			Dis=Point.greatCircleDistance(p,q);
			totaldis+=Dis;
		}
		return totaldis;	
	}
	public double averageSpeed() {
		if(track.size()<2) throw new GPSException("size not enough");
		double averagespeed=0;
		double totaldistance=totalDistance();
		double totalTime=0;
		Point p=track.get(0);
		Point q=track.get(track.size()-1);
		totalTime=ChronoUnit.SECONDS.between(p.time,q.time);
		averagespeed=totaldistance/totalTime;
		return averagespeed;
	}
		
	
	
}	
