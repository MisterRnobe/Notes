package com.medvedev.nikita.notes.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medvedev.nikita.notes.R;
import com.medvedev.nikita.notes.objects.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Note> notes;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parentView) {
        View view = convertView;
        if (view == null)
            view = layoutInflater.inflate(R.layout.note_item, parentView, false);
        Note n = (Note)getItem(i);
        ((TextView) view.findViewById(R.id.noteTitle)).setText(n.getTitle());
        ((TextView) view.findViewById(R.id.noteText)).setText(n.getNote());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(n.getCreated());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ((TextView) view.findViewById(R.id.noteTime)).setText(dateFormat.format(calendar.getTime()));
        return view;
    }
}
