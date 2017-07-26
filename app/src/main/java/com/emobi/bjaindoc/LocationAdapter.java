package com.emobi.bjaindoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class LocationAdapter extends ArrayAdapter {
        Context context;
        int vgreso;

        public LocationAdapter(Context context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return AddPatientActivity.patientdetails.size();
        }

        @Override
        public Object getItem(int position) {
            return  AddPatientActivity.patientdetails.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textname);
            TextView textnumber = (TextView) itemView.findViewById(R.id.textlogin_id);
            textcontac.setText(AddPatientActivity.patientdetails.get(position).getName().toString());
            textnumber.setText(AddPatientActivity.patientdetails.get(position).getId().toString());
            return itemView;
        }

    }

