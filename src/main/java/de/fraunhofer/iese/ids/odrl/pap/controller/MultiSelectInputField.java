package de.fraunhofer.iese.ids.odrl.pap.controller;
import java.util.List;

public class MultiSelectInputField {
    private List<String> input;

    private String op;

    public void setInput(List<String> input){
        this.input = input;
    }
    public List<String> getInput(){
        return this.input;
    }
    public void setOp(String op){
        this.op = op;
    }
    public String getOp(){
        return this.op;
    }
}
