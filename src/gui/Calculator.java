package gui;

import exception.AcragaException;
import main.Interpreter;
import main.Parser;
import main.Preprocessor;
import main.Scanner;
import token.exprtoken.Value;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {

    public Calculator() throws HeadlessException {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();

        add(jp1, BorderLayout.NORTH);
        add(jp2, BorderLayout.SOUTH);

        JTextField inputBox = new JTextField(20);
        inputBox.setForeground(Color.gray);
        JLabel inputLable = new JLabel("Input: ");
        jp1.add(inputLable);
        jp1.add(inputBox);

        JTextArea info = new JTextArea();
        info.setEditable(false);
        JScrollPane jsp = new JScrollPane(info);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(jsp);

        JLabel outputLable = new JLabel("Output: Null");
        outputLable.setHorizontalAlignment(JLabel.CENTER);
        outputLable.setVerticalAlignment(JLabel.CENTER);
        jp2.add(outputLable);

        JButton button = new JButton("Execute");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info.setText("");
                String expr = inputBox.getText();
                if (expr.trim().equals("")) {
                    outputLable.setText("Output: Null");
                    return;
                }
                TwoTuple calculatorInfo = Interpreter.interpretExpression(expr);
                // wrong
                if (calculatorInfo.second == null) {
                    info.setText("");
                    outputLable.setText("Syntax Error: " + ((Value) calculatorInfo.first).getStringValue());
                }
                // right
                else {
                    outputLable.setText("Output: " + calculatorInfo.first.toString());
                    info.setText(((StringBuilder) calculatorInfo.second).toString());
                }
            }
        });
        jp1.add(button);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }
}
