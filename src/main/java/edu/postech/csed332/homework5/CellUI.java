package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.events.DisabledEvent;
import edu.postech.csed332.homework5.events.EnabledEvent;
import edu.postech.csed332.homework5.events.Event;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class CellUI extends JTextField implements Observer
{
    /**
     * create a cell UI
     *
     * @param cell a cell model
     */
    CellUI(Cell cell)
    {
        cell.addObserver(this);
        initCellUI(cell);

        if (cell.getNumber().isEmpty())
        {
            // TODO: whenever the content is changed, cell.setNumber() or cell.unsetNumber() is accordingly invoked.
            //  You may use an action listener, a key listener, a document listener, etc.
            this.addKeyListener(new KeyListener()
            {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    
                }

                @Override
                public void keyPressed(KeyEvent e)
                {

                }

                @Override
                public void keyReleased(KeyEvent e)
                {
                    if (!getText().equals(""))
                    {
                        cell.setNumber(Integer.parseInt(getText()));
                        if (cell.getNumber().isEmpty()) setText("");
                    }

                    else
                    {
                        cell.unsetNumber();
                    }
                }
            });

        }
    }

    /**
     * Mark this cell UI as active
     */
    public void setActivate()
    {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setEditable(true);
    }

    /**
     * Mark this cell UI as inactive
     */
    public void setDeactivate()
    {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setEditable(false);
    }

    /**
     * Whenever a cell is changed, this function is called. EnabledEvent and DisabledEvent are handled here.
     * If the underlying cell is enabled, mark this cell UI as active. If the underlying cell is disabled, mark
     * this cell UI as inactive.
     *
     * @param caller the subject
     * @param arg    an argument passed to caller
     */
    @Override
    public void update(Subject caller, Event arg)
    {
        // TODO: implement this done
        if (arg instanceof EnabledEvent)
        {
            this.setActivate();
        }

        else if (arg instanceof DisabledEvent)
        {
            this.setDeactivate();
        }
    }

    /**
     * Initialize this cell UI
     *
     * @param cell a cell model
     */
    private void initCellUI(Cell cell)
    {
        setFont(new Font("Times", Font.BOLD, 30));
        setHorizontalAlignment(JTextField.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (cell.getType() == Cell.Type.EVEN)
            setBackground(Color.LIGHT_GRAY);

        if (cell.getNumber().isPresent())
        {
            setForeground(Color.BLUE);
            setText(cell.getNumber().get().toString());
            setEditable(false);
        }
    }
}
