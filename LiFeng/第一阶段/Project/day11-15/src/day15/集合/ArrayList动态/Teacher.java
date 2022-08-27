package day15.集合.ArrayList动态;

public class Teacher {
    private String tname;
    private String title;
    public String getTname() {
        return tname;
    }
    public void setTname(String tname) {
        this.tname = tname;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Teacher(String tname, String title) {
        super();
        this.tname = tname;
        this.title = title;
    }
    public Teacher() {

    }
    public String toString(){
        return "教师姓名:"+this.tname+",职称:"+this.title;
    }

}