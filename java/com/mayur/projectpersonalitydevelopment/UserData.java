package com.mayur.projectpersonalitydevelopment;

public class UserData {
    private String first_name,last_name,password,stream;

    public UserData() {

    }

    public String getStream() { return stream; }

    public void setStream(String stream) { this.stream = stream; }

    public String getFirst_name(){
        return first_name;
    }

    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }

    public String getLast_name(){
        return last_name;
    }

    public void setLast_name(String last_name){
        this.last_name = last_name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
}
