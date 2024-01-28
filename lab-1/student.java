import java.sql.*;
import java.util.Scanner;

class operations {
    
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
            e.printStackTrace(); // Handle the exception based on your application's needs
        }
    } 
    

    public static void searchAllStudents() {
        System.out.println("\n");
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM tbl_student_info";
            try (Statement stmt=connection.createStatement();) {

                try (ResultSet rs=stmt.executeQuery(query);) {
                    if(rs.next()){
                    while(rs.next())
                        System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5));
                    }
                    else{
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
                        System.out.println("Student "+i+" found:");
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
        e.printStackTrace(); // Handle the exception based on your application's needs
    }
} 


public static void InsertStudent(String Name, String Stud_id, String branch, char Section, String address) throws SQLException {
    System.out.println("\n");
    try (Connection connection = DatabaseConnector.getConnection()) {
        String query = "INSERT INTO tbl_student_info values (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, Name);
            preparedStatement.setString(2, Stud_id);
            preparedStatement.setString(3, branch);
            preparedStatement.setString(4, String.valueOf(Section));
            preparedStatement.setString(5, address);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(affectedRows + " Rows Affected");
            } else {
                System.out.println("Zero Rows Affected!");
            }
            preparedStatement.executeQuery("commit");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception based on your application's needs
        }
    }
}
public static void InsertMarks(String stu_id , String subject , int marks ) throws SQLException{
    System.out.println("\n");
    try (Connection connection = DatabaseConnector.getConnection()) {
        String query = "INSERT INTO tbl_exam_info values (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, stu_id);
            preparedStatement.setString(2, subject);
            preparedStatement.setInt(3, marks);
            preparedStatement.setString(4, (marks >= 30)?"Pass":"Fail");

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(affectedRows + " Rows Affected");
            } else {
                System.out.println("Zero Rows Affected!");
            }
            preparedStatement.executeQuery("commit");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception based on your application's needs
        }
    }
}
public static void DeleteAllStudents() throws SQLException {
    System.out.println("\nAll rows Deleted from Students Table");
    try (Connection connection = DatabaseConnector.getConnection()) {
        String query = "truncate table tbl_student_info";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
}
}

public static void DeleteStudentByID(String Stud_id) throws SQLException {
    System.out.println("\n");
    try (Connection connection = DatabaseConnector.getConnection()) {
        String query = "Delete from tbl_student_info where stud_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, Stud_id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " Rows Affected");
            } else {
                System.out.println("Zero Rows Affected!");
            }
            preparedStatement.executeQuery("commit");
        
        } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception based on your application's needs
        }
}
}

public static void DeleteMarksByID(String Stud_id) throws SQLException {
    System.out.println("\n");
    try (Connection connection = DatabaseConnector.getConnection()) {
        String query = "Delete from tbl_exam_info where stud_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, Stud_id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " Rows Affected");
            } else {
                System.out.println("Zero Rows Affected!");
            }
            preparedStatement.executeQuery("commit");
    
        } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception based on your application's needs
        }
}
}
public static void DeleteStudentByName(String Stud_name) throws SQLException {
    System.out.println("\n");
    try (Connection connection = DatabaseConnector.getConnection()) {
        String query = "Delete from tbl_student_info where stud_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, Stud_name);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " Rows Affected");
            } else {
                System.out.println("Zero Rows Affected!");
            }
            preparedStatement.executeQuery("commit");

        } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception based on your application's needs
        }
}
}
public static void DeleteMarksByName(String Stud_Name) throws SQLException {
    System.out.println("\n");
    try (Connection connection = DatabaseConnector.getConnection()) {
        String query = "delete from tbl_exam_info where tbl_exam_info.stud_id in (select stud_id from tbl_student_info where stud_name=?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, Stud_Name);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " Rows Affected");
            } else {
                System.out.println("Zero Rows Affected!");
            }
            preparedStatement.executeQuery("commit");

        } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception based on your application's needs
        }
}
}
}



public class student {
    public static void main(String args[]) throws SQLException {
        // step1 load the driver class
        // operations.searchById(101);
        // operations.searchAllStudents();
        // operations.searchByName("Abhishek");
        //operations.InsertStudent("Roshan111", "10", "C1", "A", "Birgj11");
        //operations.InsertMarks("10311","TOC",58);
        //operations.DeleteAllMarks();
        //operations.DeleteAllStudents();
        //operations.DeleteMarksByID("10311");
        //operations.DeleteStudentByID("10311");
        //operations.DeleteStudentByName("Roshan111");
        //operations.DeleteMarksByName("Roshan11");
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n");
            System.out.println("Menu:");
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
                    operations.searchById(searchId);
                    break;
                case 2:
                    operations.searchAllStudents();
                    break;
                case 3:
                    System.out.print("Enter student name to search: ");
                    String searchName = scanner.nextLine();
                    operations.searchByName(searchName);
                    break;
                case 4:
                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter branch: ");
                    String branch = scanner.nextLine();
                    System.out.print("Enter section: ");
                    char Section = scanner.next().charAt(0);
                    scanner.nextLine(); 
                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();
                    operations.InsertStudent(studentName, studentId, branch, Section, address);
                    break;
                case 5:
                    System.out.print("Enter student ID: ");
                    String marksId = scanner.nextLine();
                    System.out.print("Enter subject: ");
                    String marksSubject = scanner.nextLine();
                    System.out.print("Enter marks: ");
                    int marksValue = scanner.nextInt();
                    operations.InsertMarks(marksId, marksSubject, marksValue);
                    break;
                case 6:
                    operations.DeleteAllMarks();
                    break;
                case 7:
                    operations.DeleteAllStudents();
                    break;
                case 8:
                    System.out.print("Enter student ID to delete marks: ");
                    String deleteMarksId = scanner.nextLine();
                    operations.DeleteMarksByID(deleteMarksId);
                    break;
                case 9:
                    System.out.print("Enter student ID to delete: ");
                    String deleteStudentId = scanner.nextLine();
                    operations.DeleteStudentByID(deleteStudentId);
                    break;
                case 10:
                    System.out.print("Enter student name to delete: ");
                    String deleteStudentName = scanner.nextLine();
                    operations.DeleteStudentByName(deleteStudentName);
                    break;
                case 11:
                    System.out.print("Enter student name to delete marks: ");
                    String deleteMarksName = scanner.nextLine();
                    operations.DeleteMarksByName(deleteMarksName);
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
