package newNgon;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class SoundManager {
	// 사운드 매니저, 오디오 파일 실행
	public void playSound(String file, boolean Loop){
		
		Clip clip;

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();

			if (Loop) clip.loop(-1); // 연속 재생
		

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}