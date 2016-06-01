package com.example.jonathan.decifraapp.layout;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.music;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class flMyCiphers extends Fragment {
    private lMusicItem _lMusicItem;
    private ListView lstDados;

    public flMyCiphers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fl_my_ciphers, container, false);
        lstDados = (ListView)v.findViewById(R.id.fl_my_ciphers_lvCiphers);
        ArrayList<music> contMusics = new ArrayList<music>();
        music md1 = new music();
        md1.set_id("1");
        md1.set_name("music 1");
        md1.set_artist("Artist 1");

        music md2 = new music();
        md2.set_id("2");
        md2.set_name("music 2");
        md2.set_artist("Artist 2");

        music md3 = new music();
        md3.set_id("3");
        md3.set_name("music 3");
        md3.set_artist("Artist 3");

        contMusics.add(md1);
        contMusics.add(md2);
        contMusics.add(md3);

        _lMusicItem = new lMusicItem(v.getContext(), R.layout.l_music_item, contMusics);

        lstDados.setAdapter(_lMusicItem);

        return v;
    }

}
