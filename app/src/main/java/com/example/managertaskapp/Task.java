package com.example.managertaskapp;

public class Task {
    private int id;
    private String title;
    private String description;
    private long dueDate;

    public Task(String title, String description, long dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public long getDueDate() { return dueDate; }
    public void setDueDate(long dueDate) { this.dueDate = dueDate; }
}
