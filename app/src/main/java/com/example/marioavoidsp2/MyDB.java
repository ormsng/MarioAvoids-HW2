package com.example.marioavoidsp2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyDB {
    private ArrayList<Record> records = new ArrayList<>();

    public MyDB() {  }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public MyDB setRecords(ArrayList<Record> records){
        this.records = records;
        return this;
    }

    public void sortRecords(){
        if (records.size() >= 2)
            Collections.sort(records, new MyDB.CustomComparator());
    }
    public static class CustomComparator implements Comparator<Record> {
        @Override
        public int compare(Record record, Record t1) {
            return t1.getScore() - record.getScore();
        }
    }
}