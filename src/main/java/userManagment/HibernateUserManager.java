package userManagment;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wojtek on 2014-07-17.
 */
public class HibernateUserManager implements Manager{

    private static HibernateUserManager entity;
    private static TransactionManager tm;

    public static HibernateUserManager getEntity(){
    if (entity == null){
        entity = new HibernateUserManager();
    }
    if(tm ==null){
        tm = getTransactionManager();

    }

        return entity;
    };

    private static TransactionManager getTransactionManager() {
        try {


            Class<?> tmClass = HibernateUserManager.class.getClassLoader().loadClass("com.arjuna.ats.jta.TransactionManager");

            return (TransactionManager) tmClass.getMethod( "transactionManager" ).invoke( null );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        } catch ( InvocationTargetException e ) {
            e.printStackTrace();
        } catch ( NoSuchMethodException e ) {
            e.printStackTrace();
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(User toAdd) throws Exception {
        if (findUser(toAdd.getName()) != null){
            throw new Exception("Uzytkownik istnieje");
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-jpa-tutorial");
        EntityManager em = null;
//        System.out.print(toAdd.toString());
        try {
            tm.begin();
            em = emf.createEntityManager();
            em.persist( toAdd );
            em.flush();
            tm.commit();


        } catch ( Exception e ) {
            e.printStackTrace();
        }finally {
            if(em !=null){
                em.close();
            }
            if(emf!=null){
                emf.close();
            }
        }
    }

    public void modifyUser(String username, User toBeModified) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-jpa-tutorial");
        EntityManager em = null;

        try {
            User temporary = findUser(username);
            if( temporary == null){
                System.out.println("Nie znaleziono uzytkownika");
                throw new NoResultException();
            }
            tm.begin();
            em = emf.createEntityManager();
            //em.persist(temporary);
            temporary.setName(toBeModified.getName());
            temporary.setAge(toBeModified.getAge());

            em.persist(em.contains(temporary) ? temporary : em.merge(temporary));
            em.flush();
            tm.commit();


        } catch ( Exception e ) {
            e.printStackTrace();
        }finally {
            if(em !=null){
                em.close();
            }
            if(emf!=null){
                emf.close();
            }
        }
    }

    public void deleteUser(User toBeDeleted) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-jpa-tutorial");
        EntityManager em = null;

        try {
            tm.begin();
            Boolean loop = true;
            em = emf.createEntityManager();

//                tm.begin();
            User temporary = findUser(toBeDeleted.getName());
            if (temporary != null) {
                System.out.println(temporary.getId());
            }

            //em.persist(temporary);
            em.remove(em.contains(temporary) ? temporary : em.merge(temporary));
            em.flush();
            tm.commit();



        } catch ( Exception e ) {
            e.printStackTrace();
        }finally {
            if(em !=null){
                em.close();
            }
            if(emf!=null){
                emf.close();
            }
        }
    }

    public User findUser(String username) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-jpa-tutorial");
        EntityManager em = null;
        User toFind= null;

        try {


            em = emf.createEntityManager();

//            Session session = (org.hibernate.Session)em.getDelegate();
//            final FullTextSession fullTextSession = Search.getFullTextSession(session);
//            fullTextSession.createIndexer().startAndWait();
//
//            FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
//            em.getTransaction().begin();
//
//            QueryBuilder qb = fullTextEntityManager.getSearchFactory()
//                    .buildQueryBuilder().forEntity(User.class).get();
//            org.apache.lucene.search.Query luceneQuery = qb
//                    .keyword()
//                    .onFields("name")
//                    .matching("Hubert")
//                    .createQuery();
//
//            javax.persistence.Query jpaQuery =
//                    fullTextEntityManager.createFullTextQuery(luceneQuery, User.class);
            String query4 = "db.User.find({'name': '"+username + "'})";
            toFind = null;
            List<User> list = (List<User>) em.createNativeQuery( query4, User.class).getResultList();
            if(list.isEmpty()){
                toFind = null;
            }else{
                toFind = list.get(0);
            }
//
//            System.out.println(toFind.toString());

//            em.getTransaction().commit();


            //toFind = em.find( User.class, username);


        } catch ( Exception e ) {
            e.printStackTrace();
            toFind = null;

        }finally {
            if(em !=null){
                em.close();
            }
            if(emf!=null){
                emf.close();
            }
        }
        return toFind;
    }

    public List<User> list() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-jpa-tutorial");
        EntityManager em = null;

        List<User> result = null;
        try {
            tm.begin();
            em = emf.createEntityManager();

//            Session session = (org.hibernate.Session)em.getDelegate();
//            final FullTextSession fullTextSession = Search.getFullTextSession(session);
//            fullTextSession.createIndexer().startAndWait();
//
//            FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
//            em.getTransaction().begin();
//
//            QueryBuilder qb = fullTextEntityManager.getSearchFactory()
//                    .buildQueryBuilder().forEntity(User.class).get();
//            org.apache.lucene.search.Query luceneQuery = qb
//                    .keyword()
//                    .onFields("name")
//                    .matching("Hubert")
//                    .createQuery();
//
//            javax.persistence.Query jpaQuery =
//                    fullTextEntityManager.createFullTextQuery(luceneQuery, User.class);

            String query4 = "db.User.find({})";

            result = em.createNativeQuery(query4, User.class).getResultList();
//            System.out.println("******************WYJSCIE*************");
//            for (Object x :count)
//            System.out.println(x.toString());
            System.out.println("*********LISTA UZYTKOWNIKOW****************");
            for (User x : result)
                System.out.println(x.toString());
            System.out.println("*******************************************");
//            em.getTransaction().commit();


            //toFind = em.find( User.class, username);
            tm.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        if (result == null) {
            result = new ArrayList<User>();
        }
        return result;
    }
}
