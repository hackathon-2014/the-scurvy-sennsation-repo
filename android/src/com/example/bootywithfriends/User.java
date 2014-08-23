package com.example.bootywithfriends;


public class User {

    public String uid;
    public String name;
    
    public User(String guid, String name){
        this.uid = guid;
        this.name = name;
    }
    
    public static User[] defaultUsers = new User[]{
        new User("F655CFC7-D6AF-4091-B3C6-61AFE838B61A", "Phillip"),
        new User("86365E2F-3C03-4DDE-9F01-34AC2F9B04EA", "Michael"),
        new User("E409F57C-106A-4E70-8DE7-85AC90FC60AE", "Matthew")
    };
    
    public static User fromUid(String uid) {
        for(User user : defaultUsers) {
            if(user.uid.equals(uid)) {
                return user;
            }
        }
        return defaultUsers[0];
    }
    
    public static User fromName(String name) {
        for(User user : defaultUsers) {
            if(user.name.equals(name)) {
                return user;
            }
        }
        return defaultUsers[0];
    }
    
}
