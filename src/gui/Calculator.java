package gui;

import exception.AcragaException;
import main.Interpreter;
import main.Parser;
import main.Preprocessor;
import main.Scanner;
import token.exprtoken.Value;
import type.ValueType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;

public class Calculator extends JFrame {

    public Calculator() throws HeadlessException {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 0, 0));

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        getContentPane().add(jp1);
        getContentPane().add(jp2);
        getContentPane().add(jp3);

        JTextField inputBox = new JTextField(20);
        inputBox.setForeground(Color.gray);
        JLabel inputLable = new JLabel("Input: ");
        jp1.add(inputLable);
        jp1.add(inputBox);

        JTextArea info=new JTextArea();
        info.setEditable(false);
        JScrollPane jsp=new JScrollPane(info);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(jsp);


        JLabel outputLable = new JLabel("Output: Null");
        outputLable.setHorizontalAlignment(JLabel.CENTER);
        outputLable.setVerticalAlignment(JLabel.CENTER);
        jp3.add(outputLable);

        JButton button = new JButton("Execute");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expr = inputBox.getText();
                if (expr.trim().equals("")) {
                    outputLable.setText("Output: Null");
                    return;
                }
                TwoTuple calculatorInfo=Interpreter.interpretExpression(expr);
                if (calculatorInfo != null) {
                    if(calculatorInfo.first==null)
                        outputLable.setText("Output: " + "Syntax Error");
                    else
                        outputLable.setText("Output: " + calculatorInfo.first.toString());
                    StringBuilder sb=(StringBuilder)calculatorInfo.second;
                    StringBuilder newSb=new StringBuilder();
                    newSb.append("<html>");
                    for(int i=0;i<sb.length();i++){
                        char current=sb.charAt(i);
                        if(current=='\n')
                            newSb.append("<br/>");
                        else
                            newSb.append(current);
                    }
                    newSb.append("</html>");
                    info.setText(sb.toString());
                }
                else
                    outputLable.setText("Output: Syntax Error");
            }
        });
        jp2.add(button);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }
}
