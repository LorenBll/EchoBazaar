package main.java.model;






public class Notification {

    private String title;
    private String description;
    private long creationTime;

    public Notification ( String title , String description ) {
        this.title = title;
        this.description = description;
        this.creationTime = System.currentTimeMillis();
    }

}