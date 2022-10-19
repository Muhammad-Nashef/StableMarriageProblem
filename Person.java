import java.util.ArrayList;

// this is help class in order to solve stable marriage problem 

public class Person {
    // fields
    public static final Person NONE = new Person("None");
    private String name; // person name
    private ArrayList<Integer> pref; // person preference list
    private Person match; // matched person
    private int choiceN; // choice from the original preference list

    // constructors

    public Person(String name, ArrayList<Integer> pref) {
        this.name = name;
        this.pref = new ArrayList<Integer>(pref);
        this.match = NONE;
        this.choiceN = 0;
    }

    public Person(String name) {
        this.name = name;
        this.pref = new ArrayList<Integer>();
        this.match = NONE;
        this.choiceN = 0;
    }

    public Person() {
        this.name = "None";
        this.pref = new ArrayList<Integer>();
        this.match = NONE;
        this.choiceN = 0;
    }

    public Person(Person p) {
        this.name = p.name;
        this.pref = new ArrayList<Integer>(p.pref);
        this.match = p.match;
        this.choiceN = p.choiceN;
    }

    // methods

    public ArrayList<Integer> getPref() {
        return this.pref;
    }

    public String getName() {
        return this.name;
    }

    public Person getMatch() {
        return this.match;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMatch(Person p) {
        this.match = p;
    }

    public void setPref(ArrayList<Integer> pref) {
        this.pref = pref;
    }

    public void setChoiceN(int ch) {
        this.choiceN = ch;
    }

    public int getChoiceN() {
        return this.choiceN;
    }

    public String toString() {
        return "Name: " + this.name + " preference list: " + pref.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            Person other = (Person) o;
            return this.name.equals(other.name) && this.pref.equals(other.pref);
        } else
            return false;
    }
}
