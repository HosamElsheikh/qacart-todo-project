package com.qacart.todo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Todo {
    @JsonProperty("isCompleted")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean isCompleted;
    @JsonProperty("_id")
    private String id;
    private String item;
    private String userID;
    private String createdAt;
    @JsonProperty("__v")
    private String v;
    public Todo(){

    }
    public Todo(boolean isCompleted, String item){
        this.isCompleted = isCompleted;
        this.item = item;
    }
    public Todo(String item){
        this.item = item;
    }
    @JsonProperty("isCompleted")
    public boolean getIsCompleted() {
        return isCompleted;
    }
    @JsonProperty("isCompleted")

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }
    @JsonProperty("_id")

    public String getId() {
        return id;
    }
    @JsonProperty("_id")

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    @JsonProperty("__v")

    public String getV() {
        return v;
    }
    @JsonProperty("__v")

    public void setV(String v) {
        this.v = v;
    }
}
