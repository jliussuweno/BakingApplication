package com.bca.bakingapp.model;

public class Step {

    String idStep;
    String description;
    String longDescription;
    String linkStep;

    public Step(String idStep, String description, String longDescription, String linkStep){
        this.idStep = idStep;
        this.description = description;
        this.longDescription = longDescription;
        this.linkStep = linkStep;
    }

    public String getIdStep() {
        return idStep;
    }

    public void setIdStep(String idStep) {
        this.idStep = idStep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getLinkStep() {
        return linkStep;
    }

    public void setLinkStep(String linkStep) {
        this.linkStep = linkStep;
    }


}
