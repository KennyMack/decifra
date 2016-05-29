package com.example.jonathan.decifraapp.layout;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.actual_music;

/**
 * A simple {@link Fragment} subclass.
 */
public class flCipher extends Fragment {

    private actual_music _music;
    private TextView lblTitleMusic;
    private TextView lblTitleArtist;
    private TextView lblTab;

    public flCipher() {
        // Required empty public constructor
        this._music = actual_music.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fl_cipher, container, false);
        lblTitleMusic = (TextView) v.findViewById(R.id.fl_cipher_lblTitleMusic);
        lblTitleArtist = (TextView) v.findViewById(R.id.fl_cipher_lblTitleArtist);
        lblTab = (TextView) v.findViewById(R.id.fl_cipher_lblTab);

        lblTitleMusic.setText(this._music.get_name());
        lblTitleArtist.setText(this._music.get_artist());
        lblTab.setText(this._music.get_tab());

        return v;
    }

}
