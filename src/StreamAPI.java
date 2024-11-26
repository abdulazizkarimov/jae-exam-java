import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamAPI {
    public static void main(String[] args) {
        List<Student> students = getStudents();

        System.out.println("All students:");
        students.forEach(System.out::println);

        // *** Map ***
        List<String> studentNames = students.stream().map(Student::name).toList();
        System.out.println("\nNames of all students:");
        studentNames.forEach(System.out::println);

        // *** Filter ***
        List<Student> seniors = students.stream()
                .filter(student -> student.classification().equals(Classification.SENIOR))
                .toList();
        System.out.println("\nSeniors:");
        seniors.forEach(System.out::println);

        // *** Sort ***
        List<Student> sortedByAge = students.stream()
                .sorted(Comparator.comparing(Student::age)
                        .thenComparing(Student::classification)
                        .reversed())
                .toList();
        System.out.println("\nSorted by age:");
        sortedByAge.forEach(System.out::println);

        // *** All match ***
        boolean allMatch = students.stream()
                .allMatch(student -> student.age() > 19);
        System.out.println("\nAll students are older than 10: " + allMatch);

        // *** Any match ***
        boolean anyMatch = students.stream()
                .anyMatch(student -> student.name().equals("Juliana"));
        System.out.println("\nThere is a student named Juliana: " + anyMatch);

        // *** None match ***
        boolean noneMatch = students.stream()
                .noneMatch(student -> student.age() >= 32);
        System.out.println("\nAll students are younger than 32: " + noneMatch);

        // *** Max ***
        System.out.println("\nOldest student:");
        students.stream()
                .max(Comparator.comparing(Student::age))
                .ifPresent(System.out::println);

        // *** Min ***
        System.out.println("\nYoungest student:");
        students.stream()
                .min(Comparator.comparing(Student::age))
                .ifPresent(System.out::println);

        // *** Group ***
        Map<Classification, List<Student>> classificationListMap = students.stream()
                .collect(Collectors.groupingBy(Student::classification));
        classificationListMap.forEach(((classification, students1) -> {
            System.out.println("\n" + classification);
            students1.forEach(System.out::println);
        }));

        // *** Chaining ***
        System.out.println("\nName of the oldest senior student:");
        Optional<String> oldestSenior = students.stream()
                .filter(student -> student.classification().equals(Classification.SENIOR))
                .max(Comparator.comparing(Student::age))
                .map(Student::name);
        oldestSenior.ifPresent(System.out::println);
    }

    private static List<Student> getStudents() {
        return List.of(
                new Student("Arthur", 17, Classification.JUNIOR),
                new Student("Vincent", 26, Classification.FRESHMAN),
                new Student("Cecelia", 25, Classification.SENIOR),
                new Student("Larry", 25, Classification.SOPHOMORE),
                new Student("Fernando", 23, Classification.FRESHMAN),
                new Student("Kaleb", 18, Classification.SOPHOMORE),
                new Student("Alexandra", 25, Classification.FRESHMAN),
                new Student("Nina", 31, Classification.SENIOR),
                new Student("Jake", 19, Classification.JUNIOR),
                new Student("Austin", 19, Classification.SENIOR),
                new Student("Juliana", 17, Classification.SOPHOMORE),
                new Student("Bianca", 27, Classification.JUNIOR)
        );
    }
}

enum Classification {
    FRESHMAN,
    SOPHOMORE,
    JUNIOR,
    SENIOR
}

record Student(String name, int age, Classification classification) {
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", classification=" + classification +
                '}';
    }
}