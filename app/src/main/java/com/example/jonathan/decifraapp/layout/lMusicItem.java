package com.example.jonathan.decifraapp.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonathan.decifraapp.R;
import com.example.jonathan.decifraapp.entities.music;

import java.util.List;

/**
 * Created by jonathan on 29/05/16.
 */
public class lMusicItem extends ArrayAdapter<music> {
    private static class ViewHolder {
        LinearLayout lblId;
        TextView lblNameMusic;
        TextView lblNameArtist;
        ImageView ivIconType;
    }

    private LayoutInflater _liInflater;

    public lMusicItem(Context context, int resource, List<music> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        music _music = getItem(position);
        ViewHolder viewHolder;


        viewHolder = new ViewHolder();

        _liInflater = LayoutInflater.from(getContext());

        convertView = _liInflater.inflate(R.layout.l_music_item, parent, false);

        viewHolder.ivIconType = (ImageView) convertView.findViewById(R.id.l_music_item_ivIcon);
        viewHolder.lblId = (LinearLayout) convertView.findViewById(R.id.l_music_item_llmusicitem);
        viewHolder.lblNameMusic = (TextView) convertView.findViewById(R.id.l_music_item_lblMusicName);
        viewHolder.lblNameArtist = (TextView) convertView.findViewById(R.id.l_music_item_lblArtist);

        convertView.setTag(viewHolder);

        viewHolder.lblId.setTag(_music.get_id());
        viewHolder.lblNameMusic.setText(_music.get_name());
        viewHolder.lblNameArtist.setText(_music.get_artist());

        switch (_music.get_type())
        {
            case "acousticguitar":
                viewHolder.ivIconType.setImageResource(R.mipmap.ic_acoustic_guitar);
                break;
            case "bass":
                viewHolder.ivIconType.setImageResource(R.mipmap.ic_bass);
                break;
            case "guitar":
                viewHolder.ivIconType.setImageResource(R.mipmap.ic_guitar);
                break;
        }

        return convertView;

    }

}
