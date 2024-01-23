package it.univr;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Worker {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    private WorkerRole role;

    protected Worker(){};

    public Worker(String firstName, String lastName, String username, String password, WorkerRole role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public String getPassword(){
        return password;
    }

    public Long getId() {
        return id;
    }
    public WorkerRole getRole(){
        return role;
    }
}




