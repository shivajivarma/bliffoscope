package com.shivajivarma.servicenow.bliffoscope.model;

public class Target {

    private String type;
    private Coordinates coordinates;
    private float percentageMatch;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public float getPercentageMatch() {
        return percentageMatch;
    }

    public void setPercentageMatch(float percentageMatch) {
        this.percentageMatch = percentageMatch;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        result.append("type: \"").append(this.type).append("\"");
        if (this.getCoordinates() != null) {
            result.append(", coordinates : {x: ").append(this.getCoordinates().getX()).append(", y: ").append(this.getCoordinates().getY()).append("}");
        }
        result.append(", percentageMatch: ").append(this.percentageMatch);
        result.append("}");
        return result.toString();
    }
}
