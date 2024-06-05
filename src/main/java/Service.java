import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Collection;
import java.util.ArrayList;

class ParseError extends Exception { }

public class Service {

    public void addStudent(Student student) throws IOException {
        try (var b = new BufferedWriter(new FileWriter("db.txt", true))) {
            b.append(student.ToString());
            b.newLine();
        }
    }

    public Collection<Student> getStudents() throws IOException, ParseError {
        var ret = new ArrayList<Student>();
        try (var reader = new BufferedReader(new FileReader("db.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    ret.add(Student.Parse(line));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    throw new ParseError();
                }
            }
        }
        return ret;
    }

    public Student findStudentByName(String name) throws IOException, ParseError { 
        var students = this.getStudents();
        for (Student current : students) {
            if (current.GetName().equals(name))
                return current;
        }
        return null;
    }
}