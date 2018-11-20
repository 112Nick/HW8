package ru.tinkoff.myapplication;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import java.util.List;

@Entity
public class Node {


    //    @PrimaryKey
    @PrimaryKey(autoGenerate = true)
    private int id;

    private final int value;

    private long parentId;

    @Ignore
    @Nullable
    private final List<Node> children;

    public Node(int value, long parentId) {

        this(value, parentId, null);
    }

//    public Node(int value, long parentId) {
//        this(value, parentId, null);
//    }

    @Ignore
    public Node(int value, long parentId, @Nullable List<Node> children) {
//        this.id = id;
        this.value = value;
        this.parentId = parentId;
        this.children = children;
    }

//    @Ignore
//    public Node(int value, long parentId, @Nullable List<Node> children) {
//        this.value = value;
//        this.parentId = parentId;
//        this.children = children;
//    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public long getParentId() {
        return parentId;
    }

    @Nullable
    public List<Node> getChildren() {
        return children;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}