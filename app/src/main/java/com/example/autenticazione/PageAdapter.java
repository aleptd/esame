// questa classe gestisce la scelta del tab accedendo e creando dei tab in base a dove si clicca, se nella posizione 1 o 2.

package com.example.autenticazione;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PageAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int behaviour, int numberOfTabs) {
        super(fm, behaviour);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentRequestsToMe();
            case 1:
                return new FragmentMyRequests();
            case 2:
                return new FragmentMyAvailabilities();
            default:
                return null;


        }
    }


    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
