import java.util.*;
/*
This is the main TrashMap class with all the internal implementation.
 */
public class TrashMap<K,V> implements Map<K,V> {
    /*
    Internal classes for the implementation of the TrashMap
     */
    //This class represents the individual pieces of trash that will be stored inside a trashcan
    //Analogous to an "entry"
    protected class PieceOfTrash {
        private K key;
        private V value;

        public PieceOfTrash(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return value;
        }
    }

    //TrashCans will be the various "buckets" for each trash type
    protected class TrashCan {
        private Stack<PieceOfTrash> mainContents = new Stack<>();

        public V dig(K key) {
            V foundItem = null;

            Stack<PieceOfTrash> lookingPile = new Stack<>();

            boolean found = false;
            while(!found) {
                if(mainContents.peek().key.equals(key)) {
                    //The item on top of the main contents is what we are looking for
                    foundItem = mainContents.pop().getValue();
                    found = true;
                } else {
                    //It's not, keep digging
                    if(!mainContents.empty()) {
                        lookingPile.push(mainContents.pop());
                    } else {
                        //Item is not found, we got to the bottom of the main pile without finding it
                        return null;
                    }
                }
            }
            //Ok now that it has been found we need to put the contents of the other can back into the main contents
            while(!lookingPile.empty()) {
                mainContents.push(lookingPile.pop());
            }
            //Return the item we have found
            return foundItem;

        }

        public boolean contains(V value) {
            Stack<PieceOfTrash> pile = new Stack<>();

            boolean found = false;
            while(!found) {
                if(mainContents.peek().value.equals(value)) {
                    found = true;
                } else {
                    if(!mainContents.empty()) {
                        pile.push(mainContents.pop());
                    } else {
                        //not found and we hit the end of the stack
                        break;
                    }
                }
            }
            //put everything back
            while(!pile.empty()) {
                mainContents.push(pile.pop());
            }

            return found;
        }

        public void add(PieceOfTrash e) {
            mainContents.push(e);
        }

        public boolean isEmpty() {
            return mainContents.empty();
        }

        public int size() {
            return mainContents.size();
        }
    }

    /*
    Instance variables for each different trash bucket
     */
    private ArrayList<TrashCan> raccoonProofStorage = new ArrayList<>();

    /*
    Constructors
     */
    //This is just the basic constructor that creates a new TrashMap
    public TrashMap () {
        //Add all the trashcans (one for each trash type) to the raccoon proof storage
        for(int i=0; i<Trashifier.TrashType.values().length; i++) {
            this.raccoonProofStorage.add(new TrashCan());
        }

    }

    //This constructor creates a new TrashMap with the contents of any other map
    public TrashMap(Map<K,V> m) {
        this();
        for(K key: m.keySet()) {
            this.put(key, m.get(key));
        }

    }

    /*
    Map interface methods
     */
    @Override
    public int size() {
        int size = 0;
        for(TrashCan trashCan: this.raccoonProofStorage) {
            size += trashCan.size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        //In order to see if it contains a key, we must first see which trashcan it would be in, then check that one
        //Do this by getting the trashcode for the key

        Trashifier.TrashType keyType = Trashifier.trashType(key);
        TrashCan can = this.raccoonProofStorage.get(keyType.ordinal());
        try {
            V value = can.dig((K)key);
            return value != null;
        } catch (ClassCastException e) {
            return false;
        }

    }

    @Override
    public boolean containsValue(Object value) {
        V typedValue = null;
        try {
            typedValue = (V)value;
        } catch (ClassCastException e) {
            return false; //value type does not match or cannot be casted to TrashMap value type, we can't do anything with it
        }
        //because I can't think of a better way to do this right now: just search all the storages to see if it contains the given value
        for(TrashCan trashCan: this.raccoonProofStorage) {
            if(trashCan.contains(typedValue)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public V get(Object key) {
        //We need to find out what bucket it's in, get its trashtype first
        K typedKey = null;
        try {
            typedKey = (K)key;
        } catch (ClassCastException e) {
            throw new NoSuchElementException();
        }
        if(typedKey != null) {
            //If we got here, we were able to cast the key to our actual key type so that's good
            //Really crap variable naming but who cares really
            Trashifier.TrashType keyType = Trashifier.trashType(typedKey);
            TrashCan containingCan = this.raccoonProofStorage.get(keyType.ordinal());
            //Dig for the item in the trashcan
            V value = containingCan.dig(typedKey);
            if(value == null) {
                throw new NoSuchElementException();
            }
            return value;
        }
        //Should never happen
        return null;
    }

    @Override
    public V put(K key, V value) {
        //We need to determine the TrashType of the key so we know which trashcan to put the piece of trash in
        Trashifier.TrashType keyType = Trashifier.trashType(key);
        System.out.printf("Storing %S (key %S) in trashcan %S%n", value, key, keyType);
        PieceOfTrash item = new PieceOfTrash(key, value);
        this.raccoonProofStorage.get(keyType.ordinal()).add(item);
        //Don't really know why this returns anything, maybe in the future return what it's replacing??
        //For now we just return the same thing we added to confirm it was added I guess
        return value;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(K key: m.keySet()) {
            this.put(key, m.get(key));
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
