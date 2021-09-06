package com.mobdeve.s14.espiritu.finez.perez.project.dao;

public interface ProfileDAO {
    boolean addProfile(ProfileModel profile);
    ProfileModel checkExisting(String user);
}
