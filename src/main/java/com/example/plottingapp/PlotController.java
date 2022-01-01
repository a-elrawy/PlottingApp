package com.example.plottingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Stack;
import java.util.stream.IntStream;
public class PlotController {
    @FXML
    private TextField func;
    @FXML
    private TextField min;
    @FXML
    private TextField max;
    @FXML
    private Label errorEQ;
    @FXML
    private Label errorRange;
    @FXML
    private LineChart lineChart;
    public void initialize(){
        errorEQ.setVisible(false);
        errorRange.setVisible(false);
    }
    int counter=0;
    public void setFunc(ActionEvent event) {
        String function = func.getText();
        int maxV = 0;
        int minV = 0;
        //get Range
        try {
            minV = Integer.parseInt(min.getText());
            maxV = Integer.parseInt(max.getText());
        } catch (Exception e) {
            errorRange.setVisible(true);
        }
        if(minV > maxV){
            errorRange.setVisible(true);
            return;
        }
        // Plot
        XYChart.Series series = new XYChart.Series();
        series.setName("Plot "+ ++counter);
        int[] x = IntStream.range(minV, maxV).toArray();
        // Declaring the function
        String newFunction;
        for (int i = 0; i < x.length; i++) {
            if (x[i]< 0)
                newFunction = function.replace("x", "(0-"+(x[i]*-1)+")");//Convert Negatives
            else
                newFunction = function.replace("x", String.valueOf(x[i]));

            //Plotting
            try {
                series.getData().add(new XYChart.Data(x[i], preprocess(newFunction)));
                errorEQ.setVisible(false);
                errorRange.setVisible(false);
            } catch (Exception e) {
                errorEQ.setVisible(true);
            }
        }
        lineChart.getData().add(series);
    }

    public int preprocess(String expression){
        char[] tokens = expression.toCharArray();
        Stack<Integer> values = new Stack<Integer>();
        Stack<Character> ops = new Stack<Character>();
        for (int i = 0; i < tokens.length; i++){
            if (tokens[i] == ' ')
                continue;
            if (tokens[i] >= '0' &&
                    tokens[i] <= '9')
            {
                StringBuilder sbuf = new StringBuilder();
                while (i < tokens.length &&  tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
                i--;
            }

            else if (tokens[i] == '(')
                ops.push(tokens[i]);
            else if (tokens[i] == ')'){
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(),values.pop(),values.pop()));
                ops.pop();
            }
            else if (tokens[i] == '+' ||tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^' )
            {
                while (!ops.empty() &&
                        hasPrecedence(tokens[i],
                                ops.peek()))
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));
                ops.push(tokens[i]);
            }
        }
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                return values.pop();
    }

    public static boolean hasPrecedence(char op1, char op2) {
                if (op2 == '(' || op2 == ')')
                    return false;
                if (op1 == '^')
                    return false;
                if ((op1 == '*' || op1 == '/') &&
                        (op2 == '+' || op2 == '-'))
                    return false;
                else
                    return true;
    }
    public static int applyOp(char op, int b, int a) {
        switch (op) {
             case '+':
                 return a + b;
             case '-':
                 return a - b;
             case '*':
                 return a * b;
             case '/':
                 if (b == 0)
                     throw new UnsupportedOperationException("Cannot divide by zero");
                 return a / b;
             case '^': return (int) Math.pow(a,b);

        }
        return 0;
    }


    // Work only for positive Numbers

//    public int evaluate(String equation){
//        Stack<Integer> numbers = new Stack<>();
//        Stack<Character> operations = new Stack<>();
//        for(int i=0; i<equation.length();i++) {
//            char c = equation.charAt(i);
//            if(Character.isDigit(c)) {
//                int num = 0;
//                while (Character.isDigit(c)) {
//                    num = num * 10 + (c - '0');
//                    i++;
//                    if (i < equation.length())
//                        c = equation.charAt(i);
//                    else
//                        break;
//                }
//                i--;
//                numbers.push(num);
//            } else if(isOperator(c)){
//                    while(!operations.isEmpty() && precedence(c)<=precedence(operations.peek())){
//                        int output = applyOp(numbers, operations);
//                        numbers.push(output);
//                    }
//                    operations.push(c);
//                }
//            }
//            while(!operations.isEmpty()){
//                int output = applyOp(numbers, operations);
//                numbers.push(output);
//            }
//            return numbers.pop();
//    }
//
//    int precedence(char op){
//        if(op == '+'||op == '-')
//            return 1;
//        if(op == '*'||op == '/')
//            return 2;
//        if (op == '^')
//            return 3;
//        return 0;
//    }
//    int applyOp(Stack<Integer> numbers, Stack<Character> operations){
//        int a = numbers.pop();
//        int b = numbers.pop();
//        char op = operations.pop();
//
//        switch(op){
//            case '+': return a + b;
//            case '-': return a - b;
//            case '*': return a * b;
//            case '/': return a / b;
//            case '^': return (int) Math.pow(a,b);
//        }
//        return 0;
//    }
//    public boolean isOperator(char c){
//        return (c=='+'|| c=='-' ||c =='/' ||c=='*'||c=='^');
//    }


}