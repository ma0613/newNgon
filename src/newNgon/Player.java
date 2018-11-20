package newNgon;
import java.awt.*;
import java.util.Vector;

public class Player extends GameObject{
	int pos;
	boolean hit = false;
	Player( int N ) {
		super();
		pos = N;
		P = new Vector<Point>(3);
		for( int  i = 0 ; i < 3 ; i++ ) {
			P.add(new Point());
		}
	}
	
	void draw ( Graphics g , int N , double r , double rotation , Color c1, Color c2) {
		// 삼각형의 플레이어, 회전 각도를 받아 그 만큼 회전 시킴
		this.g =g;
		this.N = N;
		setPoint(r);
		rotate( pos*(360/N) -(180/N) + rotation);
		int [] playerX = { P.get(0).x , P.get(1).x, P.get(2).x};
		int [] playerY = { P.get(0).y , P.get(1).y, P.get(2).y};
		drawFill( playerX , playerY , 3 , hit ? c2 : c1 );
	}

	void setPoint ( double r )  { 
		int _r = (int) r;
		P.get(0).x = 320; P.get(0).y = 250; 
		P.get(1).x = 300; P.get(1).y = (int) (250 - ( _r / Math.sqrt(3.0) )); 
		P.get(2).x = 300; P.get(2).y = (int) (250 + ( _r / Math.sqrt(3.0) )); 
		
	}
}
