import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
    private static final String FILENAME = "enrollments.ser";

    public static void main(String[] args) {
        Student student = new Student(1, "Alex Lee", 20);
        Course course = new Course("JAVA101", "Java Basics", "Sam Taylor");
        Enrollment enrollment = new Enrollment(student, course, "2023-01-01");

        try (FileOutputStream file = new FileOutputStream(FILENAME);
             ObjectOutputStream output = new ObjectOutputStream(file)) {
            output.writeObject(enrollment);
        } catch (IOException e) {
            System.err.println("Could not save the enrollment: " + e.getMessage());
            return;
        }

        try (FileInputStream file = new FileInputStream(FILENAME);
             ObjectInputStream input = new ObjectInputStream(file)) {
            Enrollment saved = (Enrollment) input.readObject();
            Student savedStudent = saved.getStudent();
            Course savedCourse = saved.getCourse();

            System.out.println("Student: " + savedStudent.getName()
                    + " (ID " + savedStudent.getId() + ", age " + savedStudent.getAge() + ")");
            System.out.println("Course: " + savedCourse.getCourseCode() + " " + savedCourse.getCourseName());
            System.out.println("Instructor: " + savedCourse.getInstructor());
            System.out.println("Enrollment date: " + saved.getEnrollmentDate());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load the enrollment: " + e.getMessage());
        }
    }
}
