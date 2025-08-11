package com.rajalakshmi.feedbackservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FeedbackRequest {
    private String studentId;
    private String messId;

    @NotNull
    @Min(1) @Max(5)
    private Integer rating;

    private String comments;
    private String imageUrl;

    // getters/setters
    public String getStudentId(){return studentId;}
    public void setStudentId(String s){this.studentId=s;}
    public String getMessId(){return messId;}
    public void setMessId(String m){this.messId=m;}
    public Integer getRating(){return rating;}
    public void setRating(Integer r){this.rating=r;}
    public String getComments(){return comments;}
    public void setComments(String c){this.comments=c;}
    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String u){this.imageUrl=u;}
}