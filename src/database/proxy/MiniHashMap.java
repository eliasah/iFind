package database.proxy;

public class MiniHashMap<K, V> {

    static class Entry<K, V> {

        private final K key;

        private V value;

        private MiniHashMap.Entry<K, V> next = null;

        public Entry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(final V value) {
            this.value = value;
        }

        public void setNext(final Entry<K, V> newEntry) {
            next = newEntry;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public boolean isNotLast() {
            return (next != null);
        }
    }

    private final static int DEFAULT_CAPACITY = 16;

    private int capacity = DEFAULT_CAPACITY;
    private Entry<K, V>[] values = null;

    public MiniHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public MiniHashMap(int capacity) {
        if (capacity > DEFAULT_CAPACITY) {
            this.capacity = capacity;
        }
        values = new Entry[this.capacity];
    }

    public void put(final K key, final V value) {
        // select bucket
        final int index = getIndex(key.hashCode());
        Entry<K, V> current = values[index];
        if (null == current) {
            // insert if still empty
            values[index] = new Entry<K, V>(key, value);
        } else {
            if (key.equals(current.getKey())) {
                // change value if already in the hash map and stop 
                current.setValue(value);
                return;
            }
            while (current.isNotLast()) {
                // search all entries in the bucket
                current = current.getNext();
                if (key.equals(current.getKey())) {
                    // change value if already in the hash map and stop 
                    current.setValue(value);
                    return;
                }
            }
            // insert at the end
            current.setNext(new Entry<K, V>(key, value));
        }
    }

    public boolean remove(final K key) {
        // select bucket
        final int index = getIndex(key.hashCode());
        Entry<K, V> current = values[index];
        Entry<K, V> previous = null;
        while (null != current) {
            if (key.equals(current.getKey())) {
                if (null == previous) {
                    // remove first entry + stop
                    values[index] = current.getNext();
                } else {
                    // remove middle + stop
                    previous.setNext(current.getNext());
                    current = null;
                }
                return true;
            }
            // continue with the next entries
            previous = current;
            current = current.getNext();
        }
        return false;
    }

    public V get(final K key) {
        final int index = getIndex(key.hashCode());
        Entry<K, V> current = values[index];
        while (null != current) {
            if (key.equals(current.getKey())) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    private int getIndex(int hash) {
        if (hash < 0) {
            hash = -hash;
        }
        return (hash % capacity);
    }
}