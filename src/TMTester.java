/*
This class is solely for messing around with various functionality of the TrashMap and the Trashifier.
 */
public class TMTester {
    public static void main(String[] args) {
        //Test out the trash type generation
        String s1 = "Hello World";
        String s2 = "no one likes Java";
        String s3 = "woof";

        int num1 = 525600;
        int num2 = 8675309;
        int num3 = 12345679;

        System.out.println("strings");
        System.out.println(Trashifier.trashType(s1));
        System.out.println(Trashifier.trashType(s2));
        System.out.println(Trashifier.trashType(s3));

        System.out.println("ints");
        System.out.println(Trashifier.trashType(num1));
        System.out.println(Trashifier.trashType(num2));
        System.out.println(Trashifier.trashType(num3));

        //Test the actual TrashMap
        TrashMap<String, Integer> tmTest = new TrashMap<>();
        tmTest.put("hello", 32);
        tmTest.put("Goodbye", 64);
        System.out.println("retrieval");
        System.out.println(tmTest.get("hello"));
        System.out.println(tmTest.get("Goodbye"));


    }
}
