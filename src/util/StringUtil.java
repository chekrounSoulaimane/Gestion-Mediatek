/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Soulaimane
 */
public class StringUtil {
    
    public static boolean isNumeric(String value) {
        char[] chars = value.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean containsNumber(String value) {
        char[] chars = value.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
