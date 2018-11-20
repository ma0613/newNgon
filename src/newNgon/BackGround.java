package newNgon;

import java.awt.*;
import java.util.Vector;

public class BackGround extends GameObject {

	BackGround() {
		P = new Vector<Point>(100);
		for( int  i = 0 ; i < 100 ; i++ ) {
			P.add(new Point());
		}
	}
	
	
	void draw ( Graphics g , int N , double r , double rotation , Color c1, Color c2) {
		this.g =g;
		this.N = N;
		setPoint(r);
		rotate(rotation);
		backGroundDraw(c1,c2);
		
	}

	void setPoint(double r) {
		double radian = ((360/N)*Math.PI)/180 ;
		for( int n = 0 ; n  < N ; n++ ) {		
			P.get(n).x = (int) ( Math.cos( n* radian ) *r )+250 ;
			P.get(n).y = (int) ( Math.sin( n* radian ) *r )+250 ;
		}
	}
	
	void backGroundDraw( Color color1 , Color color2 ) {
		for( int n = 0 ; n  < N ; n++ ) {
			
			int [] triangleX = { 250 ,P.get(n).x , P.get((n+1)%N).x};
			int [] triangleY = { 250 ,P.get(n).y , P.get((n+1)%N).y};

			drawFill( triangleX , triangleY , 3 ,  n%2 == 0 ? color1 :  color2 );

		}
	}
	
	
}
