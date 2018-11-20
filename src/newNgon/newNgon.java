package newNgon;

import java.awt.*;
import javax.swing.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;


import java.util.Random;

public class newNgon extends JFrame {
	public Panel panel = new Panel(); // 판넬 생성
	public GameManager gm = new GameManager(); // 게임매니저 생성
	
	public static void main (String [] args) {
		new newNgon();
	}
	
	newNgon(){
		setTitle("광운대학교 김석호");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize( 500,500 );
		setVisible( true );

		panel.setGM(gm);
		gm.setPanel(panel);
		gm.StartThead();
		
		setContentPane( panel );
		
		Container c = getContentPane();

		c.addKeyListener(new KeyListener(gm)); // 키 리스너 생성
		c.requestFocus();
	
	}
	
	
}

class Panel extends JPanel implements Runnable{ 
	GameManager gm;
	void setGM (GameManager gm){
		this.gm = gm;
		
	}
	public void paintComponent( Graphics g ) {
		
		super.paintComponent(g);
		gm.play(g);
		
	}
	
}
