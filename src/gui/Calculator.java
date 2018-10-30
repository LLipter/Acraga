package gui;

import main.Interpreter;
import token.exprtoken.Value;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {

    public Calculator() throws HeadlessException {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 0, 0));

        JPanel jp_upper = new JPanel();
        JPanel jp_middle = new JPanel();
        JPanel jp_bottom = new JPanel();
        getContentPane().add(jp_upper);
        getContentPane().add(jp_middle);
        getContentPane().add(jp_bottom);

        JTextField inputBox = new JTextField(20);
        inputBox.setForeground(Color.gray);
        JLabel inputLable = new JLabel("Input: ");
        jp_upper.add(inputLable);
        jp_upper.add(inputBox);

        JLabel outputLable = new JLabel("Output: Null");
        outputLable.setHorizontalAlignment(JLabel.CENTER);
        outputLable.setVerticalAlignment(JLabel.CENTER);
        jp_middle.add(outputLable);

        JButton button = new JButton("Execute");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expr = inputBox.getText();
                Value value = Interpreter.interpretExpression(expr);
                if (value != null)
                    outputLable.setText("Output: " + value.toString());
                else
                    outputLable.setText("Output: Syntax Error");
            }
        });
        jp_bottom.add(button);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }
}
