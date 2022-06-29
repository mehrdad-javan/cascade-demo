package se.lexicon.cascade.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.cascade.demo.entity.Person;
import se.lexicon.cascade.demo.entity.Phone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        ex1();
        //ex2();
        ex3();
        ex4();
    }


    // CascadeType.PERSIST example
    // The CascadeType.PERSIST allows us to persist a child entity along with the parent one.
    private void ex1() {
        Person person = new Person();
        person.setName("Mehrdad Test");

        Phone phone = new Phone();
        phone.setNumber("123456789");

        person.addPhone(phone);

        entityManager.persist(person);
    }

    // CascadeType.MERGE example
    // The CascadeType.MERGE allows us to merge a child entity along with the parent one.
    private void ex2() {
        Phone phone = entityManager.find(Phone.class, 1L);
        Person person = phone.getOwner();

        person.setName("Mehrdad Javansson");
        phone.setNumber("9999999999");

        entityManager.clear();

        entityManager.merge(person);
    }

    // CascadeType.REFRESH example
    /*
    The CascadeType.REFRESH is used to propagate the refresh operation from a parent entity to a child.
    The refresh operation will discard the current entity state, and it will override it using the one loaded from the database.
     */
    private void ex3() {
        Person person = entityManager.find(Person.class, 1L);
        Phone phone = person.getPhones().get(0);

        person.setName("Mehrdad Javan");
        phone.setNumber("88888888");

        entityManager.refresh(person);
        System.out.println("person = " + person);
        System.out.println("phone = " + phone);
    }


    // CascadeType.DETACH example
    /*
        DETACH plays the role when more than one entity is associated to each other.
        CascadeType.DETACH cascades the detach operation to all associated entities detach from hibernate session.
        If one entity is detached, other associated entities will also be detached if CascadeType.
    */
    private void ex4() {
        Person person = entityManager.find(Person.class, 1L);
        Phone phone = person.getPhones().get(0);

        System.out.println(entityManager.contains(person));
        System.out.println(entityManager.contains(phone));

        entityManager.detach(person);

        System.out.println(entityManager.contains(person));
        System.out.println(entityManager.contains(phone));
        Person person2 = entityManager.find(Person.class, 1L);

    }



    // https://www.javaguides.net/2018/11/guide-to-jpa-and-hibernate-cascade-types.html
    // https://www.sourcecodeexamples.net/2020/02/jpa-cascade-types-with-examples.html
}
