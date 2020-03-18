package com.func;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Learning {

    @FunctionalInterface
    public interface NoParameters {
        void print();
    }

    @FunctionalInterface
    public interface OneParameter {
        void print(String x);
    }

    @FunctionalInterface
    public interface TwoParameters {
        void print(String x, String y);
    }

    public static class Movie {
        private String name;
        private int oscars;

        public Movie(String name, int oscars) {
            this.name = name;
            this.oscars = oscars;
        }

        public String getName() { return this.name; }

        public int getOscars() { return this.oscars; }

        public boolean hasAtLeastOneOscar() {
            return (this.oscars > 0);
        }
    }

    public static class Actor {
        private String name;
        private int age;

        public Actor(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return this.name; }

        public int getAge() { return this.age; }
    }

    public static class Employee {
        private String name;
        private String department;
        private int salary;

        public Employee(String name, String department, int salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public String getName() { return this.name; }
        public String getDepartment() { return this.department; }
        public int getSalary() { return this.salary; }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static void main(String[] args) {
        streamDemo9();
    }

    private static void streamDemo9() {
        // Count the even numbers from 0 to one billion
        long startTime = System.currentTimeMillis();
        long count = IntStream.rangeClosed(0, 1000000000)
                .parallel()
                .filter(i -> {return (i % 2 == 0);})
                .count();

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Number of evens: " + count + " - Execution Time: " + duration);
        //875 millis
        //290 millis (Parallel)
        System.out.println("\n**********************************************");
    }

    private static void streamDemo8() {
        Stream.of("Handy", "Jhuly", "Alejandra", "Gisela", "Irene")
                .parallel()
                .forEach(System.out::print);

        System.out.println("\n**********************************************");
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("George", "IT", 25000));
        employees.add(new Employee("Nicky", "Marketing", 45000));
        employees.add(new Employee("Amy", "HR", 45000));
        employees.add(new Employee("Tom", "Marketing", 80000));
        employees.add(new Employee("Carla", "IT", 90000));

        // Grouping by department name (list of employees)
        Map<String, List<Employee>> mapEmp =
                employees.parallelStream()
                        .collect(Collectors.groupingByConcurrent(Employee::getDepartment));
        System.out.println(mapEmp);
    }

    private static void streamDemo7() {
        String[] words = new String[] {
                "expect", "salad", "blow", "harmony", "committee", "manage", "field", "flavor", "chocolates"
        };

        // Simple concat of these words
        System.out.println("**************************************");
        String one = Arrays.stream(words)
                .collect(Collectors.joining());
        System.out.println(one);

        // Concat the words with a delimiter
        System.out.println("**************************************");
        String oneDelimeter = Arrays.stream(words)
                .collect(Collectors.joining("+"));
        System.out.println(oneDelimeter);

        // Concat the words with a suffix and prefix
        System.out.println("**************************************");
        String suffPref = Arrays.stream(words)
                .collect(Collectors.joining("-", "<", ">"));
        System.out.println(suffPref);

        // Partition the words by testing if their length is even
        System.out.println("**************************************");
        Map<Boolean, List<String>> partition =
                Arrays.stream(words)
                        .collect(Collectors.partitioningBy(w -> {return (w.length() % 2 == 0);}));
        System.out.println(partition);
    }

    private static void streamDemo6() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("George", "IT", 25000));
        employees.add(new Employee("Nicky", "Marketing", 45000));
        employees.add(new Employee("Amy", "HR", 45000));
        employees.add(new Employee("Tom", "Marketing", 80000));
        employees.add(new Employee("Carla", "IT", 90000));

        // Grouping by department name (list of employees)
        Map<String, List<Employee>> mapEmp =
                employees.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(mapEmp);

        // Grouping by department name (average salary)
        System.out.println("**************************************");
        Map<String, Double> mapEmp2 =
                employees.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingInt(Employee::getSalary)));
        System.out.println(mapEmp2);

    }

    private static void streamDemo5() {
        // We are going to create a map of movies and its actors
        HashMap<Movie, List<Actor>> movies = new HashMap<>();
        movies.put(new Movie("The Avengers", 11),
                List.of(new Actor("Di Caprio", 20), new Actor("Brad Pitt", 40)));
        movies.put(new Movie("The Matrix", 4),
                List.of(new Actor("Keanu Reeves", 20), new Actor("Agent Smith", 35)));
        movies.put(new Movie("Shawshank Redemption", 0),
                List.of(new Actor("The White Man", 30), new Actor("The Nigga", 50)));
        movies.put(new Movie("The Godfather", 3),
                List.of(new Actor("Tony Italian", 30), new Actor("The Mafia", 30)));
        movies.put(new Movie("Fight Club", 0),
                List.of(new Actor("Brad Pitt", 40), new Actor("George Clonney", 60)));

        // I want a list of all the actors of those movies
        List<Actor> allActors = movies.keySet()
                .stream()
                .flatMap(m -> {return movies.get(m).stream();})
                .collect(Collectors.toList());
        allActors.forEach(a -> System.out.println(a.getName()));

        // Now I want the same list but without repetitions
        System.out.println("**************************************");
        List<String> allActors2 = movies.keySet()
                .stream()
                .flatMap(m -> {return movies.get(m).stream().map(a -> {return a.getName();});})
                .distinct()
                .collect(Collectors.toList());
        allActors2.forEach(a -> {System.out.println(a);});
    }

    private static void streamDemo4() {
        String[] words = new String[] {
                "expect", "salad", "blow", "harmony", "committee", "manage", "field", "flavor", "chocolates"
        };

        String largestWord = Arrays.stream(words)
                .reduce((w1, w2) -> { return (w1.length() > w2.length()) ? w1 : w2;})
                .get();
        System.out.println(largestWord);

        System.out.println("**************************************");
        Integer sumOfLengths = Arrays.stream(words)
                .mapToInt(w -> {return w.length();})
                .reduce((s1, s2) -> {return s1 + s2;})
                .getAsInt();
        //.sum();
        System.out.println("The total sum is " + sumOfLengths);

        System.out.println("**************************************");
        String combinedWords = Arrays.stream(words)
                .reduce((w1, w2) -> {return w1.concat(w2);})
                .get();
        System.out.println(combinedWords);
    }

    private static void streamDemo3() {
        LinkedList<Movie> movies = new LinkedList<>();
        movies.add(new Movie("The Avengers", 11));
        movies.add(new Movie("The Matrix", 4));
        movies.add(new Movie("Shawshank Redemption", 0));
        movies.add(new Movie("The Godfather", 3));
        movies.add(new Movie("Fight Club", 0));

        // We are going to separate the title in two parts
        // And check if the second part of the title contains an 'e'
        movies.stream()
                .map(m -> { return m.getName().split("\\s+");})
                .filter(a -> {return a[0].contains("e");})
                .forEach(a -> System.out.println(a[0] + " " + a[1]));

        // Filter if it has at least one oscar
        System.out.println("**************************************");
        movies.stream()
                .filter(Movie::hasAtLeastOneOscar)
                .forEach(m -> System.out.println(m.getName()));
    }

    private static void streamDemo2() {
        HashSet<Movie> movies = new HashSet<>();
        movies.add(new Movie("Titanic", 11));
        movies.add(new Movie("The Matrix", 4));
        movies.add(new Movie("Shawshank Redemption", 0));
        movies.add(new Movie("The Godfather", 3));
        movies.add(new Movie("Fight Club", 0));

        List<Movie> winners = movies.stream()
                .filter(m -> m.getOscars() > 0)
                .collect(Collectors.toList());

        winners.forEach(m -> System.out.println(m.getName()));
        /*
        for (Movie m : winners) {
            System.out.println(m.getName());
        }*/

        int totalOscars = movies.stream()
                .mapToInt(m -> m.getOscars())
                .sum();
        System.out.println("Total Oscars: " + totalOscars);

        //*******************************************************

        Movie maxOscars = movies.stream()
                .max((m1, m2) -> {
                    return m1.getOscars() - m2.getOscars();
                }).get();
        System.out.println("The winner is " + maxOscars.getName());

        //*******************************************************

        movies.stream()
                .sorted(Comparator.comparing(Movie::getOscars))
                .forEach(m -> System.out.println(m.getName()));

        //*******************************************************

        System.out.println("*********************************");
        movies.stream()
                .sorted((m1, m2) -> {
                    return m2.getOscars() - m1.getOscars();
                })
                .forEach(m -> System.out.println(m.getName()));
    }

    private static void streamDemo() {
        Integer[] integers = new Integer[] {
                1, 23, 76, 92, -76, 956, 65, 990, -256
        };

        Arrays.stream(integers)
                .map(i -> i*i)
                .forEach(i->System.out.println(i));

        Integer minInteger = Arrays.stream(integers)
                .min(Comparator.naturalOrder()).get();
        System.out.println("The min value is " + minInteger);

        boolean isAnySmaller = Arrays.stream(integers)
                .anyMatch(i -> i < 0);
        System.out.println("We have negative values? " + isAnySmaller);


    }

    private static void setDemo() {
        /**
         * Sets
         * - Do not allow duplicate values to exist within them
         * - Every value in a set is unique
         * - Have a strong contract for the equals and hash-code operations
         * - The Set Interface has 3 types of implementations:
         *   + HashSet
         *   + LinkedHashSet
         *   + TreeSet
         **/

        /**
         * HashSet
         * - Uses a HashTable for storage
         * - Do not allow duplicates but allows null values
         * - Does not maintain insertion order
         **/
        HashSet<String> days = new HashSet<>();
        days.add("Monday");
        days.add("Sunday");
        days.add("Tuesday");
        days.remove("Tuesday");

        for (var day : days) {
            System.out.println(day);
        }

        // Important Methods: addAll(), clear(), isEmpty(), contains()

        /**
         * LinkedHashSet
         * - Uses a combination of HashTable and linked lists for storage
         * - Maintains the insertion order
         **/
        LinkedHashSet<Integer> ids = new LinkedHashSet<>();
        ids.add(77);
        ids.add(88);
        ids.add(99);
        ids.remove(88);

        for (var id : ids) {
            System.out.println(id);
        }

        /**
         * TreeSet
         * - Uses a HashTable for storage
         * - Do not allow duplicates but allows null values
         * - Does not maintain insertion order
         **/
    }

    private static void demo05() {
        /**
         * The Collection Framework
         * - A Collection is a group of objects
         * - A Framework is a combination of interfaces and their implementations.
         * - The Collection Framework is a combination of:
         *   + Interfaces for data structures
         *   + Concrete implementations for those data structures
         **/

        /**
         * Streams
         * - It cannot be there without a group of objects
         * - Thi is achieved with the Collection Framework
         *
         * We have this structure:
         * Collection
         *    Set -> SortedSet
         *    List
         *    Queue -> Deque
         **/

        /**
         * Maps
         * - Do not fit the definition of a Collection
         * - They have a collection of:
         *   + Keys
         *   + Values
         *   + Key-Value pairs
         **/

    }

    private static void demo04() {
        /**
         * Method References Rules
         * - Method references can only be used to replace a single method lambda expression.
         * - You must have a lambda expression to begin with
         * - The syntax is: object: method
         * - We can have a reference to: a static method, and instance method, a constructor
         *
         * ContainingClass::StaticMethod
         * ContainingObject::InstanceMethod
         * ContainingType::MethodName
         * ClassName::new
         **/

        /**
         * Best Practices
         * - Is always preferable to use: java.util.function.
         * - Avoid creating redundant functional interfaces
         * - Prefer lambdas over inner classes
         * - Keep lambda expression short and sweet
         * - Prefer one line lambdas but judge the situation
         * - Use method references when necessary. For example:
         *      String::toLowerCase;     instead of     s -> s.toLowerCase();
         *
         **/
    }

    private static void demo03() {
        /**
         * Interfaces vs Functional Interfaces.
         * - Functional Interfaces can only have one abstract method!
         * - Functional Interfaces depends on the Lambda expression.
         * - @FunctionalInterface annotation is recommended.
         **/

        /**
         * Lambda Expression Matching.
         * - Is the interface a Functional Interface?
         * - Do the lambda parameters match the parameters of the abstract method from the interface?
         * - Does the return type of the lambda march the return type of the abstract method from the interface?
         *
         **/
        NoParameters np = () -> { System.out.println("No parameters..."); };
        OneParameter op = (p1) -> { System.out.println("The parameter is: " + p1); };
        TwoParameters tp = (p1, p2) -> { System.out.println("The parameters are: " + p1 + " and " + p2); };
        np.print();
        op.print("XXX");
        tp.print("AAA", "BBB");
    }

    private static void demo02() {
        /**
         * Lambda Expressions. Same as anonymous classes but more concise!
         *
         * (arg1, arg2) -> {//body of the lambda};
         *
         **/
        Thread thread = new Thread(
                () -> {
                    System.out.println("Hello from inside the anonymous thread...");
                }
        );

        thread.start();
    }

    private static void demo01() {
        /**
         * Anonymous Classes allow:
         * - Creating an object
         * - Defining the custom implementation for the class
         **/
        Thread thread = new Thread() {
            public void run() {
                System.out.println("Hello from inside the anonymous thread...");
            }
        };

        thread.start();
    }
}
