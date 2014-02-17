package JDBC_NB;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyDB {
    private Connection con;
    
    
   private void initialization(String name){
   try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + name);
            PreparedStatement teachers = con.prepareStatement("create table if not exists 'Teachers' ('PersonalID' INTEGER , 'FIO' text, 'Rang' text);");
            PreparedStatement students = con.prepareStatement("create table if not exists 'Students' ('PersonalID' INTEGER, 'FIO' text, 'Course' INTEGER, 'Faculty' text, 'Rating' DOUBLE);");
            PreparedStatement disciplines = con.prepareStatement("create table if not exists 'Disciplines' ('ID' INTEGER, 'Name' text, 'NumberOfSt' INTEGER);");
            PreparedStatement listeners = con.prepareStatement("create table if not exists 'Listeners' ('DisciplineID' INTEGER, 'StudentID' INTEGER);");
            PreparedStatement lecturers = con.prepareStatement("create table if not exists 'Lecturers' ('DisciplineID' INTEGER, 'TeacherID' INTEGER);");
            int resTc = teachers.executeUpdate();
            int resSt = students.executeUpdate();
            int resDsc = disciplines.executeUpdate();
            int resLst = listeners.executeUpdate();
            int resLct = lecturers.executeUpdate();
        }catch(ClassNotFoundException e){
            System.out.println("Не знайшли драйвер JDBC");
            e.printStackTrace();
            System.exit(0);
        }catch (SQLException e){
            System.out.println("Не вірний SQL запит");
            e.printStackTrace();
        }     
   }
   
   private void insertTeacherData(int ID, String FIO, String Rang){
        try {
            PreparedStatement statement = con.prepareStatement("INSERT INTO Teachers(PersonalID, FIO, Rang) VALUES(?,?,?)");
            statement.setInt(1, ID);
            statement.setString(2, FIO);
            statement.setString(3, Rang);
            int result = statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
           System.out.println("Не вірний SQL запит на вставку");
           ex.printStackTrace();
        }
   }
   
   private void removeTeacherByID(int ID){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Teachers WHERE PersonalID = ?");
            st.setInt(1, ID);
            int result = st.executeUpdate();
            st.close();
            System.out.println("Видалено викладача ID = "+ ID);
        } catch (SQLException ex) {
             System.out.println("Не вірний SQL запит на видалення");
           ex.printStackTrace();
        }
   }
   
   private void getTeacherDataByID(int ID){
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM Teachers WHERE PersonalID = ?");
            st.setInt(1, ID);
            ResultSet rs = st.executeQuery();
            int rowCounter = 0;
            while(rs.next()){
                System.out.println("Викладач: ID - "+rs.getInt("PersonalID")+", ПІБ: "+rs.getString("FIO")+", Звання: "+rs.getString("Rang"));  
                rowCounter++;
            }
            if(rowCounter==0){
                System.out.println("Невірний ID!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   private void showAllTeachers(){
       try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Teachers");
            int rowCounter = 0;
            System.out.println("Викладачі:");
            while(rs.next()){
                System.out.println("Викладач: ID - "+rs.getInt("PersonalID")+", ПІБ: "+rs.getString("FIO")+", Звання: "+rs.getString("Rang"));  
                rowCounter++;
            }
            if(rowCounter==0){
                System.out.println("Не знайдено жодного викладача.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   private void insertStudentsData(int ID, String FIO, int course, String faculty, double rating){
        try {
            PreparedStatement st = con.prepareStatement("INSERT INTO Students(PersonalID,FIO,Course,Faculty,Rating) VALUES(?,?,?,?,?)");
            st.setInt(1, ID);
            st.setString(2, FIO);
            st.setString(4, faculty);
            st.setInt(3, course);
            st.setDouble(5, rating);
             
            int result = st.executeUpdate();
             
            st.close();
        } catch (SQLException ex) {
           System.out.println("Не вірний SQL запит на додавання");
           ex.printStackTrace();
        }
       
   }
 
   
   private void removeStudentByID(int ID){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Students WHERE PersonalID = ?");
            st.setInt(1, ID);
            
            int result = st.executeUpdate();
            
            System.out.println("Видалено студента ID = "+ ID);
            st.close();
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на видалення");
           ex.printStackTrace();
        }
       
   }
   
   private void getStudentDataByID(int ID){
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM Students WHERE PersonalID = ?");
            st.setInt(1, ID);
            ResultSet rs = st.executeQuery();
            int rowCounter = 0;
            while(rs.next()){
                System.out.println("Студент: ID - "+rs.getInt("PersonalID")+", ПІБ: "+rs.getString("FIO")+", курс: "+rs.getInt("Course")+", факультет: "+rs.getString("Faculty")+", рейтинг: "+rs.getDouble("Rating"));  
                rowCounter++;
            }
            if(rowCounter==0){
                System.out.println("Невірний ID!");
            }
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            ex.printStackTrace();
        }
       
   }
   
   private void showAllStudents(){
       try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Students");
            int rowCounter = 0;
            System.out.println("Студенти:");
            while(rs.next()){
                System.out.println("Студент: ID - "+rs.getInt("PersonalID")+", ПІБ: "+rs.getString("FIO")+", курс: "+rs.getInt("Course")+", факультет: "+rs.getString("Faculty")+", рейтинг: "+rs.getDouble("Rating"));  
                rowCounter++;
            }
            if(rowCounter==0){
                System.out.println("Не знайдено жодного студента.");
            }
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            ex.printStackTrace();
        }
       
   }
   
   private void insertDisciplineData(int ID, String name, int numberOfSt){
        try {
            PreparedStatement st = con.prepareStatement("INSERT INTO Disciplines(ID, Name, NumberOfSt) VALUES(?,?,?)");
            st.setInt(1, ID);
            st.setString(2, name);
            st.setInt(3, numberOfSt);
            
            int result = st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
           System.out.println("Не вірний SQL запит на додавання");
           ex.printStackTrace();
        }
   }
   
   private void removeDisciplineByID(int ID){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Disciplines WHERE ID = ?");
            st.setInt(1, ID);
            
            int result = st.executeUpdate();
            st.close();
            System.out.println("Видалено дисципліну ID = ");
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на видалення");
           ex.printStackTrace();
        }
   }
   
   private void getDisciplineDataByID(int ID){
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM Disciplines WHERE PersonalID = ?");
            st.setInt(1, ID);
            ResultSet rs = st.executeQuery();
            int rowCounter = 0;
            while(rs.next()){
                System.out.println("Дисципліна: ID - "+rs.getInt("ID")+", Назва: "+rs.getString("Name")+", Кількість студентів: "+rs.getInt("NumberOfSt"));  
                rowCounter++;
            }
            if(rowCounter==0){
                System.out.println("Невірний ID!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   private void showAllDisciplines(){
       try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Disciplines");
            int rowCounter = 0;
            System.out.println("Дисципліни:");
            while(rs.next()){
                System.out.println("Дисципліна: ID - "+rs.getInt("ID")+", Назва: "+rs.getString("Name")+", Кількість студентів: "+rs.getInt("NumberOfSt"));  
                rowCounter++;
            }
            if(rowCounter==0){
                System.out.println("Не знайдено жодноі дисципліни.");
            }
        } catch (SQLException ex) {
           System.out.println("Не вірний SQL запит на вибірку даних");
            ex.printStackTrace();
        }
       
   }
   
   private void addStudentToListeners(int disciplineID, int studentID){
        try {
            PreparedStatement st = con.prepareStatement("INSERT INTO Listeners(DisciplineID, StudentID) VALUES(?,?)");
            st.setInt(1, disciplineID);
            st.setInt(2, studentID);
            
            int result = st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на додавання");
           ex.printStackTrace();
        }
   }
   
   private void addAllStudentsToListeners(int disciplineID){
        try {
            clearAllDataFromListeners();
            PreparedStatement prst = con.prepareStatement("INSERT INTO Listeners(DisciplineID, StudentID) VALUES(?,?)");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Students");
            while(rs.next()){
                int stID = rs.getInt("PersonalID");
                prst.setInt(1, disciplineID);
                prst.setInt(2, stID);
                int result = prst.executeUpdate();
            }
            
            
            prst.close();
            st.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            ex.printStackTrace();
        }
   }
   
   private void removeStudentFromListeners(int stID){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Listeners WHERE StudentID = ?");
            st.setInt(1, stID);
            
            int result = st.executeUpdate();
            st.close();
       
            
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на видалення");
            ex.printStackTrace();
        }
          
   }
   
   private void showAllListeners(){
       try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Listeners");
            int rowCounter = 0;
            System.out.println("Слухачі:");
            int discID = 0;
            while(rs.next()){
                if(discID != rs.getInt("DisciplineID")){
                    System.out.println("Дисципліна: ID - "+rs.getInt("DisciplineID"));
                    discID = rs.getInt("DisciplineID");
                }
                System.out.println("ID студента - "+rs.getInt("StudentID"));  
                rowCounter++;
            }
            if(rowCounter==0){
                System.out.println("Не знайдено жодного слухача.");
            }
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            ex.printStackTrace();
        }
       
   }
   
   private void addTeacherToLecturers(int disciplineID, int teacherID){
        try {
            PreparedStatement st = con.prepareStatement("INSERT INTO Lecturers(DisciplineID, TeacherID) VALUES(?,?)");
            st.setInt(1, disciplineID);
            st.setInt(2, teacherID);
            
            int result = st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на додавання");
           ex.printStackTrace();
        }
   }
   
      private void removeTeacherFromLecturers(int teacherID){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Lecturers WHERE TeacherID = ?");
            st.setInt(1, teacherID);
            
            int result = st.executeUpdate();
            st.close();
       
            
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на видалення");
            ex.printStackTrace();
        }
          
   }
      
      private void showAllLecturers(){
       try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Lecturers");
            int rowCounter = 0;
            System.out.println("Лектори:");
            int discID = 0;
            while(rs.next()){
                if(discID != rs.getInt("DisciplineID")){
                    System.out.println("Дисципліна: ID - "+rs.getInt("DisciplineID"));
                    discID = rs.getInt("DisciplineID");
                }
                System.out.println("Лектор: ID - "+rs.getInt("TeacherID"));  
                rowCounter++;
            }
            if(rowCounter==0){
                System.out.println("Не знайдено жодної дисципліни.");
            }
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            ex.printStackTrace();
        }
       
   }
      
      private void clearAllDataFromStudents(){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Students");
            
            int result = st.executeUpdate();
            st.close();
            System.out.println("Students cleared!");
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на видалення");
            ex.printStackTrace();
        }
      }
      private void clearAllDataFromTeachers(){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Teachers");
            
            int result = st.executeUpdate();
            st.close();
            System.out.println("Teachers cleared!");
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на видалення");
            ex.printStackTrace();
        }
      }
      private void clearAllDataFromDisciplines(){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Disciplines");
            
            int result = st.executeUpdate();
            st.close();
            System.out.println("Disciplines cleared!");
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на видалення");
            ex.printStackTrace();
        }
      }
      private void clearAllDataFromListeners(){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Listeners");
            
            int result = st.executeUpdate();
            st.close();
            System.out.println("Listeners cleared!");
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на видалення");
            ex.printStackTrace();
        }
      }
      
       private void clearAllDataFromLecturers(){
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Lecturers");
            
            int result = st.executeUpdate();
            st.close();
            System.out.println("Lecturers cleared!");
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на видалення");
            ex.printStackTrace();
        }
      }
       private void showAllData(){
           System.out.println("*******************************");
           System.out.println("Відомості про факультет:");
           showAllTeachers();
           System.out.println("*******************************");
           showAllStudents();
           System.out.println("*******************************");
           showAllDisciplines();
           System.out.println("*******************************");
           showAllListeners();
           System.out.println("*******************************");
           showAllLecturers();
           System.out.println("*******************************");
       }
       private void clearAllDataFromDB(){
           clearAllDataFromTeachers();
           clearAllDataFromStudents();
           clearAllDataFromListeners();
           clearAllDataFromLecturers();
           clearAllDataFromDisciplines();
           System.out.println("Database cleared!");
       }
   
   
       private void searchByID(int ID){
        try {
            int stCounter = 0;
            int tcCounter = 0;
            int dcCounter = 0;
            PreparedStatement st1 = con.prepareStatement("SELECT * FROM Students WHERE PersonalID = ?");
            PreparedStatement st2 = con.prepareStatement("SELECT * FROM Teachers WHERE PersonalID = ?");
            PreparedStatement st3 = con.prepareStatement("SELECT * FROM Disciplines WHERE ID = ?");
            st1.setInt(1, ID);
            st2.setInt(1, ID);
            st3.setInt(1, ID);
            ResultSet rs1 = st1.executeQuery();
            while(rs1.next()){
                stCounter++;
            }
            ResultSet rs2 = st2.executeQuery();
            while(rs2.next()){
                tcCounter++;
            }
            ResultSet rs3 = st3.executeQuery();
            while(rs3.next()){
                dcCounter++;
            }
            
            System.out.println("Знайдено студентів - " + stCounter );
            System.out.println("Знайдено викладачів - " + tcCounter);
            System.out.println("Знайдено дисциплін - " + dcCounter);
            
        } catch (SQLException ex) {
            System.out.println("Не вірний SQL запит на пошук");
            ex.printStackTrace();
        }
       }
   public static void main(String[] args){
        MyDB db = new MyDB();
        db.initialization("FacultyDB");
        //db.clearAllDataFromStudents();
        db.insertStudentsData(5511, "Ivanov", 3, "FI", 523.4);
        db.insertStudentsData(5512, "Petrov", 3, "FI", 514.2);
        db.insertStudentsData(5513, "Sidorov", 3, "FI", 512.5);
        
        db.insertTeacherData(12, "Afonin", "professor");
        db.insertTeacherData(1, "GlybovecJunior", "GOD of programing");
        db.insertTeacherData(13, "Bublik", "Someone");
        
        db.insertDisciplineData(1, "Programming", 24);
        db.insertDisciplineData(2, "PP", 12);
        db.insertDisciplineData(3, "OOP", 32);
        
        db.addAllStudentsToListeners(2);
        db.addTeacherToLecturers(1, 1);
        //db.clearAllDataFromListeners();
        //db.addStudentToListeners(1, 5513);
        //db.addAllStudentsToListeners(1);
        //db.removeStudentByID(5512);
        //db.removeTeacherByID(2);
        //db.addTeacherToLecturers(1, 12);
        //db.clearAllDataFromStudents();
        //db.clearAllDataFromTeachers();
        //db.clearAllDataFromListeners();
        //db.clearAllDataFromLecturers();
        //db.clearAllDataFromDisciplines();
        //db.searchByID(5511);
        //db.getStudentDataByID(551);
        //db.getTeacherDataByID(1);
        db.showAllData();
        db.clearAllDataFromDB();
        //db.showAllStudents();
        
        
   }
}
