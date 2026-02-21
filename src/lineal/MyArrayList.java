package lineal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;



public class MyArrayList<E> implements List<E>{

    private E[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    
    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity) {
        data = (E[]) new Object[initialCapacity];
        size = initialCapacity;
    }

    
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size >= data.length) {
            // duplicamos la capacidad
            int newCapacity = Math.max(1, data.length * 2);
            E[] newData = (E[]) new Object[newCapacity];
        
            // copiamos los elementos
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }

            data = newData; // apuntamos al nuevo array
        }
    }

    @Override
    public boolean add(E e) {
        ensureCapacity();
        data[size++] = e;
        return true;
    }

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        
        ensureCapacity();
        for (int i = size(); i > index; i--){
            data[i] = data[i-1];
        }
        data[index] = e;
        size++;
    }

    
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size(); i++) {
            if (o == null ? data[i] == null : o.equals(data[i]) ) {
                for (int j = i; j < size()-1; j++) {
                    data[j] = data[j+1];
                }
                data[--size] = null;
                return true;            
            }
        }
        return false;
    }

    @Override
    public E remove(int index) {
        
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        E element = data[index];
        for (int i = index; i < size; i++) {
            data[i] = data[i+1];
        }
        data[--size] = null;    
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size(); i++) {
            if (o.equals(data[i]))
                return true;
        }
        return false;
    }

    @Override
    public E[] toArray() {
        E[] newArray;

        newArray = data.clone();

        return newArray;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {

        
        if (a.length >= size()){
            for (int i = 0; i < size(); i++) 
                a[i] = (T) data[i];
            return a;
        } else {
            T[] newArray = (T[]) Arrays.copyOf(a, size(), a.getClass());
            for (int i = 0; i < size(); i++) 
                newArray[i] = (T) data[i];
            return newArray;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        
        for (Object o : c) {
            if(!contains(o))
                return false;
        }
        
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
            
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int offset = 0;
        for (E e : c) {
            add(index + offset, e);
            offset++;
        }
        
        return true;
    }

    @Override
    public E get(int index) {
        return data[index];
    }

    @Override
    public String toString() {
        if (this.isEmpty())
            return "[]";
        String str = "[";
        for (int i = 0; i < size()-1; i++) {
            str += data[i] + ", ";
        }

        return str + data[size-1] + "]";

    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        
        E previous = data[index];
        data[index] = element;
        return previous;
    }

    @Override
    public int indexOf(Object o) {
        
        if (o == null) {
            for (int i = 0; i < size(); i++){
                if (get(i) == null)
                    return i;
            }
        } else {
            for (int i = 0; i < size(); i++){
                if (o.equals(get(i)))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size() - 1; i >= 0 ; i--){
                if (get(i) == null)
                    return i;
            }
        } else {
            for (int i = size() - 1; i >= 0 ; i--){
                if (o.equals(get(i)))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }

        size = 0;
    }


    @Override
    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new MyListIterator(index);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        
        for (Object o : c){
            if (remove(o))
                removed = true;
        }

        return removed;
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        boolean anyRemoved = false;
        Iterator<E> it = this.iterator();

        while (it.hasNext()) {
            E e = it.next();
            if (!c.contains(e)) {
                it.remove();    
                anyRemoved = true;
            }
        }
        return anyRemoved;
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        List<E> list = new MyArrayList<>(toIndex - fromIndex);

        if (fromIndex == toIndex)
            return list;

        for (int i= fromIndex; i < toIndex; i++){
            list.add(data[i]);
        }

        return list;
    }

    private class MyListIterator implements ListIterator<E> {

        int cursorPosition;
        boolean previousMade;
        boolean nextMade;

        public MyListIterator() {
            cursorPosition = 0;
            previousMade = false;
            nextMade = false;
        }

        public MyListIterator(int index) {
            cursorPosition = index;
            previousMade = false;
            nextMade = false;

        }

        @Override
        public boolean hasNext() {
            return cursorPosition < size();
        }

        @Override
        public E next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            previousMade = false;
            nextMade = true;
            return (E) data[cursorPosition++];
        }

        @Override
        public boolean hasPrevious() {
            return cursorPosition > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()){
                throw new NoSuchElementException();
            }
            previousMade = true;
            nextMade = false;
            return (E) data[--cursorPosition];
        }

        @Override
        public int nextIndex() {
            if (cursorPosition == size()){
                return size();
            }
            return cursorPosition;
        }

        @Override
        public int previousIndex() {
            if (cursorPosition == 0){
                return 0;
            }
            return cursorPosition - 1;
        }

        @Override
        public void remove() {
            if (previousMade) {
                MyArrayList.this.remove(cursorPosition++);
                
                previousMade = false;
            } else if(nextMade){
                MyArrayList.this.remove(--cursorPosition);
                nextMade = false;
            } else {
                throw new IllegalStateException();
            }
        }

        @Override
        public void set(E e) {
            if (previousMade) {
                MyArrayList.this.set(cursorPosition, e);
                
                previousMade = false;
            } else if(nextMade){
                MyArrayList.this.set(cursorPosition, e);
                nextMade = false;
            } else {
                throw new IllegalStateException();
            }
        }
        

        @Override
        public void add(E e) {
            if (previousMade) {
                MyArrayList.this.add(++cursorPosition, e);
                
                previousMade = false;
            } else if(nextMade){
                MyArrayList.this.add(cursorPosition++, e);
                nextMade = false;
            } else {
                throw new IllegalStateException();
            }
        }

    }
}