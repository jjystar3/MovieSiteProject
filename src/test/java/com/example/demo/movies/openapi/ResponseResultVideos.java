package com.example.demo.movies.openapi;

import java.util.ArrayList;
import java.util.Date;

import lombok.ToString;

@ToString
class ResultVideos{
    public String iso_639_1;
    public String iso_3166_1;
    public String name;
    public String key;
    public String site;
    public int size;
    public String type;
    public boolean official;
    public Date published_at;
    public String id;
}

@ToString
class RootVideos{
    public int id;
    public ArrayList<Result> results;
}