package com.shivajivarma.servicenow.bliffoscope.model;


import java.util.ArrayList;
import java.util.List;

/*
 * Model to representation the matrix of Slime torpedo, Star ship or Bliffiscope raw data etc, using boolean matrix.
 */
public class Image {

    private ArrayList<ArrayList<Boolean>> matrix;
    private int height;
    private int width;
    private String name;

    public Image(){
        this.height = 0;
        this.width = 0;
        this.matrix = new ArrayList<ArrayList<Boolean>>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ArrayList<ArrayList<Boolean>> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<ArrayList<Boolean>> matrix) {
        this.matrix = matrix;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(ArrayList<Boolean> row: this.matrix){
            for(Boolean cell: row){
                if(cell){
                    result.append("+");
                } else {
                    result.append(" ");
                }
            }
            result.append("\n");
        }
        result.append("Name : ").append(this.name).append("; Height : ").append(this.height).append("; Width : ").append(this.width).append("\n");
        return result.toString();
    }

}
