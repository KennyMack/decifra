package com.example.jonathan.decifraapp.layout;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.CreateDatabase;
import com.example.jonathan.decifraapp.entities.DatabaseController;
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
        View v = inflater.inflate(R.layout.fragment_fl_my_ciphers, container, false);

        CreateDatabase createDb = new CreateDatabase(v.getContext());
        DatabaseController db = new DatabaseController(v.getContext());
        Cursor cur = db.carregaDados();

        lstDados = (ListView)v.findViewById(R.id.fl_my_ciphers_lvCiphers);
        ArrayList<music> contMusics = new ArrayList<music>();
        while (cur.moveToNext()) {
            music novo = new music();
            novo.set_id(cur.getString(0));
            novo.set_idApi(cur.getString(1));
            novo.set_name(cur.getString(2));
            novo.set_artist(cur.getString(3));
            novo.set_tab(cur.getString(4));
            novo.set_type(cur.getString(5));
            contMusics.add(novo);
        }
        _lMusicItem = new lMusicItem(v.getContext(), R.layout.l_music_item, contMusics);
        lstDados.setOnItemLongClickListener(llmusicitem_LongClick);
        lstDados.setAdapter(_lMusicItem);

        return v;
    }

    private AdapterView.OnItemLongClickListener llmusicitem_LongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(view.getContext(), "Excluido", Toast.LENGTH_LONG).show();
            return false;
        }
    };
}
