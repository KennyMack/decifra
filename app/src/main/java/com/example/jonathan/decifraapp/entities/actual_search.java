package com.example.jonathan.decifraapp.entities;

/**
 * Created by jonathan on 05/06/16.
 */
public class actual_search {
    private static actual_search _instance = null;

    private String _searchText;

    private actual_search(){

    }

    public static actual_search getInstance(){
        if (_instance == null){
            _instance = new actual_search();
        }
        return _instance;
    }

    public String get_searchText() {
        return _searchText;
    }

    public void set_searchText(String search) {
        this._searchText = search;
    }
}
