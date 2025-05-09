package utilities;

/**
 *
 * @param <E> es el tipo de entidad asociada al evento
 * @param <S> es el tipo del servidor que procesa el evento
 */
public abstract class Event<E, S> {

    protected final S server;
    protected final E entity;
    protected final int priority;
    protected final float clock;

    public Event(S server, E entity, int priority, float clock) {
        this.server = server;
        this.entity = entity;
        this.priority = priority;
        this.clock = clock;
    }

    public abstract void analytics(Analytics analytics);

    public int getPriority() {
        return priority;
    }

    public float getClock() {
        return clock;
    }

    public S getServer() {
        return server;
    }

    public E getEntity() {
        return entity;
    }

    public abstract boolean process(FutureEventsList fel, Generator generator);

    @Override
    public String toString() {
        return "(" + entity + "," + clock + ")";
    }

}
