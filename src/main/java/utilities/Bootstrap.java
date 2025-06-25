package utilities;


public class Bootstrap {

    public Analytics run(Generator generator,Event start,Event end, Analytics analytic) {
        FutureEventsList fel = new FutureEventsList();
        Event e;
        fel.insert(end);
        fel.insert(start);
        e = fel.inminent();
        while (e.process(fel, generator)) {
            e.analytics(analytic);
            e = fel.inminent();
        }
        e.analytics(analytic);
        return analytic;
    }
}
