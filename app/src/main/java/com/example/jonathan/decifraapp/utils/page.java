package com.example.jonathan.decifraapp.utils;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.layout.flCipher;
import com.example.jonathan.decifraapp.layout.flMyCiphers;
import com.example.jonathan.decifraapp.layout.flResult;
import com.example.jonathan.decifraapp.layout.flSearch;

/**
 * Created by jonathan on 29/05/16.
 */
public class page {
    private FragmentManager _fmPrincipal;

    public page(FragmentManager fm){
        _fmPrincipal = fm;
    }

    public void setPageVisible(int page) {
        FragmentTransaction ftPage = _fmPrincipal.beginTransaction();

        if (page == R.id.nav_my_ciphers) {
            flMyCiphers _flmyciphers = new flMyCiphers();
            ftPage.replace(R.id.flPrincipal, _flmyciphers, "flmyciphers");
        } else if (page == R.id.nav_search) {
            flSearch _flsearch = new flSearch();
            ftPage.replace(R.id.flPrincipal, _flsearch, "flsearch");
            ftPage.addToBackStack("bsflsearch");
        } else if (page == R.id.nav_cipher) {
            flCipher _flcipher = new flCipher();
            ftPage.replace(R.id.flPrincipal, _flcipher, "flcipher");
            ftPage.addToBackStack("bsflcipher");
        } else if (page == R.id.nav_result) {
            flResult _flResult = new flResult();
            ftPage.replace(R.id.flPrincipal, _flResult, "flResult");
            ftPage.addToBackStack("bsflResult");
        }

        if (_fmPrincipal.findFragmentByTag("flmyciphers") == null) {
            flMyCiphers _flmyciphers = new flMyCiphers();
            ftPage.replace(R.id.flPrincipal, _flmyciphers, "flmyciphers");
        }

        ftPage.commitAllowingStateLoss();

    }

}
