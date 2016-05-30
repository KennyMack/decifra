package com.example.jonathan.decifraapp.entities;

/**
 * Created by jonathan on 29/05/16.
 */
public class music {
    private String _id;
    private String _name;
    private String _artist;

    public String get_tab() {
        return _tab;
    }

    public void set_tab(String _tab) {
        this._tab = _tab;
    }

    public String get_artist() {
        return _artist;
    }

    public void set_artist(String _artist) {
        this._artist = _artist;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String _tab;


}
