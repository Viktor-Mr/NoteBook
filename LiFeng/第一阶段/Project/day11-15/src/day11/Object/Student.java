package day11.Object;

import java.util.Objects;

public class Student {
    private int studentno;
    private String studentname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getStudentno() == student.getStudentno() &&
                Objects.equals(getStudentname(), student.getStudentname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentno(), getStudentname());
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentno=" + studentno +
                ", studentname='" + studentname + '\'' +
                '}';
    }

    public Student() {
        super();
    }
    public Student(int studentno, String studentname) {
        super();
        this.studentno = studentno;
        this.studentname = studentname;
    }
    public int getStudentno() {
        return studentno;
    }
    public void setStudentno(int studentno) {
        this.studentno = studentno;
    }
    public String getStudentname() {
        return studentname;
    }
    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }
}
