package com.example.jonathan.decifraapp.layout;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.DatabaseController;
import com.example.jonathan.decifraapp.entities.actual_music;
import com.example.jonathan.decifraapp.entities.music;
import com.example.jonathan.decifraapp.utils.page;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class flMyCiphers extends Fragment {
    private lMusicItem _lMusicItem;
    private ListView lstDados;
    private View v;
    private page _page = page.getInstance(null);

    public flMyCiphers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fl_my_ciphers, container, false);

        DatabaseController db = new DatabaseController(v.getContext());
        Cursor cur = db.loadCipher();

        lstDados = (ListView)v.findViewById(R.id.fl_my_ciphers_lvCiphers);
        lstDados.setOnItemClickListener(lvMyCiphers_itemClick);
        lstDados.setOnItemLongClickListener(llmusicitem_LongClick);

        ArrayList<music> contMusics = new ArrayList<>();
        boolean found = false;
        while (cur.moveToNext()) {
            found = true;
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

        lstDados.setAdapter(_lMusicItem);

        if (!found)
            Snackbar.make(v, "Nenhuma cifra salva", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        return v;
    }

    private AdapterView.OnItemLongClickListener llmusicitem_LongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            DatabaseController dbCtrl = new DatabaseController(view.getContext());
            music _ms = (music) lstDados.getItemAtPosition(position);
            if (dbCtrl.deleteCipher(_ms.get_id())) {
                Toast.makeText(v.getContext(), "Excluido", Toast.LENGTH_SHORT).show();
                _lMusicItem.remove(_ms);
            }
            else
                Toast.makeText(v.getContext(), "Problema ao excluir cifra", Toast.LENGTH_SHORT).show();

            return false;
        }
    };

    private AdapterView.OnItemClickListener lvMyCiphers_itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            music _ms = (music) lstDados.getItemAtPosition(position);
            actual_music music = actual_music.getInstance();

            music.set_artist(_ms.get_artist());
            music.set_name(_ms.get_name());
            music.set_tab(_ms.get_tab());
            music.set_id(_ms.get_id());
            music.set_idApi(_ms.get_idApi());
            music.set_type(_ms.get_type());

            _page.setPageVisible(R.id.nav_cipher);
        }
    };
}
