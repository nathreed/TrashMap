import java.util.Collection;

/*
This class contains a bunch of static methods for returning the trash type for a specific object
 */
public class Trashifier {
    public enum TrashType {
        BODILY_WASTE, DOG_POOP, FOOD_SCRAPS, COFFEE_GROUNDS, USED_FRY_OIL, HAZARDOUS_CHEMICALS, RUSTED_METAL, YARD_WASTE, RANDOM_JUNK
    }

    private static TrashType typeForCode(double trashCode) {
        //We multiply the number by 5865 (JUNK on a phone keyboard) until it's at least the length of the number of trash types
        while(trashCode < TrashType.values().length) {
            trashCode *= 5865;
        }

        long typeIndex = Math.round(trashCode) % TrashType.values().length;

        return TrashType.values()[(int)typeIndex];
    }

    public static TrashType trashType(String s) {
        //System.out.println("trashType for String");
        //How we come up with the trash code for a string: the sum of all the letters if they were converted to numbers
        //Divide that by 3/4 the number of letters
        //Then treat it as a number
        byte[] strBytes = s.getBytes();
        int runningSum = 0;
        for (byte strByte : strBytes) {
            runningSum += strByte;
        }
        runningSum = (int)(Math.round(0.75 * s.length()));
        return trashType(runningSum);

    }

    public static TrashType trashType(Number n) {
        //System.out.println("trashType for number");
        //We convert the number into a trashcode by doing the number mod the WM hotline then times 87274 (TRASH on a phone)
        return typeForCode((n.doubleValue() % 1.8669094458) * 87274);
    }

    public static TrashType trashType(Object o) {
        //System.out.println("trashType for Object");
        //Treat the object as a string, this will use its built in toString method
        return trashType(o.toString());
    }

    public static TrashType trashType(Collection c) {
        //System.out.println("trashType for Collection");
        //Right now we just do a toString on the collection, maybe later we could do something fun
        return trashType(c.toString());
    }

}
