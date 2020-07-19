package com.sila.eth.util;

import java.util.Map;
import java.util.Stack;

/**
 * *  Created by Adewale Adeleye on 2020-01-01
 **/
public class JavaStringReverse {
    public static void main(String[] args){
        System.out.println(reverseVowel("Computer"));
    }


    public static boolean isVowel(char c){
        return c=='a' || c=='i'|| c=='e'||c=='o'||c=='u';
    }

    public static String reverseVowel(String str){
        char[] charString = str.toCharArray();
        //Declare a new stack variable
        Stack<Character> stack = new Stack<>();
        //Iterate through the list and push the vowels to
        for(int i=0; i<charString.length;i++){
            if(isVowel(charString[i])){
                stack.push(charString[i]);
            }
        }

        for(int i=0; i<charString.length;i++){
            if(isVowel(charString[i])){
                charString[i]=stack.pop();
            }
        }
        return new String(charString);
    }


}
