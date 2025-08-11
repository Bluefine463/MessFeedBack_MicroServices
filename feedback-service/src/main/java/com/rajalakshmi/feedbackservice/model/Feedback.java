package com.rajalakshmi.feedbackservice.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "Feedback", schema = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentId;
    private String messId;
    private int rating;
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String comments;
    private String imageUrl;
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = OffsetDateTime.now(); }

    // getters and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getStudentId(){return studentId;}
    public void setStudentId(String s){this.studentId=s;}
    public String getMessId(){return messId;}
    public void setMessId(String m){this.messId=m;}
    public int getRating(){return rating;}
    public void setRating(int r){this.rating=r;}
    public String getComments(){return comments;}
    public void setComments(String c){this.comments=c;}
    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String u){this.imageUrl=u;}
    public OffsetDateTime getCreatedAt(){return createdAt;}
}

