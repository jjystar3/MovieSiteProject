package com.example.demo.openapi.job;

import java.util.ArrayList;

import lombok.ToString;

@ToString
class Cast{
    public boolean adult;
    public int gender;
    public int id;
    public String known_for_department;
    public String name;
    public String original_name;
    public double popularity;
    public String profile_path;
    public int cast_id;
    public String character;
    public String credit_id;
    public int order;
}

@ToString
class Crew{
    public boolean adult;
    public int gender;
    public int id;
    public String known_for_department;
    public String name;
    public String original_name;
    public double popularity;
    public String profile_path;
    public String credit_id;
    public String department;
    public String job;
}

@ToString
class RootCredit{
    public int id;
    public ArrayList<Cast> cast;
    public ArrayList<Crew> crew;
}

