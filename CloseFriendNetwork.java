import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;


public class CloseFriendNetwork {

    int[][] graph;
    static String accountArray[];
    Map<String, ArrayList<String>> accountData = new HashMap<>();
    static ArrayList<String> accounts = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);
    static CloseFriendNetwork t;

    public CloseFriendNetwork(String[] accountArray) // constructor
    {
        CloseFriendNetwork.accountArray = accountArray;
        graph = new int[accountArray.length][accountArray.length]; // 2d Array (Graph)
    }

    public void addFriend(String yourName, String FriendName) {

        Arrays.sort(accountArray);
        // sorting array ....

        // return index , if not matches then return negative
        int i = Arrays.binarySearch(accountArray, yourName.toLowerCase());
        int j = Arrays.binarySearch(accountArray, FriendName.toLowerCase());

        if (i < 0 || j < 0) // when name do not found in list
        {
            System.out.println("Friend Does not exist");
            return;
        }
        if (yourName.equalsIgnoreCase(FriendName)) {
            // Duplicate Names Not Allowed
            graph[i][j] = 0;
            graph[j][i] = 0;
        } else 
        {
            graph[i][j] = 1;
            graph[j][i] = 1;
        }
    }

    public void viewFriend() 
    {
        accountData.forEach((key, value) -> System.out.println("Details:\n" +
                "Name: " + key.toUpperCase() + "\n" +
                "Friends: " + value + "\n"));

    }

    public void searchFriend(String name)
    {
        boolean found = false;
        for (Map.Entry<String, ArrayList<String>> entry : accountData.entrySet()) 
        {
            if (name.equals(entry.getKey())) // checking equality
            {
                found = true;
                System.out.println("\nSearched " + entry.getKey().toUpperCase());
                System.out.println("\nFriends \n");

                for (String item : entry.getValue()) // iterating values of FriendList
                {
                    System.out.print(item + "  ");
                }
                System.out.println();
            }
        }
        if (!found) 
        {
            System.out.println("Friend Not found");
        }
    }

    void MapFriends() 
    {
        boolean[] visitedFriends = new boolean[graph.length];
        Queue<String> q = new LinkedList<>();
        q.offer(accountArray[0]); // inserting in the queue
        visitedFriends[0] = true;
        while (!q.isEmpty()) {
            String currentVertix = q.poll(); // remove from head of queue
            int index = Arrays.binarySearch(accountArray, currentVertix);
            String key = currentVertix.toLowerCase(); // to store current vertix
            ArrayList<String> friendList = new ArrayList<>(); // to store near by vertixs

            for (int j = 0; j < graph.length; j++) 
            {
                if (graph[index][j] == 1)
                // if there is and edge between 'curr' and all other vertics , print them
                // and store them in ArrayList
                {
                    friendList.add(accountArray[j]); // adding in array list
                }
                if (graph[index][j] == 1 && !visitedFriends[j])
                // if not visited and has edge , set true
                // and put it in the Queue
                {
                    q.offer(accountArray[j]);
                    visitedFriends[j] = true;
                }
                // current vertix as key and List of nearby as value
            }
            accountData.put(key, friendList); // adding in map
        }
    }

    static String[] addAccount(String name) // add new vertix in the graph
    {
        accounts.add(name.toLowerCase()); // arrayList.....
        // converting to Array
        return accountArray = accounts.toArray(new String[accounts.size()]);
    }

    static void startUp(String[] list) 
    {
        t = new CloseFriendNetwork(list); // making graph
        // adding edge...
        t.addFriend("Fahad", "Umer");
        t.addFriend("Fahad", "Haider");
        t.addFriend("Abdullah", "Haider");
        t.addFriend("Haider", "Umer");
        t.addFriend("Haider", "Anas");
        t.addFriend("Usman", "Umer");
    }

    public static void main(String[] args) 
    {
        String names[] = { "Fahad", "Umer", "Haider", "Abdullah", "Anas", "Usman" };

        for (String n : names) 
        {
            addAccount(n); // adding names in arrayList and returing array
        }
        startUp(accountArray); // initializing grpah and making edge
        t.MapFriends(); // checking near by friends and adding in Map
        do {
            System.out.println("""
                    Press!
                    1 For Add Account
                    2 For Add Friend
                    3 For Search Friend
                    4 For View All
                    """);
            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.println("Enter Name");
                    // entering name , then returing list , and passing to method start up
                    startUp(addAccount(sc.next()));
                    System.out.println("Account Added Successfully ");
                }
                case 2 -> {
                    System.out.println("Enter your Name");
                    String name = sc.next();
                    System.out.println("Enter Friend Name ");
                    String Friend = sc.next();
                    t.addFriend(name, Friend);
                    t.MapFriends();
                }
                case 3 -> {
                    System.out.println("Enter Name to serach ");
                    // converting to lower case bcz key is also in lower case
                    t.searchFriend(sc.next());
                }
                case 4 -> t.viewFriend();
                case 0 -> System.out.println();
                default -> System.out.println("Choose Correct Option ");
            }
            System.out.println("Press 1 for Main Menue ");
        } while (sc.nextInt() == 1);
        sc.close();
        // IllegalStateException: Scanner closed
    }

}
