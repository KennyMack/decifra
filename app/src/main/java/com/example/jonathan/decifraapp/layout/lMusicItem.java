package com.example.jonathan.decifraapp.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    Context v;
    public lMusicItem(Context context, int resource, List<music> objects){
        super(context, resource, objects);
        v = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        music _music = getItem(position);
        ViewHolder viewHolder;

        // inexplicable error using the holder
        //if (convertView == null) {

        viewHolder = new ViewHolder();

        LayoutInflater _liInflater = LayoutInflater.from(getContext());

        convertView = _liInflater.inflate(R.layout.l_music_item, parent, false);

        viewHolder.ivIconType = (ImageView) convertView.findViewById(R.id.l_music_item_ivIcon);
        viewHolder.lblId = (LinearLayout) convertView.findViewById(R.id.l_music_item_llmusicitem);
        viewHolder.lblNameMusic = (TextView) convertView.findViewById(R.id.l_music_item_lblMusicName);
        viewHolder.lblNameArtist = (TextView) convertView.findViewById(R.id.l_music_item_lblArtist);

        convertView.setTag(viewHolder);

        //} else {

        //viewHolder = (ViewHolder) convertView.getTag();
        //}

        viewHolder.lblId.setTag(_music.get_id());
        viewHolder.lblNameMusic.setText(_music.get_name());
        viewHolder.lblNameArtist.setText(_music.get_artist());

        switch (_music.get_type())
        {
            case "acousticguitar":
                viewHolder.ivIconType.setImageResource(R.mipmap.ic_acoustic_guitar);
                viewHolder.ivIconType.setContentDescription(v.getString(R.string.l_music_item_descacoustguitar));
                break;
            case "bass":
                viewHolder.ivIconType.setImageResource(R.mipmap.ic_bass);
                viewHolder.ivIconType.setContentDescription(v.getString(R.string.l_music_item_descbass));
                break;
            case "guitar":
                viewHolder.ivIconType.setImageResource(R.mipmap.ic_guitar);
                viewHolder.ivIconType.setContentDescription(v.getString(R.string.l_music_item_descguitar));
                break;
        }

        return convertView;

    }

}
