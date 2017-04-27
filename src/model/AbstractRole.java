package model;

import java.io.Serializable;

public abstract class AbstractRole implements Role, Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -4307407622136444656L;
    
    private String myName;
    
    public AbstractRole(String s) {
        myName = s;
    }
    

    public String getUser() {
        return myName;
    }
}
