package com.ashwingairola.attendfree;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "attendfree";  //Name of our database
    public static final int DB_VERSION = 1;             //Version of the database
    private static SQLiteDatabase db;
    private static Cursor cursor;

    DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE TIMETABLE (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "day TEXT);");

        db.execSQL("CREATE TABLE USER (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "institution TEXT," +
                "designation TEXT," +
                "numOfSubjects INTEGER," +
                "classesPerDay INTEGER," +
                "minPercentage REAL);");

        db.execSQL("CREATE TABLE SUBJECTS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "subject_name TEXT," +
                "attended INTEGER UNSIGNED," +
                "bunked INTEGER UNSIGNED," +
                "cancelled INTEGER UNSIGNED," +
                "total INTEGER UNSIGNED," +
                "percentage REAL UNSIGNED);");

        ContentValues entry = new ContentValues();

        entry.clear();
        entry.put("day", "Monday");
        db.insert("TIMETABLE", null, entry);

        entry.clear();
        entry.put("day", "Tuesday");
        db.insert("TIMETABLE", null, entry);

        entry.clear();
        entry.put("day", "Wednesday");
        db.insert("TIMETABLE", null, entry);

        entry.clear();
        entry.put("day", "Thursday");
        db.insert("TIMETABLE", null, entry);

        entry.clear();
        entry.put("day", "Friday");
        db.insert("TIMETABLE", null, entry);

        entry.clear();
        entry.put("day", "Saturday");
        db.insert("TIMETABLE", null, entry);

        entry.clear();
        entry.put("day", "Sunday");
        db.insert("TIMETABLE", null, entry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public boolean insertUserDetails(String username, String institution, String designation, int numOfSubjects, int classesPerDay, double minPercentage)
    {
        boolean flag = false;
        try
        {
            db = this.getWritableDatabase();
            ContentValues entry = new ContentValues();
            entry.put("username", username);
            entry.put("institution", institution);
            entry.put("designation", designation);
            entry.put("numOfSubjects", numOfSubjects);
            entry.put("classesPerDay", classesPerDay);
            entry.put("minPercentage", minPercentage);
            db.insert("USER", null, entry);

            for(int i=0; i<classesPerDay; ++i)
            {
                db.execSQL("ALTER TABLE TIMETABLE ADD COLUMN class"+(i+1)+" TEXT");
            }

            db.close();
            flag = true;
        }
        catch(SQLiteException e)
        {}

        return flag;
    }

    public boolean insertTimeTableDetails(ArrayList<String> subjects, String day)
    {
        boolean flag = false;
        try
        {
            db = this.getReadableDatabase();
            ContentValues entry = new ContentValues();

            cursor = db.query("USER", new String[]{"classesPerDay"}, null, null, null, null, null);

            int classesPerDay = 0;
            if(cursor.moveToFirst())
                classesPerDay = cursor.getInt(0);

            cursor.close();

            for(int i=0; i<classesPerDay; ++i)
            {
                String column = "class"+(i+1);
                entry.put(column, subjects.get(i));
                db.update("TIMETABLE", entry, "day=?", new String[]{day});
                entry.clear();
            }

            db.close();
            flag = true;
        }
        catch(Exception e)
        {}

        return flag;
    }

    public ArrayList<String> getUserDetails()
    {
        ArrayList<String> userDetails = null;
        try
        {
            db = this.getReadableDatabase();
            cursor = db.query("USER",
                    new String[]{"username, institution, designation, numOfSubjects, classesPerDay"},
                    null,null,null,null,null);

            if(cursor.moveToFirst())
            {
                userDetails = new ArrayList<>();
                userDetails.add(cursor.getString(0));
                userDetails.add(cursor.getString(1));
                userDetails.add(cursor.getString(2));
                userDetails.add(""+cursor.getInt(3));
                userDetails.add(""+cursor.getInt(4));
            }

            db.close();
        }
        catch(Exception e)
        {}

        return userDetails;
    }

    public int getNumOfRows()
    {
        db = this.getReadableDatabase();
        cursor = db.query("USER", new String[]{"_id"}, null, null, null, null, null);
        int numOfRows = cursor.getCount();
        db.close();
        return numOfRows;
    }

    public int getNumOfSubjects()
    {
        int numOfSubjects = 0;
        try
        {
            db = this.getReadableDatabase();
            cursor = db.query("USER",
                    new String[]{"numOfSubjects"},
                    null, null, null, null, null);

            if(cursor.moveToFirst())
                numOfSubjects = cursor.getInt(0);

            db.close();
        }
        catch(Exception e)
        {}

        return numOfSubjects;
    }

    public int getClassesPerDay()
    {
        int classesPerDay = 0;
        try
        {
            db = this.getReadableDatabase();
            cursor = db.query("USER", new String[]{"classesPerDay"}, null, null, null, null, null);

            if(cursor.moveToFirst())
                classesPerDay = cursor.getInt(0);

            db.close();
        }
        catch(Exception e)
        {}

        return classesPerDay;
    }

    public boolean addSubject(String subject, int attended, int bunked, int cancelled)
    {
        boolean flag = false;
        try
        {
            int total = attended+bunked;
            double percentage = (((double)attended/(double)total)*100.0);
            db = this.getReadableDatabase();
            ContentValues subjectValues = new ContentValues();
            subjectValues.put("subject_name", subject);
            subjectValues.put("attended", attended);
            subjectValues.put("bunked", bunked);
            subjectValues.put("cancelled", cancelled);
            subjectValues.put("total", total);
            subjectValues.put("percentage", percentage);
            db.insert("SUBJECTS", null, subjectValues);
            db.close();

            flag = true;
        }
        catch(Exception e)
        {}

        return flag;
    }

    public ArrayList<String> getAllSubjects()
    {
        ArrayList<String> subjects = new ArrayList<>();
        try
        {

            db = this.getReadableDatabase();
            cursor = db.query("SUBJECTS", new String[]{"subject_name"}, null, null, null, null, null);

            while(cursor.moveToNext())
            {
                subjects.add(cursor.getString(0));
            }
        }
        catch(Exception e)
        {}

        return subjects;
    }

    public ArrayList<Subject> getAllSubjectDetails()
    {
        ArrayList<Subject> subjectList = null;
        try
        {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM SUBJECTS", new String[]{});
            subjectList = new ArrayList<>();
            while(cursor.moveToNext())
            {
                Subject subject = new Subject();
                subject.setSubjectId(cursor.getInt(0));
                subject.setSubjectName(cursor.getString(1));
                subject.setAttended(cursor.getInt(2));
                subject.setBunked(cursor.getInt(3));
                subject.setCancelled(cursor.getInt(4));
                subject.setTotal(cursor.getInt(5));
                subject.setPercentage(cursor.getDouble(6));
                subjectList.add(subject);
            }
            db.close();
        }
        catch(Exception e)
        {}

        return subjectList;
    }

    public Subject getLowestAttendanceSubject()
    {
        Subject subject = null;
        try
        {
            db = this.getReadableDatabase();
            cursor = db.query("SUBJECTS", new String[]{"subject_name", "percentage"}, null, null, null, null, "percentage ASC");

            subject = new Subject();
            if(cursor.moveToFirst())
            {
                subject.setSubjectName(cursor.getString(0));
                subject.setPercentage(cursor.getDouble(1));
            }

            db.close();
        }
        catch(Exception e)
        {}

        return subject;
    }

    public double getMinPercentage()
    {
        double minPercentage = 0.0;
        try
        {
            db = this.getReadableDatabase();
            cursor = db.query("USER", new String[]{"minPercentage"}, null, null, null, null, null);
            if(cursor.moveToFirst())
            {
                minPercentage = cursor.getDouble(0);
            }
            db.close();
        }
        catch(Exception e)
        {}
        return minPercentage;
    }

    public ArrayList<String> getSchedule(String day)
    {
        ArrayList<String> schedule = null;
        try
        {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM TIMETABLE WHERE day=?", new String[]{day});
            int columns = cursor.getColumnCount();
            if(cursor.moveToFirst())
            {
                schedule = new ArrayList<>();
                for(int i=2; i<columns; ++i)
                {
                    String subject = cursor.getString(i);
                    schedule.add(subject);
                }
            }
            db.close();
        }
        catch(Exception e)
        {}
        return schedule;
    }

    public Subject getSubject(String subjectName)
    {
        Subject subject = null;
        try
        {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT attended, bunked, cancelled, total, percentage FROM SUBJECTS WHERE subject_name=?", new String[]{subjectName});
            if(cursor.moveToFirst())
            {
                subject = new Subject();
                subject.setAttended(cursor.getInt(0));
                subject.setBunked(cursor.getInt(1));
                subject.setCancelled(cursor.getInt(2));
                subject.setTotal(cursor.getInt(3));
                subject.setPercentage(cursor.getDouble(4));
            }
            db.close();
        }
        catch(Exception e)
        {}
        return subject;
    }

    public boolean updateAttendance(ArrayList<String> schedule, ArrayList<String> statusList)
    {
        boolean flag = false;
        try
        {
            System.out.println("Schedule length = "+schedule.size());
            System.out.println("Status length = "+statusList.size());
            for(int i=0; i<schedule.size(); ++i)
                System.out.println(schedule.get(i)+" "+statusList.get(i));

            db = this.getReadableDatabase();
            for(int i=0; i<schedule.size(); ++i)
            {
                String subject = schedule.get(i);
                String status = statusList.get(i);

                db.execSQL("UPDATE SUBJECTS SET "+ status +"="+ status+"+1 WHERE subject_name=?", new String[]{subject});
                cursor = db.rawQuery("SELECT attended, bunked FROM SUBJECTS WHERE subject_name=?", new String[]{subject});

                int attended=0, bunked=0;
                if(cursor.moveToFirst())
                {
                    attended = cursor.getInt(0);
                    bunked = cursor.getInt(1);
                }
                int total = attended+bunked;
                double percentage = (double)attended/(double)total*100.0;

                db.execSQL("UPDATE SUBJECTS SET total="+total+" WHERE subject_name=?", new String[]{subject});
                db.execSQL("UPDATE SUBJECTS SET percentage="+percentage+" WHERE subject_name=?", new String[]{subject});
            }
            db.close();
            flag = true;
        }
        catch(Exception e)
        {}
        return flag;
    }
}