package newNgon;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;

public class GameManager extends Thread {

	Panel panel;
	Thread th;
	
	int N = 6;
	double time = 0.0; //플레이 타임
	double rotation = 0; 
	double direction = 0;
	double rotationSpeed = 2;
	
	private BackGround bg;
	private BackGround ct;

	private Player pl;  
	
	BlockCrtl blCrtl;
	
	
	boolean moveLeft , moveRight , plus , minus , start , isPlaying , levelUp , levelDown , pause , isPlayerDie;
	
	int level = 0;
	int blockNum = 1;
	
	SoundManager sm = new SoundManager();

	private int [] plOriColor = { 255 , 255 , 255 };
	private int [] plHitColor = { 145,185,168 };
	
	private int [] ctOriColor = { 253,207,183 };
	private int [] ctHitColor = { 253,207,183 };
	
	private int [] bg1Color  = { 145,185,168 };
	private int [] bg2Color  = { 244,130,140 };
	private int [] blOriColor = { 254,251,208 };
	private int [] blHitColor = { 119,93,106 };
	
	boolean colorDir = true;
	int soundN = 0;
	
	public GameManager(){

		th = new Thread(this);
		
		bg = new BackGround( );
		ct = new BackGround( );

		pl = new Player(N);
		
		blCrtl = new BlockCrtl(this , sm);		
		
	}


	void StartThead () {
		
		th.start();
	}
	
	void setPanel(Panel panel) {
		this.panel = panel;
	}
	
	public void run() {
		sm.playSound("battle01.wav", true); 
		while(true) {
			while( !isPlaying ) {
				try {
					checkState();
					Thread.sleep(100);
					panel.repaint();

				}
				catch (InterruptedException e){
					return ;
				}
			}
			
			
			try {
				Thread.sleep(20);
				checkState();
				
				time += 0.02;
				panel.repaint();
				rotation +=direction();
				
				soundN++;
				
				for( int i = 0 ; i < level ; i ++ ) {
					
						
					if(( soundN) % 50 == (50/ level) * i  ) {
						
						sm.playSound( "pickup01.wav" , false );
						changeColor();
					}
					
				}
				
			}
			catch (InterruptedException e){
				return ;
			}
			
		}
	}
	double direction() { // 회전 방향 변경
		if( direction < 0 ) 
			direction = Math.random() * 10; // 랜덤한 시간 동안 한 방향으로 회전
		else
			direction -= 0.1f;
		
		if( direction > 5 )
			return rotationSpeed; // 시계방향 회전
		else
			return -rotationSpeed; // 반시계방향 회전
	}
	
	void checkState () {
		
		if( plus && N < 100  && !isPlaying) 
			N++;

		else if( minus && N > 3 && !isPlaying ) {
			N--;
			if( pl.pos > N )
				pl.pos = N;
		}

		if( moveLeft == true && isPlaying ) {			// 왼쪽으로 이동
			sm.playSound("jump01.wav" , false);
			pl.pos--;	// 플레이어 위치 변경
		
		}
		if( moveRight == true && isPlaying  ) { 		// 오른쪽으로 이동
			sm.playSound("jump01.wav" , false);
			pl.pos++;	// 플레이어 위치 변경
		}
			
		
		if( pl.pos > N )
			pl.pos %= N;
		
		else if( pl.pos == 0 )
			pl.pos = N;
		
		
		if( start ) {
			if( isPlayerDie ) { // 플레이어 충돌
				time = 0;
				rotationSpeed = 2;
				isPlayerDie = false;
				blCrtl.removeAllBlock(); 
			}
			else { // 게임 일시 정지
				isPlaying = !isPlaying;
				if( level == 0 )
					level = 1;
				
				blCrtl.levelChange(level);
			}
			
		}
		
		
	
		if( levelUp ) { // 일정 시간이 지나면 레벨 상승
			if(level < 5) {
				level ++ ; 
				rotationSpeed += 0.5f;
				blCrtl.levelChange(level);
				
			}
		}
		if( levelDown ) { 
			if( level > 0 ) {
				level -- ;
				rotationSpeed -= 0.5f;
				blCrtl.levelChange(level);
			}		
		}
		
		
		start = false;
		plus = false;
		minus = false;
		moveLeft = false;
		moveRight = false;
		
		levelUp = false;
		levelDown = false;
	}
	
	
	void changeColor () { // 시간이 지남에 따라 색이 변경됨
		plOriColor[0] += change(plOriColor[0]); plOriColor[1] += change(plOriColor[1]);
		ctOriColor[0] += change(ctOriColor[0]); ctOriColor[1] += change(ctOriColor[1]);
		blOriColor[0] += change(blOriColor[0]); blOriColor[1] += change(blOriColor[1]);
		blHitColor[0] += change(blHitColor[0]); blHitColor[1] += change(blHitColor[1]);
		bg1Color[0] += change(bg1Color[0]); bg1Color[1] += change(bg1Color[1]);
		bg2Color[0] += change(bg2Color[0]); bg2Color[1] += change(bg2Color[1]);	
		
	}
	
