package picture;

import jdk.jshell.execution.Util;
import utils.Tuple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Process {

    private Picture picture;
    private Picture[] pictures;
    private int width;
    private int height;


    Process(Picture picture){
        this.picture = picture;
        width = picture.getWidth();
        height = picture.getHeight();
    }

    Process(Picture[] pictures){
        this.pictures = pictures;
    }

    //returns the picture
    public Picture getPicture(){
        return picture;
    }

    //reset the picture
    private void setPicture(Picture picture){
        this.picture = picture;
        this.width = picture.getWidth();
        this.height = picture.getHeight();
    }



    public void invert(){
//        The invert transformation inverts the colour components of each pixel in the given picture.
//        replaces the original intensity value of each primary
//        colour, c, with the intensity (255 - c).

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color before = picture.getPixel(i,j);
                Color after = new Color(255 - before.getRed(),255 - before.getGreen(),255 - before.getBlue());
                picture.setPixel(i,j,after);
            }
        }

    }

    public void grayscale(){

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color before = picture.getPixel(i,j);
                int avg = (before.getBlue() + before.getGreen() + before.getRed()) / 3;
                Color after = new Color(avg,avg,avg);
                picture.setPixel(i,j,after);
            }
        }
    }
//3cc36537
    public void blur(){
        Picture blurredPic = Utils.createPicture(width,height);
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){

                //boundaries
                if(i == 0 || j == 0 || i == width - 1 || j == height - 1){
                    blurredPic.setPixel(i, j, picture.getPixel(i,j));
                }else{
                    Color newColor = getAvgBlur(i,j);
                    blurredPic.setPixel(i, j, newColor);
                }

            }
        }
        setPicture(blurredPic);

    }

    public void flip(String direction){
        switch (direction){
            case "H":
                reverseRows();
                break;
            case "V":
                reverseCols();
                break;
        }
    }

    public void rotate(String angle){

        switch (angle){
            case "90":
                transpose();
                reverseRows();
                break;
            case "180":
                reverseCols();
                reverseRows();
                break;
            case "270":
                transpose();
                reverseCols();
                break;
            default:
                throw new Error("Invalid input! The angle is either 90 or 180 or 270!");
        }
    }

    public void blend(){

        Tuple<Integer,Integer> WH = getMinWH();
        int minWidth = WH.getX().intValue();
        int minHeight = WH.getY().intValue();
        Picture blendedPic = Utils.createPicture(minWidth,minHeight);

        for (int i = 0; i < minWidth; i++){
            for (int j = 0; j < minHeight; j++){
                Color newColor = getAvgBlend(i, j);
                blendedPic.setPixel(i, j, newColor);
            }
        }
        setPicture(blendedPic);
    }

    private Tuple<Integer,Integer> getMinWH(){
        int minWidth = pictures[0].getWidth();
        int minHeight = pictures[0].getHeight();
        for (int i = 0; i < pictures.length; i++){
            minWidth = Math.min(pictures[i].getWidth(), minWidth);
            minHeight = Math.min(pictures[i].getHeight(), minHeight);
        }
        return new Tuple(minWidth,minHeight);
    }

    private Color getAvgBlend(int x, int y){
        int redSum = 0, greenSum = 0, blueSum = 0;
        for (int i = 0; i < pictures.length; i++){
            Color current = pictures[i].getPixel(x,y);
            redSum += current.getRed();
            greenSum += current.getGreen();
            blueSum += current.getBlue();
        }
        return new Color(redSum/pictures.length,greenSum/pictures.length,blueSum/pictures.length);
    }

    /************************ Helper functions *************************/

    //Helper for blur
    //returns the average of the surrounding pixels
    private Color getAvgBlur(int x, int y){
        ArrayList<Color> colors = getSurroundings(x, y);
        Iterator itr = colors.iterator();
        int redSum = 0, greenSum = 0, blueSum = 0;

        while(itr.hasNext()){
            Color temp = (Color) itr.next();
            redSum += temp.getRed();
            greenSum += temp.getGreen();
            blueSum += temp.getBlue();
        }

        int len = colors.size();
        return new Color(redSum/len,greenSum/len,blueSum/len);

    }

    //returns the surrounding pixels
    private ArrayList<Color> getSurroundings(int x, int y){

        ArrayList<Color> result = new ArrayList<>();

        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++){

                result.add(picture.getPixel(i,j));
            }
        }

        return result;
    }



    //Helper for rotate, flip
    private void transpose(){
        Picture newPic = Utils.createPicture(height,width);
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color color = picture.getPixel(i,j);
                newPic.setPixel(j,i,color);
            }
        }
        setPicture(newPic);
    }
    private void reverseCols(){
        for (int i = 0; i < width; i++){
            int start = 0;
            int end = height - 1;
            while (start < end){
                Color temp = picture.getPixel(i,start);
                picture.setPixel(i,start,picture.getPixel(i,end));
                picture.setPixel(i,end,temp);
                start++;
                end--;
            }
        }

    }

    private void reverseRows(){
        for (int j = 0; j < height; j++){
            int start = 0;
            int end = width - 1;
            while (start < end){
                Color temp = picture.getPixel(start,j);
                picture.setPixel(start,j,picture.getPixel(end,j));
                picture.setPixel(end,j,temp);
                start++;
                end--;
            }
        }
    }




}
