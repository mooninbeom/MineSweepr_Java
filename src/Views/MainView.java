package Views;

import Model.CoreModel;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame { // 게임이 진행되는 Main View
    int width, height, mineNum;

    public ButtonCell[][] arr;

    CoreModel coreModel;

    MainView(int width, int height, int mineNum) {
        super(String.format("지뢰 찾기 %dx%d", width, height));
        this.width = width;
        this.height = height;
        this.mineNum = mineNum;
        setSize(700, 700);

        // 모델 생성
        coreModel = new CoreModel(height, width, mineNum, this);

        arr = new ButtonCell[width][height];

        var panel = new JPanel(new GridLayout(height, width));


        for(int i = 0; i<height; i++) {
            for(int j=0; j<width; j++) {
                var isMine = coreModel.getIsMine(i,j);
                var btn = new ButtonCell(isMine, i, j, this.coreModel);
                arr[i][j] = btn;
                panel.add(btn);
            }
        }

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(coreModel.getGameTimer(), BorderLayout.NORTH);



        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // 해당 버튼 칸 주위에 지뢰가 없을 시 ( 0 일떄 ) 사용되는 메소드
    // 해당 버튼을 disable하고 배경을 회색으로 바꿈
    public void changeBackgroundColor(int x, int y) {
        var btn = this.arr[x][y];
        btn.setBorderPainted(false);
        btn.setBackground(Color.gray);
        btn.setText("");
        btn.setEnabled(false);
        evaluateGame();
    }

    // 해당 버튼 칸 주위에 지뢰가 있을 시 ( 1 이상 ) 사용되는 메소드
    // 해당 버튼을 disable하고 주위에 있는 지뢰 갯수를 표현
    public void changeButtonTitle(int x, int y) {
        var btn = this.arr[x][y];
        btn.setText(String.format("%d",coreModel.getMineSolution()[x][y]));
        btn.setEnabled(false);
        evaluateGame();
    }

    // 버튼에 깃발 세움( 지뢰찾기에서 지뢰위치를 표시하는 것 )
    public void flagButton(int x, int y) {
        var btn = this.arr[x][y];
        if (!btn.isEnabled()) return;
        if (btn.getText().equals("깃발")) {
            btn.setText("");
            return;
        }
        btn.setText("깃발");
    }

    // 지뢰 버튼 눌렀을 시 빨강색으로 만듦
    public void mineButton(int x, int y) {
        var btn = this.arr[x][y];
        btn.setBorderPainted(false);
        btn.setBackground(Color.red);
    }


    // disabled된 버튼들의 수를 세어 게임 완료 조건인지 평가하는 메소드
    private void evaluateGame() {
        var evaluationNum = this.width * this.height - mineNum;
        var disabledBtnNum = 0;
        var flagNum = 0;

        for(int i=0 ;i < height; i++) {
            for (int j=0; j < width; j++) {
                var a = this.arr[i][j];
                if (!a.isEnabled()) disabledBtnNum++;
                if (a.getText().equals("깃발")) flagNum++;
            }
        }

        if (evaluationNum == disabledBtnNum || flagNum == mineNum) {
            var timer = coreModel.getGameTimer();
            var second = timer.getSecondAndStop();

            new GameOverView(coreModel, this, true, second);
        }
    }

}
