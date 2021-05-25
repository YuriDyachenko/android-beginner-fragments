package dyachenko.androidbeginnerfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class AboutFragment extends CommonFragment {

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        ((MainActivity) requireActivity()).hideDrawer();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        hideAllMenuItems(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDetach() {
        ((MainActivity) requireActivity()).showDrawer();
        super.onDetach();
    }
}