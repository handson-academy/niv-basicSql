package com.handson.basic.models;

public enum StudentSortField {
    id("id") ,
    createdAt ("created_at"),
    fullName ("fullname"),
    birthDate ("birth_date"),
    satScore ("sat_score"),
    graduationScore ("graduation_score");

    public final String fieldName;
    private StudentSortField(String fieldName) {
        this.fieldName = fieldName;
    }
}
