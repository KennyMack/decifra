package com.example.jonathan.decifraapp.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.music;

import java.util.List;

/**
 * Created by jonathan on 02/06/16.
 */
public class lSearchItem extends ArrayAdapter<music> {
    private static class ViewHolder {
        LinearLayout lblId;
        TextView lblNameMusic;
        TextView lblNameArtist;

    }

    private LayoutInflater _liInflater;

    public lSearchItem(Context context, int resource, List<music> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        music _music = getItem(position);
        ViewHolder viewHolder;

        //if (convertView == null) {

        viewHolder = new ViewHolder();

        _liInflater = LayoutInflater.from(getContext());

        convertView = _liInflater.inflate(R.layout.l_search_item, parent, false);

        viewHolder.lblId = (LinearLayout) convertView.findViewById(R.id.l_search_item_llsearchitem);
        viewHolder.lblNameMusic = (TextView) convertView.findViewById(R.id.l_search_item_lblMusicName);
        viewHolder.lblNameArtist = (TextView) convertView.findViewById(R.id.l_search_item_lblArtist);

        convertView.setTag(viewHolder);

        /*} else {

            viewHolder = (ViewHolder) convertView.getTag();
        }    */

        viewHolder.lblId.setTag(_music.get_id());
        viewHolder.lblNameMusic.setText(_music.get_name());
        viewHolder.lblNameArtist.setText(_music.get_artist());

        return convertView;

    }
}