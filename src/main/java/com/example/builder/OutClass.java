package com.example.builder;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OutClass {
    
    private int id;
    
    private String name;
    
    public Builder build(){
        return new Builder();
    }
    
    @Setter
    @Getter
    static class Builder{
        private int id;
        private String name;
        
        public Builder id(int id){
            this.setId(id);
            return this;
        }
        public Builder name(String name){
            this.setName(name);
            return this;
        }
        public OutClass builder(){
            OutClass oc = new OutClass();
            oc.setId(this.id);
            oc.setName(this.name);
            return oc;
        }
    }
    
    public static void main(String[] args) {
        OutClass oc = new OutClass().build().id(11).name("aaa").builder();
        System.out.println(oc);
    }
    
}
