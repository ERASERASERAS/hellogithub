/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer;

import analyzer.Analyzer.Errors;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Ярослав
 */
public class Handler {
    enum eState {Start , Final , Error , State1, State2, State3, State4, State5, State6, State7, State8};
    private static String _analyzedString;
    private static int _length;
    //private static Errors _error;  
    private static LinkedList<Errors> _errors = new LinkedList<Errors>();
    private static int _errorPos;
    private static int _position;
    private static LinkedList<String> _identificators = new LinkedList<String>();
    private static LinkedList<String> _constants = new LinkedList<String>();
    
    
  //  public static void setError(Errors error,int pos){
   //     _error = error;
   //     _errorPos = pos;
   // }
    
    
    
    public static Result analyzing(String analyzedString){
        _position = 0;
        _analyzedString = analyzedString;
        _identificators.clear();
        _constants.clear();
        _errors.clear();
        _length = analyzedString.length();
        //setError(Errors.NotError,-1);
        base(_analyzedString.toCharArray());
        return new Result(_errors,_errorPos,_identificators,_constants);
    }
    
    private static void base(char [] inputString){
        eState State = eState.Start;
        boolean result = false;
        
        
        while(State != eState.Error && State != eState.Final){
            //if((_position == _length) && (State != eState.Error)){
            //    State = eState.Final;
            //}
             if(_position>_length){
                State = eState.Error;
               // setError(Errors.OutOfRange,_position - 1);
                _errors.add(Errors.OutOfRange);
                _errorPos = _position -1;
            }
            else{
                switch(State){
                    case Start: {
                       if(_analyzedString.length() >=_position+2) {
                            String dos = _analyzedString.substring(_position,_position+2);
                            if(dos.equalsIgnoreCase("DO")){
                                State = eState.State1;
                                _position +=2;
                            }
                            else{
                                State = eState.Error;
                           // setError(Errors.DOExpected,_position);
                                _errors.add(Errors.DOExpected);
                                _errorPos = _position;
                            }
                       }
                       else{
                            State = eState.Error;
                           // setError(Errors.DOExpected,_position);
                                _errors.add(Errors.DOExpected);
                                _errorPos = _position;
                       }
                        break;
                    }
                    
                    case State1: {
                        try{
                            if(inputString[_position] == ' '){
                             _position++;
                            }
                            else if(inputString[_position] == '\n'){
                                State = eState.State2;
                                _position++;
                            }  
                            else{
                                
                            }
                        }
                        catch(Exception e){
                                State = eState.Error;
                                //setError(Errors.NewLineExpectedError,_position);
                                _errors.add(Errors.NewLineExpectedError);
                                _errorPos = _position;
                        }
                        
                        break;
                    }
                    
                    case State2: {
                        try{
                            if(inputString[_position] == ' '){
                            _position++;
                        }
                        else if(assignmentOp(inputString)){
                            State = eState.State3;;
                        }
                        else{
                            State = eState.Error;
                           // if(_error == Errors.NotError){
                                //setError(Errors.ExpressionExpected,_position);
                           // }
                           _errors.add(Errors.ExpressionExpected);
                           _errorPos = _position;
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                           // if(_error == Errors.NotError){
                                //setError(Errors.ExpressionExpected,_position);
                           // }
                           _errors.add(Errors.ExpressionExpected);
                           _errorPos = _position;
                        }
                        break;                       
                    }
                    
                    case State3: {
                        try{
                            if(inputString[_position] == ' '){
                                _position++;
                            }
                            else if(inputString[_position] == '\n'){
                                State = eState.State4;
                                _position++;
                            }
                            else{
                                State = eState.Error;
                           // setError(Errors.NewLineExpectedError,_position);
                                _errors.add(Errors.NewLineExpectedError);
                                _errorPos = _position;
                            }
                        }
                        catch(Exception e){
                            State = eState.Error;
                           // setError(Errors.NewLineExpectedError,_position);
                                _errors.add(Errors.NewLineExpectedError);
                                _errorPos = _position;
                        }
                        break;
                    }
                    
                    case State4: {
                        if(_analyzedString.length() >=_position+4){
                            if(inputString[_position] == ' '){
                                _position++;
                            }
                            else if(_analyzedString.substring(_position, _position+4).equalsIgnoreCase("LOOP")){
                                State = eState.State5;
                             _position+=4;
                            }
                            else{
                                State = eState.Error;
                            //setError(Errors.LOOPExpected,_position);
                                _errors.add(Errors.LOOPExpected);
                                _errorPos = _position;
                            }
                        }
                        else{
                              State = eState.Error;
                            //setError(Errors.LOOPExpected,_position);
                                _errors.add(Errors.LOOPExpected);
                                _errorPos = _position;
                        }
                        
                        
                        break;
                    }
                    
                    case State5: {
                        try{
                             if(inputString[_position] == ' '){
                                State = eState.State6;
                                _position++;
                            }
                            else{
                                State = eState.Error;
                           // setError(Errors.NewLineExpectedError,_position);
                                _errors.add(Errors.SpaceExpected);
                                _errorPos = _position;
                            }
                        }
                        catch(Exception e){                            
                                State = eState.Error;
                           // setError(Errors.NewLineExpectedError,_position);
                                _errors.add(Errors.SpaceExpected);
                                _errorPos = _position;                           
                        }
                        break;
                    }
                    
                    case State6 :{
                       
                            if(_analyzedString.length() >= _position +5){
                            if(inputString[_position] == ' '){
                                _position++;
                            }
                            else if(_analyzedString.substring(_position,_position +5).equalsIgnoreCase("UNTIL")){
                                _position+=5;
                                State = eState.State7;
                            }
                            else{
                                State = eState.Error;
                            //setError(Errors.UNTILExpected,_position);
                                _errors.add(Errors.UNTILExpected);
                                _errorPos = _position;
                            }
                        }
                        else{
                            State = eState.Error;
                            //setError(Errors.UNTILExpected,_position);
                                _errors.add(Errors.UNTILExpected);
                                _errorPos = _position;
                        }
                       
                        
                        break;
                    }
                    
                    case State7 :{
                        try{
                            if(inputString[_position] == ' '){
                            _position++;
                            State = eState.State8;
                        }
                        else{
                            State = eState.Error;
                            //setError(Errors.SpaceExpected,_position);
                            _errors.add(Errors.SpaceExpected);
                            _errorPos = _position;
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                            //setError(Errors.SpaceExpected,_position);
                            _errors.add(Errors.SpaceExpected);
                            _errorPos = _position;
                        }
                        
                        break;
                    }
                    
                    case State8 :{
                        try{
                            if(inputString[_position] == ' '){
                            _position++;
                        }
                        else if(logicalExpr(inputString)){
                            State = eState.Final;
                        }
                        else{
                            State = eState.Error;
                            //setError(Errors.ExpressionExpected,_position);
                            _errors.add(Errors.ExpressionExpected);
                            _errorPos = _position;
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                            //setError(Errors.ExpressionExpected,_position);
                            _errors.add(Errors.ExpressionExpected);
                            _errorPos = _position;
                        }
                        
                        break;
                    }
                    default :{
                        State = eState.Error;
                        //setError(Errors.UnknownError,_position);
                        _errors.add(Errors.UnknownError);
                        _errorPos = _position;
                        break;
                    }
                        
                }
            }
        }
    }
    
    private static boolean assignmentOp(char [] inputString){
        eState State = eState.Start;
        boolean result = false;
        
        while((State != eState.Error) && (State != eState.Final)){
           
            if(_position>_length){
                State = eState.Error;
                //setError(Errors.OutOfRange,_position - 1);
                _errors.add(Errors.OutOfRange);
                _errorPos = _position - 1;
            }
            else{
                switch(State){
                    case Start :{
                        try{
                            if(identificator(inputString)){
                            State = eState.State1;                           
                        }
                        else{
                            State = eState.Error;
                            //if(_error == Errors.NotError){
                            //    setError(Errors.IDExpected,_position);
                            //}
                            _errors.add(Errors.IDExpected);
                            _errorPos = _position;
                        
                        }
                        }
                        catch(Exception e){
                             State = eState.Error;
                            //if(_error == Errors.NotError){
                            //    setError(Errors.IDExpected,_position);
                            //}
                            _errors.add(Errors.IDExpected);
                            _errorPos = _position;
                        }
                        
                        break;
                    }
                    
                    case State1 :{
                        try{
                            if(inputString[_position] == ' '){
                            _position++;
                        }
                        else if(inputString[_position] == '='){
                            State = eState.State2;
                            _position++;
                        }
                        else{
                            State = eState.Error;
                            //setError(Errors.EqualSignExpected,_position);
                            _errors.add(Errors.EqualSignExpected);
                            _errorPos = _position;
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                            //setError(Errors.EqualSignExpected,_position);
                            _errors.add(Errors.EqualSignExpected);
                            _errorPos = _position;
                        }
                        break;
                    }
                    
                    case State2 :{
                        try{
                            if(inputString[_position] == ' '){
                            _position++;
                        }
                        else if(constant(inputString)){
                            State = eState.Final;
                        }
                        else{
                            State = eState.Error;
                           // if(_error == Errors.NotError){
                             //   setError(Errors.ConstExpected,_position);
                           // }
                           _errors.add(Errors.ConstExpected);
                           _errorPos = _position;
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                           // if(_error == Errors.NotError){
                             //   setError(Errors.ConstExpected,_position);
                           // }
                           _errors.add(Errors.ConstExpected);
                           _errorPos = _position;
                        }
                        break;
                    }
                    
                    default :{
                        State = eState.Error;
                        //setError(Errors.UnknownError,_position);
                        _errors.add(Errors.UnknownError);
                        _errorPos = _position;
                        break;
                    }
                    
                }
            }
        }
        
        if(State == eState.Final){
            result = true;
        }
        return result;
    }
    
    private static boolean logicalExpr(char [] inputString){
        eState State = eState.Start;
        boolean result = false;
        
        while((State != eState.Error) && (State != eState.Final)){
            if(_position>_length){
                State = eState.Error;
                //setError(Errors.OutOfRange,_position - 1);
                _errors.add(Errors.OutOfRange);
                _errorPos = _position-1;
            }
    
            else{
                switch(State){
                    case Start :{
                        try{
                            if(operand(inputString)){
                            State = eState.State1;
                        }
                        else{
                            State = eState.Error;
                            //if(_error == Errors.NotError){
                            //    setError(Errors.OperandExpected,_position);
                            //}
                            _errors.add(Errors.OperandExpected);
                            _errorPos = _position;
                        }
                        }
                        catch(Exception e){
                             State = eState.Error;
                            //if(_error == Errors.NotError){
                            //    setError(Errors.OperandExpected,_position);
                            //}
                            _errors.add(Errors.OperandExpected);
                            _errorPos = _position;
                        }
                        
                        break;
                    }
                    
                    case State1 :{
                        try{
                             if(inputString[_position] == ' '){
                            State = eState.State2;
                            _position++;
                        }
                        else{
                            State = eState.Final;
                        }
                        }
                        catch(Exception e){
                            State = eState.Final;
                        }
                       
                        break;
                    }
                    
                    case State2 :{
                        try{
                            if(inputString[_position] == ' '){
                            _position++;
                        }
                        else if(logicalOp(inputString)){
                            State = eState.State3;
                        }
                        else{
                            State = eState.Error;
                            //if(_error == Errors.NotError){
                            //    setError(Errors.LogicalOperationExpected,_position);
                            //} 
                            _errors.add(Errors.LogicalOperationExpected);
                            _errorPos = _position;
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                            //if(_error == Errors.NotError){
                            //    setError(Errors.LogicalOperationExpected,_position);
                            //} 
                            _errors.add(Errors.LogicalOperationExpected);
                            _errorPos = _position;
                        }
                        
                        break;
                    }
                    
                    case State3 :{
                        try{
                            if(inputString[_position] == ' '){
                            State = eState.State4;
                            _position++;
                        }
                        else{
                            State = eState.Error;
                            //setError(Errors.SpaceExpected,_position);
                            _errors.add(Errors.SpaceExpected);
                            _errorPos = _position;
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                            //setError(Errors.SpaceExpected,_position);
                            _errors.add(Errors.SpaceExpected);
                            _errorPos = _position;
                        }
                        break;
                    }
                    
                    case State4 :{
                        try{
                            if(inputString[_position] == ' '){
                            _position++;
                        }
                        else if(operand(inputString)){
                            State = eState.Final;
                        }
                        else{
                            State = eState.Error;
                           // if(_error == Errors.NotError){
                            //    setError(Errors.OperandExpected,_position);
                            //}     
                            _errors.add(Errors.OperandExpected);
                            _errorPos = _position;
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                           // if(_error == Errors.NotError){
                            //    setError(Errors.OperandExpected,_position);
                            //}     
                            _errors.add(Errors.OperandExpected);
                            _errorPos = _position;
                        }
                        break;
                    }
                    
                    default :{
                        State = eState.Error;
                        //setError(Errors.UnknownError,_position);
                        _errors.add(Errors.UnknownError);
                        _errorPos = _position;
                        break;
                    }
                }
            }
        }

        result = State == eState.Final;
        return result;
    }
    
    private static boolean operand(char [] inputString){
        eState State = eState.Start;
        boolean result = false;
        
        while((State != eState.Error) && (State != eState.Final)){
          
            if(_position>_length){
                State = eState.Error;
                //setError(Errors.OutOfRange,_position - 1);
                _errors.add(Errors.OutOfRange);
                _errorPos = _position-1;

            }
            else{
                switch(State){
                    case Start :{
                        try{
                            if(term(inputString)){
                            State = eState.State1;
                        }
                        else{
                            State = eState.Error;
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                        }
                        break;
                    }
                    
                    case State1 :{
                       try{
                            if(inputString[_position] == ' '){
                            _position++;
                        }
                        else if(ratioOp(inputString)){
                            State = eState.State2;
                        }
                        else{
                            State = eState.Error;
                            //setError(Errors.RatioOperationExpected,_position);
                            _errors.add(Errors.RatioOperationExpected);
                            _errorPos = _position;
                        }
                       }
                       catch(Exception e){
                           State = eState.Error;
                            //setError(Errors.RatioOperationExpected,_position);
                            _errors.add(Errors.RatioOperationExpected);
                            _errorPos = _position;
                       }
                        break;
                    }
                    
                    case State2 :{
                        try{
                            if(inputString[_position] == ' '){
                            _position++;
                        }
                        else if(term(inputString)){
                            State = eState.Final;
                        }
                        else{
                            State = eState.Error;
                            //setError(Errors.TermExpected,_position);
                        }
                        }
                        catch(Exception e){
                            State = eState.Error;
                        }
                        break;
                    }
                    
                    default :{
                        State = eState.Error;
                        //setError(Errors.UnknownError,_position);??
                        _errors.add(Errors.UnknownError);
                        _errorPos = _position;
                        break;
                    }
                }
            }
        }
        if(State == eState.Final){
            result = true;
        }
        return result;
    }
    
    private static boolean term(char [] inputString){
        eState State = eState.Start;
        boolean result = false;
        
        while((State != eState.Error) && (State != eState.Final)){
            if((_position == _length) && (State != eState.Error)){
                State = eState.Final;
            }
            else if(_position>=_length){
                State = eState.Error;
                //setError(Errors.OutOfRange,_position - 1);
                _errors.add(Errors.OutOfRange);
                _errorPos = _position - 1;
            }
            else{
                switch(State){
                    case Start :{
                        try{
                             if(constant(inputString)){ 
                            State = eState.Final;
                        }
                        else if(identificator(inputString)){
                            State = eState.Final;
                        }
                        else{
                            State = eState.Error;                             
                        }
                        }
                        catch(Exception e){
                            State = eState.Error; 
                        }
                       
                        break;
                    }
                    default :{
                        State = eState.Error;
                        //setError(Errors.UnknownError,_position);
                        _errors.add(Errors.UnknownError);
                        _errorPos = _position;
                        break;
                    }
                }
            }
        }
        
        if(State == eState.Final){
            result = true;
        }
        return result;
    }
    
    private static boolean ratioOp(char [] inputString){
        eState State = eState.Start;
        boolean result = false;
        
        while((State != eState.Error) && (State != eState.Final)){
            if((_position == _length) && (State != eState.Error)){
                State = eState.Final;
            }
            else if(_position>=_length){
                State = eState.Error;
                //setError(Errors.OutOfRange,_position - 1);
                _errors.add(Errors.OutOfRange);
                _errorPos = _position - 1;
            }
            else{
                switch(State){
                    case Start :{
                        if(inputString[_position] == '<'){
                            State = eState.State1;
                            _position++;
                        }
                        else if(inputString[_position] == '='){
                            State = eState.Final;
                            _position++;
                        }
                        else if(inputString[_position] == '>'){
                            State = eState.State2;
                            _position++;
                        }
                        else{
                            State = eState.Error;                           
                        }
                        break;
                    }
                    
                    case State1 :{
                        if(inputString[_position] == '='){
                            State = eState.Final;
                            _position++;
                        }
                        else if(inputString[_position] == '>'){
                            State = eState.Final;
                            _position++;
                        }
                        else{
                            State = eState.Final;
                        }
                        break;
                    }
                    
                    case State2 :{
                        if(inputString[_position] == '='){
                            _position++;
                            State = eState.Final;
                        }
                        else{
                            State = eState.Final;
                        }
                        break;
                    }
                    
                    default :{
                        State = eState.Error;
                        //setError(Errors.UnknownError,_position);
                        _errors.add(Errors.UnknownError);
                        _errorPos = _position;
                        break;
                    }
                }
            }
        }
        
        if(State == eState.Final){
            result = true;
        }
        return result;
    }
   
    private static boolean logicalOp(char [] inputString){
        eState State = eState.Start;
        boolean result = false;
        
        while((State != eState.Error) && (State != eState.Final)){
            if((_position == _length) && (State != eState.Error)){
                State = eState.Final;
            }
            else if(_position>=_length){
                State = eState.Error;
                //setError(Errors.OutOfRange,_position - 1);
                _errors.add(Errors.OutOfRange);
                _errorPos = _position - 1;;
            }
            else{
                if(_analyzedString.substring(_position , _position +3).equalsIgnoreCase("and")){
                    State = eState.Final;
                    _position+=3;
                }
                
                else if(_analyzedString.substring(_position , _position +3).equalsIgnoreCase("xor")){
                    State = eState.Final;
                    _position +=3;
                }
                
                else if(_analyzedString.substring(_position , _position +2).equalsIgnoreCase("OR")){
                    State = eState.Final;
                    _position +=2;
                }
                else{
                    State = eState.Error;
                    //setError(Errors.UnknownLogicalOperation,_position);
                    _errors.add(Errors.UnknownLogicalOperation);
                    _errorPos = _position;
                }
            }
        }
        if(State == eState.Final){
            result = true;
        }
        return result;
    }
    
    private static boolean identificator(char [] inputString){
        eState State = eState.Start;
        boolean result = false;
        String id = "";
        while((State != eState.Error) && (State != eState.Final)){            
            if((_position == _length) && (State != eState.Error)){
                State = eState.Final;
            }
            else if(_position>=_length){
                State = eState.Error;
                //setError(Errors.OutOfRange,_position - 1);
                _errors.add(Errors.OutOfRange);
                _errorPos = _position -1;
            }
            else{
                switch(State){
                    case Start :{
                        if((inputString[_position] >= 'a') && (inputString[_position] <= 'z')){
                            id+=inputString[_position];
                            State = eState.State1;
                            _position++;
                        }
                        else{
                            State = eState.Error;                          
                        }
                        break;
                    }
                    
                    case State1 :{
                        if((inputString[_position] >= 'a') && (inputString[_position] <= 'z')||(inputString[_position] >= '0') && (inputString[_position] <= '9')){
                            id+=inputString[_position];
                            _position++;
                        }
                        else{
                            State = eState.Final;
                        }
                        break;
                    }
                    
                    default :{
                        State = eState.Error;
                        //setError(Errors.UnknownError,_position);
                        _errors.add(Errors.UnknownError);
                        _errorPos = _position;
                        break;
                    }
                }
            }
        }
        if(id.length()>8){
            State = eState.Error;
           // setError(Errors.TooMuchID,_position-id.length());
           _errors.add(Errors.TooMuchID);
           _errorPos = _position - id.length();
        }
        else if(id.equalsIgnoreCase("DO") || id.equalsIgnoreCase("AND") || id.equalsIgnoreCase("LOOP") || id.equalsIgnoreCase("UNTIL") || id.equalsIgnoreCase("OR") || id.equalsIgnoreCase("XOR")){
            State = eState.Error;
           // setError(Errors.IDIsReservedCommand,_position - id.length());
           _errors.add(Errors.IDIsReservedCommand);
           _errorPos = _position - id.length();
        }
        if(State == eState.Final){
            _identificators.add(id);
            result = true;
        }
        return result;       
    }
    
    private static boolean constant(char [] inputString){
        eState State = eState.Start;
        boolean result = false;
        String constant = "";
        
        while((State != eState.Error) && (State != eState.Final)){
            if((_position == _length) && (State != eState.Error)){
                State = eState.Final;
            }
            else if(_position>=_length){
                State = eState.Error;
                //setError(Errors.OutOfRange,_position - 1);
                _errors.add(Errors.OutOfRange);
                _errorPos = _position -1;
            }
            else{
                switch(State){
                    case Start :{
                        if(inputString[_position] == '0'){
                            State = eState.State2;
                            constant +=inputString[_position];
                            _position++;
                        }
                        else if(inputString[_position] == '-'){
                            constant += inputString[_position];
                            _position++;
                            State = eState.State1;
                        }
                        else if((inputString[_position] >= '1') && (inputString[_position] <= '9')){
                            constant += inputString[_position];
                            _position++;
                            State = eState.State3;
                        }
                        else{                           
                            State = eState.Error;
                            //setError(Errors.ConstExpected,_position);
                        }
                        break;
                    }
                    
                    case State1:{
                        if(inputString[_position] == '0'){
                            constant +=inputString[_position];
                            _position++;
                            State = eState.State4;
                        }
                        else if((inputString[_position] >= '1') && (inputString[_position] <='9')){
                            constant += inputString[_position];
                            _position++;
                            State = eState.State3;
                        }
                        else{
                            //setError(Errors.ConstExpected,_position);
                            State = eState.Error;
                        }
                        break;
                    }
                    
                    case State2 :{
                        if(inputString[_position] == '.'){
                            constant += inputString[_position];
                            _position++;
                            State = eState.State5;
                        }                       
                        else{
                            State = eState.Final;
                        }
                        break;
                    }
                    
                    case State3 :{
                        if((inputString[_position] >= '0') && (inputString[_position] <='9')){
                            constant += inputString[_position];
                            _position++;
                        }
                        else if(inputString[_position] == '.'){
                            constant +=inputString[_position];
                            _position++;
                            State = eState.State5;
                        }
                        else{
                            State = eState.Final;
                        }
                        break;
                    }
                    
                    case State4 :{
                        if(inputString[_position] == '.'){
                            constant += inputString[_position];
                            _position++;
                            State = eState.State5;
                        }
                        else{
                            State = eState.Error;
                        }
                        break;
                    }
                    
                    case State5 :{
                        if((inputString[_position] >= '0') && (inputString[_position] <='9')){
                            constant += inputString[_position];
                            _position++;
                            State = eState.State6;
                        }
                        else{
                            State = eState.Error;
                        }
                        break;
                    }
                    
                    case State6 :{
                        if((inputString[_position] >= '0') && (inputString[_position] <='9')){
                            constant += inputString[_position];
                            _position++;
                        }
                        else{
                            State = eState.Final;
                        }
                        break;
                    }
                    
                    default :{
                        State = eState.Error;
                       // setError(Errors.UnknownError,_position);
                       _errors.add(Errors.UnknownError);
                       _errorPos = _position;
                    }
                }
            }
        }
        if(State != eState.Error){
            if((Double.parseDouble(constant) > 32767) || (Double.parseDouble(constant) <-32768)){
                State = eState.Error;
                //setError(Errors.TooMuchConst,_position - constant.length());
                _errors.add(Errors.TooMuchConst);
                _errorPos = _position;
            }
            else{
                _constants.add(constant);          
            }
        }
        
       
        
        if(State == eState.Final){
            result = true;
        }
        return result;
    }
   
}


