package veronica.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class BigTableDao {
    private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }  // get
}  // class declaration