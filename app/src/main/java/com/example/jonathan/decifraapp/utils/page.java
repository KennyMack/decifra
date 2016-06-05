package com.example.jonathan.decifraapp.utils;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.layout.flCipher;
import com.example.jonathan.decifraapp.layout.flHome;
import com.example.jonathan.decifraapp.layout.flMyCiphers;
import com.example.jonathan.decifraapp.layout.flResult;
import com.example.jonathan.decifraapp.layout.flSearch;

/**
 * Created by jonathan on 29/05/16.
 */
public class page {
    private FragmentManager _fmPrincipal;
    private static page _instance = null;

    public static page getInstance(FragmentManager fm){
        if (_instance == null) {
            _instance = new page(fm);
        }

        return _instance;
    }

    private page(FragmentManager fm){
        _fmPrincipal = fm;
    }

    public void setPageVisible(int page) {
        FragmentTransaction ftPage = _fmPrincipal.beginTransaction();

        if (page == R.id.nav_home){
            flHome _flhome = new flHome();
            //ftPage.add(_flhome, "Home");
            //ftPage.addToBackStack("Home");
            ftPage.replace(R.id.flPrincipal, _flhome);
        }
        else if (page == R.id.nav_my_ciphers) {
            flMyCiphers _flmyciphers = new flMyCiphers();
            //ftPage.add(_flmyciphers, "MyCiphers");
            //ftPage.addToBackStack("MyCiphers");
            ftPage.replace(R.id.flPrincipal, _flmyciphers);
        }
        else if (page == R.id.nav_search) {
            flSearch _flsearch = new flSearch();
            ftPage.replace(R.id.flPrincipal, _flsearch);
            //ftPage.add(_flsearch, "Search");
            //ftPage.addToBackStack("Search");
        }
        else if (page == R.id.nav_cipher) {
            flCipher _flcipher = new flCipher();
            ftPage.replace(R.id.flPrincipal, _flcipher);
            //ftPage.add(_flcipher, "Cipher");
            //ftPage.addToBackStack("Cipher");
        }
        else if (page == R.id.nav_result) {
            flResult _flResult = new flResult();
            ftPage.replace(R.id.flPrincipal, _flResult);
            //ftPage.add(_flcipher, "Cipher");
            //ftPage.addToBackStack("Cipher");
        }
        //ftPage.commit();
        ftPage.commitAllowingStateLoss();
    }

}
