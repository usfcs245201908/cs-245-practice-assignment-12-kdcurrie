public class Hashtable {
    int size;
    HashNode[] bucket;
    double loadFactor = (float) 0.5;
    int entries = 0;

    class HashNode {
        String key;
        String value;
        HashNode next;

        public HashNode(String key, String value){
            this.key = key;
            this.value = value;
            next = null;
        }
    }
    Hashtable(){
        size = 10000;
        bucket = new HashNode[size];
        entries = 0;
    }

    /**********************************************************************
     *containsKey
     * Returns true if a key/value pair exists with any unspecific value
     *********************************************************************/
    boolean containsKey(String key) {
        return bucket[getHash(key)] != null;
    }

    /*****************************************************************************************
     *Get Function
     * Returns the value associated with the key which is passed as an argument; returns ​null​
     *if no key/value pair is contained by theHashtable instance.
     *****************************************************************************************/
    String get(String key){
        HashNode head = bucket[getHash(key)];

        while(head != null){
            if (head.key.equals(key)){
                return head.value;
            } else {
                head = head.next;
            }
        }
        return null;
    }

    HashNode getNode(String key){
        HashNode head = bucket[getHash(key)];

        while(head != null){
            if (head.key.equals(key)){
                return head;
            } else {
                head = head.next;
            }
        }
        return null;
    }

    /***********************************************************************************
    *Put Function
     * first checks if the first position is empty
     * then checks if key value is the same, it will replace the value
     * otherwise loops until a next Node doesn't exist and adds to the end of the list.
     * separate chaining
    ***********************************************************************************/
    void put (String key, String value){
        HashNode newNode = new HashNode(key, value);
        if (bucket[getHash(key)] == null){
            bucket[getHash(key)] = newNode;
        } else {
            HashNode currentNode = bucket[getHash(key)];
            while (currentNode != null) {
                if (currentNode.key.equals(key)) {
                    currentNode.value = value;
                    return;
                }
                if(currentNode.next == null) {
                    currentNode.next = newNode;
                    ++entries;
                    if ((entries * 1.0) / size >= loadFactor){
                        increaseBucketSize();
                    }
                    return;
                }
                currentNode = currentNode.next;
            }
        }
    }

    /*****************************************************************************************
     *Remove Function
     * Removes the key/value pair from the Hashtable instance and returns the value associated
     * with the key to the caller. Throws an Exception instance if the key is not present in
     * the Hashtable instance.
     ****************************************************************************************/
    String remove(String key) throws Exception {
//        HashNode removeNode = getNode(key);
//        HashNode nextNode = removeNode.next;
//        if(nextNode != null) {
//
//        }
        HashNode head = bucket[getHash(key)];

        // if null, then done
        if (head != null) {
            if (head.key.equals(key)) {
                bucket[getHash(key)] = head.next;
                --entries;
                return head.value;
            } else {
                HashNode prev = head;
                HashNode curr;

                while (head.next != null) {
                    curr = head.next;
                    if (curr != null && curr.key == key) {
                        head.next = curr.next;
                        --entries;
                        return curr.value;
                    }
                }
                return null;
            }
        } else {
            throw new Exception();
        }
    }
    /***************
     *Hash Function
     ***************/
    int getHash(String key){
        return Math.abs(key.hashCode() % size);
    }
    /********************************************
     *doubles bucket size if we reached 50% full
     *******************************************/
    private void increaseBucketSize(){
        HashNode[] temp = bucket;
        size = size *2;
        bucket = new HashNode[size];
//        entries = 0;
        for(HashNode head : temp) {
            while(head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }
    }
}