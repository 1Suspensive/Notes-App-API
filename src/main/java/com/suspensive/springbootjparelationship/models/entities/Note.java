package com.suspensive.springbootjparelationship.models.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tittle;
    @Column(nullable = false)
    private String content;

    private List<String> tags;

    public Note(){
        
    }

    public Note(String tittle, String content, List<String> tags) {
        this.tittle = tittle;
        this.content = content;
        this.tags = tags;
    }

    public Note(String tittle,String content){
        this.tittle = tittle;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Note [id=" + id + ", tittle=" + tittle + ", content=" + content + ", tags=" + tags + "]";
    }
    
}
