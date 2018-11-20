package ru.tinkoff.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NodeViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<Node> data;

    private final OnItemClickListener<Integer> onItemClickListener;

    public Adapter(Context context, OnItemClickListener<Integer> onItemClickListener) {
//    public Adapter(Context context) {


        layoutInflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public NodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NodeViewHolder(layoutInflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NodeViewHolder holder, int position) {
//        holder.bind(data.get(position), onItemClickListener);
        holder.bind(data.get(position), onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void add(Node newData) {
        data.add(0, newData);
        notifyItemInserted(0);
    }

//    public void addToPosition(Worker newData) {
//        data.add(data.indexOf(newData), newData);
//        notifyItemInserted(data.indexOf(newData));
//    }

    public void remove(Node w) {
        notifyItemRemoved(data.indexOf(w));
        data.remove(w);
    }



    final static class NodeViewHolder extends RecyclerView.ViewHolder {
        private final TextView value;
        private final RelativeLayout container;


        NodeViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            value = itemView.findViewById(R.id.value);
        }

        //        void bind(ResourceItem resourceItem, OnItemClickListener<ResourceItem> onItemClickListener) {
        void bind(Node node, OnItemClickListener onItemClickListener) {
            value.setText(String.valueOf(node.getValue()));
            int color;
            if (node.getParentId() == 0) { //TODO
                if (node.getChildren() == null ) {
                    container.setBackgroundResource(R.color.colorNone);
                } else {
                    container.setBackgroundResource(R.color.colorChild);
                }
            } else {
                if (node.getChildren() == null) {
                    container.setBackgroundResource(R.color.colorParent);
                } else {
                    container.setBackgroundResource(R.color.colorParentAndChild);
                }
            }
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(node.getId()));
        }
    }
}