	int change (int colorN) {
		int changeColorSpeed = 5;
		if( colorDir ) {
			
			if( colorN+changeColorSpeed >= 255 ) {
				colorDir = false;
				return -changeColorSpeed;
			}
				
			return changeColorSpeed;
		}
		else {
			if( colorN-changeColorSpeed <= 0 ) {
				colorDir = true;
				return changeColorSpeed;
			}
				
			return -changeColorSpeed;
		}
	}
	


	public void play (Graphics g) {
	
		// 오브젝트들을 그려줌
		bg.draw( g , N , 5000 , rotation , new Color (bg1Color[0] , bg1Color[1] , bg1Color[2]) , new Color (bg2Color[0] , bg2Color[1] , bg2Color[2])  );

		ct.draw ( g , N , 50 + (int) ( Math.random() * 12 ) , rotation , new Color (ctOriColor[0] , ctOriColor[1] , ctOriColor[2]) , new Color (ctOriColor[0] , ctOriColor[1] , ctOriColor[2]) );

		pl.draw( g , N , 15 + (int) ( Math.random() * 30) , rotation , new Color (plOriColor[0] , plOriColor[1] , plOriColor[2]), new Color (plHitColor[0] , plHitColor[1] , plHitColor[2]));
		
		
		if( (int)time == 10*level  ) {
			levelUp = true;
		}

		if( isPlaying  && blCrtl.bls.size() > 0 ) {
			blCrtl.checkCollide(pl);
			blCrtl.blockMove();
		}
		
		if( blCrtl.bls.size() > 0 ) {
			for( int i = 0 ; i < blCrtl.bls.size() ; i++ ) {
				
				if( blCrtl.bls.get(i).BlockCirR  > 0 )
					blCrtl.bls.get(i).draw( g , N , blCrtl.bls.get(i).BlockCirR  , rotation, new Color (blOriColor[0] , blOriColor[1] , blOriColor[2]) , new Color (blHitColor[0] , blHitColor[1] , blHitColor[2]) );
			}
		}
	

		g.setColor(Color.white);
		
		if( isPlaying ) {
			if( ( (int) ( time*10) % 10 )% 2 == 0 )
				g.setFont(new Font ( "Arial" , Font.BOLD , 25 ));
			else
				g.setFont(new Font ( "Arial" , Font.BOLD , 30 ));
			if((int)time == 0 || (int)time == 10 || (int)time == 20 ||  (int)time == 30 ||  (int)time == 40 || (int)time == 50 ) {
				g.drawString( "Level " + level + " Start!", 170 , 200);
			}
		
			g.drawString((int)time +"" + "." + (int) ( time*10) % 10, 230, 260);

		}
			
		else {
			g.setFont(new Font ( "Arial" , Font.BOLD , 30 ));
			g.drawString("NGON", 207 , 150);
			g.setFont(new Font ( "Arial" , Font.BOLD , 25 ));
			g.drawString("Press Space to Start!", 130 , 200);
			
			g.setFont(new Font ( "Arial" , Font.BOLD , 15 ));
			g.drawString(" press A to move left", 170 , 320);
			g.drawString("press D to move right", 170 , 335);
			
			g.setFont(new Font ( "Arial" , Font.BOLD , 30 ));
			g.drawString((int)time +"" + "." + (int) ( time*10) % 10, 230, 260);
			
		}
	
	}

}



