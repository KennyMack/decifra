package com.example.jonathan.decifraapp.layout;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.actual_music;
import com.example.jonathan.decifraapp.utils.page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class flSearch extends Fragment {

    private actual_music _music;
    private View v;
    private EditText txtUri;
    private Button btnSearch;
    private View vBackGround;
    private ImageView ivWaitIcon;
    private LinearLayout llPrincipal;
    private page _page = page.getInstance(null);

    public flSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fl_search, container, false);
        this.txtUri = (EditText) v.findViewById(R.id.fl_search_txtUrl);
        this.btnSearch = (Button) v.findViewById(R.id.fl_search_btnSearch);
        this.ivWaitIcon = (ImageView) v.findViewById(R.id.fl_search_ivWaitIcon);
        this.vBackGround = v.findViewById(R.id.fl_search_vBackGround);
        this.llPrincipal = (LinearLayout) v.findViewById(R.id.fl_search_llPrincipal);
        this.btnSearch.setOnClickListener(this.btn_Search_Click);
        this._music = actual_music.getInstance();

        return v;
    }

    private View.OnClickListener btn_Search_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            String urlCipher = txtUri.getText().toString();

            if (!urlCipher.equals("")) {
                llPrincipal.setVisibility(View.INVISIBLE);
                vBackGround.setVisibility(View.VISIBLE);
                ivWaitIcon.setVisibility(View.VISIBLE);


                new DownloadTask().execute(clearUrlCipher(urlCipher));
            }
            else
                Snackbar.make(v, "Digite uma Url.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

        }
    };

    private String clearUrlCipher(String url){
        String iniUrl = url.trim().substring(0, 4);
        String ret = "";
        if (iniUrl.toUpperCase().equals("WWW.")) {
            ret = "http://" + url;
        }
        else if (!iniUrl.toUpperCase().equals("WWW.") &&
                iniUrl.toUpperCase().equals("HTTP")) {
            String urlIniHttp = url.substring(0, 11);

            if (!urlIniHttp.toUpperCase().equals("HTTP://WWW.")) {
                urlIniHttp = "http://www.";
                ret = urlIniHttp + url.substring(7, url.length());
            }
            else
                ret = url;
        }
        else if (!iniUrl.toUpperCase().equals("WWW.") &&
                !iniUrl.toUpperCase().equals("HTTP")) {
            ret = "http://www." + url;
        }
        else {
            ret = url;
        }


        return ret.toLowerCase();
    }

    private void goToCipher() {
        _page.setPageVisible(R.id.nav_cipher);
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return downloadContent(params[0]);
            } catch (IOException e) {
                return "invalid";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("invalid")) {
                _music.set_tab(result);
                _music.set_artist("Randon");
                _music.set_name("Wat");
                goToCipher();
            }
            else {
                llPrincipal.setVisibility(View.VISIBLE);
                vBackGround.setVisibility(View.INVISIBLE);
                ivWaitIcon.setVisibility(View.INVISIBLE);
                Snackbar.make(v, "Cifra não encontrada.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    private String downloadContent(String purl) throws IOException {
        InputStream is = null;


        try {
            URL url = new URL(purl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

            return inputStreamToString(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String inputStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
