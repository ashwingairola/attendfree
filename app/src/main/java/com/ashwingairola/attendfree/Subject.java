package com.ashwingairola.attendfree;

public class Subject
{
    private int subjectId, attended, bunked, cancelled, total;
    private String subjectName;
    private double percentage;

    public int getCancelled()
    {
        return cancelled;
    }

    public void setCancelled(int cancelled)
    {
        this.cancelled = cancelled;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public int getBunked()
    {
        return bunked;
    }

    public void setBunked(int bunked)
    {
        this.bunked = bunked;
    }

    public int getAttended()
    {
        return attended;
    }

    public void setAttended(int attended)
    {
        this.attended = attended;
    }

    public int getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
