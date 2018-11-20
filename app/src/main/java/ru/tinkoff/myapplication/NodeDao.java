package ru.tinkoff.myapplication;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public abstract class NodeDao {

    @Transaction
    public Single<Node> getNodeWithChildrenById(long id) {
        return Single.zip(
                getNodeById(id),
                getAllNodesForParentId(id),
                (node, children) -> new Node(
//                        node.getId(),
                        node.getValue(),
                        node.getParentId(),
                        children
                )
        );
    }

    public Completable insertNode(Node node) {
        return Completable.fromRunnable(() -> insertNodeInternal(node));
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertNodeInternal(Node node);

    @Query("SELECT * FROM Node WHERE id = :id")
    protected abstract Single<Node> getNodeById(long id);

    @Query("SELECT * FROM Node WHERE parentId = :parentId")
    protected abstract Single<List<Node>> getAllNodesForParentId(long parentId);
}