class BlockCrtl {
	Vector <Block> bls = new Vector<Block>(10);

	int level = 0;
	private double [] levelSpeed = { 0 , 3   , 3   , 4   , 4   , 5 }; 
	private int [] blockStartPos = { 0 , 400 , 360 , 480 , 600 , 600  }; // 레벨에 따른 블락 위치 지정
	private int [] distance = 	   { 0 , 0   , 120 , 120 , 120 , 120  }; // 블락 간에 거리 
	SoundManager sm;
	GameManager gm;
	
	BlockCrtl(GameManager gm , SoundManager sm){
		
		this.gm = gm;
		this.sm = sm;
	}
	
	void removeAllBlock () {
		for( int i = bls.size() - 1 ; i >= 0 ; i-- ) {
			bls.remove(i);
		}
	}
	
	void levelChange( int _level ) {
		level = _level;
		
		if( bls.size() <= level  ) {
			for( ; bls.size() < level ; ) {
				bls.add( new Block() );
			}
			for( int i = 0 ; i < level ; i++ ) {
				
				bls.get(i).currentSpead = levelSpeed[level];
				bls.get(i).blockStartPos = blockStartPos[level] ;
			}
		}
		else {
			for( ; bls.size() > level  ; ) {
			
				bls.remove(level);
			}
		}
	
			
	}
	
	void allStopBlock () {
		
		for( int i = 0 ; i < level ; i++ ) {
			bls.get(i).currentSpead = 0;
		}
			
	}
	
	boolean checkCollide ( Player pl ) {
		for( int i = 0 ; i < level ; i++ ) {
			bls.get(i).blockColor =  bls.get(i).blockIdleColor; // bls.get(i).blockIdleColor;
			if( bls.get(i).BlockCirR > 0 && bls.get(i).BlockCirR  < Math.sqrt( Math.pow( Math.abs(pl.P.get(0).x-250) , 2 ) +  Math.pow( Math.abs(pl.P.get(0).y-250) , 2 ) ) ) {
				
				
				if( bls.get(i).isBlock[ pl.pos -1 ] == 1 ) {
					
					bls.get(i).blockColor = bls.get(i).blockHitColor;
					
					allStopBlock ();
					
					
					if( pl.hit == false ) {
						
						sm.playSound( "die01.wav" , false );
						pl.hit = true;
					}
					
					gm.isPlaying = false;
					gm.isPlayerDie = true;
					
					gm.level = 0;
					
					return true;
				}
				
			}

		}
		
		pl.hit = false;
		return false;
	}
	
	void blockMove () {
		if( level == 0 )
			return;
		
		for( int i = 0 ; i < level ; i++ ) {
			
				if( bls.get(i).BlockCirR > 40   ) {
					if( i == 0 )
						bls.get(i).BlockCirR -= bls.get(i).currentSpead;
					else if(i > 0) {
						if( bls.get(i).BlockCirR == bls.get(i - 1).BlockCirR + distance[level] )
							bls.get(i).BlockCirR -= bls.get(i).currentSpead;
						else if( bls.get(i).BlockCirR < bls.get(i - 1).BlockCirR  )
							bls.get(i).BlockCirR -= bls.get(i).currentSpead;
					}	
				}
				else
				{	
					bls.get(i).BlockCirR = bls.get(i).blockStartPos;
					bls.get(i).randomBlock();
					
				}

		}
		
	}
	
	
}

