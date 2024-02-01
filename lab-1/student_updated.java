import java.sql.*;
import java.util.Scanner;

class Student_info {
    private String stud_name;
    private String stud_id;
    private String branch;
    private char section;
    private String address;

    public String getStud_name() {
        return stud_name;
    }

    public void setStud_name(String stud_name) {
        this.stud_name = stud_name;
    }

    public String getStud_id() {
        return stud_id;
    }

    public void setStud_id(String stud_id) {
        this.stud_id = stud_id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

class Exam_info {
    private String stud_id;
    private String subject;
    private int mark;
    private String status;

    public String getStud_id() {
        return stud_id;
    }

    public void setStud_id(String stud_id) {
        this.stud_id = stud_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class Operations1 {

    public static void searchById(int studentId) {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM tbl_student_info WHERE stud_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, studentId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        do {
                            String foundStudentId = resultSet.getString("stud_id");
                            String foundStudentName = resultSet.getString("stud_name");
                            String foundStudentBranch = resultSet.getString("branch");
                            String foundStudentSection = resultSet.getString("section");
                            String foundStudentAddress = resultSet.getString("address");
                            System.out.println("Student found:");
                            System.out.println("\n");
                            System.out.println("ID: " + foundStudentId);
                            System.out.println("Name: " + foundStudentName);
                            System.out.println("Branch: " + foundStudentBranch);
                            System.out.println("Section: " + foundStudentSection);
                            System.out.println("Address: " + foundStudentAddress);
                        } while (resultSet.next());
                    } else {
                        System.out.println("Student with ID " + studentId + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchAllStudents() {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM tbl_student_info";
            try (Statement stmt = connection.createStatement()) {

                try (ResultSet rs = stmt.executeQuery(query)) {
                    if (rs.next()) {
                        do {
                            System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4) + "  " + rs.getString(5));
                        } while (rs.next());
                    } else {
                        System.out.println("No Records Available!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchByName(String name) {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM tbl_student_info WHERE stud_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    int i = 1;
                    if (resultSet.next()) {
                        do {
                            String foundStudentId = resultSet.getString("stud_id");
                            String foundStudentName = resultSet.getString("stud_name");
                            String foundStudentBranch = resultSet.getString("branch");
                            String foundStudentSection = resultSet.getString("section");
                            String foundStudentAddress = resultSet.getString("address");
                            System.out.println("Student " + i + " found:");
                            System.out.println("\n");
                            System.out.println("ID: " + foundStudentId);
                            System.out.println("Name: " + foundStudentName);
                            System.out.println("Branch: " + foundStudentBranch);
                            System.out.println("Section: " + foundStudentSection);
                            System.out.println("Address: " + foundStudentAddress);
                            System.out.println("\n");
                            i++;
                        } while (resultSet.next());
                    } else {
                        System.out.println("Student with Name " + name + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void InsertStudent(Student_info student) throws SQLException {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "INSERT INTO tbl_student_info VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, student.getStud_name());
                preparedStatement.setString(2, student.getStud_id());
                preparedStatement.setString(3, student.getBranch());
                preparedStatement.setString(4, String.valueOf(student.getSection()));
                preparedStatement.setString(5, student.getAddress());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println(affectedRows + " Rows Affected");
                } else {
                    System.out.println("Zero Rows Affected!");
                }
                preparedStatement.execute("commit");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void InsertMarks(Exam_info exam) throws SQLException {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "INSERT INTO tbl_exam_info VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, exam.getStud_id());
                preparedStatement.setString(2, exam.getSubject());
                preparedStatement.setInt(3, exam.getMark());
                preparedStatement.setString(4, (exam.getMark() >= 30) ? "Pass" : "Fail");

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println(affectedRows + " Rows Affected");
                } else {
                    System.out.println("Zero Rows Affected!");
                }
                preparedStatement.execute("commit");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void DeleteAllStudents() throws SQLException {
        System.out.println("\nAll rows Deleted from Students Table");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "truncate table tbl_student_info";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void DeleteAllMarks() throws SQLException {
        System.out.println("\nAll rows Deleted from Exam Table");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "truncate table tbl_exam_info";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void DeleteStudentByID(String stud_id) throws SQLException {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "Delete from tbl_student_info where stud_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, stud_id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println(affectedRows + " Rows Affected");
                } else {
                    System.out.println("Zero Rows Affected!");
                }
                preparedStatement.execute("commit");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void DeleteMarksByID(String stud_id) throws SQLException {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "Delete from tbl_exam_info where stud_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, stud_id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println(affectedRows + " Rows Affected");
                } else {
                    System.out.println("Zero Rows Affected!");
                }
                preparedStatement.execute("commit");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void DeleteStudentByName(String stud_name) throws SQLException {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "Delete from tbl_student_info where stud_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, stud_name);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println(affectedRows + " Rows Affected");
                } else {
                    System.out.println("Zero Rows Affected!");
                }
                preparedStatement.execute("commit");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void DeleteMarksByName(String stud_name) throws SQLException {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "delete from tbl_exam_info where tbl_exam_info.stud_id in (select stud_id from tbl_student_info where stud_name=?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, stud_name);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println(affectedRows + " Rows Affected");
                } else {
                    System.out.println("Zero Rows Affected!");
                }
                preparedStatement.execute("commit");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


public class student_updated {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Search by ID");
            System.out.println("2. Search all students");
            System.out.println("3. Search by Name");
            System.out.println("4. Insert Student");
            System.out.println("5. Insert Marks");
            System.out.println("6. Delete All Marks");
            System.out.println("7. Delete All Students");
            System.out.println("8. Delete Marks by ID");
            System.out.println("9. Delete Student by ID");
            System.out.println("10. Delete Student by Name");
            System.out.println("11. Delete Marks by Name");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter student ID to search: ");
                    int searchId = scanner.nextInt();
                    Operations1.searchById(searchId);
                    break;

                case 2:
                    Operations1.searchAllStudents();
                    break;

                case 3:
                    System.out.print("Enter student name to search: ");
                    String searchName = scanner.nextLine();
                    Operations1.searchByName(searchName);
                    break;

                case 4:
                    System.out.print("Enter student details:\nName: ");
                    String studentName = scanner.nextLine();
                    System.out.print("ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Branch: ");
                    String branch = scanner.nextLine();
                    System.out.print("Section: ");
                    char section = scanner.next().charAt(0);
                    scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();

                    Student_info student = new Student_info();
                    student.setStud_name(studentName);
                    student.setStud_id(studentId);
                    student.setBranch(branch);
                    student.setSection(section);
                    student.setAddress(address);

                    Operations1.InsertStudent(student);
                    break;

                case 5:
                    System.out.print("Enter exam details:\nStudent ID: ");
                    String marksId = scanner.nextLine();
                    System.out.print("Subject: ");
                    String marksSubject = scanner.nextLine();
                    System.out.print("Marks: ");
                    int marksValue = scanner.nextInt();

                    Exam_info exam = new Exam_info();
                    exam.setStud_id(marksId);
                    exam.setSubject(marksSubject);
                    exam.setMark(marksValue);

                    Operations1.InsertMarks(exam);
                    break;

                case 6:
                    Operations1.DeleteAllMarks();
                    break;

                case 7:
                    Operations1.DeleteAllStudents();
                    break;

                case 8:
                    System.out.print("Enter student ID to delete marks: ");
                    String deleteMarksId = scanner.nextLine();
                    Operations1.DeleteMarksByID(deleteMarksId);
                    break;

                case 9:
                    System.out.print("Enter student ID to delete: ");
                    String deleteStudentId = scanner.nextLine();
                    Operations1.DeleteStudentByID(deleteStudentId);
                    break;

                case 10:
                    System.out.print("Enter student name to delete: ");
                    String deleteStudentName = scanner.nextLine();
                    Operations1.DeleteStudentByName(deleteStudentName);
                    break;

                case 11:
                    System.out.print("Enter student name to delete marks: ");
                    String deleteMarksName = scanner.nextLine();
                    Operations1.DeleteMarksByName(deleteMarksName);
                    break;

                case 0:
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
