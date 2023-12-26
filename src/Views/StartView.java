package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class StartView extends JFrame {

    // 처음 나오는 시작화면
    // 이름, 게임 크기, 지뢰 갯수를 받아 MainView를 init한다.
    public StartView() {
        super("시작창");
        var contentPane = getContentPane();
        var textField = new JTextField();
        var sizeComboBox = new JComboBox<String>(new String[]{"10x10", "12x12", "15x15"});
        var mineNumComboBox = new JComboBox<Integer>(new Integer[]{10, 15, 20, 25});
        var startBtn = new JButton("시작!");


        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setText("홍길동");

        var label1 = new JLabel("이름");
        var label2 = new JLabel("크기");
        var label3 = new JLabel("지뢰 갯수");

        label1.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);

        setSize(400, 300);

        var panel = new JPanel(new GridLayout(3,2));
        panel.add(label1);
        panel.add(textField);
        panel.add(label2);
        panel.add(sizeComboBox);
        panel.add(label3);
        panel.add(mineNumComboBox);


        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var boxModel = sizeComboBox.getModel();
                StringTokenizer st = new StringTokenizer(boxModel.getSelectedItem().toString(), "x");
                int size = Integer.parseInt(st.nextToken());
                setVisible(false);


                var mineModel = mineNumComboBox.getModel();
                var mine = mineModel.getSelectedItem().toString();
                var mineNum = Integer.parseInt(mine);

                new MainView(size, size, mineNum);
            }
        });


        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(startBtn, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);

    }

}
