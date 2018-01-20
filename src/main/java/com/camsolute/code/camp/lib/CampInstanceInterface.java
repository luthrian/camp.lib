package com.camsolute.code.camp.lib;

import java.sql.Timestamp;

public interface CampInstanceInterface {

    public boolean isFirst();

    public boolean isCurrent();

    public Timestamp timestamp();

    public void timestamp(Timestamp ts);

}
