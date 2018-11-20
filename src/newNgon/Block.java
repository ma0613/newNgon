package newNgon;
import java.awt.*;
import java.util.Vector;

import javax.swing.*;

public class Block extends GameObject {

	int [] isBlock = new int[ 100 ];// ������� ����� �������� ǥ��
	double BlockCirR = 0;
	
	int blockStartPos = 500 ;
	
	double currentSpead = 0; 

	Color blockColor =  new Color( 254 ,251 , 208 );
	Color blockIdleColor =  new Color( 254 ,251 , 208 );
	Color blockHitColor =  new Color( 119 , 93 , 106 );
	
	Block( ) {
		super( );
		P = new Vector<Point>(200);
		for( int  i = 0 ; i < 200 ; i++ ) {
			P.add(new Point());
		}
	}
	void draw ( Graphics g , int N , double r , double rotation , Color c1, Color c2) {
		this.g =g;
		this.N = N;
		// �⺻ ���� �浹�� ���� ����
		blockIdleColor = c1;
		blockHitColor = c2;
		
		setPoint(r);
		rotate(rotation);
		drawFillBlock(r);
		
	}
	
	void setPoint ( double r ) {
		// �������� ���̸�ŭ ��ǥ ����
		double radian = ((360/N)*Math.PI)/180 ;
		for( int n = 0 ; n  < N ; n++ ) {		
			P.get(n).x = (int) ( Math.cos( n* radian ) *r )+250 ;
			P.get(n).y = (int) ( Math.sin( n* radian ) *r )+250 ;
		}
		for( int n = N ; n  < 2*N ; n++ ) {		
			P.get(n).x = (int) ( Math.cos( n* radian ) * ( r + (int) ( Math.random() * 20 ) + 20 ))+250 ;
			P.get(n).y = (int) ( Math.sin( n* radian ) * ( r + (int) ( Math.random() * 20 ) + 20 ))+250 ;
			
		}
	}


	void drawFillBlock(double r) {
		// ��ǥ���� ĥ����
		if( r > 40 ) {

			for( int n = 0 ; n < N ; n++ ) {
				if(isBlock[n] == 0) { // ����� ���� ���̶�� �Ѿ
					continue;
				}
				int [] pX = { P.get(n).x , P.get(n+N).x , P.get((n+1)%N + N ).x , P.get((n+1)%N).x};
				int [] pY = { P.get(n).y , P.get(n+N).y , P.get((n+1)%N + N ).y , P.get((n+1)%N).y};
				
				drawFill( pX , pY , 4 ,  blockColor );

			}
		}
		
	}
	
	void randomBlock () { // �浹�� �� �ִ� ����� N-1�� ��ŭ ����
		for( int n = 0 ; n < N ; n++ )
			isBlock[n] = 0; 
		for( int n = 0 ; n < N-1 ; n++ ) 
			isBlock[(int) ( Math.random() * N )] = 1; 
	}
	
	
	
}
