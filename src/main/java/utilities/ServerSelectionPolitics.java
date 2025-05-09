package utilities;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class ServerSelectionPolitics<T> {

    protected ArrayList<T> servers;
    protected Comparator<T> comparator;

    public abstract T selectServer();

    public ArrayList<T> getServers() {
        return servers;
    }

}
