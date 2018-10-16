/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA.data;

/**
 *
 * @author uczen
 */
public class Person {

    /**
     *
     */
    protected String name = "";

    /**
     *
     */
    protected String surname = "";

    /**
     *
     */
    public Person() {

    }

    /**
     *
     * @param name
     * @param surname
     */
    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /**
     *
     * @return
     */
    public String getNameAndSurname() {
        return name + " " + surname;
    }
    
    /**
     *
     * @return
     */
    public String toString(){
        return getNameAndSurname();
    }
}
