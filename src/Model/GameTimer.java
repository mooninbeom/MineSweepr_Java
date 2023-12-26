package Model;

import javax.swing.*;
import java.awt.*;


// 게임 경과 시간 측정을 위한 클래스 ( Thread 사용 )
public class GameTimer extends JLabel implements Runnable {
    private final int width = 150, height = 150;
    private int second = 0;

    private boolean timerFlag = true;

    public CoreModel coreModel;

    public GameTimer(CoreModel coreModel) {
        this.coreModel = coreModel;
        setOpaque(true);
        setForeground(Color.black);
        setFont(this.getFont().deriveFont(30f));
        setText("경과 시간 : " + second + "초");

        setHorizontalAlignment(JLabel.CENTER);
    }



    @Override
    public void run() {
        while (timerFlag) {
            try {
                Thread.sleep(1000);
            } catch (Exception e ) {
                e.printStackTrace();
            }

            this.second ++;
            this.setText("경과 시간 : " + this.second + "초");
        }
    }

    // flag를 두어 쓰레드 조정
    private void stopTimer() {
        this.timerFlag = false;
    }

    // 쓰레드 종료 후 경과 시간 get
    public int getSecondAndStop() {
        stopTimer();
        return this.second;
    }
}
