/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcos
 */
public class Chat {
    private final List<String> chat;
    
    public Chat(){
        chat = new ArrayList<>();
    }
    
    public void novaMensagem(String mens){
        chat.add(mens+"\n");
    }
    
    public String getMensagem(){
        StringBuilder str = new StringBuilder();
        for (String string : chat) {
            str.append(string);
        }
        return str.toString();
    }
}
