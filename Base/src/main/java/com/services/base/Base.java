package com.services.base;

public class Base {
    public static void setProfile(String profile) {
        System.setProperty("spring.profiles.active", profile);
        System.out.println("Using Profile="+profile);
    }

    public static void setApplicationName(String applicationName) {
        System.setProperty("spring.application.name", applicationName);
    }

    public static void configureApp(String applicationName) {
        setApplicationName(applicationName);
        setProfile();
    }
    public static void setProfile() {
        String profile = System.getProperty("spring.profiles.active");
        if (profile != null) {
            setProfile(profile);
        } else {
            setProfile("local");
        }
    }
}
