package com.example.thomas.sncf2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomas on 23/12/2017.
 */

public class ObjetAdaptater extends ArrayAdapter<HashMap<String,String>> {

    public ObjetAdaptater(Activity context, ArrayList mapArrayLIst) {
        super(context, 0,mapArrayLIst);
    }

    @Override
    public View getView (int position , View contentView, ViewGroup parent){
        if (contentView == null){
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.row_objet,parent, false);
        }

        ObjetViewHolder viewHolder = (ObjetViewHolder) contentView.getTag();

        if (viewHolder == null){
            viewHolder = new ObjetViewHolder();
            viewHolder.gare = (TextView) contentView.findViewById(R.id.gare);
            viewHolder.date= (TextView) contentView.findViewById(R.id.date);
            viewHolder.type= (TextView) contentView.findViewById(R.id.type);
            viewHolder.nature= (TextView) contentView.findViewById(R.id.nature);
            contentView.setTag(viewHolder);
        }

        HashMap<String,String> objet = getItem(position);
        viewHolder.gare.setText(objet.get("gare"));
        viewHolder.date.setText(objet.get("date"));
        viewHolder.nature.setText(objet.get("nature"));
        viewHolder.type.setText(objet.get("type"));

        return contentView;
    }


    private class ObjetViewHolder{
        public TextView gare ;
        public TextView date ;
        public TextView nature ;
        public TextView type ;
    }
}
