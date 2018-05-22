package com.mobile2.projeto2.activities.ActivitiesList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile2.projeto2.R;
import com.mobile2.projeto2.entity.Atividade;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivitiesListAdapter extends RecyclerView.Adapter<ActivitiesListAdapter.ViewHolder>{

    private RecyclerInterface recyclerInterface;
    private List<Atividade> activitiesList;
    private Context context;

    ActivitiesListAdapter(List<Atividade> activitiesList, Context context){
        this.activitiesList = activitiesList;
        this.context = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }
    @Override public void onBindViewHolder(ActivitiesListAdapter.ViewHolder holder, int position) {
        Atividade atividade = activitiesList.get(position);
        holder.nome.setText(atividade.getNomeAtv());
        holder.descricao.setText(atividade.getDescAtv());
        holder.tag1.setText(atividade.getTag1());
        holder.tag2.setText(atividade.getTag2());
        holder.tag3.setText(atividade.getTag3());
    }
    @Override public int getItemCount() {
        return activitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.atv_nome) TextView nome;
        @BindView(R.id.atv_desc) TextView descricao;
        @BindView(R.id.tag_1) TextView tag1;
        @BindView(R.id.tag_2) TextView tag2;
        @BindView(R.id.tag_3) TextView tag3;
        @BindView(R.id.users_image) ImageView userImage;
        @BindView(R.id.difficulty_image) ImageView diffImage;


        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.container) void onItemClick(View view){
            if(recyclerInterface != null){
                recyclerInterface.onClick(view, getAdapterPosition());
            }
        }

    }
    public void setRecyclerInterface(RecyclerInterface recyclerInterface){
        this.recyclerInterface = recyclerInterface;
    }

}
