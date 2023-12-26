package Views;

import EventListener.CellClickListener;
import Model.CoreModel;

import javax.swing.*;


// 버튼 View
public class ButtonCell extends JButton {
    public Boolean isMine;
    public int x, y;
    CoreModel coreModel;

    ButtonCell(Boolean isMine, int x, int y, CoreModel coreModel) {
        super();
        this.isMine = isMine;
        this.x = x;
        this.y = y;
        setOpaque(true);
        setBorderPainted(true);
        setFocusPainted(false);
        this.coreModel = coreModel;

        var listener = new CellClickListener(this, coreModel);

        addActionListener(listener);
        addMouseListener(listener);
    }
}

