package com.example.korepetytio.client;

public class ClientPassword {
    private String password;

    public ClientPassword(String password) {
    }

    public boolean validatePassword(String password){
        boolean correctPassword = false;
        boolean whitespace = false;
        boolean specialCounter = false;
        boolean correctLength = password.length() >= 8 && password.length() <= 32;
        for(int i = 0; i< password.length(); i++){
            char l = password.charAt(i);
            if(!Character.isWhitespace(l)) {
                whitespace = true;}
            if (l == '$' || l == '#' || l == '?' || l == '!' || l == '_' || l == '=' || l == '%') {
                specialCounter = true;}
            }
        if(!whitespace && specialCounter && correctLength){
            correctPassword = true;
        }
        return correctPassword;
    }
}
