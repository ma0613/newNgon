package newNgon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
	
	GameManager gm;
	
	KeyListener(GameManager gm){
		this.gm = gm;
	}
		
	public void keyPressed(KeyEvent e) {	// ����ڰ� �Է��� Ű�� ����	�̺�Ʈ �߻�	
		switch (e.getKeyChar()) {

		case 'a' : case 'A' :
			gm.moveLeft = true;
			
			break;	
		case 'd' : case 'D' :
			gm.moveRight = true;
			
			break;	
		case 'w' : case 'W' :
			gm.plus = true;
			
			break;	
		case 's' : case 'S' :
			gm.minus = true;
			
			break;	
		case 'o' : case 'O' :
			gm.levelUp = true;
			
			break;	
		case 'p' : case 'P' :
			gm.levelDown = true;
		
			break;	
		case ' ' :
			gm.start = !gm.start;
			
			break;	
		}
		
	}	
	
}
