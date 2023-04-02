package com.example.beermanager;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class FragmentTwo extends Fragment {

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_two, container, false);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(v.getContext());
        List<DayInfo> allDays = dataBaseHelper.getGoodDays();

        if (!allDays.isEmpty()) {
            ListView lv_list = v.findViewById(R.id.framgent_good_lv);

            ArrayAdapter<DayInfo>arrayAdapter = new ArrayAdapter<DayInfo>(v.getContext(), android.R.layout.simple_list_item_1, allDays){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    if(getItem(position).getTrue())
                        v.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    else
                        v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return v;
                }
            };
            lv_list.setAdapter(arrayAdapter);
        } else
        Toast.makeText(mContext, "Historie je prázdná, nejsou žádné záznamy o úspěších.", Toast.LENGTH_SHORT).show();


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

}