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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private ProgressBar ivWaitIcon;
    private LinearLayout llPrincipal;
    private lSearchItem _lSearchItem;
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
        this.llPrincipal = (LinearLayout) v.findViewById(R.id.fl_search_llPrincipal);
        this.lvSearchResult = (ListView) v.findViewById(R.id.fl_search_lvSearchResult);
        this.btnSearch.setOnClickListener(this.btn_Search_Click);
        this._music = actual_music.getInstance();

        this.lvSearchResult.setOnItemClickListener(lvSearchResult_itemClick);
        this.lvSearchResult.setOnLongClickListener(lvSearchResult_LongItemClick);
        setHasOptionsMenu(true);

        return v;
    }

    private AdapterView.OnItemClickListener lvSearchResult_itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            music _ms = (music) lvSearchResult.getItemAtPosition(position);

            if (_ms != null) {
                _music.set_artist(_ms.get_artist());
                _music.set_name(_ms.get_name());
                _music.set_tab(_ms.get_tab());
                _music.set_id(_ms.get_id());
                _music.set_type(_ms.get_type());

                goToCipher();
            }
        }
    };


    private View.OnLongClickListener lvSearchResult_LongItemClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            Toast.makeText(v.getContext(), "Salvar cifra", Toast.LENGTH_LONG).show();
            return false;
        }
    };

    private View.OnClickListener btn_Search_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            String nameArtist = txtArtist.getText().toString();
            String nameMusic = txtMusic.getText().toString();

            if ((!nameArtist.equals("")) || (!nameMusic.equals(""))) {

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

    private void goToCipher() {
        _page.setPageVisible(R.id.nav_cipher);
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return downloadContent(params[0]);
            } catch (IOException e) {
                return "{ \"success\": false, \"data\":[]  }";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            JSONObject jsResponse;
            try {
                jsResponse = new JSONObject(result);

                if (jsResponse.getBoolean("success") &&
                    jsResponse.getJSONArray("data").length() > 0) {

                    ArrayList<music> contMusics = new ArrayList<music>();

                    for (int i = 0; i < jsResponse.getJSONArray("data").length(); i++) {
                        JSONObject jsItem = jsResponse.getJSONArray("data").getJSONObject(i);

                        for (int music = 0; music < jsItem.getJSONArray("music").length(); music++) {

                            music md = new music();
                            //TODO: Mudar para get_idAPi
                            md.set_id(jsItem.getString("_id"));
                            md.set_name(jsItem.getString("name"));
                            md.set_artist(jsItem.getString("artist"));

                            JSONObject jsItemMusic = jsItem.getJSONArray("music").getJSONObject(music);
                            md.set_type(jsItemMusic.getString("type"));
                            String content = "";

                            for (int musicContent = 0; musicContent < jsItemMusic.getJSONArray("content").length(); musicContent++) {
                                content +=  jsItemMusic.getJSONArray("content").get(musicContent) + "\n";
                            }
                            md.set_tab(content);


                            contMusics.add(md);
                        }
                    }
                    _lSearchItem = new lSearchItem(v.getContext(), R.layout.l_search_item, contMusics);

                    lvSearchResult.setAdapter(_lSearchItem);

                    lvSearchResult.setVisibility(View.VISIBLE);
                    ivWaitIcon.setVisibility(View.INVISIBLE);

                }
                else {
                    Snackbar.make(v, "Nenhuma Cifra encontrada.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
            catch (JSONException e) {
                Toast.makeText(v.getContext(), "Não foi possível carregar. Tente novamente",
                        Toast.LENGTH_LONG).show();
            }
            finally {
                llPrincipal.setVisibility(View.VISIBLE);
                ivWaitIcon.setVisibility(View.INVISIBLE);
                jsResponse = null;
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
