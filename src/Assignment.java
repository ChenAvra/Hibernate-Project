import Entities.Administrators;
import Entities.Mediaitems;
import Entities.Users;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.EntityType;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            String statement = "select * from MediaitemsEntity titles where ROWNUM<="+top_n+" order by mid desc";
            Query query=session.createQuery(statement);
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

}
