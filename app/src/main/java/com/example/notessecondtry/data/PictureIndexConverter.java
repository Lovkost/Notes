package com.example.notessecondtry.data;

import com.example.notessecondtry.R;

import java.util.Random;

public class PictureIndexConverter {

    private static Random rnd = new Random();
    private static Object syncObj = new Object();

    private static int[] picIndex = {R.drawable.chili,
            R.drawable.bull64,
            R.drawable.pyramids32,
            R.drawable.german,
            R.drawable.anubis32,
            R.drawable.origami64,
    };

    public static int randomPictureIndex(){
        synchronized (syncObj){
            return rnd.nextInt(picIndex.length);
        }
    }

    public static int getPictureByIndex(int index){
        if (index < 0 || index >= picIndex.length){
            index = 0;
        }
        return picIndex[index];
    }

    public static int getIndexByPicture(int picture){
        for(int i = 0; i < picIndex.length; i++){
            if (picIndex[i] == picture){
                return i;
            }
        }
        return 0;
    }
}
