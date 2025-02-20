package models;

import java.util.Date;

public class Member {
    private int id;
    private String lastName;
    private String firstName;
    private Date registrationDate;
    private String phoneNumber;
    private boolean subscriptionStatus;

    // Constructor
    public Member(int id, String lastName, String firstName, Date registrationDate, 
                 String phoneNumber, boolean subscriptionStatus) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.registrationDate = registrationDate;
        this.phoneNumber = phoneNumber;
        this.subscriptionStatus = subscriptionStatus;
    }

    // No-argument constructor
    public Member() {
        this.id = 0;
        this.lastName = "";
        this.firstName = "";
        this.registrationDate = new Date();
        this.phoneNumber = "";
        this.subscriptionStatus = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public boolean isSubscriptionStatus() { return subscriptionStatus; }
    public void setSubscriptionStatus(boolean subscriptionStatus) { this.subscriptionStatus = subscriptionStatus; }
}