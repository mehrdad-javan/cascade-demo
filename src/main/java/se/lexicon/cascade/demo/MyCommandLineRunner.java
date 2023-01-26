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

    }


    // The CascadeType.PERSIST
    private void ex1() {
        Person person = new Person();
        person.setName("Mehrdad Test");

        Phone phone = new Phone();
        phone.setNumber("123456789");

        person.addPhone(phone);

        entityManager.persist(person);
    }

    // The CascadeType.MERGE
    private void ex2() {
        Phone phone = entityManager.find(Phone.class, 1L);
        Person person = phone.getOwner();

        person.setName("Mehrdad Javansson");
        phone.setNumber("9999999999");

        entityManager.clear();

        entityManager.merge(person);
    }

    // CascadeType.REFRESH example
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
