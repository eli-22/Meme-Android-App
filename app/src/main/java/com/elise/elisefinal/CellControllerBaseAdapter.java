package com.elise.elisefinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elise.elisefinal.model.Meme;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CellControllerBaseAdapter extends BaseAdapter {

        Context context;
        ArrayList<Meme> memeArrayList;

    public CellControllerBaseAdapter(Context context, ArrayList<Meme> memeArrayList) {
            this.context = context;
            this.memeArrayList = memeArrayList;
        }

        @Override
        public int getCount() {
            return memeArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return memeArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position,
        View cellReusableViewObject,
        ViewGroup parent) {
            Meme meme = memeArrayList.get(position);

            if (cellReusableViewObject == null) {
                cellReusableViewObject = LayoutInflater
                        .from(context)
                        .inflate(R.layout.database_cell, parent,false);
            }

            TextView cell_id        = cellReusableViewObject.findViewById(R.id.cell_id);
            TextView cell_name      = cellReusableViewObject.findViewById(R.id.cell_name);
            TextView cell_link      = cellReusableViewObject.findViewById(R.id.cell_link);

            String id = String.valueOf(meme.getId());
            cell_id.setText(id);
            cell_name.setText(meme.getName());
            cell_link.setText(meme.getUrl());

            return cellReusableViewObject;
        }
    }
