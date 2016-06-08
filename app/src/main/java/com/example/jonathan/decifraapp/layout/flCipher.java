package com.example.jonathan.decifraapp.layout;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.DatabaseController;
import com.example.jonathan.decifraapp.entities.actual_music;

/**
 * A simple {@link Fragment} subclass.
 */
public class flCipher extends Fragment {

    private actual_music _music;
    private View v;

    public flCipher() {
        // Required empty public constructor
        this._music = actual_music.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fl_cipher, container, false);
        TextView lblTitleMusic = (TextView) v.findViewById(R.id.fl_cipher_lblTitleMusic);
        TextView lblTitleArtist = (TextView) v.findViewById(R.id.fl_cipher_lblTitleArtist);
        TextView lblTab = (TextView) v.findViewById(R.id.fl_cipher_lblTab);

        lblTitleMusic.setText(this._music.get_name());
        lblTitleArtist.setText(this._music.get_artist());
        lblTab.setText(this._music.get_tab());

        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ac_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ac_main_save_cipher:
                DatabaseController crud = new DatabaseController(v.getContext());

                if (!crud.alreadyExists(_music.get_idApi(), _music.get_type())) {

                    if (crud.insert(_music.get_idApi(),
                                    _music.get_name(),
                                    _music.get_artist(),
                                    _music.get_tab(),
                                    _music.get_type()))
                        Toast.makeText(v.getContext(), "Salvo", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(v.getContext(), "Problema ao salvar cifra", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(v.getContext(), "Cifra já salva", Toast.LENGTH_SHORT).show();

                return true;
            default:
                break;
        }

        super.onOptionsItemSelected(item);
        return false;
    }

}
