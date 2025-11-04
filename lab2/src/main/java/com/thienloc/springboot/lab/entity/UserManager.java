package com.thienloc.springboot.lab.entity;

public class UserManager {
    // su dung spring jpa nên ko cần làm cái này
    // EntityManagerFactory factory = Persistence.createEntityManagerFactory("Lab1");
    // EntityManager manager = factory.createEntityManager();
    // public void findAll(){
    //     String sql = "SELECT loc FROM User loc";
    //     TypedQuery<User> query = manager.createQuery(sql, User.class);
    //     List<User> users = query.getResultList();
    //     users.forEach(System.out::println);
    // }
    // public void findById(){
    //     User user = manager.find(User.class, "<<user-id>>");

    //     System.out.println(user);
    //     if(user != null){
    //         System.out.println("User found");
    //     }else{
    //         System.out.println("User not found");
    //     }
    // }
    // public void create(){
    //     User user = new User("U01","123456","<EMAIL>",false);
    //     try{
    //         manager.getTransaction().begin();
    //         manager.persist(user);
    //         manager.getTransaction().commit();
    //     }catch(Exception e){
    //         manager.getTransaction().rollback();
    //     }
    // }
    // public void update() {

    //     User user = manager.find(User.class, "<<user-id>>");
    // user.setFullname("<<new-fullname>>");
    // user.setEmail("<<new-email>>");
    // try {
    //     manager.getTransaction().begin();
    //     manager.merge(user);
    //     manager.getTransaction().commit();


    // }
    // catch (Exception e) {
    //     manager.getTransaction().rollback();
    // }
    // }
    // public void deleteById(){
    //     User user = manager.find(User.class, "<<user-id>>");
    //     try{
    //         manager.getTransaction().begin();
    //         manager.remove(user);
    //         manager.getTransaction().commit();
    //     }catch(Exception e){}
    // }
}
