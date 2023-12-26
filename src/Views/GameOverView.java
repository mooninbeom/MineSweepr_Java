package Views;

import Model.CoreModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 게임 성공 or 실패시 나오는 View
public class GameOverView extends JFrame {

    CoreModel coreModel;
    MainView mainView;

    private final int gameTime;


    // Initializer
    public GameOverView(CoreModel coreModel, MainView mainView, boolean isComplete, int gameTime) {
        this.coreModel = coreModel;
        this.mainView = mainView;
        this.gameTime = gameTime;
        var contentPane = getContentPane();
        setSize(300, 300);

        var panel = new JPanel(new GridLayout(1, (isComplete) ? 2 : 3));

        var successTime = String.format("성공했습니다!!, 경과시간 : %d 초", this.gameTime);

        var label = new JLabel( (isComplete) ? successTime : "지뢰를 밟았습니다!!" );
        label.setHorizontalAlignment(JLabel.CENTER);

        var restartBtn = new JButton("다시 시작");
        var selectBtn = new JButton("처음으로");
        var exitBtn = new JButton("종료하기");

        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });

        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goStart();
                new StartView();
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        if (!isComplete) {
            panel.add(restartBtn);
        }
        panel.add(selectBtn);
        panel.add(exitBtn);
        contentPane.add(label, BorderLayout.CENTER);
        contentPane.add(panel, BorderLayout.SOUTH);


        setVisible(true);
    }

    // 처음 화면으로 복귀
    void goStart() {
        mainView.dispose();
        dispose();
    }

    // 조건 그대로 다시 시작 ( 게임 오버 시 나옴 )
    void restart() {
        var height = mainView.height;
        var width = mainView.width;
        var mineNum = mainView.mineNum;

        new MainView(width, height, mineNum);

        mainView.dispose();
        dispose();
    }

}
