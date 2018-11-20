package newNgon;


import java.util.Vector;

import java.awt.*;

public abstract class GameObject {
	public Graphics g;
	int N ;
	public Vector<Point> P; // 좌표를 저장할 백터
	
	GameObject(  ){
	}
	abstract void draw ( Graphics g , int N , double r , double rotation , Color c1, Color c2);
	// 순수 가상 함수, 재정의 필요
	abstract void setPoint ( double r );
	// 순수 가상 함수, 재정의 필요
	final void rotate ( double greed ) { //회전 변환을 통해 중심점으로부터 임의의 각도만큼 회전한 점을 구한다.
		for( int n = 0 ; n < P.capacity() ; n++ ) {
			int tempX = P.get(n).x, tempY = P.get(n).y;
			P.get(n).x = (int) (Math.cos(((greed)*Math.PI/180))*( tempX -250 ) - Math.sin(((greed)*Math.PI/180))*( tempY -250 )) +250;
			P.get(n).y = (int) (Math.sin(((greed)*Math.PI/180))*( tempX -250 ) + Math.cos(((greed)*Math.PI/180))*( tempY -250 )) +250;
		}			
		
	}
	final void drawFill ( int [] x , int [] y , int  n , Color color ) {
		// 입력으로 들어온 좌표 배열을 폴리곤을 만들고 색을 넣어준다. 
		g.setColor( color );
		g.drawPolygon(x,y, n);
		g.fillPolygon(x,y, n);
	}

}
