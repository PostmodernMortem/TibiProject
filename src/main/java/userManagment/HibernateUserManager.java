package userManagment;

import org.apache.lucene.search.MatchAllDocsQuery;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Wojtek on 2014-07-17.
 */
//TODO
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

    public void addUser(User toAdd) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-jpa-tutorial");
        EntityManager em = null;
        System.out.print(toAdd.toString());
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
            User temporary = findUser(toBeDeleted.getName());
            if( temporary != null){
               System.out.println(temporary.getId());
            }
            tm.begin();
            em = emf.createEntityManager();
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

            String query4 = "db.User.find({'name': '"+username + "'})";

            toFind = (User)em.createNativeQuery( query4, User.class).getSingleResult();
            System.out.println("******************WYJSCIE*************");

            System.out.println(toFind.toString());

//            em.getTransaction().commit();


            //toFind = em.find( User.class, username);
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
        return toFind;
    }

    public void list() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-jpa-tutorial");
        EntityManager em = null;

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

            List<User> result = em.createNativeQuery( query4, User.class).getResultList();
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
}
