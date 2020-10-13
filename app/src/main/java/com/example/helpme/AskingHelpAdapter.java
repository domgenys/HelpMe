package com.example.helpme;



import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AskingHelpAdapter extends ArrayAdapter<AskingHelp> {//arrayadapter of my table

    private Context context; //using inside in List of Requests
    private List<AskingHelp> visitors;
//public cunstructor of Askinghelp object
    public AskingHelpAdapter(Context context, List<AskingHelp> list){

        super(context, R.layout.row_layout, list);//passing this context to created layout
        this.context = context;
        this.visitors = list;//passing list

    }
    //Getting a link to row_layout, that why using LAYOUT_INFLATER_SERVICE
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

         //using  layout inflater because the data is on external layout ,not on activities layout.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //got connection to  row_layout
        convertView = inflater.inflate(R.layout.row_layout, parent, false);

        TextView textChar = convertView.findViewById(R.id.textChar);
        TextView textName = convertView.findViewById(R.id.textName);
        TextView textNumber = convertView.findViewById(R.id.textNumber);

        textChar.setText(visitors.get(position).getName().toUpperCase().charAt(0) + "");
        textName.setText(visitors.get(position).getName());
        textNumber.setText(visitors.get(position).getNumber());
        return convertView;
    }
}
