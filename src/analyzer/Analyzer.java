/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;

/**
 *or
 * @author Ярослав
 */
public class Analyzer {
    Result res;
    JTextArea inputField;
    JFrame mainFrame;
    JTextField errorField;

    public enum Errors {NotError , OutOfRange , LOOPExpected , DOExpected , UNTILExpected , SpaceExpected,
    TooMuchID , TooMuchConst , ExpressionExpected, UnknownError , 
    IDExpected , ConstExpected,EqualSignExpected,LogicalOperationExpected,
    LetterExpected,IDIsReservedCommand,NewLineExpectedError,OperandExpected,
    UnknownLogicalOperation,RatioOperationExpected,TermExpected};
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        
        
        Analyzer analyzerObj = new Analyzer();
        analyzerObj.go();
        
        
    
    }
    
    public void go(){
        mainFrame = new JFrame("Анализатор,15 вариант");
        mainFrame.setSize(500,300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        
        JButton analyzeButton = new JButton("Анализировать");
        analyzeButton.addActionListener( new analyzeButtonListener());
        JButton semanticButton = new JButton("Семантика");
        semanticButton.addActionListener( new semanticButonListener());
        JButton infoButton = new JButton("Задание");
        infoButton.addActionListener( new infoButtonListener());
        JButton exitButton = new JButton("Выход");
        exitButton.addActionListener( new exitButtonListener());
        
        JLabel inputLabel = new JLabel("Введите строку:");
        JLabel errorLabel = new JLabel("Сообщение об ошибке:");
        
        inputField = new JTextArea(10,20);
        inputField.setLineWrap(true);
        
        errorField = new JTextField(40);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout( new BoxLayout(inputPanel , BoxLayout.Y_AXIS));
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel , BoxLayout.Y_AXIS));
        
        
        buttonPanel.add(analyzeButton);
        buttonPanel.add(semanticButton);
        buttonPanel.add(infoButton);
        buttonPanel.add(exitButton);
        
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        mainFrame.getContentPane().add(BorderLayout.NORTH,inputPanel);
        errorPanel.add(errorLabel);
        errorPanel.add(errorField);
        mainFrame.getContentPane().add(BorderLayout.CENTER,errorPanel);
        
        mainFrame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        mainFrame.setVisible(true);
    }
    
    class analyzeButtonListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                res = Handler.analyzing(inputField.getText());
                LinkedList<Errors> errors = res.getErrors();               
                if(errors.size() == 0){
                    errorField.setText("Предложение принадлежит языку!!!");
                }
                else{
                    String s ="";
                    for(int i =0;i<errors.size();i++){
                        s+=res.getErrorMessage(errors.get(i));
                        s+=",";
                    } 
                    s+=res.getErrorPos();
                    errorField.setText(s);
                }
                
            }          
    }
    
    class semanticButonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           JFrame semanticFrame = new JFrame("Семантика");
           semanticFrame.setSize(500,300);
           JPanel panel = new JPanel();
           
           JTextArea idArea = new JTextArea(10,10);
           panel.add(idArea);
           idArea.setText("Идентификаторы:\nКоличество: ");
           JTextArea constArea = new JTextArea(10,10);
           panel.add(constArea);
           idArea.setText("");
           constArea.setText("");
           semanticFrame.getContentPane().add(panel);
           constArea.setText("Константы:\nКоличество: ");
           LinkedList<String> ids = res.getIdentificators();
           LinkedList<String> consts = res.getConstants();
           idArea.append(ids.size()+"\n");
           constArea.append(consts.size() +"\n");
           for(int i=0 ; i<ids.size();i++){
               idArea.append(ids.get(i) + "\n");
           }
           for(int i=0; i<consts.size();i++){
               constArea.append(consts.get(i) + "\n");
           }
           semanticFrame.setVisible(true);
        }       
    }
    
    class infoButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame info = new JFrame("Задание");
            info.setSize(500,300);           
            JTextArea infoArea = new JTextArea(20,10);
            info.getContentPane().add(infoArea);
            infoArea.setLineWrap(true);
            
            infoArea.append("Вариант 15:\nНаписать программу синтаксического анализа автоматного языка\n операторов цикла с постусловием языка QBasic, имеющих вид:\n");
            infoArea.append("DO\n<оператор присваивания>\nLOOP UNTIL<логическое выражение>\n");
            infoArea.append("Семантика:\n");
            infoArea.append("Вывести список таблиц и констанст.\n");
            infoArea.append("Константа должна быть в пределах от -32768 до 32767\n");
            infoArea.append("Длина идентификатора должна быть не более 8");
            
            
            info.setVisible(true);
        }        
    }
    
    class exitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.dispose();
        }       
    }
}
