package EventListener;

import Model.CoreModel;
import Views.ButtonCell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CellClickListener implements ActionListener, MouseListener {

    CoreModel coreModel;
    ButtonCell cellBtn;

    public CellClickListener(ButtonCell cellBtn, CoreModel coreModel) {
        this.cellBtn = cellBtn;
        this.coreModel = coreModel;
    }


    // 버튼 클릭시 Cell Click 메소드 실행
    @Override
    public void actionPerformed(ActionEvent e) {
        var x = cellBtn.x;
        var y = cellBtn.y;

        coreModel.cellClick(x,y);
    }


    // 버튼을 맘우스 오른쪽으로 클릭 시 깃발 세우는 메소드
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            var x = cellBtn.x;
            var y = cellBtn.y;
            coreModel.exeFlagButton(x,y);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
