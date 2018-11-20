package ru.tinkoff.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    Adapter adapter;
    EditText inputValue;
    Integer parentId;

    private final NodeDao nodeDao = App.getMyDatabase().getNodeDao();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        parentId = intent.getIntExtra("ID", 0);

        inputValue = findViewById(R.id.value);
        recyclerView = findViewById(R.id.nodes);
        adapter = new Adapter(this, this::onItemClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        getAndAddChildNodes(parentId);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            String inputVal = inputValue.getText().toString();
            Integer val = 0;
            if (!inputVal.equals("")) {
                val = Integer.valueOf(inputVal);
            }
            Node node = new Node( val, parentId);
            addNode(node);

        });


    }


    private void addNode(Node node) {
        compositeDisposable.add(
                nodeDao.insertNode(node)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            adapter.add(node);
                        })
        );

    }

    private void getAndAddChildNodes(Integer id) {
        compositeDisposable.add(
                nodeDao.getAllNodesForParentId(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(nodes -> {
                            for (int i = 0; i < nodes.size(); i++) {
                                adapter.add(nodes.get(i));
                            }
                        })
        );
    }

    private void onItemClick(Integer id) {
        startActivity(id);
    }


    private void startActivity(Integer id) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}