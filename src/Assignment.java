import Entities.*;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.EntityType;
import javax.swing.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Assignment {
    public static boolean isExistUsername (String username){
        int numOfUsers=0;
        try{
            Session session= HibernateUtil.currentSession();
            String statement="select username from Users user WHERE user.username='"+username+"'";
            Query query=session.createQuery(statement);
            numOfUsers=query.list().size();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSession();
        }

        if(numOfUsers>0){
            return true;
        }
        return false;
    }

    public static String insertUser(String username, String password, String first_name, String last_name, String day_of_birth, String month_of_birth, String year_of_birth) {
        List months=new ArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        Users usersEntity = new Users();
        try{
            if(Integer.parseInt(year_of_birth)<0 || Integer.parseInt(day_of_birth)<=0 || Integer.parseInt(day_of_birth)>31 || !months.contains(month_of_birth)){
                return null;
            }
            if(isExistUsername(username)){
                return null;
            }
            else{
                Session session = HibernateUtil.currentSession();
//                usersEntity.setUserid(usersEntity.getUserid());
//                System.out.println(usersEntity.getUserid());
                usersEntity.setUsername(username);
                usersEntity.setPassword(password);
                usersEntity.setFirstName(first_name);
                usersEntity.setLastName(last_name);
                DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                Date birthDate=format.parse(month_of_birth+" "+day_of_birth+", "+year_of_birth);
                Timestamp birthDateToSetUp = new Timestamp(birthDate.getTime());
                usersEntity.setDateOfBirth(birthDateToSetUp);
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                usersEntity.setRegistrationDate(currentDate);
                Transaction transaction = session.beginTransaction();
                session.saveOrUpdate(usersEntity);
                transaction.commit();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSession();
        }
        String userId = ""+usersEntity.getUserid();
        return userId;

    }
    public static List<Mediaitems> getTopNItems (int top_n){
        List<Mediaitems> answer =  null;
        try{
            Session session=HibernateUtil.currentSession();
            String statement = "select items from Mediaitems items order by mid desc";
            Query query=session.createQuery(statement).setMaxResults(top_n);
            answer=query.list();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSession();
        }
        return answer;
    }

    public static String validateUser (String username, String password){
        List<Users> answer =  null;
        try{
            Session session=HibernateUtil.currentSession();
            String statement = "from Users allUsers where allUsers.username='"+username+"' and allUsers.password='"+password+"'";
            Query query=session.createQuery(statement);
            answer=query.list();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSession();
        }
        if(answer.size()>0){
            String answerToString=""+answer.get(0).getUserid();
            return answerToString;
        }
        else{
            return "NOT FOUND";
        }
    }

    public static String validateAdministrator (String username, String password){
        List<Administrators> answer =  null;
        try{
            Session session=HibernateUtil.currentSession();
            String statement = "from Administrators allAdmins where allAdmins.username='"+username+"' and allAdmins.password='"+password+"'";
            Query query=session.createQuery(statement);
            answer=query.list();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            HibernateUtil.closeSession();
        }
        if(answer.size()>0){
            String answerToString=""+answer.get(0).getAdminid();
            return answerToString;
        }
        else{
            return "NOT FOUND";
        }
    }

    public static void main(String[] args) {
        Assignment a=new Assignment();
//        a.insertUser("chen","1234","chenavra","avra","19","November","1994");
//        a.insertUser("sh","1234","chenavra","avra","19","November","1994");
//        a.insertUser("sharom","1234","chenavra","avra","19","November","1994");

        a.insertToHistory("0","8");
//        a.getHistory("0") ;
//        a.insertToLog("0");
//        a.getNumberOfRegistredUsers(0);
//        System.out.println(a.getUsers());
//        System.out.println(getUser("0"));
    }

    public static void insertToHistory (String userid, String mid){
        History history=new History();
        List<Users>users=null;
        //check if user is exist
        try {
            Session session=HibernateUtil.currentSession();
            String query="select user from Users user where user.userid='"+userid+"'";

            Query query2=session.createQuery(query);
            users=query2.list();
//            users=session.createQuery(query).list();
        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally {
            HibernateUtil.closeSession();
        }
        if (users==null || users.size()==0){
            return;
        }

        //check if mid is exist
        List<Mediaitems>mids=null;

        try {
            Session session=HibernateUtil.currentSession();
            String query="select mid from Mediaitems MD where MD.mid='"+mid+"'";
            mids=session.createQuery(query).list();
        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally {
            HibernateUtil.closeSession();
        }
        if (mids==null || mids.size()==0) {
            return;
        }

        //update history

        try {

            Session session=HibernateUtil.currentSession();
            Transaction transaction = session.beginTransaction();
            history.setUserid(Long.parseLong(userid));
            history.setMid(Long.parseLong(mid));
            Timestamp timestamp=new Timestamp(System.currentTimeMillis());
            history.setViewtime(timestamp);
            session.saveOrUpdate(history);
            transaction.commit();
            System.out.println("The insertion to history table was successful"+" "+timestamp);



        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally {
            HibernateUtil.closeSession();
        }

    }

    public static Map<String,Date> getHistory (String userid){

        Session session = null;
        List<Object> usesDetailesList=null;
        Map<String,Date> usesDetailes=new HashMap<>();
        try
        {
            session=HibernateUtil.currentSession();
            String hql="select h.viewtime , mi.title from History h, Mediaitems mi where h.userid = '"+userid+"'and mi.mid = h.mid ORDER BY h.viewtime" ;
            usesDetailesList=session.createQuery(hql).list();

            for(int i=0; i<usesDetailesList.size(); i++){
                Object[] array=(Object[]) usesDetailesList.get(i);
                String title=(String)(array)[1];
                Timestamp timestamp= (Timestamp) (array)[0];
                Date date=new Date(timestamp.getTime());
                usesDetailes.put(title,date);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            HibernateUtil.closeSession();
        }
        System.out.println(usesDetailes.toString());
        return usesDetailes;


    }

    public static void insertToLog (String userid){
        Loginlog loginlog=new Loginlog();
        List<Users>users=null;
        //check if user is exist
        try {
            Session session=HibernateUtil.currentSession();
            String query="select users from Users users where users.userid='"+userid+"'";
            users=session.createQuery(query).list();
        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally {
            HibernateUtil.closeSession();
        }
        if (users==null || users.size()==0){
            return;
        }


        //update loginlog

        try {

            Session session=HibernateUtil.currentSession();
            Transaction transaction = session.beginTransaction();
            loginlog.setUserid(Long.parseLong(userid));
            Timestamp timestamp=new Timestamp(System.currentTimeMillis());
            loginlog.setLogintime(timestamp);
            session.saveOrUpdate(loginlog);
            transaction.commit();
            System.out.println("The insertion to log table was successful"+" "+timestamp);



        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally {
            HibernateUtil.closeSession();
        }

    }

    public static int getNumberOfRegistredUsers(int n){
        List<Users>users=null;
        //select users with registration date greater that sysdate-n days
        try {
            Session session=HibernateUtil.currentSession();
            String query= "select count(users.username) from Users users where users.registrationDate > sysdate()-"+n;
            users=session.createQuery(query).list();
//            users=session.createQuery(query).list();
        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally {
            HibernateUtil.closeSession();
        }
        if (users==null || users.size()==0){
            return 0;
        }
        else {
            System.out.println(users.size());
            return users.size();
        }
    }

    public static List<Users> getUsers () {
        List<Users> users = null;
        //get all users from users db
        try {
            Session session = HibernateUtil.currentSession();
            String query = "select users from Users users";
            users = session.createQuery(query).list();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            HibernateUtil.closeSession();
        }

        return users;
    }

    public static Users getUser (String userid){
        List<Users> users = null;
        //get all users from users db
        try {
            Session session = HibernateUtil.currentSession();
            String query = "select users from Users users where users.userid='"+userid+"'";
            users = session.createQuery(query).list();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            HibernateUtil.closeSession();
        }

        if (users==null || users.size()==0){
            return null;
        }
        else {
            return users.get(0);
        }

    }









}
