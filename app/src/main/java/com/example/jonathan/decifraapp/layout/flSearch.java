package com.example.jonathan.decifraapp.layout;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.actual_music;
import com.example.jonathan.decifraapp.entities.music;
import com.example.jonathan.decifraapp.utils.page;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class flSearch extends Fragment {

    private actual_music _music;
    private View v = null;
    private EditText txtArtist;
    private EditText txtMusic;
    private Button btnSearch;
    private View vBackGround;
    private ProgressBar ivWaitIcon;
    private LinearLayout llPrincipal;
    private lMusicItem _lMusicItem;
    private ListView lvSearchResult;
    private page _page = page.getInstance(null);

    public flSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fl_search, container, false);
        this.txtArtist = (EditText) v.findViewById(R.id.fl_search_txtArtist);
        this.txtMusic  = (EditText) v.findViewById(R.id.fl_search_txtMusic);
        this.btnSearch = (Button) v.findViewById(R.id.fl_search_btnSearch);
        this.ivWaitIcon = (ProgressBar) v.findViewById(R.id.fl_search_ivWaitIcon);
        this.vBackGround = v.findViewById(R.id.fl_search_vBackGround);
        this.llPrincipal = (LinearLayout) v.findViewById(R.id.fl_search_llPrincipal);
        this.lvSearchResult = (ListView) v.findViewById(R.id.fl_search_lvSearchResult);
        this.btnSearch.setOnClickListener(this.btn_Search_Click);
        this._music = actual_music.getInstance();

        setHasOptionsMenu(true);

        return v;
    }

    private View.OnClickListener btn_Search_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            String nameArtist = txtArtist.getText().toString();
            String nameMusic = txtMusic.getText().toString();

            if ((!nameArtist.equals("")) || (!nameMusic.equals(""))) {

                llPrincipal.setVisibility(View.INVISIBLE);
                vBackGround.setVisibility(View.VISIBLE);
                ivWaitIcon.setVisibility(View.VISIBLE);

                if (nameArtist.equals("") && !nameMusic.equals("")) {
                    new DownloadTask().execute(getString(R.string.app_URL) + "music/" + nameMusic);
                }
                else if (!nameArtist.equals("") && nameMusic.equals("")){
                    new DownloadTask().execute(getString(R.string.app_URL) + "artist/" + nameArtist);
                }
                else if (!nameArtist.equals("") && !nameMusic.equals("")){
                    new DownloadTask().execute(getString(R.string.app_URL) + "artist/" + nameArtist + "/music/" + nameMusic);
                }
            }
            else
                Snackbar.make(v, "Digite uma música ou artista.", Snackbar.LENGTH_LONG)
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.ac_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                // Not implemented here
                return false;
            case R.id.nav_my_ciphers:
                // Do Fragment menu item stuff here
                return false;
            case R.id.nav_cipher:
                // Do Fragment menu item stuff here
                return true;

            default:
                break;
        }

        return false;
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
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String result) {


            if (!result.equals("invalid")) {

                try {
                    JSONObject js = new JSONObject(result);

                    ArrayList<music> contMusics = new ArrayList<music>();

                    for (int i = 0; i < js.getJSONArray("data").length(); i++) {

                        JSONObject jsItem = js.getJSONArray("data").getJSONObject(i);
                        music md = new music();
                        md.set_id(jsItem.getString("_id"));
                        md.set_name(jsItem.getString("name"));
                        md.set_artist(jsItem.getString("artist"));
                        md.set_tab(jsItem.getJSONArray("music").toString());
                        contMusics.add(md);
                    }



                    /*_music.set_tab(js.getJSONArray("data").get(0).toString());
                    _music.set_artist("Randon");
                    _music.set_name("Wat");*/


                    _lMusicItem = new lMusicItem(v.getContext(), R.layout.l_music_item, contMusics);

                    lvSearchResult.setAdapter(_lMusicItem);

                    lvSearchResult.setVisibility(View.VISIBLE);
                    //llPrincipal.setVisibility(View.VISIBLE);
                    vBackGround.setVisibility(View.INVISIBLE);
                    ivWaitIcon.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    _music.set_tab("Not found");
                }

                //goToCipher();
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
        } catch (Exception e) {
            e.printStackTrace();
            return "{ \"success\": false, \"data\":[]  }";
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
