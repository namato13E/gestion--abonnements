package Camping;

import java.util.ArrayList;

public class Planing extends Animation{
    private ArrayList<Animation> animations;

    public void creerPlanning(ArrayList<Animation> listAnimation){
        for(Animation anim:listAnimation){
            System.out.println(anim);
        }
    }
}
