package newNgon;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class SoundManager {
	// ���� �Ŵ���, ����� ���� ����
	public void playSound(String file, boolean Loop){
		
		Clip clip;

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();

			if (Loop) clip.loop(-1); // ���� ���
		

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}