import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class StableMarriage {
    public static void main(String[] args) {
        // assuming that the user enters correct file
        try {
            Scanner inC = new Scanner(System.in);
            System.out.println("Welcome to java stable marriage problem solver :)");
            System.out.print("Please enter file name: ");
            String fileName = inC.nextLine();
            System.out.println();
            File f = new File(fileName);
            Scanner input = new Scanner(f);
            ArrayList<ArrayList<Person>> women_men = scanFile(input);
            Scanner input1 = new Scanner(f);
            ArrayList<ArrayList<Person>> temp = scanFile(input1);
            System.out.println("Starting to match:");
            Matching(women_men);
            setChoices(women_men, temp);
            System.out.println("The matches:");
            printRes(women_men);
            inC.close();
        } catch (IOException e) {
            System.out.println("Error occurred");
        }
    }

    // scan the input file
    // the method returns array list which contains in the index 0 arraylist for men
    // and in the index 1 array list for women
    // assuming that "END" which seperates the men and women is written uppercase.
    public static ArrayList<ArrayList<Person>> scanFile(Scanner s) {
        ArrayList<Person> men = new ArrayList<Person>();
        ArrayList<Person> women = new ArrayList<Person>();
        int flag = 0;
        String oneLine;
        while (s.hasNextLine()) {
            oneLine = s.nextLine();
            if (oneLine.equals("END")) {
                if (flag == 0) {
                    flag = 1;
                    oneLine = s.nextLine();
                } else
                    break;
            }
            String[] splittedLine = oneLine.split(":");
            splittedLine[1] = splittedLine[1].trim();
            String[] choices_String = splittedLine[1].split(" ");
            ArrayList<Integer> choices = new ArrayList<Integer>();
            for (int i = 0; i < choices_String.length; i++)
                choices.add(Integer.parseInt(choices_String[i]));
            Person p = new Person(splittedLine[0], choices);
            if (flag == 0) {
                men.add(p);
            }
            if (flag == 1) {
                women.add(p);
            }
        }
        ArrayList<ArrayList<Person>> women_men = new ArrayList<ArrayList<Person>>();
        women_men.add(men);
        women_men.add(women);
        return women_men;
        // if we want to reverse the roles,first add women then men.
    }

    // the method that performs matching.
    public static void Matching(ArrayList<ArrayList<Person>> menAndWomen) {
        ArrayList<Person> men = menAndWomen.get(0);
        ArrayList<Person> women = menAndWomen.get(1);
        while (checkandupdate(men)) {
            for (int m = 0; m < men.size(); m++) {
                Person temp = new Person();
                if (men.get(m).getPref().size() > 0 && men.get(m).getMatch().equals(temp)) {
                    System.out.println("Working on: " + m + "\n");
                    int choice = men.get(m).getPref().get(0);
                    Person w = new Person(women.get(choice));
                    Person p = check(w, menAndWomen);
                    if (p != null) {
                        p.setMatch(temp);
                    }
                    men.get(m).setMatch(w);
                    int index = getindex(women.get(choice).getPref(), m);
                    int j = index + 1;
                    for (; j < women.get(choice).getPref().size(); j++) {
                        if (men.get(women.get(choice).getPref().get(j)).getPref().contains(choice)) {
                            int k = getindex(men.get(women.get(choice).getPref().get(j)).getPref(), choice);
                            if (k != -1)
                                men.get(women.get(choice).getPref().get(j)).getPref().remove(k);
                        }
                        // the -10 value it just a value in order to delete it from the list.
                        women.get(choice).getPref().set(j, -10);
                    }
                    while (women.get(choice).getPref().contains(-10)) {
                        women.get(choice).getPref().remove(getindex(women.get(choice).getPref(), -10));
                    }
                    women.set(choice, w);
                }
            }
        }
    }

    // help method-the method check whether the forwarded person(w) matched to a man
    // before or not
    // if yes it returns the man that matched before,otherwise returns null
    public static Person check(Person p, ArrayList<ArrayList<Person>> menAndWomen) {
        for (int i = 0; i < menAndWomen.get(0).size(); i++) {
            if (menAndWomen.get(0).get(i).getMatch().equals(p) == true)
                return menAndWomen.get(0).get(i);
        }
        return null;
    }

    // help method-the method check whether there is a man that not matched yet.
    public static boolean checknone(ArrayList<Person> arr) {
        Person p = new Person();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getMatch().equals(p))
                return true;
        }
        return false;
    }

    // help method-the method check whether there is a free man with nonempty
    // preference list
    // and returns true if there is a free man with nonempty preference
    // list,otherwise false
    public static boolean checkandupdate(ArrayList<Person> arr) {
        if (checknone(arr)) {
            int res = checkfree(arr);
            if (res != -1) {
                if (arr.get(res).getPref().size() > 0)
                    return true;
            } else
                return false;
        }
        return false;
    }

    // help method-the method returns the index of a value in arraylist
    public static int getindex(ArrayList<Integer> arr, int value) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == value)
                return i;
        }
        return -1;
    }

    // the method prints the result after matching
    public static void printRes(ArrayList<ArrayList<Person>> array) {
        System.out.println("Name\t\tChoice\t\tPartner");
        System.out.println("----------------------------------------");
        for (int i = 0; i < array.get(0).size(); i++) {
            System.out.print(array.get(0).get(i).getName() + "\t\t");
            if (array.get(0).get(i).getChoiceN() != -1)
                System.out.print(array.get(0).get(i).getChoiceN() + "\t\t");
            else
                System.out.print("----\t\t");
            System.out.println(array.get(0).get(i).getMatch().getName());
        }
    }

    // help method-the method returns the index of free man
    public static int checkfree(ArrayList<Person> arr) {
        Person p = new Person();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getMatch().equals(p))
                return i;
        }
        return -1;
    }

    // help method-the method returns the key for the matched person,search by name.
    public static int getMatchNum(ArrayList<Person> arr, String name) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    // help method-the method returns the index for the matched person in preference
    // list.
    public static int getMatchPrior(Person p, int value) {
        for (int i = 0; i < p.getPref().size(); i++) {
            if (p.getPref().get(i) == value)
                return i;
        }
        return -1;
    }

    // help method-the method sets the choices for each men,arr1 is the modified
    // arraylist which contains modified preference list
    // and arr2 contains original preference list
    public static void setChoices(ArrayList<ArrayList<Person>> arr1, ArrayList<ArrayList<Person>> arr2) {
        for (int i = 0; i < arr1.get(0).size(); i++) {
            int value = getMatchNum(arr2.get(1), arr1.get(0).get(i).getMatch().getName());
            if (value != -1) {
                int valuech = getMatchPrior(arr2.get(0).get(i), value);
                arr1.get(0).get(i).setChoiceN(valuech + 1);
            } else
                arr1.get(0).get(i).setChoiceN(-1);
        }
    }
}
