package iterator;

public interface List<E> {
    Iterator iterator();

    void add(E obj);

    void remove(E obj);
}
