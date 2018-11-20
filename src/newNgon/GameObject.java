package newNgon;


import java.util.Vector;

import java.awt.*;

public abstract class GameObject {
	public Graphics g;
	int N ;
	public Vector<Point> P; // ��ǥ�� ������ ����
	
	GameObject(  ){
	}
	abstract void draw ( Graphics g , int N , double r , double rotation , Color c1, Color c2);
	// ���� ���� �Լ�, ������ �ʿ�
	abstract void setPoint ( double r );
	// ���� ���� �Լ�, ������ �ʿ�
	final void rotate ( double greed ) { //ȸ�� ��ȯ�� ���� �߽������κ��� ������ ������ŭ ȸ���� ���� ���Ѵ�.
		for( int n = 0 ; n < P.capacity() ; n++ ) {
			int tempX = P.get(n).x, tempY = P.get(n).y;
			P.get(n).x = (int) (Math.cos(((greed)*Math.PI/180))*( tempX -250 ) - Math.sin(((greed)*Math.PI/180))*( tempY -250 )) +250;
			P.get(n).y = (int) (Math.sin(((greed)*Math.PI/180))*( tempX -250 ) + Math.cos(((greed)*Math.PI/180))*( tempY -250 )) +250;
		}			
		
	}
	final void drawFill ( int [] x , int [] y , int  n , Color color ) {
		// �Է����� ���� ��ǥ �迭�� �������� ����� ���� �־��ش�. 
		g.setColor( color );
		g.drawPolygon(x,y, n);
		g.fillPolygon(x,y, n);
	}

}
