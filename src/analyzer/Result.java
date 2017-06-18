/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer;

import analyzer.Analyzer.Errors;
import analyzer.Handler.eState;
import java.util.LinkedList;

/**
 *
 * @author Ярослав
 */
public  class Result {
    private Errors _error;
    private int _errorPos;
    private String _errorMessage; 
    private LinkedList<String> _identificators;
    private LinkedList<String> _constants;
    private LinkedList<Errors> _errors;
    
    public Result(Errors error,int errorPos,LinkedList<String> ids , LinkedList<String> consts){
        _error = error;
        _errorPos = errorPos;
        _identificators = ids;
        _constants = consts;
    }
    
    public Result(LinkedList<Errors> errors,int errorPos,LinkedList<String> ids , LinkedList<String> consts){
        _errors = errors;
        _errorPos = errorPos;
        _identificators = ids;
        _constants = consts;
    }
    
    public Errors getError(){
        return _error;
    }
    
    public int getErrorPos(){
        return _errorPos;
    }
    
    public LinkedList<String> getIdentificators(){
        return _identificators;
    }
    
    public LinkedList<String> getConstants(){
        return _constants;
    }
    
    public LinkedList<Errors> getErrors(){
        return _errors;
    }
    
   
    
  
    
    public String getErrorMessage(Errors er){
        //switch(_error){
        switch(er){
            case TermExpected :{
                _errorMessage = "Ожидался терм(константа или идентификатор)";
                break;
            }
            
            case NotError :{
                _errorMessage = "Ошибок нет!!!Предложение принадлежит языку!";
                break;
            }
            
            case OutOfRange :{
                _errorMessage = "Выход за пределы строки";
                break;
            }
            
            case LOOPExpected :{
                _errorMessage = "Ожидалось ключевое слово LOOP";
                break;
            }
            
            case DOExpected :{
                _errorMessage = "Ожидалось ключевое слово DO";
                break;
            }
            
            case UNTILExpected :{
                _errorMessage = "Ожидалось ключевое слово UNTIL";
                break;
            }
            
            case SpaceExpected :{
                _errorMessage = "Ожидался пробел";
                break;
            }
            
            case TooMuchID :{
                _errorMessage = "Размер идентификатора превышает 8 символов";
                break;
            }
            
            case TooMuchConst :{
                _errorMessage = "Значение константы выходитза предела [-32768;32767]";
                break;
            }
            
            case ExpressionExpected :{
                _errorMessage = "Ожидалось выражение";
                break;
            }
            
            case UnknownError :{
                _errorMessage = "Неизвестная ошибка";
                break;
            }
            
            case IDExpected :{
                _errorMessage = "Ожидался идентификатор";
                break;
            }
            
            case ConstExpected :{
                _errorMessage = "Ожидалась контсанта";
                break;
            }
            
            case EqualSignExpected :{
                _errorMessage = "Ожидался знак равентсва";
                break;
            }
            
            case LogicalOperationExpected :{
                _errorMessage = "Ожидалась логическая операция";
                break;
            }
            
            case LetterExpected :{
                _errorMessage = "Идентификатор должен начинаться с буквы";
                break;
            }
                    
            case IDIsReservedCommand :{
                _errorMessage = "Идентификатор не должен быть зарезервированным словом";
                break;
            }
            
            case NewLineExpectedError :{
                _errorMessage = "Ожидался перенос строки";
                break;
            }
            
            case OperandExpected :{
                _errorMessage = "Ожидался операнд";
                break;
            }
            
            case RatioOperationExpected :{
                _errorMessage = "Ожидалась операция сравнения";
            }
            
            case UnknownLogicalOperation :{
                _errorMessage = "Неизвестная логическая операция";
            }
            default :{
                _errorMessage = "Ошибка не определяется";
                break;
            }
                    
        }
        return _errorMessage;
    }
    
    
    
}
