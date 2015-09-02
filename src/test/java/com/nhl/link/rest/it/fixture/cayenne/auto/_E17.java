package com.nhl.link.rest.it.fixture.cayenne.auto;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

/**
 * Class _E17 was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _E17 extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID1_PK_COLUMN = "id1";
    public static final String ID2_PK_COLUMN = "id2";

    public static final Property<Integer> ID1 = new Property<Integer>("id1");
    public static final Property<Integer> ID2 = new Property<Integer>("id2");
    public static final Property<String> NAME = new Property<String>("name");

    public void setId1(Integer id1) {
        writeProperty("id1", id1);
    }
    public Integer getId1() {
        return (Integer)readProperty("id1");
    }

    public void setId2(Integer id2) {
        writeProperty("id2", id2);
    }
    public Integer getId2() {
        return (Integer)readProperty("id2");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

}