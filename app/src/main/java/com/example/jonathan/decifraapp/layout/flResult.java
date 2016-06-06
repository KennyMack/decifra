package com.example.jonathan.decifraapp.layout;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.actual_music;
import com.example.jonathan.decifraapp.entities.actual_search;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class flResult extends Fragment {
    private actual_music _music;
    private actual_search _actual_search;
    private View v = null;
    private ProgressBar ivWaitIcon;
    private ListView lvSearchResult;
    private page _page = page.getInstance(null);

    public flResult() {
        // Required empty public constructor
        this._actual_search = actual_search.getInstance();
        this._music = actual_music.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_fl_result, container, false);
        setHasOptionsMenu(true);

        this.ivWaitIcon = (ProgressBar) v.findViewById(R.id.fl_result_ivWaitIcon);
        this.lvSearchResult = (ListView) v.findViewById(R.id.fl_result_lvSearchResult);

        this.lvSearchResult.setOnItemClickListener(lvSearchResult_itemClick);
        this.lvSearchResult.setOnLongClickListener(lvSearchResult_LongItemClick);

        ivWaitIcon.setVisibility(View.VISIBLE);

        new DownloadTask().execute(getString(R.string.app_URL) + "all/artist/" + this._actual_search.get_searchText() + "/music/" + this._actual_search.get_searchText());

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
            case R.id.nav_home:
                // Not implemented here
                return false;
            case R.id.nav_my_ciphers:
                // Do Fragment menu item stuff here
                return false;
            case R.id.nav_cipher:
                // Do Fragment menu item stuff here
                return true;
            case R.id.nav_result:
                // Do Fragment menu item stuff here
                return false;

            default:
                break;
        }

        return false;
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
                    StringBuilder sb;

                    ArrayList<music> contMusics = new ArrayList<>();

                    for (int i = 0; i < jsResponse.getJSONArray("data").length(); i++) {
                        JSONObject jsItem = jsResponse.getJSONArray("data").getJSONObject(i);

                        for (int music = 0; music < jsItem.getJSONArray("music").length(); music++) {

                            music md = new music();
                            md.set_idApi(jsItem.getString("_id"));
                            md.set_name(jsItem.getString("name"));
                            md.set_artist(jsItem.getString("artist"));

                            JSONObject jsItemMusic = jsItem.getJSONArray("music").getJSONObject(music);
                            md.set_type(jsItemMusic.getString("type"));
                            sb = new StringBuilder();

                            for (int musicContent = 0; musicContent < jsItemMusic.getJSONArray("content").length(); musicContent++) {
                                sb.append(jsItemMusic.getJSONArray("content").get(musicContent));
                                sb.append("\n");
                            }
                            md.set_tab(sb.toString());


                            contMusics.add(md);
                        }
                    }
                    lSearchItem _lSearchItem = new lSearchItem(v.getContext(), R.layout.l_search_item, contMusics);

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
                Toast.makeText(v.getContext(), "Não foi possível carregar.",
                        Toast.LENGTH_LONG).show();
            }
            finally {
                ivWaitIcon.setVisibility(View.INVISIBLE);
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
                sb.append(line);
                sb.append("\n");
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
