package dyachenko.androidbeginnerfragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import java.util.Objects;

public class NotesFragment extends CommonFragment {

    private static final String EXTRA_POSITION = "EXTRA_POSITION";
    private int position;
    private boolean isLandscape;

    public NotesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        LinearLayout layout = (LinearLayout) view;
        Context context = getContext();
        for (int i = 0; i < Notes.NOTE_STORAGE.size(); i++) {
            Note note = Notes.NOTE_STORAGE.get(i);
            TextView textView = new TextView(context);
            textView.setText(note.getNumberedTitle(i));
            textView.setTextSize(24);
            textView.setPadding(20, 20, 20, 20);
            textView.setTextColor(getResources().getColor(R.color.teal_700, Objects.requireNonNull(context).getTheme()));

            final int index = i;
            textView.setOnClickListener(v -> {
                position = index;
                showNoteDetails();
            });

            layout.addView(textView);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_POSITION, position);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(EXTRA_POSITION);
        }

        if (isLandscape) {
            showLandNoteDetails();
        }
    }

    private void showLandNoteDetails() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_fragment_container, NoteFragment.newInstance(position, isLandscape))
                .commit();
    }

    private void showPortNoteDetails() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes_fragment_container, NoteFragment.newInstance(position, isLandscape))
                .addToBackStack(null)
                .commit();
    }

    private void showNoteDetails() {
        if (isLandscape) {
            showLandNoteDetails();
        } else {
            showPortNoteDetails();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int index = Notes.searchByPartOfTitle(query.toLowerCase());
                if (index == -1) {
                    Toast.makeText(getActivity(), R.string.nothing_found, Toast.LENGTH_SHORT).show();
                } else {
                    position = index;
                    showNoteDetails();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}