package com.example.jonathan.decifraapp.entities;

/**
 * Created by jonathan on 29/05/16.
 */
public class actual_music {

    private actual_music(){

    }

    private static actual_music _instance = null;
    public static actual_music getInstance(){
        if (_instance == null){
            _instance = new actual_music();
        }
        return _instance;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_artist() {
        return _artist;
    }

    public void set_artist(String _artist) {
        this._artist = _artist;
    }

    public String get_tab() {
        return _tab;
    }

    public void set_tab(String _tab) {
        this._tab = _tab;
    }

    private String _name;
    private String _artist;
    private String _tab;
}
